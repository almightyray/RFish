package com.example.rfishx;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class DataHasilTangkapan extends AppCompatActivity {
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final String TAG = "DataHasilTangkapan";

    private FirebaseAuth mAuth;
    private CaptureRepository captureRepository;
    private EditText fishingbaseEditText;
    private EditText shipNameEditText;
    private EditText fishNameEditText;
    private EditText captureDateEditText;
    private EditText fishWeightEditText;
    private EditText captureLocationEditText;
    private EditText jarakEditText;
    private TextView displayTextView;
    private FusedLocationProviderClient fusedLocationClient;

    private Location fishingBaseLocation;
    private Location fishingGroundLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_hasil_tangkapan);

        mAuth = FirebaseAuth.getInstance();
        captureRepository = new CaptureRepository();
        fishingbaseEditText = findViewById(R.id.fishingbaseEditText);
        shipNameEditText = findViewById(R.id.shipNameEditText);
        fishNameEditText = findViewById(R.id.fishNameEditText);
        captureDateEditText = findViewById(R.id.captureDateEditText);
        fishWeightEditText = findViewById(R.id.fishWeightEditText);
        captureLocationEditText = findViewById(R.id.captureLocationEditText);
        jarakEditText = findViewById(R.id.jarakEditText);
        displayTextView = findViewById(R.id.displayTextView);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> saveCaptureData());

        Button loadButton = findViewById(R.id.loadButton);
        loadButton.setOnClickListener(v -> loadCaptureData());

        Button lokasifishingbaseButton = findViewById(R.id.lokasifishingbase);
        lokasifishingbaseButton.setOnClickListener(v -> checkLocationPermission(true));

        Button lokasifishinggroundButton = findViewById(R.id.lokasifishingground);
        lokasifishinggroundButton.setOnClickListener(v -> checkLocationPermission(false));
    }

    private void checkLocationPermission(boolean isFishingBase) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            if (isFishingBase) {
                setFishingBaseLocation();
            } else {
                setFishingGroundLocation();
            }
        }
    }

    private void saveCaptureData() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            String shipName = shipNameEditText.getText().toString().trim();
            String fishName = fishNameEditText.getText().toString().trim();
            String captureDate = captureDateEditText.getText().toString().trim();
            String fishWeightStr = fishWeightEditText.getText().toString().trim();
            String captureLocation = captureLocationEditText.getText().toString().trim();
            String fishingBase = fishingbaseEditText.getText().toString().trim();
            String jarakStr = jarakEditText.getText().toString().trim();

            if (shipName.isEmpty() || fishName.isEmpty() || captureDate.isEmpty() || fishWeightStr.isEmpty() || captureLocation.isEmpty() || fishingBase.isEmpty() || jarakStr.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double fishWeight = Double.parseDouble(fishWeightStr);
            double jarak = Double.parseDouble(jarakStr);

            // Parse the fishing base and fishing ground coordinates
            double fishingBaseLatitude = 0.0;
            double fishingBaseLongitude = 0.0;
            double fishingGroundLatitude = 0.0;
            double fishingGroundLongitude = 0.0;

            try {
                String[] fishingBaseCoords = fishingBase.split(", ");
                fishingBaseLatitude = Double.parseDouble(fishingBaseCoords[0]);
                fishingBaseLongitude = Double.parseDouble(fishingBaseCoords[1]);

                String[] fishingGroundCoords = captureLocation.split(", ");
                fishingGroundLatitude = Double.parseDouble(fishingGroundCoords[0]);
                fishingGroundLongitude = Double.parseDouble(fishingGroundCoords[1]);
            } catch (Exception e) {
                Toast.makeText(this, "Invalid coordinate format. Please use 'latitude, longitude'", Toast.LENGTH_SHORT).show();
                return;
            }

            Capture capture = new Capture(userId, shipName, fishName, captureDate, fishWeight, captureLocation, fishingBase, jarak,
                    fishingBaseLatitude, fishingBaseLongitude, fishingGroundLatitude, fishingGroundLongitude);

            captureRepository.addCapture(capture, task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(DataHasilTangkapan.this, "Data saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DataHasilTangkapan.this, "Error saving data", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }


    private void loadCaptureData() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            captureRepository.getCapturesByUserId(currentUser.getUid(), task -> {
                if (task.isSuccessful()) {
                    StringBuilder displayText = new StringBuilder();
                    List<Capture> captureList = task.getResult().toObjects(Capture.class);
                    for (Capture capture : captureList) {
                        displayText.append("Ship Name: ").append(capture.getShipName()).append("\n")
                                .append("Fish Name: ").append(capture.getFishName()).append("\n")
                                .append("Capture Date: ").append(capture.getCaptureDate()).append("\n")
                                .append("Fish Weight: ").append(capture.getFishWeight()).append("\n")
                                .append("Capture Location: ").append(capture.getCaptureLocation()).append("\n")
                                .append("Fishing Base: ").append(capture.getFishingBase()).append("\n")
                                .append("Distance: ").append(capture.getJarak()).append("\n")
                                .append("\n");
                    }
                    displayTextView.setText(displayText.toString());
                } else {
                    Toast.makeText(this, "Error getting data", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }

    private void setFishingBaseLocation() {
        getCurrentLocation(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                Location location = task.getResult();
                fishingBaseLocation = location;
                String fishingBase = location.getLatitude() + ", " + location.getLongitude();
                fishingbaseEditText.setText(fishingBase);
            } else {
                Log.e(TAG, "Unable to get current location");
                Toast.makeText(this, "Unable to get current location", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setFishingGroundLocation() {
        getCurrentLocation(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                Location location = task.getResult();
                fishingGroundLocation = location;
                String fishingGround = location.getLatitude() + ", " + location.getLongitude();
                captureLocationEditText.setText(fishingGround);
                calculateAndSetDistance();
            } else {
                Log.e(TAG, "Unable to get current location");
                Toast.makeText(this, "Unable to get current location", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void calculateAndSetDistance() {
        if (fishingBaseLocation != null && fishingGroundLocation != null) {
            double distance = calculateDistance(fishingBaseLocation.getLatitude(), fishingBaseLocation.getLongitude(),
                    fishingGroundLocation.getLatitude(), fishingGroundLocation.getLongitude());
            jarakEditText.setText(String.format("%.2f", distance));
        } else {
            Toast.makeText(this, "Unable to calculate distance. Ensure both locations are set.", Toast.LENGTH_SHORT).show();
        }
    }

    private void getCurrentLocation(OnCompleteListener<Location> onCompleteListener) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            return;
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        fusedLocationClient.getCurrentLocation(locationRequest.getPriority(), null)
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to get location: " + e.getMessage());
                    Toast.makeText(this, "Failed to get location: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Location permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in kilometers
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;
        return distance;
    }
}
