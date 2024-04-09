package com.example.carrental;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminCarLIst extends AppCompatActivity {

    ListView carlistview;
    ArrayAdapter<String> caradapter;
    ArrayList<String> cars = new ArrayList<>();
    DatabaseReference mref,addCarRef;
    AppCompatButton addcarbtn;
    String nameaddcar, priceaddcar, ratingaddcar = "0"; //Changed default rating from 3.5 to 0
    int pricecar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_car_list);

        addcarbtn = findViewById(R.id.buttonAddCar);
        carlistview = findViewById(R.id.carlist_admin);
        caradapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,cars);
        carlistview.setAdapter(caradapter);

        mref = FirebaseDatabase.getInstance().getReference().child("Cars");
        addCarRef = mref;
        /*mref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value_carname = snapshot.getKey();
                cars.add(value_carname);
                caradapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/ //End of mref
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cars.clear();
                for(DataSnapshot snapshot2: snapshot.getChildren()){
                    String car_value = snapshot2.getKey();
                    cars.add(car_value);
                }
                caradapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });//End of mref

        carlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AdminCarLIst.this, AdminCarDetails.class);
                //String test = cars.get(position);
                intent.putExtra("car_name",cars.get(position));
                startActivity(intent);
            }
        });

        addcarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(AdminCarLIst.this);
                dialog.setContentView(R.layout.add_car_layout);
                EditText editname = dialog.findViewById(R.id.edtcarname);
                EditText editprice = dialog.findViewById(R.id.edtcarprice);
                Button btnadd = dialog.findViewById(R.id.edtbutton);
                btnadd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nameaddcar = editname.getText().toString().trim();
                        priceaddcar = editprice.getText().toString().trim();
                        if(nameaddcar.isEmpty()){
                            editname.setError("Required");
                            editname.requestFocus();
                            return;
                        }
                        if(priceaddcar.isEmpty()){
                            editprice.setError("Required");
                            editprice.requestFocus();
                            return;
                        }
                        pricecar = Integer.parseInt(priceaddcar);
                        addCarRef.child(nameaddcar).child("avgrating").setValue(ratingaddcar);
                        addCarRef.child(nameaddcar).child("carName").setValue(nameaddcar);
                        addCarRef.child(nameaddcar).child("carPrice").setValue(priceaddcar);

                        //addCarRef.child(nameaddcar).child("carPrice").setValue(pricecar);
                        /*CarModel car = new CarModel(nameaddcar,pricecar,ratingaddcar);
                        addCarRef.child(nameaddcar).setValue(nameaddcar);
                        addCarRef = addCarRef.child(nameaddcar);
                        addCarRef.setValue(car);*/
                        Toast.makeText(AdminCarLIst.this, "Car Added "+nameaddcar, Toast.LENGTH_SHORT).show();
                        dialog.hide();
                    }
                });
                dialog.show();
            }
        });

    }
}