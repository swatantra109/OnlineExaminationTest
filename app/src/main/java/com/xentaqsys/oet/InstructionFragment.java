package com.xentaqsys.oet;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class InstructionFragment extends Fragment {


    AppCompatButton buttonStart;
    AppCompatCheckBox checkBoxAgree;
    FrameLayout cardView;
    public InstructionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_instruction, container, false);
        buttonStart = (AppCompatButton) view.findViewById(R.id.buttonStart);
        checkBoxAgree = (AppCompatCheckBox) view.findViewById(R.id.checkbox);
        cardView = (FrameLayout) view.findViewById(R.id.buttonCard);
        cardView.setBackgroundColor(Color.parseColor("#cc5100"));
//        buttonStart.setVisibility(View.GONE);
        checkBoxAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
//                    buttonStart.setEnabled(false);

//                        buttonStart.getBackground().setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);

//                    buttonStart.setVisibility(View.VISIBLE);
                    cardView.setBackgroundColor(Color.parseColor("#ff6600"));



                }
                else {
//                    buttonStart.getBackground().setColorFilter(null);
//                    buttonStart.setVisibility(View.GONE);
                    cardView.setBackgroundColor(Color.parseColor("#cc5100"));
//                    buttonStart.setEnabled(true);
                }
            }
        });

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBoxAgree.isChecked()){

                    loadFragment(QuestionsFragment.class);


                }
                else {

                    Toast.makeText(getActivity(),"Please select the agree with Terms and Conditions",Toast.LENGTH_SHORT).show();
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

}
