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
    private TextView mTimer;
    private Button mStart;
    private Button mPause;
    private Button mReset;
    private Long mMiliSecondTime;
    private Long mStartTime;
    private Long mTimeBuffer;
    private Long mUpdateTime;
    private Handler mHandler;
    private int mHours;
    private int mSeconds;
    private int mMinutes;
    private int mMiliSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimer = (TextView)findViewById(R.id.timerText);
        mStart = (Button) findViewById(R.id.start_button);
        mPause = (Button) findViewById(R.id.pause_button);
        mReset = (Button) findViewById(R.id.reset_button);

        mMiliSecondTime = 0L;
        mStartTime = 0L;
        mTimeBuffer = 0L;
        mUpdateTime = 0L;
        mSeconds = 0;
        mMinutes =0;
        mHours =0;
        mMiliSeconds =0;

        mHandler = new Handler();

        //Start button
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStartTime = SystemClock.uptimeMillis();
                mHandler.postDelayed(mRunnable, 0);

                mReset.setEnabled(false);
                mStart.setEnabled(false);

            }
        });

        //Pause button
        mPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mTimeBuffer += mMiliSecondTime;
                mHandler.removeCallbacks(mRunnable);
                mReset.setEnabled(true);
                mStart.setEnabled(true);
            }
        });

        //Reset button
        mReset.setOnClickListener(new View.OnClickListener() {
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

                mTimer.setText(mResetValue);
            }
        });
    }

    /**
     * Runs the stopwatch
     */
    public Runnable mRunnable = new Runnable() {
        @Override
        public void run() {

            mMiliSecondTime = SystemClock.uptimeMillis() - mStartTime;
            mUpdateTime = mTimeBuffer + mMiliSecondTime;
            mSeconds = (int)(mUpdateTime/1000);
            mMinutes = mSeconds/60;
            mHours = mMinutes/60;
            mSeconds = mSeconds % 60;
            mMiliSeconds = (int)(mUpdateTime%1000);
            String time = String.format(Locale.getDefault(), "%02d:%02d:%02d:%03d", mHours, mMinutes, mSeconds, mMiliSeconds);

            mTimer.setText(time);

            mHandler.postDelayed(this,0);
        }
    };

    //Animating the back button function
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out);
    }
}