package com.example.nguyensang.anime_online_official.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nguyensang.anime_online_official.Customclass.GridSpacingItemDecoration;
import com.example.nguyensang.anime_online_official.Customclass.Phim;
import com.example.nguyensang.anime_online_official.Adapters.PhimAdapter;
import com.example.nguyensang.anime_online_official.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Random;

public class ShowTheLoaiActivity extends AppCompatActivity {

    private RecyclerView myRecyclerViewShowTheLoai;
    private Toolbar toolbarShowTheLoai;
    private GridLayoutManager layoutManager;
    private ArrayList<Phim> arrayListPhim;
    private PhimAdapter adapterPhim;
    private int indexPage = 1;
    private int soTrangCurrent = 1;
    private String linkPhim ;
    private String tenTheLoai;
    private String linkPhimCurrent;
    private boolean kt = false;

    private int spanCount = 3; // 3 columns
    private int spacing = 10; // 50px

    private  ArrayList<Integer> arrHinhLayOut;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_the_loai);
        anhXa();
        // chuyễn màn hình truyền 2 tham số llink và tên
        Intent intent = this.getIntent();
        Bundle args = intent.getBundleExtra("linkTheLoai");
        ArrayList<String> arraylist = (ArrayList<String>) args.getSerializable("ARRAYLIST");
        // lấy link
        linkPhim = arraylist.get(0)+"?page=1";
        Log.d("LINKPHIM", linkPhim);
        linkPhimCurrent = linkPhim;

        // lấy tên
        tenTheLoai = arraylist.get(1);
        toolbarShowTheLoai.setTitle(tenTheLoai);
        //collapsingToolbarShowTheLoai.setTitle(tenTheLoai);
        //circleProgressBar.setColorSchemeResources(R.color.blue);
        // set background cho collapsinglayout
        setRanDomLayOut();
        loadPhim();

        // toolbar
        setSupportActionBar(toolbarShowTheLoai);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbarShowTheLoai.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSupportNavigateUp();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }

    private void setRanDomLayOut(){
        arrHinhLayOut = new ArrayList<>();
        arrHinhLayOut.add(R.drawable.img_bg_tl_1);
        arrHinhLayOut.add(R.drawable.img_bg_tl_6);
        arrHinhLayOut.add(R.drawable.img_bg_tl_14);
        arrHinhLayOut.add(R.drawable.img_bg_tl_15);
        arrHinhLayOut.add(R.drawable.img_bg_tl_4);
        random = new Random();
        int  n = random.nextInt(4) + 0;
        //imgBackGround.setBackgroundResource(arrHinhLayOut.get(n));

    }

    private void loadPhim() {
        arrayListPhim = new ArrayList<>();
        adapterPhim = new PhimAdapter(this, arrayListPhim);
        // làm mượt recyclerView
        myRecyclerViewShowTheLoai.setHasFixedSize(true);
        myRecyclerViewShowTheLoai.setNestedScrollingEnabled(false);
        //chọn layout Grid
        //layoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        layoutManager = new GridLayoutManager(this, 3);
//        layoutManager.setAutoMeasureEnabled(true);
//
        boolean includeEdge = false;
        myRecyclerViewShowTheLoai.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        // set layout
        myRecyclerViewShowTheLoai.setLayoutManager(layoutManager);
        myRecyclerViewShowTheLoai.setAdapter(adapterPhim);
        ///////////

        // load trang đầu tiên
        getDuLieu(linkPhim);
        // lấy số trang
        final RequestQueue resq= Volley.newRequestQueue(this);
        StringRequest stringreq = new StringRequest(Request.Method.GET, linkPhim, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Document doc = Jsoup.parse(response);
                    Elements ul = doc.getElementsByClass("last");
                    int tongSoTrang = Integer.parseInt(ul.get(0).text());
                    loadMore(tongSoTrang);
                    // load thêm dữ liệu
//                    soTrangCurrent++;
//                    while (soTrangCurrent <= soTrang)
//                    {
//                        int soTrangNext = soTrangCurrent + 1;
//                        linkPhimCurrent = linkPhimCurrent.replace("?page="+soTrangCurrent + "", "?page="+soTrangNext + "");
//                        getDuLieu(linkPhimCurrent);
//                        soTrangCurrent++;
//                    }
                    //myRecyclerViewShowTheLoai.scrollToPosition(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ShowTheLoaiActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
        resq.add(stringreq);
    }

    private  void loadMore(final int tongSoTrang){
        myRecyclerViewShowTheLoai.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) //check for scroll down
                {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
                    Log.d("QQQQ :=",visibleItemCount+pastVisiblesItems+" SIZE="+totalItemCount);
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        Log.d("ZZZ","OK OK OK nek");
                        indexPage++;
                        if (indexPage <= tongSoTrang) {
                            linkPhimCurrent = linkPhimCurrent.replace("?page=" + soTrangCurrent + "", "?page=" + indexPage + "");
                            soTrangCurrent = indexPage;
                            getDuLieu(linkPhimCurrent);
                        } else {
                            //circleProgressBar.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });
    }

    private void getDuLieu(String url){
        final RequestQueue resq= Volley.newRequestQueue(this);
        StringRequest stringreq= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    Document doc= Jsoup.parse(response);
                    Elements div = doc.getElementsByClass("ah-col-film");
                    for (Element d : div) {
                        Phim a = new Phim();
                        a.setLink(d.getElementsByTag("a").get(0).attr("href"));
                        a.setHinhAnh(d.getElementsByTag("a").get(1).getElementsByTag("img").get(0).attr("src").replace("&amp;", "&"));
                        a.setSoTapHienCo(d.getElementsByTag("a").get(1).getElementsByTag("span").get(0).text());
                        a.setTenPhim(d.getElementsByTag("a").get(1).getElementsByTag("span").get(2).text());
                        a.setNoiDungPhim(d.getElementsByTag("a").get(1).getElementsByClass("ah-des-hover").text());
                        arrayListPhim.add(a);
                        adapterPhim.notifyDataSetChanged();
                        //adapterPhim.notifyItemInserted(arrayListPhim.size() - 1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ShowTheLoaiActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
        resq.add(stringreq);
    }

    private void anhXa() {
        myRecyclerViewShowTheLoai = findViewById(R.id.myRecyclerViewShowTheLoai);
        toolbarShowTheLoai = findViewById(R.id.toolbarShowTheLoai);
        //collapsingToolbarShowTheLoai = findViewById(R.id.collapsingToolbarShowTheLoai);
        //myNest = findViewById(R.id.myNestShowTheLoai);
        //circleProgressBar = findViewById(R.id.progress_circle);
        //imgBackGround = findViewById(R.id.imgCollapShowTheLoai);
    }
}
