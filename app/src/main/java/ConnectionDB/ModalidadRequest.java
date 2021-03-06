package ConnectionDB;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ModalidadRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://192.168.0.3:8000/pruebaBD/ObtenerMod.php";
    private Map<String, String> params;

    public ModalidadRequest (String especialidad, Response.Listener<String> listener){
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap();

        params.put("especialidad", especialidad);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
