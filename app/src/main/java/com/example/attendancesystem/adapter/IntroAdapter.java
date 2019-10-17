package com.example.attendancesystem.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



import com.example.attendancesystem.R;
import com.example.attendancesystem.model.IntroContent;

import java.util.List;


public class IntroAdapter extends PagerAdapter {

  private   Context context;
  private   List<IntroContent> intro_content_list;

    public IntroAdapter(Context context, List<IntroContent> intro_content_list) {
        this.context = context;
        this.intro_content_list = intro_content_list;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.intro_content_layout, null);
        ImageView intro_imageview = view.findViewById(R.id.intro_imageView_id);
        TextView intro_title_textview = view.findViewById(R.id.intro_title_textview_id);
        TextView intro_details_textview = view.findViewById(R.id.intro_details_textview_id);
        intro_imageview.setImageResource(intro_content_list.get(position).getIntro_image());
        intro_title_textview.setText(intro_content_list.get(position).getIntro_title());
        intro_details_textview.setText(intro_content_list.get(position).getIntro_details());
        container.addView(view);
        return view;

    }

    @Override
    public int getCount() {
        return intro_content_list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
