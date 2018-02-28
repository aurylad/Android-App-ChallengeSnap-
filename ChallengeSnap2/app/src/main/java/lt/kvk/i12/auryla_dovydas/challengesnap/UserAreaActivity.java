package lt.kvk.i12.auryla_dovydas.challengesnap;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import lt.kvk.i12.auryla.dovydas.challengesnap.R;


import static lt.kvk.i12.auryla_dovydas.challengesnap.LoginActivity.getNameFromLogin;
import static lt.kvk.i12.auryla_dovydas.challengesnap.LoginActivity.getPasswordFromLogin;
import static lt.kvk.i12.auryla_dovydas.challengesnap.LoginActivity.getScoreFromLogin;
import static lt.kvk.i12.auryla_dovydas.challengesnap.LoginActivity.getUsernameFromLogin;


public class UserAreaActivity extends AppCompatActivity {

    private Button btChallenge;
    private Button btResult;
    private Button btAbout;
    private Button btLogout;
    private static int score;
    private static String username;
    private static String name;

    static int updatedScore;
    static String updatedChallengeTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        btChallenge = (Button) findViewById(R.id.bChallenges);
        btLogout = (Button) findViewById(R.id.bLogout);
        btResult = (Button) findViewById(R.id.bResults) ;
        btAbout = (Button)findViewById(R.id.bAbout);

        score = getScoreFromLogin ();
        name = getNameFromLogin ();
        username = getUsernameFromLogin ();

        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle("Hi, " + name+" !");

        //menu buttons event listeners
        btChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(UserAreaActivity.this, ChallengeActivity.class);
                startActivity(intent5);
            }
        });

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(UserAreaActivity.this, LoginActivity.class);
                startActivity(intent6);
            }
        });

        btResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String edUsername = getUsernameFromLogin () ;
                final String edPassword = getPasswordFromLogin ();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success){
                                updatedScore = jsonResponse.getInt("score");
                                updatedChallengeTitle = jsonResponse.getString("challengeTitle");

                                Intent intent = new Intent(UserAreaActivity.this, ResultActivity.class);

                                intent.putExtra("score", updatedScore);
                                intent.putExtra("challengeTitle", updatedChallengeTitle);
                                UserAreaActivity.this.startActivity(intent);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(UserAreaActivity.this);
                                builder.setMessage("Check network connection...").setNegativeButton("Retry", null).create().show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                LoginRequest loginRequest = new LoginRequest(edUsername, edPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(UserAreaActivity.this);
                queue.add(loginRequest);
            }
        });

        btAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserAreaActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

    }

    //setters and getters
    public static void setScore(int receivedScore){
        score = receivedScore;
    }

    public static int getScore(){
        return score;
    }

    public static String getUsername (){
        return username;
    }




}
