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
import com.example.phils.ResponseModels.ResponseModelStockMake;

import java.util.List;

public class StockMakeAdapterClass extends RecyclerView.Adapter<StockMakeAdapterClass.MyViewHolder>{

    private StockMakeAdapterClass.RecycleViewClickListener listener;
    List<ResponseModelStockMake> data;

    public StockMakeAdapterClass(RecycleViewClickListener listener, List<ResponseModelStockMake> data) {
        this.listener = listener;
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
        holder.make_id.setText(data.get(position).getSn());
        holder.make_name.setText(data.get(position).getMake_name());
        holder.make_status.setText(data.get(position).getMake_status());
        if(data.get(position).getMake_status().equals("Disable"))
            holder.make_status.setTextColor(Color.parseColor("#ed0e1d"));
        else
            holder.make_status.setTextColor(Color.parseColor("#0eed3e"));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView make_id,make_name,make_status;
        Button btn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            make_id = itemView.findViewById(R.id.make_id);
            make_name = itemView.findViewById(R.id.make_name);
            make_status = itemView.findViewById(R.id.make_status);
            btn = itemView.findViewById(R.id.id);
            btn.setOnClickListener(this);
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
