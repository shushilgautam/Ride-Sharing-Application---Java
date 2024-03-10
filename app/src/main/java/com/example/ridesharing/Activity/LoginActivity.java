package com.example.ridesharing.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ridesharing.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final int RC_SIGN_IN = 9001; // Request code for Google Sign-In
    private GoogleApiClient mGoogleApiClient;
    TextInputEditText email ,password;
    private TextView forgotPassword, signup;
    MaterialButton Continue,phoneIntent;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);
        Continue=findViewById(R.id.continebutton);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        signup=findViewById(R.id.signupTextView);
        phoneIntent=findViewById(R.id.enternumberbutton);
        phoneIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,PhoneActivity.class));
            }
        });
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                         Toast.makeText(LoginActivity.this, authResult.getUser().getDisplayName().toString(), Toast.LENGTH_SHORT).show();
                        Intent i =new Intent(LoginActivity.this,HomeActivity.class);

                        startActivity(i);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(LoginActivity.this,SignupActivity.class));
                Intent i =new Intent(LoginActivity.this,DriverRegistrationActivity.class);
                startActivity(i);
            }
        });
        // Set up Google Sign-In options
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleApiClient with access to the Google Sign-In API
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        forgotPassword = findViewById(R.id.forgotPasswordTextView);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(i);
            }
        });
        // Set click listener for the Google button
        findViewById(R.id.googlebutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the Google Sign-In process
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Handle connection failures
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, get user's information
            GoogleSignInAccount account = result.getSignInAccount();
            // Retrieve user details
            String idToken = account.getIdToken();
            String fullName = account.getDisplayName();
            // Send idToken to your server for verification
            // Obtain a Firebase custom token from your server
            String customFirebaseToken = getCustomFirebaseTokenFromServer(idToken);
            // Sign in with the custom token
            signInWithCustomToken(customFirebaseToken);
        } else {
            // Handle unsuccessful sign-in
        }
    }
    private String getCustomFirebaseTokenFromServer(String idToken) {
        // Call your server API to get the custom Firebase token
        // You need to implement this on your server
        // The server should verify the idToken and generate a Firebase custom token
        // For simplicity, this example assumes the server directly returns a custom token
        // You should implement proper security checks in your actual server implementation
        // In a real-world scenario, you'd make a network call to your server to get the token
        // Replace this with your actual server API call
        // Example implementation (replace this with your actual server call):
        // return makeServerApiCall(idToken);
        return "your_custom_firebase_token";
    }
    private void signInWithCustomToken(String customToken) {
        FirebaseAuth.getInstance().signInWithCredential(GoogleAuthProvider.getCredential(customToken, null))
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        // Update UI or handle further actions
                    } else {
                        // If sign in fails, display a message to the user.
                        // Handle unsuccessful sign-in
                    }
                });
    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        FirebaseUser user1= firebaseAuth.getCurrentUser();
//        if(firebaseAuth.getCurrentUser()!=null)
//                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
//    }
}