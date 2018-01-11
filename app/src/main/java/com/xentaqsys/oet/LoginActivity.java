package com.xentaqsys.oet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class LoginActivity extends AppCompatActivity {

    private static final String PREVIOUS_USER = "PreviousUser";
    private ConnectionDetector connectionDetector;
    private AlertDialogManager alertDialogManager;
    private SharedPreferences sharedPreferences;
    private String sharedPreferenceValue;

    private LinearLayout linearLayoutContainer;

    //    Login View

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewRegister;
    private TextView textViewForget;

    //    Registeration Card
    private EditText editTextRegisterName;
    private EditText editTextRegisterQualification;
    private EditText editTextRegisterMobile;
    private EditText editTextRegisterEmail;
    private EditText editTextRegisterPassword;
    private EditText editTextRegisterConfirmPassword;
    private Button buttonRegister;
    private TextView textViewRegisterLogin;
    AlertDialog.Builder builder;
    ListView modeList;
    AlertDialog alert;

    //    Forget Card
    private EditText editTextForgetEmail;
    private EditText editTextForgetMobile;
    private Button buttonSendOTP;
    private TextView textViewFogetRegister;
    private TextView textViewForgetLogin;

    //    Send OTP Card
    private EditText editTextConfirmOTP;
    private Button buttonConfirmOTP;
    private TextView textViewConfirmOTPRegister;
    private TextView textViewConfirmOTPLogin;

    public static boolean setPreference(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREVIOUS_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        linearLayoutContainer = (LinearLayout) findViewById(R.id.linear_layout_container);
        sharedPreferences = getSharedPreferences(PREVIOUS_USER, Context.MODE_PRIVATE);
        sharedPreferenceValue = sharedPreferences.getString("Status", "0");

        if (connectionDetectorDialog() && ifPreviousUser()) {

            //Todo Move to Next Screen Call next Activity or Dashboard Activity
            Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
            finish();
            startActivity(intent);
        } else {
            //Todo Show Login View
            showLoginCard();
        }
    }

    private boolean connectionDetectorDialog() {
        connectionDetector = new ConnectionDetector(getApplicationContext());

        if (!connectionDetector.isConnectingToInternet()) {
            alertDialogManager.showAlertDialog(LoginActivity.this,
                    "Internet Connection Error!",
                    "Please connect to working Internet connection", false);

            return false;
        } else {
            return true;
        }
    }

    private boolean ifPreviousUser() {
        //Todo If previous user return true else false check it and store it in sharedPreference
        return sharedPreferenceValue.equalsIgnoreCase("1");


    }

    private void showLoginCard() {
        linearLayoutContainer.removeAllViews();
        final LayoutInflater layoutInflater =
                (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        final View addLoginView = layoutInflater.inflate(R.layout.login_view2, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f);
        params.gravity = Gravity.CENTER;
        addLoginView.setLayoutParams(params);
        linearLayoutContainer.addView(addLoginView);


        buttonLogin = (Button) addLoginView.findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);

                finish();
                startActivity(intent);
            }
        });
        textViewRegister = (TextView) addLoginView.findViewById(R.id.signup);
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegisterView();
            }
        });


       /* buttonLogin = (Button) addLoginView.findViewById(R.id.btn_server_login);
        editTextUsername = (EditText) addLoginView.findViewById(R.id.et_email);
        editTextPassword = (EditText) addLoginView.findViewById(R.id.et_password);
        textViewRegister = (TextView) addLoginView.findViewById(R.id.textViewRegister);
        textViewForget = (TextView) addLoginView.findViewById(R.id.textViewForget);

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterView();
            }
        });

        textViewForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForgetView();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String vUserName = editTextUsername.getText().toString();
                String vPassword = editTextPassword.getText().toString();
                boolean failFlag = false;

                if (vUserName.isEmpty()) {
//                    ToDo SetError with Red Box
                    failFlag = true;
                    editTextUsername.setError("This Field can not be Empty ");
                }
                if (vPassword.isEmpty()) {
//                    ToDo SetError with Red Box
                    failFlag = true;
                    editTextPassword.setError("This Field can not be Empty");
                }
                if (!isValidEmailAddress(vUserName)) {
//                   ToDo SetError with Red Box
                    failFlag = true;
                    editTextUsername.setError("This is not a valid Email ID");
                }
                if (!isValidPassword(vPassword)) {
//                   ToDo SetError with Red Box
                    failFlag = true;
                    editTextPassword.setError("This is not valid Password");

                }
                if (!failFlag) {
//                    ToDo Api Call to validate email and password is ok or not
//                    ToDo Save the user Credentials to the private storage like shared preferences
//                    ToDo Move to the Dashboard Screen if user is a valid user
                    String vUsername = null,vEmailId = null,vStatus = null,vUserType = null,vMessage = null;
                    try {
                        JSONObject obj = new JSONObject(loadJSONFromAsset("login.json"));
                        JSONArray m_jArry = obj.getJSONArray("Details");

                        for (int i = 0; i < m_jArry.length(); i++) {
                            JSONObject jo_inside = m_jArry.getJSONObject(i);

                            vUsername = jo_inside.getString("UserName");
                            vEmailId = jo_inside.getString( "EmailId");
                            vStatus = jo_inside.getString("Status");
                            vUserType = jo_inside.getString("UserType");
                            vMessage = jo_inside.getString("Message");


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    assert vStatus != null;
                    if (vStatus.equalsIgnoreCase("1")){

                            Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                            setPreference(LoginActivity.this, "Status", vStatus);
                            setPreference(LoginActivity.this,"Emailid",vEmailId);
                            setPreference(LoginActivity.this,"Username",vUsername);
                            setPreference(LoginActivity.this,"UserType",vUserType);
                            finish();
                            startActivity(intent);



                    }
                    Toast.makeText(LoginActivity.this,""+vMessage,Toast.LENGTH_SHORT).show();


                }





                if (isValidEmailAddress(editTextUsername.getText().toString())){

                    Toast.makeText(LoginActivity.this, "Username is valid", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this, "Username not valid", Toast.LENGTH_SHORT).show();
                }
                if (isValidPassword(editTextPassword.getText().toString())){

                    Toast.makeText(LoginActivity.this, "Password is valid", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this, "Password not valid", Toast.LENGTH_SHORT).show();
                }

                if (DummyJsonFileParseforLoginButton(editTextUsername.getText().toString(), editTextPassword.getText().toString())) {

                    // TODO SAVE IT IN SHARED PREFERENCE FOR PREVIOUS USER FEATURE
                    Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                    setPreference(LoginActivity.this, "Success", "YES");


                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Username or Password not matched", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isValidPassword(String password) {
        String ePattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(password);
        return m.matches();
    }

    public String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private boolean DummyJsonFileParseforLoginButton(String tbusername, String tbpassword) {

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("login.json"));
            Log.d("Details-->", obj.getString("UserName"));
            String userId = obj.getString("UserName");
            String userName = obj.getString("UserName");
            String emailId = obj.getString("EmailId");
            String status = obj.getString("Status");
            String usertype = obj.getString("UserType");
            String message = obj.getString("Message");

            if (status.equals("1")) {
                setPreference(LoginActivity.this, "Username", userName);
                setPreference(LoginActivity.this, "Emailid", emailId);
                return true;
            } else {
                return false;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void showForgetView() {
        linearLayoutContainer.removeAllViews();
        final LayoutInflater layoutInflater =
                (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        final View addLoginView = layoutInflater.inflate(R.layout.forget_card, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f);
        params.gravity = Gravity.CENTER;
        addLoginView.setLayoutParams(params);
        linearLayoutContainer.addView(addLoginView);
        buttonSendOTP = (Button) addLoginView.findViewById(R.id.btn_send_otp);
        editTextForgetEmail = (EditText) addLoginView.findViewById(R.id.et_forget_mail);
        editTextForgetMobile = (EditText) addLoginView.findViewById(R.id.et_forget_mobile);
        textViewFogetRegister = (TextView) addLoginView.findViewById(R.id.textViewRegister);
        textViewForgetLogin = (TextView) addLoginView.findViewById(R.id.textViewForget);
        textViewForgetLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginCard();
            }
        });
        textViewFogetRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterView();
            }
        });
        buttonSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String vEmail = editTextForgetEmail.getText().toString();
                String vMobileNo = editTextForgetMobile.getText().toString();
                boolean failFlag = false;

                if (vEmail.isEmpty()) {
//                    set error with Red Box
                    failFlag =true;
                    editTextForgetEmail.setError("This field can not be Empty");

                }
                if (vMobileNo.isEmpty()) {
//                    set error with Red Box
                    failFlag = true;
                    editTextForgetMobile.setError("This field can not be Empty");
                }
                if (!isValidEmailAddress(vEmail) && !vEmail.isEmpty()) {
//                    set error with Red Box
                    failFlag =true;
                    editTextForgetEmail.setError("This is not a valid Email Id");
                }
                if (!isValidMobileNo(vMobileNo) && vMobileNo.isEmpty()) {
                    failFlag = true;
                    editTextForgetMobile.setError("This is not a valid Mobile No.");

                }
                if (!failFlag){
//                   Todo Api call to send Otp to the user email id or mobile no.
//                   Todo move to confirm OTP view
                    showOTPCard();

                }


            }
        });
    }

    private void showOTPCard() {
        linearLayoutContainer.removeAllViews();
        linearLayoutContainer.removeAllViews();
        final LayoutInflater layoutInflater =
                (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        final View addLoginView = layoutInflater.inflate(R.layout.send_otp_card, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f);
        params.gravity = Gravity.CENTER;
        addLoginView.setLayoutParams(params);
        linearLayoutContainer.addView(addLoginView);
        buttonConfirmOTP = (Button) addLoginView.findViewById(R.id.btn_confirm_otp);
        editTextConfirmOTP = (EditText) addLoginView.findViewById(R.id.et_otp);
        textViewConfirmOTPLogin = (TextView) addLoginView.findViewById(R.id.textViewConfirmOTPLogin);
        textViewConfirmOTPRegister = (TextView) addLoginView.findViewById(R.id.textViewConfirmOTPRegister);

        textViewConfirmOTPLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoginCard();
            }
        });
        textViewConfirmOTPRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegisterView();

            }
        });
        buttonConfirmOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String vOTP = editTextConfirmOTP.getText().toString();
                boolean failFlag = false;


                if (vOTP.isEmpty()) {
//                    set error with Red Box
                    failFlag = true;
                    editTextConfirmOTP.setError("This field can not be Empty");

                }
                if (!isValidOTP(vOTP) && !vOTP.isEmpty()) {
//                    set error with Red Box
                    editTextConfirmOTP.setError("This is not a valid OTP");
                }
                if (!failFlag){
//                    Todo Api call to confirm the otp with server if ok then move to the login view and login with new password
                    showLoginCard();

                }

            }
        });
    }

    private boolean isValidOTP(String vOTP) {

        String ePattern = "^[0-9]{6}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(vOTP);
        return m.matches();
    }


    private void showRegisterView() {
        linearLayoutContainer.removeAllViews();
        final LayoutInflater layoutInflater =
                (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        assert layoutInflater != null;
        final View addLoginView = layoutInflater.inflate(R.layout.register2, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f);
        params.gravity = Gravity.CENTER;
        addLoginView.setLayoutParams(params);
        linearLayoutContainer.addView(addLoginView);


        textViewRegisterLogin = (TextView) addLoginView.findViewById(R.id.loginfromregister);
        textViewRegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoginCard();
            }
        });


//        TODO INSERT REGISTRATION DETAILS WHEN PASSWORD AND CONFIRM PASSWORD MATCHES TO SERVER BY VOLLEY AND MOVE TOLOGIN CARD
/*
        buttonRegister = (Button) addLoginView.findViewById(R.id.btn_server_register);
        editTextRegisterName = (EditText) addLoginView.findViewById(R.id.et_register_name);
        editTextRegisterQualification = (EditText) addLoginView.findViewById(R.id.et_register_qualification);
        editTextRegisterMobile = (EditText) addLoginView.findViewById(R.id.et_register_mobile);
        editTextRegisterEmail = (EditText) addLoginView.findViewById(R.id.et_register_email);
        editTextRegisterPassword = (EditText) addLoginView.findViewById(R.id.et_register_password);
        editTextRegisterConfirmPassword = (EditText) addLoginView.findViewById(R.id.et_register_password_confirm);
        textViewRegisterLogin = (TextView) addLoginView.findViewById(R.id.textViewRegsiterLogin);
        editTextRegisterQualification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> courseList =new ArrayList<>();
                courseList.add("B.Tech");
                courseList.add("M.Tech");
                courseList.add("MCA");
                courseList.add("BCA");
                courseList.add("M.COM");
                courseList.add("B.COM");
                courseList.add("M.PHILL");
                courseList.add("D.PHILL");
                builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("SELECT OEM :");
//                builder.setIcon(R.drawable.ic_comment_check_outline_grey);
                modeList = new ListView(LoginActivity.this);
                modeList.setVerticalScrollBarEnabled(false);

                modeList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                modeList.setAdapter(new ArrayAdapter<String>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, courseList));


                builder.setView(modeList);
                alert = builder.show();
                //alert.show();
                modeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String val = (String) parent.getItemAtPosition(position);

                        editTextRegisterQualification.setText(val);
                        alert.hide();

                    }
                });
            }
        });
        textViewRegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginCard();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String vName = editTextRegisterName.getText().toString();
//                ToDo make qualification to be selected from dropdown list and list should come from api server side
//              String vQualification =
                String vMobileNo = editTextRegisterMobile.getText().toString();
                String vEmail = editTextRegisterEmail.getText().toString();
                String vPassword = editTextRegisterPassword.getText().toString();
                String vConfirmPassword = editTextRegisterConfirmPassword.getText().toString();
                String vQualification = editTextRegisterQualification.getText().toString();
                boolean failFlag = false;

                if (vName.isEmpty()) {
//                    set error with Red box
                    failFlag = true;
                    editTextRegisterName.setError("This field can not be Empty");
                }
//                Todo check qualification value should be selected
*//*
                else if (){

                }
*//*
                if (vQualification.isEmpty()){
                    failFlag = true;
                    editTextRegisterQualification.setError("This field can not be Empty");
                }
                if (vMobileNo.isEmpty()) {
//                    set error with Red Box
                    failFlag = true;
                    editTextRegisterMobile.setError("This field can not be Empty");

                }
                if (vEmail.isEmpty()) {
//                    set error with Red Box
                    failFlag = true;
                    editTextRegisterEmail.setError("This field can not be Empty");

                }
                if (vPassword.isEmpty()) {
//                    set error with Red Box
                    failFlag = true;
                    editTextRegisterPassword.setError("This field can not be Empty");
                }
                if (vConfirmPassword.isEmpty()) {
//                    set error with Red Box
                    failFlag = true;
                    editTextRegisterConfirmPassword.setError("This field can not be Empty");
                }
                if (!isValidName(vName) && !vName.isEmpty()) {
//                    set error with Red Box
                    failFlag = true;
                    editTextRegisterName.setError("This is not  a valid Name");
                }

                if (!isValidMobileNo(vMobileNo) && !vMobileNo.isEmpty()) {
//                    set error with Red Box
                    failFlag = true;
                    editTextRegisterMobile.setError("This is not a valid Mobile No.");
                }
                if (!isValidEmailAddress(vEmail) && !vEmail.isEmpty()) {
//                    set error with Red Box
                    failFlag = true;
                    editTextRegisterEmail.setError("This is not a valid Email Id");
                }
                if (!isValidPassword(vPassword) && !vPassword.isEmpty()) {
//                    set error with Red Box
                    failFlag = true;
                    editTextRegisterPassword.setError("This is not a valid Password");
                }
                if (!vPassword.equals(vConfirmPassword) && !vConfirmPassword.isEmpty() && vPassword.isEmpty()) {
                    failFlag = true;
                    editTextRegisterConfirmPassword.setError("Password not Matched");
                    editTextRegisterPassword.setError("");

                }
                if (!failFlag) {
//                    Todo Api Call to Server to perform Registration and get a value true or false
//                    Todo move to the login view after successful and if not then show the error message
                    showLoginCard();

                }

            }
        });*/
    }

    public static boolean isValidMobileNo(String vMobileNo) {
        String ePattern = "^\\d{10}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(vMobileNo);
        return m.matches();
    }

    public static boolean isValidName(String vName) {
        String ePattern = "^[a-zA-Z\\s]*$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(vName);
        return m.matches();
    }

    private boolean isNotEmptyField(String fieldValue) {
        if (fieldValue.equalsIgnoreCase("")) {

            return false;
        }
        return true;
    }
}



