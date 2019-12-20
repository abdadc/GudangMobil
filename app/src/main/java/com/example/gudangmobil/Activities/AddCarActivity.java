package com.example.gudangmobil.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gudangmobil.MainMenuActivity;
import com.example.gudangmobil.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddCarActivity extends AppCompatActivity {

    EditText txtNama, txtNopol, txtWarna;
    Button choose, upload;
    ImageView imgview;
    StorageReference strRef;
    public Uri imguri;
    Spinner spinJenis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        strRef = FirebaseStorage.getInstance().getReference();

        choose = findViewById(R.id.btn_choose);
        upload = findViewById(R.id.btn_upload);
        imgview = findViewById(R.id.img_preview);
        txtNama = findViewById(R.id.edit_nama);
        txtNopol = findViewById(R.id.edit_nopol);
        txtWarna = findViewById(R.id.edit_warna);
        spinJenis = findViewById(R.id.spinner_jenis);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.jenis_mobil, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinJenis.setAdapter(adapter);

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileChooser();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileUploader();
            }
        });
    }

    private void FileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent ,1);
    }

    private void FileUploader(){
        final StorageReference reference = strRef.child(System.currentTimeMillis()+".");
        final String jenis = spinJenis.getSelectedItem().toString();
        final String nama = txtNama.getText().toString();
        final String nopol = txtNopol.getText().toString();
        final String warna = txtWarna.getText().toString();


        final UploadTask uploadTask =  reference.putFile(imguri);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                    @RequiresApi(api = Build.VERSION_CODES.P)
                    @Override
                    public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Toast.makeText(AddCarActivity.this,"Image Uploaded Succesfully",Toast.LENGTH_SHORT).show();
                        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if (!task.isSuccessful()) {
                                    Log.i("problem", task.getException().toString());
                                }

                                return reference.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    Uri downloadUri = task.getResult();
                                    System.out.println(downloadUri.toString());

                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(jenis).child(nama);

//                                    Log.i("seeThisUri", downloadUri.toString());// This is the one you should store

                                    ref.child("gambar").setValue(downloadUri.toString());
                                    ref.child("nama").setValue(nama);
                                    ref.child("nopol").setValue(nopol);
                                    ref.child("warna").setValue(warna);

                                    Intent moveit = new Intent(AddCarActivity.this , MainMenuActivity.class);
                                    startActivity(moveit);


                                } else {
                                    Log.i("wentWrong","downloadUri failure");
                                }
                            }
                        });
                    }

//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                            @Override
//                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                                if (!task.isSuccessful()) {
//                                    throw task.getException();
//
//                                }
//                                // Continue with the task to get the download URL
//                                return strRef.getDownloadUrl()+getExtension(imguri);

//                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });

    }

    private String getExtension(Uri uri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode == RESULT_OK & data != null ){
            imguri = data.getData();
            imgview.setImageURI(imguri);
        }
    }

}
