package com.example.carrental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URI;

public class ShowCarDetails extends AppCompatActivity {

    String[] city_items = {"Dhaka", "Chittagong", "Sylhet", "Khulna", "Rajshahi", "Mymensingh", "Barisal"};
    AutoCompleteTextView source_textview, destination_textview; String item, source_city, destination_city;
    AppCompatButton rentbtn;
    int rent,total_rent=0; TextView totalrent;
    TextInputLayout source_layout;
    ArrayAdapter<String> adapterItems;
    TextView carname,carprice;
    RatingBar ratingBar;
    ImageView carimage;
    FirebaseAuth firebaseAuth;
    DatabaseReference firebaseDatabase,userRef,userRef2,userRef2_helper, carRef;
    Double totalrating,usernumbers;
    String avgrating,price;
    float rating;
    Long numberofusers;

    URI uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_car_details);

        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("ratings");
        userRef = FirebaseDatabase.getInstance().getReference().child("ratings");
        userRef2 = FirebaseDatabase.getInstance().getReference().child("ratings");
        userRef2_helper = FirebaseDatabase.getInstance().getReference().child("ratings");
        carRef = FirebaseDatabase.getInstance().getReference().child("Cars");

        firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getUid();

        rentbtn = findViewById(R.id.purchase_showdetails);
        totalrent = findViewById(R.id.totalprice_showdetails);
        carname = findViewById(R.id.carname_showdetails);
        carprice = findViewById(R.id.carprice_showdetails);
        carimage = findViewById(R.id.carimage_showdetails);
        ratingBar = findViewById(R.id.rating_showdetails);

        destination_textview = findViewById(R.id.destination_dropdown);
        source_textview = findViewById(R.id.source_dropdown);
        source_layout = findViewById(R.id.source_layout);

        adapterItems = new ArrayAdapter<String>(this,R.layout.city_dropdown_items,city_items);

        destination_textview.setAdapter(adapterItems);
        source_textview.setAdapter(adapterItems);
        source_textview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //item = adapterItems.getItem(position).toString().trim();
                item = parent.getItemAtPosition(position).toString().trim();
                source_city = item;
                total_rent = calculateRent(source_city, destination_city);
                if(total_rent==1){
                    totalrent.setText("Total Rent");
                }
                else if(total_rent!=1){
                    String test = String.valueOf(total_rent);
                    totalrent.setText("Total Rent: "+test);
                }

                //source_textview.setText(item);
                //Toast.makeText(ShowCarDetails.this, "Clicked On "+item, Toast.LENGTH_SHORT).show();
            }
        });
        destination_textview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //item = adapterItems.getItem(position).toString().trim();
                item = parent.getItemAtPosition(position).toString().trim();
                destination_city = item;
                total_rent = calculateRent(source_city, destination_city);
                if(total_rent==1){
                    totalrent.setText("Total Rent");
                }
                else if(total_rent!=1){
                    String test = String.valueOf(total_rent);
                    totalrent.setText("Total Rent: "+test);
                }
                //source_textview.setText(item);
                //Toast.makeText(ShowCarDetails.this, "Clicked On "+item, Toast.LENGTH_SHORT).show();
            }
        });




        Intent intent = getIntent();
        String name = intent.getStringExtra("carname");
        price = intent.getStringExtra("carprice"); //declared global
        rent = Integer.parseInt(price);

        carprice.setText(price);
        carname.setText(name);

        //Showing the rating on entrance of activity
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(name)){
                    firebaseDatabase = userRef.child(name)
                            .child("users")
                            .child(uid)
                            .child("rating");
                    firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String temp = snapshot.getValue(String.class);
                            if(temp!=null){
                                rating = Float.parseFloat(temp);
                                ratingBar.setRating(rating);
                            }
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

        //Update database everytime rating changes
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //if(snapshot.hasChild(name)){
                            //float temp_rating = ratingBar.getRating();
                            String temp = String.valueOf(rating);
                            userRef.child(name)
                                    .child("users")
                                    .child(uid)
                                    .child("rating").setValue(temp);
                        //}
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                //Average Rating
                userRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(name)){
                            userRef2 = userRef2_helper.child(name)
                                    .child("users");
                            userRef2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                    numberofusers = snapshot2.getChildrenCount();
                                    totalrating = 0.0;
                                    for(DataSnapshot usersnapshot: snapshot2.getChildren()) {
                                        String rating = usersnapshot.child("rating").getValue(String.class);
                                        Double rat = Double.parseDouble(rating);
                                        //System.out.println(rat);
                                        totalrating += rat;
                                    }
                                    usernumbers = numberofusers.doubleValue();
                                    usernumbers = totalrating/usernumbers;
                                    avgrating = String.valueOf(usernumbers);
                                    userRef2_helper.child(name)
                                            .child("ratingavg")
                                            .child("ratingavg").setValue(avgrating);
                                    carRef.child(name)
                                            .child("avgrating").setValue(avgrating);
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
            }
        });


        //Average Rating
        /*firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(name)){
                    firebaseDatabase = firebaseDatabase.child(name)
                            .child("users");
                            //.child(uid);
                    firebaseDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot2) {
                            numberofusers = snapshot2.getChildrenCount();
                            totalrating = 0.0;
                            for(DataSnapshot usersnapshot: snapshot2.getChildren()){
                                String rating = usersnapshot.child("rating").getValue(String.class);
                                Double rat = Double.parseDouble(rating);
                                //System.out.println(rat);
                                totalrating+=rat;
                            }
                            //Look here
                            //System.out.println(totalrating+" Inside loop");
                            //System.out.println(uid);
                            usernumbers = numberofusers.doubleValue();
                            usernumbers = totalrating/usernumbers;
                            //rating = usernumbers.floatValue();
                            //ratingBar.setRating(rating);
                            //rating = snapshot2.child("rating").getValue(String.class);
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
        });*/

        //usernumbers = numberofusers.doubleValue();
        //usernumbers = totalrating/2.0;
        //System.out.println(totalrating+" Outside loop");
        //float f;

        //ratingBar.setRating(f);

        rentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(total_rent==0 || total_rent==1){
                    Toast.makeText(ShowCarDetails.this, "Select Source and Destination", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(new Intent(ShowCarDetails.this, PaymentActivity.class));
                finish();
            }
        });

        //Set car images
        if(name.equals("Audi")){
            carimage.setImageResource(R.drawable.audi);
        }
        else if(name.equals("Hyundai")){
            carimage.setImageResource(R.drawable.hyundai);
        }
        else if(name.equals("Tesla")){
            carimage.setImageResource(R.drawable.tesla);
        }
        else if(name.equals("Mercedes")){
            carimage.setImageResource(R.drawable.mercedes);
        }
        else if (name.equals("Honda")){
            carimage.setImageResource(R.drawable.honda);
        }
        else if(name.equals("Suzuki")){
            carimage.setImageResource(R.drawable.suzuki);
        }
        else if(name.equals("Ford")){
            carimage.setImageResource(R.drawable.ford);
        }
        else if(name.equals("Ferrari")){
            carimage.setImageResource(R.drawable.ferrari);
        }
        else if(name.equals("Toyota")){
            carimage.setImageResource(R.drawable.toyota);
        }
        else if(name.equals("Corolla")){
            carimage.setImageResource(R.drawable.corolla);
        }

    }

    int calculateRent(String s, String d){
        if(s==null || d==null){
            return 1;
        }
        if(s==d){
            Toast.makeText(this, "Source & Destination can't be same", Toast.LENGTH_SHORT).show();
            return 1;
        }
        // Combinations
        if ((s == "Dhaka" && d == "Chittagong") || (s == "Chittagong" && d == "Dhaka")) {
            return rent*10;
        }

        if ((s == "Dhaka" && d == "Sylhet") || (s == "Sylhet" && d == "Dhaka")) {
            return rent*23;
        }

        if ((s == "Dhaka" && d == "Khulna") || (s == "Khulna" && d == "Dhaka")) {
            return rent*12;
        }

        if ((s == "Dhaka" && d == "Rajshahi") || (s == "Rajshahi" && d == "Dhaka")) {
            return rent*15;
        }

        if ((s == "Dhaka" && d == "Mymensingh") || (s == "Mymensingh" && d == "Dhaka")) {
            return rent*16;
        }

        if ((s == "Dhaka" && d == "Barisal") || (s == "Barisal" && d == "Dhaka")) {
            return rent*18;
        }

        if ((s == "Chittagong" && d == "Sylhet") || (s == "Sylhet" && d == "Chittagong")) {
            return rent*20;
        }

        if ((s == "Chittagong" && d == "Khulna") || (s == "Khulna" && d == "Chittagong")) {
            return rent*25;
        }

        if ((s == "Chittagong" && d == "Rajshahi") || (s == "Rajshahi" && d == "Chittagong")) {
            return rent*29;
        }

        if ((s == "Chittagong" && d == "Mymensingh") || (s == "Mymensingh" && d == "Chittagong")) {
            return rent*30;
        }

        if ((s == "Chittagong" && d == "Barisal") || (s == "Barisal" && d == "Chittagong")) {
            return rent*35;
        }

        if ((s == "Sylhet" && d == "Khulna") || (s == "Khulna" && d == "Sylhet")) {
            return rent*40;
        }

        if ((s == "Sylhet" && d == "Rajshahi") || (s == "Rajshahi" && d == "Sylhet")) {
            return rent*37;
        }

        if ((s == "Sylhet" && d == "Mymensingh") || (s == "Mymensingh" && d == "Sylhet")) {
            return rent*22;
        }

        if ((s == "Sylhet" && d == "Barisal") || (s == "Barisal" && d == "Sylhet")) {
            return rent*42;
        }

        if ((s == "Khulna" && d == "Rajshahi") || (s == "Rajshahi" && d == "Khulna")) {
            return rent*16;
        }

        if ((s == "Khulna" && d == "Mymensingh") || (s == "Mymensingh" && d == "Khulna")) {
            return rent*29;
        }

        if ((s == "Khulna" && d == "Barisal") || (s == "Barisal" && d == "Khulna")) {
            return rent*13;
        }

        if ((s == "Rajshahi" && d == "Mymensingh") || (s == "Mymensingh" && d == "Rajshahi")) {
            return rent*33;
        }

        if ((s == "Rajshahi" && d == "Barisal") || (s == "Barisal" && d == "Rajshahi")) {
            return rent*31;
        }

        if ((s == "Mymensingh" && d == "Barisal") || (s == "Barisal" && d == "Mymensingh")) {
            return rent*34;
        }

        return 1;
    }

}