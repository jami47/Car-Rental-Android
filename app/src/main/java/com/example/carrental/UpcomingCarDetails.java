package com.example.carrental;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class UpcomingCarDetails extends AppCompatActivity {
    ImageView imageView;
    TextView classview, productionview, nameview;
    AppCompatButton backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_car_details);

        nameview = findViewById(R.id.carname_upcomingdetails);
        imageView = findViewById(R.id.img_upcomingdetails);
        classview = findViewById(R.id.class_upcomingdetails);
        productionview = findViewById(R.id.production_upcomingdetails);
        backbtn = findViewById(R.id.back_upcomingdetails);

        Intent intent = getIntent();
        CarApi car = intent.getParcelableExtra("car");

        classview.setText(car.clas);
        nameview.setText(car.title);
        productionview.setText(String.valueOf(car.production));
        Picasso.get().load(car.image).into(imageView);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}