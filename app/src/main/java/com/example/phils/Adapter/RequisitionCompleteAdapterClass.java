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
import com.example.phils.ResponseModels.ResponseModelRequisitionComplete;

import java.util.List;

public class RequisitionCompleteAdapterClass extends RecyclerView.Adapter<RequisitionCompleteAdapterClass.MyViewHolder> {

    private RequisitionCompleteAdapterClass.RecycleViewClickListener listener;
    private List<ResponseModelRequisitionComplete> data;

    public RequisitionCompleteAdapterClass(RecycleViewClickListener listener, List<ResponseModelRequisitionComplete> data) {
        this.listener = listener;
        this.data = data;
    }

    public void setFilteredList(List<ResponseModelRequisitionComplete> filteredList)
    {
        this.data = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_requisition_complete,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.snid.setText(data.get(position).getSn());
        holder.compId.setText(data.get(position).getReq_id());
        holder.reqfrom.setText(data.get(position).getUser_full_name() +" - "+data.get(position).getUser_employee_id());
        holder.comfor.setText(data.get(position).getReq_user_id_details());
        holder.jobn.setText(data.get(position).getJob_number());
        holder.seam.setText(data.get(position).getSeam_number());
        holder.cat.setText(data.get(position).getStock_category_name());
        holder.typ.setText(data.get(position).getStock_type_name());

        holder.siz.setText(data.get(position).getStock_size_name());
        holder.quantity.setText(data.get(position).getAssign_quantity());
        holder.reqqun.setText(data.get(position).getReq_quantity());
        holder.byap.setText(data.get(position).getReq_manager_id());
        if(data.get(position).getReq_manager_id().equals("Default")){

//            holder.req_approved_by.setTextColor(Color.parseColor("#151b26"));

        }else {
            holder.byap.setTextColor(Color.parseColor("#2118d6"));
        }
        holder.maanger.setText(data.get(position).getReq_manager_status());
        holder.comm.setText(data.get(position).getReq_manager_comment());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView snid,compId,reqfrom,comfor,jobn,seam,
                cat,typ,siz,quantity,reqqun,byap,maanger,comm;
        Button id;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            snid = itemView.findViewById(R.id.snid);
            compId = itemView.findViewById(R.id.compId);
            reqfrom = itemView.findViewById(R.id.reqfrom);
            comfor = itemView.findViewById(R.id.comfor);
            jobn = itemView.findViewById(R.id.jobn);
            seam = itemView.findViewById(R.id.seam);
            cat = itemView.findViewById(R.id.cat);
            typ = itemView.findViewById(R.id.typ);
            siz = itemView.findViewById(R.id.siz);

            quantity = itemView.findViewById(R.id.quantity);
            reqqun = itemView.findViewById(R.id.reqqun);
            byap = itemView.findViewById(R.id.byap);
            maanger = itemView.findViewById(R.id.maanger);
            comm = itemView.findViewById(R.id.comm);
            //id.setOnClickListener(this);


//            btn.setOnClickListener(this);
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
