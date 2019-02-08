package com.example.nguyensang.anime_online_official.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by NguyenSang on 04/21/2018.
 */

public class SearchFragment extends Fragment {

    private EditText edtNoiDungSearch;
    private Button btnSearch;
    private View view;
    private TextView txtThongBao;

    private RecyclerView recyclerViewSearch;
    private PhimAdapter adapterSearch;
    private RecyclerView.LayoutManager layoutManagerSearch;
    private ArrayList<Phim> arrayListSearch;

    private String link = "http://animehay.tv/tim-kiem?q=";

    private int spanCount = 3; // 3 columns
    private int spacing = 10; // 50px
    private boolean includeEdge = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        anhXa();
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchNoiDung = edtNoiDungSearch.getText().toString().trim();
                searchNoiDung = searchNoiDung.replace(" ","+");
                String linkSearch =  link + searchNoiDung;

                getDataSearch(linkSearch);

                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            }
        });

        return view;
    }

    private void getDataSearch(String url) {
        arrayListSearch = new ArrayList<>();
        adapterSearch = new PhimAdapter(getActivity(),arrayListSearch);
        layoutManagerSearch = new GridLayoutManager(getActivity(),3);

        recyclerViewSearch.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        recyclerViewSearch.setLayoutManager(layoutManagerSearch);
        recyclerViewSearch.setNestedScrollingEnabled(false);
        recyclerViewSearch.setAdapter(adapterSearch);

        final RequestQueue resq= Volley.newRequestQueue(getActivity());
        StringRequest stringreq= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    Document doc= Jsoup.parse(response);
                    Elements div = doc.getElementsByClass("ah-col-film");
                    for(Element e :div){
                        Phim a = new Phim();
                        a.setLink(e.getElementsByTag("a").get(0).attr("href"));
                        a.setHinhAnh(e.getElementsByTag("a").get(1).getElementsByTag("img").get(0).attr("src").replace("&amp;", "&"));
                        a.setSoTapHienCo(e.getElementsByTag("a").get(1).getElementsByTag("span").get(0).text());
                        a.setTenPhim(e.getElementsByTag("a").get(1).getElementsByTag("span").get(1).text());
                        a.setNoiDungPhim(e.getElementsByTag("a").get(1).getElementsByClass("ah-des-hover").text());
                        arrayListSearch.add(a);
                    }
                    if (arrayListSearch.size() == 0){
                        txtThongBao.setVisibility(View.VISIBLE);
                    }else{
                        txtThongBao.setVisibility(View.GONE);
                    }
                    adapterSearch.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
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

    private void anhXa() {
        edtNoiDungSearch = view.findViewById(R.id.edtTimKim);
        btnSearch = view.findViewById(R.id.btnTimKiem);
        recyclerViewSearch =view.findViewById(R.id.myRecyclerSearch);
        txtThongBao = view.findViewById(R.id.txtAlertTimKiem);
    }
}
