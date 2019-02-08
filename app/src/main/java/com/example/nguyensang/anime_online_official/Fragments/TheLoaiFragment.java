package com.example.nguyensang.anime_online_official.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.nguyensang.anime_online_official.Customclass.GridSpacingItemDecoration;
import com.example.nguyensang.anime_online_official.R;
import com.example.nguyensang.anime_online_official.Customclass.TheLoai;
import com.example.nguyensang.anime_online_official.Adapters.TheLoaiAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NguyenSang on 04/21/2018.
 */

@SuppressLint("ValidFragment")
public class TheLoaiFragment extends Fragment {

    private Spinner spinnerTheLoai;
    private RecyclerView myRecyclerViewTheLoai;
    private View view;
    private List<String> arrTheLoaiSpinner;
    private TheLoaiAdapter adapterTheLoai;
    private ArrayList<TheLoai> arrTheLoaiNhat;
    private ArrayList<TheLoai> arrTheLoaiTrungQuoc;
    private ArrayList<TheLoai> arrTheLoaiHoatHinh;
    private ArrayList<TheLoai> arrTheLoaiNamPhatHanh;
    private ArrayList<ArrayList<TheLoai>> arrDSTheLoai;

    private RecyclerView.LayoutManager layoutManager;
    private int spanCount = 2; // 3 columns
    private int spacing = 30; // 20 pixel
    private boolean includeEdge = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_theloai, container, false);
        anhXa();
        // set Spniner
        arrTheLoaiSpinner = new ArrayList<>();
        arrTheLoaiSpinner.add("Anime Nhật");
        arrTheLoaiSpinner.add("Anime Trung Quốc");
        arrTheLoaiSpinner.add("Hoạt Hình");
        arrTheLoaiSpinner.add("Năm phát hành");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,arrTheLoaiSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerTheLoai.setAdapter(adapter);

        InitBien();

        // Recycler View The Loai
        spinnerTheLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                adapterTheLoai = new TheLoaiAdapter(getActivity(), arrDSTheLoai.get(position));
                myRecyclerViewTheLoai.setAdapter(adapterTheLoai);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    private void InitBien() {

        InitArrTheLoai();
        //recycler view data
        layoutManager = new GridLayoutManager(getActivity(),2);
        myRecyclerViewTheLoai.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        adapterTheLoai = new TheLoaiAdapter(getActivity(), arrDSTheLoai.get(0));
        myRecyclerViewTheLoai.setLayoutManager(layoutManager);
        myRecyclerViewTheLoai.setNestedScrollingEnabled(false);
        myRecyclerViewTheLoai.setAdapter(adapterTheLoai);
    }


    private void anhXa() {
        spinnerTheLoai = view.findViewById(R.id.snipperTheLoai);
        myRecyclerViewTheLoai = view.findViewById(R.id.myRecyclerTheLoai);
    }

    private void InitArrTheLoai()
    {
        arrTheLoaiNhat = new ArrayList<>();
        arrTheLoaiTrungQuoc = new ArrayList<>();
        arrTheLoaiHoatHinh = new ArrayList<>();
        arrTheLoaiNamPhatHanh = new ArrayList<>();
        arrDSTheLoai = new ArrayList<>();
        arrDSTheLoai.add(arrTheLoaiNhat);
        arrDSTheLoai.add(arrTheLoaiTrungQuoc);
        arrDSTheLoai.add(arrTheLoaiHoatHinh);
        arrDSTheLoai.add(arrTheLoaiNamPhatHanh);
        arrDSTheLoai.get(0).add(new TheLoai ("Hành động", "http://animehay.tv/the-loai/phim-anime/hanh-dong"));
        arrDSTheLoai.get(0).add(new TheLoai ("Tình Cảm", "http://animehay.tv/the-loai/phim-anime/tinh-cam"));
        arrDSTheLoai.get(0).add(new TheLoai ("Lịch sử", "http://animehay.tv/the-loai/phim-anime/lich-su"));
        arrDSTheLoai.get(0).add(new TheLoai ("Hài Hước", "http://animehay.tv/the-loai/phim-anime/hai-huoc"));
        arrDSTheLoai.get(0).add(new TheLoai ("Viễn Tưởng", "http://animehay.tv/the-loai/phim-anime/vien-tuong"));
        arrDSTheLoai.get(0).add(new TheLoai ("Võ Thuật", "http://animehay.tv/the-loai/phim-anime/vo-thuat"));
        arrDSTheLoai.get(0).add(new TheLoai ("Giả tưởng", "http://animehay.tv/the-loai/phim-anime/gia-tuong"));
        arrDSTheLoai.get(0).add(new TheLoai ("Kinh Dị", "http://animehay.tv/the-loai/phim-anime/kinh-di"));
        arrDSTheLoai.get(0).add(new TheLoai ("Phiêu Lưu", "http://animehay.tv/the-loai/phim-anime/phieu-luu"));
        arrDSTheLoai.get(0).add(new TheLoai ("Học Đường", "http://animehay.tv/the-loai/phim-anime/hoc-duong"));
        arrDSTheLoai.get(0).add(new TheLoai ("Đời Thường", "http://animehay.tv/the-loai/phim-anime/doi-thuong"));
        arrDSTheLoai.get(0).add(new TheLoai ("Siêu nhiên", "http://animehay.tv/the-loai/phim-anime/sieu-nhien"));
        arrDSTheLoai.get(0).add(new TheLoai ("Harem", "http://animehay.tv/the-loai/phim-anime/harem"));
        arrDSTheLoai.get(0).add(new TheLoai ("Ecchi", "http://animehay.tv/the-loai/phim-anime/ecchi"));
        arrDSTheLoai.get(0).add(new TheLoai ("Shounen", "http://animehay.tv/the-loai/phim-anime/shounen"));
        arrDSTheLoai.get(0).add(new TheLoai ("Phép Thuật", "http://animehay.tv/the-loai/phim-anime/phep-thuat"));
        arrDSTheLoai.get(0).add(new TheLoai ("Trò chơi", "http://animehay.tv/the-loai/phim-anime/tro-choi"));
        arrDSTheLoai.get(0).add(new TheLoai ("Thám Tử", "http://animehay.tv/the-loai/phim-anime/tham-tu"));
        arrDSTheLoai.get(0).add(new TheLoai ("Mystery", "http://animehay.tv/the-loai/phim-anime/mystery"));
        arrDSTheLoai.get(0).add(new TheLoai ("Drama", "http://animehay.tv/the-loai/phim-anime/drama"));
        arrDSTheLoai.get(0).add(new TheLoai ("Seinen", "http://animehay.tv/the-loai/phim-anime/seinen"));
        arrDSTheLoai.get(0).add(new TheLoai ("Ác quỷ", "http://animehay.tv/the-loai/phim-anime/ac-quy"));
        arrDSTheLoai.get(0).add(new TheLoai ("Ma cà rồng", "http://animehay.tv/the-loai/phim-anime/ma-ca-rong"));
        arrDSTheLoai.get(0).add(new TheLoai ("Psychological", "http://animehay.tv/the-loai/phim-anime/psychological"));
        arrDSTheLoai.get(0).add(new TheLoai ("Shoujo", "http://animehay.tv/the-loai/phim-anime/shoujo"));
        arrDSTheLoai.get(0).add(new TheLoai ("Shounen Ai", "http://animehay.tv/the-loai/phim-anime/shounen-ai"));
        arrDSTheLoai.get(0).add(new TheLoai ("Tragedy", "http://animehay.tv/the-loai/phim-anime/tragedy"));
        arrDSTheLoai.get(0).add(new TheLoai ("Mecha", "http://animehay.tv/the-loai/phim-anime/mecha"));
        arrDSTheLoai.get(0).add(new TheLoai ("Siêu năng lực", "http://animehay.tv/the-loai/phim-anime/sieu-nang-luc"));
        arrDSTheLoai.get(0).add(new TheLoai ("Parody", "http://animehay.tv/the-loai/phim-anime/parody"));
        arrDSTheLoai.get(0).add(new TheLoai ("Quân đội", "http://animehay.tv/the-loai/phim-anime/quan-doi"));
        arrDSTheLoai.get(0).add(new TheLoai ("Live Action", "http://animehay.tv/the-loai/phim-anime/live-action"));
        arrDSTheLoai.get(0).add(new TheLoai ("Shoujo AI", "http://animehay.tv/the-loai/phim-anime/shoujo-ai"));
        arrDSTheLoai.get(0).add(new TheLoai ("Josei", "http://animehay.tv/the-loai/phim-anime/josei"));
        arrDSTheLoai.get(0).add(new TheLoai ("Thể Thao", "http://animehay.tv/the-loai/phim-anime/the-thao"));
        arrDSTheLoai.get(0).add(new TheLoai ("Âm nhạc", "http://animehay.tv/the-loai/phim-anime/am-nhac"));
        arrDSTheLoai.get(0).add(new TheLoai ("Samurai", "http://animehay.tv/the-loai/phim-anime/samurai"));
        arrDSTheLoai.get(0).add(new TheLoai ("Tokusatsu", "http://animehay.tv/the-loai/phim-anime/tokusatsu"));
        arrDSTheLoai.get(0).add(new TheLoai ("Space", "http://animehay.tv/the-loai/phim-anime/space"));
        arrDSTheLoai.get(0).add(new TheLoai ("Thriller", "http://animehay.tv/the-loai/phim-anime/thriller"));
        ////////////////////////////////////////////////////////
        arrDSTheLoai.get(1).add(new TheLoai ("Tiên Hiệp", "http://animehay.tv/the-loai/hoat-hinh-trung-quoc/tien-hiep"));
        arrDSTheLoai.get(1).add(new TheLoai ("Kiếm Hiệp", "http://animehay.tv/the-loai/hoat-hinh-trung-quoc/kiem-hiep"));
        arrDSTheLoai.get(1).add(new TheLoai ("Xuyên Không", "http://animehay.tv/the-loai/hoat-hinh-trung-quoc/xuyen-khong"));
        arrDSTheLoai.get(1).add(new TheLoai ("Trùng Sinh", "http://animehay.tv/the-loai/hoat-hinh-trung-quoc/trung-sinh"));
        arrDSTheLoai.get(1).add(new TheLoai ("Huyền Ảo", "http://animehay.tv/the-loai/hoat-hinh-trung-quoc/huyen-ao"));
        arrDSTheLoai.get(1).add(new TheLoai ("Ngôn Tình", "http://animehay.tv/the-loai/hoat-hinh-trung-quoc/ngon-tinh"));
        arrDSTheLoai.get(1).add(new TheLoai ("Dị Giới", "http://animehay.tv/the-loai/hoat-hinh-trung-quoc/di-gioi"));
        arrDSTheLoai.get(1).add(new TheLoai ("Khoa Huyễn", "http://animehay.tv/the-loai/hoat-hinh-trung-quoc/khoa-huyen"));
        arrDSTheLoai.get(1).add(new TheLoai ("Hài Hước[CN]", "http://animehay.tv/the-loai/hoat-hinh-trung-quoc/hai-huoc-cn"));
        arrDSTheLoai.get(1).add(new TheLoai ("Huyền Huyễn", "http://animehay.tv/the-loai/hoat-hinh-trung-quoc/huyen-huyen"));
        /////////////////////////////////////////////////////
        arrDSTheLoai.get(2).add(new TheLoai ("Mỹ", "http://animehay.tv/the-loai/phim-hoat-hinh/my"));
        arrDSTheLoai.get(2).add(new TheLoai ("Hàn Quốc", "http://animehay.tv/the-loai/phim-hoat-hinh/han-quoc"));
        arrDSTheLoai.get(2).add(new TheLoai ("Malaysia", "http://animehay.tv/the-loai/phim-hoat-hinh/malaysia"));
        arrDSTheLoai.get(2).add(new TheLoai ("Anh", "http://animehay.tv/the-loai/phim-hoat-hinh/anh"));
        arrDSTheLoai.get(2).add(new TheLoai ("Pháp", "http://animehay.tv/the-loai/phim-hoat-hinh/phap"));
        arrDSTheLoai.get(2).add(new TheLoai ("Đài loan", "http://animehay.tv/the-loai/phim-hoat-hinh/dai-loan"));
        arrDSTheLoai.get(2).add(new TheLoai ("Khác", "http://animehay.tv/the-loai/phim-hoat-hinh/khac"));
        /////////////////////////////////////////////////////
        arrDSTheLoai.get(3).add(new TheLoai ("2018", "http://animehay.tv/phim-phat-hanh/2018"));
        arrDSTheLoai.get(3).add(new TheLoai ("2017", "http://animehay.tv/phim-phat-hanh/2017"));
        arrDSTheLoai.get(3).add(new TheLoai ("2016", "http://animehay.tv/phim-phat-hanh/2016"));
        arrDSTheLoai.get(3).add(new TheLoai ("2015", "http://animehay.tv/phim-phat-hanh/2015"));
        arrDSTheLoai.get(3).add(new TheLoai ("2014", "http://animehay.tv/phim-phat-hanh/2014"));
        arrDSTheLoai.get(3).add(new TheLoai ("2013", "http://animehay.tv/phim-phat-hanh/2013"));
        arrDSTheLoai.get(3).add(new TheLoai ("2012", "http://animehay.tv/phim-phat-hanh/2012"));
        arrDSTheLoai.get(3).add(new TheLoai ("Trước 2012", "http://animehay.tv/phim-phat-hanh/-2012"));
        /////////////////////////////////////////////////////
    }


}
