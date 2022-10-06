package com.example.phils.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelStockSize;

import java.util.List;

public class StockSizeAdapterClass extends RecyclerView.Adapter<StockSizeAdapterClass.MyViewHolder> {

    private StockSizeAdapterClass.RecycleViewClickListener listener;
    List<ResponseModelStockSize> data;

    public StockSizeAdapterClass(RecycleViewClickListener listener, List<ResponseModelStockSize> data) {
        this.listener = listener;
        this.data = data;
    }

    //    public StockSizeAdapterClass(RecycleViewClickListener listener, List<ResponseModelStockSize> data) {
//        this.listener = listener;
//        this.data = data;
//    }

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
        holder.sn.setText(data.get(position).getSn());
        holder.stock_category_name.setText(data.get(position).getStock_category_name());
        holder.stock_type_name.setText(data.get(position).getStock_type_name());
        holder.stock_size_name.setText(data.get(position).getStock_size_name());
        holder.stock_size_status.setText(data.get(position).getStock_size_status());
        if(data.get(position).getStock_size_status().equals("Disable"))
            holder.stock_size_status.setTextColor(Color.parseColor("#ed0e1d"));
        else
            holder.stock_size_status.setTextColor(Color.parseColor("#0eed3e"));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView sn,stock_category_name,stock_type_name,stock_size_name,stock_size_status;
        Button btn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sn = itemView.findViewById(R.id.snsize);
            stock_category_name = itemView.findViewById(R.id.categorysize);
            stock_type_name = itemView.findViewById(R.id.typesize);
            stock_size_name = itemView.findViewById(R.id.size);
            btn = itemView.findViewById(R.id.id);
            stock_size_status = itemView.findViewById(R.id.statussize);
            btn.setOnClickListener(this);


        }
        @Override
        public void onClick(View view) {

            listener.onClick(view,getAdapterPosition());
            // Toast.makeText(context, user_id.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }
    public interface RecycleViewClickListener{
        void onClick(View v,int position);
    }

}