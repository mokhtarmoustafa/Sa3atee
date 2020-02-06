package com.saaty;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class CustomPagerAdapter extends PagerAdapter {
    Activity activity;
    List<Integer> images;
    List<String> texts;

    public CustomPagerAdapter(Activity activity, List<Integer> images,List<String> texts) {
        this.activity = activity;
        this.images=images;
        this.texts=texts;
    }

    @Override
    public int getCount() {
        return images.size();
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = ((Activity)activity).getLayoutInflater();
        View viewItem = inflater.inflate(R.layout.xyz_layout, container, false);
      Integer img= images.get(position);
      String text=texts.get(position);


        ImageView imageView = (ImageView) viewItem.findViewById(R.id.image_id);
        imageView.setImageResource(img);

        TextView textView1 = (TextView) viewItem.findViewById(R.id.text_msg);
        textView1.setVisibility(View.VISIBLE);
        textView1.setText(text);
        ((ViewPager)container).addView(viewItem);

        return viewItem;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        // TODO Auto-generated method stub
        return view == ((View)object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        ((ViewPager) container).removeView((View) object);
    }
}
