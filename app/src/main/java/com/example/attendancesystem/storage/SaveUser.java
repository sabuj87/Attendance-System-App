package com.example.attendancesystem.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.attendancesystem.model.Student;
import com.example.attendancesystem.model.Teacher;

import static android.content.Context.MODE_PRIVATE;

public class SaveUser {

   private Boolean value;
    public void admin_saveData(Context context,Boolean b) {
        SharedPreferences pref = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isUserLogIn", b);
        editor.commit();

    }
    public boolean admin_loadData(Context context) {
        SharedPreferences pref =context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        value = pref.getBoolean("isUserLogIn", false);
        return value;


    }

    public void introSave(Context context,Boolean b) {
        SharedPreferences pref = context.getSharedPreferences("intro", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("intro", b);
        editor.commit();

    }
    public boolean introLoad(Context context) {
        SharedPreferences pref =context.getSharedPreferences("intro", MODE_PRIVATE);
        value = pref.getBoolean("intro", false);
        return value;


    }


    public void teacher_saveData(Context context,Boolean b) {
        SharedPreferences pref = context.getSharedPreferences("myPrefss", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isUserLogIn", b);
        editor.commit();

    }
    public boolean teacher_loadData(Context context) {
        SharedPreferences pref =context.getSharedPreferences("myPrefss", MODE_PRIVATE);
         value = pref.getBoolean("isUserLogIn", false);
        return value;


    }

    public void Student_saveData(Context context,Boolean b) {
        SharedPreferences pref = context.getSharedPreferences("SV", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isUserLogIn", b);
        editor.commit();

    }
    public boolean Student_loadData(Context context) {
        SharedPreferences pref =context.getSharedPreferences("SV", MODE_PRIVATE);
        value = pref.getBoolean("isUserLogIn", false);
        return value;


    }

    public void teacher_IDsaveData(Context context,String b) {
        SharedPreferences pref = context.getSharedPreferences("myPrefsss", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("isUserLogIn", b);
        editor.commit();

    }
    public String teacher_IDloadData(Context context) {
        SharedPreferences pref =context.getSharedPreferences("myPrefsss", MODE_PRIVATE);
        String  values = pref.getString("isUserLogIn",null);
        return values;


    }  public void teacher_NameSaveData(Context context,String b) {
        SharedPreferences pref = context.getSharedPreferences("name", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("isUserLogIn", b);
        editor.commit();

    }
    public String teacher_NameLoadData(Context context) {
        SharedPreferences pref =context.getSharedPreferences("name", MODE_PRIVATE);
        String  values = pref.getString("isUserLogIn",null);
        return values;


    }

    public void teacher_ShiftSaveData(Context context,String b) {
        SharedPreferences pref = context.getSharedPreferences("shift", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("isUserLogIn", b);
        editor.commit();

    }
    public String teacher_ShiftLoadData(Context context) {
        SharedPreferences pref =context.getSharedPreferences("shift", MODE_PRIVATE);
        String  values = pref.getString("isUserLogIn",null);
        return values;


    }

    public void teacher_DeptSaveData(Context context,String b) {
        SharedPreferences pref = context.getSharedPreferences("tdept", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("isUserLogIn", b);
        editor.commit();

    }
    public String teacher_DeptLoadData(Context context) {
        SharedPreferences pref =context.getSharedPreferences("tdept", MODE_PRIVATE);
        String  values = pref.getString("isUserLogIn",null);
        return values;


    }
    public void teacher_CourseSaveData(Context context,String b) {
        SharedPreferences pref = context.getSharedPreferences("tcourse", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("isUserLogIn", b);
        editor.commit();

    }
    public String teacher_CourseLoadData(Context context) {
        SharedPreferences pref =context.getSharedPreferences("tcourse", MODE_PRIVATE);
        String  values = pref.getString("isUserLogIn",null);
        return values;


    }

    public void saveTeacher(Context context,Teacher teacher){
        SharedPreferences sharedPreferences=context.getSharedPreferences("TEACHER",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("id",teacher.getId());
        editor.putString("name",teacher.getName());
        editor.putString("desination",teacher.getDesignation());
        editor.putString("dept",teacher.getDepartment());
        editor.putString("shift",teacher.getShift());
        editor.apply();
    }

    public Teacher getTeacher(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("TEACHER",Context.MODE_PRIVATE);
        Teacher teacher=new Teacher(sharedPreferences.getString("id",null),

                sharedPreferences.getString("name",null),
                sharedPreferences.getString("dept",null),
                sharedPreferences.getString("desination",null),
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                sharedPreferences.getString("shift",null)



        );
        return teacher;
    }

    public void saveStudent(Context context, Student student){
        SharedPreferences sharedPreferences=context.getSharedPreferences("STUDENT",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("id",student.getId());
        editor.putString("name",student.getName());
        editor.putString("batch",student.getBatch());
        editor.putString("dept",student.getDepartment());
        editor.putString("shift",student.getShift());
        editor.putString("course",student.getCourse());
        editor.putString("course_code",student.getCourse_code());
        editor.putString("password",student.getPassword());
        editor.apply();
    }

    public Student getStudent(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("STUDENT",Context.MODE_PRIVATE);
        Student student=new Student(sharedPreferences.getString("name",null),
                sharedPreferences.getString("id",null),
                "",
                "",
                sharedPreferences.getString("dept",null),
                sharedPreferences.getString("batch",null),
                "",
                "",
                "",
                "",
                sharedPreferences.getString("course",null),
                sharedPreferences.getString("course_code",null),
                sharedPreferences.getString("shift",null),
                sharedPreferences.getString("password",null)





        );
        return student;
    }

}
