package com.xentaqsys.oet;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends Fragment {

    AppCompatEditText editTextOldPassword,editTextNewPassword,editTextConfirmNewPassword;
    AppCompatButton buttonUpdate;
    AppCompatImageButton appCompatImageButtonPrevious;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        appCompatImageButtonPrevious = (AppCompatImageButton) view.findViewById(R.id.button_previous);
        editTextOldPassword = (AppCompatEditText)view.findViewById(R.id.et_old_password);
        editTextNewPassword = (AppCompatEditText)view.findViewById(R.id.et_new_password);
        editTextConfirmNewPassword = (AppCompatEditText)view.findViewById(R.id.et_confirm_new_password);
        buttonUpdate = (AppCompatButton)view.findViewById(R.id.btn_update_password);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vOldPassword = editTextOldPassword.getText().toString();
                String vNewPassword = editTextNewPassword.getText().toString();
                String vConfirmNewPassword = editTextConfirmNewPassword.getText().toString();
                boolean failFlag = false;
                Class fragmentClass = null;

                if (vOldPassword.trim().isEmpty()){
                    failFlag = true;
                    editTextOldPassword.setError("This field can not be empty");
                }
                if (vNewPassword.trim().isEmpty()){
                    failFlag = true;
                    editTextNewPassword.setError("This field can not be empty");
                }
                if (vConfirmNewPassword.trim().isEmpty()){
                    failFlag = true;
                    editTextConfirmNewPassword.setError("This field can not be empty");
                }
                if (!LoginActivity.isValidPassword(vOldPassword) && !vOldPassword.trim().isEmpty()){
                    failFlag = true;
                    editTextOldPassword.setError("Not a valid Password");
                }
                if (!LoginActivity.isValidPassword(vNewPassword) && !vNewPassword.trim().isEmpty()){
                    failFlag = true;
                    editTextNewPassword.setError("Not a valid Password");
                }
                if (!LoginActivity.isValidPassword(vConfirmNewPassword) && !vConfirmNewPassword.trim().isEmpty()){
                    failFlag = true;
                    editTextConfirmNewPassword.setError("Not a valid Password");
                }
                if (!vNewPassword.trim().equals(vConfirmNewPassword.trim())){
                    failFlag = true;
                    editTextConfirmNewPassword.setError("Password not matched");
                }
                if (!failFlag){

//                    Todo Api call to update the password of user with reference to new on on server by services
                    fragmentClass = FragmentDashBoard.class;
                    loadFragment(fragmentClass);
                }
            }
        });
        appCompatImageButtonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }
    void loadFragment(Class xyz) {
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
