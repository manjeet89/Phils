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
import com.example.phils.ResponseModels.ResponseModelReciverComplete;

import java.util.List;

public class ReciverCompleteAdapterClass extends RecyclerView.Adapter<ReciverCompleteAdapterClass.MyViewHolder> {

    private ReciverCompleteAdapterClass.RecycleViewClickListener listener;
    private List<ResponseModelReciverComplete> data;


    public ReciverCompleteAdapterClass(RecycleViewClickListener listener, List<ResponseModelReciverComplete> data) {
        this.listener = listener;
        this.data = data;
    }

    public void setFilteredList(List<ResponseModelReciverComplete> filteredList)
    {
        this.data = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_requisition_reciver_complete,parent,false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.reciversn.setText(data.get(position).getSn());
        holder.reciverIdandsomething.setText(data.get(position).getReq_id()+ "#"+data.get(position).getAssign_id());
        holder.reciveremyId.setText(data.get(position).getUser_full_name() +" - "+data.get(position).getUser_employee_id());
        holder.reciverjobnumber.setText(data.get(position).getJob_number());
        holder.recivercategory.setText(data.get(position).getStock_category_name());
        holder.recivertype.setText(data.get(position).getStock_type_name());
        holder.reciversize.setText(data.get(position).getStock_size_name());
        holder.reciverquantity.setText(data.get(position).getAssign_quantity());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView reciversn,reciverIdandsomething,reciveremyId,reciverjobnumber,recivercategory,recivertype,
                reciversize,reciverquantity;
        Button id;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            reciversn = itemView.findViewById(R.id.reciversn);
            reciverIdandsomething = itemView.findViewById(R.id.reciverIdandsomething);
            reciveremyId = itemView.findViewById(R.id.reciveremyId);
            reciverjobnumber = itemView.findViewById(R.id.reciverjobnumber);
            recivercategory = itemView.findViewById(R.id.recivercategory);
            recivertype = itemView.findViewById(R.id.recivertype);
            reciversize = itemView.findViewById(R.id.reciversize);
            reciverquantity = itemView.findViewById(R.id.reciverquantity);
            id = itemView.findViewById(R.id.id);
            id.setOnClickListener(this);
            id.setTextColor(Color.parseColor("#1ca6eb"));



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
