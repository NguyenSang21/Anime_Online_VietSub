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

import com.example.nguyensang.anime_online_official.Activity.ShowTheLoaiActivity;
import com.example.nguyensang.anime_online_official.R;
import com.example.nguyensang.anime_online_official.Customclass.TheLoai;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by NguyenSang on 04/21/2018.
 */

public class TheLoaiAdapter extends RecyclerView.Adapter<TheLoaiAdapter.RecyclerViewHolders> {
    private Context context;
    private ArrayList<TheLoai> phimArrayList;
    private ArrayList<Integer> arrayListHinh;
    public TheLoaiAdapter(Context context, ArrayList<TheLoai> phimArrayListList) {
        this.context = context;
        this.phimArrayList = phimArrayListList;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_dong_theloai, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView,context,phimArrayList);
        return rcv;
    }

    private void addArrayHinh(){
        arrayListHinh = new ArrayList<>();
        arrayListHinh.add(R.drawable.item4);
//        arrayListHinh.add(R.drawable.item2);
//        arrayListHinh.add(R.drawable.item3);
//        arrayListHinh.add(R.drawable.item4);
//        arrayListHinh.add(R.drawable.item5);
//        arrayListHinh.add(R.drawable.item6);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        addArrayHinh();
        Random rand = new Random();
        //int  n = rand.nextInt(5) + 0;
        holder.txtTenTheLoai.setText(phimArrayList.get(position).getTenTheLoai());
        holder.imgTheLoai.setBackgroundResource(arrayListHinh.get(0));

    }

    @Override
    public int getItemCount() {
        return phimArrayList.size();
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView imgTheLoai;
        public TextView txtTenTheLoai;

        ArrayList<TheLoai> arrayListTheLoai= new ArrayList<>();
        Context context;

        public RecyclerViewHolders(View itemView, Context context, ArrayList<TheLoai> arrayList) {
            super(itemView);
            this.context = context;
            arrayListTheLoai = arrayList;
            itemView.setOnClickListener(this);
            imgTheLoai = itemView.findViewById(R.id.imgTheLoai);
            txtTenTheLoai = itemView.findViewById(R.id.txtTenTheLoai);
        }

        @Override
        public void onClick(View view) {
            int pos=getAdapterPosition();
            TheLoai theLoai = arrayListTheLoai.get(pos);
            Intent intent= new Intent(context,ShowTheLoaiActivity.class);
            ArrayList<String> arr = new ArrayList<>();
            arr.add(theLoai.getLinkTheLoai());
            arr.add(theLoai.getTenTheLoai());
            Bundle args = new Bundle();
            args.putSerializable("ARRAYLIST",arr);
            intent.putExtra("linkTheLoai",args);
            context.startActivity(intent);
        }
    }
}
