package com.example.phils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phils.Adapter.StockListAdapterClass;
import com.example.phils.ResponseModels.ResponseModelStockList;

import java.util.List;

public class UserAdapterClass extends RecyclerView.Adapter<UserAdapterClass.MyViewHolder>{

        private Context context;
        List<ResponseModelUser> data;

    public UserAdapterClass(Context context, List<ResponseModelUser> data) {
        this.context = context;
        this.data = data;
    }
    public void setFilteredList(List<ResponseModelUser> filteredList)
    {
        this.data = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_user_activity,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.user_id.setText(data.get(position).getUser_id());
        holder.user_full_name.setText(data.get(position).getUser_full_name());
        holder.emp_type_name.setText(data.get(position).getEmp_type_name());
        holder.user_name.setText(data.get(position).getUser_name());
        holder.user_phone_number.setText(data.get(position).getUser_phone_number());
        holder.user_otp.setText(data.get(position).getUser_otp());
        holder.employee_type.setText(data.get(position).getEmployee_type());
        holder.user_status.setText(data.get(position).getUser_status());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView user_id,user_full_name,emp_type_name,user_name,user_phone_number,user_otp,employee_type,user_status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            user_id = itemView.findViewById(R.id.user_id);
            user_full_name = itemView.findViewById(R.id.user_full_name);
            emp_type_name = itemView.findViewById(R.id.emp_type_name);
            user_name = itemView.findViewById(R.id.user_name);
            user_phone_number = itemView.findViewById(R.id.user_phone_number);
            user_otp = itemView.findViewById(R.id.user_otp);
            employee_type = itemView.findViewById(R.id.employee_type);
            user_status = itemView.findViewById(R.id.user_status);
        }
    }
}
