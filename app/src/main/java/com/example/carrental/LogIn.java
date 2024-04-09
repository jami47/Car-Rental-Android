package com.example.carrental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogIn extends AppCompatActivity {

    private TextView signup;
    String emailfirebase,passwordfirebase, email, Password;
    private EditText Email, password;
    private TextView forget_password;
    private ProgressBar progressBar;
    private Button Login;
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null){
            //reload();
            Toast.makeText(LogIn.this, "Log In Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LogIn.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        forget_password = findViewById(R.id.forget_password);
        signup = findViewById(R.id.sign_up);
        Email = findViewById(R.id.Email);
        password = findViewById(R.id.password);
        Login = findViewById(R.id.B1);
        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Admin").child("Login Info");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                 emailfirebase = String.valueOf(snapshot.child("Email").getValue());
                passwordfirebase = String.valueOf(snapshot.child("Password").getValue());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this, SignUp.class);
                startActivity(intent);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIN();
            }
        });

        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this, ResetPasswordActivity.class));
            }
        });
    }

    private void SignIN() {

        email = Email.getText().toString().trim();
        Password = password.getText().toString().trim();

        if(email.isEmpty()){
            Email.setError("Enter Email");
            Email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email.setError("Enter a valid email");
            Email.requestFocus();
            return;
        }
        if(Password.isEmpty()){
            password.setError("Enter password");
            password.requestFocus();
            return;
        }
        Login.setClickable(false); //Onek bar click korle onek bar sign in hoye jawa bondho kore.
        //  Admin Login

        //Admin
        if(email.equals(emailfirebase) && Password.equals(passwordfirebase))
        {   Login.setClickable(true);
            Toast.makeText(LogIn.this, "Welcome Admin", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LogIn.this, AdminActivity.class);
            startActivity(intent);
            finish();
        }
        //  User Login
        else {
            firebaseAuth.signInWithEmailAndPassword(email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Login.setClickable(true);
                        Toast.makeText(LogIn.this, "Log In Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LogIn.this, MainActivity.class));
                        finish();
                    } else {
                        Login.setClickable(true);
                        Toast.makeText(LogIn.this, "Log In Failure", Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }
    }
}