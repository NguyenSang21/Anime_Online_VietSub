package com.example.nguyensang.anime_online_official.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nguyensang.anime_online_official.Activity.LoginActivity;
import com.example.nguyensang.anime_online_official.R;

/**
 * Created by NguyenSang on 04/21/2018.
 */

public class LoginFragment extends Fragment {

    private Button btnLogin;
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        anhXa();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    private void anhXa() {
        btnLogin = view.findViewById(R.id.btnLogin);
    }
}
