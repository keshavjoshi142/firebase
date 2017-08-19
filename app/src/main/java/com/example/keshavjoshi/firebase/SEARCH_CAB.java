package com.example.keshavjoshi.firebase;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

public class SEARCH_CAB extends AppCompatActivity {
    Button but2;
    Spinner destination;
    EditText seats;
    Button search;
    DatePickerDialog datePickerDialog;
    final String[] Date = new String[1];
    Context context=this;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__cab);
        but2=(Button)findViewById(R.id.button4);
        destination=(Spinner)findViewById(R.id.spinner);
        seats=(EditText)findViewById(R.id.editText4);
        search=(Button)findViewById(R.id.button7);
        text=(TextView)findViewById(R.id.textView6);
        String[] string1 = {"Jaipur","Delhi"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, string1);
        destination.setAdapter(adapter1);

        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(SEARCH_CAB.this,
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





         search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String y = seats.getText().toString();
                String x = destination.getSelectedItem().toString();
                final String a = x + "_" + Date[0];
                text.setText(a);
                if (y.equals("") || x.equals("") || Date.length == 0) {
                    text.setText("ALL FIELDS ARE COMPULSORY");
                } else {

                    Intent intent = new Intent(context, view.class);
                    intent.putExtra("key", a);
                    intent.putExtra("key1", y);
                    startActivity(intent);
                }
            }

        });

    }
}

