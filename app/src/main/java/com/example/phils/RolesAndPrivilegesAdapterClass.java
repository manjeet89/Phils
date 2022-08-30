package com.example.phils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phils.Adapter.JobSizeAdapterClass;
import com.example.phils.ResponseModels.ResponseModelJobSize;

import java.util.List;

public class RolesAndPrivilegesAdapterClass extends RecyclerView.Adapter<RolesAndPrivilegesAdapterClass.MyViewHolder>{

    private Context context;
    private List<ResponseMdelRoleAndPrivileged> data;

    public RolesAndPrivilegesAdapterClass(Context context, List<ResponseMdelRoleAndPrivileged> data) {
        this.context = context;
        this.data = data;
    }

    public void setFilteredList(List<ResponseMdelRoleAndPrivileged> filteredList)
    {
        this.data = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_roles_and_privileges,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.sn.setText(data.get(position).getSn());
        holder.RoleempName.setText(data.get(position).getEmp_type_name());
        holder.created.setText(data.get(position).getEmp_type_created_on());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView sn,RoleempName,created;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sn = itemView.findViewById(R.id.sn);
            RoleempName = itemView.findViewById(R.id.RoleempName);
            created = itemView.findViewById(R.id.created);
        }
    }
}
