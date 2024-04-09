package com.example.carrental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ProfileUser extends AppCompatActivity {

    BottomNavigationView bottomNavigationView; FloatingActionButton floatingActionButton;
    String bithday_profile,email_profile,name_profile,city_profile,phone_profile;
    TextView userName,userEmail,userBirthday,userCity,userPhone;
    private String uid;
    private DatabaseReference user_ref;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        floatingActionButton = findViewById(R.id.floating_profile);

        userName = findViewById(R.id.username_profile);
        userEmail = findViewById(R.id.useremail_profile);
        userBirthday = findViewById(R.id.userbirthday_profile);
        userCity = findViewById(R.id.usercity_profile);
        userPhone = findViewById(R.id.userphone_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        user_ref = FirebaseDatabase.getInstance().getReference().child("user");
        uid = firebaseAuth.getUid();

        user_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(uid)){
                    //userName.setText("Test1");
                    user_ref = user_ref.child(uid).child("User Information");
                    //HashMap<String, Object> map = new HashMap<>();
                    user_ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot2) {
                            //userName.setText("Test2");
                            UserInfo userInfo = snapshot2.getValue(UserInfo.class);

                            bithday_profile = userInfo.birthday;
                            name_profile = userInfo.name;
                            email_profile = userInfo.email;
                            city_profile = userInfo.city;
                            phone_profile = userInfo.phone;

                            userName.setText(name_profile);
                            userEmail.setText(email_profile);
                            userCity.setText(city_profile);
                            userBirthday.setText(bithday_profile);
                            userPhone.setText(phone_profile);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //userName.setText(name_profile);


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.profile_item){
                    Toast.makeText(ProfileUser.this, "Profile", Toast.LENGTH_SHORT).show();
                }
                if(item.getItemId()==R.id.about_item){
                    Toast.makeText(ProfileUser.this, "About Us", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ProfileUser.this, AboutUs.class));
                }
                if(item.getItemId()==R.id.logout_item){
                    //clears all activities and returns to Login Page

                    Intent intents = new Intent(ProfileUser.this, LogIn.class);
                    Toast.makeText(ProfileUser.this, "Logged Out", Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intents);
                    finish();
                }
                if(item.getItemId()==R.id.teacher_item){
                    Toast.makeText(ProfileUser.this, "Teachers", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ProfileUser.this, AdminTeacherActivity.class));
                }
                return false;
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileUser.this, "Upcoming Cars", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ProfileUser.this, UpcomingCars.class));
            }
        });
    }
}