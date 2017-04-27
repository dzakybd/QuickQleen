package id.qq.quickqleen;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.seatgeek.placesautocomplete.DetailsCallback;
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
import com.seatgeek.placesautocomplete.PlacesAutocompleteTextView;
import com.seatgeek.placesautocomplete.model.Place;
import com.seatgeek.placesautocomplete.model.PlaceDetails;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrackLaundry extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    GoogleMap mGoogleMap;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    LatLng awal,tujuan;
    boolean locsetted = false;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.from)
    PlacesAutocompleteTextView from;
    @BindView(R.id.to)
    PlacesAutocompleteTextView to;
    @BindView(R.id.map)
    MapView mMapView;
    @BindView(R.id.harga)
    TextView harga;
    @BindView(R.id.order)
    Button order;
    String distance,duration,price,total;
    int tot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_laundry);
        ButterKnife.bind(this);
        setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        tot = getIntent().getExtras().getInt("total");
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
        to.setEnabled(false);
        from.setOnPlaceSelectedListener(
                new OnPlaceSelectedListener() {
                    @Override
                    public void onPlaceSelected(final Place place) {
                        from.getDetailsFor(place, new DetailsCallback() {
                            @Override
                            public void onSuccess(final PlaceDetails details) {
                                from.setText(details.name);
                                awal=new LatLng(details.geometry.location.lat, details.geometry.location.lng);
                                mGoogleMap.addMarker(new MarkerOptions().position(awal));
                                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(awal));
                                to.setEnabled(true);
                            }
                            @Override
                            public void onFailure(final Throwable failure) {
                                Log.d("test", "failure " + failure);
                            }

                        });
                    }
                });

        to.setOnPlaceSelectedListener(
                new OnPlaceSelectedListener() {
                    @Override
                    public void onPlaceSelected(final Place place) {
                        to.getDetailsFor(place, new DetailsCallback() {
                            @Override
                            public void onSuccess(final PlaceDetails details) {
                                to.setText(details.name);
                                tujuan=new LatLng(details.geometry.location.lat, details.geometry.location.lng);
                                mGoogleMap.addMarker(new MarkerOptions().position(tujuan));
                                mapsetted();
                            }
                            @Override
                            public void onFailure(final Throwable failure) {
                                Log.d("test", "failure " + failure);
                            }
                        });
                    }
                });
    }

    @OnClick(R.id.order)
    public void onViewClicked() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mencari Driver");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(TrackLaundry.this, DriverFind.class);
                i.putExtra("awal.latitude",awal.latitude);
                i.putExtra("awal.longitude",awal.longitude);
                i.putExtra("tujuan.latitude",tujuan.latitude);
                i.putExtra("tujuan.longitude",tujuan.longitude);
                startActivity(i);
            }
        }, 2000);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if(!locsetted){
            locsetted = true;
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude())));
        }

    }

    public void mapsetted(){
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(awal);
        builder.include(tujuan);
        GoogleDirection.withServerKey(getResources().getString(R.string.googlegeneralkey))
                .from(awal)
                .to(tujuan)
                .avoid(AvoidType.TOLLS)
                .avoid(AvoidType.FERRIES)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        if(direction.isOK()) {
                            ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
                            distance = direction.getRouteList().get(0).getLegList().get(0).getDistance().getText();
                            duration = direction.getRouteList().get(0).getLegList().get(0).getDuration().getText();
                            price = "Rp "+String.valueOf((int)Double.parseDouble(distance.replace(" km",""))*3000);
                            total = "Rp "+String.valueOf((int)(Double.parseDouble(distance.replace(" km",""))*3000)+tot);
                            harga.setText("Jarak: "+distance+" | Waktu: "+duration+"\nCuci: "+"Rp "+tot+" | Antar: "+price+"\nTotal: "+total);
                            mGoogleMap.addPolyline(DirectionConverter.createPolyline(TrackLaundry.this, directionPositionList, 5, ResourcesCompat.getColor(getResources(), R.color.primary, null)));
                        } else {
                            Log.d("mapse", rawBody);
                            // Do something
                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        Log.d("mapse", t.toString());
                    }
                });
        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 50);
        mGoogleMap.moveCamera(cu);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
        mMapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mMapView.onDestroy();
//    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
