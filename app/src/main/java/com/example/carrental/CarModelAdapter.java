package com.example.carrental;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CarModelAdapter extends RecyclerView.Adapter<CarModelAdapter.ViewHolder> {

    /*DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();
    //String uid = FirebaseAuth.getInstance().getUid();
    String name;
    Long numberofusers;
    Double usernumbers,totalrating;
    float rating;*/
    Context context;
    ArrayList<CarModel> carModels;
    CarModelAdapter(Context context, ArrayList<CarModel>carModels){
        this.context = context;
        this.carModels = carModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cars_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override //Look here man
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //Shows contents on the recycler view
        holder.txtName.setText(carModels.get(position).carName);
        holder.txtPrice.setText(String.valueOf(carModels.get(position).carPrice));
        holder.ratingBar.setRating(Float.parseFloat(carModels.get(position).avgrating));
        //holder.ratingBar.setIsIndicator(false);

        /*name = carModels.get(position).carName;
        numberofusers;
        totalrating;

        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("ratings");
        firebaseDatabase.addValueEventListener(new ValueEventListener() {
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
                                System.out.println(rat);
                                totalrating+=rat;
                            }
                            //Look here
                            System.out.println(totalrating+" Inside loop");
                            //System.out.println(uid);
                            usernumbers = numberofusers.doubleValue();
                            usernumbers = totalrating/usernumbers;
                            rating = usernumbers.floatValue();
                            holder.ratingBar.setRating(rating);
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

        //holder.ratingBar.setIsIndicator(true);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowCarDetails.class);
                intent.putExtra("carname",carModels.get(position).carName);
                intent.putExtra("carprice",String.valueOf(carModels.get(position).carPrice));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return carModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtPrice; //Look here as well
        LinearLayout llrow;
        RatingBar ratingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.carname_recycler);
            txtPrice = itemView.findViewById(R.id.carprice_recycler);
            ratingBar = itemView.findViewById(R.id.rating_recycler);
            llrow = itemView.findViewById(R.id.llRow);
        }
    }
}
