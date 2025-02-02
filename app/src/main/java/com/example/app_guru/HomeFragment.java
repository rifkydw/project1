
package com.example.app_guru;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.content.Intent;
import androidx.fragment.app.Fragment;

import com.example.app_guru.Adapter.UjianAdapter;
import com.google.firebase.database.*;
import java.util.*;

public class HomeFragment extends Fragment {
    private ListView ujianListView;
    private DatabaseReference databaseReference;
    private List<Ujian> ujianInfoList;
    private UjianAdapter ujianAdapter;
    private ValueEventListener ujianListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ujianListView = view.findViewById(R.id.LV_HF); // Pastikan ID ini benar di layout XML
        ujianInfoList = new ArrayList<>();
        ujianAdapter = new UjianAdapter(getActivity(), ujianInfoList);
        ujianListView.setAdapter(ujianAdapter);

        // Referensi ke Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("jadwal_ujian");

        // Baca data ujian dari Firebase dan update ListView
        ujianListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ujianInfoList.clear(); // Bersihkan daftar sebelum menambahkan data baru
                for (DataSnapshot ujianSnapshot : dataSnapshot.getChildren()) {
                    // Ambil informasi ujian
                    String ujianId = ujianSnapshot.getKey(); // ID ujian
                    String namaUjian = ujianSnapshot.child("nama_ujian").getValue(String.class);
                    String tanggal = ujianSnapshot.child("tanggal").getValue(String.class);
                    String waktuMulai = ujianSnapshot.child("waktu_mulai").getValue(String.class); // Waktu mulai
                    String waktuSelesai = ujianSnapshot.child("waktu_selesai").getValue(String.class); // Waktu selesai
                    String pengawas = ujianSnapshot.child("pengawas").getValue(String.class);

                    // Tambahkan data ujian ke daftar
                    Ujian ujianInfo = new Ujian(ujianId, namaUjian, tanggal, waktuMulai, waktuSelesai, pengawas);
                    ujianInfoList.add(ujianInfo);
                }

                ujianAdapter.notifyDataSetChanged(); // Update tampilan
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };

        databaseReference.addValueEventListener(ujianListener); // Tambahkan listener

        // Tombol untuk menambahkan ujian baru
        ImageButton addButton = view.findViewById(R.id.btn_add);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TambahUjian.class); // Aktivitas untuk tambah ujian
            startActivity(intent);
        });

        ujianListView.setOnItemClickListener((parent, view1, position, id) -> {
            Ujian selectedUjian = ujianInfoList.get(position);

            Intent intent = new Intent(getActivity(), DetailUjian.class); // Aktivitas detail ujian
            intent.putExtra("ujianId", selectedUjian.getUjianId()); // Mengirim ID ujian
            startActivity(intent); // Buka detail ujian
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Hapus listener untuk mencegah kebocoran memori
        if (databaseReference != null && ujianListener != null) {
            databaseReference.removeEventListener(ujianListener);
        }
    }
}
