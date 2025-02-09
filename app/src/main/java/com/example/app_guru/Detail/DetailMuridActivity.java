package com.example.app_guru.Detail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_guru.Edit.EditMuridActivity;
import com.example.app_guru.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailMuridActivity extends AppCompatActivity {
    private ListView listView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference muridRef;
    private List<Map<String, String>> muridData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_murid);

        listView = findViewById(R.id.listViewDetailMurid);
        firebaseDatabase = FirebaseDatabase.getInstance();
        muridRef = firebaseDatabase.getReference("Murid").orderByChild("nama").getRef(); // Mengurutkan berdasarkan nama

        muridData = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                muridData,
                android.R.layout.simple_list_item_2,
                new String[]{"info", "detail"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );

        listView.setAdapter(adapter);

        muridRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nama = snapshot.child("nama").getValue(String.class);
                    String kelas = snapshot.child("kelas").getValue(String.class);

                    String id = snapshot.getKey(); // Menggunakan key Firebase sebagai ID

                    // Menambahkan data ke list hanya jika ID valid
                    if (id != null) {
                        Map<String, String> muridInfo = new HashMap<>();
                        muridInfo.put("id", id); // Menggunakan ID sebagai kunci
                        muridInfo.put("info", nama != null ? nama : ""); // Nama murid
                        muridInfo.put("detail", "Kelas: " + (kelas != null ? kelas : "")); // Detail kelas & jenis kelamin

                        muridData.add(muridInfo);
                    }
                }
                adapter.notifyDataSetChanged(); // Memperbarui adapter untuk menampilkan data
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DetailMuridActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Menambahkan listener untuk mengarahkan ke EditMuridActivity saat item ListView diklik
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Map<String, String> selectedMurid = muridData.get(position);
            String muridId = selectedMurid.get("id"); // Mengambil ID untuk dikirim ke Edit Activity

            if (muridId != null) {
                Intent intent = new Intent(DetailMuridActivity.this, EditMuridActivity.class);
                intent.putExtra("muridKey", muridId); // Mengirim ID murid ke aktivitas edit
                startActivity(intent);
            } else {
                Toast.makeText(DetailMuridActivity.this, "ID murid tidak ditemukan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}