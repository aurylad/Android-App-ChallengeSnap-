package lt.kvk.i12.auryla_dovydas.challengesnap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import lt.kvk.i12.auryla.dovydas.challengesnap.R;

public class AboutActivity extends AppCompatActivity {

    TextView twFirst;
    TextView twSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        twFirst = (TextView)findViewById(R.id.twFirst);
        twSecond = (TextView)findViewById(R.id.twSecond);

        twFirst.setText("Created:" +"\n"+ "Created by:" +"\n"+ "University:" + "\n\n" + "Group:" +"\n"+ "Version:");
        twSecond.setText("11th of January, 2018" + "\n" + "Dovydas Auryla" + "\n" + "Klaipeda state university"+"\n"+"of applied sciences" + "\n" + "I12-2" + "\n" + "DEMO" );
    }
}
