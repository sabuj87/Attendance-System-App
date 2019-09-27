package com.example.attendancesystem.view.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.attendancesystem.R;
import com.example.attendancesystem.model.Teacher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

import xyz.hasnat.sweettoast.SweetToast;

public class AddTeacherActivity extends AppCompatActivity {
    private Toolbar addTeacherToolbar;
    private EditText teacherNameEt,teacherEmailEt,teacherPhoneEt,teacherAddressEt,teacherIdEt;
    private Spinner teacherDesignationSp;
    private String[] desigList;
    private String selectedDesig;
    private Button addTeacherBtn;
    private String intendedDept,intentedShift;
    private DatabaseReference teacherRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);
        addTeacherToolbar=findViewById(R.id.addTeacherToolbar);
        setSupportActionBar(addTeacherToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
         Intent  intent=getIntent();
         intendedDept=intent.getStringExtra("TDEPT");
         intentedShift=intent.getStringExtra("TSHIFT");

        teacherNameEt=findViewById(R.id.addTeacherName);
        teacherEmailEt=findViewById(R.id.addTeacherEmail);
        teacherAddressEt=findViewById(R.id.addTeacherAddress);
        teacherPhoneEt=findViewById(R.id.addTeacherPhone);
        teacherIdEt=findViewById(R.id.addTeacherId);
        addTeacherBtn=findViewById(R.id.addTbtn);
        teacherDesignationSp=findViewById(R.id.teacherDesignationSp);

        desigList=getResources().getStringArray(R.array.desig);

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(AddTeacherActivity.this,android.R.layout.simple_list_item_1,desigList);
        teacherDesignationSp.setAdapter(arrayAdapter);
        teacherDesignationSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDesig=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTeacher();
            }
        });


    }


    private void addTeacher(){
        String name=teacherNameEt.getText().toString();
        String email=teacherEmailEt.getText().toString();
        String address=teacherAddressEt.getText().toString();
        String phone=teacherPhoneEt.getText().toString();
        String ID=teacherIdEt.getText().toString();

        if(name.isEmpty()){
            teacherNameEt.setError("Enter teacher name");
            teacherNameEt.requestFocus();
        }else  if(email.isEmpty()){
            teacherEmailEt.setError("Enter Email");
            teacherEmailEt.requestFocus();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            teacherEmailEt.setError("Enter Email");
            teacherEmailEt.requestFocus();
        }else if(address.isEmpty()){
            teacherAddressEt.setError("Enter address");
            teacherAddressEt.requestFocus();
        }else if(phone.isEmpty()){
            teacherPhoneEt.setError("Enter phone number");
            teacherPhoneEt.requestFocus();
        }else if(selectedDesig.isEmpty() && selectedDesig.equals("Select Designation")){
            SweetToast.warning(getApplicationContext(),"Select Designation");
        }else if(ID.isEmpty()){
            teacherIdEt.setError("Enter ID");
            teacherIdEt.requestFocus();
        }else {

            teacherRef= FirebaseDatabase.getInstance().getReference().child("Department").child(intendedDept).child("Teacher").child(intentedShift);
            String key=teacherRef.push().getKey();

            Teacher teacher=new Teacher(ID,name,intendedDept,selectedDesig,"",phone,email,"","",address,"","1234",intentedShift);
            teacherRef.child(key).setValue(teacher).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        SweetToast.success(getApplicationContext(),"Teacher data successfully");
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
