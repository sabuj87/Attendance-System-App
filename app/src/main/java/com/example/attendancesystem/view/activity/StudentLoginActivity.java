package com.example.attendancesystem.view.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.attendancesystem.R;
import com.example.attendancesystem.model.Student;
import com.example.attendancesystem.storage.SaveUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import xyz.hasnat.sweettoast.SweetToast;

public class StudentLoginActivity extends AppCompatActivity {
    private EditText studentIDET,studentPassET;
    private Button studentLoginBtn;
    private DatabaseReference studentRef;
    private List<Student> studentList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        studentIDET=findViewById(R.id.studentLoginID);
        studentPassET=findViewById(R.id.studentLoginpass);
        studentLoginBtn=findViewById(R.id.studentLoginBtn);
        studentLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentLogIn();
            }
        });


    }

    private void studentLogIn() {

        final String  id=studentIDET.getText().toString();
        final String  pass=studentPassET.getText().toString();
        if(id.isEmpty()){
            studentIDET.setError("Enter Student ID");
            studentIDET.requestFocus();
        }else if(pass.isEmpty()){
            studentPassET.setError("Enter student pass");
            studentIDET.requestFocus();
        }else {
            studentRef= FirebaseDatabase.getInstance().getReference().child("Department");
            studentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    studentList.clear();

                    if(dataSnapshot.exists()){
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            String dept=dataSnapshot1.getKey();
                            DatabaseReference deptRef=studentRef.child(dept).child("Student");
                            deptRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                                    for(DataSnapshot ds1:dataSnapshot.getChildren()){
                                        for (DataSnapshot ds2:ds1.getChildren()){
                                            for (DataSnapshot ds3:ds2.getChildren()){
                                                for(DataSnapshot ds4:ds3.getChildren()){
                                                    if(ds4.hasChildren()){
                                                        Student student=ds4.getValue(Student.class);
                                                        if(student.getId().equals(id)){
                                                            new SaveUser().saveStudent(getApplicationContext(),student);
                                                            studentList.add(student);
                                                            if(studentList.get(0).getId().equals(id) && studentList.get(0).getPassword().equals(pass)){
                                                                SweetToast.success(getApplicationContext(),"Login Successfully");
                                                                new SaveUser().Student_saveData(getApplicationContext(),true);
                                                                Intent intent=new Intent(StudentLoginActivity.this,StudentActivity.class);
                                                                startActivity(intent);
                                                                finish();
                                                            }
                                                            else {
                                                                SweetToast.error(getApplicationContext(),"You Entered wrong Id or password");
                                                            }


                                                        }

                                                    }
                                                }


                                            }
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



    }
}
