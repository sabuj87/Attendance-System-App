package com.example.attendancesystem.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.attendancesystem.R;
import com.example.attendancesystem.model.Student;

import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentListViewHolder> {

    private List<Student> studentList;
    private Context context;

    public StudentListAdapter(Context context,List<Student> studentList) {

        this.context = context;
        this.studentList = studentList;
    }

    public  StudentListAdapter(){

    }

    @NonNull
    @Override
    public StudentListAdapter.StudentListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.single_student_layout,viewGroup,false);
        return new StudentListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentListAdapter.StudentListViewHolder studentListViewHolder, int i) {
        studentListViewHolder.Student_name.setText(studentList.get(studentListViewHolder.getAdapterPosition()).getName());
        studentListViewHolder.course_code.setText(studentList.get(studentListViewHolder.getAdapterPosition()).getCourse_code());
        studentListViewHolder.id.setText(studentList.get(studentListViewHolder.getAdapterPosition()).getId());
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class StudentListViewHolder extends  RecyclerView.ViewHolder {
        TextView Student_name;
        TextView course_code;
        TextView id;
        public StudentListViewHolder(@NonNull View itemView) {
            super(itemView);
            Student_name=itemView.findViewById(R.id.studentNameTV);
            course_code=itemView.findViewById(R.id.studentCourseTv);
            id=itemView.findViewById(R.id.studentIDv);
        }
    }

    public void updateCollection(List<Student> studentList){
        this.studentList=studentList;
        notifyDataSetChanged();
    }
}
