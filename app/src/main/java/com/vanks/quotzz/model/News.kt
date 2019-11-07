package com.vanks.quotzz.model

import androidx.annotation.MainThread
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


class News {
    var author: String? = null
    var title: String = ""
    var description: String = ""
    var url: String? = ""
    var urlToImage: String = ""
    var content: String = ""
    var publishedAt: String = ""
    var source: HashMap<String, String> = HashMap<String, String>()

    fun getOrigin(): String {
        if (this.author != null) {
            return this.author + " @ " + this.source["name"].toString()
        } else {
            return this.source["name"].toString()
        }
    }
}

class ArticleJson {
    val totalResults: Int = 0
    val articles: ArrayList<News> = ArrayList<News>()
}

interface Webservice {
    @GET("https://newsapi.org/v2/everything?apiKey=37bd379b7c7e4280ad84f7e8d176e870&language=en")
    fun getArticlesByQuery(@Query("q") searchQuery: String): Call<ArticleJson>

    @GET("https://newsapi.org/v2/top-headlines?apiKey=37bd379b7c7e4280ad84f7e8d176e870&language=en")
    fun getTopHeadlinesArticles(): Call<ArticleJson>

    @GET("https://newsapi.org/v2/top-headlines?apiKey=37bd379b7c7e4280ad84f7e8d176e870&language=en")
    fun getLocalNews(@Query("country") country: String): Call<ArticleJson>
}


enum class ArticleQueryType {
    QUERY,
    CATEGORY
}

class ArticleQuery(val queryType: ArticleQueryType, val queryString: String, val categoryIndex: Int)