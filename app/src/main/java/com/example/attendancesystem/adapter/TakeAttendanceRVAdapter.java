package com.example.attendancesystem.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.attendancesystem.R;
import com.example.attendancesystem.model.Student;

import java.util.ArrayList;
import java.util.List;

public class TakeAttendanceRVAdapter extends RecyclerView.Adapter<TakeAttendanceRVAdapter.TakeAttendanceRVViewHolder> {

    private List<Student> studentList=new ArrayList<>();
    private Context context;
    public  static List<String> presentList=new ArrayList<>();
    public  static List<String> absentList=new ArrayList<>();

    public TakeAttendanceRVAdapter(Context context, List<Student> studentList) {

        this.context = context;
        this.studentList = studentList;
    }

    public TakeAttendanceRVAdapter(){

    }

    @NonNull
    @Override
    public TakeAttendanceRVViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.take_attendance_layout,viewGroup,false);
        return new TakeAttendanceRVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TakeAttendanceRVViewHolder takeAttendanceRVViewHolder, final int i) {
        takeAttendanceRVViewHolder.Student_name.setText(studentList.get(takeAttendanceRVViewHolder.getAdapterPosition()).getName());
        takeAttendanceRVViewHolder.Student_Id.setText(studentList.get(takeAttendanceRVViewHolder.getAdapterPosition()).getId());

        takeAttendanceRVViewHolder.presentRBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(!presentList.contains(studentList.get(takeAttendanceRVViewHolder.getAdapterPosition()).getId())){
                        presentList.add(studentList.get(takeAttendanceRVViewHolder.getAdapterPosition()).getId());

                    }

                }else {
                    presentList.remove(studentList.get(takeAttendanceRVViewHolder.getAdapterPosition()).getId());
                }
            }
        });
        takeAttendanceRVViewHolder.absentRBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    absentList.add(studentList.get(takeAttendanceRVViewHolder.getAdapterPosition()).getId());
                }
                else {
                    absentList.remove(studentList.get(takeAttendanceRVViewHolder.getAdapterPosition()).getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class TakeAttendanceRVViewHolder extends  RecyclerView.ViewHolder {
        TextView Student_name;
        TextView Student_Id;
        RadioButton presentRBtn;
        RadioButton absentRBtn;
        public TakeAttendanceRVViewHolder(@NonNull View itemView) {
            super(itemView);
            Student_name=itemView.findViewById(R.id.takeattendanceStudentName);
            Student_Id =itemView.findViewById(R.id.takeattendanceStudentID);
            presentRBtn=itemView.findViewById(R.id.presentRadioBtn);
            absentRBtn=itemView.findViewById(R.id.absentRadioBtn);
        }
    }

    public void updateCollection(List<Student> studentList){
        this.studentList=studentList;
        notifyDataSetChanged();
    }
}
