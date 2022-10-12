package com.example.phils.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelStockCategory;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class StockCategoryAdapterClass extends RecyclerView.Adapter<StockCategoryAdapterClass.MyViewHolder> {

    private StockCategoryAdapterClass.RecycleViewClickListener listener;
    private List<ResponseModelStockCategory> data;

    public StockCategoryAdapterClass(RecycleViewClickListener listener, List<ResponseModelStockCategory> data) {
        this.listener = listener;
        this.data = data;
    }

    public void setFilteredList(List<ResponseModelStockCategory> filteredList)
    {
        this.data = filteredList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_stock_category_activity,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.sn.setText(data.get(position).getSn());
        holder.category.setText(data.get(position).getStock_category_name());
        holder.cat_group.setText(data.get(position).getEmp_type_name());
        holder.status.setText(data.get(position).getStock_category_status());
        if(data.get(position).getStock_category_status().equals("Disable"))
            holder.status.setTextColor(Color.parseColor("#ed0e1d"));
        else
            holder.status.setTextColor(Color.parseColor("#0eed3e"));

        holder.refrance.setText(data.get(position).getStock_category_id());

    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView sn,category,cat_group,status,refrance;
        Button btn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sn = itemView.findViewById(R.id.sn);
            category = itemView.findViewById(R.id.category);
            cat_group = itemView.findViewById(R.id.cat_group);
            status = itemView.findViewById(R.id.status);
            btn = itemView.findViewById(R.id.id);
            refrance = itemView.findViewById(R.id.refrance);
            btn.setOnClickListener(this);
            btn.setTextColor(Color.parseColor("#1ca6eb"));
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