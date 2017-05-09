package id.qq.quickqleen;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

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
        holder.jarak.setText(laundry[1]);
        holder.nama.setText(laundry[0]);
        holder.icon.setImageDrawable(new IconicsDrawable(context)
                .icon(FontAwesome.Icon.faw_map_marker)
                .color(ContextCompat.getColor(context, R.color.primary)));
        holder.view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailLaundry.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("nama", laundry[0]);
                intent.putExtra("jarak", laundry[1]);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return laundries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.view2)
        CardView view;
        @BindView(R.id.nama)
        TextView nama;
        @BindView(R.id.icon)
        ImageView icon;
        @BindView(R.id.jarak)
        TextView jarak;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
