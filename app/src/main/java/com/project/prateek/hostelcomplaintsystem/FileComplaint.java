package com.project.prateek.hostelcomplaintsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.project.prateek.hostelcomplaintsystem.mMySQL.Downloader;

import org.json.JSONException;
import org.json.JSONObject;

public class FileComplaint extends AppCompatActivity {

final static String urlAddress = "http://192.168.43.64/webapp/wardename.php";
   // ArrayAdapter<CharSequence> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_complaint);
       final Spinner spinner = (Spinner)findViewById(R.id.Swarden);
        new Downloader(FileComplaint.this,urlAddress,spinner).execute();

        Bundle bundle = getIntent().getExtras();

        final TextView fname = (TextView)findViewById(R.id.fname);
        final TextView usrid = (TextView)findViewById(R.id.usrid);
        final TextView h_blok = (TextView)findViewById(R.id.h_blk);
        final TextView rm_no = (TextView)findViewById(R.id.r_no);
        final TextView sub = (TextView)findViewById(R.id.subject);
        final TextView msg = (TextView)findViewById(R.id.message);


        final String name = bundle.getString("name");
        final String username = bundle.getString("username");
        final String h_bl = bundle.getString("hostel_block");
        final int r_no = bundle.getInt("room_no",-1);



        fname.setText(name);
        usrid.setText(username);
        h_blok.setText(h_bl);
        rm_no.setText(r_no +"");



        /*adapter = ArrayAdapter.createFromResource(this,R.array.warden_names,android.R.layout.simple_spinner_item);
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
        });*/


        final Button Sbutton = (Button)findViewById(R.id.Sbutton);
        final Button Rbutton = (Button)findViewById(R.id.Rbutton);

        Rbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {
                sub.setText("");
                msg.setText("");
            }
        });



        Sbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                final String s_name= fname.getText().toString();
                final String u_name = usrid.getText().toString();
                final String r_name = spinner.getSelectedItem().toString();
                final String hostel_block= h_blok.getText().toString();
                final int room_no = Integer.parseInt(rm_no.getText().toString());
                final String c_subject= sub.getText().toString();
                final String c_message= msg.getText().toString();


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {

                                Toast.makeText(FileComplaint.this,"Complain Registered Successfullty",Toast.LENGTH_LONG).show();

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(FileComplaint.this);
                                builder.setMessage("Complain Registeration Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ComplainRequest complainRequest = new ComplainRequest(s_name, u_name, r_name, hostel_block, room_no, c_subject, c_message, responseListener);
                RequestQueue queue = Volley.newRequestQueue(FileComplaint.this);
                queue.add(complainRequest);
            }
        });

        final TextView Lout = (TextView)findViewById(R.id.Lout);
        Lout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(FileComplaint.this,LoginActivity.class);
                FileComplaint.this.startActivity(intent);
            }
        });

    }
}
