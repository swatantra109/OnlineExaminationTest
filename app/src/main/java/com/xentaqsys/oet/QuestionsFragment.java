package com.xentaqsys.oet;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;


import java.util.ArrayList;

import static com.xentaqsys.oet.DividerItemDecoration.VERTICAL_LIST;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionsFragment extends Fragment {

    RecyclerView recyclerViewOptions;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<OptionItems> dashBoardItems =new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    AppCompatImageButton button_next;
    LinearLayout completelinear;
    int sampleexample=0;
    AppCompatButton appCompatButtonCompleted;
    AppCompatButton appCompatButtonDoAgain;
    public QuestionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_questions, container, false);



        recyclerViewOptions = (RecyclerView) view.findViewById(R.id.recyclerview_options);
        completelinear = (LinearLayout) view.findViewById(R.id.completelinear);
        appCompatButtonCompleted = (AppCompatButton) view.findViewById(R.id.buttonTestCompleted);
        appCompatButtonDoAgain = (AppCompatButton) view.findViewById(R.id.buttonDoAgain);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewOptions.setLayoutManager(mLayoutManager);
//        recyclerViewDashboard.setHasFixedSize(true);
        recyclerViewOptions.addItemDecoration(new DividerItemDecoration(getActivity(),VERTICAL_LIST));


        loadQuestion(sampleexample);
        appCompatButtonDoAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(InstructionFragment.class);
            }
        });
        appCompatButtonCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(ReviewFragment.class);
            }
        });
        button_next = (AppCompatImageButton)view.findViewById(R.id.button_next);
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sampleexample++;
                if (sampleexample<3){
                    loadQuestion(sampleexample);
                }
                else if (sampleexample>=3){
//                    TODO REVIEW PAGE
                    completelinear.setVisibility(View.VISIBLE);
                    completelinear.bringToFront();
                    Toast.makeText(getActivity(),"Test Completed",Toast.LENGTH_SHORT).show();
                }


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
        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.flContent, fragment).commit();


    }
    private void loadQuestion(int ij) {
        dashBoardItems.clear();
        if (ij==0){
            for (int i=0;i<4;i++){

                String s= Character.toString((char)(65+i));
                OptionItems item = new OptionItems();
                item.setValue1("CourseName:"+i);
                item.setValue2("TestName:"+i);
                item.setValue3(s);
                dashBoardItems.add(item);
//            courseName.add(String.valueOf(i));

            }

        }
        else if (ij==1){


                String s= Character.toString((char)(65));
                OptionItems item = new OptionItems();
                item.setValue1("True");
                item.setValue2("TestName:");
                item.setValue3(s);
                dashBoardItems.add(item);
//            courseName.add(String.valueOf(i));
            String s2= Character.toString((char)(66));
            OptionItems item2 = new OptionItems();
            item2.setValue1("False");
            item2.setValue2("TestName:");
            item2.setValue3(s2);
            dashBoardItems.add(item);

        }
        else if (ij==2){

            for (int i=0;i<4;i++){

                String s= Character.toString((char)(65+i));
                OptionItems item = new OptionItems();
                item.setValue1("Option:"+i);
                item.setValue2("TestName:"+i);
                item.setValue3(s);
                dashBoardItems.add(item);
//            courseName.add(String.valueOf(i));

            }

        }
        else if (ij==3){

        }
        else if (ij==4){

        }

        mAdapter = new OptionListAdapter(getActivity(), dashBoardItems);
        recyclerViewOptions.setAdapter(mAdapter);
    }

}
