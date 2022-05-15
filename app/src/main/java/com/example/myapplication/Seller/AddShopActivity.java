package com.example.myapplication.Seller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DataClasses.ShopDataClass;
import com.example.myapplication.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddShopActivity extends AppCompatActivity {
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;
    EditText etshopname, etshoptype, stshopcity, etshopaddress;
    Button btnrequest, btnlocation, btnncamera, btngallery;
    ImageView imgshop;
    String str;
    String imageFileName = "";
    Uri contentUri;
    StorageReference mStorageReference;
    private DatabaseReference mFirebaseDatabase;
    public static final int CAMERA_PERM_CODE = 101;
    FusedLocationProviderClient fusedLocationProviderClient;
    String Location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Add Shop");
        etshopname = findViewById(R.id.etShopName);
        etshoptype = findViewById(R.id.etShopType);
        stshopcity = findViewById(R.id.etShopCity);
        etshopaddress = findViewById(R.id.etShopAddress);
        btnrequest = findViewById(R.id.btnRequest);
        btnlocation = findViewById(R.id.btnLocation);
        btnncamera = findViewById(R.id.btnCamera);
        btngallery = findViewById(R.id.btnGallery);
        imgshop = findViewById(R.id.imgShop);
        imgshop.setVisibility(View.GONE);
        Intent intent = getIntent();
        str = intent.getStringExtra("shop");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Admin");
        btnncamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askCameraPermission();
            }
        });
        btnlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationEnabled();
                Location = "";
                if (ActivityCompat.checkSelfPermission(AddShopActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    Location();
                } else {
                    ActivityCompat.requestPermissions(AddShopActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });
        btngallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
                imgshop.setVisibility(View.VISIBLE);
            }
        });
        btnrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((TextUtils.isEmpty(etshopname.getText().toString()))) {
                    Toast.makeText(AddShopActivity.this, "Please enter Shop Name .", Toast.LENGTH_LONG).show();
                } else if ((TextUtils.isEmpty(etshoptype.getText().toString()))) {
                    Toast.makeText(AddShopActivity.this, "Please enter Shop Type.", Toast.LENGTH_LONG).show();
                } else if ((TextUtils.isEmpty(stshopcity.getText().toString()))) {
                    Toast.makeText(AddShopActivity.this, "Please enter Shop City.", Toast.LENGTH_LONG).show();
                } else if ((TextUtils.isEmpty(etshopaddress.getText().toString()))) {
                    Toast.makeText(AddShopActivity.this, "Please enter Shop Address.", Toast.LENGTH_LONG).show();
                }
                else if ((TextUtils.isEmpty(Location))) {
                    Toast.makeText(AddShopActivity.this, "Please Click on Location Button.", Toast.LENGTH_LONG).show();
                }
                else if (imageFileName.isEmpty()) {
                    Toast.makeText(AddShopActivity.this, "Please Choose Images.", Toast.LENGTH_LONG).show();
                } else {
                    uploadImageToFirebase(imageFileName, contentUri);
                    finish();
                }
            }
        });
    }
    private void Location() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(AddShopActivity.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                       // etshopname.setText(Html.fromHtml("<font color= '#6200EE'><b>Latitude</font>"
                         //       + addresses.get(0).getLatitude()));
                        Location = addresses.get(0).getAddressLine(0);
                        if (Location!="")
                        {
                            Toast.makeText(AddShopActivity.this, "Location Added", Toast.LENGTH_SHORT).show();
                            btnlocation.setText("My Location: "+Location);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
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
    private void createUser(String uri) {
        String Name = etshopname.getText().toString();
        String Type = etshoptype.getText().toString();
        String City = stshopcity.getText().toString();
        String Address = etshopaddress.getText().toString();
        mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(str))
                {
                    Toast.makeText(AddShopActivity.this, "Already Exist", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    ShopDataClass user = new ShopDataClass(Name, Type, City,Address,Location,str,uri);
                    mFirebaseDatabase.child("admin1").child(str).setValue(user);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void uploadImageToFirebase(String fileName, Uri uri)
    {
        StorageReference image = mStorageReference.child("pictures/" + fileName + "/"+str);
        image.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String img = uri.toString();
                        createUser(img);
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
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void locationEnabled () {
        LocationManager lm = (LocationManager) getSystemService(Context. LOCATION_SERVICE ) ;
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager. GPS_PROVIDER ) ;
        } catch (Exception e) {
            e.printStackTrace() ;
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager. NETWORK_PROVIDER ) ;
        } catch (Exception e) {
            e.printStackTrace() ;
        }
        if (!gps_enabled && !network_enabled) {
            new AlertDialog.Builder(AddShopActivity. this )
                    .setMessage( "Please Turn ON your Location" )
                    .setPositiveButton( "Settings" , new
                            DialogInterface.OnClickListener() {
                                @Override
                                public void onClick (DialogInterface paramDialogInterface , int paramInt) {
                                    startActivity( new Intent(Settings. ACTION_LOCATION_SOURCE_SETTINGS )) ;
                                }
                            })
                    .setNegativeButton( "Cancel" , null )
                    .show() ;
        }
    }
}