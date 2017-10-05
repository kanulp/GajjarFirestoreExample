package kanu_lp.com.gajjarfirestoreexample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Kanu on 10/5/2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private List<FavModel> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView fav;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.fav = (TextView) itemView.findViewById(R.id.fav);
        }
    }

    public CustomAdapter(List<FavModel> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_list, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        holder.name.setText(dataSet.get(listPosition).getUser());
        holder.fav.setText(dataSet.get(listPosition).getFavsinger());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
