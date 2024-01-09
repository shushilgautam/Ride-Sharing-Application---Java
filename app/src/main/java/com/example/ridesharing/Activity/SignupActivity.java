package com.example.ridesharing.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.text.TextUtils;

import com.example.ridesharing.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    Button signup;
    TextInputEditText signupfullname,signupemail,signupnumber,password,signupconfirmpassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        signup=findViewById(R.id.btnsignup);
        signupfullname=findViewById(R.id.fullname);
        signupemail=findViewById(R.id.email);
        signupnumber=findViewById(R.id.phonenumber);
        password=findViewById(R.id.password);
        signupconfirmpassword=findViewById(R.id.conpassword);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {


                    firebaseAuth.createUserWithEmailAndPassword(signupemail.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Toast.makeText(SignupActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users/normal");
                            DatabaseReference userRef = usersRef.child(user.getUid());


                            userRef.child("userId").setValue(user.getUid());
                            userRef.child("fullname").setValue(signupfullname.getText().toString());
                            userRef.child("email").setValue(signupemail.getText().toString());
                            userRef.child("number").setValue(signupnumber.getText().toString());
                            userRef.child("password").setValue(password.getText().toString());


                            Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(i);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("Error = ",e.getMessage() );
                        }
                    });

                }
            }

            private boolean validateInputs() {

                TextInputLayout tilFullname = findViewById(R.id.tilFullname);
                String username = signupfullname.getText().toString();
                String email = signupemail.getText().toString();
                String number = signupnumber.getText().toString();
                String userpassword = password.getText().toString();
                String confirmpassword = signupconfirmpassword.getText().toString();

                if (username.isEmpty() || email.isEmpty() || number.isEmpty() || userpassword.isEmpty() || confirmpassword.isEmpty()) {
                    showToast("All fields are required");
                    tilFullname.setBoxStrokeColor(getResources().getColor(R.color.your_error_stroke_color));
                    return false;
                }

                if (!isValidEmail(email)) {
                    showToast("Invalid email format");
                    return false;
                }

                if (!isValidPhoneNumber(number)) {
                    showToast("Invalid phone number");
                    return false;
                }

                if (!isValidPassword(userpassword, confirmpassword)) {
                    showToast("Passwords do not match");
                    return false;
                }


                return true;
            }

            private boolean isValidEmail(String email) {
                if (TextUtils.isEmpty(email)) {
                    showToast("Email is required");
                    return false;
                }


                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    showToast("Invalid email format");
                    return false;
                }


                if (!email.endsWith("gmail.com")) {
                    showToast("Email must be a Gmail account");
                    return false;
                }

                return true;
            }


            private boolean isValidPhoneNumber(String phoneNumber) {


                return !TextUtils.isEmpty(phoneNumber);
            }

            private boolean isValidPassword(String password, String confirmPassword) {

                return password.equals(confirmPassword);
            }

            private void showToast(String message) {
                Toast.makeText(SignupActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}


