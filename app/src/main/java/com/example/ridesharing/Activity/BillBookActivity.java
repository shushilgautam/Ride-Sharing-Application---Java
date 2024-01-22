package com.example.ridesharing.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ridesharing.R;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;

public class BillBookActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE_PHOTO1 = 1;
    private static final int REQUEST_IMAGE_PICK_PHOTO1 = 2;
    private static final int REQUEST_IMAGE_CAPTURE_PHOTO2 = 3;
    private static final int REQUEST_IMAGE_PICK_PHOTO2 = 4;

    private ImageView photoImageView1, photoImageView2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billbook);

        photoImageView1 = findViewById(R.id.photoImageView1);
        photoImageView2 = findViewById(R.id.photoImageView2);

        MaterialButton addPhotoButton1 = findViewById(R.id.add1);
        MaterialButton addPhotoButton2 = findViewById(R.id.add2);

        addPhotoButton1.setOnClickListener(view -> showImageSourceDialog(REQUEST_IMAGE_CAPTURE_PHOTO1, REQUEST_IMAGE_PICK_PHOTO1));
        addPhotoButton2.setOnClickListener(view -> showImageSourceDialog(REQUEST_IMAGE_CAPTURE_PHOTO2, REQUEST_IMAGE_PICK_PHOTO2));
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
}
