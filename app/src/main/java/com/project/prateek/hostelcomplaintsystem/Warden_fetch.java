package com.project.prateek.hostelcomplaintsystem;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Warden_fetch extends AppCompatActivity {
    private static String url = "http://192.168.43.64/webapp/fetchcmp.php";
    //static public String cmp_id = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warden_fetch);
        Bundle bundle = getIntent().getExtras();
        final String username = bundle.getString("username");
        final ListView lv = (ListView) findViewById(R.id.allcmp);


        class Downloader extends AsyncTask<Void, Integer, String> {


            Context c;
            String address;
            ListView lv;
            ProgressDialog pd;

            public Downloader(Context c, String address, ListView lv) {
                this.c = c;
                this.address = address;
                this.lv = lv;
            }

            //B4 JOB STARTS
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pd = new ProgressDialog(c);
                pd.setTitle("Fetch Data");
                pd.setMessage("Fetching Data... Please Wait");
                pd.show();
            }

            @Override
            protected String doInBackground(Void... params) {
                String data = downloadData();
                return data;
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pd.dismiss();

                if (s != null) {
                    Parser p = new Parser(c, s, lv);
                    p.execute();
                } else {
                    Toast.makeText(c, "Unable to download Data", Toast.LENGTH_SHORT).show();
                }
            }

            private String downloadData() {
                //connect and get String of data
                InputStream is = null;
                String line = null;
                try {
                    URL url = new URL(address);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    is = new BufferedInputStream(con.getInputStream());
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));

                    StringBuffer sb = new StringBuffer();

                    if (br != null) {
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "/n");
                        }
                    } else {
                        return null;
                    }
                    return sb.toString();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {

                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return null;
            }


            class Parser extends AsyncTask<Void, Integer, Integer> {

                Context c;
                ListView lv;
                String data;
                ArrayList<String> complain = new ArrayList<>();
                ProgressDialog pd;
                //public String cmp_id = "0";

                public Parser(Context c, String data, ListView lv) {
                    this.c = c;
                    this.data = data;
                    this.lv = lv;
                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    pd = new ProgressDialog(c);
                    pd.setTitle("Parser");
                    pd.setMessage("Parsing...Please Wait");
                    pd.show();
                }

                @Override
                protected Integer doInBackground(Void... params) {
                    return this.parse();
                }

                @Override
                protected void onPostExecute(Integer integer) {
                    super.onPostExecute(integer);
                    if (integer == 1) {

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(c, android.R.layout.simple_list_item_1, complain);
                        lv.setAdapter(adapter);

                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+" is Selected", Toast.LENGTH_LONG).show();
                                final String cmp_id = parent.getItemAtPosition(position).toString();

                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);
                                            boolean success = jsonResponse.getBoolean("success");

                                            if (success) {
                                                String c_from_reg = jsonResponse.getString("C_From_Reg");
                                                int cm_id = jsonResponse.getInt("Complaint_No");
                                                String c_from_name = jsonResponse.getString("C_From_Name");
                                                int r_no = jsonResponse.getInt("Room_No");
                                                String hostel_block = jsonResponse.getString("Hostel_Block");
                                                String to_warden_id = jsonResponse.getString("To_warden_id");
                                                String subject = jsonResponse.getString("Subject");
                                                String message = jsonResponse.getString("Message");
                                                Intent intent = new Intent(Warden_fetch.this, CmpDisplay.class);
                                                intent.putExtra("C_From_Reg", c_from_reg);
                                                intent.putExtra("Complaint_No", cm_id);
                                                intent.putExtra("C_From_Name", c_from_name);
                                                intent.putExtra("Room_No", r_no);
                                                intent.putExtra("To_warden_id", to_warden_id);
                                                intent.putExtra("Hostel_Block", hostel_block);
                                                intent.putExtra("Subject", subject);
                                                intent.putExtra("Message", message);


                                                //Toast.makeText(Warden_fetch.this, "Lo", Toast.LENGTH_SHORT).show();
                                                Warden_fetch.this.startActivity(intent);
                                            } else {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(Warden_fetch.this);
                                                builder.setMessage("Not Selected")
                                                        .setNegativeButton("Retry", null)
                                                        .create()
                                                        .show();
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };

                                CmpfetchRequest fetchRequest = new CmpfetchRequest(cmp_id, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(Warden_fetch.this);
                                queue.add(fetchRequest);


                            }
                        });


                    } else {
                        Toast.makeText(c, "Unable to Parse", Toast.LENGTH_SHORT).show();
                    }

                    pd.dismiss();
                }

                //Parsing The Data
                private int parse() {
                    try {
                        //Add That Data To JSON Array First
                        JSONArray js = new JSONArray(data);

                        //Create JO to Hold Single Item
                        JSONObject jo = null;

                        complain.clear();

                        //LOOP THRU ARRAY
                        for (int i = 0; i < js.length(); i++) {
                            jo = js.getJSONObject(i);

                            // RETRIEVE NAME
                            String c_id = jo.getString("Complaint_No");
                            //ADD IT TO ARRAYLIST
                            complain.add(c_id);

                        }
                        return 1;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            }


        }


        final Downloader d = new Downloader(this, url, lv);
        d.execute();


    }
}