package com.vanks.quotzz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.vanks.quotzz.databinding.ActivityFullscreenBinding
import com.vanks.quotzz.model.Quote
import com.vanks.quotzz.model.QuoteJson
import com.vanks.quotzz.model.QuoteViewModel

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

        viewModel.quotes.observe(this, Observer<QuoteJson> { json ->
            binding.quote = json.quotes[0]

        })

        // dummy_button.setOnTouchListener(mDelayHideTouchListener)
    }
}
