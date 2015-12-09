package com.introtoandroid.viewsamples;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TextInputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textinput);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText text1 = (EditText) findViewById(R.id.EditText01);
        final EditText text2 = (EditText) findViewById(R.id.EditText02);
        final Spinner spin = (Spinner) findViewById(R.id.Spinner01);
        final Button submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                TextView text_sel = (TextView) spin.getSelectedView();
                Toast.makeText(TextInputActivity.this,
                        "1 = " + text1.getText() + " 2 = " + text2.getText() +
                                "\n spinner = " + text_sel.getText(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        final String[] COLORS = {"red", "green", "orange", "blue", "purple",
                "black", "yellow", "cyan", "magenta"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, COLORS);

        AutoCompleteTextView text = (AutoCompleteTextView)
                findViewById(R.id.AutoCompleteTextView01);
        text.setAdapter(adapter);

        MultiAutoCompleteTextView mtext = (MultiAutoCompleteTextView)
                findViewById(R.id.MultiAutoCompleteTextView01);
        mtext.setAdapter(adapter);
        mtext.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        final EditText text_filtered = (EditText) findViewById(R.id.input_filtered);
        text_filtered.setFilters(new InputFilter[]{new InputFilter.AllCaps(),
                new InputFilter.LengthFilter(2)});
    }
}