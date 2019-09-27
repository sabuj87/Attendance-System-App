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
import android.widget.EditText;
import android.widget.Spinner;

import com.example.attendancesystem.R;
import com.example.attendancesystem.model.Course;
import com.example.attendancesystem.model.Teacher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import xyz.hasnat.sweettoast.SweetToast;

public class AddCourseActivity extends AppCompatActivity {
    private Spinner courseBatchSp;
    private Spinner courseTeacherSp,courseTitleSP;
    private EditText courseCodeET;
    private DatabaseReference teacherListRef,batchListRef,courseRef,courseTitleRef,courseCodeRef;
    private List<String> teacherList,batchList,teacherIDList,CourseTitleList,courseCodeList;
    private String intentedDep,intentedShift;
    private Button addCourseBtn;
    private String selected_batch,selected_teacher,selected_teacherID,selected_course_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        courseBatchSp=findViewById(R.id.courseBatchSp);
        courseTeacherSp=findViewById(R.id.courseTeacherSp);
        courseTitleSP=findViewById(R.id.courseTitleSp);
        courseCodeET=findViewById(R.id.courseCode);
        addCourseBtn=findViewById(R.id.addCourseBtn);

        Intent intent=getIntent();
        intentedDep=intent.getStringExtra("CDEPT");
        intentedShift=intent.getStringExtra("CSHIFT");
        //SweetToast.success(getApplicationContext(),intentedShift);
        teacherList=new ArrayList<>();
        batchList=new ArrayList<>();
        teacherIDList=new ArrayList<>();
        CourseTitleList=new ArrayList<>();
        courseCodeList=new ArrayList<>();

        courseRef=FirebaseDatabase.getInstance().getReference().child("Department").child(intentedDep).child("Course").child(intentedShift);
        courseTitleRef=FirebaseDatabase.getInstance().getReference().child("Department").child(intentedDep).child("Courselist");
        courseTitleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CourseTitleList.clear();
                CourseTitleList.add(0,"Select course");
                courseCodeList.add(0,"Course Code");
                if (dataSnapshot.exists()){


                    for (DataSnapshot ds1:dataSnapshot.getChildren()){
                        String key=ds1.getKey();
                        String key1=ds1.getValue().toString();
                        CourseTitleList.add(key);
                        courseCodeList.add(key1);
                    }

                    ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(AddCourseActivity.this,android.R.layout.simple_list_item_1,CourseTitleList);

                    courseTitleSP.setAdapter(arrayAdapter);
                    courseTitleSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selected_course_title=parent.getItemAtPosition(position).toString();
                            courseCodeET.setText(courseCodeList.get(position));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        teacherListRef= FirebaseDatabase.getInstance().getReference().child("Department").child(intentedDep).child("Teacher").child(intentedShift);
        teacherListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                teacherList.clear();
                teacherIDList.clear();
                teacherList.add(0,"Select teacher");
                teacherIDList.add(0,"id");

                if(dataSnapshot.exists()){

                        for(DataSnapshot dataSnapshot2:dataSnapshot.getChildren()){
                            if(dataSnapshot2.hasChildren()){
                                Teacher teacher=dataSnapshot2.getValue(Teacher.class);
                                String name=teacher.getName();
                                String id=teacher.getId();
                                teacherList.add(name);
                                teacherIDList.add(id);
                            }
                        }


                    ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(AddCourseActivity.this,android.R.layout.simple_list_item_1,teacherList);
                    courseTeacherSp.setAdapter(arrayAdapter);
                    courseTeacherSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selected_teacher=parent.getItemAtPosition(position).toString();
                            selected_teacherID=teacherIDList.get(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        batchListRef=FirebaseDatabase.getInstance().getReference().child("Department").child(intentedDep).child("Student");
        batchListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                batchList.clear();
                batchList.add("Select batch");

                if(dataSnapshot.exists()){
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        if(dataSnapshot1.hasChildren()){
                             String key=dataSnapshot1.getKey();
                            batchList.add(key);
                        }
                    }
                    ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(AddCourseActivity.this,android.R.layout.simple_list_item_1,batchList);
                    courseBatchSp.setAdapter(arrayAdapter);
                    courseBatchSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selected_batch=parent.getItemAtPosition(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTeacher();
            }
        });


    }

    private void addTeacher(){

        String course1=courseCodeET.getText().toString();

        if(selected_course_title.equals("Select course")){
          SweetToast.error(getApplicationContext(),"Select course");
        }else if(course1.isEmpty()){
            courseCodeET.setError("Give course code");
        }else if(selected_batch.equals("Select batch")){
            SweetToast.warning(getApplicationContext(),"Select batch");
        }else if(selected_teacher.equals("Select teacher")) {
            SweetToast.warning(getApplicationContext(), "Select teacher");
        }else if(selected_teacherID.equals("id")){

        }
        else {
            String key=courseRef.push().getKey();
            Course course=new Course("",selected_course_title,course1,selected_teacher,selected_teacherID,selected_batch);
            courseRef.child(key).setValue(course).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        SweetToast.success(getApplicationContext(),"Course added successfully");
                    }
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
