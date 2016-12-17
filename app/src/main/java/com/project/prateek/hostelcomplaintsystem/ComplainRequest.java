package com.project.prateek.hostelcomplaintsystem;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by PRATEEK on 10/28/2016.
 */

public class ComplainRequest extends StringRequest {
    private static final String COMPLAIN_REQUEST_URL = "http://192.168.43.64/webapp/complain.php";
    private Map<String, String> params;

    public ComplainRequest(String name, String username, String receipt, String hostel_block, int room_no, String subject, String message, Response.Listener<String> listener) {
        super(Method.POST, COMPLAIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("username", username);
        params.put("receipt", receipt);
        params.put("hostel_block", hostel_block);
        params.put("room_no",room_no + "");
        params.put("subject", subject);
        params.put("message", message);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
