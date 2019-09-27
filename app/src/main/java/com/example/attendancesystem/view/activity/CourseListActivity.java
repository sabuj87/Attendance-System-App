package com.example.attendancesystem.view.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.attendancesystem.R;
import com.example.attendancesystem.adapter.CourseListAdapter;
import com.example.attendancesystem.model.Course;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CourseListActivity extends AppCompatActivity {
   private FloatingActionButton addCourseButton;
   private RecyclerView courseRv;
   private String intented_dept,intented_Shift;
   private List<Course>  courseList=new ArrayList<>();
   private DatabaseReference courseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Intent intent=getIntent();
        intented_dept=intent.getStringExtra("CDEPT");
        intented_Shift=intent.getStringExtra("CSHIFT");
        addCourseButton=findViewById(R.id.addCBtn);
        courseRv=findViewById(R.id.CourseListRV);

        courseRef= FirebaseDatabase.getInstance().getReference().child("Department").child(intented_dept).child("Course").child(intented_Shift);
        courseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                courseList.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        if(dataSnapshot1.hasChildren()){
                            Course course=dataSnapshot1.getValue(Course.class);
                            courseList.add(course);
                        }
                    }
                    CourseListAdapter courseListAdapter=new CourseListAdapter(CourseListActivity.this,courseList);
                    courseRv.setLayoutManager(new LinearLayoutManager(CourseListActivity.this));
                    courseListAdapter.notifyDataSetChanged();
                    courseRv.setAdapter(courseListAdapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(CourseListActivity.this,AddCourseActivity.class);
                intent1.putExtra("CDEPT",intented_dept);
                intent1.putExtra("CSHIFT",intented_Shift);
                startActivity(intent1);
            }
        });







    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
