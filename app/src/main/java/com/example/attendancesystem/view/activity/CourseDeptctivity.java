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
import com.example.attendancesystem.model.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CourseDeptctivity extends AppCompatActivity {

    private Spinner cDeptSp,cShiftSp;
    private Button cNextBtn;
    private DatabaseReference cDeptRef;
    private List<String> cDeptList =new ArrayList<>();
    private String cSelectedDept,cSelectedShift;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_deptctivity);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        cDeptSp =findViewById(R.id.CdeptSp);
        cShiftSp=findViewById(R.id.CShiftSp);
        cNextBtn =findViewById(R.id.cNextBtn);
        cDeptRef = FirebaseDatabase.getInstance().getReference().child("Department");
        cDeptRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cDeptList.clear();
                cDeptList.add("Select Department");
                if(dataSnapshot.exists()){
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        if(dataSnapshot1.hasChildren()){
                            String key=dataSnapshot1.getKey();
                            cDeptList.add(key);
                        }
                    }
                    ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(CourseDeptctivity.this,android.R.layout.simple_list_item_1, cDeptList);
                    cDeptSp.setAdapter(arrayAdapter);
                    cDeptSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            cSelectedDept =parent.getItemAtPosition(position).toString();

                           ArrayAdapter<String> arrayAdapter1=new ArrayAdapter<>(CourseDeptctivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.shift));
                           cShiftSp.setAdapter(arrayAdapter1);
                           cShiftSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                               @Override
                               public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                   cSelectedShift=parent.getItemAtPosition(position).toString();

                               }

                               @Override
                               public void onNothingSelected(AdapterView<?> parent) {

                               }
                           });

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

        cNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cSelectedDept !=null && !cSelectedDept.equals("Select Department") && cSelectedShift !=null && !cSelectedShift.equals("Select shift")){
                    Intent intent=new Intent(CourseDeptctivity.this,CourseListActivity.class);
                    intent.putExtra("CDEPT", cSelectedDept);
                    intent.putExtra("CSHIFT",cSelectedShift);
                    startActivity(intent);
                }
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
