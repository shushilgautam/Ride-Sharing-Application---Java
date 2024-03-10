package com.example.ridesharing.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ridesharing.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class DriverLiscenseActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE_PHOTO1 = 1;
    private static final int REQUEST_IMAGE_PICK_PHOTO1 = 2;
    private static final int REQUEST_IMAGE_CAPTURE_PHOTO2 = 3;
    private static final int REQUEST_IMAGE_PICK_PHOTO2 = 4;

    private ImageView photoImageView1, photoImageView2;

    TextInputEditText driverlicense;

    Button done;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_license);

        driverlicense = findViewById(R.id.lic_number);

        done = findViewById(R.id.btndone);


        // databaseReference = FirebaseDatabase.getInstance().getReference("users/driversinfo/DriverLicense");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users/driversinfo").child(user.getUid()).child("DriverLicense");
        storageReference = FirebaseStorage.getInstance().getReference();

        photoImageView1 = findViewById(R.id.photoImageView1);
        photoImageView2 = findViewById(R.id.photoImageView2);

        MaterialButton addPhotoButton1 = findViewById(R.id.btn_addphoto);
        MaterialButton addPhotoButton2 = findViewById(R.id.add_photo);

        addPhotoButton1.setOnClickListener(view -> showImageSourceDialog(REQUEST_IMAGE_CAPTURE_PHOTO1, REQUEST_IMAGE_PICK_PHOTO1));
        addPhotoButton2.setOnClickListener(view -> showImageSourceDialog(REQUEST_IMAGE_CAPTURE_PHOTO2, REQUEST_IMAGE_PICK_PHOTO2));

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadDataToFirebase();
            }
        });
    }

    private void showImageSourceDialog(final int captureRequestCode, final int pickRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image Source");

        String[] options = {"Camera", "Gallery"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        dispatchTakePictureIntent(captureRequestCode);
                        break;
                    case 1:
                        pickImageFromGallery(pickRequestCode);
                        break;
                }
            }
        });

        builder.show();
    }

    private void pickImageFromGallery(int requestCode) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, requestCode);
    }

    private void dispatchTakePictureIntent(int requestCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, requestCode);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Bitmap selectedImageBitmap = null;

            if (requestCode == REQUEST_IMAGE_CAPTURE_PHOTO1 || requestCode == REQUEST_IMAGE_PICK_PHOTO1) {
                selectedImageBitmap = handleImageResult(data);
                photoImageView1.setImageBitmap(selectedImageBitmap);
            } else if (requestCode == REQUEST_IMAGE_CAPTURE_PHOTO2 || requestCode == REQUEST_IMAGE_PICK_PHOTO2) {
                selectedImageBitmap = handleImageResult(data);
                photoImageView2.setImageBitmap(selectedImageBitmap);
            }
        }
    }

    private Bitmap handleImageResult(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            return (Bitmap) extras.get("data");
        } else {
            Uri selectedImageUri = data.getData();
            try {
                return MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void uploadDataToFirebase() {
        String licenseNumber = driverlicense.getText().toString().trim();
        if (licenseNumber.isEmpty()) {
            driverlicense.setError("Please enter license number");
            driverlicense.requestFocus();
            return;
        }
        if (photoImageView1.getDrawable() == null || photoImageView2.getDrawable() == null) {
            return;
        }
        Bitmap photo1Bitmap = ((BitmapDrawable) photoImageView1.getDrawable()).getBitmap();
        Bitmap photo2Bitmap = ((BitmapDrawable) photoImageView2.getDrawable()).getBitmap();

        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        photo1Bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos1);
        byte[] photo1Data = baos1.toByteArray();

        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        photo2Bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos2);
        byte[] photo2Data = baos2.toByteArray();

        String licensePhotoFolder = "driver_license_photos";

        String licensePhoto1Name = "license_photo_1.jpg";
        String licensePhoto2Name = "license_photo_2.jpg";

        StorageReference licensePhotoFolderRef = storageReference.child(licensePhotoFolder);

        StorageReference photo1Ref = licensePhotoFolderRef.child(licensePhoto1Name);
        StorageReference photo2Ref = licensePhotoFolderRef.child(licensePhoto2Name);

        UploadTask uploadTask1 = photo1Ref.putBytes(photo1Data);
        UploadTask uploadTask2 = photo2Ref.putBytes(photo2Data);

        uploadTask1.addOnSuccessListener(taskSnapshot -> {
            photo1Ref.getDownloadUrl().addOnSuccessListener(uri -> {
                String photo1Url = uri.toString();
                uploadTask2.addOnSuccessListener(taskSnapshot1 -> {
                    photo2Ref.getDownloadUrl().addOnSuccessListener(uri1 -> {
                        String photo2Url = uri1.toString();

                        // Store the license number along with the photo URLs
                        DriverClass driverClass = new DriverClass(licenseNumber, photo1Url, photo2Url);

                        // Set the value to the database reference directly without generating a key
                        databaseReference.setValue(driverClass)
                                .addOnSuccessListener(aVoid -> {
                                    // Data uploaded successfully
                                    Intent i = new Intent(DriverLiscenseActivity.this, DriverRegistrationActivity.class);
                                    startActivity(i);
                                })
                                .addOnFailureListener(e -> {
                                    // Handle failure
                                });
                    });
                });
            });
        }).addOnFailureListener(e -> {
            // Handle failure
        });
    }
}