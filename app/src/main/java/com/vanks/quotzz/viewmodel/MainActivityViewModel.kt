package com.vanks.quotzz.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vanks.quotzz.model.ArticleJson

class MainActivityViewModel : ViewModel() {
    var articles: LiveData<ArticleJson> =
        MutableLiveData<ArticleJson>()
    var searchTerm: LiveData<String> =
        MutableLiveData<String>()
}

fun convertStringToLiveData(term: String) : LiveData<String> {
    val s = MutableLiveData<String>()
    s.value = term
    return s
}