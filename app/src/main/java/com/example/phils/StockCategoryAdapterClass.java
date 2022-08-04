package com.example.phils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StockCategoryAdapterClass extends RecyclerView.Adapter<StockCategoryAdapterClass.MyViewHolder> {

    private Context context;
    private List<ResponseModelStockCategory> data;

    public void setFilteredList(List<ResponseModelStockCategory> filteredList)
    {
        this.data = filteredList;
        notifyDataSetChanged();
    }

    public StockCategoryAdapterClass(Context context, List<ResponseModelStockCategory> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_stock_category_activity,parent,false);
       return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.sn.setText(data.get(position).getStock_category_id());
        holder.category.setText(data.get(position).getStock_category_name());
        holder.cat_group.setText(data.get(position).getEmp_type_name());
        holder.status.setText(data.get(position).getStock_category_status());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView sn,category,cat_group,status;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sn = itemView.findViewById(R.id.sn);
            category = itemView.findViewById(R.id.category);
            cat_group = itemView.findViewById(R.id.cat_group);
            status = itemView.findViewById(R.id.status);
        }
    }
}