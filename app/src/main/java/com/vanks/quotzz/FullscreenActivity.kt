package com.vanks.quotzz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.vanks.quotzz.databinding.ActivityFullscreenBinding
import com.vanks.quotzz.model.QuoteJson
import com.vanks.quotzz.model.QuoteRepository
import com.vanks.quotzz.model.QuoteViewModel
import kotlinx.android.synthetic.main.activity_fullscreen.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FullscreenActivity : AppCompatActivity() {

    private val viewModel: QuoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fullscreen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.hide()

        val binding: ActivityFullscreenBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_fullscreen
        )
        val initialSearchQuery = "Latest News"
        binding.searchTerm = initialSearchQuery

        val quoteRepository = QuoteRepository()
        viewModel.quotes = quoteRepository.pullArticles("Latest News")


        viewModel.quotes.observe(this, Observer<QuoteJson> { json ->
            if (json.articles.size > 0) {
                binding.quote = json.articles[0]
            }
        })

        search_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val searchTerm = search_field.text.toString()
                binding.searchTerm = searchTerm
                viewModel.quotes = quoteRepository.pullArticles(searchTerm)
            }
        })
    }
}
