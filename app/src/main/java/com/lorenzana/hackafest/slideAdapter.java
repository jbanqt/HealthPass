package com.lorenzana.hackafest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class slideAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public slideAdapter(Context context){
        this.context = context;
    }

    //array for the images
    public int[] slide_images = {
            R.drawable.img1, R.drawable.img2, R.drawable.logo
    };

    //array for the headings
    public String[] slide_heads = {
            "Keep Safe",
            "Easy Contact Tracing", "Privacy Protected"
    };

    //array for the desc
    public String[] slide_descs = {
            "Health Pass will keep you safe from touching objects that might carry virus",
            "Health Pass is hassle-free",
            "Generate your information with privacy"
    };

    @Override
    public int getCount() {

        return slide_heads.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout) o;
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImg = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideHead = (TextView) view.findViewById(R.id.slide_head);
        TextView slideDesc = (TextView) view.findViewById(R.id.slide_desc);

        slideImg.setImageResource(slide_images[position]);
        slideHead.setText(slide_heads[position]);
        slideDesc.setText(slide_descs[position]);

        container.addView(view);

        return view;
    }

    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((RelativeLayout)object);
    }
}

