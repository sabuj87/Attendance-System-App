package com.example.attendancesystem.view.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.attendancesystem.R;
import com.example.attendancesystem.model.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import xyz.hasnat.sweettoast.SweetToast;

public class AttendaceStudentActivity extends AppCompatActivity {
    private  String intentded_course;
   private DatabaseReference studentRef,deptref,batchRef;
   private String dept,batch;
   private List<Student> studentList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendace_student);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent=getIntent();
        intentded_course=intent.getStringExtra("SC");

        studentRef= FirebaseDatabase.getInstance().getReference().child("Department");
        studentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                studentList.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        dept=dataSnapshot1.getKey();
                        deptref=studentRef.child(dept).child("Student");
                        deptref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                   for(DataSnapshot ds1:dataSnapshot.getChildren()){
                                       for (DataSnapshot ds2:ds1.child("allstudent").getChildren()){
                                           Student student=ds2.getValue(Student.class);
                                           if(student.getCourse().contains(intentded_course)){
                                               studentList.add(student);
                                           }




                                       }
                                   }
                                   SweetToast.success(getApplicationContext(),Integer.toString(studentList.size()));
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
