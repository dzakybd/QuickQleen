package id.qq.driverquickqleen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailLaundry extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.image)
    CircleImageView image;
    @BindView(R.id.view1)
    CardView view1;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.berat1)
    EditText berat1;
    @BindView(R.id.berat2)
    EditText berat2;
    @BindView(R.id.berat3)
    EditText berat3;
    @BindView(R.id.totalberat)
    TextView totalberat;
    @BindView(R.id.hargaberat)
    TextView hargaberat;
    @BindView(R.id.totalharga)
    TextView totalharga;
    @BindView(R.id.view2)
    CardView view2;
    @BindView(R.id.hargaantar)
    TextView hargaantar;
    @BindView(R.id.konfirmasi)
    Button konfirmasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laundry);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        berat1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hitungberat();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        berat2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hitungberat();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        berat3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hitungberat();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void hitungberat() {
        int a, b, c;
        int antar = 7000;
        int h_berat;
        if (berat1.getText().toString().contentEquals("")) a = 0;
        else a = Integer.parseInt(berat1.getText().toString());

        if (berat2.getText().toString().contentEquals("")) b = 0;
        else b = Integer.parseInt(berat2.getText().toString());

        if (berat3.getText().toString().contentEquals("")) c = 0;
        else c = Integer.parseInt(berat3.getText().toString());
        totalberat.setText(String.valueOf(a + b + c));
        hargaberat.setText(String.valueOf((a * 2000) + (b * 4000) + (c * 3000)));

        hargaantar.setText(String.valueOf(antar));
        totalharga.setText(String.valueOf(Integer.parseInt(hargaberat.getText().toString()) + Integer.parseInt(hargaantar.getText().toString())));
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


    @OnClick(R.id.konfirmasi)
    public void onViewClicked() {
        Intent i = new Intent(DetailLaundry.this, Main.class);
        startActivity(i);
        finish();
    }
}
