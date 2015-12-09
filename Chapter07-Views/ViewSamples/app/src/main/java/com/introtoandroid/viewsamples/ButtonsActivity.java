package com.introtoandroid.viewsamples;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ButtonsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.buttons);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Button basic_button = (Button) findViewById(R.id.basic_button);
        basic_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(ButtonsActivity.this, "Button clicked", Toast.LENGTH_SHORT).show();
            }
        });

        final ImageButton image_button = (ImageButton) findViewById(R.id.image_button);
        image_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(ButtonsActivity.this, "Image button clicked",
                        Toast.LENGTH_SHORT).show();
            }
        });


        final ToggleButton toggle_button = (ToggleButton) findViewById(R.id.toggle_button);
        toggle_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView tb = (TextView) findViewById(R.id.text_feature);
                tb.setText(toggle_button.isChecked() ?
                        "This feature is on" : "This feature is off");
                Toast.makeText(ButtonsActivity.this, "Toggle button clicked",
                        Toast.LENGTH_SHORT).show();
            }
        });

        final Switch basic_switch = (Switch) findViewById(R.id.switch1);
        basic_switch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String strState = basic_switch.isChecked() ?
                        "The switch is on" : "The switch is off";
                Toast.makeText(ButtonsActivity.this, strState, Toast.LENGTH_SHORT).show();
            }
        });

        final Button submit_button = (Button) findViewById(R.id.submit_demo);
        submit_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ToggleButton tb = (ToggleButton) findViewById(R.id.toggle_button);
                Toast.makeText(ButtonsActivity.this, tb.isChecked() ? "On" : "Off",
                        Toast.LENGTH_LONG).show();
                ButtonsActivity.this.finish();
            }
        });

        final CheckBox check_button = (CheckBox) findViewById(R.id.checkbox01);
        check_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) findViewById(R.id.checkbox01);
                cb.setText(check_button.isChecked() ?
                        "This option is checked" : "This option is not checked");
                Toast.makeText(ButtonsActivity.this, "CheckBox clicked",
                        Toast.LENGTH_SHORT).show();
            }
        });

        final RadioGroup group = (RadioGroup) findViewById(R.id.RadioGroup01);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                TextView tv = (TextView) findViewById(R.id.TextView01);
                if (checkedId != -1) {
                    RadioButton rb = (RadioButton) findViewById(checkedId);
                    if (rb != null) {
                        tv.setText("You chose: " + rb.getText());
                        Toast.makeText(ButtonsActivity.this, rb.getText() + " selected",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    tv.setText("Choose 1");
                }
            }
        });

        final Button clear_choice = (Button) findViewById(R.id.Button01);
        clear_choice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RadioGroup group = (RadioGroup) findViewById(R.id.RadioGroup01);
                if (group != null) {
                    group.clearCheck();
                    Toast.makeText(ButtonsActivity.this, "Choice cleared",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}