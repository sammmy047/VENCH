package com.example.project;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.project.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                {
                    // Toast.makeText(MapsActivity.this, "Done", Toast.LENGTH_SHORT).show();
                    String u="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+mMap.getMyLocation().getLatitude()+"%2C"+mMap.getMyLocation().getLatitude()+"&radius=1500&type=restaurant&keyword=cruise&key=AIzaSyC7UKuwO25AjghBak_8UZtYFHr2vJtJELY";

                    try {
                        //  PlacesGetter places=new PlacesGetter();
                        //places.execute(mMap,u);
                        SetUpMarker(0.005f,0,1);
                        SetUpMarker(0.005f,0.005f,2);
                        SetUpMarker(-0.005f,0,3);
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(MapsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }

                return false;
            }
        } );
    }
    void SetUpMarker(float oX,float oY,int shopIndex)
    {
        MarkerOptions markerOptions = new MarkerOptions();
        LatLng latLng = new LatLng( mMap.getMyLocation().getLatitude()+oX, mMap.getMyLocation().getLongitude()+oY);
        markerOptions.position(latLng);
        markerOptions.title("Shop "+shopIndex);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mMap.addMarker(markerOptions);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                Intent t=new Intent(MapsActivity.this,chatwindow.class);
                t.putExtra("name","Shop 1");
                t.putExtra("email","ns");
                startActivity(t);
                Toast.makeText(getBaseContext(),marker.getTitle(), Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }
}
