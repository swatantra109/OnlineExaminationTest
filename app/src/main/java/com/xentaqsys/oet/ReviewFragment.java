package com.xentaqsys.oet;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends Fragment {


    AppCompatImageButton appCompatButtonNext;
    LinearLayout completelinear;
    AppCompatButton appCompatButtonCertificate;
    AppCompatButton appCompatButtonHome;

    public ReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        appCompatButtonNext = (AppCompatImageButton) view.findViewById(R.id.button_next);
        appCompatButtonCertificate = (AppCompatButton) view.findViewById(R.id.buttonTestCompleted);
        appCompatButtonHome = (AppCompatButton) view.findViewById(R.id.buttonDoAgain);
        completelinear = (LinearLayout) view.findViewById(R.id.completelinear);

        appCompatButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                completelinear.setVisibility(View.VISIBLE);
                completelinear.bringToFront();
            }
        });

        appCompatButtonCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        appCompatButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(FragmentDashBoard.class);
            }
        });
        return view;
    }
    void loadFragment(Class xyz){
        Fragment fragment = null;
//        Class fragmentClass = null;


        try {
            fragment = (Fragment) xyz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();


    }
}
