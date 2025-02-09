package com.example.app_guru.Add;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_guru.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddDataMuridActivity extends AppCompatActivity {
    private EditText editTextName, editTextEmail, editTextPassword, editTextPlaceOfBirth, editTextDateOfBirth, editTextClass;
    private Button buttonAddUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_data_murid);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPlaceOfBirth = findViewById(R.id.editTextPlaceOfBirth);
        editTextDateOfBirth = findViewById(R.id.editTextDateOfBirth);
        editTextClass = findViewById(R.id.editTextClass);
        buttonAddUser = findViewById(R.id.buttonAddUser);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        buttonAddUser.setOnClickListener(v -> addUser());
    }

    private void addUser() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String placeOfBirth = editTextPlaceOfBirth.getText().toString().trim();
        String dateOfBirth = editTextDateOfBirth.getText().toString().trim();
        String studentClass = editTextClass.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || placeOfBirth.isEmpty() || dateOfBirth.isEmpty() || studentClass.isEmpty()) {
            Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = task.getResult().getUser();

                        if (firebaseUser != null) {
                            Map<String, Object> userData = new HashMap<>();
                            userData.put("nama", name);
                            userData.put("email", email);
                            userData.put("Tempat_Lahir", placeOfBirth);
                            userData.put("Tanggal_Lahir", dateOfBirth);
                            userData.put("Kelas", studentClass);

                            databaseReference.child("Murid").child(firebaseUser.getUid()).setValue(userData)
                                    .addOnCompleteListener(dbTask -> {
                                        if (dbTask.isSuccessful()) {
                                            Toast.makeText(AddDataMuridActivity.this, "Pengguna berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                                            resetInputFields();
                                        } else {
                                            Toast.makeText(AddDataMuridActivity.this, "Gagal menambahkan pengguna: " + dbTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    } else {
                        Toast.makeText(AddDataMuridActivity.this, "Gagal membuat pengguna: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void resetInputFields() {
        editTextName.setText("");
        editTextEmail.setText("");
        editTextPassword.setText("");
        editTextPlaceOfBirth.setText("");
        editTextDateOfBirth.setText("");
        editTextClass.setText("");
    }
}