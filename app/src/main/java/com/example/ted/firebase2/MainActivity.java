package com.example.ted.firebase2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Hi", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        // textview at the top of the screen to show messages
        final TextView textViewToChange = (TextView) findViewById(R.id.myTextView);

        // event for create user button
        Button createUserButton = (Button) findViewById(R.id.createUserButton);
        createUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Firebase ref = new Firebase("https://glaring-torch-2705.firebaseio.com");
                ref.createUser("ted_sommers@hotmail.com", "3edc4rfv", new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> result) {
                        textViewToChange.append("\n-Successfully created user account with uid: " + result.get("uid"));
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        textViewToChange.append("\n-failed to create user");
                    }
                });
            }
        });

        // event for reset password button
        Button resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Firebase ref = new Firebase("https://glaring-torch-2705.firebaseio.com");
                ref.resetPassword("ted_sommers@hotmail.com", new Firebase.ResultHandler() {
                    @Override
                    public void onSuccess() {
                        textViewToChange.append("\n-mail sent");
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        textViewToChange.append("\n-failed to send email" + firebaseError);
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
