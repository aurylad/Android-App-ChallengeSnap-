package lt.kvk.i12.auryla_dovydas.challengesnap;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by auryl on 2018-01-09.
 */

public class GetImageNameRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "https://challangesnap.000webhostapp.com/getImageNames.php";
    private Map<String, String> params;

    public GetImageNameRequest(String username, int idAuto, Response.Listener<String>listener){
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("idAuto", idAuto+"");

    }

    @Override
    public Map<String, String> getParams() {

        return params;
    }
}
