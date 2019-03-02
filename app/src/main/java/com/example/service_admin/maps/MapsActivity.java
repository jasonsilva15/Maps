package com.example.service_admin.maps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SNIHostName;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Bodega> bodegaList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        bodegaList.add(new Bodega("Bodega 1","Bodega de Tilapia Sps",15.685236,-87.926628));
        bodegaList.add(new Bodega("Bodega 2","Bodega de Tilapia Sps",14.685236,-88.923628));
        bodegaList.add(new Bodega("Bodega 3","Bodega de Tilapia Sps",15.685036,-86.928628));
        bodegaList.add(new Bodega("Bodega 4","Bodega de Tilapia Sps",14.685036,-85.920628));


    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
               //limpia ubicacion anterior
                mMap.clear();

                location.getLatitude();
                location.getLongitude();
                // Add a marker in Sydney and move the camera
                //LatLng sydney = new LatLng(-34, 151);
                //LatLng Honduras = new LatLng(15.2000, -86.2419);
                //ubicacion actual
                LatLng Honduras = new LatLng(location.getLatitude(), location.getLongitude());

                mMap.addMarker(new MarkerOptions().position(Honduras).title("Marker in honduras"));

                mMap.moveCamera(CameraUpdateFactory.newLatLng(Honduras));

                mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker marker) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                       Context context= getApplicationContext();
                       LinearLayout info =new LinearLayout(context);

                        TextView title =new TextView(context);
                        title.setTextColor(Color.BLUE);
                        title.setGravity(Gravity.CENTER);
                        title.setTypeface(null,Typeface.BOLD_ITALIC);
                        title.setText(marker.getTitle());

                        TextView snippet = new TextView(context);
                        snippet.setTextColor(Color.BLACK);
                        snippet.setText(marker.getSnippet());

                        info.addView(title);
                        info.addView(snippet);
                        return  info;
                    }

                });
                //SIRVE PARA EL MOVIMIENTO EN LA CAMARA
                mMap.animateCamera(CameraUpdateFactory.newLatLng(Honduras));


                for (int i =0 ; i < bodegaList.size(); i++) {
                    LatLng bodegaPosition = new LatLng(bodegaList.get(i).latitud, bodegaList.get(i).longitud);
                    mMap.addMarker(new MarkerOptions().position(bodegaPosition).title(bodegaList.get(i).nombre).snippet( bodegaList.get(i).ubicacion + "\n cantidad:1000 \n Fecha vencimiento: 21 Feb-2021"  ));

                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, listener);
    }
}
