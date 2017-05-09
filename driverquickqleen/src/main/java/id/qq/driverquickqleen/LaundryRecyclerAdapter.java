package id.qq.driverquickqleen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ilham Aulia Majid on 19-Apr-17.
 */

public class LaundryRecyclerAdapter extends RecyclerView.Adapter<LaundryRecyclerAdapter.ViewHolder> {

    Context context;
    List<String> laundries;


    public LaundryRecyclerAdapter(Context context, List<String> laundries) {
        this.context = context;
        this.laundries = laundries;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.laundry_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String[] laundry = laundries.get(position).split(",");
        holder.status.setText(laundry[2]);
        holder.jarak.setText(laundry[1]);
        holder.nama.setText(laundry[0]);
        if(laundry[2].contentEquals("Selesai"))holder.status.setTextColor(Color.GREEN);
        else if(laundry[2].contentEquals("Menunggu"))holder.status.setTextColor(Color.LTGRAY);
        else if(laundry[2].contentEquals("Diproses"))holder.status.setTextColor(Color.DKGRAY);
        else if(laundry[2].contentEquals("Batal"))holder.status.setTextColor(Color.RED);
        holder.view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, TrackLaundry.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("nama", laundry[0]);
                intent.putExtra("jarak", laundry[1]);
                intent.putExtra("status", laundry[2]);
                intent.putExtra("mode", laundry[3]);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return laundries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.nama)
        TextView nama;
        @BindView(R.id.jarak)
        TextView jarak;
        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.view)
        CardView view;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
