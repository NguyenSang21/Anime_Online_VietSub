package com.example.nguyensang.anime_online_official.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nguyensang.anime_online_official.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Example about replacing fragments inside a ViewPager. I'm using
 * android-support-v7 to maximize the compatibility.
 *
 * @author Dani Lao (@dani_lao)
 *
 */
public class RootFragment extends Fragment {
    private FirebaseUser user;
    private FragmentTransaction trans;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /* Inflate the layout for this fragment */
        View view = inflater.inflate(R.layout.fragment_root, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            trans = getActivity().getSupportFragmentManager().beginTransaction();
            /*
             * IMPORTANT: We use the "root frame" defined in
             * "root_fragment.xml" as the reference to replace fragment
             */
            UserFragment userLogin = new UserFragment();
            trans.replace(R.id.root_frame, userLogin);
            /*
             * IMPORTANT: The following lines allow us to add the fragment
             * to the stack and return to it later, by pressing back
             */
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            trans.addToBackStack(null);
            //trans.commit();
            trans.commitAllowingStateLoss();
        }else{
            trans= getActivity().getSupportFragmentManager().beginTransaction();
            trans.replace(R.id.root_frame, new LoginFragment());
            trans.addToBackStack(null);
            trans.commit();
        }
        // show fragment đăng nhập
        return view;
    }

}