package com.vanks.quotzz.bindingadapter;

import android.util.Log;
import android.widget.ImageView;
import android.R;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

public class ImageViewBindingAdapter {
    @BindingAdapter({"imageUrl"})
    public static void imageLoader(ImageView imageView, String imageUrl) {
        Log.i("ImageViewBindingAdapter", "url " + imageUrl);
        Picasso.get().load(imageUrl)
                .placeholder(R.drawable.picture_frame)
                .into(imageView);
    }
}
