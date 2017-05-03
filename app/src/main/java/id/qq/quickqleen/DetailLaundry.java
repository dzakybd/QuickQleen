package id.qq.quickqleen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailLaundry extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.keterangan)
    RelativeLayout keterangan;
    @BindView(R.id.nama1)
    TextView nama1;
    @BindView(R.id.harga1)
    TextView harga1;
    @BindView(R.id.check1)
    CheckBox check1;
    @BindView(R.id.nama2)
    TextView nama2;
    @BindView(R.id.harga2)
    TextView harga2;
    @BindView(R.id.check2)
    CheckBox check2;
    @BindView(R.id.nama3)
    TextView nama3;
    @BindView(R.id.harga3)
    TextView harga3;
    @BindView(R.id.check3)
    CheckBox check3;
    @BindView(R.id.nama4)
    TextView nama4;
    @BindView(R.id.harga4)
    TextView harga4;
    @BindView(R.id.check4)
    CheckBox check4;
    @BindView(R.id.nama5)
    TextView nama5;
    @BindView(R.id.harga5)
    TextView harga5;
    @BindView(R.id.check5)
    CheckBox check5;
    @BindView(R.id.listpilihan)
    ScrollView listpilihan;
    @BindView(R.id.order)
    Button order;
    int int_harga1, int_harga2, int_harga3, int_harga4, int_harga5;
    @BindView(R.id.mode)
    Spinner mode;
    @BindView(R.id.head)
    LinearLayout head;
    @BindView(R.id.a)
    TextView a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laundry);
        ButterKnife.bind(this);
        nama1.setText("Cuci Kering");
        nama2.setText("Cuci Setrika");
        nama3.setText("Cuci Mamel");
        nama4.setText("Cuci Wenter");
        nama5.setText("Cuci Sendiri");
        int_harga1 = 2000;
        int_harga2 = 3000;
        int_harga3 = 4000;
        int_harga4 = 5000;
        int_harga5 = 6000;
        setTitle(getIntent().getExtras().getString("nama"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        harga1.setText(String.valueOf(int_harga1) + "/kg");
        harga2.setText(String.valueOf(int_harga2) + "/kg");
        harga3.setText(String.valueOf(int_harga3) + "/kg");
        harga4.setText(String.valueOf(int_harga4) + "/kg");
        harga5.setText(String.valueOf(int_harga5) + "/kg");

        // Spinner click listener
        mode.setOnItemSelectedListener(DetailLaundry.this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<>();
        categories.add("Antar");
        categories.add("Jemput");
        categories.add("Antar + Jemput");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mode.setAdapter(dataAdapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
//        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @OnClick({R.id.order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.order:
                Intent intent = new Intent(this, TrackLaundry.class);
                intent.putExtra("nama", getIntent().getExtras().getString("nama"));
                startActivity(intent);
                break;
        }
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
