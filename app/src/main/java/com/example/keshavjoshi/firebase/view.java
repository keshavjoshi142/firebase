package com.example.keshavjoshi.firebase;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class view extends AppCompatActivity {
    ListView listView;
    DatabaseReference dref;
    final ArrayList<String> childrens = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    Context context=this;
    String id;
    ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, childrens);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        final String x=getIntent().getExtras().getString("key");
        final String seat=getIntent().getExtras().getString("key1");
        final int seats=Integer.parseInt(seat);
        dref= FirebaseDatabase.getInstance().getReference();
        progress = ProgressDialog.show(this, "Loading...",
                "Loading Posted Cabs....", true);
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    Thread.sleep(5000);
                    dref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                            for (DataSnapshot child : children) {
                                cab c = child.getValue(cab.class);
                                int a = Integer.parseInt(c.Seats());
                                String search=c.Search();
                                if (a >= seats && x.equals(search)) {
                                    childrens.add(c.Name());
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError){

                             }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        progress.dismiss();
                    }
                });
            }
        }).start();




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name=listView.getItemAtPosition(position).toString();

                Intent intent1=new Intent(context,cab_details.class).putExtra("key",name);
                startActivity(intent1);

            }
        });
    }
}
