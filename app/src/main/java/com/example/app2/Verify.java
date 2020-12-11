package com.example.app2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Verify extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    private TextView textView1;
    ProgressDialog progressDialog;
    String m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        imageView = findViewById(R.id.image);
        textView = findViewById(R.id.name);
        textView1 = findViewById(R.id.likes);
        m  = getIntent().getStringExtra("name");

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Fetching...");
        progressDialog.setProgress(0);
        progressDialog.show();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(m);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                textView.setText(snapshot.child("name").getValue().toString());
                textView1.setText(snapshot.child("likes").getValue().toString()+" Likes");
                String url = snapshot.child("ImageLink").getValue().toString();

                Picasso.with(getApplicationContext()).load(url).into(imageView);
                progressDialog.setProgress(100);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}