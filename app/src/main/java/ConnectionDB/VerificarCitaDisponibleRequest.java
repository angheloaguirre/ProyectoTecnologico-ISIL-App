package ConnectionDB;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class VerificarCitaDisponibleRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://192.168.0.3:8000/pruebaBD/VerificarDisponible.php";
    private Map<String, String> params;
    public VerificarCitaDisponibleRequest(String codigo, Response.Listener<String> listener){
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);

        params = new HashMap();
        params.put("codigo", codigo);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
