package com.example.app_guru.Detail;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_guru.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DetailUjian extends AppCompatActivity {
    private EditText namaUjianEditText, tanggalEditText, waktuMulaiEditText, waktuSelesaiEditText, pengawasEditText;
    private Button saveButton, deleteButton;
    private DatabaseReference databaseReference;
    private String ujianId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_ujian);
        namaUjianEditText = findViewById(R.id.nama_ujian_edit_text);
        tanggalEditText = findViewById(R.id.tanggal_edit_text);
        waktuMulaiEditText = findViewById(R.id.waktu_mulai_edit_text);
        waktuSelesaiEditText = findViewById(R.id.waktu_selesai_edit_text);
        pengawasEditText = findViewById(R.id.pengawas_edit_text);
        saveButton = findViewById(R.id.save_button);
        deleteButton = findViewById(R.id.delete_button);

        // Dapatkan ID ujian dari Intent
        ujianId = getIntent().getStringExtra("ujianId");

        // Referensi ke Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("jadwal_ujian").child(ujianId);

        // Muat data ujian ke UI
        loadUjianData();

        // Simpan perubahan saat tombol Save diklik
        saveButton.setOnClickListener(v -> updateUjian());

        // Hapus ujian saat tombol Delete diklik
        deleteButton.setOnClickListener(v -> deleteUjian());
    }
    private void loadUjianData() {
    }
    private void deleteUjian() {
    }
    private void updateUjian() {
        String namaUjian = namaUjianEditText.getText().toString().trim();
        String tanggal = tanggalEditText.getText().toString().trim();
        String waktuMulai = waktuMulaiEditText.getText().toString().trim();
        String waktuSelesai = waktuSelesaiEditText.getText().toString().trim();
        String pengawas = pengawasEditText.getText().toString().trim();

        if (namaUjian.isEmpty() || tanggal.isEmpty() || waktuMulai.isEmpty() || waktuSelesai.isEmpty() || pengawas.isEmpty()) {
            Toast.makeText(this, "Semua bidang harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> ujianMap = new HashMap<>();
        ujianMap.put("nama_ujian", namaUjian);
        ujianMap.put("tanggal", tanggal);
        ujianMap.put("waktu_mulai", waktuMulai);
        ujianMap.put("waktu_selesai", waktuSelesai);
        ujianMap.put("pengawas", pengawas);

        databaseReference.updateChildren(ujianMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Data ujian berhasil diperbarui", Toast.LENGTH_SHORT).show();
                finish(); // Kembali ke aktivitas sebelumnya
            } else {
                Toast.makeText(this, "Gagal memperbarui data", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
