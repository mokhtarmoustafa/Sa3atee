package com.saaty.productDetails;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.saaty.R;
import com.saaty.models.ProductimagesItem;
import com.saaty.util.urls;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ProductImagesSliderDialogAdapter extends PagerAdapter {
    Activity activity;
    List<ProductimagesItem> images;


    public ProductImagesSliderDialogAdapter(Activity activity, List<ProductimagesItem> images) {
        this.activity = activity;
        this.images=images;

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
        ProductimagesItem item= images.get(position);



        ImageView imageView = (ImageView) viewItem.findViewById(R.id.image_id);
        Picasso.with(activity).load(urls.base_url+"/"+item.getImageLink()).into(imageView);
        //imageView.setImageResource(img);


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


