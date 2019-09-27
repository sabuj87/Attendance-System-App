package com.example.attendancesystem.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.attendancesystem.R;
import com.example.attendancesystem.model.Teacher;
import com.example.attendancesystem.storage.SaveUser;
import com.example.attendancesystem.view.activity.SelectedCourse1Activity;
import com.example.attendancesystem.view.activity.SelectedCourseActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherFragment extends Fragment {
    private List<Teacher> teacherList=new ArrayList<>();
    private DatabaseReference teacherRef;
    private String dept;
    private String intentedId,shift;
    private TextView name,id,desig,depat,tShift;
    private CardView teacherAttendanceCV,ViewAttendanceCV;

    public TeacherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_teacher, container, false);
        SaveUser saveUser=new SaveUser();
        intentedId=saveUser.teacher_IDloadData(getContext());

        name=view.findViewById(R.id.teacherInfoName);
        id=view.findViewById(R.id.teacherInfoId);
        desig=view.findViewById(R.id.teacherInfoDesig);
        tShift=view.findViewById(R.id.teacherShiftDept);
        depat=view.findViewById(R.id.teacherInfoDept);
        ViewAttendanceCV=view.findViewById(R.id.ViewAttendanceCV);
        teacherAttendanceCV=view.findViewById(R.id.teacherAttendanceCV);

        name.setText(saveUser.getTeacher(getContext()).getName());
        id.setText(saveUser.getTeacher(getContext()).getId());
        desig.setText(saveUser.getTeacher(getContext()).getDesignation());
        tShift.setText(saveUser.getTeacher(getContext()).getShift());
        depat.setText(saveUser.getTeacher(getContext()).getDepartment());


       teacherAttendanceCV.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
             Intent intent=new Intent(getContext(), SelectedCourseActivity.class);
             intent.putExtra("TID",intentedId);
             startActivity(intent);
           }
       });
       ViewAttendanceCV.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(getContext(), SelectedCourse1Activity.class);
               startActivity(intent);
           }
       });
        return  view;
    }

    private void fetchTeacher(){
        teacherRef= FirebaseDatabase.getInstance().getReference().child("Department");
        teacherRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                teacherList.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        dept=dataSnapshot1.getKey();
                       DatabaseReference deptref=teacherRef.child(dept).child("Teacher");
                        deptref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    for(DataSnapshot dataSnapshot2:dataSnapshot.getChildren()){
                                        for(DataSnapshot dataSnapshot3:dataSnapshot2.getChildren()){
                                            if(dataSnapshot3.hasChildren()){
                                                Teacher teacher=dataSnapshot3.getValue(Teacher.class);
                                                if(teacher.getId().equals(intentedId)){
                                                    String name1=teacher.getName();
                                                    String id1=teacher.getId();
                                                    String desig1=teacher.getDesignation();
                                                    String dept1=teacher.getDepartment();
                                                    shift=teacher.getShift();
                                                    name.setText(name1);
                                                    id.setText(id1);
                                                    desig.setText(desig1);
                                                    depat.setText(dept1);
                                                    tShift.setText(shift);


                                                }

                                            }
                                        }







                                    }

                                        teacherAttendanceCV.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (!shift.equals("")){
                                                    Intent intent = new Intent(getContext(), SelectedCourseActivity.class);
                                                intent.putExtra("TID", intentedId);
                                                intent.putExtra("TSHIFT", shift);
                                                startActivity(intent);
                                            }
                                            }
                                        });




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
