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
import android.widget.Toast;

import com.example.attendancesystem.R;
import com.example.attendancesystem.adapter.CheckableSpinnerAdapter;
import com.example.attendancesystem.model.Course;
import com.example.attendancesystem.model.SpinnerObject;
import com.example.attendancesystem.model.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import xyz.hasnat.sweettoast.SweetToast;

public class AddStudentActivity extends AppCompatActivity {

    private Toolbar addStudentToolbar;
    private Spinner studentDeptSP,studentSemesterSp,studentYearSp,studentCourseSp;
    private EditText addStudentName,addStudentEmail,addStudentID,addStudentPhone;
    private String[] dept;
    private String[] semester;
    private String[] year;
    private ArrayList<String> courseNamelist=new ArrayList<>();
    private ArrayList<String> course_codeList=new ArrayList<>();

    private ArrayAdapter<String> deptAdapter,semesterAdapter,yearAdapter,courseAdapter;
    private Button addStudentButton;
    private String SelectedDept;
    private String SelectedYear;
    private String SelectedSemister;
    private String intentDept;
    private String intentBatch;
    private String intentShift;

    private DatabaseReference studentRef,attendanceRef,coureRef;


    private final List<CheckableSpinnerAdapter.SpinnerItem<SpinnerObject>> course_spinner_items = new ArrayList<>();
    private final List<CheckableSpinnerAdapter.SpinnerCode<SpinnerObject>> course_spinner_code = new ArrayList<>();
    private final Set<SpinnerObject> course_selected_items = new HashSet<>();
    private final Set<SpinnerObject> selected_course=new HashSet<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        addStudentToolbar=findViewById(R.id.addStudentToolbar);
        setSupportActionBar(addStudentToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent=getIntent();

       intentDept= intent.getStringExtra("DEPT");
       intentBatch=intent.getStringExtra("BATCH");
       intentShift=intent.getStringExtra("SHIFT");




        //studentDeptSP=findViewById(R.id.addStudentDept);
        studentSemesterSp=findViewById(R.id.addStudentSemester);
        studentYearSp=findViewById(R.id.addStudentYear);
        studentCourseSp=findViewById(R.id.addStudentCourse);
        addStudentButton=findViewById(R.id.addStudentBtn);
        addStudentName=findViewById(R.id.addStudentName);
        addStudentEmail=findViewById(R.id.addStudentEmail);
        addStudentID=findViewById(R.id.addStudentID);
        addStudentPhone=findViewById(R.id.addStudentPhone);

        studentRef= FirebaseDatabase.getInstance().getReference().child("Department").child(intentDept).child("Student").child(intentBatch).child("allstudent").child(intentShift);
        //attendanceRef=FirebaseDatabase.getInstance().getReference().child("Department").child(intentDept).child("Student").child(intentBatch).child("attendance");

        dept=getResources().getStringArray(R.array.department);
        semester=getResources().getStringArray(R.array.semester);
        year=getResources().getStringArray(R.array.year);

        coureRef=FirebaseDatabase.getInstance().getReference().child("Department").child(intentDept).child("Course").child(intentShift);
        coureRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                courseNamelist.clear();
                course_codeList.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        if(dataSnapshot1.hasChildren()){
                            Course course=dataSnapshot1.getValue(Course.class);
                            String name=course.getCourse_name();
                            String code=course.getCourse_code();
                            courseNamelist.add(name);
                            course_codeList.add(code);

                        }
                    }
                    List<SpinnerObject> all_objects = new ArrayList<>();// from wherever
                    for (int i = 0; i < courseNamelist.size(); i++) {
                        SpinnerObject myObject=new SpinnerObject();
                        myObject.setmName(courseNamelist.get(i));
                        myObject.setC_code(course_codeList.get(i));
                        all_objects.add(myObject);
                    }

                    for(SpinnerObject o : all_objects) {
                        course_spinner_items.add(new CheckableSpinnerAdapter.SpinnerItem<>(o,o.getmName()));
                        course_spinner_code.add(new CheckableSpinnerAdapter.SpinnerCode<>(o,o.getC_code()));
                    }

                    String headerText = "Select Courses";
                    CheckableSpinnerAdapter cadapter = new CheckableSpinnerAdapter<>(AddStudentActivity.this,headerText,course_spinner_items,course_spinner_code,course_selected_items,selected_course);
                    studentCourseSp.setAdapter(cadapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        // to start with any pre-selected, add them to the `selected_items` set




        semesterAdapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,semester);
        yearAdapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,year);
       // co urseAdapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,course);


        studentSemesterSp.setAdapter(semesterAdapter);
        studentYearSp.setAdapter(yearAdapter);




        studentSemesterSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectedSemister=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
       studentYearSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               SelectedYear=parent.getItemAtPosition(position).toString();
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });







        addStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addStudent();
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

    private void addStudent(){
        StringBuilder stringBuildert=new StringBuilder();
        for(SpinnerObject so:course_selected_items){
            stringBuildert.append(so.getmName().concat(","));
        } StringBuilder stringBuilderc=new StringBuilder();
        for(SpinnerObject so:selected_course){
            stringBuilderc.append(so.getC_code().concat(","));
        }
        String name=addStudentName.getText().toString();
        String email=addStudentEmail.getText().toString();
        String ID=addStudentID.getText().toString();
        String phone=addStudentPhone.getText().toString();

      // Toast.makeText(AddStudentActivity.this,stringBuilder,Toast.LENGTH_SHORT).show();
       if(name.isEmpty()){
           addStudentName.setError("Enter student name");
           addStudentName.requestFocus();
       } else if(email.isEmpty()){
           addStudentEmail.setError("Enter student email");
           addStudentEmail.requestFocus();
        }else if(phone.isEmpty()){
           addStudentPhone.setError("Enter Phone Number");
           addStudentPhone.requestFocus();
       }

       else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
           addStudentEmail.setError("Enter valid email");
           addStudentEmail.requestFocus();
       }else if(SelectedSemister.equals("Select semester")){
           SweetToast.warning(getApplicationContext(),"Select semester");
       }else if(SelectedYear.equals("Select year")){
           SweetToast.warning(getApplicationContext(),"Select year");
       }else if(ID.isEmpty()){
           addStudentID.setError("Enter valid ID");
           addStudentID.requestFocus();
       }else if(stringBuildert.toString().isEmpty()){
           SweetToast.warning(getApplicationContext(),"Select courses");
       }else {

           String key=studentRef.push().getKey();

           Student student=new Student(name,ID,SelectedYear,SelectedSemister,intentDept,intentBatch,"",email,phone,"",stringBuildert.toString(),stringBuilderc.toString(),intentShift,"1234");

           studentRef.child(key).setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
               @Override
               public void onComplete(@NonNull Task<Void> task) {
                   if(task.isSuccessful()){
                       SweetToast.success(getApplicationContext(),"Student Data added Successfully");
                   }
               }
           });

       }

    }
}
