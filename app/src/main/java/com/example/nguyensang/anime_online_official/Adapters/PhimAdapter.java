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

public class PhimAdapter extends RecyclerView.Adapter<PhimAdapter.RecyclerViewHolders> {
    private Context context;
    private ArrayList<Phim> phimArrayList;
    public PhimAdapter(Context context, ArrayList<Phim> phimArrayListList) {
        this.context = context;
        this.phimArrayList = phimArrayListList;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_dong_phim, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView,context,phimArrayList);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.txtTenPhim.setText(phimArrayList.get(position).getTenPhim());
        holder.txtSoTapPhim.setText(phimArrayList.get(position).getSoTapHienCo());

        Picasso.get()
                .load(phimArrayList.get(position).getHinhAnh())
                .fit()
                .centerCrop()
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(holder.imgHinh);
    }

    @Override
    public int getItemCount() {
        return phimArrayList.size();
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView imgHinh;
        public TextView txtTenPhim,txtSoTapPhim;
        ArrayList<Phim> arrayListPhim;
        Context context;

        public RecyclerViewHolders(View itemView, Context context, ArrayList<Phim> arrayList) {
            super(itemView);
            this.context=context;
            arrayListPhim=arrayList;
            itemView.setOnClickListener(this);
            txtTenPhim =  itemView.findViewById(R.id.txtTenPhim);
            txtSoTapPhim =  itemView.findViewById(R.id.txtSoTapPhim);
            imgHinh= itemView.findViewById(R.id.imgPhim);
        }

        @Override
        public void onClick(View view) {
            int pos=getAdapterPosition();
            Phim phim= arrayListPhim.get(pos);
            Intent intent= new Intent(context,ChiTietPhimActivity.class);
            ArrayList<String> arr = new ArrayList<>();
            arr.add(phim.getLink());
            arr.add(phim.getHinhAnh());
            Bundle args = new Bundle();
            args.putSerializable("ARRAYLIST",arr);
            intent.putExtra("linkPhim", args);
            context.startActivity(intent);
        }
    }
}
