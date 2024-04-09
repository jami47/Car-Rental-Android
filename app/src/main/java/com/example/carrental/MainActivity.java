package com.example.carrental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<CarModel> carModels;
    private androidx.appcompat.widget.Toolbar toolbar;
    private FirebaseAuth firebaseAuth;
    private Button logoutbtn;
    private CarModelAdapter adapter;
    DatabaseReference carRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerCars);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        toolbar =  findViewById(R.id.tb);
        setSupportActionBar(toolbar);

        carRef = FirebaseDatabase.getInstance().getReference().child("Cars");
        firebaseAuth = FirebaseAuth.getInstance();
        logoutbtn = findViewById(R.id.buttonLogOut);

        /*logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Toast.makeText(MainActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LogIn.class));
                finish();
            }
        });*/

        carModels = new ArrayList<>();
        /*CarModel carModel = new CarModel("Audi",350);
        CarModel carModel1 = new CarModel("Mercedes",415);
        carModels.clear();
        carModels.add(carModel);
        //carModels.add(carModel); carModels.add(carModel1);*/

        /*carModels.add(new CarModel("Audi",350));
        carModels.add(new CarModel("Mercedes",320));
        carModels.add(new CarModel("Hyundai",390));
        carModels.add(new CarModel("Suzuki",450));
        carModels.add(new CarModel("Honda",500));
        carModels.add(new CarModel("Toyota",510));
        carModels.add(new CarModel("Ford",550));
        carModels.add(new CarModel("Mustang",520));
        carModels.add(new CarModel("Hero",600));
        carModels.add(new CarModel("Ferrari",700));*/

        carRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                carModels.clear();
                for(DataSnapshot carsnapshot: snapshot.getChildren()){
                    String ang = carsnapshot.child("avgrating").getValue(String.class);
                    String cme = carsnapshot.child("carName").getValue(String.class);
                    String cce = carsnapshot.child("carPrice").getValue(String.class);
                    //int cce = Integer.parseInt(carsnapshot.child("carPrice").getValue().toString());//error
                    //int cce = carsnapshot.child("carPrice").getValue(Integer.class);
                    //int cce = 250;
                    CarModel car = new CarModel(ang,cme,cce); // = carsnapshot.getValue(CarModel.class);
                    carModels.add(car);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        /*CarModelAdapter */adapter = new CarModelAdapter(this,carModels);
        recyclerView.setAdapter(adapter);
        //recyclerView.notify();
    } // END OF OnCreate Method

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.profile_item){
            Toast.makeText(this, "Profile Page", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, ProfileUser.class));
        }
        if(item.getItemId()==R.id.logout_item){
            //Apadoto Signout er kaj ekhane rakhlam
            /*firebaseAuth.signOut();
            Toast.makeText(MainActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, LogIn.class));
            finish();*/
            showAlertDialog();
        }
        if(item.getItemId()==R.id.notification){
            Toast.makeText(this, "Upcoming Cars", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, UpcomingCars.class));
        }
        if(item.getItemId()==R.id.about_item){
            Toast.makeText(this, "About Us", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, AboutUs.class));
        }
        return super.onOptionsItemSelected(item);
    }


    //Alert For Log Out of app
    private void showAlertDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));
        alert.setTitle("ALERT");
        alert.setMessage("Do you want to Log Out?");
        alert.setIcon(R.drawable.baseline_logout_24);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                firebaseAuth.signOut();
                Toast.makeText(MainActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LogIn.class));
                finish();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alert.show();
    }
}