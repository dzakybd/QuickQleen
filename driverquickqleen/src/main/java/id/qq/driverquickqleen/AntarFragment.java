package id.qq.driverquickqleen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class AntarFragment extends Fragment {


    @BindView(R.id.listantar)
    RecyclerView listantar;
    Unbinder unbinder;
    List<String> laundries;

    public AntarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_antar, container, false);
        unbinder = ButterKnife.bind(this, view);
        laundries = new ArrayList<>();
        laundries.add("Laundry 1,10 km");
        laundries.add("Laundry 2,20 km");
        laundries.add("Laundry 3,30 km");
        laundries.add("Laundry 1,10 km");
        laundries.add("Laundry 2,20 km");
        listantar.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        listantar.setHasFixedSize(true);
        LaundryRecyclerAdapter adapter = new LaundryRecyclerAdapter(getActivity(), laundries);
        listantar.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
