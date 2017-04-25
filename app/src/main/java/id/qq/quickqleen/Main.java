package id.qq.quickqleen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main extends AppCompatActivity {

    @BindView(R.id.listlaundry)
    RecyclerView listlaundry;
    List<String> laundries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        laundries=new ArrayList<>();
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
}
