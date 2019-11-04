package com.vanks.quotzz.model

import android.content.Context
import android.util.Log
import androidx.annotation.Dimension
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.*
import retrofit2.http.GET

class Quote {
    var quote: String = ""
    var author: String = ""
    var tags: List<String> = ArrayList<String>()

    fun getFontSize () : Int {
        return 100
    }

    override fun toString(): String {
        return "Quote(quote='$quote', author='$author', tags=$tags)"
    }
}

class QuoteJson {
    var quotes: List<Quote> = ArrayList<Quote>()
    override fun toString(): String {
        return "QuoteJson(quotes=$quotes)"
    }
}

class QuoteViewModel : ViewModel() {
    val quotes: LiveData<QuoteJson> = QuoteRepository().getQuotes()
}

interface Webservice {
    @GET("/v1/quotes")
    fun getQuotes(): Call<QuoteJson>
}

class QuoteRepository {
    fun getQuotes(): LiveData<QuoteJson> {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://opinionated-quotes-api.gigalixirapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val webservice: Webservice = retrofit.create(Webservice::class.java)

        val data = MutableLiveData<QuoteJson>()
        webservice.getQuotes().enqueue(object : Callback<QuoteJson> {
            override fun onResponse(call: Call<QuoteJson>, response: Response<QuoteJson>) {
                data.value = response.body()
            }

            // Error case is left out for brevity.
            override fun onFailure(call: Call<QuoteJson>, t: Throwable) {
                Log.e("QuoteRepository", t.toString())
                val d = QuoteJson()
//                d.quotes = [Quote()]
//                data.value =
            }
        })
        return data
    }
}