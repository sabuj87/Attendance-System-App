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
import com.example.attendancesystem.adapter.StudentListAdapter;
import com.example.attendancesystem.model.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentListActivity extends AppCompatActivity {
   private Toolbar studentListToolbar;
   private FloatingActionButton addStudentBtn;
   private DatabaseReference studentListRef;
   private List<Student> studentList=new ArrayList<>();
   private RecyclerView studentListRV;
    private String intentDept;
    private String intentBatch;
    private String intentShift;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        studentListToolbar=findViewById(R.id.studentListToolbar);
        addStudentBtn=findViewById(R.id.addStudentBtn);
        studentListRV=findViewById(R.id.StudentListRV);
        Intent intent=getIntent();
        intentDept= intent.getStringExtra("DEPT");
        intentBatch=intent.getStringExtra("BATCH");
        intentShift=intent.getStringExtra("SHIFT");

        setSupportActionBar(studentListToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        studentListRef=FirebaseDatabase.getInstance().getReference().child("Department").child(intentDept).child("Student").child(intentBatch).child("allstudent").child(intentShift);
        studentListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                studentList.clear();
                if(dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        if (dataSnapshot1.hasChildren()) {
                            Student student = dataSnapshot1.getValue(Student.class);
                            studentList.add(student);
                        }

                        StudentListAdapter studentListAdapter=new StudentListAdapter(StudentListActivity.this,studentList);
                        studentListRV.setLayoutManager(new LinearLayoutManager(StudentListActivity.this));
                        studentListAdapter.notifyDataSetChanged();
                        studentListRV.setAdapter(studentListAdapter);



                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(StudentListActivity.this,AddStudentActivity.class);

                intent1.putExtra("DEPT",intentDept);
                intent1.putExtra("BATCH",intentBatch);
                intent1.putExtra("SHIFT",intentShift);

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
