package com.example.ridesharing.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class BillBookActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE_PHOTO1 = 1;
    private static final int REQUEST_IMAGE_PICK_PHOTO1 = 2;
    private static final int REQUEST_IMAGE_CAPTURE_PHOTO2 = 3;
    private static final int REQUEST_IMAGE_PICK_PHOTO2 = 4;

    private ImageView photoImageView1, photoImageView2;
    private TextInputEditText vehicleProductionYearEditText;
    private MaterialButton addPhotoButton1, addPhotoButton2;

    Button doneButton;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    private String uniqueId; // Declaring the unique ID at class level

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billbook);

        photoImageView1 = findViewById(R.id.photoImageView1);
        photoImageView2 = findViewById(R.id.photoImageView2);
        vehicleProductionYearEditText = findViewById(R.id.production_year);
        addPhotoButton1 = findViewById(R.id.add1);
        addPhotoButton2 = findViewById(R.id.add2);
        doneButton = findViewById(R.id.btndone);

        storageReference = FirebaseStorage.getInstance().getReference().child("BillBookImages");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users/driversinfo").child(user.getUid()).child("Billbook");

        addPhotoButton1.setOnClickListener(view -> showImageSourceDialog(REQUEST_IMAGE_CAPTURE_PHOTO1, REQUEST_IMAGE_PICK_PHOTO1));
        addPhotoButton2.setOnClickListener(view -> showImageSourceDialog(REQUEST_IMAGE_CAPTURE_PHOTO2, REQUEST_IMAGE_PICK_PHOTO2));

        doneButton.setOnClickListener(view -> {
            if (vehicleProductionYearEditText.getText().toString().isEmpty()) {
                // Production year is empty
                showDialog("Please enter the vehicle production year.");
            } else if (photoImageView1.getDrawable() == null || photoImageView2.getDrawable() == null) {
                // Both photos are not uploaded
                showDialog("Please upload both photos.");
            } else {
                // All fields are filled, upload data
                uploadData();
            }
        });

        // Generate unique ID once in onCreate
        uniqueId = databaseReference.push().getKey();
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void uploadData() {
        // Upload photos and production year to database
        uploadImage(photoImageView1, "photo1");
        uploadImage(photoImageView2, "photo2");
        saveDataToDatabase(vehicleProductionYearEditText.getText().toString());
    }

    private void uploadImage(ImageView imageView, final String photoName) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        String imageName = UUID.randomUUID().toString() + ".jpg";
        StorageReference imageRef = storageReference.child(imageName);

        imageRef.putBytes(data).addOnSuccessListener(taskSnapshot -> {
            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                String imageUrl = uri.toString();
                // Use the same uniqueId for both photos
                if (uniqueId != null) {
                    databaseReference.child(photoName).setValue(imageUrl);
                }
            });
        }).addOnFailureListener(e -> {
            // Handle upload failure
        });
    }

    private void saveDataToDatabase(String productionYear) {
        if (uniqueId != null) {
            databaseReference.child("ProductionYear").setValue(productionYear)
                    .addOnSuccessListener(aVoid -> {
                        // Data uploaded successfully, show success message and return to VehicleInfoActivity
                        //showDialog("Data uploaded successfully.");
                        startActivity(new Intent(BillBookActivity.this, VehicleInfoActivity.class));
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        // Handle database upload failure
                        showDialog("Failed to upload data. Please try again.");
                    });
        }
    }

    private void showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    // OK button clicked, dismiss dialog
                    dialog.dismiss();
                });
        builder.create().show();
    }
}