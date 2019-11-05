package com.vanks.quotzz.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.*
import retrofit2.http.GET
import retrofit2.http.Query


class News {
    var author: String = ""
    var title: String = ""
    var description: String = ""
    var url: String = ""
    var urlToImage: String = ""
    var content: String = ""
    var publishedAt: String = ""
    var source: HashMap<String, String> = HashMap<String, String>()

    fun getOrigin() : String {
        if(this.author != null) {
            return this.author + " @ " + this.source["name"].toString()
        } else {
            return this.source["name"].toString()
        }
    }
}

class QuoteJson {
    val articles: ArrayList<News> = ArrayList<News>()
}

class QuoteViewModel : ViewModel() {
    var quotes: LiveData<QuoteJson> = QuoteRepository().data
}

interface Webservice {
    @GET("https://newsapi.org/v2/everything?apiKey=37bd379b7c7e4280ad84f7e8d176e870")
    fun getQuotes(@Query("q") searchQuery: String): Call<QuoteJson>
}

class QuoteRepository {
    var data = MutableLiveData<QuoteJson>()

    fun pullArticles(searchQuery: String): LiveData<QuoteJson> {
        // Set default values
        val d = QuoteJson()
        var a = News()
        a.author = "Searching"
        a.source["name"] = "Internet"
        a.title = "Pulling articles..."
        a.urlToImage = "https://giphygifs.s3.amazonaws.com/media/RHBAyK1GxTgPe/giphy.gif"
        d.articles.add(a)
        data.value = d

        val retrofit = Retrofit.Builder()
            .baseUrl("https://quotes.rest")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val webservice: Webservice = retrofit.create(Webservice::class.java)

        webservice.getQuotes(searchQuery).enqueue(object : Callback<QuoteJson> {
            override fun onResponse(call: Call<QuoteJson>, response: Response<QuoteJson>) {
                if (response.body() != null) {
                    data.value = response.body()
                }
            }

            // Error case is left out for brevity.
            override fun onFailure(call: Call<QuoteJson>, t: Throwable) {
                Log.e("QuoteRepository", t.toString())
            }
        })
        return data
    }
}