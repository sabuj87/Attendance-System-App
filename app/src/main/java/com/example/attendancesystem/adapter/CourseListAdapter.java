package com.example.attendancesystem.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.attendancesystem.R;
import com.example.attendancesystem.model.Course;
import com.example.attendancesystem.model.Teacher;

import java.util.List;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseListViewHolder> {

    private List<Course> courseList;
    private Context context;

    public CourseListAdapter(Context context, List<Course> courseList) {

        this.context = context;
        this.courseList = courseList;
    }

    public CourseListAdapter(){

    }

    @NonNull
    @Override
    public CourseListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.single_student_layout,viewGroup,false);
        return new CourseListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseListViewHolder courseListViewHolder, int i) {
        courseListViewHolder.Course_name.setText(courseList.get(courseListViewHolder.getAdapterPosition()).getCourse_name());
        courseListViewHolder.course_code.setText(courseList.get(courseListViewHolder.getAdapterPosition()).getCourse_code());
        courseListViewHolder.teacherName.setText(courseList.get(courseListViewHolder.getAdapterPosition()).getTeacher());

    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    class CourseListViewHolder extends  RecyclerView.ViewHolder {
        TextView Course_name,teacherName;
        TextView course_code;

        public CourseListViewHolder(@NonNull View itemView) {
            super(itemView);
            Course_name=itemView.findViewById(R.id.studentNameTV);
            course_code=itemView.findViewById(R.id.studentCourseTv);
            teacherName=itemView.findViewById(R.id.studentIDv);
        }
    }

    public void updateCollection(List<Course> courseList){
        this.courseList =courseList;
        notifyDataSetChanged();
    }
}
