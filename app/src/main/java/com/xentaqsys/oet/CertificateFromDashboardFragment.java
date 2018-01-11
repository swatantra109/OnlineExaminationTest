package com.xentaqsys.oet;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class CertificateFromDashboardFragment extends Fragment {
    private List<CertificateDashboardItems> certificateDashboardItems;
    private CertificateDashboardAdapter  certificateDashboardAdapter;
    RecyclerView recyclerView;
    private LinearLayoutManager nLayoutManager;
    private AppCompatImageButton appCompatImageButtonPrevious;


    public CertificateFromDashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_certificate_from_dashboard, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.reviewTestRecyclerView);
        appCompatImageButtonPrevious =(AppCompatImageButton) view.findViewById(R.id.button_previous);
        nLayoutManager = new LinearLayoutManager(getActivity());
        certificateDashboardItems = new ArrayList<CertificateDashboardItems>();
        certificateDashboardAdapter = new CertificateDashboardAdapter(getActivity(),certificateDashboardItems);
        recyclerView.setLayoutManager(nLayoutManager);
        recyclerView.setAdapter(certificateDashboardAdapter);
        appCompatImageButtonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        LoadJsonData();

        return view;
    }

    private void LoadJsonData() {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("review_dashboard.json"));
            JSONArray m_jArry = obj.getJSONArray(getArguments().getString("TestName"));

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                CertificateDashboardItems item1 = new CertificateDashboardItems();
                item1.setAttempt(jo_inside.getString("Attempt"));
                item1.setRight(jo_inside.getString("Right"));
                item1.setWrong(jo_inside.getString("Wrong"));
                item1.setMarks(jo_inside.getString("Marks"));
//                item1.setCompleted(jo_inside.getString("Completed"));


                certificateDashboardItems.add(item1);

            }
            certificateDashboardAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open(filename);
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
}
