package com.example.carrental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminCarDetails extends AppCompatActivity {

    DatabaseReference mref,mref2;
    TextInputEditText carnameedt, carpriceedt, carratingedt;
    String car_name, car_price, car_rating;
    AppCompatButton updatebtn, deletebtn, backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_car_details);

        mref = FirebaseDatabase.getInstance().getReference().child("Cars");
        carnameedt = findViewById(R.id.carname_admincardetails);
        carpriceedt = findViewById(R.id.carprice_admincardetails);
        carratingedt = findViewById(R.id.carrating_admincardetails);
        updatebtn = findViewById(R.id.update_admincardetails);
        backbtn = findViewById(R.id.back_admincardetails);
        deletebtn = findViewById(R.id.delete_admincardetails);

        carnameedt.setClickable(false); // User jate change korte na pare Car er name.
        carratingedt.setClickable(false);

        Intent intent = getIntent();
        car_name = intent.getStringExtra("car_name");

        //Start korar surutei je value gula dekhabe
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(car_name).exists()){
                    mref2 = mref.child(car_name);
                    mref2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot2) {
                            //car_price = String.valueOf(snapshot2.child("carPrice").getValue(Integer.class));
                            car_price = snapshot2.child("carPrice").getValue(String.class);
                            car_rating = snapshot2.child("avgrating").getValue(String.class);
                            carnameedt.setText(car_name);
                            carpriceedt.setText(car_price);
                            carratingedt.setText(car_rating);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    //CarModel car = snapshot.child(car_name).getValue(CarModel.class);

                    //car_price = snapshot.child(car_name).child("carPrice").getValue(String.class);

                    /*car_rating = car.avgrating;
                    car_price = String.valueOf(car.carPrice);*/
                    /*carnameedt.setText(car_name);
                    carpriceedt.setText(car_price);*/
                    //carratingedt.setText(car_rating);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                car_price = carpriceedt.getText().toString().trim();
                if(car_price.isEmpty()){
                    Toast.makeText(AdminCarDetails.this, "Rent can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                System.out.println(car_price);
                int num = Integer.parseInt(car_price);
                mref2 = mref.child(car_name);
                mref2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Toast.makeText(AdminCarDetails.this, "Rent Updated", Toast.LENGTH_SHORT).show();
                        //mref2.child("carPrice").setValue(num);
                        mref2.child("carPrice").setValue(car_price);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminCarDetails.this, "Car Deleted", Toast.LENGTH_SHORT).show();
                mref2 = mref.child(car_name);
                mref2.setValue(null);
                //startActivity(new Intent(AdminCarDetails.this, AdminCarLIst.class));
                finish();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}