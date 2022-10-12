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
import com.example.phils.ResponseModels.ResponseModelRequisitionList;

import java.util.List;

public class RequisitionAdapterClass extends RecyclerView.Adapter<RequisitionAdapterClass.MyViewHolder> {

    private RequisitionAdapterClass.RecycleViewClickListener listener;
    private List<ResponseModelRequisitionList> data;

    public RequisitionAdapterClass(RecycleViewClickListener listener, List<ResponseModelRequisitionList> data) {
        this.listener = listener;
        this.data = data;
    }

    public void setFilteredList(List<ResponseModelRequisitionList> filteredList)
    {
        this.data = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_requsition_open_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.req_sn.setText(data.get(position).getSn());
        holder.req_id.setText(data.get(position).getReq_id());
        holder.req_requisition_from.setText(data.get(position).getUser_full_name()+" - " +data.get(position).getUser_employee_id());
        holder.req_requisition_for.setText(data.get(position).getReq_user_id());
        holder.req_job_number.setText(data.get(position).getJob_number());
        holder.req_seam_number.setText(data.get(position).getSeam_number());
        holder.req_category.setText(data.get(position).getStock_category_name());
        holder.req_type.setText(data.get(position).getStock_type_name());
        holder.req_size.setText(data.get(position).getStock_size_name());
        holder.req_quantity_assign.setText(data.get(position).getAssign_quantity());
        holder.req_quantity_required.setText(data.get(position).getReq_quantity());
        holder.req_approved_by.setText(data.get(position).getReq_manager_id());
        holder.manager_status.setText(data.get(position).getReq_manager_status());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView req_sn,req_id,req_requisition_from,req_requisition_for,req_job_number,req_seam_number,req_category,
                req_type,req_size,req_quantity_assign,req_quantity_required,req_approved_by,manager_status;
        Button editid;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            req_sn = itemView.findViewById(R.id.req_sn);
            req_id = itemView.findViewById(R.id.req_id);
            req_requisition_from = itemView.findViewById(R.id.req_requisition_from);
            req_requisition_for = itemView.findViewById(R.id.req_requisition_for);
            req_job_number = itemView.findViewById(R.id.req_job_number);
            req_seam_number = itemView.findViewById(R.id.req_seam_number);
            req_category = itemView.findViewById(R.id.req_category);
            req_type = itemView.findViewById(R.id.req_type);
            req_size = itemView.findViewById(R.id.req_size);
            req_quantity_assign = itemView.findViewById(R.id.req_quantity_assign);
            req_quantity_required = itemView.findViewById(R.id.req_quantity_required);
            req_approved_by = itemView.findViewById(R.id.req_approved_by);
            manager_status = itemView.findViewById(R.id.manager_status);
            editid = itemView.findViewById(R.id.editid);
            editid.setOnClickListener(this);
            editid.setTextColor(Color.parseColor("#1ca6eb"));

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
