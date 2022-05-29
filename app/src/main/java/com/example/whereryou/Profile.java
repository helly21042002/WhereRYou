package com.example.whereryou;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class Profile extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        mAuth = FirebaseAuth.getInstance();



        Button setd = findViewById(R.id.setd);
        TextView textview = findViewById(R.id.textViewintro);
        EditText edittext = findViewById(R.id.gointo);
        Button Home = (Button) findViewById(R.id.home);
        Button Search = (Button) findViewById(R.id.search);
        Button Profile = (Button) findViewById(R.id.profile);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {

        } else {
            textview.append("Hey,"+ currentUser.getEmail());

        }


        setd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Dest = edittext.getText().toString();
                ParseObject obj = new ParseObject("destination");
                obj.put("Destination", Dest);
                obj.put("email", ""+currentUser.getEmail());
                obj.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if ( e ==null){
                            Toast.makeText(Profile.this, "Destination updated", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(Profile.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

        Home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        Search.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(getApplicationContext(), Search.class);
                startActivity(intent);
            }
        });



    }

}
