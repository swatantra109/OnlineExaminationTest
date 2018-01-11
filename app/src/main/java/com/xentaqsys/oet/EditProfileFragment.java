package com.xentaqsys.oet;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {

    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    EditText editTextUsername,editTextQualification,editTextMobileNo,editTextUserID;
    RelativeLayout relativeLayoutrelativeQualification;
    AppCompatImageView compatImageViewProfile;
    AppCompatImageButton compatImageViewPrevious;
    Button buttonUpdate;
    AlertDialog.Builder builder;
    ListView modeList;
    AlertDialog alert;
    Bitmap bitmap;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_edit_profile, container, false);

        compatImageViewPrevious = (AppCompatImageButton) view.findViewById(R.id.button_previous);
        compatImageViewProfile = (AppCompatImageView)view.findViewById(R.id.uploadpic);
        editTextUsername = (EditText) view.findViewById(R.id.et_username);
        editTextQualification = (EditText)view.findViewById(R.id.et_qualification);
        buttonUpdate = (Button) view.findViewById(R.id.btn_update_profile);
        editTextUserID = (EditText)view.findViewById(R.id.et_userid);
        editTextMobileNo = (EditText)view.findViewById(R.id.et_mobile_no);

        editTextUserID.setText(DashBoardActivity.getPreference(getActivity(),"Emailid","abc@xyz.com"));
        editTextUsername.setText(DashBoardActivity.getPreference(getActivity(),"Username","Enter your name"));
        editTextQualification.setText(DashBoardActivity.getPreference(getActivity(),"Qualification","Select your course"));
        editTextMobileNo.setText(DashBoardActivity.getPreference(getActivity(),"MobileNo","Mobile"));
        if (!DashBoardActivity.getPreference(getActivity(),"userphoto","abc").equalsIgnoreCase("abc")){
            String base=DashBoardActivity.getPreference(getActivity(),"userphoto","abc");
            byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);

            compatImageViewProfile.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes,0, imageAsBytes.length) );
        }


        compatImageViewPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });


        editTextQualification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> qualifications = new ArrayList<>();

                try {
                    JSONObject obj = new JSONObject(loadJSONFromAsset("course_list.json"));
                    JSONArray m_jArry = obj.getJSONArray("courses");
                    for (int i = 0; i < m_jArry.length(); i++) {
                        JSONObject jo_inside = m_jArry.getJSONObject(i);

                        String name = jo_inside.getString("name");
                        qualifications.add(name);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("SELECT QUALIFICATION :");
                builder.setIcon(R.drawable.ic_action_certi);
                modeList = new ListView(getActivity());
                modeList.setVerticalScrollBarEnabled(false);

                modeList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                modeList.setAdapter(new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_dropdown_item_1line, qualifications));


                builder.setView(modeList);
                alert = builder.show();
                //alert.show();
                modeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String val = (String) parent.getItemAtPosition(position);

                        editTextQualification.setText(val);
                        alert.hide();

                    }
                });
            }
        });

        editTextMobileNo = (EditText) view.findViewById(R.id.et_mobile_no);
               compatImageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissionREAD_EXTERNAL_STORAGE(getActivity())) {
                    // do your stuff..
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,
                            "Select Picture"), SELECT_PICTURE);
                }

            }
        });

               buttonUpdate.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       String vUsername = editTextUsername.getText().toString();
                       String vQualification = editTextQualification.getText().toString();
                       String vMobileNo = editTextMobileNo.getText().toString();
                       boolean failFlag =false;
                       Class fragmentClass = null;

                       if (vUsername.trim().isEmpty()){
                           failFlag = true;
                           editTextUsername.setError("This field can not be Empty");

                       }
                       if (vQualification.trim().isEmpty()){
                           failFlag = true;
                           editTextQualification.setError("This field can not be Empty");
                       }
                       if (vMobileNo.trim().isEmpty()){
                           failFlag = true;
                           editTextMobileNo.setError("This field can not be Empty");
                       }
                       if (!LoginActivity.isValidName(vUsername) && !vUsername.trim().isEmpty()){
                           failFlag =false;
                           editTextUsername.setError("Not a valid Username");
                       }
                       if ((!LoginActivity.isValidMobileNo(vMobileNo)) && !vMobileNo.trim().isEmpty()){
                           failFlag =false;
                           editTextUsername.setError("Not a valid Mobile Number");
                       }
                       if (!failFlag){
//                           Todo Api call to store the user details on server with profile pic also
                           ByteArrayOutputStream stream=new ByteArrayOutputStream();
                           if (bitmap!=null){
                               getRoundedShape(bitmap).compress(Bitmap.CompressFormat.PNG, 90, stream);
                               byte[] image=stream.toByteArray();
                               //System.out.println("byte array:"+image);
                               //final String img_str = "data:image/png;base64,"+ Base64.encodeToString(image, 0);
                               //System.out.println("string:"+img_str);
                               String img_str = Base64.encodeToString(image, 0);
                          /* //decode string to image
                           String base=img_str;
                           byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
                           ImageView ivsavedphoto = (ImageView)this.findViewById(R.id.usersavedphoto);
                           ivsavedphoto.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes,0, imageAsBytes.length) );*/
                               //save in sharedpreferences
                               LoginActivity.setPreference(getActivity(),"userphoto",img_str);
                           }
                           else {
                               Toast.makeText(getActivity(),"Profile Picture is not Updated",Toast.LENGTH_SHORT).show();
                           }


                           /*SharedPreferences preferences = getSharedPreferences("myprefs",MODE_PRIVATE);
                           SharedPreferences.Editor editor = preferences.edit();
                           editor.putString("userphoto",img_str);
                           editor.commit();*/
                           LoginActivity.setPreference(getActivity(),"Username",vUsername);
                           LoginActivity.setPreference(getActivity(),"Qualification",vQualification);
                           LoginActivity.setPreference(getActivity(),"MobileNo",vMobileNo);
                           fragmentClass = FragmentDashBoard.class;
                           loadFragment(fragmentClass);
                       }

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
    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }
    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
//                compatImageViewProfile.setImageURI(selectedImageUri);
//                compatImageViewProfile.setImageDrawable(RoundedBitmapDrawableFactory.create(getResources(),selectedImagePath));
                File image = new File(selectedImagePath);
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);
                bitmap = Bitmap.createScaledBitmap(bitmap,100,100,true);
                compatImageViewProfile.setImageBitmap(getRoundedShape(bitmap));


            }
        }
    }
    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth = 500;
        int targetHeight = 500;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth, targetHeight), null);
        return targetBitmap;
    }
    public String getPath(Uri contentUri) {
// we have to check for sdk version because from lollipop the retrieval of path is different
        if (Build.VERSION.SDK_INT < 21) {
            // can post image
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();

            return cursor.getString(column_index);
        } else {
            String filePath = "";
            String wholeID = DocumentsContract.getDocumentId(contentUri);

            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];

            String[] column = {MediaStore.Images.Media.DATA};

            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            Cursor cursor = getActivity().getContentResolver().
                    query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            column, sel, new String[]{id}, null);


            int columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
            return filePath;
        }
    }
}
