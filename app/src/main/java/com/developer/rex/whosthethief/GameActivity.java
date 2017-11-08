package com.developer.rex.whosthethief;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    String no;
    String[] Rank = {"","Judge", "Police", "Thief", "Robber"};
    Random rand = new Random();
    List<Integer> myList = new ArrayList<Integer>();
    Integer[] RankInt;
    int value;
    int[] playerScore= {0,0,0,0,0,0};
    int[] scoreIndex = {0, 90, 100, 60, 60};
    TextView pl1, pl2, pl3, pl4, sc1, sc2, sc3, sc4;
    RadioGroup rg1, rg2;
    RadioButton r1,r2,r3,r4;
    Button PlayBtn, SubmitBtn;

    int winner = 0;
    int gameOver = 500;
    boolean over = false;

    Thread thread;

    int[] Thief= new int[3];
    private int Answer;
    private TextView mTextField;
    private Button BackBtn;
    private TextView n1, n2, n3, n4;
    private TextView thieflbl, robberlbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        initAll(savedInstanceState);
    }



    public void play(View view) {
        suffleSet();
        score();

        PlayBtn.setVisibility (View.INVISIBLE);

        if(Integer.valueOf(no) < Thief[0]) {
            visible(false);
            computer();
        }
        else {
            visible(true);
            mTextField.setText("Player "+Thief[0]+" Choose the Culprits!");
        }
    }

    public void submit(View v){
        if((r1.isChecked() && Answer == 1) || (r2.isChecked() && Answer == 2)){
                //Toast.makeText(this, "Detective Police", Toast.LENGTH_SHORT).show();
                int index = Indexer(2);
                playerScore[index] += 100;
                ScoreLbl(index, playerScore[index]);

                mTextField.setText("Correct Guess!");
            }
            else if((r1.isChecked() && Answer == 2) || (r2.isChecked() && Answer == 1)) {
                //Toast.makeText(this, "Bad Police!", Toast.LENGTH_SHORT).show();

                int index = Indexer(3);
                playerScore[index] += 60;
                ScoreLbl(index, playerScore[index]);

                index = Indexer(4);
                playerScore[index] += 60;
                ScoreLbl(index, playerScore[index]);
                mTextField.setText("Wrong Guess!");
            }
            else {
                Toast.makeText(this, "Culprit not guessed!", Toast.LENGTH_SHORT).show();
                return;
            }

        visible(false);
        GameOver();
    }

    public void suffleSet() {
        Collections.shuffle(myList);
        RankInt = myList.toArray(new Integer[myList.size()]);
        for(int i=1; i<=4; i++) {
            RankLbl(i, RankInt[i-1]);
        }
    }

    private int Indexer(int value) {
        int index = Arrays.asList(RankInt).indexOf(value)+1;
        return index;
    }

    private void score() {
        int index = Indexer(1);
        playerScore[index] += 90;
        ScoreLbl(index, playerScore[index]);
        radioSet();
    }

    private void radioSet() {
        Thief[0] = Indexer(2);
        Thief[1] = Indexer(3);
        Thief[2] = Indexer(4);

        Answer = rand.nextInt(2) + 1;

        if(Answer == 1) {
            r1.setText("Player No: "+Thief[1]);
            r3.setText("Player No: "+Thief[1]);

            r2.setText("Player No: "+Thief[2]);
            r4.setText("Player No: "+Thief[2]);
        }
        else {
            r1.setText("Player No: "+Thief[2]);
            r3.setText("Player No: "+Thief[2]);

            r2.setText("Player No: "+Thief[1]);
            r4.setText("Player No: "+Thief[1]);
        }
        
    }

    private void initAll(Bundle savedInstanceState) {
        xmlCall();

        if(savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                no = null;
            } else {
                no = extras.getString("PlayerNo");
            }
        } else {
            no = (String) savedInstanceState.getSerializable("PlayerNo");
        }

        for(int i=1; i<=4; i++){
            myList.add(i);
        }
        putX();
        NameLbl();

        visible(false);
        PlayBtn.setVisibility(View.VISIBLE);
        BackBtn.setVisibility(View.INVISIBLE);

        Toast.makeText(this, "Total Players: "+ no, Toast.LENGTH_SHORT).show();
    }

    public void xmlCall(){
        pl1 = (TextView) findViewById(R.id.pl1);
        pl2 = (TextView) findViewById(R.id.pl2);
        pl3 = (TextView) findViewById(R.id.pl3);
        pl4 = (TextView) findViewById(R.id.pl4);

        sc1 = (TextView) findViewById(R.id.s1);
        sc2 = (TextView) findViewById(R.id.s2);
        sc3 = (TextView) findViewById(R.id.s3);
        sc4 = (TextView) findViewById(R.id.s4);

        rg1 = (RadioGroup) findViewById(R.id.rg1);
        rg2 = (RadioGroup) findViewById(R.id.rg2);

        r1 = (RadioButton) findViewById(R.id.r1);
        r2 = (RadioButton) findViewById(R.id.r2);
        r3 = (RadioButton) findViewById(R.id.r3);
        r4 = (RadioButton) findViewById(R.id.r4);

        thieflbl = (TextView) findViewById(R.id.thieflbl);
        robberlbl = (TextView) findViewById(R.id.robberlbl);

        n1 = (TextView) findViewById(R.id.n1);
        n2 = (TextView) findViewById(R.id.n2);
        n3 = (TextView) findViewById(R.id.n3);
        n4 = (TextView) findViewById(R.id.n4);

        BackBtn = (Button) findViewById(R.id.BackBtn);

        SubmitBtn = (Button) findViewById(R.id.sub);
        PlayBtn = (Button) findViewById(R.id.playBtn);

        mTextField = (TextView) findViewById(R.id.mTextField);

        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(r1.isChecked()){
                    r4.setChecked(true);
                }
                else if(r2.isChecked()){
                    r3.setChecked(true);
                }
            }
        });

        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(r3.isChecked()){
                    r2.setChecked(true);
                }
                else if(r4.isChecked()){
                    r1.setChecked(true);
                }
            }
        });
    }

    private void putX() {
        pl1.setText("X");
        pl2.setText("X");
        pl3.setText("X");
        pl4.setText("X");
        sc1.setText("X");
        sc2.setText("X");
        sc3.setText("X");
        sc4.setText("X");
    }

    public void visible(boolean state){
        if(state) {
            thieflbl.setVisibility(View.VISIBLE);
            robberlbl.setVisibility(View.VISIBLE);
            r1.setVisibility(View.VISIBLE);
            r2.setVisibility(View.VISIBLE);
            r3.setVisibility(View.VISIBLE);
            r4.setVisibility(View.VISIBLE);

            SubmitBtn.setVisibility(View.VISIBLE);
        }
        else {
            thieflbl.setVisibility(View.INVISIBLE);
            robberlbl.setVisibility(View.INVISIBLE);

            rg1.clearCheck();
            rg2.clearCheck();

            r1.setVisibility(View.INVISIBLE);
            r2.setVisibility(View.INVISIBLE);
            r3.setVisibility(View.INVISIBLE);
            r4.setVisibility(View.INVISIBLE);

            SubmitBtn.setVisibility(View.INVISIBLE);
        }


    }

    public void NameLbl() {
        String lbl = "Player";
        n1.setText(lbl+" 1");

        if(Integer.parseInt(no) < 2){
            lbl = "Computer";
        }
        n2.setText(lbl+" 2");

        if(Integer.parseInt(no) < 3){
            lbl = "Computer";
        }
        n3.setText(lbl+" 3");

        if(Integer.parseInt(no) < 4){
            lbl = "Computer";
        }
        n4.setText(lbl+" 4");
    }

    private void RankLbl(int pl, int tkt){
        String rank = "";
        if(tkt == 3 || tkt == 4) {
            rank = "?";
        }
        else {
            rank = Rank[tkt];
        }

        if(pl == 1){
            pl1.setText(rank);
        }
        else if(pl == 2){
            pl2.setText(rank);
        }
        else if(pl == 3){
            pl3.setText(rank);
        }
        else{
            pl4.setText(rank);
        }
    }

    private void ScoreLbl(int pl, int sc) {
        if(pl == 1){
            sc1.setText(String.valueOf(sc));
        }
        else if(pl == 2){
            sc2.setText(String.valueOf(sc));
        }
        else if(pl == 3){
            sc3.setText(String.valueOf(sc));
        }
        else if(pl == 4){
            sc4.setText(String.valueOf(sc));
        }
    }

    private void computer() {
        new CountDownTimer(4000, 1000) {
            public void onTick(long millisUntilFinished) {
                mTextField.setText("Computer is deciding as Police!: " + millisUntilFinished / 1000 + "s");
            }

            public void onFinish() {
                int ComDecision = rand.nextInt(2) + 1;
                if(ComDecision == 1) {
                    r1.performClick();
                }
                else {
                    r2.performClick();
                }
                mTextField.setText("done!");
                SubmitBtn.performClick();
            }
        }.start();
    }

    public void GameOver(){
        for(int i=1; i<=4; i++){
            if(playerScore[i] > playerScore[0]){
                playerScore[0] = playerScore[i];
                winner = i;
            }
            if (playerScore[i] > gameOver){
                over = true;
                break;
            }
        }

        if(over){
            if(winner > Integer.valueOf(no)){
                mTextField.setText("Computer "+winner+" won!");
            }
            else {
                mTextField.setText("Player "+winner+" won!");
            }
            Toast.makeText(this, "Game Over! Start Again!", Toast.LENGTH_SHORT).show();
            PlayBtn.setVisibility(View.INVISIBLE);
            BackBtn.setVisibility(View.VISIBLE);
            visible(false);
            return;
        }
        PlayBtn.setVisibility(View.VISIBLE);
    }

    public void back(View view) {
        finish();
    }
}
