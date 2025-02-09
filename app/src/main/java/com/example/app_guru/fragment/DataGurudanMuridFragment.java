package com.example.app_guru.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.app_guru.Add.AddDataGuruActivity;
import com.example.app_guru.Add.AddDataMuridActivity;
import com.example.app_guru.Detail.DetailGuruActivity;
import com.example.app_guru.Detail.DetailMuridActivity;
import com.example.app_guru.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DataGurudanMuridFragment extends Fragment {
    private ListView listView;
    private Button btn_add_guru, btn_add_murid;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference guruRef, muridRef;
    private ArrayAdapter<String> adapter;
    private List<String> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data_gurudan_murid, container, false);

        listView = view.findViewById(R.id.Lv_DGM);
        btn_add_guru = view.findViewById(R.id.btn_add1);
        btn_add_murid = view.findViewById(R.id.btn_add2);

        firebaseDatabase = FirebaseDatabase.getInstance();
        guruRef = firebaseDatabase.getReference("Guru"); // Referensi untuk data guru
        muridRef = firebaseDatabase.getReference("Murid"); // Referensi untuk data murid

        data = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);

        // Ambil jumlah guru dan murid dari Firebase
        guruRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long guruCount = dataSnapshot.getChildrenCount();
                data.add("Data Guru: " + guruCount);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        muridRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long muridCount = dataSnapshot.getChildrenCount();
                data.add("Data Murid: " + muridCount);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            String item = data.get(position);
            Intent intent = new Intent();

            if (item.contains("Guru")) {
                intent = new Intent(getContext(), DetailGuruActivity.class); // Menuju aktivitas detail guru
            } else if (item.contains("Murid")) {
                intent = new Intent(getContext(), DetailMuridActivity.class); // Menuju aktivitas detail murid
            }

            startActivity(intent);
        });

        btn_add_guru.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddDataGuruActivity.class);
            startActivity(intent);
        });

        btn_add_murid.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddDataMuridActivity.class);
            startActivity(intent);
        });

        return view;
    }
}
