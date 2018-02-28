package lt.kvk.i12.auryla_dovydas.challengesnap;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by auryl on 2017-10-18.
 */

public class LoginRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://challangesnap.000webhostapp.com/login.php";
    private Map<String, String> params;

    public LoginRequest(String username, String password, Response.Listener<String>listener){
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {

        return params;
    }
}
