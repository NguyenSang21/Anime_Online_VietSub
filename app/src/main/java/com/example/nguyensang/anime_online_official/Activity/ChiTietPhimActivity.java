package com.example.nguyensang.anime_online_official.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nguyensang.anime_online_official.Customclass.GridSpacingItemDecoration;
import com.example.nguyensang.anime_online_official.Customclass.LinkTapPhim;
import com.example.nguyensang.anime_online_official.R;
import com.example.nguyensang.anime_online_official.Customclass.SQLite;
import com.example.nguyensang.anime_online_official.Adapters.TapPhimAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;
import tcking.github.com.giraffeplayer2.PlayerManager;
import tcking.github.com.giraffeplayer2.VideoView;

public class ChiTietPhimActivity extends AppCompatActivity implements LinkTapPhim {
    private String link = null;
    private ImageView imgHinhChiTiet, imgHinhNenChiTiet;
    private TextView txtTenPhimChiTiet, txtNamPhatHanh ,txtTheLoai ,txtThoiLuong ,txtNoiDung, txtTapMoi;
    private ArrayList<String> links = new ArrayList<>();

    private LinearLayoutManager layoutManagerTapPhim;
    private TapPhimAdapter tapPhimAdapter;
    private RecyclerView recyclerViewTapPhim;
    private String hinhAnh = null;
    private TextView txtProgressLoad;
    private VideoView videoView;
    private WebView webView;
    private MyJavaScriptInterface javaScriptInterface;
    private Toolbar toolbarChitiet;

    private FirebaseUser mUser;
    private DatabaseReference mData;
    private FloatingActionButton fab;
    private boolean check = false;
    private SQLite db;

    private AVLoadingIndicatorView avi;
    private SweetAlertDialog pDialog;

