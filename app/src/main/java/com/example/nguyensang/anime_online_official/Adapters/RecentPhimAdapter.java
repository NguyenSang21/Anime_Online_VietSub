package com.example.nguyensang.anime_online_official.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nguyensang.anime_online_official.Activity.ChiTietPhimActivity;
import com.example.nguyensang.anime_online_official.Customclass.Phim;
import com.example.nguyensang.anime_online_official.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by NguyenSang on 04/21/2018.
 */

public class RecentPhimAdapter extends RecyclerView.Adapter<RecentPhimAdapter.RecyclerViewHolders> {
    private Context context;
    private ArrayList<Phim> phimArrayList;
    public RecentPhimAdapter(Context context, ArrayList<Phim> phimArrayListList) {
        this.context = context;
        this.phimArrayList = phimArrayListList;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_dong_recent, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView,context,phimArrayList);
        return rcv;
    }


    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.txtTenPhimRecent.setText(phimArrayList.get(position).getTenPhim());
        holder.txtDateRecent.setText(phimArrayList.get(position).getNamPhatHanh()); // mượn phương thức năm phát hành de luu ngay
        Picasso.get()
                .load(phimArrayList.get(position).getHinhAnh())
                .fit()
                .into(holder.imgRecent);
    }

    @Override
    public int getItemCount() {
        return phimArrayList.size();
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView txtTenPhimRecent, txtDateRecent;
        public ImageView imgRecent;
        ArrayList<Phim> arrayListRecent;
        Context context;

        public RecyclerViewHolders(View itemView, Context context, ArrayList<Phim> arrayList) {
            super(itemView);
            this.context = context;
            arrayListRecent = arrayList;
            itemView.setOnClickListener(this);
            txtTenPhimRecent = itemView.findViewById(R.id.txtTenPhimRecent);
            txtDateRecent = itemView.findViewById(R.id.txtNgayRecent);
            imgRecent = itemView.findViewById(R.id.imgRecent);
        }

        @Override
        public void onClick(View view) {
            int pos=getAdapterPosition();
            Intent intent= new Intent(context,ChiTietPhimActivity.class);
            ArrayList<String> arr = new ArrayList<>();
            arr.add(arrayListRecent.get(pos).getLink());
            arr.add(arrayListRecent.get(pos).getHinhAnh());
            Bundle args = new Bundle();
            args.putSerializable("ARRAYLIST",arr);
            intent.putExtra("linkPhim", args);
            context.startActivity(intent);
        }
    }
}
