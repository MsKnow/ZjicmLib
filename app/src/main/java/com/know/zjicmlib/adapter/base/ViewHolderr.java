package com.know.zjicmlib.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.know.zjicmlib.APP;
import com.know.zjicmlib.R;
import com.know.zjicmlib.modle.bean.Boo;

/**
 * Created by know on 2016/4/24.
 */
public class ViewHolderr extends RecyclerView.ViewHolder {

    SparseArray<View> views;
    View itemView;

    public ViewHolderr(View itemView) {
        super(itemView);
        views = new SparseArray<>();
        this.itemView = itemView;
    }

    public <T extends View> T getView(int id){

        View view = views.get(id);

        if (view == null){
            view = this.itemView.findViewById(id);
            views.put(id,view);
        }else {
            //Log.e("holder","wei重用");
        }

        return (T) view;

    }

    public ViewHolderr setText(int id,String str){

        TextView textView = getView(id);
        textView.setText(str);

        return this;
    }

    public ViewHolderr setButton(int id,String str){

        Button button = getView(id);
        button.setText(str);

        return this;
    }

    public ViewHolderr setImageFromNet(int id,String url){
        ImageView imageView = getView(id);
        Glide.with(APP.aContext)
                .load(url)
                .placeholder(R.drawable.ic_wait_24dp)
                .into(imageView);
        return this;
    }

    public Button getButton(int id){
        Button button = getView(id);
        return button;
    }

    public ImageView getImageView(int id){
        ImageView imageView = getView(id);
        return imageView;
    }

}
