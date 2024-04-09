package com.example.carrental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class SignUp extends AppCompatActivity {

    private EditText name, Email, password, dateofbirth, city, phone;
    private FirebaseAuth firebaseauth;
    private DatePickerDialog picker;
    private Button signupbutton;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseauth.getCurrentUser();
        if(currentUser != null){
            //reload();
            Toast.makeText(SignUp.this, "Log In Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SignUp.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseauth = FirebaseAuth.getInstance();

        name = findViewById(R.id.namesignup);
        password = findViewById(R.id.passsignup);
        Email = findViewById(R.id.emailsignup);
        dateofbirth = findViewById(R.id.dateofbirth);
        signupbutton = findViewById(R.id.signupbutton);
        city = findViewById(R.id.citysignup);
        phone = findViewById(R.id.phonesignup);

        dateofbirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                picker = new DatePickerDialog(SignUp.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateofbirth.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                    }
                },year,month,day);
                picker.show();
            }
        });

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names = name.getText().toString().trim();
                String emails = Email.getText().toString().trim();
                String passwords = password.getText().toString().trim();
                String birthday = dateofbirth.getText().toString().trim();
                String cities = city.getText().toString().trim();
                String phones = phone.getText().toString().trim();

                if(names.isEmpty()){
                    name.setError("Enter Name");
                    name.requestFocus();
                    return;
                }
                if(emails.isEmpty()){
                    Email.setError("Enter Email");
                    Email.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(emails).matches()){
                    Email.setError("Enter a valid email");
                    Email.requestFocus();
                    return;
                }
                if (birthday.isEmpty()) {
                    dateofbirth.setError("Required");
                    dateofbirth.requestFocus();
                    return;
                }
                if(passwords.isEmpty() || passwords.length()<6){
                    password.setError("Enter password of minimum length 6");
                    password.requestFocus();
                    return;
                }
                if(cities.isEmpty()){
                    city.setError("Required");
                    city.requestFocus();
                    return;
                }
                if(phones.length() != 11){
                    phone.setError("Enter valid phone number");
                    phone.requestFocus();
                    return;
                }

                Signin(emails,passwords);
            }
        });

    }
    private void Signin(String Email, String Password){
        firebaseauth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = firebaseauth.getCurrentUser();
                    String uid = user.getUid();
                    updateUi(uid, Email);
                } else{
                    Toast.makeText(SignUp.this, "Sign In Failure", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateUi(String uid, String email){
        HashMap<String, Object> map = new HashMap<>();

        map.put("name",name.getText().toString().trim());
        map.put("city",city.getText().toString().trim());
        map.put("phone",phone.getText().toString().trim());
        map.put("email",Email.getText().toString().trim());
        map.put("birthday", dateofbirth.getText().toString().trim());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user");
            reference.child(uid).child("User Information").setValue(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            
                            if(task.isSuccessful()){
                                Toast.makeText(SignUp.this, "Sign Up successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUp.this, LogIn.class));
                                finish();
                            }
                            else{
                                
                                Exception e = task.getException();
                                if(e==null){
                                    Toast.makeText(SignUp.this, "Sign Up Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    return;   
                                }
                                Toast.makeText(SignUp.this, "Sign Up Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            
    }

}