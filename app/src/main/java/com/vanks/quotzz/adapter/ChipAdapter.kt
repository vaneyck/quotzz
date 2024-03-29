package com.vanks.quotzz.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.vanks.quotzz.databinding.SingleLabelBinding
import com.vanks.quotzz.model.*
import com.vanks.quotzz.viewmodel.MainActivityViewModel

class ChipAdapter(
    val context: Context,
    val articleRepository: ArticleRepository,
    val viewModel: MainActivityViewModel
) : RecyclerView.Adapter<ChipViewHolder>() {

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

        holder.binding.labelButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                // Mark as selected the active label
                articleRepository.pullLabelCollection().value?.labels?.forEach { it.selected = false }
                articleRepository.pullLabelCollection().value?.labels?.get(position)?.selected = true
                viewModel.searchTerm = articleRepository.generateSearchTerm(labels[position])
                viewModel.articles = articleRepository.pullArticles(
                    ArticleQuery(
                        ArticleQueryType.CATEGORY,
                        "",
                        position
                    )
                )
            }
        })
    }

}

class ChipViewHolder(val binding: SingleLabelBinding) :
    RecyclerView.ViewHolder(binding.getRoot())