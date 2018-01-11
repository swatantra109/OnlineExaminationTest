package com.xentaqsys.oet;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.util.Base64;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DashBoardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String PREVIOUS_USER = "PreviousUser";
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }


    //    navigation items
    TextView username,emailid;
    ImageView imageViewProfilePic;
    NavigationView mNavigationView;
    View mHeaderView;

    RelativeLayout relativeLayoutSelectedCourse,relativeLayoutSelectedTest;
    AlertDialog.Builder builder;
    ListView modeList;
    AlertDialog alert;
    ArrayList<String> courses;
    TextView textViewCourseName,textViewTestName;
    Class fragmentClass = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dash_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayShowTitleEnabled(false);



//        relativeLayoutSelectedCourse = (RelativeLayout) findViewById(R.id.relativeLayoutCourse);
//        relativeLayoutSelectedTest = (RelativeLayout) findViewById(R.id.relativeLayoutTest);
//        textViewCourseName = (TextView) findViewById(R.id.textViewCourseName);
//        textViewTestName = (TextView) findViewById(R.id.textViewTestName);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        EndDrawerToggle toggle = new EndDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //        navigation items


        // NavigationView Header
        mHeaderView =  navigationView.getHeaderView(0);
        username = (TextView) mHeaderView.findViewById(R.id.textViewEmailUsername);
        username.setText(getPreference(this,"Username","abc singh"));

        emailid = (TextView) mHeaderView.findViewById(R.id.textViewEmail);
        emailid.setText(getPreference(this,"Emailid","abc@xyz.com"));

        if (!getPreference(DashBoardActivity.this,"userphoto","abc").trim().equalsIgnoreCase("abc")){
            String base=getPreference(DashBoardActivity.this,"userphoto","abc");
            byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
            imageViewProfilePic = (ImageView) mHeaderView.findViewById(R.id.imageView);

            imageViewProfilePic.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes,0, imageAsBytes.length) );
        }


   /*     relativeLayoutSelectedCourse.setOnClickListener(new View.OnClickListener() {
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



                builder = new AlertDialog.Builder(DashBoardActivity.this);
                builder.setTitle("SELECT Course :");
                builder.setIcon(R.drawable.ic_action_certi);
                modeList = new ListView(DashBoardActivity.this);
                modeList.setVerticalScrollBarEnabled(false);

                modeList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                modeList.setAdapter(new ArrayAdapter<String>(DashBoardActivity.this,
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
        });*/

      /*  relativeLayoutSelectedTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> testList =new ArrayList<>();

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



                builder = new AlertDialog.Builder(DashBoardActivity.this);

                builder.setTitle("SELECT TEST :");

                builder.setIcon(R.drawable.ic_action_certi);
                modeList = new ListView(DashBoardActivity.this);
                modeList.setVerticalScrollBarEnabled(false);

                modeList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                modeList.setAdapter(new ArrayAdapter<String>(DashBoardActivity.this,
                        android.R.layout.simple_dropdown_item_1line, testList));


                builder.setView(modeList);
                alert = builder.show();
                //alert.show();
                modeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String val = (String) parent.getItemAtPosition(position);

                        textViewTestName.setText(val);
                        alert.hide();

                    }
                });

            }
        });*/
        if (savedInstanceState==null) {
            //do your stuff
            loadFragment(new FragmentDashBoard());
        }


    }


    public static String getPreference(Context context, String key,String defaultvalue) {
        SharedPreferences settings = context.getSharedPreferences(PREVIOUS_USER, Context.MODE_PRIVATE);
        return settings.getString(key, defaultvalue);
    }
    public String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = getAssets().open(filename);
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }     else {
            int fragments = getSupportFragmentManager().getBackStackEntryCount();
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.flContent);

//           if(fragment instanceof FragmentDashBoard){
//
//           }
            if (fragments == 0 || fragment instanceof FragmentDashBoard ) {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Exit")
                        .setMessage("Are you sure you want to close this App?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            } else {
                if (getFragmentManager().getBackStackEntryCount() > 1) {
                    getFragmentManager().popBackStack();
                } else {
                    super.onBackPressed();
                }
            }
        }
    }

    public static boolean setPreference(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREVIOUS_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment  fragmentClass= null;
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            fragmentClass = new FragmentDashBoard();
            loadFragment(fragmentClass);
            // Handle the camera action
        } else if (id == R.id.nav_edit_profile) {
            fragmentClass = new EditProfileFragment();
            loadFragment(fragmentClass);

        } else if (id == R.id.nav_change_password) {
            fragmentClass = new  ChangePasswordFragment();
            loadFragment(fragmentClass);
        } else if (id == R.id.nav_logout) {

            SharedPreferences settings = getSharedPreferences(PREVIOUS_USER, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.clear().apply();
            Intent intent = new Intent(DashBoardActivity.this,LoginActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void loadFragment(Fragment xyz) {
        Fragment fragment = null;
//        Class fragmentClass = null;


        try {
            fragment = xyz;
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (xyz instanceof FragmentDashBoard ){
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }else {
            fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.flContent, fragment).commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
