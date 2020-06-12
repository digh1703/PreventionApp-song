package com.example.preventionapp;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

@SuppressWarnings("unused")
public class MainActivity extends AppCompatActivity {

    AppBarConfiguration mAppBarConfiguration;
    androidx.appcompat.widget.Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment2, new MainFragment());
        fragmentTransaction.commit();

        toolbar = (androidx.appcompat.widget.Toolbar)findViewById(R.id.toolbar);
        setTitle("");
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        //네비게이션 뷰 자체를 컨트롤할때
        navigationView = findViewById(R.id.nav_view);

        //네비게이션 뷰 내의 버튼 여기서 생성
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_2, R.id.nav_3, R.id.nav_4, R.id.nav_5 , R.id.nav_6)
                .setDrawerLayout(drawer)
                .build();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.toolbar_menu);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.open,
                R.string.closed);
        drawer.addDrawerListener(actionBarDrawerToggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                switch (menuItem.getItemId()) {
                    case R.id.nav_0:
                        fragmentTransaction.replace(R.id.fragment2, new MainFragment());
                        fragmentTransaction.commit();
                        return true;
                    case R.id.nav_1:
                        fragmentTransaction.replace(R.id.fragment2, new CrimeMapMenu());
                        fragmentTransaction.commit();
                        return true;
                    case R.id.nav_2:
                        fragmentTransaction.replace(R.id.fragment2, new CallFragment());
                        fragmentTransaction.commit();
                        return true;
                    case R.id.nav_3:
                        fragmentTransaction.replace(R.id.fragment2, new HospitalMapFragment());
                        fragmentTransaction.commit();
                        return true;
                    case R.id.nav_4:
                        fragmentTransaction.replace(R.id.fragment2, new InfoFragment());
                        fragmentTransaction.commit();
                        return true;
                    case R.id.nav_5:
                        fragmentTransaction.replace(R.id.fragment2, new BoardFragment());
                        fragmentTransaction.commit();
                        return true;
                    case R.id.nav_6:
                        fragmentTransaction.replace(R.id.fragment2, new NewsActivity());
                        fragmentTransaction.commit();
                        return true;
                }
                return true;
            }
        });

        //if(FirebaseAuth.getInstance().getCurrentUser()==null){
       //     startSignUpActivity();
       // }

        TedPermission.with(this)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        Toast.makeText(MainActivity.this, "위치 정보 권한이 허용되었습니다.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {

                    }
                })
                .setDeniedMessage("위치 정보 권한을 허용하여야 앱을 사용할 수 있습니다.")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .check();
    }


    //activity_main id.main_toolbar 에 menu , main_toolbar.xml을 더하는 과정
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_toolbar, menu);
        //xml activity_main 가 menu 객체로 변환
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.board_toolbar_search:
                Toast.makeText(getApplicationContext(), "search", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.board_toolbar_option:
                Toast.makeText(getApplicationContext(), "option", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.loginButton:
                    FirebaseAuth.getInstance().signOut();
                    startSignUpActivity();
                    break;
            }

        }
    };

    private void startSignUpActivity(){
        Intent intent=new Intent(this,SignupActivity.class);
        startActivity(intent);
    }

}
