package com.example.attendancesystem.view.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.attendancesystem.R;
import com.example.attendancesystem.storage.SaveUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import xyz.hasnat.sweettoast.SweetToast;

public class ShowAttendanceActivity extends AppCompatActivity {
   private Spinner showAttendanceSp;
   private Button showAttendanceBtn;
    private String courses;
    private String course_code;
    private List<String> courseList=new ArrayList<>();
    private List<String> course_codeList=new ArrayList<>();
    private List<String> PresentList=new ArrayList<>();
    private List<String> absentList=new ArrayList<>();
    private String selected_course;
    private DatabaseReference presentRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_attendance);
        showAttendanceSp=findViewById(R.id.ShowAttendanceSp);
        showAttendanceBtn=findViewById(R.id.showAttendanceBtn);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SaveUser saveUser=new SaveUser();
        courses=saveUser.getStudent(getApplicationContext()).getCourse();
        course_code=saveUser.getStudent(getApplicationContext()).getCourse_code();

        courseList= Arrays.asList(courses.split(","));
        course_codeList= Arrays.asList(course_code.split(","));


        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(ShowAttendanceActivity.this,android.R.layout.simple_list_item_1,courseList);
        showAttendanceSp.setAdapter(arrayAdapter);
        showAttendanceSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_course=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        showAttendanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected_course!=null){

                    presentRef= FirebaseDatabase.getInstance().getReference().child("Department").child(new SaveUser().getStudent(getApplicationContext()).getDepartment())
                            .child("Attendance").child(new SaveUser().getStudent(getApplicationContext()).getShift()).child(selected_course);

                    presentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            PresentList.clear();
                            absentList.clear();
                            if(dataSnapshot.exists()){
                                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                    for(DataSnapshot dataSnapshot2:dataSnapshot1.child("Present").getChildren()){

                                        String key=dataSnapshot2.getKey();

                                            if(key.equals(new SaveUser().getStudent(getApplicationContext()).getId())){
                                                PresentList.add(key);
                                            }



                                    }

                                    for(DataSnapshot dataSnapshot2:dataSnapshot1.child("Absent").getChildren()){

                                        String key=dataSnapshot2.getValue().toString();
                                        if(key.equals(new SaveUser().getStudent(getApplicationContext()).getId())){
                                            absentList.add(key);

                                        }
                                    }

                                }



                                final AlertDialog dialoga =new AlertDialog.Builder(ShowAttendanceActivity.this).create();
                                View view= LayoutInflater.from(getApplicationContext()).inflate(R.layout.viewatedance,null);

                                TextView presentTV,absentTV,name,id;
                                Button button;

                                presentTV=view.findViewById(R.id.presentStudentTV1);
                                absentTV=view.findViewById(R.id.absentStudentTV1);
                                name=view.findViewById(R.id.vName);
                                id=view.findViewById(R.id.vID);
                                name.setText(selected_course);
                                id.setText(course_codeList.get(courseList.indexOf(selected_course)));
                                button=view.findViewById(R.id.Okbtn);
                                presentTV.setText(Integer.toString(PresentList.size()));
                                absentTV.setText(Integer.toString(absentList.size()));
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialoga.dismiss();
                                    }
                                });


                                dialoga.setView(view);
                                dialoga.setCancelable(true);
                                dialoga.show();





                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



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
