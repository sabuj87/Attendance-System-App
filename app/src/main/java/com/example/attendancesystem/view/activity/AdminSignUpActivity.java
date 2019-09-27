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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.attendancesystem.R;
import com.example.attendancesystem.model.Admin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminSignUpActivity extends AppCompatActivity {
    private EditText adminSignUpNameET,adminSignUpEmailET,adminSignUpPasswordET,adminSignUpConfirmPassET;
    private Button signUpButton;
    private FirebaseAuth mAuth;
    private String uId;
    private DatabaseReference adminRef;
    private ProgressBar adminSignUPPB;
    private LinearLayout adminSignUPLL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_up);

        adminSignUpNameET=findViewById(R.id.adminSignUpNameET);
        adminSignUpEmailET=findViewById(R.id.adminSignUpEmailET);
        adminSignUpPasswordET=findViewById(R.id.adminSignUpPasswordET);
        adminSignUpConfirmPassET=findViewById(R.id.adminSignUPConfirmPassET);
        signUpButton=findViewById(R.id.signUpBtn);
        adminSignUPPB=findViewById(R.id.adminSignUPPB);

        adminSignUPLL=findViewById(R.id.addSignUpLL);
        mAuth=FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               signUp();
            }
        });

    }

    private void signUp() {
        final String name=adminSignUpNameET.getText().toString().trim();
        final String email=adminSignUpEmailET.getText().toString().trim();
        String password=adminSignUpPasswordET.getText().toString().trim();
        String confirmPassword=adminSignUpConfirmPassET.getText().toString().trim();

        if(name.isEmpty()){
            adminSignUpNameET.setError("Enter admin name");
            adminSignUpNameET.requestFocus();
        } else if(email.isEmpty()){
            adminSignUpEmailET.setError("Enter admin email");
            adminSignUpEmailET.requestFocus();
        }
        else if(password.isEmpty()){
            adminSignUpPasswordET.setError("Enter password");
            adminSignUpPasswordET.requestFocus();
        }
        else if(confirmPassword.isEmpty()){
            adminSignUpConfirmPassET.setError("Confirm your password");
            adminSignUpConfirmPassET.requestFocus();
        }  else if(!confirmPassword.equals(password)){
            adminSignUpConfirmPassET.setError("Password does not match");
            adminSignUpConfirmPassET.requestFocus();
        }else {
          mAuth.createUserWithEmailAndPassword(email,password)
                  .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                          if(task.isSuccessful()){
                             uId=mAuth.getCurrentUser().getUid();
                             adminRef= FirebaseDatabase.getInstance().getReference().child("Admin").child(uId);
                              Admin admin=new Admin(
                                      name,
                                      email,
                                      "",
                                      "",
                                      "",
                                      "",
                                      "",
                                      "",
                                      "",
                                      "",
                                      0,
                                      "",
                                      "",
                                      0,
                                      "",
                                      "1234"


                              );
                              adminRef.setValue(admin)
                                      .addOnCompleteListener(new OnCompleteListener<Void>() {
                                          @Override
                                          public void onComplete(@NonNull Task<Void> task) {
                                              if(task.isSuccessful()){
                                                  mAuth.signOut();

                                                  Intent intent=new Intent(AdminSignUpActivity.this,AdminLoginActivity.class);
                                                  startActivity(intent);
                                                  finish();
                                                  Toast.makeText(getApplicationContext(),"Sign up successfully",Toast.LENGTH_SHORT).show();


                                              }else {
                                               mAuth.signOut();
                                              }
                                          }
                                      }).addOnFailureListener(new OnFailureListener() {
                                  @Override
                                  public void onFailure(@NonNull Exception e) {
                                      Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                                  }
                              });

                          }else {
                              adminSignUPLL.setVisibility(View.VISIBLE);
                              adminSignUPPB.setVisibility(View.GONE);
                              Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                          }
                      }
                  });
          adminSignUPLL.setVisibility(View.GONE);
          adminSignUPPB.setVisibility(View.VISIBLE);
        }

    }
}
