package com.example.phils.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelStockType;

import java.util.List;

public class StockTypeAdapterClass extends RecyclerView.Adapter<StockTypeAdapterClass.MyViewHolder>{
    private Context context;
    private List<ResponseModelStockType> data;

    public StockTypeAdapterClass(Context context, List<ResponseModelStockType> data) {
        this.context = context;
        this.data = data;
    }

    public void setFilteredList(List<ResponseModelStockType> filteredList)
    {
        this.data = filteredList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_stock_type_activity,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.sn.setText(data.get(position).getStock_type_id());
        holder.category.setText(data.get(position).getStock_category_id());
        holder.type.setText(data.get(position).getStock_type_name());
        holder.status.setText(data.get(position).getStock_type_status());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView sn,category,type,status;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sn = itemView.findViewById(R.id.sn);
            category = itemView.findViewById(R.id.category);
            type = itemView.findViewById(R.id.type);
            status = itemView.findViewById(R.id.status);

        }
    }

}
