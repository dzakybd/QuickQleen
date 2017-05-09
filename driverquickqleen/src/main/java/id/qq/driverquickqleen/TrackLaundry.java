package id.qq.driverquickqleen;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

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
    LatLng driver,user,laundry;
    boolean locsetted = false;
    boolean ambil = false;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.map)
    MapView mMapView;
    @BindView(R.id.keterangan)
    TextView keterangan;
    @BindView(R.id.button)
    Button button;
    double distance,duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_laundry);
        ButterKnife.bind(this);
        distance=duration=0;
        setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
    }

    private void lewat(){
        Intent intent = new Intent(this, Main.class);
        intent.putExtra("nama", getIntent().getExtras().getString("nama"));
//        intent.putExtra("total", tot);
        intent.putExtra("c1", getIntent().getExtras().getInt("c1"));
        intent.putExtra("c2", getIntent().getExtras().getInt("c2"));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @OnClick(R.id.button)
    public void onViewClicked() {
        if(!ambil){
            ambil=true;
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Mengambil tugas");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TrackLaundry.this);
                    alertDialogBuilder.setMessage("Selamat bekerja!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                    button.setText("Sudah sampai");
                }
            }, 2000);
        }else{
            Intent intent = new Intent(this, DetailLaundry.class);
            startActivity(intent);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
//            mGoogleMap.setMyLocationEnabled(true);
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
            driver = new LatLng(location.getLatitude(),location.getLongitude());
            user = new LatLng(location.getLatitude()+0.005,location.getLongitude());
            laundry = new LatLng(location.getLatitude(),location.getLongitude()+0.005);
            mGoogleMap.addMarker(new MarkerOptions().position(driver).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_ojek))).setTitle("Driver");
            mGoogleMap.addMarker(new MarkerOptions().position(user).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_home))).setTitle("User");
            mGoogleMap.addMarker(new MarkerOptions().position(laundry).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_laundry))).setTitle("Laundry");
            mapsetted();
        }

    }

    public void mapsetted(){
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(user);
        builder.include(laundry);
        builder.include(driver);
        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 50);
        mGoogleMap.moveCamera(cu);
        if(getIntent().getExtras().getString("mode").contentEquals("0")){
         mapantar();
        }else mapjemput();
    }

    private void mapantar(){
        GoogleDirection.withServerKey(getResources().getString(R.string.googlegeneralkey))
                .from(driver)
                .to(user)
                .avoid(AvoidType.TOLLS)
                .avoid(AvoidType.FERRIES)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        if(direction.isOK()) {
                            ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
                            distance += Double.parseDouble(direction.getRouteList().get(0).getLegList().get(0).getDistance().getText().replace(" km",""));
                            duration += Double.parseDouble(direction.getRouteList().get(0).getLegList().get(0).getDuration().getText().replace(" mins",""));
                            mGoogleMap.addPolyline(DirectionConverter.createPolyline(TrackLaundry.this, directionPositionList, 5, ResourcesCompat.getColor(getResources(), R.color.primary_light, null)));
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
        GoogleDirection.withServerKey(getResources().getString(R.string.googlegeneralkey))
                .from(user)
                .to(laundry)
                .avoid(AvoidType.TOLLS)
                .avoid(AvoidType.FERRIES)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        if(direction.isOK()) {
                            ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
                            distance += Double.parseDouble(direction.getRouteList().get(0).getLegList().get(0).getDistance().getText().replace(" km",""));
                            duration += Double.parseDouble(direction.getRouteList().get(0).getLegList().get(0).getDuration().getText().replace(" mins",""));
                            keterangan.setText("Jarak :"+distance+" km\nWaktu :"+duration+" menit\nUpah : Rp "+Math.round(distance*3000));
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
    }

    private void mapjemput(){
        GoogleDirection.withServerKey(getResources().getString(R.string.googlegeneralkey))
                .from(driver)
                .to(laundry)
                .avoid(AvoidType.TOLLS)
                .avoid(AvoidType.FERRIES)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        if(direction.isOK()) {
                            ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
                            distance += Double.parseDouble(direction.getRouteList().get(0).getLegList().get(0).getDistance().getText().replace(" km",""));
                            duration += Double.parseDouble(direction.getRouteList().get(0).getLegList().get(0).getDuration().getText().replace(" mins",""));
                            mGoogleMap.addPolyline(DirectionConverter.createPolyline(TrackLaundry.this, directionPositionList, 5, ResourcesCompat.getColor(getResources(), R.color.primary_light, null)));
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
        GoogleDirection.withServerKey(getResources().getString(R.string.googlegeneralkey))
                .from(laundry)
                .to(user)
                .avoid(AvoidType.TOLLS)
                .avoid(AvoidType.FERRIES)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        if(direction.isOK()) {
                            ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
                            distance += Double.parseDouble(direction.getRouteList().get(0).getLegList().get(0).getDistance().getText().replace(" km",""));
                            duration += Double.parseDouble(direction.getRouteList().get(0).getLegList().get(0).getDuration().getText().replace(" mins",""));
                            keterangan.setText("Jarak :"+distance+" km\nWaktu :"+duration+" menit\nUpah : Rp "+Math.round(distance*3000));
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
