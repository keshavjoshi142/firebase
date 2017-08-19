package com.example.keshavjoshi.firebase;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.annotation.FloatRange;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class POST_CAB extends AppCompatActivity {
    TextView text1;
    TextView text2;
   EditText name;
    EditText price;
    EditText seats;
    Button but;
    Button but2;
    Button but3;
    TimePicker time;
     DatePicker date;
    Spinner destination;
    Spinner cab;
    DatabaseReference dref;
    DatePickerDialog datePickerDialog;
    EditText contact;
    TextView warning;
    TextView email;
    ProgressDialog progress;
    Context context=this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__cab);
        email=(TextView)findViewById(R.id.textView5);
        text1=(TextView)findViewById(R.id.textView);
        text2=(TextView)findViewById(R.id.textView2);
        name=(EditText)findViewById(R.id.name);
        //price=(EditText)findViewById(R.id.price);
        seats=(EditText)findViewById(R.id.editText4);
        contact=(EditText)findViewById(R.id.editText3);
        but=(Button)findViewById(R.id.button3);
        but2=(Button)findViewById(R.id.button4);
        warning=(TextView)findViewById(R.id.textView3);
        but3=(Button)findViewById(R.id.button5);
        final String[] Time = new String[1];
        final String[] Date = new String[1];

        email.setText(getIntent().getExtras().get("key1").toString());
        name.setText(getIntent().getExtras().get("key3").toString());
        destination=(Spinner)findViewById(R.id.Destination);
        cab=(Spinner)findViewById(R.id.cab);
        dref= FirebaseDatabase.getInstance().getReference();



        String[] string1 = {"Jaipur","Delhi"};
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, string1);
        destination.setAdapter(adapter1);

        String[] string2 = {"toofan","xylo","weganor","indica"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, string2);
        cab.setAdapter(adapter2);
       // GoogleSignInResult result= Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(POST_CAB.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                    for(int i=1;i<10;i++) {
                        if (monthOfYear+1==i){
                            Date[0] = (dayOfMonth + "/"+"0"
                                + (monthOfYear + 1) + "/" + year);
                            break;
                    }
                        else{
                            Date[0] = (dayOfMonth + "/"+
                                    + (monthOfYear + 1) + "/" + year);
                        }
                    }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }

        });

        but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(POST_CAB.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Time[0] =(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String NAME=name.getText().toString();
                final String DESTINATION=destination.getSelectedItem().toString();
                final String CAB=cab.getSelectedItem().toString();
                final String ID=getIntent().getExtras().getString("key2").toString();
                final String EMAIL= (String) email.getText();
                final String SEAT=seats.getText().toString();
                final String CONTACT=contact.getText().toString();
                final String TIME=Time[0];
                final String DATE=Date[0];
                if (NAME.equals("") || DESTINATION.equals("") || CAB.equals("") || SEAT.equals("") || CONTACT.equals("") || TIME == null || DATE == null) {
                    warning.setText("ALL FIELDS ARE COMPULSORY");

                } else {
                    final Map<String, String> userdata = new HashMap<String, String>();

                    userdata.put("Name", NAME);
                    userdata.put("Destination", DESTINATION);
                    userdata.put("Cab", CAB);
                    userdata.put("ID", ID);
                    userdata.put("Email", EMAIL);
                    userdata.put("Seats", SEAT);
                    userdata.put("Contact", CONTACT);
                    userdata.put("Time", Time[0]);
                    userdata.put("Date", Date[0]);
                    userdata.put("Search", DESTINATION + "_" + DATE);
                    userdata.put("Remove", TIME + "_" + DATE);

                    dref.push().setValue(userdata);


                    Toast.makeText(POST_CAB.this, "YOUR CAB HAS BEEN POSTED", Toast.LENGTH_SHORT).show();


                }


            }

        });






}
    }




