package com.anas.project2;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.anas.project2.Fragments.AttendanceTrackerFragment;
import com.anas.project2.Fragments.HomeFragment;
import com.anas.project2.Fragments.HowtouseFragment;
import com.anas.project2.Fragments.ProfileFragment;
import com.anas.project2.Fragments.QRFragment;
import com.anas.project2.Fragments.SettingsFragment;
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
                    frag = new AttendanceTrackerFragment();
                    Toast.makeText(this, "AT Frag", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.row_qr:
                    frag = new QRFragment();
                    Toast.makeText(this, "QR Frag", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.row_profile:
                    frag = new ProfileFragment();
                    Toast.makeText(this, "Profile frag", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.row_settings:
                    frag = new SettingsFragment();
                    Toast.makeText(this, "Settings Frag", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.row_howtouse:
                    frag = new HowtouseFragment();
                    Toast.makeText(this, "Howtouse Frag", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.row_sugg:
                    Suggestion();
                    Toast.makeText(this, "Suggestion Frag", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.row_share:
                    Share();
                    Toast.makeText(this, "Share Frag", Toast.LENGTH_SHORT).show();
                    break;
            }
            layDL.closeDrawer(GravityCompat.START);

            if (frag != null) {
                loadFrag(frag);
            }

            return true;
        });
    }

    private void Share() {

        Intent iShare=new Intent(Intent.ACTION_SEND);
        iShare.setType("text/plain");
        iShare.putExtra(Intent.EXTRA_TEXT,"Download Attendify App, https://github.com/anas2002-developer/project2");

        //for mutliple app chosing to open
        startActivity(Intent.createChooser(iShare,"Share via"));

    }

    private void Suggestion() {
        Intent iEmail=new Intent(Intent.ACTION_SEND);

        //for differentiating email from share and other types
        iEmail.setType("message/rfc822");
        iEmail.putExtra(Intent.EXTRA_EMAIL,new String[]{"anas4112002@gmail.com","shrutisaini1415@gmail.com","sania.hasan2111@gmail.com"});
        iEmail.putExtra(Intent.EXTRA_SUBJECT,"Bug Fix");
        iEmail.putExtra(Intent.EXTRA_TEXT,"Please, Fix the bugs");
        startActivity(Intent.createChooser(iEmail,"Email via"));
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