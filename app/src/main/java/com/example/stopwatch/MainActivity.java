package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

/**
 * Loads UI of the StopWatch.
 *Provides the actions start, pause and restart of the stopwatch.
 */
public class MainActivity extends AppCompatActivity {
    private TextView tvTimer;
    private Button btnStart;
    private Button btnPause;
    private Button btnReset;
    private Long mMiliSecondTime;
    private Long mStartTime;
    private Long mTimeBuffer;
    private Long mUpdateTime;
    private Handler handler;
    private int mHours;
    private int mSeconds;
    private int mMinutes;
    private int mMiliSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTimer = (TextView)findViewById(R.id.timerText);
        btnStart = (Button) findViewById(R.id.start_button);
        btnPause = (Button) findViewById(R.id.pause_button);
        btnReset = (Button) findViewById(R.id.reset_button);

        mMiliSecondTime = 0L;
        mStartTime = 0L;
        mTimeBuffer = 0L;
        mUpdateTime = 0L;
        mSeconds = 0;
        mMinutes =0;
        mHours =0;
        mMiliSeconds =0;

        handler = new Handler();

        //Start button
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStartTime = SystemClock.uptimeMillis(); // the time of the start button clicked.
                handler.postDelayed(mRunnable, 0);

                btnReset.setEnabled(false); //disables the reset button
                btnStart.setEnabled(false); //disables the start button

            }
        });

        //Pause button
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mTimeBuffer += mMiliSecondTime; //saves the time in miliseconds
                handler.removeCallbacks(mRunnable); //pause the timer.
                btnReset.setEnabled(true); //enables the reset button
                btnStart.setEnabled(true); //enables the start button
            }
        });

        //Reset button
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mMiliSecondTime =0L;
                mStartTime = 0L;
                mTimeBuffer =0L;
                mUpdateTime = 0L;
                mSeconds = 0;
                mMinutes =0;
                mHours =0;
                mMiliSeconds =0;

                String mResetValue = "00:00:00:00";
                tvTimer.setText(mResetValue); // reset the timer
            }
        });
    }

    /**
     * Runs the stopwatch
     */
    public Runnable mRunnable = new Runnable() {
        @Override
        public void run() {

            mMiliSecondTime = SystemClock.uptimeMillis() - mStartTime; //time in miliseconds after clicking the start button
            mUpdateTime = mTimeBuffer + mMiliSecondTime; //if starts after a pause time buffer added
            mSeconds = (int)(mUpdateTime/1000);
            mMinutes = mSeconds/60;
            mHours = mMinutes/60;
            mSeconds = mSeconds % 60;
            mMiliSeconds = (int)(mUpdateTime%1000);
            String time = String.format(Locale.getDefault(), "%02d:%02d:%02d:%03d", mHours, mMinutes, mSeconds, mMiliSeconds);

            tvTimer.setText(time);

            handler.postDelayed(this,0);//setting the timer after every milisecond
        }
    };

    //Animating the back button function
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out);
    }
}