package com.example.newsbreezer.view.saved

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.newsbreezer.R
import com.example.newsbreezer.model.BreakingNewsModel
import kotlinx.android.synthetic.main.adapter_saved_article_item.view.*


class SavedArticleListAdapter(
        private var companyList: MutableList<BreakingNewsModel.Article>,
) : RecyclerView.Adapter<SavedArticleListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.adapter_saved_article_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return companyList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val companyDetails: BreakingNewsModel.Article = companyList[position]
        holder.bind(companyDetails)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(articleDetails: BreakingNewsModel.Article) {
            itemView.tag = articleDetails

            with(itemView) {
                try {
                    articleDetails.urlToImage?.let {
                        imgView.clipToOutline = true
                        var requestOptions = RequestOptions()
                        requestOptions = requestOptions.transform(CenterCrop(), RoundedCorners(context.resources.getDimension(R.dimen.article_corner_radius).toInt()))
                        Glide.with(context)
                                .load(it)
                                .placeholder(R.drawable.placeholder)
                                .apply(requestOptions)
                                .into(imgView)
                    }
                    articleDetails.title?.let { tvTitle.text = it }
                    articleDetails.source?.name?.let { tvHeader.text = it }
                    articleDetails.publishedAt?.let { tvDate.text = it }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}















