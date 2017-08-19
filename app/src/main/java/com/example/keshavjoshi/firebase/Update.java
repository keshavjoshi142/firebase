package com.example.keshavjoshi.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Update extends AppCompatActivity {
    EditText change;
    DatabaseReference dref;
    Button but;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        change=(EditText)findViewById(R.id.editText);
        but=(Button)findViewById(R.id.button8);
        dref=FirebaseDatabase.getInstance().getReference();
      final  String id= (String) getIntent().getExtras().get("key2");

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  String seats=change.getText().toString();
                dref.orderByChild("ID").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            child.getRef().child("Seats").setValue(seats);
                        }
                    }




                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });
    }
}
