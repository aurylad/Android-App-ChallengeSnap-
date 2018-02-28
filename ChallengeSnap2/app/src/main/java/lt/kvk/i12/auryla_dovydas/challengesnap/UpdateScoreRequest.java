package lt.kvk.i12.auryla_dovydas.challengesnap;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by auryl on 2017-12-12.
 */

public class UpdateScoreRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://challangesnap.000webhostapp.com/updateScores.php";
    private Map<String, String> params;

    public UpdateScoreRequest(String username, int score, String challengeTitle, Response.Listener<String>listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("score", score + "");
        params.put("challengeTitle",challengeTitle );


    }

    @Override
    public Map<String, String> getParams() {

        return params;
    }
}
