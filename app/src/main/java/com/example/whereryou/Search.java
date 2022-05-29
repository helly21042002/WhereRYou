package com.example.whereryou;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        Button Home = (Button) findViewById(R.id.home);
        Button Search = (Button) findViewById(R.id.search);
        Button Profile = (Button) findViewById(R.id.profile);
        ListView simpleList = findViewById(R.id.searchList);
        ArrayList<String> UsersEmails = new ArrayList<>();
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    // The query was successful.
                    for (ParseUser obj : objects) {
                        UsersEmails.add(obj.getUsername());

                    }
                    Toast.makeText(Search.this, "Successful Query"+ UsersEmails.toString(), Toast.LENGTH_LONG).show();

                } else {
                    // Something went wrong.
                    Toast.makeText(Search.this, "Problem finding Users" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.activity_list_view,R.id.textView1, UsersEmails);
        simpleList.setAdapter(arrayAdapter);

        Home.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
