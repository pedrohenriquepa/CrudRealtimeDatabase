package com.example.tilt;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

public class UploadActivity extends AppCompatActivity {

    ImageView uploadImage;
    Button saveButton;
    EditText uploadTopic, uploadDesc, uploadLang;
    String imageURL;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        uploadImage = findViewById(R.id.uploadImage);
        uploadDesc = findViewById(R.id.uploadDesc);
        uploadTopic = findViewById(R.id.uploadTopic);
        uploadLang = findViewById(R.id.uploadLang);
        saveButton = findViewById(R.id.saveButton);


        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri = data.getData();
                            uploadImage.setImageURI(uri);
                        } else {
                            Toast.makeText(UploadActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );


        uploadImage.setOnClickListener(view -> {
            Intent photoPicker = new Intent(Intent.ACTION_PICK);
            photoPicker.setType("image/*");
            activityResultLauncher.launch(photoPicker);
        });

        saveButton.setOnClickListener(view -> saveData());
    }

    private void saveData() {

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Android Images")
                .child(uri.getLastPathSegment());


        AlertDialog.Builder builder = new AlertDialog.Builder(UploadActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();


        storageReference.putFile(uri).addOnSuccessListener(taskSnapshot ->
                taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(uriTask -> {
                    if (uriTask.isSuccessful()) {
                        Uri urlImage = uriTask.getResult();
                        imageURL = urlImage.toString();
                        uploadData();
                        dialog.dismiss();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(UploadActivity.this, "Failed to get download URL", Toast.LENGTH_SHORT).show();
                    }
                })).addOnFailureListener(e -> {
            dialog.dismiss();
            Toast.makeText(UploadActivity.this, "Failed to upload file", Toast.LENGTH_SHORT).show();
        });
    }

    private void uploadData() {

        String title = uploadTopic.getText().toString();
        String desc = uploadDesc.getText().toString();
        String lang = uploadLang.getText().toString();

        DataClass dataClass = new DataClass(title, desc, lang, imageURL);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Android Tutorials").child(title);
        databaseReference.setValue(dataClass).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(UploadActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(UploadActivity.this, "Failed to save data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
