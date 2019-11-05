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

class ArticleJson {
    val articles: ArrayList<News> = ArrayList<News>()
}

class QuoteViewModel : ViewModel() {
    var articles: LiveData<ArticleJson> = ArticleRepository().data
}

interface Webservice {
    @GET("https://newsapi.org/v2/everything?apiKey=37bd379b7c7e4280ad84f7e8d176e870")
    fun getQuotes(@Query("q") searchQuery: String): Call<ArticleJson>
}

class ArticleRepository {
    var data = MutableLiveData<ArticleJson>()

    fun pullArticles(searchQuery: String): LiveData<ArticleJson> {
        // Set default values
        val d = ArticleJson()
        var a = News()
        a.author = "Searching"
        a.source["name"] = "Internet"
        a.title = "Retrieving articles..."
        a.url = "https://google.com"
        a.urlToImage = "https://i.ytimg.com/vi/bhuKGm7CGHk/maxresdefault.jpg"
        d.articles.add(a)
        data.value = d

        val retrofit = Retrofit.Builder()
            .baseUrl("https://articles.rest")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val webservice: Webservice = retrofit.create(Webservice::class.java)

        webservice.getQuotes(searchQuery).enqueue(object : Callback<ArticleJson> {
            override fun onResponse(call: Call<ArticleJson>, response: Response<ArticleJson>) {
                if (response.body() != null) {
                    data.value = response.body()
                }
            }

            // Error case is left out for brevity.
            override fun onFailure(call: Call<ArticleJson>, t: Throwable) {
                Log.e("ArticleRepository", t.toString())
            }
        })
        return data
    }
}