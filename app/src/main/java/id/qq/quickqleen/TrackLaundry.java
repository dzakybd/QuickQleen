package id.qq.quickqleen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrackLaundry extends AppCompatActivity {

    @BindView(R.id.from)
    EditText from;
    @BindView(R.id.to)
    EditText to;
    @BindView(R.id.map)
    MapView map;
    @BindView(R.id.harga)
    TextView harga;
    @BindView(R.id.order)
    Button order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_laundry);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.order)
    public void onViewClicked() {
    }
}
