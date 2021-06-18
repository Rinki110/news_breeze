package com.example.newsbreezer.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsbreezer.R
import com.example.newsbreezer.data.network.ResultData
import com.example.newsbreezer.model.BreakingNewsModel
import com.example.newsbreezer.view.ArticleListAdapter.ArticleListListener
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_article_details.*
import kotlinx.android.synthetic.main.fragment_article_list.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.lang.Exception



/**
 * A simple [Fragment] subclass.
 * Use the [ArticleListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArticleListFragment : Fragment(), KodeinAware,ArticleListListener {

    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        viewModel.getNewsDetails()
        viewModel.newsResultData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultData.Loading -> Loader.show(requireActivity())
                is ResultData.Failure -> {
                    Loader.hide()
                    // Todo: we can use dialog to display error msg or we can add textView in layout to display error msg here
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
                is ResultData.Success -> {
                    Loader.hide()
                    initRecyclerView(it.value as MutableList<BreakingNewsModel.Article>)
                }
            }
        })

        imageIconSaved.setOnClickListener {
            findNavController().navigate(R.id.savedArticleFragment)
        }
    }

    private fun initRecyclerView(items: MutableList<BreakingNewsModel.Article>) {
        try {
            if (items.isNullOrEmpty() || items.size == 0) {
                tvNoData.visibility = View.VISIBLE
            } else {
                tvNoData.visibility = View.GONE
                val reportListAdapter = ArticleListAdapter(items,this)
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = reportListAdapter
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            //Log.e(TAG, "Error :------------ ${e.message}")
        }
    }

    override fun onReadButtonClick(article: BreakingNewsModel.Article) {
        val action = ArticleListFragmentDirections.actionArticleListFragmentToArticleDetailsFragment(Gson().toJson(article))
        findNavController().navigate(action)

    }

    override fun onSaveButtonClick(isSaved:Boolean,article: BreakingNewsModel.Article) {

        //todo need to save in room database
        if(isSaved)
            viewModel.saveSelectedArticleDetails(article)
        else
            viewModel.removeSelectedArticleDetails(article)
    }

    override fun onSaveIconClick(isSaved:Boolean,article: BreakingNewsModel.Article) {

    }


}