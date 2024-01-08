package com.example.ridesharing.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ridesharing.R;
import com.google.android.material.button.MaterialButton;


public class LoginActivity extends AppCompatActivity {
    TextView signup;
    MaterialButton login,number;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        number=findViewById(R.id.enternumberbutton);
        signup=findViewById(R.id.signupTextView);
        login=findViewById(R.id.continebutton);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(i);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(i);
            }
        });
        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,PhoneActivity.class);
                startActivity(i);
            }
        });

    }
}
