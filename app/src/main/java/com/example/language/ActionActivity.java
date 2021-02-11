package com.example.language;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class ActionActivity extends AppCompatActivity {
    TextView name_txt, preparation_txt, work_txt, rest_txt, cycle_txt, calm_txt, section_txt;
    String id, name, prep, work, rest, cycle, calm;
    int prep_int, work_int, rest_int, cycle_int, calm_int;
    int START_TIME_IN_MILLIS;
    private TextView mTextViewCountDown;
    private Button mButtonStartPause, mButtonNext;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis;
    private ArrayList<Integer> timers;
    private ArrayList<String> titles;
    private SoundPool soundPool;
    private int sound1;
    private boolean mTimerStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        name_txt = findViewById(R.id.name_txt);
        preparation_txt = findViewById(R.id.prep_ans);
        work_txt = findViewById(R.id.work_ans);
        rest_txt = findViewById(R.id.rest_ans);
        cycle_txt = findViewById(R.id.cycle_ans);
        calm_txt = findViewById(R.id.calm_ans);
        section_txt = findViewById(R.id.text_section);
        getAndSetIntentData();
        timers = new ArrayList<Integer>();
        titles = new ArrayList<String>();
        titles.add("Preparation");
        timers.add(new Integer(prep_int));
        for (int i = 0; i < cycle_int; i++){
            timers.add(new Integer(work_int));
            timers.add(new Integer(rest_int));
            titles.add("Work");
            titles.add("Rest");
        }
        timers.add(new Integer(calm_int));
        titles.add("Calm");
        START_TIME_IN_MILLIS=prep_int*1000;
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        mTimerStarted = false;
        mButtonNext = findViewById(R.id.button_next);
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCountDownTimer.cancel();
                timerCycle();
            }
        });
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mTimerStarted){
                    timerCycle();
                    playSound();
                }
                else if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                    playSound();
                }
            }
        });
        updateCountDownText();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(6)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(6, AudioManager.STREAM_MUSIC, 0);
        }
        sound1 = soundPool.load(this, R.raw.sound, 1);
    }
    public void playSound() {
        soundPool.play(sound1, 1, 1, 0, 0, 1);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }
    void getAndSetIntentData(){
        if(getIntent().hasExtra("id")&& getIntent().hasExtra("Name") && getIntent().hasExtra("Preparation") && getIntent().hasExtra("Work")&& getIntent().hasExtra("Rest")&& getIntent().hasExtra("Cycle")&& getIntent().hasExtra("Calm")){
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("Name");
            prep = getIntent().getStringExtra("Preparation");
            work = getIntent().getStringExtra("Work");
            rest = getIntent().getStringExtra("Rest");
            cycle = getIntent().getStringExtra("Cycle");
            calm = getIntent().getStringExtra("Calm");
            prep_int = Integer.parseInt(prep);
            work_int = Integer.parseInt(work);
            rest_int = Integer.parseInt(rest);
            cycle_int = Integer.parseInt(cycle);
            calm_int = Integer.parseInt(calm);
            name_txt.setText(name);
            preparation_txt.setText(prep);
            work_txt.setText(work);
            rest_txt.setText(rest);
            cycle_txt.setText(cycle);
            calm_txt.setText(calm);


        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
    private void timerCycle(){
        mTimerStarted = true;
        if(!timers.isEmpty()) {
            START_TIME_IN_MILLIS=(timers.get(0) + 1)*1000;
            mTimeLeftInMillis = START_TIME_IN_MILLIS;
            section_txt.setText(titles.get(0));
            startTimer();
            timers.remove(0);
            titles.remove(0);
        }
        else{
            mButtonStartPause.setVisibility(View.INVISIBLE);
            mButtonNext.setVisibility(View.INVISIBLE);
        }
    }
    private void startTimer() {
        mTimerRunning=true;
            mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mTimeLeftInMillis = millisUntilFinished;
                    updateCountDownText();
                }

                @Override
                public void onFinish() {
                        mTimerRunning = false;
                        timerCycle();
                }
            }.start();
            mButtonStartPause.setText("pause");

    }
    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("Start");
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }
}
