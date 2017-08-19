package com.example.keshavjoshi.firebase;

import android.app.ProgressDialog;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.ParseException;
import java.util.TimeZone;
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    Button b1;
    Button b2;
    TextView welcome;
    TextView name;
    String email;
    String Name;
    String Id;
    Context context=this;
    DatabaseReference dref;
    String remove;
    private GoogleApiClient mGoogleApiClient;
    Button b3;
    ProgressDialog progress;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        welcome = (TextView) findViewById(R.id.textView4);
        name = (TextView) findViewById(R.id.textView9);
        b3=(Button)findViewById(R.id.button6);
        final Intent intent = getIntent();
        name.setText(intent.getExtras().getString("key1").toString());
        Name = intent.getExtras().getString("key1").toString();
        email = intent.getExtras().getString("key2").toString();
        Id = intent.getExtras().getString("key3").toString();
        dref=FirebaseDatabase.getInstance().getReference();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
        int month = (Integer) c.get(Calendar.MONTH);


        remove = (c.get(Calendar.DATE) + "/" + month + 1 + "/" + c.get(Calendar.YEAR));

        // formattedDate have current date/time

        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentx = new Intent(context, POST_CAB.class);
                intentx.putExtra("key1", email);
                intentx.putExtra("key2", Id);
                intentx.putExtra("key3", Name);
                startActivity(intentx);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context, SEARCH_CAB.class);


                startActivity(intent1);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenty=new Intent(context,Update.class);
                intenty.putExtra("key2", Id);
                startActivity(intenty);
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                // [START_EXCLUDE]
                                updateUI(false);
                                // [END_EXCLUDE]
                            }



















                        });
                Intent intent = new Intent(MainActivity.this, login.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.del:

                dref.orderByChild("ID").equalTo(Id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren())
                            child.getRef().setValue(null);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                Toast.makeText(context, "YOUR POST IS DELETED SUCESSFULLY", Toast.LENGTH_LONG).show();



                break;

        }
        return true;
    }
    private void updateUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            // findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
           // mStatusTextView.setText(R.string.signed_out);

           // findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            // findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
