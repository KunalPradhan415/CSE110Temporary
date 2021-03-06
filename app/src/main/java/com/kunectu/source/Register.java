package com.kunectu.source;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kunalpradhan.kunectu.R;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;

public class Register extends AppCompatActivity implements View.OnClickListener {

    Button bRegister;
    EditText etEmail, etPassword, etUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        etUsername = (EditText) findViewById(R.id.etUsername);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);

        bRegister = (Button) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.bRegister){
            String emailAddress = etEmail.getText().toString().toLowerCase();
            String username = etUsername.getText().toString().toLowerCase();
            if (emailAddress.endsWith("@ucsd.edu")) {

                ParseUser currentUser = ParseUser.getCurrentUser();
                currentUser.logOut();

                ParseUser user = new ParseUser();
                user.setUsername(username);
                user.setPassword(etPassword.getText().toString());
                user.setEmail(emailAddress);

                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(), "Registered!", Toast.LENGTH_SHORT).show();
                            // Hooray! Let them use the app now.
                        } else {

                            switch (e.getCode()) {
                                case ParseException.EMAIL_TAKEN: {
                                    etEmail.setError("Email taken.");
                                }
                                case ParseException.USERNAME_TAKEN: {
                                    etUsername.setError("Username taken.");
                                }
                            }
                        }
                    }
                });
            }
            else {
                etEmail.setError("Email must be an @ucsd.edu account!");
            }
        }
    }

}