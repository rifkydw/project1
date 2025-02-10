package com.example.app_guru.Edit;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditMuridActivity extends AppCompatActivity {

    private EditText editTextName, editTextClass, editTextEmail, editTextDateOfBirth, editTextPlaceOfBirth;
    private Button btnSave, btnDelete;
    private DatabaseReference muridRef;
    private FirebaseAuth auth;
    private String muridKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_murid);
        // Inisialisasi komponen
        editTextName = findViewById(R.id.editTextName);
        editTextClass = findViewById(R.id.editTextClass);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextDateOfBirth = findViewById(R.id.editTextDateOfBirth);
        editTextPlaceOfBirth = findViewById(R.id.editTextPlaceOfBirth);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        auth = FirebaseAuth.getInstance();

        // Mendapatkan key unik murid
        muridKey = getIntent().getStringExtra("muridKey");

        if (muridKey == null) {
            Toast.makeText(this, "Key murid tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        muridRef = FirebaseDatabase.getInstance().getReference("Murid").child(muridKey);

        // Mengambil data murid berdasarkan key unik
        muridRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Menampilkan data di EditText
                    String name = dataSnapshot.child("nama").getValue(String.class);
                    String kelas = dataSnapshot.child("Kelas").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String jenisKelamin = dataSnapshot.child("Jenis_Kelamin").getValue(String.class);
                    String tanggalLahir = dataSnapshot.child("Tanggal_Lahir").getValue(String.class);
                    String tempatLahir = dataSnapshot.child("Tempat_Lahir").getValue(String.class);

                    editTextName.setText(name != null ? name : "");
                    editTextClass.setText(kelas != null ? kelas : "");
                    editTextEmail.setText(email != null ? email : "");
                    editTextDateOfBirth.setText(tanggalLahir != null ? tanggalLahir : "");
                    editTextPlaceOfBirth.setText(tempatLahir != null ? tempatLahir : "");
                } else {
                    Toast.makeText(EditMuridActivity.this, "Data murid tidak ditemukan", Toast.LENGTH_SHORT).show();
                    finish(); // Kembali ke aktivitas sebelumnya jika data tidak ditemukan
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(EditMuridActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Menyimpan perubahan saat tombol "Simpan" diklik
        btnSave.setOnClickListener(v -> {
            String newName = editTextName.getText().toString().trim();
            String newClass = editTextClass.getText().toString().trim();
            String newEmail = editTextEmail.getText().toString().trim();
            String newDateOfBirth = editTextDateOfBirth.getText().toString().trim();
            String newPlaceOfBirth = editTextPlaceOfBirth.getText().toString().trim();

            if (newName.isEmpty() || newClass.isEmpty() || newEmail.isEmpty() ||  newDateOfBirth.isEmpty() || newPlaceOfBirth.isEmpty()) {
                Toast.makeText(EditMuridActivity.this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
                return;
            }

            // Memperbarui data murid berdasarkan key unik
            muridRef.child("nama").setValue(newName);
            muridRef.child("Kelas").setValue(newClass);
            muridRef.child("email").setValue(newEmail);
            muridRef.child("Tanggal_Lahir").setValue(newDateOfBirth);
            muridRef.child("Tempat_Lahir").setValue(newPlaceOfBirth);

            Toast.makeText(EditMuridActivity.this, "Data murid berhasil diperbarui", Toast.LENGTH_SHORT).show();
            finish(); // Kembali ke aktivitas sebelumnya setelah disimpan
        });

        // Menghapus data murid dan akun saat tombol "Hapus" diklik
        btnDelete.setOnClickListener(v -> {
            muridRef.removeValue().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();

                    if (user != null) {
                        user.delete().addOnCompleteListener(deleteTask -> {
                            if (deleteTask.isSuccessful()) {
                                Toast.makeText(EditMuridActivity.this, "Data murid dan akun berhasil dihapus", Toast.LENGTH_SHORT).show();
                                finish(); // Kembali ke aktivitas sebelumnya setelah dihapus
                            } else {
                                Toast.makeText(EditMuridActivity.this, "Gagal menghapus akun murid", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(EditMuridActivity.this, "Akun murid tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditMuridActivity.this, "Gagal menghapus data murid", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}

