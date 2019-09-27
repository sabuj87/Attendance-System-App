package com.example.attendancesystem.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.attendancesystem.R;
import com.example.attendancesystem.model.Teacher;

import java.util.List;

public class AttendanceStudentAdapter extends RecyclerView.Adapter<AttendanceStudentAdapter.TeacherListViewHolder> {

    private List<Teacher> teacherlist;
    private Context context;

    public AttendanceStudentAdapter(Context context, List<Teacher> teacherlist) {

        this.context = context;
        this.teacherlist = teacherlist;
    }

    public AttendanceStudentAdapter(){

    }

    @NonNull
    @Override
    public TeacherListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.single_student_layout,viewGroup,false);
        return new TeacherListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherListViewHolder teacherListViewHolder, int i) {
        teacherListViewHolder.Student_name.setText(teacherlist.get(teacherListViewHolder.getAdapterPosition()).getName());
        teacherListViewHolder.course_code.setText(teacherlist.get(teacherListViewHolder.getAdapterPosition()).getDesignation());
    }

    @Override
    public int getItemCount() {
        return teacherlist.size();
    }

    class TeacherListViewHolder extends  RecyclerView.ViewHolder {
        TextView Student_name;
        TextView course_code;
        public TeacherListViewHolder(@NonNull View itemView) {
            super(itemView);
            Student_name=itemView.findViewById(R.id.studentNameTV);
            course_code=itemView.findViewById(R.id.studentCourseTv);
        }
    }

    public void updateCollection(List<Teacher> studentList){
        this.teacherlist =studentList;
        notifyDataSetChanged();
    }
}
