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
import com.example.attendancesystem.model.Teacher;
import com.example.attendancesystem.storage.SaveUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import xyz.hasnat.sweettoast.SweetToast;

public class SelectedCourseActivity extends AppCompatActivity {
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
                   Intent intent=new Intent(SelectedCourseActivity.this,DatePickerActivity.class);
                   intent.putExtra("SC",selected_course);
                   startActivity(intent);
               }

           }
       });


    }

    private void fetchCourse(){
        CourseRef = FirebaseDatabase.getInstance().getReference().child("Department");
        CourseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                courseList.clear();
                course_namelist.clear();
                course_namelist.add(0,"Select course");
                if(dataSnapshot.exists()){
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        dept=dataSnapshot1.getKey();
                        Query deptref= CourseRef.child(dept).child("Course").child(intendedShift).orderByChild("teacher_id").equalTo(intendedId);
                        deptref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    for(DataSnapshot dataSnapshot2:dataSnapshot.getChildren()){
                                        if(dataSnapshot2.hasChildren()){
                                            Course course=dataSnapshot2.getValue(Course.class);
                                            String name=course.getCourse_name();
                                            course_namelist.add(name);

                                        }
                                        ArrayAdapter<String> adapter=new ArrayAdapter<>(SelectedCourseActivity.this,android.R.layout.simple_list_item_1,course_namelist);
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
