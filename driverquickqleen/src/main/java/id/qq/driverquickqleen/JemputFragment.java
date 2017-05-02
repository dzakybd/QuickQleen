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
public class JemputFragment extends Fragment {


    @BindView(R.id.listjemput)
    RecyclerView listjemput;
    Unbinder unbinder;
    List<String> laundries;

    public JemputFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jemput, container, false);
        unbinder = ButterKnife.bind(this, view);
        laundries = new ArrayList<>();
        laundries.add("Laundry 12,10 km");
        laundries.add("Laundry 21,20 km");
        laundries.add("Laundry 31,30 km");
        laundries.add("Laundry 15,10 km");
        laundries.add("Laundry 22,20 km");
        listjemput.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        listjemput.setHasFixedSize(true);
        LaundryRecyclerAdapter adapter = new LaundryRecyclerAdapter(getActivity(), laundries);
        listjemput.setAdapter(adapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
