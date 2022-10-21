package com.example.phils.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phils.MultipleItemSelectInSpinner.AssignUserJobWelderGrienderData;
import com.example.phils.R;

import java.util.ArrayList;

public class AssignUserMultiWelderGrienderAdapter  extends RecyclerView.Adapter<AssignUserMultiWelderGrienderAdapter.MultiViewHolder>{

    private Context context;
    private ArrayList<AssignUserJobWelderGrienderData> assignUserJobWelderGrienderData;

    public AssignUserMultiWelderGrienderAdapter(Context context, ArrayList<AssignUserJobWelderGrienderData> assignUserJobWelderGrienderData) {
        this.context = context;
        this.assignUserJobWelderGrienderData = assignUserJobWelderGrienderData;
    }

    public void setAssignUserJobWelderGrienderData(ArrayList<AssignUserJobWelderGrienderData> assignUserJobWelderGrienderData) {
        this.assignUserJobWelderGrienderData = new ArrayList<>();
        this.assignUserJobWelderGrienderData = assignUserJobWelderGrienderData;
        notifyDataSetChanged();
    }

    public void setFilteredList(ArrayList<AssignUserJobWelderGrienderData> filteredList)
    {
        this.assignUserJobWelderGrienderData = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_employee,parent,false);
        return new MultiViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MultiViewHolder holder, int position) {
        holder.bind(assignUserJobWelderGrienderData.get(position));
        holder.textView.setTextColor(Color.parseColor("#000000"));
    }

    @Override
    public int getItemCount() {
        return assignUserJobWelderGrienderData.size();
    }


    class MultiViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private ImageView imageView;

        public MultiViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
            // super(itemView);
        }
        void bind(final AssignUserJobWelderGrienderData assignUserJobWelderGrienderData) {
            imageView.setVisibility(assignUserJobWelderGrienderData.isChecked() ? View.VISIBLE : View.GONE);
            textView.setText(assignUserJobWelderGrienderData.getName());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    assignUserJobWelderGrienderData.setChecked(!assignUserJobWelderGrienderData.isChecked());
                    imageView.setVisibility(assignUserJobWelderGrienderData.isChecked() ? View.VISIBLE : View.GONE);
                }
            });
        }
    }

    public ArrayList<AssignUserJobWelderGrienderData> getAll() {
        return assignUserJobWelderGrienderData;
    }
    public ArrayList<AssignUserJobWelderGrienderData> getSelected() {
        ArrayList<AssignUserJobWelderGrienderData> selected = new ArrayList<>();
        for (int i = 0; i < assignUserJobWelderGrienderData.size(); i++) {
            if (assignUserJobWelderGrienderData.get(i).isChecked()) {
                selected.add(assignUserJobWelderGrienderData.get(i));
            }
        }
        return selected;
    }
    public  void filterList(ArrayList<AssignUserJobWelderGrienderData> filterlist){
        assignUserJobWelderGrienderData = filterlist;
        notifyDataSetChanged();
    }
}
