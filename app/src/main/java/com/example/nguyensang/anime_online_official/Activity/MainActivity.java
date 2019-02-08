package com.example.nguyensang.anime_online_official.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.nguyensang.anime_online_official.Customclass.BottomNavigationViewBehavior;
import com.example.nguyensang.anime_online_official.Customclass.BottomNavigationViewHelper;
import com.example.nguyensang.anime_online_official.Fragments.HomeFragment;
import com.example.nguyensang.anime_online_official.Fragments.MoreFragment;
import com.example.nguyensang.anime_online_official.Customclass.Phim;
import com.example.nguyensang.anime_online_official.R;
import com.example.nguyensang.anime_online_official.Fragments.RootFragment;
import com.example.nguyensang.anime_online_official.Fragments.SearchFragment;
import com.example.nguyensang.anime_online_official.Fragments.TheLoaiFragment;
import com.example.nguyensang.anime_online_official.Adapters.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class MainActivity extends AppCompatActivity {

    private ArrayList<Phim> arrayListAutoSile;
    private ViewPagerAdapter adapterAutoSlide;
    private ViewPager myViewPager;
    private int dotscount;
    private BottomNavigationView bottomNav;
    private LinearLayout sliderDotspanel;
    private ImageView[] dots;
    private GestureDetector tapGestureDetector;
    private String url = "http://animehay.tv/";

    private HomeFragment homeFragment;
    private TheLoaiFragment theLoaiFragment;
    private SearchFragment searchFragment;
    private MoreFragment moreFragment;
    private RootFragment userFragment; // màn hình đăng nhập


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhXa();
        if(checkInternet()){
            //setAutoSlidePhim(url);

            // chạy navigation bottom

            bottomNav.setOnNavigationItemSelectedListener(navListener);
            bottomNav.setOnNavigationItemSelectedListener(navListener);
            //
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNav.getLayoutParams();
            layoutParams.setBehavior(new BottomNavigationViewBehavior());

            InitFragment();

            // chạy fragment home
            getSupportFragmentManager().beginTransaction().show(homeFragment).commit();
            //
        }

    }


    private void anhXa() {
        //myViewPager = findViewById(R.id.myViewPagerAutoSlide);
        //sliderDotspanel = findViewById(R.id.SliderDots);
        bottomNav = findViewById(R.id.bottom_nav);
        BottomNavigationViewHelper.disableShiftMode(bottomNav);
    }

    private boolean checkInternet(){
        boolean checkInternet= isNetworkAvailable(this);
        if (checkInternet==false){
            ShowDialog();
            return false;
        }else {
            return true;
        }
    }

    public boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null;
    }

    private void ShowDialog(){
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        pDialog.setTitleText("Internet ?")
                .setContentText("Vui lòng kiểm tra lại Internet!")
                .setCancelText("Thoát")
                .setConfirmText("Tải Lại")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        finishAffinity();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        finish();
                        startActivity(getIntent());
                    }
                })
                .setCancelable(false);
        pDialog.show();
    }

    private void InitFragment() {
        homeFragment = new HomeFragment();
        theLoaiFragment = new TheLoaiFragment();
        searchFragment = new SearchFragment();
        moreFragment = new MoreFragment();
        userFragment = new RootFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, homeFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, theLoaiFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, searchFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, userFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, moreFragment).commit();

        getSupportFragmentManager().beginTransaction().hide(homeFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(theLoaiFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(searchFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(userFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(moreFragment).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.navHome:
                            getSupportFragmentManager().beginTransaction().show(homeFragment).commit();
                            getSupportFragmentManager().beginTransaction().hide(theLoaiFragment).commit();
                            getSupportFragmentManager().beginTransaction().hide(searchFragment).commit();
                            getSupportFragmentManager().beginTransaction().hide(userFragment).commit();
                            getSupportFragmentManager().beginTransaction().hide(moreFragment).commit();
                            break;
                        case R.id.navTheLoai:
                            getSupportFragmentManager().beginTransaction().show(theLoaiFragment).commit();
                            getSupportFragmentManager().beginTransaction().hide(homeFragment).commit();
                            getSupportFragmentManager().beginTransaction().hide(searchFragment).commit();
                            getSupportFragmentManager().beginTransaction().hide(userFragment).commit();
                            getSupportFragmentManager().beginTransaction().hide(moreFragment).commit();
                            break;
                        case R.id.navSearch:

                            getSupportFragmentManager().beginTransaction().show(searchFragment).commit();
                            getSupportFragmentManager().beginTransaction().hide(homeFragment).commit();
                            getSupportFragmentManager().beginTransaction().hide(userFragment).commit();
                            getSupportFragmentManager().beginTransaction().hide(moreFragment).commit();
                            getSupportFragmentManager().beginTransaction().hide(theLoaiFragment).commit();
                            break;
                        case R.id.navUser:
                            getSupportFragmentManager().beginTransaction().show(userFragment).commit();
                            getSupportFragmentManager().beginTransaction().hide(searchFragment).commit();
                            getSupportFragmentManager().beginTransaction().hide(homeFragment).commit();
                            getSupportFragmentManager().beginTransaction().hide(moreFragment).commit();
                            getSupportFragmentManager().beginTransaction().hide(theLoaiFragment).commit();
                            break;
                        case R.id.navMore:

                            getSupportFragmentManager().beginTransaction().show(moreFragment).commit();
                            getSupportFragmentManager().beginTransaction().hide(userFragment).commit();
                            getSupportFragmentManager().beginTransaction().hide(searchFragment).commit();
                            getSupportFragmentManager().beginTransaction().hide(homeFragment).commit();
                            getSupportFragmentManager().beginTransaction().hide(theLoaiFragment).commit();
                            break;
                    }
                    return true;
                }
            };

//    private void setAutoSlidePhim(String link) {
//        arrayListAutoSile = new ArrayList<>();
//        adapterAutoSlide = new ViewPagerAdapter(MainActivity.this,arrayListAutoSile);
//        myViewPager.setAdapter(adapterAutoSlide);
//        // LẤY DỮ LIỆU HÌNH ẢNH TỪ SERVER --> DUA VAO VIEWPAGER
//        final RequestQueue resq= Volley.newRequestQueue(MainActivity.this);
//        StringRequest stringreq= new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Document document= Jsoup.parse(response);
//                if (document!=null) {
//                    Elements div = document.getElementsByClass("ah-col-film");
//                    if (div != null){
//                        for (Element d : div) {
//                            Phim a = new Phim();
//                            a.setLink(d.getElementsByTag("a").get(0).attr("href"));
//                            a.setHinhAnh(d.getElementsByTag("a").get(1).getElementsByTag("img").get(0).attr("src").replace("&amp;", "&"));
//                            a.setSoTapHienCo(d.getElementsByTag("a").get(1).getElementsByTag("span").get(0).text());
//                            a.setTenPhim(d.getElementsByTag("a").get(1).getElementsByTag("span").get(1).text());
//                            a.setNoiDungPhim(d.getElementsByTag("a").get(1).getElementsByClass("ah-des-hover").text());
//                            arrayListAutoSile.add(a);
//                            adapterAutoSlide.notifyDataSetChanged();
//                            if(arrayListAutoSile.size() == 4)
//                            {
//                                break;
//                            }
//
//                        }
//                    }
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
//            }
//        });
//        resq.add(stringreq);
//
//        // CHỈNH DOTS CHO VIEWPAGER
//        Timer timer= new Timer();
//        if(timer!=null)
//        {
//            timer.scheduleAtFixedRate(new MyTimerTask(),2000,4000);
//        }
//
//        // 4 trang phim được đề cử , Mặc định
//        dotscount =4;
//        dots = new ImageView[dotscount];
//        for (int i=0;i<dotscount;i++){
//            dots[i]= new ImageView(MainActivity.this);
//
//            dots[i].setImageDrawable(ContextCompat.getDrawable(MainActivity.this,R.drawable.active_dot));
//            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//
//            params.setMargins(8,0,8,0);
//            sliderDotspanel.addView(dots[i],params);
//        }
//        dots[0].setImageDrawable(ContextCompat.getDrawable(MainActivity.this,R.drawable.active_dot));
//        myViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                for (int i=0;i<dotscount;i++){
//                    dots[i].setImageDrawable(ContextCompat.getDrawable(MainActivity.this,R.drawable.noactive_dot));
//                }
//                dots[position].setImageDrawable(ContextCompat.getDrawable(MainActivity.this,R.drawable.active_dot));
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//        tapGestureDetector = new GestureDetector(MainActivity.this,new TapGestureListener());
//        myViewPager.setOnTouchListener(new View.OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//                tapGestureDetector.onTouchEvent(event);
//                return false;
//            }
//        });
//    }

    private class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
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

            Intent intent= new Intent(MainActivity.this,ChiTietPhimActivity.class);
            arr.add(link);
            arr.add(HinhAnh);
            Bundle args = new Bundle();
            args.putSerializable("ARRAYLIST",arr);
            intent.putExtra("linkPhim", args);
            startActivity(intent);

            return false;
        }
    }
    // thoát ứng dụng
    boolean doubleBackToExitPressedOnce = false;
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Nhấn back một lần nữa để thoát", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }



}
