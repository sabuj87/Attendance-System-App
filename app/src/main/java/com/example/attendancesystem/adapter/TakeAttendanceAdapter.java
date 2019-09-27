package com.example.attendancesystem.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.attendancesystem.R;
import com.example.attendancesystem.model.Student;

import java.util.ArrayList;
import java.util.List;

public class TakeAttendanceAdapter extends BaseAdapter{

    Context context;
    List<Student> studentList=new ArrayList<>();
    LayoutInflater inflater;
    public  static List<String> presentList=new ArrayList<>();
    public  static List<String> absentList=new ArrayList<>();

    public TakeAttendanceAdapter(Context context, List<Student> studentList) {
        this.context = context;
        this.studentList = studentList;


        inflater=(LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return studentList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        convertView=inflater.inflate(R.layout.take_attendance_layout,null);

        TextView student_name=convertView.findViewById(R.id.takeattendanceStudentName);
        TextView id=convertView.findViewById(R.id.takeattendanceStudentID);
        final RadioButton present=convertView.findViewById(R.id.presentRadioBtn);
        final RadioButton absent=convertView.findViewById(R.id.absentRadioBtn);

        student_name.setText(studentList.get(position).getName());
        id.setText(studentList.get(position).getId());



        present.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                      if(!presentList.contains(studentList.get(position).getId())){
                          presentList.add(studentList.get(position).getId());

                      }

                }else {
                    presentList.remove(studentList.get(position).getId());
                }
            }
        });
        absent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    absentList.add(studentList.get(position).getId());
                }
            }
        });
        return convertView;
    }
}
