package lt.kvk.i12.auryla_dovydas.challengesnap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

import lt.kvk.i12.auryla.dovydas.challengesnap.R;

import static lt.kvk.i12.auryla_dovydas.challengesnap.LoginActivity.getUsernameFromLogin;

public class RetrieveImageActivity extends AppCompatActivity {

    String imageName;
    String date;
    String imagePath;
    String challengeTitle;
    int autoId;
    //int autoId = 1;
    ProgressDialog imageUploadingProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_image);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final TextView twImageName = (TextView)findViewById(R.id.twName);
        final TextView twChallenge = (TextView)findViewById(R.id.twChallenge);
        final TextView twDate = (TextView)findViewById(R.id.twDate);
        final Button btNextPhoto = (Button)findViewById(R.id.btNextPhoto);


        final Intent intent = getIntent();
         imageName = intent.getStringExtra("image_name");
         date = intent.getStringExtra("date");
         imagePath =  intent.getStringExtra("image_path");
         challengeTitle = intent.getStringExtra("challenge_title");
         autoId = intent.getIntExtra("id_auto",0);


        twImageName.setText(imageName);
        twDate.setText(date);
        twChallenge.setText(challengeTitle);


        btNextPhoto.setOnClickListener(new View.OnClickListener() {
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
                                String challengeTitle = jsonResponse.getString("challenge_title");
                                autoId = jsonResponse.getInt("id_auto");

                                twImageName.setText(imageName);
                                twDate.setText(date);
                                twChallenge.setText(challengeTitle);
                                new DownloadImageTask((ImageView) findViewById(R.id.iwDownloaded)) .execute(imagePath);


                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RetrieveImageActivity.this);
                                builder.setMessage("No more pictures left!").setNegativeButton("Retry", null).create().show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                //int idAut = 1;
                SecondGetImageNameRequest secondGetImageNameRequest = new SecondGetImageNameRequest(edUsername, autoId, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RetrieveImageActivity.this);
                queue.add(secondGetImageNameRequest);
                autoId++;
            }
        });



        new DownloadImageTask((ImageView) findViewById(R.id.iwDownloaded)) .execute(imagePath);

    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            imageUploadingProgressDialog = ProgressDialog.show(RetrieveImageActivity.this,"","Please Wait",false,false);
        }
        protected void onPostExecute(Bitmap result) {
            imageUploadingProgressDialog.dismiss();
            bmImage.setImageBitmap(result);
        }
    }

}
