package com.vanks.quotzz.bindingadapter;

import android.util.Log;
import android.widget.ImageView;
import android.R;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vanks.quotzz.adapter.ArticleAdapter;
import com.vanks.quotzz.model.ArticleJson;

public class CustomBindingAdapter {
    @BindingAdapter({"imageUrl"})
    public static void imageLoader(ImageView imageView, String imageUrl) {
        Log.i("CustomBindingAdapter", "url " + imageUrl);
        Picasso.get().load(imageUrl)
                //.placeholder(R.drawable.picture_frame)
                .into(imageView);
    }

    @BindingAdapter("data")
    public static void setArticlesInRecyclerView(RecyclerView recyclerView, ArticleJson articleJson) {
        ArticleAdapter adapter = (ArticleAdapter) recyclerView.getAdapter();
        adapter.setData(articleJson.getArticles());
    }
}
