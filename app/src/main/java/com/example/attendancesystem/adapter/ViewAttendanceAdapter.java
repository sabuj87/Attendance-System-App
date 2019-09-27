package com.example.attendancesystem.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.attendancesystem.R;
import com.example.attendancesystem.model.Student;
import com.example.attendancesystem.storage.SaveUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import xyz.hasnat.sweettoast.SweetToast;

public class ViewAttendanceAdapter extends RecyclerView.Adapter<ViewAttendanceAdapter.ViewAttendanceViewHolder> {

    private List<Student> studentList=new ArrayList<>();
    private List<String> presentList=new ArrayList<>();
    private List<String> absentList=new ArrayList<>();

    private Context context;
    private DatabaseReference presentRef,absentRef;

    public ViewAttendanceAdapter(Context context, List<Student> studentList) {

        this.context = context;
        this.studentList = studentList;
    }

    public ViewAttendanceAdapter(){

    }

    @NonNull
    @Override
    public ViewAttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.single_student_layout,viewGroup,false);
        return new ViewAttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewAttendanceViewHolder viewAttendanceViewHolder, final int i) {
        viewAttendanceViewHolder.Student_name.setText(studentList.get(viewAttendanceViewHolder.getAdapterPosition()).getName());
        viewAttendanceViewHolder.course_code.setText(studentList.get(viewAttendanceViewHolder.getAdapterPosition()).getCourse_code());
        viewAttendanceViewHolder.id.setText(studentList.get(viewAttendanceViewHolder.getAdapterPosition()).getId());


        viewAttendanceViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presentRef= FirebaseDatabase.getInstance().getReference().child("Department").child(studentList.get(viewAttendanceViewHolder.getAdapterPosition()).getDepartment())
                        .child("Attendance").child(new SaveUser().getTeacher(context).getShift()).child(new SaveUser().teacher_CourseLoadData(context));

                presentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        presentList.clear();
                        absentList.clear();
                        if(dataSnapshot.exists()){
                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                               for(DataSnapshot dataSnapshot2:dataSnapshot1.child("Present").getChildren()){

                                       String key=dataSnapshot2.getKey();
                                       if(viewAttendanceViewHolder.getAdapterPosition()!=-1){
                                           if(key.equals(studentList.get(i).getId())){
                                               presentList.add(key);
                                       }


                                   }
                                }

                                for(DataSnapshot dataSnapshot2:dataSnapshot1.child("Absent").getChildren()){

                                    String key=dataSnapshot2.getValue().toString();
                                    if(key.equals(studentList.get(viewAttendanceViewHolder.getAdapterPosition()).getId())){
                                        absentList.add(key);

                                    }
                                }

                            }



                            final AlertDialog dialoga =new AlertDialog.Builder(context).create();
                            View view=LayoutInflater.from(context).inflate(R.layout.viewatedance,null);

                            TextView presentTV,absentTV,name,id;
                            Button button;

                            presentTV=view.findViewById(R.id.presentStudentTV1);
                            absentTV=view.findViewById(R.id.absentStudentTV1);
                            name=view.findViewById(R.id.vName);
                            id=view.findViewById(R.id.vID);
                            name.setText(studentList.get(viewAttendanceViewHolder.getAdapterPosition()).getName());
                            id.setText(studentList.get(viewAttendanceViewHolder.getAdapterPosition()).getId());
                            button=view.findViewById(R.id.Okbtn);
                            presentTV.setText(Integer.toString(presentList.size()));
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
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class ViewAttendanceViewHolder extends  RecyclerView.ViewHolder {
        TextView Student_name;
        TextView course_code;
        TextView id;
        public ViewAttendanceViewHolder(@NonNull View itemView) {
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
