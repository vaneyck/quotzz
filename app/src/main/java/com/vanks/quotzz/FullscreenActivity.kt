package com.vanks.quotzz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.vanks.quotzz.adapter.ArticleAdapter
import com.vanks.quotzz.adapter.ChipAdapter
import com.vanks.quotzz.databinding.ActivityFullscreenBinding
import com.vanks.quotzz.model.*
import com.vanks.quotzz.model.LabelCollection
import com.vanks.quotzz.viewmodel.MainActivityViewModel
import com.vanks.quotzz.viewmodel.convertStringToLiveData
import kotlinx.android.synthetic.main.activity_fullscreen.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FullscreenActivity : AppCompatActivity() {

    val TAG : String = "FullscreenActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fullscreen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.hide()

        val initialSearchQuery = LabelCollection.labels[0].name

        val binding: ActivityFullscreenBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_fullscreen
        )

        val articleRepository = ArticleRepository(this)

        val viewModel: MainActivityViewModel by viewModels()
        viewModel.articles = articleRepository.pullArticles(ArticleQuery(ArticleQueryType.CATEGORY, "", 0))
        viewModel.searchTerm = convertStringToLiveData(initialSearchQuery)

        // Observing changes in ViewModel
        viewModel.articles.observe(this, Observer<ArticleJson> { json ->
            if (json.articles.size > 0) {
                Log.d(TAG, "Setting articles ${json.articles.size}")
                binding.articles = json
            }
        })

        viewModel.searchTerm.observe(this, Observer<String> { term ->
            Log.d(TAG, "Listening and setting search term ${term}" )
            binding.searchTerm = term
        })

        // Setting recycler view after data is loaded
        val adapter = ArticleAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        binding.labels = LabelCollection()

        // Chip config
        val labelAdapter = ChipAdapter(this, articleRepository, viewModel)
        labelsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        labelsRecyclerView.adapter = labelAdapter

        search_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val searchTerm = search_field.text.toString().trim()
                viewModel.searchTerm = convertStringToLiveData(searchTerm)
                viewModel.articles = articleRepository.pullArticles(ArticleQuery(ArticleQueryType.QUERY, searchTerm, 0))
            }
        })
    }
}
