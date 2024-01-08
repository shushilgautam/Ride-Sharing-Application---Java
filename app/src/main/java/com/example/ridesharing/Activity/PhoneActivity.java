package com.example.ridesharing.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ridesharing.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;
public class PhoneActivity extends AppCompatActivity {
    TextInputEditText editnumber;
    MaterialButton otp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_activity);
        editnumber=findViewById(R.id.phonenumber);
        otp=findViewById(R.id.sendotp);
        ProgressBar progressBar=findViewById(R.id.progressbar_phone);
        otp.setOnClickListener((v) -> {
            if (!editnumber.getText().toString().trim().isEmpty()){
                if ((editnumber.getText().toString().trim()).length()==10){
                    progressBar.setVisibility(View.VISIBLE);
                    otp.setVisibility(View.INVISIBLE);
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+977" + editnumber.getText().toString(),
                            60,
                            TimeUnit.SECONDS,
                            PhoneActivity.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    progressBar.setVisibility(View.GONE);
                                    otp.setVisibility(View.VISIBLE);
                                }
                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    progressBar.setVisibility(View.GONE);
                                    otp.setVisibility(View.VISIBLE);
                                    Toast.makeText(PhoneActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    Log.d("error ",e.getMessage());
                                }
                                @Override
                                public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    progressBar.setVisibility(View.GONE);
                                    otp.setVisibility(View.VISIBLE);
                                    Intent intent=new Intent(getApplicationContext(),OTPActivity.class);
                                    intent.putExtra("mobile",editnumber.getText().toString());
                                    intent.putExtra("backendotp",backendotp);
                                    startActivity(intent);
                                }
                            }
                    );
                }else {
                    Toast.makeText(PhoneActivity.this, "Please enter the correct number", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(PhoneActivity.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
            }
        });
    }
}