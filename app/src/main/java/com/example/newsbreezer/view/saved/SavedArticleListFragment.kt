package com.example.newsbreezer.view.saved

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsbreezer.R
import com.example.newsbreezer.model.BreakingNewsModel
import com.example.newsbreezer.view.HomeViewModel
import com.example.newsbreezer.view.HomeViewModelFactory
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import kotlinx.android.synthetic.main.fragment_article_details.*
import kotlinx.android.synthetic.main.fragment_article_list.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.lang.Exception


class SavedArticleListFragment : Fragment(),KodeinAware {

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
        return inflater.inflate(R.layout.fragment_saved_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_back_black)
        toolbar.background.alpha = 0
        toolbar.title = "Saved"

        bottomLayout.setShapeBackground()

        //todo get live data of saved news from room data base

        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        viewModel.newsSavedData.observe(viewLifecycleOwner, {
            Log.e("TAG", "newsSavedData :------------ ${it.size}")
            initRecyclerView(it as MutableList<BreakingNewsModel.Article>)

        })

    }

    private fun initRecyclerView(items: MutableList<BreakingNewsModel.Article>) {
        try {
            if (items.isNullOrEmpty() || items.size == 0) {
                tvNoData.visibility = View.VISIBLE
            } else {
                tvNoData.visibility = View.GONE
                val reportListAdapter = SavedArticleListAdapter(items)
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = reportListAdapter
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()

        }
    }

    private fun View.setShapeBackground() {
        val cornerSize = resources.getDimension(R.dimen.background_corner_radius)
        background = MaterialShapeDrawable(
            ShapeAppearanceModel.builder()
                .setTopLeftCornerSize(cornerSize)
                .setTopRightCornerSize(cornerSize)
                .build()
        ).apply {
            setTint(ContextCompat.getColor(requireActivity(), R.color.white))
            paintStyle = Paint.Style.FILL
        }
    }



}