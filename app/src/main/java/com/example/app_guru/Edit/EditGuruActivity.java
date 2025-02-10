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
import com.google.firebase.database.DatabaseReference;

public class EditGuruActivity extends AppCompatActivity {
    private EditText editTextName, editTextClass, editTextEmail, editTextDateOfBirth, editTextPlaceOfBirth;
    private Button btnSave, btnDelete;
    private DatabaseReference guruRef;
    private FirebaseAuth auth;
    private String guruKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_guru);

        editTextName = findViewById(R.id.editTextName);
        editTextClass = findViewById(R.id.editTextClass);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextDateOfBirth = findViewById(R.id.editTextDateOfBirth);
        editTextPlaceOfBirth = findViewById(R.id.editTextPlaceOfBirth);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        auth = FirebaseAuth.getInstance();

        guruKey = getIntent().getStringExtra("guruKey");

        if (guruKey == null) {
            Toast.makeText(this, "Key murid tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
    }
}