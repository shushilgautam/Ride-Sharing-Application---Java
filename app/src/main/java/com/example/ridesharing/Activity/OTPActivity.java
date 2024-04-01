package com.example.ridesharing.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ridesharing.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {
    EditText e1, e2, e3, e4, e5, e6;
    String getotpbackend;
    MaterialButton veriftotp;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        sharedPreferences= PreferenceManager
//                .getDefaultSharedPreferences(this);
        sharedPreferences = getSharedPreferences("MySharedEmailPref", MODE_PRIVATE);

        setContentView(R.layout.activity_otp);
        e1 = findViewById(R.id.otp1);
        e2 = findViewById(R.id.otp2);
        e3 = findViewById(R.id.otp3);
        e4 = findViewById(R.id.otp4);
        e5 = findViewById(R.id.otp5);
        e6 = findViewById(R.id.otp6);
        veriftotp = findViewById(R.id.textButton);
        TextView textView = findViewById(R.id.textmobileshownumber);
        textView.setText(String.format(
                "+977-%s", getIntent().getStringExtra("mobile")
        ));
        getotpbackend = getIntent().getStringExtra("backendotp");
        final ProgressBar progressBarverifyotp = findViewById(R.id.progressbar_otp);
        veriftotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!e1.getText().toString().trim().isEmpty() && !e2.getText().toString().trim().isEmpty()
                        && !e3.getText().toString().trim().isEmpty() && !e4.getText().toString().trim().isEmpty()
                        && !e5.getText().toString().trim().isEmpty() && !e6.getText().toString().trim().isEmpty()) {
                    String entercodeotp = e1.getText().toString() +
                            e2.getText().toString() +
                            e3.getText().toString() +
                            e4.getText().toString() +
                            e5.getText().toString() +
                            e6.getText().toString();
                    if (getotpbackend != null) {
                        progressBarverifyotp.setVisibility(View.VISIBLE);
                        veriftotp.setVisibility(View.INVISIBLE);
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                getotpbackend, entercodeotp
                        );
                        FirebaseAuth.getInstance().getCurrentUser().linkWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressBarverifyotp.setVisibility(View.GONE);
                                    veriftotp.setVisibility(View.VISIBLE);
                                    Toast.makeText(OTPActivity.this, "Linking Succesfull", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(OTPActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    progressBarverifyotp.setVisibility(View.GONE);
                                    veriftotp.setVisibility(View.VISIBLE);
                                }
                            }
                        });

//                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
//                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<AuthResult> task) {
//                                        progressBarverifyotp.setVisibility(View.GONE);
//                                        veriftotp.setVisibility(View.VISIBLE);
//                                        Log.d("User",FirebaseAuth.getInstance().getCurrentUser().getUid());
//                                        if (task.isSuccessful()){
//
//                                            AuthCredential authCredential= EmailAuthProvider.getCredential(sharedPreferences.getString("email",""),sharedPreferences.getString("password",""));
//                                            FirebaseAuth.getInstance().getCurrentUser().linkWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                                    if(task.isSuccessful()){
//                                                        Toast.makeText(OTPActivity.this, "Linking Succesfull", Toast.LENGTH_SHORT).show();
//                                                        Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
//                                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                                        startActivity(intent);
//                                                    }else{
//                                                        Toast.makeText(OTPActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                                    }
//                                                }
//                                            });
//
//                                        } else{
//                                            Toast.makeText(OTPActivity.this,"Enter the correct OTP", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
                    } else {
                        Toast.makeText(OTPActivity.this, "Please check internet connectivity", Toast.LENGTH_SHORT).show();
                    }
                    //               Toast.makeText(OTPActivity.this, "OTP verify", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OTPActivity.this, "Please enter all number", Toast.LENGTH_SHORT).show();
                }
            }
        });
        TextView resendlevel1 = findViewById(R.id.resentotp);
        resendlevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+977" + getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        OTPActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(OTPActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newbackendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                getotpbackend = newbackendotp;
                                Toast.makeText(OTPActivity.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });
        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (e1.getText().toString().length() == 1)
                    e2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        e2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (e2.getText().toString().length() == 1)
                    e3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        e3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (e3.getText().toString().length() == 1)
                    e4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        e4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (e4.getText().toString().length() == 1)
                    e5.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        e5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (e5.getText().toString().length() == 1)
                    e6.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}