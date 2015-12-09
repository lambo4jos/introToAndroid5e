package com.introtoandroid.simplefragdialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.text.method.CharacterPickerDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class SimpleFragDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_frag_dialog);

        // HANDLE BUTTON CLICKS
        // Handle Toast Button
        Button launchToast = (Button) findViewById(R.id.Button_Toast);
        launchToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SimpleFragDialogActivity.this,
                        "This is a toast of the toast broadcasting system. This is only a toast.",
                        Toast.LENGTH_SHORT).show();
            }
        });
        // Handle Basic Dialog Button
        Button launchBasicDialog = (Button) findViewById(R.id.Button_SimpleDialog);
        launchBasicDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = BasicDialogFragment.newInstance();
                showDialogFragment(newFragment);
            }
        });
        // Handle Alert Dialog Button
        Button launchAlertDialog = (Button) findViewById(R.id.Button_AlertDialog);
        launchAlertDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = AlertDialogFragment.newInstance();
                showDialogFragment(newFragment);
            }
        });
        // Handle Character Picker Button
        Button launchCharDialog = (Button) findViewById(R.id.Button_CharacterPickerDialog);
        launchCharDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = CharacterPickerDialogFragment.newInstance();
                showDialogFragment(newFragment);
            }
        });
        // Handle Date Picker Dialog Button
        Button launchDateDialog = (Button) findViewById(R.id.Button_DatePickerDialog);
        launchDateDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = DatePickerDialogFragment.newInstance();
                showDialogFragment(newFragment);
            }
        });
        // Handle Progress Dialog Button
        Button launchProgressDialog = (Button) findViewById(R.id.Button_ProgressDialog);
        launchProgressDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = ProgressDialogFragment.newInstance();
                showDialogFragment(newFragment);
            }
        });
        // Handle Time Picker Dialog Button
        Button launchTimeDialog = (Button) findViewById(R.id.Button_TimePickerDialog);
        launchTimeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = TimePickerDialogFragment.newInstance();
                showDialogFragment(newFragment);
            }
        });
        // Handle Custom Dialog Button
        Button launchCustomDialog = (Button) findViewById(R.id.Button_CustomDialog);
        launchCustomDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = CustomDialogFragment.newInstance();
                showDialogFragment(newFragment);
            }
        });
    }

    public static class BasicDialogFragment extends DialogFragment {
        public static BasicDialogFragment newInstance() {
            return new BasicDialogFragment();
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Dialog basicDialog = new Dialog(getActivity());
            basicDialog.setTitle("Basic Dialog");
            return basicDialog;
        }
    }

    public static class AlertDialogFragment extends DialogFragment {
        public static AlertDialogFragment newInstance() {
            return new AlertDialogFragment();
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setTitle("Alert Dialog");
            alertDialog.setMessage("You have been alerted.");
            alertDialog.setIcon(android.R.drawable.btn_star);
            alertDialog.setPositiveButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getActivity(),
                                    "Clicked OK!", Toast.LENGTH_SHORT).show();
                        }
                    });
            return alertDialog.create();
        }
    }

    public static class CharacterPickerDialogFragment extends DialogFragment {
        public static CharacterPickerDialogFragment newInstance() {
            return new CharacterPickerDialogFragment();
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new CharacterPickerDialog(getActivity(),
                    new View(getActivity()), null, "AEIOU", true) {
                @Override
                public void onClick(View view) {
                    Button letter = (Button) view;
                    Toast.makeText(getActivity(),
                            "Clicked " + letter.getText() + "!", Toast.LENGTH_SHORT).show();
                    this.dismiss();
                }
            };
        }
    }

    public static class DatePickerDialogFragment extends DialogFragment {
        public static DatePickerDialogFragment newInstance() {
            return new DatePickerDialogFragment();
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Handle any DatePickerDialog initialization here
            // Always init the date picker to today's date
            int iDay, iMonth, iYear;
            Calendar cal = GregorianCalendar.getInstance();
            iDay = cal.get(Calendar.DAY_OF_MONTH);
            iMonth = cal.get(Calendar.MONTH);
            iYear = cal.get(Calendar.YEAR);
            DatePickerDialog dateDialog = new DatePickerDialog(getActivity(), dateListener, iYear, iMonth, iDay);
            dateDialog.setTitle("Pick a date");
            dateDialog.setMessage("Choose wisely");
            return dateDialog;
        }

        private DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //noinspection deprecation
                Time dtOfInterest = new Time();
                dtOfInterest.set(dayOfMonth, monthOfYear, year);
                long dtDob = dtOfInterest.toMillis(true);
                CharSequence strDate = DateFormat.format("MMMM dd, yyyy", dtDob);
                Toast.makeText(getActivity(), strDate, Toast.LENGTH_SHORT).show();
            }
        };
    }

    public static class ProgressDialogFragment extends DialogFragment {
        public static ProgressDialogFragment newInstance() {
            return new ProgressDialogFragment();
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            // make it a spinner
            progressDialog.setIndeterminate(true);
            progressDialog.setTitle("Showing Progress");
            progressDialog.setMessage("Houston, we're making progress!");
            return progressDialog;
        }
    }

    public static class TimePickerDialogFragment extends DialogFragment {
        public static TimePickerDialogFragment newInstance() {
            return new TimePickerDialogFragment();
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Handle any TimePickerDialog initialization here
            int iHour, iMinute;
            // Always init the time picker to current time
            Calendar cal1 = GregorianCalendar.getInstance();
            iHour = cal1.get(Calendar.HOUR_OF_DAY);
            iMinute = cal1.get(Calendar.MINUTE);
            TimePickerDialog timeDialog = new TimePickerDialog(getActivity(), timeListener, iHour, iMinute, true);
            timeDialog.setMessage("Take your time...");
            return timeDialog;
        }

        private TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Toast.makeText(getActivity(), "You picked " + hourOfDay + " hours and " + minute + " minutes.", Toast.LENGTH_SHORT).show();
            }
        };
    }

    public static class CustomDialogFragment extends DialogFragment {
        public static CustomDialogFragment newInstance() {
            return new CustomDialogFragment();
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.custom_dialog, (ViewGroup) getActivity().findViewById(R.id.root));
            final EditText p1 = (EditText) layout.findViewById(R.id.EditText_Pwd1);
            final EditText p2 = (EditText) layout.findViewById(R.id.EditText_Pwd2);
            final TextView error = (TextView) layout.findViewById(R.id.TextView_PwdProblem);
            p2.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    String strPass1 = p1.getText().toString();
                    String strPass2 = p2.getText().toString();
                    if (strPass1.equals(strPass2)) {
                        error.setText(R.string.settings_pwd_equal);
                    } else {
                        error.setText(R.string.settings_pwd_not_equal);
                    }
                }

                // ... other required overrides do nothing
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(layout);
            // Now configure the AlertDialog
            builder.setTitle(R.string.password_title);
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                    // We forcefully dismiss and remove the Dialog, so it
                    // cannot be used again
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String strPassword1 = p1.getText().toString();
                    String strPassword2 = p2.getText().toString();
                    if (strPassword1.equals(strPassword2)) {
                        Toast.makeText(getActivity(), "Matching passwords=" + strPassword2, Toast.LENGTH_SHORT).show();
                    }
                    // We forcefully dismiss and remove the Dialog, so it
                    // cannot be used again
                    dialog.dismiss();
                }
            });
            // Create the AlertDialog and show it
            return builder.create();
        }
    }

    void showDialogFragment(DialogFragment newFragment) {
        newFragment.show(getSupportFragmentManager(), null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_simple_frag_dialog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
