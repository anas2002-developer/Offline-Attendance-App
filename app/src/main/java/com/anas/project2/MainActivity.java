package com.anas.project2;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.anas.project2.Fragments.HomeFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {


    DrawerLayout layDL;
    NavigationView vNV;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        layDL = findViewById(R.id.layDL);
        vNV = findViewById(R.id.vNV);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, layDL, toolbar, R.string.open_drawer, R.string.close_drawer);

        layDL.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            loadFrag(new HomeFragment());
            vNV.setCheckedItem(R.id.row_home);
        }
        NavClick();





    }

    private void NavClick() {
        vNV.setNavigationItemSelectedListener(item -> {
            Fragment frag = null;
            switch (item.getItemId()) {

                case R.id.row_home:
                    frag = new HomeFragment();
                    Toast.makeText(this, "Home Frag", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.row_attendance_track:
                    Toast.makeText(this, "Nav item", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.row_qr:
                    Toast.makeText(this, "Nav item", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.row_profile:
                    Toast.makeText(this, "Nav item", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.row_settings:
                    Toast.makeText(this, "Nav item", Toast.LENGTH_SHORT).show();
                    break;
            }
            layDL.closeDrawer(GravityCompat.START);

            if (frag != null) {
                loadFrag(frag);
            }

            return true;
        });
    }

    private void loadFrag(Fragment frag) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layFL, frag)
                .commit();
    }
    @Override
    public void onBackPressed() {
        Fragment currFrag = getSupportFragmentManager().findFragmentById(R.id.layFL);
        if (layDL.isDrawerOpen(GravityCompat.START)){
            layDL.closeDrawer(GravityCompat.START);
        }
        else if(currFrag!=new HomeFragment()) {
            loadFrag(new HomeFragment());
            vNV.setCheckedItem(R.id.row_home);
        }
        else {
            super.onBackPressed();
        }
    }

}