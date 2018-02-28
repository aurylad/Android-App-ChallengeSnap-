package lt.kvk.i12.auryla_dovydas.challengesnap;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import lt.kvk.i12.auryla.dovydas.challengesnap.R;

import static lt.kvk.i12.auryla_dovydas.challengesnap.LoginActivity.getUsernameFromLogin;

public class ResultActivity extends AppCompatActivity {

    static TextView twPoints;
    TextView twDone;
    Button btViewImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        twPoints = (TextView) findViewById(R.id.twPoints);
        twDone = (TextView) findViewById(R.id.twDone);
        btViewImages = (Button) findViewById(R.id.btViewImages) ;

        //using intent get all updated data from server, after result button is pressed
        Intent intent = getIntent();

        int updatedScore = intent.getIntExtra("score",0);
        String UpdatedchallengeTitle = intent.getStringExtra("challengeTitle");

        twPoints.setText("" + updatedScore);
        twDone.setText(UpdatedchallengeTitle);

        btViewImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String edUsername = getUsernameFromLogin () ;

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success){
                                String imageName = jsonResponse.getString("image_name");
                                String date = jsonResponse.getString("date");
                                String imagePath = jsonResponse.getString("image_path");
                                int idAuto = jsonResponse.getInt("id_auto");
                                String challengeTitle = jsonResponse.getString("challenge_title");


                                Intent intent = new Intent(ResultActivity.this, RetrieveImageActivity.class);

                                intent.putExtra("image_name", imageName);
                                intent.putExtra("image_path", imagePath);
                                intent.putExtra("id_auto", idAuto);
                                intent.putExtra("date", date);
                                intent.putExtra("challenge_title", challengeTitle);

                                ResultActivity.this.startActivity(intent);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(ResultActivity.this);
                                builder.setMessage("No pictures to show...").setNegativeButton("Retry", null).create().show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                int idAut = 0;
                GetImageNameRequest getImageNameRequest = new GetImageNameRequest(edUsername, idAut, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ResultActivity.this);
                queue.add(getImageNameRequest);

            }
        });
    }
}
