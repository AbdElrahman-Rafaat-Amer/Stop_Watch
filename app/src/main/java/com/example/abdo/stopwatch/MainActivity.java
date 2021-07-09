package com.example.abdo.stopwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.transition.Transition;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private CardView cardView;
    private ImageView playButton, pauseButton, stopButton, timeLabsButton, arrow;
    private TextView timeView, timeViewMS, timeLabs;
    private int timer = 0, ms = 0, lapCount = 0, hours, minutes, seconds;
    private String MS, time;
    private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        playButton = findViewById(R.id.playBtn);
        pauseButton = findViewById(R.id.pauseBtn);
        stopButton = findViewById(R.id.stopBtn);
        timeLabsButton = findViewById(R.id.timeLapseBtn);
        timeView = findViewById(R.id.time_view);
        timeViewMS = findViewById(R.id.time_view_ms);
        timeLabs = findViewById(R.id.timeLapse);
        arrow = findViewById(R.id.arrow);
        cardView = findViewById(R.id.card_view);

        runTime();

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timeLabs.getVisibility() == View.GONE) {
                    arrow.setImageResource(R.drawable.up_arrow);
                    timeLabs.setVisibility(View.VISIBLE);
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());

                } else {
                    arrow.setImageResource(R.drawable.down_arrow);
                    timeLabs.setVisibility(View.GONE);
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                }

            }
        });
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                playButton.setVisibility(View.GONE);
                pauseButton.setVisibility(View.VISIBLE);
                stopButton.setVisibility(View.GONE);
                timeLabsButton.setVisibility(View.VISIBLE);

                running = true;

            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                playButton.setVisibility(View.VISIBLE);
                pauseButton.setVisibility(View.GONE);
                stopButton.setVisibility(View.VISIBLE);
                timeLabsButton.setVisibility(View.GONE);

                running = false;

            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                playButton.setVisibility(View.VISIBLE);
                pauseButton.setVisibility(View.GONE);
                stopButton.setVisibility(View.GONE);
                timeLabsButton.setVisibility(View.GONE);

                running = false;
                timeView.setText("00:00:00");
                timeViewMS.setText("00");
                timeLabs.setText("");
                timer = 0;
                lapCount = 0;

                arrow.setImageResource(R.drawable.down_arrow);
                timeLabs.setVisibility(View.GONE);
                TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
            }
        });

        timeLabsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeLapseFun();
            }
        });
    }

    private void timeLapseFun() {
        lapCount++;

        String laptext = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
        String msString = String.format(Locale.getDefault(), "%02d", ms);

        // adding ms to lap text
        laptext = laptext + ":" + msString;

        if (lapCount >= 10) {
            laptext = " Lap " + lapCount + " ------------->       " + laptext + " \n" + timeLabs.getText();
        } else {
            laptext = " Lap " + lapCount + " --------------->       " + laptext + " \n" + timeLabs.getText();

        }

        //showing simple toast message to user
        Toast.makeText(MainActivity.this, "Lap " + lapCount, Toast.LENGTH_SHORT).show();

        // showing the lap text
        timeLabs.setText(laptext);
    }

    private void runTime() {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                hours = timer / 3600;
                minutes = (timer % 3600) / 60;
                seconds = timer % 60;

                if (running) {
                    time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
                    timeView.setText(time);
                    timer++;
                }
                handler.postDelayed(this, 1000);
            }
        });

        Handler handler2 = new Handler();
        handler2.post(new Runnable() {
            @Override
            public void run() {
                if (ms >= 99) {
                    ms = 0;
                }

                if (running) {
                    MS = String.format(Locale.getDefault(), "%02d", ms);
                    timeViewMS.setText(MS);
                    ms++;
                }

                handler2.postDelayed(this, 1);
            }
        });
    }


}