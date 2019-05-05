package com.example.braintrainer;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button bt1;
    Button bt2;
    Button bt3;
    Button bt4;
    Button go;
    Button playAgain;
    TextView countDownTextView;
    TextView equationTextView;
    TextView scoreTextView;
    TextView resultTextView;
    TextView answer;
    CountDownTimer countDownTimer;

    LinearLayout goLayout;
    LinearLayout linearLayout;
    GridLayout gridLayout;
    LinearLayout resultLayout;

    Map<Integer,String> colors = new HashMap<>();
    Map<Integer,Integer> background = new HashMap<>();

    //answer for equation

    ArrayList<String> answers = new ArrayList<>();
    int locationOfCorrectAnswer;
    int locationOfBackGround;
    int score = 0;;
    int questions = 0;
    Random rand;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize
        bt1 = (Button) findViewById(R.id.button1);
        bt2 = (Button) findViewById(R.id.button2);
        bt3 = (Button) findViewById(R.id.button3);
        bt4 = (Button) findViewById(R.id.button4);
        go = (Button) findViewById(R.id.go);
        playAgain = (Button) findViewById(R.id.playAgain);

        countDownTextView = (TextView) findViewById(R.id.countDownTextView);
        equationTextView = (TextView) findViewById(R.id.equationTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        answer = (TextView) findViewById(R.id.answer);


        goLayout = (LinearLayout) findViewById(R.id.goLayout);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        resultLayout = (LinearLayout) findViewById(R.id.resultLayout);

        colors.put(0,"RED");
        colors.put(1,"GREEN");
        colors.put(2,"YELLOW");
        colors.put(3,"BLUE");

        background.put(0,Color.RED);
        background.put(1,Color.GREEN);
        background.put(2,Color.YELLOW);
        background.put(3,Color.BLUE);

    }



    public void timer(int secsLeft){
        int mins = (int) secsLeft/60;
        int secs = secsLeft - mins * 60;

        if(secs == 0) countDownTextView.setText(mins+":00s");
        else if(secs < 10) countDownTextView.setText(mins+":0"+secs+"s");
        else countDownTextView.setText(mins+":"+secs+"s");
    }

    public void click(View view){
        if(view.getTag().toString().equals(locationOfCorrectAnswer+"")) {
            answer.setText("Correct!");
            score++;

        }else{
            answer.setText("Incorrect!");
        }
        questions++;
        scoreTextView.setText(score+"/"+questions);

        generateQuestion();
    }

    public void goClick(View view){
        goLayout.setVisibility(View.INVISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
        gridLayout.setVisibility(View.VISIBLE);
        startGame();
    }

    public static Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

    public void generateQuestion(){
        rand = new Random();
        answers.clear();

        int a = rand.nextInt(4);
        equationTextView.setText("Pick "+colors.get(a) +" Color");

        if(colors.get(a).equals("RED")) locationOfBackGround = (int) getKeyFromValue(background,Color.RED);
        else if(colors.get(a).equals("GREEN")) locationOfBackGround = (int) getKeyFromValue(background,Color.GREEN);
        else if(colors.get(a).equals("YELLOW")) locationOfBackGround = (int) getKeyFromValue(background,Color.YELLOW);
        else if(colors.get(a).equals("BLUE")) locationOfBackGround = (int) getKeyFromValue(background,Color.BLUE);

        //creating answer
        locationOfCorrectAnswer = rand.nextInt(4);
        for(int i = 0; i < 4; i++){
            if(i == locationOfCorrectAnswer){
                answers.add(colors.get(a));
            }else{
                int temp = rand.nextInt(4);
                while(temp == a || answers.contains(colors.get(temp))) {
                    temp = rand.nextInt(4);
                }
                answers.add(colors.get(temp));
            }
        }
        setBackGround();
        bt1.setText(answers.get(0));
        bt2.setText(answers.get(1));
        bt3.setText(answers.get(2));
        bt4.setText(answers.get(3));
    }

    public void startGame(){
        score = 0;
        questions = 0;
        resultLayout.setVisibility(View.INVISIBLE);
        bt1.setEnabled(true);
        bt2.setEnabled(true);
        bt3.setEnabled(true);
        bt4.setEnabled(true);
        //setBackGround();

        scoreTextView.setText("0/0");
        countDownTextView.setText("25s");
        answer.setText("");
        generateQuestion();
        countDownTimer = new CountDownTimer(25000,1000){
            @Override
            public void onTick(long l) {
                timer((int) l/1000);
            }
            @Override
            public void onFinish() {
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                answer.setText("");
                bt1.setEnabled(false);
                bt2.setEnabled(false);
                bt3.setEnabled(false);
                bt4.setEnabled(false);
                resultTextView.setText("Your Score: "+ score);

                mediaPlayer.start();
                resultLayout.setVisibility(View.VISIBLE);
            }
        }.start();

    }

    public void setBackGround(){
        ArrayList<Integer> b = new ArrayList<Integer>();
        int num = rand.nextInt(4);
        for(int i = 0; i < 4; i++){
            while(b.contains(num)){
                num = rand.nextInt(4);
            }
            System.out.println(background.get(num));
            b.add(num);
        }
        bt1.setBackgroundColor(background.get(b.get(0)));
        bt2.setBackgroundColor(background.get(b.get(1)));
        bt3.setBackgroundColor(background.get(b.get(2)));
        bt4.setBackgroundColor(background.get(b.get(3)));
    }

    public void playAgain(View view){
        goLayout.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.INVISIBLE);
        gridLayout.setVisibility(View.INVISIBLE);
        resultLayout.setVisibility(View.INVISIBLE);
    }
}
