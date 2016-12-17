package com.project.prateek.hostelcomplaintsystem.mMySQL;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by PRATEEK on 11/10/2016.
 */

public class DataParser extends AsyncTask<Void,Void,Integer>{

    Context c;
    String ujsondata;
    Spinner spinner;


    ProgressDialog pd;

    public DataParser(Context c, Spinner spinner, String ujsondata){
        this.c = c;
        this.ujsondata = ujsondata;
        this.spinner = spinner;
    }

    ArrayList<String> spacecrafts = new ArrayList<>();
    @Override
    protected void onPreExecute(){
        super.onPreExecute();

        pd=new ProgressDialog(c);
        pd.setTitle("Parse");
        pd.setMessage("Parsing...Please Wait");
        pd.show();
    }


    @Override
    protected Integer doInBackground(Void... params) {
        return this.parseData();
    }

    @Override
    protected void onPostExecute(Integer result){
        super.onPostExecute(result);
        pd.dismiss();
        if(result == 0){
            Toast.makeText(c,"Unable to Parse",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(c,"Parsed Successfully",Toast.LENGTH_SHORT).show();

            //BIND
            ArrayAdapter adapter = new ArrayAdapter(c,android.R.layout.simple_list_item_1,spacecrafts);
            spinner.setAdapter(adapter);
        }

    }

    private int parseData(){
       try{
           JSONArray ja = new JSONArray(ujsondata);
           JSONObject jo = null;
           spacecrafts.clear();
           Spacecraft s = null;
           for(int i = 0;i < ja.length();i++){
               jo = ja.getJSONObject(i);
               int id = jo.getInt("user_id");
               String war_id = jo.getString("username");
               s = new Spacecraft();
               s.setId(id);
               s.setWar_id(war_id);

               spacecrafts.add(war_id);

           }
           return 1;
       }catch (JSONException e)
       {
           e.printStackTrace();
       }
        return 0;
    }

}
