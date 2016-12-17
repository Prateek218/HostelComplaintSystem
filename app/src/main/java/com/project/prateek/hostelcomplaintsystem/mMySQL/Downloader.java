package com.project.prateek.hostelcomplaintsystem.mMySQL;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by PRATEEK on 11/10/2016.
 */

public class Downloader extends AsyncTask<Void,Void,String>{
    Context c;
    String urlAddress;
    Spinner spinner;
    ProgressDialog pd;

    public Downloader(Context c, String urlAddress, Spinner spinner){
        this.c = c;
        this.urlAddress = urlAddress;
        this.spinner = spinner;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();

        pd=new ProgressDialog(c);
        pd.setTitle("Fetch");
        pd.setMessage("Fetching...Please Wait");
        pd.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        return this.downloaddata();
    }

    @Override
    protected void onPostExecute(String s){
        super.onPostExecute(s);
        pd.dismiss();
        if(s == null)
        {
            Toast.makeText(c,"Unable to Retrieve null Returned",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(c,"Success",Toast.LENGTH_SHORT).show();

            //CALL PARSER TO PASS DATA
            DataParser parser = new DataParser(c,spinner,s);
            parser.execute();

        }
    }

    private String downloaddata()
    {
        HttpURLConnection con = Connector.connect(urlAddress);
        if(con==null)
        {
            return null;
        }
        InputStream is = null;
        try{
            is =  new BufferedInputStream(con.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line=null;
            StringBuffer response = new StringBuffer();

            if(br != null)
            {
                while ((line = br.readLine()) !=  null)
                {
                    response.append(line+"\n");
                }
            }
            else {
                return null;
            }
            return response.toString();

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            {
                if (is != null){
                    try{
                        is.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }



}
