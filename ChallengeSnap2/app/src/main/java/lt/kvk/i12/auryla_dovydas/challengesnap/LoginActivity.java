package lt.kvk.i12.auryla_dovydas.challengesnap;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import lt.kvk.i12.auryla.dovydas.challengesnap.R;

public class LoginActivity extends AppCompatActivity {
    static String name;
    static String username;
    static int score;
    static String password;
    ProgressDialog imageUploadingProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final TextView registerLink = (TextView) findViewById(R.id.tvRegisterHere);

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, lt.kvk.i12.auryla_dovydas.challengesnap.RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        //Reikalingas prisijungimui

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String edUsername = etUsername.getText().toString();
                final String edPassword = etPassword.getText().toString();
                imageUploadingProgressDialog = ProgressDialog.show(LoginActivity.this,"","Please Wait",false,false);
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success){

                                name = jsonResponse.getString("name");
                                username = jsonResponse.getString("username");
                                score = jsonResponse.getInt("score");
                                password = jsonResponse.getString("password");
                                imageUploadingProgressDialog.dismiss();
                                Intent intent = new Intent(LoginActivity.this, UserAreaActivity.class);

                                intent.putExtra("name", name);
                                intent.putExtra("username", username);
                                intent.putExtra("score", score);
                                intent.putExtra("password", password);
                                LoginActivity.this.startActivity(intent);

                            }else{
                                imageUploadingProgressDialog.dismiss();
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed...").setNegativeButton("Retry", null).create().show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                LoginRequest loginRequest = new LoginRequest(edUsername, edPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

    }

    public static String getNameFromLogin (){
        return name;
    }

    public static String getUsernameFromLogin (){
        return username;
    }

    public static int getScoreFromLogin (){
        return score;
    }

    public static String getPasswordFromLogin (){
        return password;
    }


}
