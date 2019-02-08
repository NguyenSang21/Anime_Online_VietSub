package com.example.nguyensang.anime_online_official.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nguyensang.anime_online_official.Activity.ChiTietPhimActivity;
import com.example.nguyensang.anime_online_official.Activity.MainActivity;
import com.example.nguyensang.anime_online_official.Adapters.ViewPagerAdapter;
import com.example.nguyensang.anime_online_official.Customclass.GridSpacingItemDecoration;
import com.example.nguyensang.anime_online_official.Customclass.Phim;
import com.example.nguyensang.anime_online_official.Adapters.PhimAdapter;
import com.example.nguyensang.anime_online_official.R;
import com.example.nguyensang.anime_online_official.Customclass.ShimmerText;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.romainpiel.shimmer.ShimmerTextView;
import com.wang.avi.AVLoadingIndicatorView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by NguyenSang on 04/21/2018.
 */

@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment {

    private LinearLayout sliderDotspanel;
    private ImageView[] dots;
    private GestureDetector tapGestureDetector;
    private ArrayList<Phim> arrayListAutoSile;
    private ViewPagerAdapter adapterAutoSlide;
    private ViewPager myViewPager;
    private int dotscount;
    private RecyclerView myRecyclerViewPhimMoiCapNhat, myRecyclerViewPhimTopHanhDong, myRecyclerViewPhimTopShoujo, myRecyclerViewPhimTopTinhCam, myRecyclerViewPhimTopHaiHuoc;
    private ArrayList<RecyclerView> recyclerViewArrayList;
    private ShimmerText setShimmer;
    private ShimmerTextView txtMoiCapNhieu, txtTopHanhDong, txtTopHangNgay, txtTopTinhCam, txtTopHaiHuoc;
    private ArrayList<Phim> phimMoiCapNhat;
    private ArrayList<Phim> phimTopHanhDong;
    private ArrayList<Phim> phimTopShoujo;
    private ArrayList<Phim> phimTopTinhCam;
    private ArrayList<Phim> phimTopHaiHuoc;
    private ArrayList<ArrayList<Phim>> dsPhim;
    private View view;
    private String url = "http://animehay.tv";
    private String urlHanhDong = "http://animehay.tv/the-loai/phim-anime/hanh-dong";
    private String urlShoujo = "http://animehay.tv/the-loai/phim-anime/shoujo";
    private String urlTinhCam = "http://animehay.tv/the-loai/phim-anime/tinh-cam";
    private String urlHaiHuoc = "http://animehay.tv/the-loai/phim-anime/hai-huoc";
    private String urlMoiCapNhat = "http://animehay.tv/the-loai/phim-anime/hoc-duong";
    private RecyclerView.LayoutManager layoutManager = null;
    private PhimAdapter adapterPhim ;
    private AdView adViewHome;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        anhXa();

        //startAnim();
        
        adMob();
        
        initBien();

        setAutoSlidePhim(url);

        getDanhSachFilm(urlHaiHuoc, 4);
        getDanhSachFilm(urlTinhCam, 3);
        getDanhSachFilm(urlShoujo, 2);
        getDanhSachFilm(urlHanhDong, 1);
        getDanhSachFilm(urlMoiCapNhat, 0);

//        if(arrayListAutoSile.size() == 0)
//        {
//            setAutoSlidePhim(url);
//            Log.d("AAAA", "fetch data" );
//        }
//        if(phimMoiCapNhat.size() == 0)
//        {
//            getDanhSachFilm(url,0);
//            Log.d("AAAA", "fetch data" );
//        }
//        if(phimTopShoujo.size() == 0)
//        {
//            getDanhSachFilm(urlShoujo,0);
//            Log.d("AAAA", "fetch data" );
//        }
//        if(phimTopTinhCam.size() == 0)
//        {
//            getDanhSachFilm(urlTinhCam,0);
//            Log.d("AAAA", "fetch data" );
//        }
//        if(phimTopHanhDong.size() == 0)
//        {
//            getDanhSachFilm(urlHanhDong,0);
//            Log.d("AAAA", "fetch data" );
//        }
//        if(phimTopHaiHuoc.size() == 0)
//        {
//            getDanhSachFilm(urlHaiHuoc,0);
//            Log.d("AAAA", "fetch data" );
//        }
        // set hiệu ứng text
        setShimmer = new ShimmerText();
        setShimmer.setSimmerTextView(txtTopHanhDong, getActivity(),2000, false);
        setShimmer.setSimmerTextView(txtMoiCapNhieu, getActivity(),2000,false);
        setShimmer.setSimmerTextView(txtTopHangNgay, getActivity(), 2000, false);
        setShimmer.setSimmerTextView(txtTopTinhCam,getActivity(),2000, false);
        setShimmer.setSimmerTextView(txtTopHaiHuoc,getActivity(),2000, false);
        return view;
    }

    private void adMob() {
        AdRequest adRequest = new AdRequest.Builder().build();
        adViewHome.loadAd(adRequest);
    }

    private void initBien() {
        phimMoiCapNhat = new ArrayList<>();
        phimTopHanhDong = new ArrayList<>();
        phimTopShoujo = new ArrayList<>();
        phimTopTinhCam = new ArrayList<>();
        phimTopHaiHuoc = new ArrayList<>();
        dsPhim = new ArrayList<>();
        dsPhim.add(phimMoiCapNhat);
        dsPhim.add(phimTopHanhDong);
        dsPhim.add(phimTopShoujo);
        dsPhim.add(phimTopTinhCam);
        dsPhim.add(phimTopHaiHuoc);
        recyclerViewArrayList = new ArrayList<>();
        recyclerViewArrayList.add(myRecyclerViewPhimMoiCapNhat);
        recyclerViewArrayList.add(myRecyclerViewPhimTopHanhDong);
        recyclerViewArrayList.add(myRecyclerViewPhimTopShoujo);
        recyclerViewArrayList.add(myRecyclerViewPhimTopTinhCam);
        recyclerViewArrayList.add(myRecyclerViewPhimTopHaiHuoc);

    }

    private void loadPhim(ArrayList<Phim> ds, RecyclerView myRecycler){
        adapterPhim = new PhimAdapter(getActivity(), ds);
        // làm mượt recyclerView
        myRecycler.setHasFixedSize(true);
        // chọn layout Grid
        layoutManager = new GridLayoutManager(getActivity(),3);
        int spanCount = 3; // 3 columns
        int spacing = 10; // 50px
        boolean includeEdge = false;
        myRecycler.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        myRecycler.setLayoutManager(layoutManager);
        myRecycler.setNestedScrollingEnabled(false);
        myRecycler.setAdapter(adapterPhim);
    }

    private void getDanhSachFilm(String url, final int index){
        // LẤY DỮ LIỆU HÌNH ẢNH TỪ SERVER --> DUA VAO VIEWPAGER
        final RequestQueue resq= Volley.newRequestQueue(getActivity());
            StringRequest stringreq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Document document = Jsoup.parse(response);
                    if (document != null) {
                        Elements div = document.getElementsByClass("ah-col-film");
                        if (div != null) {
                            for (int j=0; j<6; j++)
                            {
                                try{
                                    Phim a = new Phim();
                                    a.setLink(div.get(j).getElementsByTag("a").get(0).attr("href"));
                                    a.setHinhAnh(div.get(j).getElementsByTag("a").get(1).getElementsByTag("img").get(0).attr("src").replace("&amp;", "&"));
                                    a.setSoTapHienCo(div.get(j).getElementsByTag("a").get(1).getElementsByTag("span").get(0).text());
                                    a.setTenPhim(div.get(j).getElementsByTag("a").get(1).getElementsByTag("span").get(2).text());
                                    a.setNoiDungPhim(div.get(j).getElementsByTag("a").get(1).getElementsByClass("ah-des-hover").text());
                                    dsPhim.get(index).add(a);
                                }catch (Exception ex){
                                    ex.printStackTrace();
                                }
                            }
                            loadPhim(dsPhim.get(index), recyclerViewArrayList.get(index));
                            adapterPhim.notifyDataSetChanged();
                            //stopAnim();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
                }
            });
            resq.add(stringreq);
    }

    private void setAutoSlidePhim(String link) {
        arrayListAutoSile = new ArrayList<>();
        adapterAutoSlide = new ViewPagerAdapter(getActivity(),arrayListAutoSile);
        myViewPager.setAdapter(adapterAutoSlide);
        // LẤY DỮ LIỆU HÌNH ẢNH TỪ SERVER --> DUA VAO VIEWPAGER
        final RequestQueue resq= Volley.newRequestQueue(getActivity());
        StringRequest stringreq= new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Document document= Jsoup.parse(response);
                if (document!=null) {
                    Elements div = document.getElementsByClass("ah-col-film");
                    if (div != null){
                        for (Element d : div) {
                            Phim a = new Phim();
                            try {
                                a.setLink(d.getElementsByTag("a").get(0).attr("href"));
                                a.setHinhAnh(d.getElementsByTag("a").get(1).getElementsByTag("img").get(0).attr("src").replace("&amp;", "&"));
                                a.setSoTapHienCo(d.getElementsByTag("a").get(1).getElementsByTag("span").get(0).text());
                                a.setTenPhim(d.getElementsByTag("a").get(1).getElementsByTag("span").get(2).text());
                                a.setNoiDungPhim(d.getElementsByTag("a").get(1).getElementsByClass("ah-des-hover").text());
                                arrayListAutoSile.add(a);
                                adapterAutoSlide.notifyDataSetChanged();
                                if (arrayListAutoSile.size() == 4) {
                                    break;
                                }
                            }
                            catch (Exception e)
                            {
                                Log.d("Error", e.getMessage());
                            }

                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
        resq.add(stringreq);

        // CHỈNH DOTS CHO VIEWPAGER
        Timer timer= new Timer();
        if(timer!=null)
        {
            timer.scheduleAtFixedRate(new MyTimerTask(),2000,4000);
        }

        // 4 trang phim được đề cử , Mặc định
        dotscount =4;
        dots = new ImageView[dotscount];
        for (int i=0;i<dotscount;i++){
            dots[i]= new ImageView(getActivity());

            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.active_dot));
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8,0,8,0);
            sliderDotspanel.addView(dots[i],params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.active_dot));
        myViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i=0;i<dotscount;i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.noactive_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tapGestureDetector = new GestureDetector(getActivity(),new TapGestureListener());
        myViewPager.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                tapGestureDetector.onTouchEvent(event);
                return false;
            }
        });
    }

    private class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            FragmentActivity check = getActivity();
            if (check != null)
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(myViewPager.getCurrentItem()==0){
                            myViewPager.setCurrentItem(1);
                        }
                        else if(myViewPager.getCurrentItem()==1){
                            myViewPager.setCurrentItem(2);
                        }
                        else if(myViewPager.getCurrentItem()==2){
                            myViewPager.setCurrentItem(3);
                        }
                        else{
                            myViewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }
    }

    //  BẮT SỰ KIỆN ONCLICK CHO VIEWPAGER
    public class TapGestureListener extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            ArrayList<String> arr = new ArrayList<>();
            String link = "";
            String HinhAnh = "";
            if (myViewPager.getCurrentItem()==0)
            {
                link = arrayListAutoSile.get(myViewPager.getCurrentItem()).getLink();
                HinhAnh = arrayListAutoSile.get(myViewPager.getCurrentItem()).getHinhAnh();
            }
            else if(myViewPager.getCurrentItem()==1) {
                link = arrayListAutoSile.get(myViewPager.getCurrentItem()).getLink();
                HinhAnh = arrayListAutoSile.get(myViewPager.getCurrentItem()).getHinhAnh();
            }
            else if (myViewPager.getCurrentItem()==2){
                link = arrayListAutoSile.get(myViewPager.getCurrentItem()).getLink();
                HinhAnh = arrayListAutoSile.get(myViewPager.getCurrentItem()).getHinhAnh();
            }
            else {
                link = arrayListAutoSile.get(myViewPager.getCurrentItem()).getLink();
                HinhAnh = arrayListAutoSile.get(myViewPager.getCurrentItem()).getHinhAnh();
            }

            Intent intent= new Intent(getActivity(),ChiTietPhimActivity.class);
            arr.add(link);
            arr.add(HinhAnh);
            Bundle args = new Bundle();
            args.putSerializable("ARRAYLIST",arr);
            intent.putExtra("linkPhim", args);
            startActivity(intent);

            return false;
        }
    }

