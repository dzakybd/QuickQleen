package id.qq.quickqleen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailLaundry extends AppCompatActivity {

    @BindView(R.id.text)
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laundry);
        ButterKnife.bind(this);
        text.setText(getIntent().getExtras().getString("nama")+" "+getIntent().getExtras().getString("jarak"));
    }
}
