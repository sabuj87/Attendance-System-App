package com.example.attendancesystem.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.attendancesystem.R;
import com.example.attendancesystem.storage.SaveUser;
import com.example.attendancesystem.view.activity.ShowAttendanceActivity;

import java.util.ArrayList;
import java.util.List;

import xyz.hasnat.sweettoast.SweetToast;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentFragment extends Fragment {
  private TextView name,id,dept,shift,batch;
  private LinearLayout ViewAttendance;
  private String courses;
  private List<String> courseList=new ArrayList<>();

    public StudentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      View view=inflater.inflate(R.layout.fragment_student, container, false);

      name=view.findViewById(R.id.StudentName);
      id=view.findViewById(R.id.StudentId);
      dept=view.findViewById(R.id.StudentDept);
      shift=view.findViewById(R.id.StudentShift);
      batch=view.findViewById(R.id.StudentBatch);
      ViewAttendance=view.findViewById(R.id.ViewAttendance);

      SaveUser saveUser=new SaveUser();

      courses=saveUser.getStudent(getContext()).getCourse();


      name.setText(saveUser.getStudent(getContext()).getName());
      id.setText(saveUser.getStudent(getContext()).getId());
      dept.setText(saveUser.getStudent(getContext()).getDepartment());
     shift.setText(saveUser.getStudent(getContext()).getShift());
     batch.setText(saveUser.getStudent(getContext()).getBatch());

     ViewAttendance.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent=new Intent(getContext(), ShowAttendanceActivity.class);
             startActivity(intent);
         }
     });



      return view;
    }

}
