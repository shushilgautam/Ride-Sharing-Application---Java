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
                            sendEmailVerification(user);
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

            private void sendEmailVerification(FirebaseUser user) {
                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(SignupActivity.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            private boolean validateInputs() {
                TextInputLayout tilFullname = findViewById(R.id.tilFullname);
                TextInputLayout tilEmail = findViewById(R.id.tilemail);
                TextInputLayout tilPhoneNumber = findViewById(R.id.tilnumber);
                TextInputLayout tilPassword = findViewById(R.id.tilpassword);
                TextInputLayout tilConfirmPassword = findViewById(R.id.tilconpassword);

                String username = signupfullname.getText().toString();
                String email = signupemail.getText().toString();
                String number = signupnumber.getText().toString();
                String userpassword = password.getText().toString();
                String confirmpassword = signupconfirmpassword.getText().toString();

                boolean isValid = true;

                if (username.isEmpty()) {
                    tilFullname.setError("Full name is required");
                    isValid = false;
                } else if (!isValidUserName(username)) {
                    tilFullname.setError("Invalid userrname format");
                    isValid = false;
                } else {
                    tilFullname.setError(null);
                }

                if (email.isEmpty()) {
                    tilEmail.setError("Email is required");
                    isValid = false;
                } else if (!isValidEmail(email)) {
                    tilEmail.setError("Invalid email format");
                    isValid = false;
                } else {
                    tilEmail.setError(null);
                }

                if (number.isEmpty()) {
                    tilPhoneNumber.setError("Phone number is required");
                    isValid = false;
                } else if (!isValidPhoneNumber(number)) {
                    tilPhoneNumber.setError("Invalid phone number");
                    isValid = false;
                } else {
                    tilPhoneNumber.setError(null);
                }

                if (userpassword.isEmpty()) {
                    tilPassword.setError("Password is required");
                    isValid = false;
                } else {
                    tilPassword.setError(null);
                }


                if (confirmpassword.isEmpty()) {
                    tilConfirmPassword.setError("Confirm password is required");
                    isValid = false;
                } else if (!isValidPassword(userpassword, confirmpassword)) {
                    tilConfirmPassword.setError("Passwords do not match");
                    tilConfirmPassword.setErrorEnabled(true); // Enable error visibility
                    isValid = false;
                } else {
                    tilConfirmPassword.setError(null);
                    tilConfirmPassword.setErrorEnabled(false); // Disable error visibility
                }

                return isValid;
            }





            private boolean isValidUserName(String fullName) {
                if (TextUtils.isEmpty(fullName)) {
                    return false;
                }

                if (!fullName.contains(" ")) {
                    return false;
                }

                return true;
            }


            private boolean isValidEmail(String email) {
                if (TextUtils.isEmpty(email)) {
                    return false;
                }

                return Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.endsWith("gmail.com");
            }


            private boolean isValidPhoneNumber(String phoneNumber) {
                if (TextUtils.isEmpty(phoneNumber)) {
                    return false;
                }


                return phoneNumber.startsWith("98") && phoneNumber.length() == 10;
            }
            private boolean isValidPassword(String password, String confirmPassword) {
                if (TextUtils.isEmpty(password)) {
                    return false;
                }


                String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$";

                return password.matches(passwordRegex) && password.equals(confirmPassword);
            }

        });
    }
}