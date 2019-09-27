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
import com.example.attendancesystem.view.fragment.StudentFragment;
import com.example.attendancesystem.view.fragment.TeacherFragment;

public class StudentActivity extends AppCompatActivity {

    private NavigationView student_nav_view;
    private DrawerLayout student_nav_drawer;
    private Toolbar student_toolbar;
    private String intententId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);


        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.student_fragment_container,new StudentFragment())
                   .commit();
        }

        student_toolbar =findViewById(R.id.student_toolbar_id);
        setSupportActionBar(student_toolbar);

        student_nav_drawer =findViewById(R.id.student_nav_drawer_id);
        student_nav_view =findViewById(R.id.student_nav_view_id);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, student_nav_drawer, student_toolbar,R.string.nav_drawer_open, R.string.nav_drawer_close);

        student_nav_drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        student_nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_item_logout:

                        new SaveUser().Student_saveData(getApplicationContext(),false);
                        Intent intent=new Intent(StudentActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();

                }
                return true;
            }
        });


    }

}
