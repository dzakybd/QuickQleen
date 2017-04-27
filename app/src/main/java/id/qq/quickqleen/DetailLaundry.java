package id.qq.quickqleen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailLaundry extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.berat)
    EditText berat;
    @BindView(R.id.hitsendiri)
    CheckBox hitsendiri;
    @BindView(R.id.a)
    TextView a;
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
    @BindView(R.id.total)
    TextView total;
    @BindView(R.id.order)
    Button order;
    @BindView(R.id.con)
    LinearLayout con;
    int int_harga1,int_harga2,int_harga3,int_harga4,int_harga5,tot,kg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laundry);
        ButterKnife.bind(this);
        tot=kg=0;
        berat.setText("1");
        nama1.setText("Cuci Kering");
        nama2.setText("Cuci Setrika");
        nama3.setText("Cuci Mamel");
        nama4.setText("Cuci Wenter");
        nama5.setText("Cuci Sendiri");
        int_harga1=2000;
        int_harga2=3000;
        int_harga3=4000;
        int_harga4=5000;
        int_harga5=6000;
        setTitle(getIntent().getExtras().getString("nama"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        harga1.setText(String.valueOf(int_harga1)+"/kg");
        harga2.setText(String.valueOf(int_harga2)+"/kg");
        harga3.setText(String.valueOf(int_harga3)+"/kg");
        harga4.setText(String.valueOf(int_harga4)+"/kg");
        harga5.setText(String.valueOf(int_harga5)+"/kg");
        berat.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                updateharga();
            }
        });
    }


    @OnClick({R.id.hitsendiri, R.id.order,R.id.check1, R.id.check2, R.id.check3, R.id.check4, R.id.check5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.hitsendiri:
                if (hitsendiri.isChecked()) {
                    berat.setEnabled(false);
                } else {
                    berat.setEnabled(true);
                }
                break;
            case R.id.order:
                Intent intent = new Intent(this, TrackLaundry.class);
                intent.putExtra("nama", getIntent().getExtras().getString("nama"));
                intent.putExtra("total",tot);
                startActivity(intent);
                break;
            case R.id.check1:
                updateharga();
                break;
            case R.id.check2:
                updateharga();
                break;
            case R.id.check3:
                updateharga();
                break;
            case R.id.check4:
                updateharga();
                break;
            case R.id.check5:
                updateharga();
                break;
        }
    }

    private void updateharga(){
        tot=0;
        kg=0;
        if(berat.getText().toString().contentEquals(""))kg=0;
        else kg=Integer.parseInt(berat.getText().toString());
        if(check1.isChecked())tot+=int_harga1*kg;
        if(check2.isChecked())tot+=int_harga2*kg;
        if(check3.isChecked())tot+=int_harga3*kg;
        if(check4.isChecked())tot+=int_harga4*kg;
        if(check5.isChecked())tot+=int_harga5*kg;
        total.setText("Rp "+String.valueOf(tot));
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
