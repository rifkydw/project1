package com.example.app_guru;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TambahUjian extends AppCompatActivity {

    private EditText ujianNameEditText;
    private EditText pengawasEditText;
    private DatePicker tanggalPicker;
    private EditText waktuMulaiEditText; // Untuk waktu mulai
    private EditText waktuSelesaiEditText; // Untuk waktu selesai
    private Spinner soalSpinner;
    private Button saveButton;

    private DatabaseReference databaseReference;
    private List<String> soalOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tambah_ujian);

        // Inisialisasi komponen UI
        ujianNameEditText = findViewById(R.id.ujian_name_edit_text);
        pengawasEditText = findViewById(R.id.pengawas_edit_text);
        tanggalPicker = findViewById(R.id.tanggal_picker);
        waktuMulaiEditText = findViewById(R.id.waktu_mulai_edit_text);
        waktuSelesaiEditText = findViewById(R.id.waktu_selesai_edit_text);
        soalSpinner = findViewById(R.id.soal_spinner);
        saveButton = findViewById(R.id.save_button);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("jadwal_ujian");

        // Memuat daftar soal untuk spinner
        loadSoalOptions();

        // Simpan jadwal ujian ke Firebase
        saveButton.setOnClickListener(v -> saveJadwalUjian());
    }

    private void loadSoalOptions() {
        soalOptions = new ArrayList<>();
        DatabaseReference soalRef = databaseReference.getRoot().child("kelas");

        soalRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot kelasSnapshot : dataSnapshot.getChildren()) {
                    soalOptions.add(kelasSnapshot.getKey()); // Nama kelas sebagai opsi soal
                }

                ArrayAdapter<String> soalAdapter = new ArrayAdapter<>(
                        TambahUjian.this,
                        android.R.layout.simple_spinner_item,
                        soalOptions
                );
                soalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                soalSpinner.setAdapter(soalAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(TambahUjian.this, "Gagal memuat opsi soal: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveJadwalUjian() {
        String ujianName = ujianNameEditText.getText().toString().trim();
        String pengawas = pengawasEditText.getText().toString().trim();

        if (ujianName.isEmpty() || pengawas.isEmpty()) {
            Toast.makeText(this, "Nama ujian dan pengawas harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ambil tanggal ujian
        int day = tanggalPicker.getDayOfMonth();
        int month = tanggalPicker.getMonth() + 1;
        int year = tanggalPicker.getYear();

        // Ambil waktu mulai dan waktu selesai dari EditText
        String waktuMulai = waktuMulaiEditText.getText().toString().trim();
        String waktuSelesai = waktuSelesaiEditText.getText().toString().trim();

        String soalDipilih = soalSpinner.getSelectedItem().toString();

        // Buat objek ujian dengan waktu mulai dan waktu selesai
        Map<String, Object> ujianMap = new HashMap<>();
        ujianMap.put("nama_ujian", ujianName);
        ujianMap.put("pengawas", pengawas);
        ujianMap.put("tanggal", day + "/" + month + "/" + year);
        ujianMap.put("waktu_mulai", waktuMulai);
        ujianMap.put("waktu_selesai", waktuSelesai);
        ujianMap.put("soal_dipilih", soalDipilih);

        // Simpan ke Firebase dengan ID unik
        String ujianId = databaseReference.push().getKey(); // ID unik untuk ujian
        databaseReference.child(ujianId).setValue(ujianMap);

        Toast.makeText(this, "Jadwal ujian berhasil disimpan", Toast.LENGTH_SHORT).show();
        finish(); // Kembali ke aktivitas sebelumnya setelah menyimpan

    }
}