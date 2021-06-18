package com.example.newsbreezer.view


import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.newsbreezer.R
import com.example.newsbreezer.model.BreakingNewsModel
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_article_details.*



class ArticleDetailsFragment : Fragment() {


    private lateinit var articleData: BreakingNewsModel.Article

    override fun onAttach(context: Context) {
        super.onAttach(context)

        setHasOptionsMenu(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            val safeArgs = ArticleDetailsFragmentArgs.fromBundle(it)
            articleData = Gson().fromJson(
                safeArgs.articleDetails,
                BreakingNewsModel.Article::class.java
            )
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_back_white)
        toolbar.background.alpha = 0
        //toolbar.setNavigationOnClickListener(View.OnClickListener { activity!!.onBackPressed() })

        bottomLayout.setShapeBackground()
        bindDataWithView()
    }

    private fun bindDataWithView(){
        try {
            Glide.with(this).load(articleData.urlToImage).into(imgView)
            tvDate.text = articleData.publishedAt
            tvTitle.text = articleData.title
            articleData.author?.let { tvAuthor.text =it }
            tvDescription.text = articleData.description

        }catch (e: Exception){
            e.printStackTrace()
        }

    }

    private fun View.setShapeBackground() {
        val cornerSize = resources.getDimension(R.dimen.background_corner_radius)
        background = MaterialShapeDrawable(
            ShapeAppearanceModel.builder()
                .setTopLeftCornerSize(cornerSize)
                .setTopRightCornerSize(cornerSize)
                // .setTopLeftCorner(CutCornerTreatment())

                .build()
        ).apply {
            //fillColor = ColorStateList.valueOf(Color.WHITE)
            setTint(ContextCompat.getColor(requireActivity(), R.color.screen_background))
            paintStyle = Paint.Style.FILL


        }
    }

    private fun setToolbar() {
        try {
            //toolbar.setBackgroundColor()


            /*for (i in 0 until toolbar.childCount) {
                val child = toolbar.getChildAt(i)
                //Log.e(TAG,"Print toolbar child :--- $child")
                if (child is TextView) {
                    //child.paintFlags = child.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                    child.background = ContextCompat.getDrawable(requireActivity(), R.drawable.dotted_line)

                    break
                }
            }*/
        } catch (e: java.lang.Exception) {
            e.printStackTrace()

        }
    }
}