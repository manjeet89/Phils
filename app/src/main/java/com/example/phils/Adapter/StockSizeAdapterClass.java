package com.example.phils.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelStockSize;

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
        holder.stock_size_id.setText(data.get(position).getStock_size_id());
        holder.stock_category_name.setText(data.get(position).getStock_category_name());
        holder.stock_type_name.setText(data.get(position).getStock_type_name());
        holder.stock_size_name.setText(data.get(position).getStock_size_name());
        holder.stock_size_status.setText(data.get(position).getStock_size_status());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView stock_size_id,stock_category_name,stock_type_name,stock_size_name,stock_size_status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            stock_size_id = itemView.findViewById(R.id.snsize);
            stock_category_name = itemView.findViewById(R.id.categorysize);
            stock_type_name = itemView.findViewById(R.id.typesize);
            stock_size_name = itemView.findViewById(R.id.size);
            stock_size_status = itemView.findViewById(R.id.statussize);
        }
    }

}
