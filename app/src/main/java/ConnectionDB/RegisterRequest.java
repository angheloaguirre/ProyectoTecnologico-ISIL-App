package ConnectionDB;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://192.168.0.3:8000/pruebaBD/Register.php";
    private Map<String, String> params;
    public RegisterRequest(String nombres, String apellidopaterno, String apellidomaterno, String contraseña, String usuario, String fechanac, String numcelular, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap();
        params.put("nombres", nombres);
        params.put("apellidopaterno", apellidopaterno);
        params.put("apellidomaterno", apellidomaterno);
        params.put("contraseña", contraseña);
        params.put("usuario", usuario);
        params.put("fechanac", fechanac);
        params.put("numcelular", numcelular);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}