//    void startAnim(){
//        relativeLayoutHome.setVisibility(View.VISIBLE);
//        aviHome.show();
//        // or avi.smoothToShow();
//    }
//
//    void stopAnim(){
//        aviHome.hide();
//        relativeLayoutHome.setVisibility(View.GONE);
//        // or avi.smoothToHide();
//    }

    private void anhXa() {
        myRecyclerViewPhimMoiCapNhat = view.findViewById(R.id.myRecyclerPhimMoiCapNhat);
        myRecyclerViewPhimTopHanhDong = view.findViewById(R.id.myRecyclerPhimTopHanhDong);
        myRecyclerViewPhimTopShoujo = view.findViewById(R.id.myRecyclerPhimTopShoujo);
        myRecyclerViewPhimTopTinhCam = view.findViewById(R.id.myRecyclerPhimTopTinhCam);
        myRecyclerViewPhimTopHaiHuoc = view.findViewById(R.id.myRecyclerPhimTopHaiHuoc);
        txtMoiCapNhieu = view.findViewById(R.id.shimmer_txt_MoiCapNhat);
        txtTopHanhDong = view.findViewById(R.id.shimmer_txt_topHanhDong);
        txtTopHangNgay = view.findViewById(R.id.shimmer_txt_TopHangNgay);
        txtTopHaiHuoc = view.findViewById(R.id.shimmer_txt_topHaiHuoc);
        txtTopTinhCam = view.findViewById(R.id.shimmer_txt_topTinhCam);
        //relativeLayoutHome = view.findViewById(R.id.layoutRelaHome);
        //aviHome = view.findViewById(R.id.aviHome);
        adViewHome = view.findViewById(R.id.adViewHome);
        myViewPager = view.findViewById(R.id.myViewPagerAutoSlide);
        sliderDotspanel = view.findViewById(R.id.SliderDots);
    }
}
