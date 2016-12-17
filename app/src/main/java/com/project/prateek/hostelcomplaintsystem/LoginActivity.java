package com.project.prateek.hostelcomplaintsystem;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    ArrayAdapter<CharSequence> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Spinner spinner = (Spinner)findViewById(R.id.l_select);
        adapter = ArrayAdapter.createFromResource(this,R.array.login_select,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+" is Selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final EditText etUsername = (EditText) findViewById(R.id.etregno);
        final EditText etPassword = (EditText) findViewById(R.id.etpass);
        final ImageButton bLogin = (ImageButton) findViewById(R.id.bLogin);
        final ImageButton bReset = (ImageButton) findViewById(R.id.bReset);


        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                final String r_name = spinner.getSelectedItem().toString();
                // Response received from the server
                if(r_name.equals("Student")) {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    String name = jsonResponse.getString("name");
                                    int age = jsonResponse.getInt("age");
                                    String h_bl = jsonResponse.getString("hostel_block");
                                    int r_no = jsonResponse.getInt("room_no");
                                    String gen = jsonResponse.getString("gender");
                                    String cont = jsonResponse.getString("contact");
                                    String e_mail = jsonResponse.getString("email");
                                    Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                                    intent.putExtra("name", name);
                                    intent.putExtra("age", age);
                                    intent.putExtra("hostel_block", h_bl);
                                    intent.putExtra("room_no", r_no);
                                    intent.putExtra("gender", gen);
                                    intent.putExtra("contact", cont);
                                    intent.putExtra("email", e_mail);
                                    intent.putExtra("username", username);



                                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    LoginActivity.this.startActivity(intent);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage("Login Failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    LoginRequest loginRequest = new LoginRequest(username, password, r_name, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    queue.add(loginRequest);
                }
                else
                {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    String name = jsonResponse.getString("name");
                                    int age = jsonResponse.getInt("age");
                                    String h_bl = jsonResponse.getString("hostel_block");
                                    int r_no = jsonResponse.getInt("room_no");
                                    String gen = jsonResponse.getString("gender");
                                    String cont = jsonResponse.getString("contact");
                                    String e_mail = jsonResponse.getString("email");


                                    Intent intent = new Intent(LoginActivity.this, WUserActivity.class);
                                    intent.putExtra("name", name);
                                    intent.putExtra("age", age);
                                    intent.putExtra("hostel_block", h_bl);
                                    intent.putExtra("room_no", r_no);
                                    intent.putExtra("gender", gen);
                                    intent.putExtra("contact", cont);
                                    intent.putExtra("email", e_mail);
                                    intent.putExtra("username", username);

                                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    LoginActivity.this.startActivity(intent);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage("Login Failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    LoginRequest loginRequest = new LoginRequest(username, password,r_name, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    queue.add(loginRequest);
                }
            }
        });

        bReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {
                etUsername.setText("");
                etPassword.setText("");
            }
        });
    }
}