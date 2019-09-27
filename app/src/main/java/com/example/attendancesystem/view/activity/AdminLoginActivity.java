package com.example.attendancesystem.view.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.attendancesystem.R;
import com.example.attendancesystem.model.Admin;
import com.example.attendancesystem.storage.SaveUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import xyz.hasnat.sweettoast.SweetToast;

public class AdminLoginActivity extends AppCompatActivity {
    private LinearLayout goSignUp;
    private EditText adminSignINEmailET,adminSingInPassET;
    private Button adminLogInBtn;
    private FirebaseAuth auth;
    private SaveUser saveUser=new SaveUser();
    private String femail,fpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_login);
        adminSignINEmailET=findViewById(R.id.adminSignInEmailET);
        adminSingInPassET=findViewById(R.id.adminSignInPassET);
        adminLogInBtn=findViewById(R.id.adminLoginBtn);

        auth=FirebaseAuth.getInstance();


        adminLogInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        final String email=adminSignINEmailET.getText().toString().trim();
        final String password=adminSingInPassET.getText().toString().trim();

        if(email.isEmpty()){
            adminSignINEmailET.setError("Enter admin email");
            adminSignINEmailET.requestFocus();
        }else if(password.isEmpty()){
            adminSingInPassET.setError("Password is empty");
            adminSingInPassET.requestFocus();
        }else {

            DatabaseReference adminLogRef=FirebaseDatabase.getInstance().getReference().child("Admin");
            adminLogRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                            Admin admin=dataSnapshot1.getValue(Admin.class);
                            if(admin.getName().equals(email) && admin.getPassword().equals(password)){

                                SweetToast.success(getApplicationContext(),"Login successfully");
                                startActivity(new Intent(AdminLoginActivity.this,AdminActivity.class));
                                saveUser.admin_saveData(getApplicationContext(),true);
                                finish();

                            }else {
                                SweetToast.error(getApplicationContext(),"You entered wrong user name or password");
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
           /* auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Login successfully",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AdminLoginActivity.this,AdminActivity.class));
                                saveUser.admin_saveData(getApplicationContext(),true);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });*/
        }

    }
}
