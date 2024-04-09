package com.example.carrental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UpcomingCars extends AppCompatActivity {

    //ImageView imageView;
    ListView listView;
    String url;
    ArrayList<String> carnames = new ArrayList<String>();
    ArrayList<CarApi> cars = new ArrayList<CarApi>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_cars);

        listView = findViewById(R.id.carlist_upcoming);

        //imageView = findViewById(R.id.img_upcoming);

        //url = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Red92VR4rear.jpg/400px-Red92VR4rear.jpg";

        //Picasso.get().load(url).into(imageView);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://api.myjson.online/v1/records/4805dfda-abce-47df-95f0-9776e01b7293";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        //String companyname = jsonObject1.getString("company");
                        String img = jsonObject1.getString("image");
                        String title = jsonObject1.getString("title");
                        String clas =  jsonObject1.getString("class");
                        int production = jsonObject1.getInt("start_production");
                        carnames.add(title);
                        CarApi car = new CarApi(img,title,clas,production);
                        cars.add(car);
                    }

                    ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,carnames);
                    listView.setAdapter(arrayAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            /*Intent intent = new Intent(UnmarriedActivity.this, HobbiesActivity.class);
                            String test = hobbies.get(position);
                            intent.putExtra("hobbie",test);
                            intent.put*/
                            Intent intent = new Intent(UpcomingCars.this, UpcomingCarDetails.class);
                            CarApi car = cars.get(position);
                            intent.putExtra("car",car);
                            startActivity(intent);
                        }
                    });
                }
                catch (JSONException e){
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
}