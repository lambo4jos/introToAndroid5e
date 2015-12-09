package com.introtoandroid.passwordmatcher;

import android.app.Activity;
import android.app.KeyguardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class PasswordMatcherActivity extends Activity {
    EditText password;
    EditText matchingPassword;
    TextView passwordResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_matcher);

        password = (EditText) findViewById(R.id.password);
        matchingPassword = (EditText) findViewById(R.id.matchingPassword);
        passwordResult = (TextView) findViewById(R.id.passwordResult);

        Button button = (Button) findViewById(R.id.matchButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p = password.getText().toString();
                String mp = matchingPassword.getText().toString();

                if (p.equals(mp) && !p.isEmpty() && !mp.isEmpty()) {
                    passwordResult.setVisibility(View.VISIBLE);
                    passwordResult.setText(R.string.passwords_match_notice);
                    passwordResult.setTextColor(getResources().getColor(
                            R.color.green));
                } else {
                    passwordResult.setVisibility(View.VISIBLE);
                    passwordResult
                            .setText(R.string.passwords_do_not_match_notice);
                    passwordResult.setTextColor(getResources().getColor(
                            R.color.red));
                }
            }
        });
    }
}
