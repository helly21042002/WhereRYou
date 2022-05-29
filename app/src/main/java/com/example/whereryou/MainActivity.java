package com.example.whereryou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        TextView tv = findViewById(R.id.feed1);
        // Save the current Installation to Back4App
        ParseInstallation.getCurrentInstallation().saveInBackground();

        Button Home = (Button) findViewById(R.id.home);
        Button Search = (Button) findViewById(R.id.search);
        Button Profile = (Button) findViewById(R.id.profile);

        ParseQuery parseQuery = new ParseQuery("destination");
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
                                        @Override
                                        public void done(List<ParseObject> objects, ParseException e) {
                                            if (e == null) {
                                                // fetch your records here
                                                for ( ParseObject objparse : objects) {
                                                    tv.append(""+objparse.get("email").toString()+" is going to "+objparse.get("Destination").toString()+"   ");
                                                }
                                            }
                                            else
                                            {
                                                //parse error
                                            }
                                        }
                                    });

        Search.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getApplicationContext(), Search.class);
                startActivity(intent);
            }
        });

        Profile.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });



    }




}
