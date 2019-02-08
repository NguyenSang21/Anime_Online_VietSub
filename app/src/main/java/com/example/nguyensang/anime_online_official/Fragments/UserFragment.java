package com.example.nguyensang.anime_online_official.Fragments;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.example.nguyensang.anime_online_official.Customclass.GridSpacingItemDecoration;
import com.example.nguyensang.anime_online_official.Customclass.Phim;
import com.example.nguyensang.anime_online_official.Adapters.PhimAdapter;
import com.example.nguyensang.anime_online_official.R;
import com.example.nguyensang.anime_online_official.Adapters.RecentPhimAdapter;
import com.example.nguyensang.anime_online_official.Customclass.SQLite;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by NguyenSang on 04/21/2018.
 */

@SuppressLint("ValidFragment")
public class UserFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private View view;
    private CircleImageView imgUser;
    private TextView txtUserName, txtUserEmail, txtEmptyRecent, txtEmptyFavourite;
    private ImageButton btnLogOut, btnDropRecent, btnDisAction;
    private DatabaseReference mData;
    private RecyclerView mRecyclerViewFavourite, mRecyclerViewRecent;
    private ArrayList<Phim> arrayListFavourite, arrayListRecent;
    private PhimAdapter adapterFavourite;
    private RecentPhimAdapter recentPhimAdapter;
    private SQLite db;
    private LinearLayoutManager mLayoutFavourite, mLayoutRecent ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);
        anhXa();
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        mData= FirebaseDatabase.getInstance().getReference();
        //////////data

        db= new SQLite(getActivity(),"DataRecent.sqlite",null,1);
        db.QueryData("CREATE TABLE IF NOT EXISTS DuLieu(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenPhim VARCHAR ,LinkPhim VARCHAR , NgayGio VARCHAR, LinkAnh VARCHAR)");
        //////////
        getUserDetails();
        setPhimFavourite();
        setTruyenRecent();
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialog();
            }
        });
        return view;
    }

    private void getUserDetails() {
        if (user != null) {
            String name= user.getDisplayName();
            Uri urlUserPhoto=user.getPhotoUrl();
            String userEmail=user.getEmail();
            if (name != null){
                txtUserName.setText(name);
            }
            if(urlUserPhoto != null){
                Picasso.get().load(urlUserPhoto).into(imgUser);
            }
            if (userEmail != null){
                txtUserEmail.setText(userEmail);
            }
        }
    }

    private void setTruyenRecent() {
        /////// kết nối data
        mLayoutRecent = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mRecyclerViewRecent.setLayoutManager(mLayoutRecent);
        mRecyclerViewRecent.setHasFixedSize(true);
        mRecyclerViewRecent.setNestedScrollingEnabled(false);
        arrayListRecent = new ArrayList<>();
        recentPhimAdapter = new RecentPhimAdapter(getActivity(),arrayListRecent);
        mRecyclerViewRecent.setAdapter(recentPhimAdapter);
        if(db != null){
            Cursor duLieu=db.GetData("SELECT *FROM DuLieu");
            for (duLieu.moveToLast();!duLieu.isBeforeFirst();duLieu.moveToPrevious()){
                Phim phim = new Phim();
                phim.setTenPhim(duLieu.getString(1));
                phim.setLink(duLieu.getString(2));
                phim.setNamPhatHanh(duLieu.getString(3));
                phim.setHinhAnh(duLieu.getString(4));
                arrayListRecent.add(phim);
                if (arrayListRecent.size() == 15){
                    break;
                }else if (arrayListRecent.size() == 500){
                    getContext().deleteDatabase("DataRecent.sqlite");
                }
                recentPhimAdapter.notifyDataSetChanged();
            }
            if(recentPhimAdapter.getItemCount()==0){
                txtEmptyRecent.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setPhimFavourite() {
        mLayoutFavourite = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        mRecyclerViewFavourite.setLayoutManager(mLayoutFavourite);
        mRecyclerViewFavourite.addOnScrollListener(new CenterScrollListener());
        mRecyclerViewFavourite.setHasFixedSize(true);
//        int spanCount = 3; // 3 columns
//        int spacing = 10; // 50px
//        boolean includeEdge = false;
//        mRecyclerViewFavourite.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        arrayListFavourite = new ArrayList<>();
        adapterFavourite= new PhimAdapter(getContext(), arrayListFavourite);
        mRecyclerViewFavourite.setAdapter(adapterFavourite);
        mRecyclerViewFavourite.setNestedScrollingEnabled(false);
        if (user != null){
            String id= user.getUid();

            Toast.makeText(getActivity(), "Có Tk", Toast.LENGTH_SHORT).show();

            mData.child(id).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    String link = dataSnapshot.getValue().toString().trim();
                    //Toast.makeText(getActivity(), link, Toast.LENGTH_SHORT).show();
                    getDataUser(link);


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
        }

    }

    private void getDataUser(final String url){
        final RequestQueue resq= Volley.newRequestQueue(getActivity());
        StringRequest stringreq= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    Document doc= Jsoup.parse(response);
                    Phim phim = new Phim();
                    String tenPhim = doc.getElementsByClass("ah-pif-fname").get(0).text();
                    String imgNen = doc.getElementsByClass("ah-pif-fcover ah-bg-bd").get(0).getElementsByTag("img").attr("src").replace("&amp;", "&");
                    phim.setHinhAnh(imgNen);
                    phim.setTenPhim(tenPhim);
                    phim.setLink(url);
                    arrayListFavourite.add(phim);
                    adapterFavourite.notifyDataSetChanged();
                    if (adapterFavourite.getItemCount()!=0){
                        txtEmptyFavourite.setVisibility(View.GONE);
                    }
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

    private void ShowDialog(){
        SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE);
        pDialog.setTitleText("Chờ tí ?")
                .setContentText("Bạn có muốn thoát !")
                .setCancelText("Thoát")
                .setConfirmText("Quay Lại")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        mAuth.signOut();
                        FragmentTransaction trans = getFragmentManager().beginTransaction();
                        trans.replace(R.id.root_frame, new RootFragment());
                        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        trans.addToBackStack(null);
                        trans.commitAllowingStateLoss();
                        sDialog.cancel();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.cancel();
                    }
                })
                .setCancelable(false);
        pDialog.show();
    }

    private void anhXa() {
        btnLogOut = view.findViewById(R.id.btnOut);
        mRecyclerViewFavourite = view.findViewById(R.id.mRecyclerViewFavourite);
        mRecyclerViewRecent = view.findViewById(R.id.mRecyclerViewRecent);
        //btnDropRecent = view.findViewById(R.id.btnDropRecent);
        txtUserName = view.findViewById(R.id.userName);
        imgUser = view.findViewById(R.id.userImage);
        //btnDisAction = view.findViewById(R.id.btnDisableAction);
        txtUserEmail = view.findViewById(R.id.userEmail);
        txtEmptyFavourite = view.findViewById(R.id.txtEmptyFavourite);
        txtEmptyRecent = view.findViewById(R.id.txtEmptyRecent);

    }
}
