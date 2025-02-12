package com.example.app_guru.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.app_guru.Data.DataNilaiActivity;
import com.example.app_guru.R;


public class DataNilaiFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data_nilai, container, false);
        ListView listView = view.findViewById(R.id.Lv_DN);

        // Data untuk ListView
        String[] items = {"Activity 1"};

        // Buat adapter untuk ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                items
        );
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        // Menuju Activity 1
                        startActivity(new Intent(getActivity(), DataNilaiActivity.class));
                        break;
                }
            }
        });
        return view;
    }
}