package com.example.attendancesystem.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.attendancesystem.R;

public class MainActivity extends AppCompatActivity {
    private LinearLayout adminCard,teacherCard,studentCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      LinearLayout adminCard=findViewById(R.id.adminCard);
        adminCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AdminLoginActivity.class);
                startActivity(intent);
                finish();

            }
        });   teacherCard=findViewById(R.id.teacherCard);
        teacherCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,TeacherLoginActivity.class);
                startActivity(intent);
                finish();

            }
        });   studentCard=findViewById(R.id.studentCard);
        studentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,StudentLoginActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}
