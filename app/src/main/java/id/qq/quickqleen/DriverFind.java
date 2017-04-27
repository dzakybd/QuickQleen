package id.qq.quickqleen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import de.hdodenhof.circleimageview.CircleImageView;

public class DriverFind extends AppCompatActivity implements OnMapReadyCallback {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.image)
    CircleImageView image;
    @BindView(R.id.nama)
    TextView nama;
    @BindView(R.id.plat)
    TextView plat;
    @BindView(R.id.map)
    MapView mMapView;
    @BindView(R.id.call)
    Button call;
    @BindView(R.id.message)
    Button message;
    GoogleMap mGoogleMap;
    LatLng awal,tujuan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_find);
        ButterKnife.bind(this);
        setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Driver found!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        awal = new LatLng(getIntent().getExtras().getDouble("awal.latitude"),getIntent().getExtras().getDouble("awal.longitude"));
        tujuan = new LatLng(getIntent().getExtras().getDouble("tujuan.latitude"),getIntent().getExtras().getDouble("tujuan.longitude"));
        mGoogleMap.addMarker(new MarkerOptions().position(awal).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_angkot)));
        mGoogleMap.addMarker(new MarkerOptions().position(tujuan));
        mapsetted();
    }

    @Override
    public void onPause() {
        super.onPause();
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
                Intent i = new Intent(DriverFind.this, Main.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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
                            mGoogleMap.addPolyline(DirectionConverter.createPolyline(DriverFind.this, directionPositionList, 5, ResourcesCompat.getColor(getResources(), R.color.primary, null)));
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
}
