package com.example.myapplication.Owner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.DataClasses.ShopDataClass;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddShopActivity extends AppCompatActivity {
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;
    EditText etshopname, etshoptype, stshopcity;
    Button btnrequest, btnncamera, btngallery;
    ImageView imgshop;
    String str;
    String imageFileName;
    Uri contentUri;
    StorageReference mStorageReference;
    private DatabaseReference mFirebaseDatabase;
    public static final int CAMERA_PERM_CODE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);
        etshopname = findViewById(R.id.etShopName);
        etshoptype = findViewById(R.id.etShopType);
        stshopcity = findViewById(R.id.etShopCity);
        btnrequest = findViewById(R.id.btnRequest);
        btnncamera = findViewById(R.id.btnCamera);
        btngallery = findViewById(R.id.btnGallery);
        imgshop = findViewById(R.id.imgShop);
        Intent intent = getIntent();
        str = intent.getStringExtra("shop");
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Admin");
        btnncamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askCameraPermission();
            }
        });
        btngallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
            }
        });
        btnrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             createUser();
             finish();
            }
        });
    }
    private void askCameraPermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},CAMERA_PERM_CODE);
        }
        else {
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERM_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera Permission Required", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void openCamera()
    {
       Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       //startActivity(camera,CAMERA_REQUEST_CODE);
       startActivityForResult(camera,CAMERA_REQUEST_CODE);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == CAMERA_REQUEST_CODE)
        {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            imgshop.setImageBitmap(image);
        }
        if (requestCode == GALLERY_REQUEST_CODE)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                contentUri = data.getData();
                String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss").format(new Date());
                imageFileName = "JPEG_" + timeStamp + "." + getFileExt(contentUri);
                Log.d("tag","onActivityResult: Gallery Image Uri: " +imageFileName);
                imgshop.setImageURI(contentUri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private String getFileExt(Uri contentUri)
    {
        ContentResolver c = getContentResolver();
        MimeTypeMap mime  = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }
    private void createUser() {
        String Name = etshopname.getText().toString();
        String Type = etshoptype.getText().toString();
        String City = stshopcity.getText().toString();
        mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(str))
                {
                    Toast.makeText(AddShopActivity.this, "Already Exist", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    ShopDataClass user = new ShopDataClass(Name, Type, City,str);
                    mFirebaseDatabase.child("admin1").child(str).setValue(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        uploadImageToFirebase(imageFileName, contentUri);
    }
    public void uploadImageToFirebase(String fileName, Uri uri)
    {
        StorageReference image = mStorageReference.child("pictures/" + fileName + "/"+str);
        // StorageReference image = mStorageReference.child("pictures/" + fileName+str);
        image.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                    }
                });
                Toast.makeText(AddShopActivity.this, "Upload Success Fully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddShopActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}