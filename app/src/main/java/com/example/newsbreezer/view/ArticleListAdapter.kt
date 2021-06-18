package com.example.newsbreezer.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.newsbreezer.R
import com.example.newsbreezer.model.BreakingNewsModel
import kotlinx.android.synthetic.main.adapter_article_item.view.*


class ArticleListAdapter(
        private var companyList: MutableList<BreakingNewsModel.Article>,
        private val clickListener: ArticleListListener
) : RecyclerView.Adapter<ArticleListAdapter.ViewHolder>() {

    public interface  ArticleListListener{
        fun onReadButtonClick(article: BreakingNewsModel.Article)
        fun onSaveButtonClick(isSaved:Boolean,article: BreakingNewsModel.Article)
        fun onSaveIconClick(isSaved:Boolean,article: BreakingNewsModel.Article)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.adapter_article_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return companyList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val companyDetails: BreakingNewsModel.Article = companyList[position]
        holder.bind(clickListener,companyDetails)

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(clickListener: ArticleListListener,articleDetails: BreakingNewsModel.Article) {
            itemView.tag = articleDetails

            with(itemView) {
                try {

                    /*with(companyDetails) {
                        title?.let { tvCompanyName.text = it }
                        description?.let { tvTitle.text = it }
                        publishedAt?.let { tvBody.text = it }
                    }*/

                    articleDetails.urlToImage?.let {
                        //Glide.with(this).load(it).into(imgView)
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
                    articleDetails.description?.let { tvDescription.text = it }
                    articleDetails.publishedAt?.let { tvDate.text = it }

                    /*imageIconSave.setOnClickListener {
                        clickListener.onSaveIconClick(articleDetails)
                        //if(imageIconSave.)
                    }*/
                    btnRead.setOnClickListener {
                        clickListener.onReadButtonClick(articleDetails)
                    }

                    btnSave.setOnClickListener {

                        if(btnSave.text.equals("Save")){
                            btnSave.text = "Saved"
                            imageIconSave.setBackgroundColor(ContextCompat.getColor(context, R.color.button_active))
                            imageIconSave.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_save_active))
                            clickListener.onSaveButtonClick(true,articleDetails)

                        }else{
                            btnSave.text = "Save"
                            imageIconSave.setBackgroundColor(ContextCompat.getColor(context, R.color.button_inactive))
                            imageIconSave.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_save_inactive))
                            clickListener.onSaveButtonClick(false,articleDetails)

                        }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}

/*class ArticleListListener(val clickListener: (article: BreakingNewsModel.Article) -> Unit) {
    fun onReadButtonClick(article: BreakingNewsModel.Article) = clickListener(article)
    fun onSaveButtonClick(article: BreakingNewsModel.Article) = clickListener(article)
    fun onSaveIconClick(article: BreakingNewsModel.Article) = clickListener(article)
}*/















