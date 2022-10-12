package com.example.phils.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelStockUOM;

import java.util.List;

public class StockUOMAdapterClass extends RecyclerView.Adapter<StockUOMAdapterClass.MyViewHolder> {

    private StockUOMAdapterClass.RecycleViewClickListener listener;
    List<ResponseModelStockUOM> data;

    public StockUOMAdapterClass(RecycleViewClickListener listener, List<ResponseModelStockUOM> data) {
        this.listener = listener;
        this.data = data;
    }

    public void setFilteredList(List<ResponseModelStockUOM> filteredList)
    {
        this.data = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_stock_uom_activity,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.uom_id.setText(data.get(position).getSn());
        holder.uom_name.setText(data.get(position).getUom_name());
        holder.uom_status.setText(data.get(position).getUom_status());
        if(data.get(position).getUom_status().equals("Disable"))
            holder.uom_status.setTextColor(Color.parseColor("#ed0e1d"));
        else
            holder.uom_status.setTextColor(Color.parseColor("#0eed3e"));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView uom_id,uom_name,uom_status;
        Button btn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            uom_id = itemView.findViewById(R.id.uom_id);
            uom_name = itemView.findViewById(R.id.uom_name);
            uom_status = itemView.findViewById(R.id.uom_status);
            btn = itemView.findViewById(R.id.id);
            btn.setOnClickListener(this);
            btn.setTextColor(Color.parseColor("#1ca6eb"));

        }

        @Override
        public void onClick(View view) {
            listener.onClick(view,getAdapterPosition());

        }
    }

    public interface RecycleViewClickListener{
        void onClick(View v,int position);
    }
}