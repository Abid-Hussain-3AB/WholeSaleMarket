package com.example.myapplication.Seller;

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
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.DataClasses.ProductDataClass;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText etproductname, etproducttype,etproductmompany, etproductprice, etminsale, etmaxsale, etproductquantity,etproductdetail;
    Button btnaddproduct, btncancel, btnChooseimg;
    String str;
    ImageView imgproduct;
    String imageFileName = "";
    Uri contentUri;
    String item;
    private DatabaseReference mFirebaseDatabase;
    StorageReference mStorageReference;
    public static final int CAMERA_PERM_CODE = 101;
    public static final int GALLERY_REQUEST_CODE = 105;
    String uniqueID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Add Product");
        uniqueID = UUID.randomUUID().toString();
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(this);
        List Choice = new ArrayList();
        Choice.add("Electronics");
        Choice.add("Groceries");
        Choice.add("Home Appliances");
        Choice.add("Cosmetics");
        Choice.add("Jewelry");
        Choice.add("Apparel");
        Choice.add("Constructions");
        Choice.add("VehicleParts");
        Choice.add("Fertilizers");
        ArrayAdapter dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Choice);
        dataAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(dataAdapter);
        etproductname = findViewById(R.id.etProductName);
        etproducttype = findViewById(R.id.etProductType);
        etproductmompany = findViewById(R.id.etProductCompany);
        etproductprice = findViewById(R.id.etProducPrice);
        etminsale = findViewById(R.id.etProductMinForSale);
        etmaxsale = findViewById(R.id.etProductMaxForSale);
        etproductquantity = findViewById(R.id.etProductQuantity);
        etproductdetail = findViewById(R.id.etProductDetail);
        btnaddproduct = findViewById(R.id.btnAddProduct);
        btncancel = findViewById(R.id.btnCancel);
        btnChooseimg = findViewById(R.id.btnChooseIMG);
        imgproduct = findViewById(R.id.imgproduct);
        imgproduct.setVisibility(View.GONE);
        Intent intent = getIntent();
        str = intent.getStringExtra("shop");
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Products");
        mStorageReference = FirebaseStorage.getInstance().getReference();
        btnChooseimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // askCameraPermission();
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
                imgproduct.setVisibility(View.VISIBLE);
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnaddproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String name = etproductname.getText().toString();
                String type = etproducttype.getText().toString();
                String company = etproductmompany.getText().toString();
                String price = etproductprice.getText().toString();
                String min = etminsale.getText().toString();
                String max = etmaxsale.getText().toString();
                String quantity = etproductquantity.getText().toString();
                String detail = etproductdetail.getText().toString();
                if (name.isEmpty())
                {
                    Toast.makeText(AddProductActivity.this, "Please enter Product Name .", Toast.LENGTH_LONG).show();
                }
                else if (type.isEmpty()) {
                    Toast.makeText(AddProductActivity.this, "Please enter Product Type.", Toast.LENGTH_LONG).show();
                }
                else if (company.isEmpty()) {
                    Toast.makeText(AddProductActivity.this, "Please enter Product Company.", Toast.LENGTH_LONG).show();
                }
                else if (price.isEmpty()) {
                    Toast.makeText(AddProductActivity.this, "Please enter Product Price.", Toast.LENGTH_LONG).show();
                }
                else if (min.isEmpty()) {
                    Toast.makeText(AddProductActivity.this, "Please enter Product Minimum For Sale.", Toast.LENGTH_LONG).show();
                }
                else if (max.isEmpty()) {
                    Toast.makeText(AddProductActivity.this, "Please enter Product Maximum For Sale.", Toast.LENGTH_LONG).show();
                }
                else if (quantity.isEmpty()) {
                    Toast.makeText(AddProductActivity.this, "Please enter Product Quantity.", Toast.LENGTH_LONG).show();
                }
                else if (detail.isEmpty()) {
                    Toast.makeText(AddProductActivity.this, "Please enter Product Detail.", Toast.LENGTH_LONG).show();
                }
                else {
                    Pattern pattern = Pattern.compile("[^0-9]");
                    Matcher matcher1 = pattern.matcher(price);
                    Matcher matcher2 = pattern.matcher(min);
                    Matcher matcher3 = pattern.matcher(max);
                    Matcher matcher4 = pattern.matcher(quantity);
                    boolean isStringContainsSpecialCharacter = matcher1.find();
                    boolean isMinContainsSpecialCharacter = matcher2.find();
                    boolean isMaxContainsSpecialCharacter = matcher3.find();
                    boolean isQuantityContainsSpecialCharacter = matcher4.find();
                        if ( isStringContainsSpecialCharacter || Integer.parseInt(price) <= 0) {
                        Toast.makeText(AddProductActivity.this, "Please check Product Price.", Toast.LENGTH_LONG).show();
                    } else if (isMinContainsSpecialCharacter || isMaxContainsSpecialCharacter || Integer.parseInt(min) <= 0 || Integer.parseInt(max) < Integer.parseInt(min)) {
                        Toast.makeText(AddProductActivity.this, "Please check Product Minimum or Maximum field.", Toast.LENGTH_LONG).show();
                    } else if (isQuantityContainsSpecialCharacter ||  Integer.parseInt(quantity)<Integer.parseInt(max)) {
                        Toast.makeText(AddProductActivity.this, "Please check Product Quantity field.", Toast.LENGTH_LONG).show();
                    }  else if (imageFileName.isEmpty()) {
                        Toast.makeText(AddProductActivity.this, "Please Choose Images.", Toast.LENGTH_LONG).show();
                   }
                  else {
                          uploadImageToFirebase(imageFileName, uniqueID, contentUri);
                          finish();
                    }
                }
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
    private void createProducts(String productTpe, String uri1) {
        String name = etproductname.getText().toString();
        String type = etproducttype.getText().toString();
        String company = etproductmompany.getText().toString();
        String price = etproductprice.getText().toString();
        String min = etminsale.getText().toString();
        String max = etmaxsale.getText().toString();
        String quantity = etproductquantity.getText().toString();
        String detail = etproductdetail.getText().toString();
            mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ProductDataClass user = new ProductDataClass(name, type, company, price, min, max, quantity, detail, uniqueID, str, uri1);
                    mFirebaseDatabase.child(str).child(productTpe).child(uniqueID).setValue(user);
                    Toast.makeText(AddProductActivity.this, "Data Added", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(AddProductActivity.this, "Fail to Add Data " + databaseError, Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void askCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        } else {
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERM_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //openCamera();
            } else {
                Toast.makeText(this, "Camera Permission Required", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void openCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivity(camera,CAMERA_REQUEST_CODE);
        // startActivityForResult(camera,CAMERA_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == GALLERY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                contentUri = data.getData();
                String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss").format(new Date());
                imageFileName = "JPEG_" + timeStamp + "." + getFileExt(contentUri);
                Log.d("tag", "onActivityResult: Gallery Image Uri: " + imageFileName);
                imgproduct.setImageURI(contentUri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getFileExt(Uri contentUri) {
        ContentResolver c = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }

    public void uploadImageToFirebase(String fileName, String id, Uri uri) {
        StorageReference image = mStorageReference.child("pictures/" + fileName + str + id);
        // StorageReference image = mStorageReference.child("pictures/" + fileName+str);
        image.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String img = uri.toString();
                        if (item.equals("Electronics")) {
                            createProducts("Electronics", img);
                        } else if (item.equals("Groceries")) {
                            createProducts("Groceries", img);
                        } else if (item.equals("Home Appliances")) {
                            createProducts("Home Appliances", img);
                        } else if (item.equals("Cosmetics")) {
                            createProducts("Cosmetics", img);
                        } else if (item.equals("Jewelry")) {
                            createProducts("Jewelry", img);
                        } else if (item.equals("Apparel")) {
                            createProducts("Apparel", img);
                        } else if (item.equals("Constructions")) {
                            createProducts("Constructions", img);
                        } else if (item.equals("VehicleParts")) {
                            createProducts("VehicleParts", img);
                        } else if (item.equals("Fertilizers")) {
                            createProducts("Fertilizers", img);
                        }
                    }
                });
                Toast.makeText(AddProductActivity.this, "Upload Success Fully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddProductActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        item = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}