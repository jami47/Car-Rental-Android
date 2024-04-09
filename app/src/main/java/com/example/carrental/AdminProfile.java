package com.example.carrental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminProfile extends AppCompatActivity {

    TextInputEditText emailedt,passwordedt;
    TextInputLayout emaillayout, passwordlayout;
    AppCompatButton updatebtn;
    //private DatabaseReference reference;
    String emailfirebase,passwordfirebase, email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        emailedt = findViewById(R.id.email_adminprofile);
        passwordedt = findViewById(R.id.password_adminprofile);
        updatebtn = findViewById(R.id.update_adminprofile);
        emaillayout = findViewById(R.id.emaillayout_adminprofile);
        passwordlayout = findViewById(R.id.passwordlayout_adminprofile);

        //reference = FirebaseDatabase.getInstance().getReference();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Admin").child("Login Info");
        //Get admin email and password from firebase
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                emailfirebase = String.valueOf(snapshot.child("Email").getValue());
                passwordfirebase = String.valueOf(snapshot.child("Password").getValue());
                emailedt.setText(emailfirebase);
                passwordedt.setText(passwordfirebase);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Update Profile
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailedt.getText().toString().trim();
                password = passwordedt.getText().toString().trim();

                if(email.isEmpty()){
                    //emailedt.setError("Enter Email");
                    passwordlayout.setErrorEnabled(false);
                    emaillayout.setErrorEnabled(true);
                    emaillayout.setError("Required");
                    emaillayout.requestFocus();
                    //emailedt.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    //emailedt.setError("Enter a valid email");
                    passwordlayout.setErrorEnabled(false);
                    emaillayout.setErrorEnabled(true);
                    emaillayout.setError("Enter a valid email");
                    emaillayout.requestFocus();
                    //emailedt.requestFocus();
                    return;
                }
                if(password.isEmpty()){
                    //passwordedt.setError("Enter password");
                    emaillayout.setErrorEnabled(false);
                    passwordlayout.setErrorEnabled(true);
                    passwordlayout.setError("Enter password");
                    passwordlayout.requestFocus();
                    //passwordedt.requestFocus();
                    return;
                }
                else{
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child("Email").setValue(email);
                            databaseReference.child("Password").setValue(password);
                            emaillayout.setErrorEnabled(false);
                            passwordlayout.setErrorEnabled(false);
                            Toast.makeText(AdminProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }

}