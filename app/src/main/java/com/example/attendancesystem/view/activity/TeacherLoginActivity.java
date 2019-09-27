package com.example.attendancesystem.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.attendancesystem.R;
import com.example.attendancesystem.model.Teacher;
import com.example.attendancesystem.storage.SaveUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import xyz.hasnat.sweettoast.SweetToast;

public class TeacherLoginActivity extends AppCompatActivity {
    public static Activity fa;
    private EditText teacherIdEt,teacherPassword;
    private List<String> teacherIdList=new ArrayList<>();
    private List<String> passwordList=new ArrayList<>();
    private Button teacherLogInBtn;
    private DatabaseReference teacherRef,deptref;
    private String dept,shift,tdept;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fa=this;
        setContentView(R.layout.activity_teacher_login);
        teacherIdEt=findViewById(R.id.teacher_Login_id);
        teacherPassword=findViewById(R.id.teacherLoginPassword);
        teacherLogInBtn=findViewById(R.id.teacherLoginBtn);

        teacherLogInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teacherLogin();
            }
        });

    }

    private void teacherLogin(){
        final String teacherId=teacherIdEt.getText().toString();
        final String tpassword=teacherPassword.getText().toString();
        if (teacherId.isEmpty()){
            teacherIdEt.setError("Enter teacher ID");
            teacherIdEt.requestFocus();
        }else if(tpassword.isEmpty()){
            teacherPassword.setError("Enter Password");
            teacherPassword.requestFocus();
        }else {
          teacherRef= FirebaseDatabase.getInstance().getReference().child("Department");
          teacherRef.addListenerForSingleValueEvent(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  teacherIdList.clear();
                  passwordList.clear();

                  if(dataSnapshot.exists()){
                     for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                         dept=dataSnapshot1.getKey();
                         deptref=teacherRef.child(dept).child("Teacher");
                         deptref.addValueEventListener(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                 if(dataSnapshot.exists()){
                                     for(DataSnapshot dataSnapshot2:dataSnapshot.getChildren()){
                                         for (DataSnapshot dataSnapshot3:dataSnapshot2.getChildren()){
                                             if(dataSnapshot3.hasChildren()){
                                                 Teacher teacher=dataSnapshot3.getValue(Teacher.class);
                                                 if(teacher.getId().equals(teacherId)){
                                                     String id=teacher.getId();
                                                     String password=teacher.getPassword();
                                                     teacherIdList.add(id);
                                                     passwordList.add(password);

                                                     shift=teacher.getShift();
                                                     tdept=teacher.getDepartment();

                                                     SaveUser saveUser=new SaveUser();
                                                     saveUser.saveTeacher(TeacherLoginActivity.this,teacher);

                                                 }

                                             }

                                         }


                                     }

                                     if(teacherIdList.contains(teacherId) && passwordList.contains(tpassword)){


                                         SweetToast.success(getApplicationContext(),"Successfully login");
                                         Intent intent=new Intent(TeacherLoginActivity.this,TeacherActivity.class);
                                         intent.putExtra("TEACHERID",teacherId);

                                         SaveUser saveUser=new SaveUser();
                                         saveUser.teacher_IDsaveData(getApplicationContext(),teacherId);
                                         saveUser.teacher_ShiftSaveData(getApplicationContext(),shift);
                                         saveUser.teacher_saveData(TeacherLoginActivity.this,true);
                                         saveUser.teacher_DeptSaveData(getApplicationContext(),tdept);
                                         startActivity(intent);
                                         finish();

                                     }else {
                                         SweetToast.error(getApplicationContext(),"This id is not register");
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
