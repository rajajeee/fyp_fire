package com.fyp.fyp_firebrigade;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class RequestActivity extends AppCompatActivity {
    ImageButton imageButton;
    EditText name, address, lat, lng;
    private Bitmap bitmap;
    private static final int IMAGE_REQUEST_CODE = 3;
    Button coordinates, add;
    boolean isPermissionGranted = false;
    FusedLocationProviderClient fusedLocationProviderClient;
    String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        imageButton = findViewById(R.id.pickImage);
        name = findViewById(R.id.edtRequestName);
        address = findViewById(R.id.edtRequestAddress);
        lat = findViewById(R.id.edtRequestLat);
        lng = findViewById(R.id.edtRequestLng);
        coordinates = findViewById(R.id.getCoordinates);
        add = findViewById(R.id.btnAddRequest);

        fusedLocationProviderClient = new FusedLocationProviderClient(getApplicationContext());

        checkPermission();
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPermissionGranted) {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 111);
                } else {
                    checkPermission();
                }
            }
        });

        coordinates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPermissionGranted) {
            getLocation();
                } else {
                    checkPermission();
                }
            }
        });
            webMethods methods = new webMethods(getApplicationContext());
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methods.addNewFireRequest(name.getText().toString(),address.getText().toString(),lat.getText().toString(),
                        lng.getText().toString(),bitmap);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
                bitmap = image;
            imageButton.setImageBitmap(image);
            imageButton.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }

    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(RequestActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(RequestActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(RequestActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            isPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(RequestActivity.this, permissions, IMAGE_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            isPermissionGranted = true;
        } else {
            return;
        }
    }

    public void getLocation() {

        @SuppressLint("MissingPermission") Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @SuppressLint("MissingPermission")
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    lat.setText(String.valueOf(location.getLatitude()));
                    lng.setText(String.valueOf(location.getLongitude()));
                    //     Toast.makeText(getContext(), current.toString(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),"Location Not Found. Try Again",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}