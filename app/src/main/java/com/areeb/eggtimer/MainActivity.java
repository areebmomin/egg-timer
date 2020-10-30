package com.areeb.eggtimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView timeTextView;
    SeekBar timerSeekBar;
    Boolean counterIsActive = false;
    Button goButton;
    CountDownTimer countDownTimer;
    String secondsString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing xml views
        timerSeekBar = findViewById(R.id.timeSeekBar);
        timeTextView = findViewById(R.id.timeTextView);
        goButton = findViewById(R.id.goButton);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(0);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void startTimer(View view)
    {
        int endTime = timerSeekBar.getProgress() * 1000 + 100;
        if (endTime <= 100)
            return;

        if (counterIsActive)
        {
            resetTimer();
        }
        else {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            goButton.setText(R.string.stop_btn);

            countDownTimer = new CountDownTimer(endTime, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                    timerSeekBar.setProgress(timerSeekBar.getProgress()-1);
                }

                @Override
                public void onFinish() {
                    resetTimer();
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();
                }
            };
            countDownTimer.start();
        }
    }

    public void updateTimer(int secondsLeft)
    {
        int minute = secondsLeft / 60;
        int secondsInteger = secondsLeft - (minute * 60);

        secondsString = Integer.toString(secondsInteger);
        if (secondsInteger <= 9)
        {
            secondsString = "0" + secondsInteger;
        }

        secondsString = minute + ":" + secondsString;

        timeTextView.setText(secondsString);
    }

    public void resetTimer()
    {
        timerSeekBar.setProgress(timerSeekBar.getProgress());
        timerSeekBar.setEnabled(true);
        timeTextView.setText(secondsString);
        countDownTimer.cancel();
        goButton.setText(getString(R.string.go));
        counterIsActive = false;
    }
}