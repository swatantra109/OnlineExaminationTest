package com.xentaqsys.oet;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDashBoard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDashBoard extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView username,emailid;
    RelativeLayout relativeLayoutSelectedCourse,relativeLayoutSelectedTest;
    AlertDialog.Builder builder;
    ListView modeList;
    AlertDialog alert;
    ArrayList<String> courses;
    String totalTime = null,attemptCount = null,completionCount = null,noOfQuestion = null;
    TextView textViewCourseName,textViewTestName,textViewTotalTime,textViewAttemptCount,textViewCompletionCount,textViewNoofQuestion;
    Button button;
    Fragment fragment = null;
    Class fragmentClass = null;
    LinearLayout linearLayoutReview;
    LinearLayout linearLayoutCertificate;

    public FragmentDashBoard() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentDashBoard newInstance(String param1, String param2) {
        FragmentDashBoard fragment = new FragmentDashBoard();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open(filename);
            int size = is. available();
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dashboard2, container, false);
        AppCompatButton button1 = (AppCompatButton) view.findViewById(R.id.certificate_button) ;
        Drawable leftDrawable = AppCompatResources.getDrawable(getActivity(), R.drawable.ic_action_certificate);
        button1.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, null, null);
        AppCompatButton button2 = (AppCompatButton) view.findViewById(R.id.review_button) ;
        Drawable leftDrawable1 = AppCompatResources.getDrawable(getActivity(), R.drawable.ic_action_review);
        button2.setCompoundDrawablesWithIntrinsicBounds(leftDrawable1, null, null, null);
       /* relativeLayoutSelectedCourse = (RelativeLayout) view.findViewById(R.id.relativeLayoutCourse);
        relativeLayoutSelectedTest = (RelativeLayout) view.findViewById(R.id.relativeLayoutTest);
        linearLayoutReview = (LinearLayout) view.findViewById(R.id.linear_layout_review);
        linearLayoutCertificate = (LinearLayout) view.findViewById(R.id.linear_layout_certificate);
        textViewCourseName = (TextView) view.findViewById(R.id.textViewCourseName);
        textViewTestName = (TextView) view.findViewById(R.id.textViewTestName);
        textViewTotalTime = (TextView) view.findViewById(R.id.tv_total_time);
        textViewAttemptCount = (TextView)view.findViewById(R.id.tv_attemp_count);
        textViewCompletionCount = (TextView)view.findViewById(R.id.tv_completion_count);
        textViewNoofQuestion = (TextView)view.findViewById(R.id.tv_number_question);

        relativeLayoutSelectedCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    JSONObject obj = new JSONObject(loadJSONFromAsset("course_list.json"));
                    JSONArray m_jArry = obj.getJSONArray("courses");
                    courses = new ArrayList<>();
                    for (int i = 0; i < m_jArry.length(); i++) {
                        JSONObject jo_inside = m_jArry.getJSONObject(i);

                        String name = jo_inside.getString("name");
                        courses.add(name);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("SELECT Course :");
                builder.setIcon(R.drawable.ic_action_certi);
                modeList = new ListView(getActivity());
                modeList.setVerticalScrollBarEnabled(false);

                modeList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                modeList.setAdapter(new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_dropdown_item_1line, courses));


                builder.setView(modeList);
                alert = builder.show();
                //alert.show();
                modeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String val = (String) parent.getItemAtPosition(position);

                        textViewCourseName.setText(val);
                        alert.hide();

                    }
                });
            }
        });

        relativeLayoutSelectedTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<String> testList =new ArrayList<>();

                try {
                    JSONObject obj = new JSONObject(loadJSONFromAsset("test_list.json"));
                    JSONArray m_jArry = obj.getJSONArray(textViewCourseName.getText().toString());
//                    courses = new ArrayList<>();
                    for (int i = 0; i < m_jArry.length(); i++) {
                        JSONObject jo_inside = m_jArry.getJSONObject(i);

                        String name = jo_inside.getString("name");
                        testList.add(name);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("SELECT TEST :");

                builder.setIcon(R.drawable.ic_action_certi);
                modeList = new ListView(getActivity());
                modeList.setVerticalScrollBarEnabled(false);

                modeList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                modeList.setAdapter(new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_dropdown_item_1line, testList));


                builder.setView(modeList);
                alert = builder.show();
                //alert.show();
                modeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String val = (String) parent.getItemAtPosition(position);
                        textViewTestName.setText(val);
                        try {
                            JSONObject obj = new JSONObject(loadJSONFromAsset("test_details.json"));
                            JSONArray m_jArry = obj.getJSONArray(val);
//                    courses = new ArrayList<>();
                            for (int i = 0; i < m_jArry.length(); i++) {
                                JSONObject jo_inside = m_jArry.getJSONObject(i);

                                totalTime = jo_inside.getString("total_time");
                                attemptCount = jo_inside.getString("attempt_count");
                                completionCount = jo_inside.getString("completion_count");
                                noOfQuestion = jo_inside.getString("no_of_question");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                        textViewTotalTime.setText(totalTime);
                        textViewAttemptCount.setText(attemptCount);
                        textViewCompletionCount.setText(completionCount);
                        textViewNoofQuestion.setText(noOfQuestion);


                        alert.hide();

                    }
                });

            }
        });

        button = (Button) view.findViewById(R.id.btn_test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((textViewCourseName.getText().toString()).equalsIgnoreCase("Select Course Name")){
                    textViewCourseName.setError("Please Select a course");
                }
                if ((textViewTestName.getText().toString()).equalsIgnoreCase("Select Your Test")){
                    textViewTestName.setError("Please Select a test");
                }
                if (!(textViewTestName.getText().toString()).equalsIgnoreCase("Select Your Test") && !(textViewCourseName.getText().toString()).equalsIgnoreCase("Select Course Name")){
                    fragmentClass = InstructionFragment.class;
                    try {
                        fragment = (Fragment) fragmentClass.newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.flContent, fragment).commit();
                }

            }
        });
        linearLayoutReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String abc = textViewCourseName.getText().toString();
                String abc2 = textViewTestName.getText().toString();

                if ((textViewCourseName.getText().toString()).equalsIgnoreCase("Select Course Name")){
                    textViewCourseName.setError("Please Select a course");
                }
                if ((textViewTestName.getText().toString()).equalsIgnoreCase("Select Your Test")){
                    textViewTestName.setError("Please Select a test");
                }
                if (!textViewCourseName.getText().toString().equalsIgnoreCase("Select Course Name") && !textViewTestName.getText().toString().equalsIgnoreCase("Select Your Test")){

                    fragmentClass = ReviewFromDashboardFragment.class;
                    try {
                        fragment = (Fragment) fragmentClass.newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Bundle args = new Bundle();
                    args.putString("TestName", textViewTestName.getText().toString() );
                    fragment.setArguments(args);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.flContent, fragment).commit();
                }




            }
        });
        linearLayoutCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((textViewCourseName.getText().toString()).equalsIgnoreCase("Select Course Name")){
                    textViewCourseName.setError("Please Select a course");
                }
                if ((textViewTestName.getText().toString()).equalsIgnoreCase("Select Your Test")){
                    textViewTestName.setError("Please Select a test");
                }
                if (!(textViewTestName.getText().toString()).equalsIgnoreCase("Select Course Name") && !(textViewCourseName.getText().toString()).equalsIgnoreCase("Select Your Test")){
                    fragmentClass = CertificateFromDashboardFragment.class;
                    try {
                        fragment = (Fragment) fragmentClass.newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Bundle args = new Bundle();
                    args.putString("TestName", textViewTestName.getText().toString() );
                    fragment.setArguments(args);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.flContent, fragment).commit();
                }





            }
        });*/
        return view;
    }


}
