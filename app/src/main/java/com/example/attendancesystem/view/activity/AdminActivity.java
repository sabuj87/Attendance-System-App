package com.example.attendancesystem.view.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.attendancesystem.R;
import com.example.attendancesystem.storage.SaveUser;
import com.example.attendancesystem.view.fragment.AdminHomeFragment;

public class AdminActivity extends AppCompatActivity {
    private NavigationView admin_nav_view;
    private DrawerLayout admin_nav_drawer;
    private Toolbar admin_toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        admin_toolbar=findViewById(R.id.admin_toolbar_id);
        setSupportActionBar(admin_toolbar);

        admin_nav_drawer=findViewById(R.id.admin_nav_drawer_id);
        admin_nav_view=findViewById(R.id.admin_nav_view_id);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, admin_nav_drawer, admin_toolbar,R.string.nav_drawer_open, R.string.nav_drawer_close);

        admin_nav_drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container,new AdminHomeFragment()).commit();
        }
        admin_nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_item_logout:
                        Intent intent=new Intent(AdminActivity.this,MainActivity.class);
                        SaveUser saveUser=new SaveUser();
                        saveUser.admin_saveData(AdminActivity.this,false);
                        startActivity(intent);
                        finish();

                }
                return true;
            }
        });


    }
}
