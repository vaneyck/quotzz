package com.vanks.quotzz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.vanks.quotzz.adapter.ArticleAdapter
import com.vanks.quotzz.databinding.ActivityFullscreenBinding
import com.vanks.quotzz.model.ArticleJson
import com.vanks.quotzz.model.ArticleRepository
import com.vanks.quotzz.model.QuoteViewModel
import kotlinx.android.synthetic.main.activity_fullscreen.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FullscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fullscreen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.hide()


        val binding: ActivityFullscreenBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_fullscreen
        )

        val initialSearchQuery = "Latest News"
        val quoteRepository = ArticleRepository()
        binding.searchTerm = initialSearchQuery

        val viewModel: QuoteViewModel by viewModels()
        viewModel.articles = quoteRepository.pullArticles("Latest News")
        viewModel.articles.observe(this, Observer<ArticleJson> { json ->
            println ("###### Pulled some articles " + json.articles.size)
            if (json.articles.size > 0) {
                binding.articles = json
            }
        })

        // Setting recycler view after data is loaded
        val adapter = ArticleAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        search_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val searchTerm = search_field.text.toString()
                binding.searchTerm = searchTerm
                viewModel.articles = quoteRepository.pullArticles(searchTerm)
            }
        })
    }
}
