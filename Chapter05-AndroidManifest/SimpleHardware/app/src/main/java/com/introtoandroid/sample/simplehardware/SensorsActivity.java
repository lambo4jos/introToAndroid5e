package com.introtoandroid.sample.simplehardware;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SensorsActivity extends AppCompatActivity implements SensorEventListener {

    private static final String DEBUG_TAG = "SensorsActivity";
    private SensorManager sensors = null;
    private static final SparseIntArray buttonSensorMap = new SparseIntArray() {
        {
            put(R.id.sensor_accel, Sensor.TYPE_ACCELEROMETER);
            put(R.id.sensor_gravity, Sensor.TYPE_GRAVITY);
            put(R.id.sensor_linear_accel, Sensor.TYPE_LINEAR_ACCELERATION);
            put(R.id.sensor_mag, Sensor.TYPE_MAGNETIC_FIELD);
            put(R.id.sensor_orient, Sensor.TYPE_ORIENTATION);
            put(R.id.sensor_gyro, Sensor.TYPE_GYROSCOPE);
            put(R.id.sensor_rot_vector, Sensor.TYPE_ROTATION_VECTOR);
            put(R.id.sensor_light, Sensor.TYPE_LIGHT);
            put(R.id.sensor_prox, Sensor.TYPE_PROXIMITY);
            put(R.id.sensor_pressure, Sensor.TYPE_PRESSURE);
            put(R.id.sensor_rel_humid, Sensor.TYPE_RELATIVE_HUMIDITY);
            put(R.id.sensor_ambient_temp, Sensor.TYPE_AMBIENT_TEMPERATURE);
            put(R.id.sensor_temp, Sensor.TYPE_TEMPERATURE);
            put(R.id.sensor_step_detector, Sensor.TYPE_STEP_DETECTOR);
            put(R.id.sensor_step_counter, Sensor.TYPE_STEP_COUNTER);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);
        final TextView status = (TextView) findViewById(R.id.status);
        sensors = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        final RadioGroup sensorPicker = (RadioGroup) findViewById(R.id.sensor_group);

        final Button start = (Button) findViewById(R.id.start_sensor);
        final Button stop = (Button) findViewById(R.id.stop_sensor);

        sensorPicker
                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        Log.v(DEBUG_TAG, "onCheckedChanged");
                        handleStartSensor(status, start, stop, checkedId);
                    }

                });

        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(DEBUG_TAG, "onClickListener");
                int checkedId = sensorPicker.getCheckedRadioButtonId();
                if (checkedId == -1) {
                    Toast.makeText(SensorsActivity.this, "Select a sensor first", Toast.LENGTH_SHORT).show();
                } else {
                    handleStartSensor(status, start, stop, checkedId);
                }
            }

        });

        stop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sensors.unregisterListener(SensorsActivity.this);
                start.setVisibility(View.VISIBLE);
                stop.setVisibility(View.GONE);
            }

        });
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        String sensorName = sensor.getName();

        TextView status = (TextView) findViewById(R.id.status);

        status.setText("Sensor: " + sensorName + " changed accuracy to: "
                + accuracy);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // do as little as possible here; offload work to another thread if
        // needed; don't block
        StringBuilder sensorMessage = new StringBuilder(event.sensor.getName())
                .append(" new values: ");

        for (float value : event.values) {
            sensorMessage.append("[").append(value).append("]");
        }

        sensorMessage.append(" with accuracy ").append(event.accuracy);
        sensorMessage.append(" at timestamp ").append(event.timestamp);

        sensorMessage.append(".");

        TextView status = (TextView) findViewById(R.id.status);
        status.setText(sensorMessage);
    }

    public void onPause() {
        if (sensors != null) {
            sensors.unregisterListener(this);
        }
        super.onPause();
    }

    private void handleStartSensor(TextView status, Button start, Button stop,
                                   int checkedId) {
        int sensorId = buttonSensorMap.get(checkedId);
        Sensor defaultSensor = sensors.getDefaultSensor(sensorId);
        boolean isAvailable = false;
        if (defaultSensor != null) {
            isAvailable = sensors.registerListener(SensorsActivity.this,
                    defaultSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (!isAvailable) {
            RadioButton checked = (RadioButton) findViewById(checkedId);
            status.setText("Current sensor (" + checked.getText()
                    + ") not available.");
        } else {
            stop.setVisibility(View.VISIBLE);
            start.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sensors, menu);
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
