package com.example.ridesharing.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ridesharing.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class IdConfirmation extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    private ImageView photoImageView;
    private Uri imageUri;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.idconfirmation);

        photoImageView = findViewById(R.id.photoImageView);
        MaterialButton addPhotoButton = findViewById(R.id.add);
        Button doneButton = findViewById(R.id.btndone);

        storageReference = FirebaseStorage.getInstance().getReference().child("DriverID");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users/driversinfo").child(user.getUid()).child("DriverConfirmId");

        addPhotoButton.setOnClickListener(view -> showImageSourceDialog());

        doneButton.setOnClickListener(view -> {
            // Perform action on "Done" button click
            // Here you can add any additional logic before finishing the activity
            finish();
        });
    }

    private void showImageSourceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image Source");

        String[] options = {"Camera", "Gallery"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        dispatchTakePictureIntent();
                        break;
                    case 1:
                        pickImageFromGallery();
                        break;
                }
            }
        });

        builder.show();
    }

    private void pickImageFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_IMAGE_PICK);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    if (imageBitmap != null) {
                        photoImageView.setImageBitmap(imageBitmap);
                        uploadImage(imageBitmap);
                    }
                }
            } else if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                Uri selectedImageUri = data.getData();
                try {
                    Bitmap selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    photoImageView.setImageBitmap(selectedImageBitmap);
                    uploadImage(selectedImageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void uploadImage(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            String imageName = UUID.randomUUID().toString() + ".jpg";
            StorageReference imageRef = storageReference.child(imageName);

            imageRef.putBytes(data)
                    .addOnSuccessListener(taskSnapshot -> {
                        imageRef.getDownloadUrl()
                                .addOnSuccessListener(uri -> {
                                    String imageUrl = uri.toString();
                                    saveImageUrlToDatabase(imageUrl);
                                })
                                .addOnFailureListener(e -> {
                                    // Handle failure to get download URL
                                });
                    })
                    .addOnFailureListener(e -> {
                        // Handle upload failure
                    });
        }
    }

    private void saveImageUrlToDatabase(String imageUrl) {
        // Directly set the value under the "VehicleImg" node
        databaseReference.child("image_url").setValue(imageUrl)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                    // Return to VehicleInfoActivity
                    setResult(RESULT_OK);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}