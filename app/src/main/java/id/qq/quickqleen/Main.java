package id.qq.quickqleen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
    @BindView(R.id.promo)
    CardView promo;
    @BindView(R.id.all)
    CardView all;

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
        laundries.add("Laundry 3,30 km");
        laundries.add("Laundry 1,10 km");
        laundries.add("Laundry 2,20 km");
        laundries.add("Laundry 3,30 km");
        laundries.add("Laundry 1,10 km");
        laundries.add("Laundry 2,20 km");
        laundries.add("Laundry 3,30 km");
        listlaundry.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listlaundry.setHasFixedSize(true);
        LaundryRecyclerAdapter adapter = new LaundryRecyclerAdapter(getApplicationContext(), laundries);
        listlaundry.setAdapter(adapter);

    }

    @OnClick({R.id.near, R.id.promo, R.id.all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.near:
                break;
            case R.id.promo:
                break;
            case R.id.all:
                break;
        }
    }
}
