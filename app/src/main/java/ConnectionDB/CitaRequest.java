package ConnectionDB;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CitaRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://192.168.0.3:8000/pruebaBD/GenerarCita.php";
    private Map<String, String> params;
    public CitaRequest(String codigo, String fecha, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);

        params = new HashMap();
        params.put("codigo", codigo);
        params.put("fecha", fecha);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
