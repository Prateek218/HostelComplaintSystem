package com.project.prateek.hostelcomplaintsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class WUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wuser);



        TextView Lout = (TextView)findViewById(R.id.Logout);
        TextView complain = (TextView)findViewById(R.id.complain);
        TextView abtus = (TextView)findViewById(R.id.abtus);


        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final String username = intent.getStringExtra("username");
        final int age = intent.getIntExtra("age", -1);
        final String h_bl = intent.getStringExtra("hostel_block");
        final int r_no = intent.getIntExtra("room_no",-1);
        final String gen = intent.getStringExtra("gender");
        final String con = intent.getStringExtra("contact");
        final String e_mail = intent.getStringExtra("email");

        final TextView tvWelcomeMsg = (TextView) findViewById(R.id.tvWelcomeMsg);
        final TextView etUsername = (TextView) findViewById(R.id.etusr);
        final TextView etAge = (TextView) findViewById(R.id.etage);
        final TextView etHb = (TextView) findViewById(R.id.ethb);
        final TextView etRn = (TextView) findViewById(R.id.etrn);
        final TextView etGen = (TextView) findViewById(R.id.etgen);
        final TextView etCont = (TextView) findViewById(R.id.etcont);
        final TextView etEmail = (TextView) findViewById(R.id.etemail);

        // Display user details
        String message = "Welcome to Your Profile,"+name;
        tvWelcomeMsg.setText(message);
        etUsername.setText(username);
        etAge.setText(age + "");
        etHb.setText(h_bl);
        etRn.setText(r_no + "");
        etGen.setText(gen);
        etCont.setText(con);
        etEmail.setText(e_mail);


        Lout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(WUserActivity.this,LoginActivity.class);
                WUserActivity.this.startActivity(intent);
            }
        });

        complain.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(WUserActivity.this, Warden_fetch.class);
                Bundle b = new Bundle();
                b.putString("username", username);
                intent.putExtras(b);
                WUserActivity.this.startActivity(intent);
            }
        });



        abtus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent AbtusIntent = new Intent(WUserActivity.this,Abtus.class);
                WUserActivity.this.startActivity(AbtusIntent);
            }
        });
    }
}
