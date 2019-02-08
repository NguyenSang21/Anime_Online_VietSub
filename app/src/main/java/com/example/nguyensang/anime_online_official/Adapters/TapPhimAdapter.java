package com.example.nguyensang.anime_online_official.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nguyensang.anime_online_official.Customclass.LinkTapPhim;
import com.example.nguyensang.anime_online_official.R;
import com.example.nguyensang.anime_online_official.Customclass.SQLite;

import java.util.ArrayList;

/**
 * Created by NguyenSang on 04/21/2018.
 */

public class TapPhimAdapter extends RecyclerView.Adapter<TapPhimAdapter.RecyclerViewHolders> {
    private Context context;
    private ArrayList<String> tapPhimArray;
    private int row_index;
    private SQLite db ;
    private String linkCurrent = "";

    public TapPhimAdapter(Context context, ArrayList<String> tapPhimArray , int row_index) {
        this.context = context;
        this.tapPhimArray = tapPhimArray;
        this.row_index = row_index;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_dong_tap_so, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView, context, tapPhimArray);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, final int position) {
//        db = new SQLite(context, "DataRecent.sqlite", null, 1);
//        db.QueryData("CREATE TABLE IF NOT EXISTS DuLieuTapPhim(Id INTEGER PRIMARY KEY AUTOINCREMENT, TapPhim VARCHAR )");
//        if (db != null){
//            String link = tapPhimArray.get(position);
//            Cursor duLieu = db.GetData("SELECT *FROM DuLieuTapPhim");
//            for (duLieu.moveToLast(); !duLieu.isBeforeFirst(); duLieu.moveToPrevious()){
//                String tapP = duLieu.getString(1);
//                if (link.equals(tapP)){
//                    holder.txtTapSo.setTextColor(Color.parseColor("#F44336"));
//                }
//            }
//        }

        holder.txtTapSo.setText(""+(position+1));
        holder.layoutRela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = position;
                String link = tapPhimArray.get(position);
//                if (!link.equals(linkCurrent)){
//                    db.Insert2(link);
//                }
//                linkCurrent = link;
                LinkTapPhim link2 = (LinkTapPhim) context;
                link2.linkTapPhim(link);
                notifyDataSetChanged();
            }
        });
        if(row_index == position){
            holder.layoutRela.setBackgroundColor(Color.parseColor("#00BCD4"));
            holder.txtTapSo.setTextColor(Color.parseColor("#ffffff"));
        }
        else
        {
            holder.layoutRela.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.txtTapSo.setTextColor(Color.parseColor("#0097a7"));
        }
    }

    @Override
    public int getItemCount() {
        return tapPhimArray.size();
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder {

        public TextView txtTapSo;
        public RelativeLayout layoutRela;
        private ArrayList<String> arrayListTap;
        Context context;

        public RecyclerViewHolders(View itemView, Context context, ArrayList<String> arrayList) {
            super(itemView);
            this.context = context;
            arrayListTap = arrayList;
            txtTapSo = itemView.findViewById(R.id.txtTapPhimSo);
            layoutRela = itemView.findViewById(R.id.layoutTapPhim);
        }
    }
}