    private LinearLayout layoutContent;
    private String tenPhim = "Đang cập nhật";
    private String imgNen = "";
    private String noiDung = "Nội dung: đang cập nhật";
    private String nam = "null";
    private String thoiLuong = "null";
    private String theLoai = "null";
    private String tapMoi = "null";
    private String linkPhim ="";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_phim);
        anhXa();

        // lấy link
        Intent intent = this.getIntent();
        Bundle args = intent.getBundleExtra("linkPhim");
        ArrayList<String> arraylist = (ArrayList<String>) args.getSerializable("ARRAYLIST");
        //
        link = arraylist.get(0); // link phim
        hinhAnh = arraylist.get(1);
        /// data recent
        db = new SQLite(this, "DataRecent.sqlite", null, 1);
        ///////// lấy thông tin
        getThongTinPhim(link);
        ///////// lấy link
        getLink(link);
        ///////// phần user
        getInfoUser();
        ///////// toolbar
        setSupportActionBar(toolbarChitiet);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("");
        }
        toolbarChitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSupportNavigateUp();
            }
        });
    }

    private void getInfoUser(){
        mUser = FirebaseAuth.getInstance().getCurrentUser() ;
        mData = FirebaseDatabase.getInstance().getReference();

        if (mUser != null) {
            final String id = mUser.getUid();
            mData.child(id).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    final String links = dataSnapshot.getValue().toString().trim();
                    if(link.equals(links)){
                        check = true;
                        fab.setImageResource(R.drawable.redfavorite);
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(ChiTietPhimActivity.this, "Bạn đã thích phim này rồi !", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }
                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }
                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            /// kiểm tra nếu chưa có node đó thì khởi tạo
            if (!check){
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mData.child(id).push().setValue(link, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if(databaseError == null){
                                    Toast.makeText(ChiTietPhimActivity.this, "Thêm Thành công !", Toast.LENGTH_SHORT).show();
                                    fab.setImageResource(R.drawable.redfavorite);
                                }else{
                                    Toast.makeText(ChiTietPhimActivity.this, "Lỗi thêm !", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        check = true;
                    }
                });
            }

        }else{
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ChiTietPhimActivity.this, "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    private void getLink(final String url) {
        final RequestQueue resq= Volley.newRequestQueue(this);
        StringRequest stringreq= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    Document doc= Jsoup.parse(response);
                    linkPhim = doc.getElementsByClass("ah-pif-ftool ah-bg-bd ah-clear-both").get(0).getElementsByTag("a").get(0).attr("href");
                    getTapPhim(linkPhim);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChiTietPhimActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
        resq.add(stringreq);
    }

    private void getThongTinPhim(final String url) {
        final RequestQueue resq= Volley.newRequestQueue(this);
        StringRequest stringreq= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    Document doc= Jsoup.parse(response);

                    tenPhim = doc.getElementsByClass("ah-pif-fname").get(0).text();

                    imgNen = doc.getElementsByClass("ah-pif-fcover ah-bg-bd").get(0).getElementsByTag("img").attr("src").replace("&amp;", "&");

                    noiDung = doc.getElementsByTag("p").get(0).text();

                    nam = doc.getElementsByClass("ah-pif-body ah-clear-both").get(0).getElementsByTag("li").get(1).text();

                    thoiLuong = doc.getElementsByClass("ah-pif-body ah-clear-both").get(0).getElementsByTag("li").get(3).text();

                    theLoai = doc.getElementsByClass("ah-pif-body ah-clear-both").get(0).getElementsByTag("li").get(2).text();

                    tapMoi = doc.getElementsByClass("ah-pif-body ah-clear-both").get(0).getElementsByTag("li").get(0).text();

                    setThongTinPhim(hinhAnh, imgNen, noiDung, nam, thoiLuong, tenPhim, theLoai, tapMoi);
                    DateFormat df = new SimpleDateFormat("HH:mm a,dd-MM-yyyy");
                    String date = df.format(Calendar.getInstance().getTime());
                    db.Insert(tenPhim, url, date, hinhAnh);
                } catch (Exception e) {
                    e.printStackTrace();
                    setThongTinPhim(hinhAnh, imgNen, noiDung, nam, thoiLuong, tenPhim, theLoai, tapMoi);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChiTietPhimActivity.this, tenPhim, Toast.LENGTH_SHORT).show();
            }
        });
        resq.add(stringreq);
    }

    private  void setThongTinPhimError()
    {
        Picasso.get()
                .load(R.drawable.imagenotfound)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .fit()
                .centerCrop()
                .into(imgHinhChiTiet);
        Picasso.get()
                .load(R.drawable.bgnotfound)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .fit()
                .centerCrop()
                .into(imgHinhNenChiTiet);
        txtNoiDung.setText("Nội Dung: đang cập nhật");
        txtNamPhatHanh.setText("null");
        txtThoiLuong.setText("null");
        txtTenPhimChiTiet.setText("Đang cập nhật");
        txtTheLoai.setText("null");
        txtTapMoi.setText("null");
    }


    private void setThongTinPhim(String hinhAnh, String hinhNen, String noiDung ,String namPhatHanh ,String thoiLuong ,String tenPhim ,String theLoai, String tapMoi) {
        // lấy hình
        Picasso.get()
                .load(hinhAnh)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .fit()
                .centerCrop()
                .into(imgHinhChiTiet);

        // lấy hình nền
        Picasso.get()
                .load(hinhNen)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .fit()
                .centerCrop()
                .into(imgHinhNenChiTiet);

        // lấy nội dung
        txtNoiDung.setText(noiDung);
        txtNamPhatHanh.setText(namPhatHanh);
        txtThoiLuong.setText(thoiLuong);
        txtTenPhimChiTiet.setText(tenPhim);
        txtTheLoai.setText(theLoai);
        txtTapMoi.setText(tapMoi);
        // lấy thông tin xong ẩn
        layoutContent.setVisibility(View.VISIBLE);

    }

    private void getTapPhim(final String url){
        final RequestQueue resq= Volley.newRequestQueue(this);
        StringRequest stringreq= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    Document doc= Jsoup.parse(response);
                    Elements div = doc.getElementsByClass("ah-wf-le ah-bg-bd");
                    final Elements lies = div.get(0).getElementsByTag("a");
                    for (int i = 0; i < lies.size(); i++) {
                        String link = "";
                        link = lies.get(i).attr("href");
                        if(link != "") {
                            links.add(link);
                        }
                    }

                    //
                    if (links.size() > 0){
                        //int tapCuoiCung = links.size()-1;
                        String linkPlayTapDau = links.get(0);
                        initWebview(linkPlayTapDau);
                        tapPhimAdapter = new TapPhimAdapter(ChiTietPhimActivity.this, links, 0);
                        layoutManagerTapPhim = new LinearLayoutManager(ChiTietPhimActivity.this,LinearLayoutManager.HORIZONTAL,false);
                        layoutManagerTapPhim.setAutoMeasureEnabled(true);
                        recyclerViewTapPhim.setLayoutManager(layoutManagerTapPhim);
                        recyclerViewTapPhim.scrollToPosition(0);
                        recyclerViewTapPhim.setHasFixedSize(true);
                        recyclerViewTapPhim.setNestedScrollingEnabled(true);
                        recyclerViewTapPhim.setAdapter(tapPhimAdapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChiTietPhimActivity.this, "Chưa cập nhật tập phim !", Toast.LENGTH_SHORT).show();
                txtProgressLoad.setVisibility(View.VISIBLE);
                videoView.setVisibility(View.GONE);
            }
        });
        resq.add(stringreq);
    }


    private void anhXa() {
        imgHinhChiTiet = findViewById(R.id.imgHinhChiTiet);
        imgHinhNenChiTiet = findViewById(R.id.imgHinhNenChiTiet);
        txtTenPhimChiTiet = findViewById(R.id.txtTenPhimChiTiet);
        txtNamPhatHanh = findViewById(R.id.txtNamPhatHanh);
        txtTheLoai = findViewById(R.id.txtTheLoaiChiTiet);
        txtThoiLuong = findViewById(R.id.txtThoiLuongChiTiet);
        txtNoiDung = findViewById(R.id.txtNoiDungChiTiet);
        txtTheLoai = findViewById(R.id.txtTheLoaiChiTiet);
        txtTapMoi = findViewById(R.id.txtTapMoiChiTiet);
        recyclerViewTapPhim = findViewById(R.id.myRecyclerShowTapPhim);
        fab = findViewById(R.id.btnFloat);
        txtProgressLoad = findViewById(R.id.txtTitleProcessLoad);
        webView = findViewById(R.id.webViewTapPhimChiTiet);
        toolbarChitiet = findViewById(R.id.toolbarShowChitiet);
        videoView = findViewById(R.id.video_viewChiTiet);
        layoutContent = findViewById(R.id.layoutContent);
        avi = findViewById(R.id.progressIndicator);
    }

    @Override
    public void linkTapPhim(String link) {
        avi.show();
        initWebview(link);
    }

    private void initWebview(String url) {
        javaScriptInterface = new MyJavaScriptInterface();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(javaScriptInterface, "HTMLOUT");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

                setJavascript();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });
        webView.loadUrl(url);
    }

    class MyJavaScriptInterface {
        @JavascriptInterface
        public void getLinkVideo(String jsResult) {
            Log.d("LINK_VIDEO", jsResult);
            playVideo(jsResult);
        }
    }

    private void playVideo(final String url){
        //standalone player
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(videoView != null){
                    videoView.setVisibility(View.VISIBLE);
                    if (videoView.isCurrentActivePlayer()) {
                        //videoView.getPlayer().stop();
                        videoView.getPlayer().release();
                    }
                    avi.hide();
                    videoView.getVideoInfo().setUri(Uri.parse(url));
                    videoView.getPlayer().start();
                }
            }
        });

    }

    private void setJavascript() {
        webView.loadUrl("javascript: window.HTMLOUT.getLinkVideo(infoLoad.link_main);");
    }


    protected void onPause() {

        super.onPause();
    }


    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    @Override
    public void onBackPressed() {
        if (PlayerManager.getInstance().onBackPressed()) {
            return;
        }
        if(videoView != null)
        {
            if (videoView.isCurrentActivePlayer()) {
                videoView.getPlayer().pause();
                videoView.getPlayer().stop();
                videoView.getPlayer().release();
            }
        }
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void showDialog(){
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Đang tải chờ xí nhé !!");
        pDialog.setCancelable(false);
        pDialog.show();
    }
}
