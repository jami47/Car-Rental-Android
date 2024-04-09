package com.example.carrental;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {

    CardView profile_card,about_card,exit_card,logout_card,car_card,upcoming_card;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        profile_card = findViewById(R.id.profileCard);
        about_card =  findViewById(R.id.aboutCard);
        exit_card = findViewById(R.id.exitCard);
        logout_card = findViewById(R.id.logoutCard);
        car_card = findViewById(R.id.carCard);
        upcoming_card = findViewById(R.id.upcomingCard);

        profile_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AdminActivity.this, AdminProfile.class));
            }
        });
        about_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, AboutUs.class));
            }
        });
        exit_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AdminActivity.this, LogIn.class));
                finish();
            }
        });
        logout_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(AdminActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AdminActivity.this, LogIn.class));
                finish();*/
                Toast.makeText(AdminActivity.this, "Course Instructors", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AdminActivity.this, AdminTeacherActivity.class));
            }
        });
        car_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, AdminCarLIst.class));
            }
        });
        upcoming_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminActivity.this, "Upcoming Cars", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AdminActivity.this, UpcomingCars.class));
            }
        });
    }
}