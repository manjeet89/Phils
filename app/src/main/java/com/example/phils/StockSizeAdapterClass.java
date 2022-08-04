package com.example.phils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StockSizeAdapterClass extends RecyclerView.Adapter<StockSizeAdapterClass.MyViewHolder> {
    private Context context;
    List<ResponseModelStockSize> data;

    public StockSizeAdapterClass(Context context, List<ResponseModelStockSize> data) {
        this.context = context;
        this.data = data;
    }

    public void setFilteredList(List<ResponseModelStockSize> filteredList)
    {
        this.data = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_stock_size_activity,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.sn.setText(data.get(position).getStock_size_id());
        holder.category.setText(data.get(position).getStock_category_name());
        holder.type.setText(data.get(position).getStock_type_name());
        holder.size.setText(data.get(position).getStock_size_name());
        holder.status.setText(data.get(position).getStock_size_status());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView sn,category,type,size,status;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sn = itemView.findViewById(R.id.sn);
            category = itemView.findViewById(R.id.category);
            type = itemView.findViewById(R.id.type);
            size = itemView.findViewById(R.id.size);
            status = itemView.findViewById(R.id.status);

        }
    }
}
