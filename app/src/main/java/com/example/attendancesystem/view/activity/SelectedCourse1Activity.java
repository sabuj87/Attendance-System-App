package com.example.attendancesystem.view.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.attendancesystem.R;
import com.example.attendancesystem.model.Course;
import com.example.attendancesystem.storage.SaveUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectedCourse1Activity extends AppCompatActivity {
    private Spinner selectCourseSp;
    private Button nextBtn;
    private String intendedId,intendedShift;
    private List<Course> courseList=new ArrayList<>();
    private List<String> course_namelist=new ArrayList<>();
    private DatabaseReference CourseRef;
    private String dept;
    private String selected_course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_course);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        intendedId=new SaveUser().teacher_IDloadData(getApplicationContext());
        selectCourseSp=findViewById(R.id.TtCourseSp);
        nextBtn=findViewById(R.id.tsNextBtn);

        intendedShift=new SaveUser().teacher_ShiftLoadData(getApplicationContext());
        //  SweetToast.success(getApplicationContext(),intendedShift);


        fetchCourse();
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!selected_course.equals("Select course")){
                    new SaveUser().teacher_CourseSaveData(getApplicationContext(),selected_course);
                    Intent intent=new Intent(SelectedCourse1Activity.this,ViewAttendenceActivity.class);
                    intent.putExtra("SC",selected_course);
                    startActivity(intent);
                }


            }
        });


    }

    private void fetchCourse(){

        DatabaseReference deptref= FirebaseDatabase.getInstance().getReference().child("Department").child(new SaveUser().getTeacher(SelectedCourse1Activity.this).getDepartment()).child("Course").child(intendedShift);
        deptref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                courseList.clear();
                course_namelist.clear();
                course_namelist.add(0,"Select course");
                if(dataSnapshot.exists()){
                    for(DataSnapshot dataSnapshot2:dataSnapshot.getChildren()){
                        if(dataSnapshot2.hasChildren()){
                            Course course=dataSnapshot2.getValue(Course.class);
                            if(course.getTeacher_id().equals(intendedId)){
                                String name=course.getCourse_name();
                                course_namelist.add(name);
                            }


                        }
                        ArrayAdapter<String> adapter=new ArrayAdapter<>(SelectedCourse1Activity.this,android.R.layout.simple_list_item_1,course_namelist);
                        selectCourseSp.setAdapter(adapter);
                        selectCourseSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                selected_course=parent.getItemAtPosition(position).toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    }



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
