package com.example.app_guru;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        loadFragment(new HomeFragment());

        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        Fragment fragment = null;

        if (itemId == R.id.itm1) {
            fragment = new HomeFragment();
        } else if (itemId == R.id.itm2) {
            fragment = new ();
        } else if (itemId == R.id.itm3) {
            fragment = new ();
        } else if (itemId == R.id.itm4) {
            fragment = new ();
        } else if (itemId == R.id.itm5) {
            fragment = new ();
        } else {
            // Penanganan jika itemId tidak cocok dengan yang diharapkan
        }

        return loadFragment(fragment);
    }


    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.FL1, fragment).commit();
            return true;
        }
        return false;
    }
}