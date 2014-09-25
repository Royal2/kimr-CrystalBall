package android.kimr.crystalball;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;


public class CrystalBall extends Activity {
    private AnimationDrawable rocketAnimation;
    private TextView answerText;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float acceleration;
    private float currentAcceleration;
    private float previousAcceleration;

    private final SensorEventListener sensorListener = new SensorEventListener() {
        //Sensor Acceleration
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            previousAcceleration = currentAcceleration;
            currentAcceleration = FloatMath.sqrt(x * x + y * y + z * z);
            float delta = currentAcceleration - previousAcceleration;
            acceleration = acceleration * 0.9f + delta;

            //acceleration > 15 - Sensitivity for Shake

            //shake notification - text
            //media player - sound effect
            //Prediction text slide effect - animation
            if(acceleration > 15){
                //Shake Text Display
                Toast toast = Toast.makeText(getApplication(), "device has shaken", Toast.LENGTH_SHORT);
                toast.show();

                //Shake sound effects
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplication(), R.raw.crystal_ball);
                mediaPlayer.start(); // no need to call prepare(); create() does that for you

                //Shake predictions text animation
                answerText = (TextView) findViewById(R.id.answerText);
                answerText.setText(Predictions.get().getPredictions()); //fetching prediction message
                answerText.startAnimation(AnimationUtils.loadAnimation(CrystalBall.this, android.R.anim.slide_in_left)); //selecting animation --> starts animation

                /*
                ValueAnimator =(TextView) findViewById(R.id.answerText);
                AnimatorSet = (Predictions.get().getPredictions());
                ObjectAnimator animation = ObjectAnimator.ofFloat(getApplicationContext(), "rotation", 0f, 360f);
                animation.setDuration(2000);
                animation.start();
                */

            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    //create, resume, pause.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crystal_ball);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        acceleration = 0.0f;
        currentAcceleration = SensorManager.GRAVITY_EARTH;
        previousAcceleration = SensorManager.GRAVITY_EARTH;
    }

    @Override
    protected void onResume() {

        super.onResume();
        sensorManager.registerListener(sensorListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {

        super.onPause();
        sensorManager.unregisterListener(sensorListener);
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.crystal_ball, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/
}