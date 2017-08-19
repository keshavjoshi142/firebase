package com.example.keshavjoshi.firebase;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class cab_details extends AppCompatActivity {
    TextView a;
    TextView b;
    TextView c;
    TextView d;
    TextView e;
    TextView f;
    TextView h;
    TextView seats;
    TextView name;
    TextView destination;
    TextView date;
    TextView time;
    TextView price;
    TextView contact;
    DatabaseReference dref;

    String txtPhn;
    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cab_details);
        a = (TextView) findViewById(R.id.name);
        b = (TextView) findViewById(R.id.destination);
        c = (TextView) findViewById(R.id.date);
        d = (TextView) findViewById(R.id.time);
        //e = (TextView) findViewById(R.id.price);
        f = (TextView) findViewById(R.id.contact);
        h = (TextView) findViewById(R.id.textView8);

        seats = (TextView) findViewById(R.id.textView10);
        name = (TextView) findViewById(R.id.textView13);
        destination = (TextView) findViewById(R.id.textView14);
        date = (TextView) findViewById(R.id.textView16);
        time = (TextView) findViewById(R.id.textView17);
        //price = (TextView) findViewById(R.id.textView15);
        contact = (TextView) findViewById(R.id.textView18);

        String name1 = getIntent().getExtras().getString("key");
        dref = FirebaseDatabase.getInstance().getReference();
        dref.orderByChild("Name").equalTo(name1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    cab c = child.getValue(cab.class);
                    name.setText(c.Name());
                    destination.setText(c.Destination());
                    // price.setText(c.Price());
                    date.setText(c.Date());
                    time.setText(c.Time());
                    contact.setText(c.Contact());
                    txtPhn = c.Contact();
                    seats.setText(c.Seats());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
