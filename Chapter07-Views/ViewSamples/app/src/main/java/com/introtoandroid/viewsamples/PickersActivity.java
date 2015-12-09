package com.introtoandroid.viewsamples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class PickersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pickers);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TextView text = (TextView) findViewById(R.id.text_datetime);
        final DatePicker date = (DatePicker) findViewById(R.id.DatePicker01);
        final TimePicker time = (TimePicker) findViewById(R.id.TimePicker01);

        time.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                text.setText(calendar.getTime().toString());
            }
        });

        Calendar cal = Calendar.getInstance();

        date.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth, time.getCurrentHour(),
                                time.getCurrentMinute());
                        text.setText(calendar.getTime().toString());
                    }
                });
    }
}