package com.example.nguyensang.anime_online_official.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nguyensang.anime_online_official.Customclass.Phim;
import com.example.nguyensang.anime_online_official.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by NguyenSang on 04/22/2018.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Phim> phimList;

    public ViewPagerAdapter(Context context, List<Phim> phimList) {
        this.context = context;
        this.phimList = phimList;
    }

    @Override
    public int getCount() {
        return phimList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.custom_layout_viewpager,null);
        ImageView imageView = view.findViewById(R.id.imageViewPager);
        TextView txtView = view.findViewById(R.id.txtTenPhimVP);
        txtView.setText(phimList.get(position).getTenPhim());
        Picasso.get()
                .load(phimList.get(position).getHinhAnh())
                .fit()
                .centerCrop()
                .into(imageView);
        ViewPager vp= (ViewPager) container;
        vp.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp= (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
