package com.vanks.quotzz.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vanks.quotzz.model.ArticleJson
import com.vanks.quotzz.model.Label
import com.vanks.quotzz.model.LabelCollection

class MainActivityViewModel : ViewModel() {
    var articles: LiveData<ArticleJson> =
        MutableLiveData<ArticleJson>()
    var searchTerm: LiveData<SearchTerm> =
        MutableLiveData<SearchTerm>()
    var labelCollection: LiveData<LabelCollection> =
        MutableLiveData<LabelCollection>()
}

class SearchTerm(val term: String?, val label: Label?) {
    fun getText(): String {
        if (term != null) {
            return term
        } else if (label != null) {
            return label.name
        } else {
            return ""
        }
    }
}