package com.example.phils.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelAssignRequisition;

import java.util.List;

public class AssignRequisitionAdapterClass  extends  RecyclerView.Adapter<AssignRequisitionAdapterClass.MyViewHolder>{

    private  AssignRequisitionAdapterClass.RecycleViewClickListener listener;
    private AssignRequisitionAdapterClass.ItemClickListener  itemClickListener;
    private List<ResponseModelAssignRequisition> data;
    int selectPosition = -1;

    public AssignRequisitionAdapterClass(RecycleViewClickListener listener, ItemClickListener itemClickListener, List<ResponseModelAssignRequisition> data) {
        this.listener = listener;
        this.itemClickListener = itemClickListener;
        this.data = data;
    }

//    public AssignRequisitionAdapterClass(RecycleViewClickListener listener, List<ResponseModelAssignRequisition> data) {
//        this.listener = listener;
//        this.data = data;
//    }

    public void setFilteredList(List<ResponseModelAssignRequisition> filteredList)
    {
        this.data = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_assign_stock_requisition,parent,false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
       // holder.stockId.setText(data.get(position).getStock_id());
        holder.stockId.setChecked(position == selectPosition);

       holder.stockId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if(b)
               {
                   selectPosition = holder.getAdapterPosition();
                   itemClickListener.onClick(holder.stockId.getText().toString());
               }
           }
       });
        holder.assignbatch.setText(data.get(position).getStock_batch_number());
        holder.assigncategory.setText(data.get(position).getStock_category_name());
        holder.assigntype.setText(data.get(position).getStock_type_name());
        holder.assignsize.setText(data.get(position).getStock_size_name());

        holder.assignmaxalot.setText(data.get(position).getStock_lot());
        holder.assignavailable.setText(data.get(position).getAvailableStock());
        holder.assignstock.setText(data.get(position).getStock_quantity());
        holder.assignmake.setText(data.get(position).getMake_name());
        holder.assignuom.setText(data.get(position).getUom_name());
    }

    @Override public long getItemId(int position)
    {
        // pass position
        return position;
    }
    @Override public int getItemViewType(int position)
    {
        // pass position
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView assignbatch,assigncategory,assigntype,assignsize,assignmaxalot,assignavailable,assignstock,assignmake,assignuom;
        RadioButton stockId;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            assignbatch = itemView.findViewById(R.id.assignbatch);
            assigncategory = itemView.findViewById(R.id.assigncategory);
            assigntype = itemView.findViewById(R.id.assigntype);
            assignsize = itemView.findViewById(R.id.assignsize);
            assignmaxalot = itemView.findViewById(R.id.assignmaxalot);
            assignavailable = itemView.findViewById(R.id.assignavailable);
            assignstock = itemView.findViewById(R.id.assignstock);
            assignmake = itemView.findViewById(R.id.assignmake);
            assignuom = itemView.findViewById(R.id.assignuom);
            stockId = itemView.findViewById(R.id.stockId);
            stockId.setOnClickListener(this);


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

    public interface ItemClickListener {
        // Create method
        void onClick(String s);
    }
}
