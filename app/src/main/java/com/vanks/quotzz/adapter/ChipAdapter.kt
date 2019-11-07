package com.vanks.quotzz.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.vanks.quotzz.databinding.SingleLabelBinding
import com.vanks.quotzz.model.*
import com.vanks.quotzz.viewmodel.MainActivityViewModel
import com.vanks.quotzz.viewmodel.convertStringToLiveData


class ChipAdapter(val context: Context, val articleRepository: ArticleRepository, val viewModel: MainActivityViewModel) : RecyclerView.Adapter<ChipViewHolder>() {

    var labels: ArrayList<Label> = ArrayList()

    fun setData(items: ArrayList<Label>) {
        labels = items
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChipViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<SingleLabelBinding>(
            inflater,
            com.vanks.quotzz.R.layout.single_label,
            parent,
            false
        )
        return ChipViewHolder(binding)
    }

    override fun getItemCount() = labels.size

    override fun onBindViewHolder(holder: ChipViewHolder, position: Int) {
        holder.binding.label = labels[position]
        holder.binding.labelButton.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                viewModel.searchTerm = convertStringToLiveData(labels[position].name)
                viewModel.articles = articleRepository.pullArticles(ArticleQuery(ArticleQueryType.CATEGORY, "", position))
            }
        })
    }

}

class ChipViewHolder(val binding: SingleLabelBinding) :
    RecyclerView.ViewHolder(binding.getRoot())