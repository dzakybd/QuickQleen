package id.qq.quickqleen;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main extends AppCompatActivity {

    @BindView(R.id.listlaundry)
    RecyclerView listlaundry;
    List<String> laundries;
    @BindView(R.id.near)
    CardView near;
    @BindView(R.id.all)
    CardView all;
    @BindView(R.id.myorder)
    CardView myorder;
    @BindView(R.id.text_home)
    TextView textHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        laundries = new ArrayList<>();
        laundries.add("Laundry 1,10 km");
        laundries.add("Laundry 2,20 km");
        laundries.add("Laundry 3,30 km");
        laundries.add("Laundry 1,10 km");
        laundries.add("Laundry 2,20 km");
        listlaundry.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listlaundry.setHasFixedSize(true);
        LaundryRecyclerAdapter adapter = new LaundryRecyclerAdapter(getApplicationContext(), laundries);
        listlaundry.setAdapter(adapter);
    }

    @OnClick({R.id.near, R.id.all, R.id.myorder})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.near:
                textHome.setText("Near My Location");
                Log.d("errornih",textHome.getText().toString());
                laundries = new ArrayList<>();
                laundries.add("wiw,3 km");
                laundries.add("waw,1 km");
                laundries.add("wow,2 km");
                laundries.add("wew,3 km");
                LaundryRecyclerAdapter adapter = new LaundryRecyclerAdapter(getApplicationContext(), laundries);
                listlaundry.setAdapter(adapter);
                break;
            case R.id.all:
                textHome.setText("All Laundry");
                Log.d("errornih",textHome.getText().toString());
                laundries = new ArrayList<>();
                laundries.add("Laundry A,3 km");
                laundries.add("Laundry B,1 km");
                laundries.add("Laundry C,2 km");
                laundries.add("Laundry D,3 km");
                laundries.add("Laundry E,3 km");
                laundries.add("Laundry F,3 km");
                laundries.add("Laundry G,3 km");
                laundries.add("Laundry H,3 km");
                laundries.add("Laundry I,3 km");
                LaundryRecyclerAdapter adapterall = new LaundryRecyclerAdapter(getApplicationContext(), laundries);
                listlaundry.setAdapter(adapterall);
                break;
            case R.id.myorder:
                textHome.setText("My Order");
                Log.d("errornih",textHome.getText().toString());
                laundries = new ArrayList<>();
                laundries.add("Laundry Gebang,3 km");
                laundries.add("Laundry Keputih,1 km");
                laundries.add("Laundry Mulyos,2 km");
                laundries.add("Laundry Kertajaya,3 km");
                LaundryRecyclerAdapter adaptermyorder = new LaundryRecyclerAdapter(getApplicationContext(), laundries);
                listlaundry.setAdapter(adaptermyorder);
                break;
        }
    }

    private void out() {
        AlertDialog.Builder pilihan = new AlertDialog.Builder(this);
        pilihan.setMessage("Anda ingin keluar?");
        pilihan.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Prefs.clear();
                startActivity(new Intent(Main.this, Login.class));
                finish();
            }
        });
        pilihan.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alert = pilihan.create();
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.findItem(R.id.out).setIcon(
                new IconicsDrawable(this)
                        .icon(FontAwesome.Icon.faw_sign_out)
                        .color(Color.WHITE)
                        .actionBar()
        );
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.out:
                out();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
