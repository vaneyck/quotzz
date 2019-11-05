package com.vanks.quotzz.adapter

import androidx.recyclerview.widget.RecyclerView
import com.vanks.quotzz.model.News
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.vanks.quotzz.databinding.SingleArticleBinding


class ArticleAdapter : RecyclerView.Adapter<ArticleViewHolder>() {

    var articles: ArrayList<News> = ArrayList()

    fun setData(items: ArrayList<News>) {
        articles = items
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<SingleArticleBinding>(inflater, com.vanks.quotzz.R.layout.single_article, parent, false)
        return ArticleViewHolder(binding)
    }

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.binding.article = articles[position]
    }

}

class ArticleViewHolder(val binding: SingleArticleBinding) : RecyclerView.ViewHolder(binding.getRoot())