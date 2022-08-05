package com.example.phils.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelStockMake;

import java.util.List;

public class StockMakeAdapterClass extends RecyclerView.Adapter<StockMakeAdapterClass.MyViewHolder>{
    private Context context;
    List<ResponseModelStockMake> data;

    public StockMakeAdapterClass(Context context, List<ResponseModelStockMake> data) {
        this.context = context;
        this.data = data;
    }
    public void setFilteredList(List<ResponseModelStockMake> filteredList)
    {
        this.data = filteredList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_stock_make_activity,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.make_id.setText(data.get(position).getMake_id());
        holder.make_name.setText(data.get(position).getMake_name());
        holder.make_status.setText(data.get(position).getMake_status());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView make_id,make_name,make_status;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            make_id = itemView.findViewById(R.id.make_id);
            make_name = itemView.findViewById(R.id.make_name);
            make_status = itemView.findViewById(R.id.make_status);
        }
    }
}
