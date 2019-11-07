package com.vanks.quotzz.bindingadapter;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vanks.quotzz.R;
import com.vanks.quotzz.adapter.ArticleAdapter;
import com.vanks.quotzz.adapter.ChipAdapter;
import com.vanks.quotzz.model.ArticleJson;
import com.vanks.quotzz.model.LabelCollection;

public class CustomBindingAdapter {
    private static String TAG = "CustomBindingAdapter";

    @BindingAdapter({"imageUrl"})
    public static void imageLoader(ImageView imageView, String imageUrl) {
        Log.i(TAG, "Loading image : url is " + imageUrl);
        if (imageUrl == null || imageUrl.isEmpty()) {
            return;
        }
        Picasso.get().load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(imageView);
    }

    @BindingAdapter("articleData")
    public static void setArticlesInRecyclerView(RecyclerView recyclerView, ArticleJson articleJson) {
        ArticleAdapter adapter = (ArticleAdapter) recyclerView.getAdapter();
        adapter.setData(articleJson.getArticles());
    }

    @BindingAdapter("labelData")
    public static void setLabelsInRecyclerView(RecyclerView recyclerView, LabelCollection labelCollection) {
        ChipAdapter adapter = (ChipAdapter) recyclerView.getAdapter();
        adapter.setData(labelCollection.labels);
    }
}
