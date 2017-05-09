package id.qq.quickqleen;

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

public class MyOrderRecyclerAdapter extends RecyclerView.Adapter<MyOrderRecyclerAdapter.ViewHolder> {

    Context context;
    List<String> laundries;

    public MyOrderRecyclerAdapter(Context context, List<String> laundries) {
        this.context = context;
        this.laundries = laundries;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myorder_item, parent, false); //layout
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String[] laundry = laundries.get(position).split(",");
        if(laundry[1].contentEquals("Selesai"))holder.status.setTextColor(Color.GREEN);
        else if(laundry[1].contentEquals("Menunggu"))holder.status.setTextColor(Color.LTGRAY);
        else if(laundry[1].contentEquals("Diproses"))holder.status.setTextColor(Color.DKGRAY);
        else if(laundry[1].contentEquals("Batal"))holder.status.setTextColor(Color.RED);
        holder.status.setText(laundry[1]);
        holder.nama.setText(laundry[0]);
        holder.view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, History.class); //routingnya ke detaillaundry
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("nama", laundry[0]);
                intent.putExtra("status", laundry[1]);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return laundries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //inisialisasi pendefinisian view
        @BindView(R.id.view2)
        CardView view;
        @BindView(R.id.nama)
        TextView nama;
        @BindView(R.id.status)
        TextView status;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
