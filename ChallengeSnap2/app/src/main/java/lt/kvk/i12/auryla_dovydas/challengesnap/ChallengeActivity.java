package lt.kvk.i12.auryla_dovydas.challengesnap;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import lt.kvk.i12.auryla.dovydas.challengesnap.R;

public class ChallengeActivity extends AppCompatActivity {

    private Button acceptButton;
    private Button skipButton;
    private TextView twPoint;
    private TextView twTime;
    private TextView twDescription;
    private TextView twChallenge;
    private TextView twLabelTime;
    private TextView twLabelPoints;

    int numberOfSkips = 0;
    public static int challengeValue;
    public static String challengeTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        twPoint = (TextView)findViewById(R.id.twPoints);
        twTime = (TextView)findViewById(R.id.twTime);
        twDescription = (TextView)findViewById(R.id.twDescription);
        twChallenge = (TextView)findViewById(R.id.twChallenge);
        twLabelTime = (TextView)findViewById(R.id.twLabelTime);
        twLabelPoints = (TextView)findViewById(R.id.twLabelPoints);
        acceptButton = (Button) findViewById(R.id.btAccept);
        skipButton = (Button) findViewById(R.id.btSkip);

 //first challenge content for beginning
        int firstChalpoints = 5;
        int firstChalTime = 1;
        String firstChalDescription = "Take picture of sky";
        String firstChalTitle = "Challenge 1";
        setChallengeValue(firstChalpoints);
        setChallengeTitle(firstChalTitle);

        twPoint.setText("" + firstChalpoints);
        twTime.setText(firstChalTime + " days");
        twDescription.setText(firstChalDescription);
        twChallenge.setText(firstChalTitle);


        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent1 = new Intent(ChallengeActivity.this, UploadImageActivity.class);
            startActivity(intent1);
            }
        });

//action on SKIP button pressed
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                numberOfSkips--;

                if (numberOfSkips > -4) {

                    switch (numberOfSkips) {
                        case -1:
                            int secondChallPoints = 10;
                            int secondChalTime = 2;
                            String secondChalDescription = "Take picture of bird";
                            String secondChalTitle = "Challenge 2";
                            setContent(secondChallPoints, secondChalTime, secondChalDescription, secondChalTitle);
                            setChallengeValue(secondChallPoints);
                            setChallengeTitle(secondChalTitle);
                            break;
                        case -2:
                            int thirdChallPoints = 6;
                            int thirdChalTime = 2;
                            String thirdChalDescription = "Take picture of red car";
                            String thirdChalTitle = "Challenge 3";
                            setContent(thirdChallPoints, thirdChalTime, thirdChalDescription, thirdChalTitle);
                            setChallengeValue(thirdChallPoints);
                            setChallengeTitle(thirdChalTitle);
                            break;
                        case -3:
                            int fourthChallPoints = 11;
                            int fourthChalTime = 1;
                            String fourthChalDescription = "Take picture of sitting dog";
                            String fourthChalTitle = "Challenge 4";
                            setContent(fourthChallPoints, fourthChalTime, fourthChalDescription, fourthChalTitle);
                            setChallengeValue(fourthChallPoints);
                            setChallengeTitle(fourthChalTitle);
                            break;
                    }
                } else { twChallenge.setText("No more challenges left!");
                    twPoint.setVisibility(View.INVISIBLE);
                    twTime.setVisibility(View.INVISIBLE);
                    acceptButton.setVisibility(View.INVISIBLE);
                    skipButton.setVisibility(View.INVISIBLE);
                    twLabelTime.setVisibility(View.INVISIBLE);
                    twLabelPoints.setVisibility(View.INVISIBLE);

                    new CountDownTimer(5000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            twDescription.setText("Please wait: " + millisUntilFinished / 1000 + " seconds");
                        }

                        public void onFinish() {

                            Intent intent = new Intent(ChallengeActivity.this, UserAreaActivity.class);
                            startActivity(intent);
                        }
                    }.start();
                }
            }
        });
    }

    public void setContent (int point, int time, String description, String chalTitle){
        twPoint.setText("" + point);
        twTime.setText(time + " days");
        twDescription.setText(description);
        twChallenge.setText(chalTitle);
    }

    public static void setChallengeValue(int challValue){
        challengeValue =  challValue;
    }

    public static int getChallengeValue() {
        return challengeValue;
    }

    public static void setChallengeTitle(String challTitle){
        challengeTitle = challTitle;
    }

    public static String getChallengeTitle() {
        return challengeTitle;
    }


}
