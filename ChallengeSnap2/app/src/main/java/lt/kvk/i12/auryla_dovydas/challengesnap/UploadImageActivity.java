package lt.kvk.i12.auryla_dovydas.challengesnap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.ImageView;
import android.net.Uri;
import android.provider.MediaStore;
import java.io.BufferedReader;
import java.net.URLEncoder;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import java.io.UnsupportedEncodingException;
import android.util.Base64;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import lt.kvk.i12.auryla.dovydas.challengesnap.R;

import static lt.kvk.i12.auryla_dovydas.challengesnap.UserAreaActivity.getScore;
import static lt.kvk.i12.auryla_dovydas.challengesnap.UserAreaActivity.getUsername;

public class UploadImageActivity extends AppCompatActivity implements View.OnClickListener {

    Bitmap bitmap;
    boolean check = true;
    Button selectImageGallery, uploadImageServer;
    ImageView selectedImageView;
    EditText editTextImageName;
    ProgressDialog imageUploadingProgressDialog;
    String getImageNameEditText;
    String usernameId = getUsername();
    static int score = ChallengeActivity.getChallengeValue();
    String challengeTitle;
    String serverUploadPath ="https://challangesnap.000webhostapp.com/img_upload_to_server.php" ;
    //TextView twScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        selectedImageView = (ImageView)findViewById(R.id.iwDownloaded);
        editTextImageName = (EditText)findViewById(R.id.editTextImageName);
        selectImageGallery = (Button)findViewById(R.id.buttonSelect);
        uploadImageServer = (Button)findViewById(R.id.buttonUpload);
        selectImageGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
            }
        });


        uploadImageServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // start update score in database //
                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success){
                                //twScore.setText("" + score);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                //get current points and plus challenge value
                score = score + getScore();

                //get selected challenge title
                challengeTitle = ChallengeActivity.getChallengeTitle() + "\n";


                UpdateScoreRequest registerRequest = new UpdateScoreRequest(usernameId, score, challengeTitle, responseListener);
                RequestQueue queue = Volley.newRequestQueue(UploadImageActivity.this);
                queue.add(registerRequest);
                    // ================ //

                getImageNameEditText = editTextImageName.getText().toString();
                ImageUploadToServerFunction();
            }
        });
    }

    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {
            Uri uri = I.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                selectedImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void ImageUploadToServerFunction(){

        ByteArrayOutputStream byteArrayOutputStreamObject ;
        byteArrayOutputStreamObject = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStreamObject);
        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();
        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);
        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                imageUploadingProgressDialog = ProgressDialog.show(UploadImageActivity.this,"Image is Uploading","Please Wait",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);
                // Dismiss the progress dialog after done uploading.
                imageUploadingProgressDialog.dismiss();
                // Printing uploading success message coming from server on android app.
                Toast.makeText(UploadImageActivity.this,string1,Toast.LENGTH_LONG).show();
                // Setting image as transparent after done uploading.
                selectedImageView.setImageResource(android.R.color.transparent);
                Intent afterImageUpload = new Intent(UploadImageActivity.this, UserAreaActivity.class);
                startActivity(afterImageUpload);

            }

            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();
                HashMap<String,String> HashMapParams = new HashMap<String,String>();
                HashMapParams.put("image_name", getImageNameEditText);
                HashMapParams.put("image_path", ConvertImage);
                HashMapParams.put("username", usernameId);
                HashMapParams.put("challengeTitle", challengeTitle);
                String FinalData = imageProcessClass.ImageHttpRequest(serverUploadPath, HashMapParams);
                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
    }

    @Override
    public void onClick(View v) {
    }

    public class ImageProcessClass{

        public String ImageHttpRequest(String requestURL,HashMap<String, String> PData) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url;
                HttpURLConnection httpURLConnectionObject ;
                OutputStream OutPutStream;
                BufferedWriter bufferedWriterObject ;
                BufferedReader bufferedReaderObject ;
                int RC ;
                url = new URL(requestURL);
                httpURLConnectionObject = (HttpURLConnection) url.openConnection();
                httpURLConnectionObject.setReadTimeout(100000000);
                httpURLConnectionObject.setConnectTimeout(100000000);
                httpURLConnectionObject.setRequestMethod("POST");
                httpURLConnectionObject.setDoInput(true);
                httpURLConnectionObject.setDoOutput(true);
                OutPutStream = httpURLConnectionObject.getOutputStream();
                bufferedWriterObject = new BufferedWriter(
                        new OutputStreamWriter(OutPutStream, "UTF-8"));

                bufferedWriterObject.write(bufferedWriterDataFN(PData));
                bufferedWriterObject.flush();
                bufferedWriterObject.close();
                OutPutStream.close();
                RC = httpURLConnectionObject.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReaderObject = new BufferedReader(new InputStreamReader(httpURLConnectionObject.getInputStream()));
                    stringBuilder = new StringBuilder();
                    String RC2;

                    while ((RC2 = bufferedReaderObject.readLine()) != null){
                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            StringBuilder stringBuilderObject;
            stringBuilderObject = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {

                if (check)
                    check = false;
                else
                    stringBuilderObject.append("&");
                stringBuilderObject.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));
                stringBuilderObject.append("=");
                stringBuilderObject.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilderObject.toString();
        }
    }
}
