package com.vanks.quotzz.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.vanks.quotzz.model.News
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.vanks.quotzz.databinding.SingleArticleBinding
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent
import android.net.Uri


class ArticleAdapter(val context: Context) : RecyclerView.Adapter<ArticleViewHolder>() {

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
        holder.binding.readButton.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val uri = Uri.parse(articles[position].url)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                context.startActivity(intent)
            }
        })
    }

}

class ArticleViewHolder(val binding: SingleArticleBinding) : RecyclerView.ViewHolder(binding.getRoot())