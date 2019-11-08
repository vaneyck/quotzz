package com.vanks.quotzz.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.*
import com.vanks.quotzz.util.getDeviceCountryCode
import com.vanks.quotzz.viewmodel.SearchTerm

class ArticleRepository(val context: Context) {
    val TAG = "ArticleRepository"

    var data = MutableLiveData<ArticleJson>()
    var searchConfig = MutableLiveData<SearchTerm>()
    var labelCollection = MutableLiveData<LabelCollection>()

    fun generateSearchTerm(term : String): LiveData<SearchTerm> {
        searchConfig.value = SearchTerm(term, null)
        return searchConfig
    }

    fun generateSearchTerm(label : Label) : LiveData<SearchTerm> {
        searchConfig.value = SearchTerm(null, label)
        return searchConfig
    }

    fun pullLabelCollection() : LiveData<LabelCollection> {
        labelCollection.value = LabelCollection()
        return labelCollection
    }

    fun pullArticles(articleQuery: ArticleQuery): LiveData<ArticleJson> {
        // Set default values
        data.value = getSearchingContent()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://articles.rest")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val webservice: Webservice = retrofit.create(Webservice::class.java)

        Log.d(TAG, "QueryType " + articleQuery.queryType)

        if (articleQuery.queryType.toString() == ArticleQueryType.QUERY.toString()) {
            Log.d(TAG, "Getting Query ")
            webservice.getArticlesByQuery(articleQuery.queryString).enqueue(saveData(data))
        } else if (articleQuery.queryType.toString() == ArticleQueryType.CATEGORY.toString()) {
            Log.d(TAG, "Getting Category ")
            if (articleQuery.categoryIndex == 0) {
                webservice.getTopHeadlinesArticles().enqueue(saveData(data))
            } else if (articleQuery.categoryIndex == 1) {
                Log.d(TAG, "Country code is ${getDeviceCountryCode(context)}")
                webservice.getLocalNews(getDeviceCountryCode(context)).enqueue(saveData(data))
            } else if (articleQuery.categoryIndex == 2) {
                webservice.getCategoryNews("business").enqueue(saveData(data))
            } else if (articleQuery.categoryIndex == 3) {
                webservice.getCategoryNews("technology").enqueue(saveData(data))
            }
        }
        return data
    }

    fun saveData(data: MutableLiveData<ArticleJson>): Callback<ArticleJson> {
        return object : Callback<ArticleJson> {
            override fun onResponse(call: Call<ArticleJson>, response: Response<ArticleJson>) {
                Log.i(TAG, "In callback " + response.body())
                if (response.body() != null) {
                    data.value = response.body()
                    if (data.value!!.totalResults == 0) {
                        data.value = getEmptyState()
                    }

                }
            }

            // Error case is left out for brevity.
            override fun onFailure(call: Call<ArticleJson>, t: Throwable) {
                Log.e(TAG, t.toString())
                data.value = getEmptyState()
            }
        }
    }

    private fun getSearchingContent(): ArticleJson {
        val d = ArticleJson()
        var a = News()
        a.author = null
        a.source["name"] = ""
        a.title = "Retrieving articles..."
        a.url = null
        a.urlToImage = "https://i.ytimg.com/vi/bhuKGm7CGHk/maxresdefault.jpg"
        d.articles.add(a)
        return d
    }

    private fun getEmptyState(): ArticleJson {
        val d = ArticleJson()
        var a = News()
        a.author = null
        a.source["name"] = ""
        a.title = "Did not get any matches :-("
        a.url = null
        a.urlToImage =
            "https://www.pnglot.com/pngfile/detail/84-844638_cartoon-business-man-working-with-computer-computer-work.png"
        d.articles.add(a)
        return d
    }
}