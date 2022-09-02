package com.example.phils.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelStockList;

import java.util.List;

public class StockListAdapterClass extends RecyclerView.Adapter<StockListAdapterClass.MyViewHolder>{


    private StockListAdapterClass.RecycleViewClickListener listener;
    List<ResponseModelStockList> data;

    public StockListAdapterClass(RecycleViewClickListener listener, List<ResponseModelStockList> data) {
        this.listener = listener;
        this.data = data;
    }

    public void setFilteredList(List<ResponseModelStockList> filteredList)
    {
        this.data = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_stock_list_activity,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.list_id.setText(data.get(position).getSn());
        holder.list_category_name.setText(data.get(position).getStock_category_name());
        holder.list_type_name.setText(data.get(position).getStock_type_name());
        holder.list_size_name.setText(data.get(position).getStock_size_name());
        holder.list_batch_number.setText(data.get(position).getStock_batch_number());
        holder.list_make_name.setText(data.get(position).getMake_name());
        holder.list_uom_name.setText(data.get(position).getUom_name());
        holder.list_safety.setText(data.get(position).getSafety_stock());
        holder.list_quantity.setText(data.get(position).getStock_quantity());
        holder.list_price.setText(data.get(position).getStock_price());
        holder.list_status.setText(data.get(position).getStock_status());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView list_id,list_category_name,list_type_name,list_size_name,
                list_batch_number,list_make_name,list_uom_name,list_safety,
                list_quantity,list_price,list_status;
        Button btn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            list_id = itemView.findViewById(R.id.list_id);
            list_category_name = itemView.findViewById(R.id.list_category_name);
            list_type_name = itemView.findViewById(R.id.list_type_name);
            list_size_name = itemView.findViewById(R.id.list_size_name);
            list_batch_number = itemView.findViewById(R.id.list_batch_number);
            list_make_name = itemView.findViewById(R.id.list_make_name);
            list_uom_name = itemView.findViewById(R.id.list_uom_name);
            list_safety = itemView.findViewById(R.id.list_safety);
            list_quantity = itemView.findViewById(R.id.list_quantity);
            list_price = itemView.findViewById(R.id.list_price);
            list_status = itemView.findViewById(R.id.list_status);
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
