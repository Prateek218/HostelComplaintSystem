package com.project.prateek.hostelcomplaintsystem;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by PRATEEK on 11/9/2016.
 */

public class CmpfetchRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://192.168.43.64/webapp/Wcmpfetch.php";
    private Map<String, String> params;

    public CmpfetchRequest(String cmp_id, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("Complaint_No", cmp_id+" ");

    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
