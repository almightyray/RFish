package com.example.rfishx;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LokasiPenangkapan extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi_penangkapan);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.LokasiPenangkapan);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            mapFragment = new SupportMapFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.LokasiPenangkapan, mapFragment).commit();
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng location1 = new LatLng(-4.4129857, 119.5366135);
        LatLng location2 = new LatLng(-5.100526, 119.300611);
        LatLng location3 = new LatLng(-2.097516,116.941838);
        LatLng location4 = new LatLng( -4.971904,117.027499);
        LatLng location5 = new LatLng(-2.107033,116.951356);
        LatLng location6 = new LatLng(-5.761886,119.654424);
        LatLng location7 = new LatLng(-4.952869,116.741963);
        LatLng location8 = new LatLng(-2.107033,116.941838);
        LatLng location9 = new LatLng(-5.999832,119.492620);
        LatLng location10 = new LatLng( -3.905906,116.637267);

        MarkerOptions options1 = new MarkerOptions()
                .position(location1)
                .title("Lokasi 1");
        MarkerOptions options2 = new MarkerOptions()
                .position(location2)
                .title("Lokasi 2");
        MarkerOptions options3 = new MarkerOptions()
                .position(location3)
                .title("Lokasi 3");
        MarkerOptions options4 = new MarkerOptions()
                .position(location4)
                .title("Lokasi 4");
        MarkerOptions options5 = new MarkerOptions()
                .position(location5)
                .title("Lokasi 5");
        MarkerOptions options6 = new MarkerOptions()
                .position(location6)
                .title("Lokasi 6");
        MarkerOptions options7 = new MarkerOptions()
                .position(location7)
                .title("Lokasi 7");
        MarkerOptions options8 = new MarkerOptions()
                .position(location8)
                .title("Lokasi 8");
        MarkerOptions options9 = new MarkerOptions()
                .position(location9)
                .title("Lokasi 9");
        MarkerOptions options10 = new MarkerOptions()
                .position(location10)
                .title("Lokasi 10");

        mMap.addMarker(options1);
        mMap.addMarker(options2);
        mMap.addMarker(options3);
        mMap.addMarker(options4);
        mMap.addMarker(options5);
        mMap.addMarker(options6);
        mMap.addMarker(options7);
        mMap.addMarker(options8);
        mMap.addMarker(options9);
        mMap.addMarker(options10);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location1, 10));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

        // Radius Penangkapan
        mMap.addCircle(new CircleOptions()
                .center(location1)
                .radius(500) // radius dalam meter
                .strokeColor(0xFF0000FF) // warna garis tepi (biru)
                .fillColor(0x220000FF) // warna isian dengan transparansi (biru)
                .strokeWidth(5)); // lebar garis tepi

        mMap.addCircle(new CircleOptions()
                .center(location2)
                .radius(500) // radius dalam meter
                .strokeColor(0xFF0000FF) // warna garis tepi (biru)
                .fillColor(0x220000FF) // warna isian dengan transparansi (biru)
                .strokeWidth(5)); // lebar garis tepi
    }
}
