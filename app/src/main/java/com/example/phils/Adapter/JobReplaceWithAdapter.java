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

import com.example.phils.MultipleItemSelectInSpinner.JobReplaceWithData;
import com.example.phils.R;

import java.util.ArrayList;

public class JobReplaceWithAdapter extends  RecyclerView.Adapter<JobReplaceWithAdapter.MultiViewHolder>{

    private Context context;
    private ArrayList<JobReplaceWithData> jobReplaceWithData;

    public JobReplaceWithAdapter(Context context, ArrayList<JobReplaceWithData> jobReplaceWithData) {
        this.context = context;
        this.jobReplaceWithData = jobReplaceWithData;
    }

    public void setJobReplaceWithData(ArrayList<JobReplaceWithData> jobReplaceWithData) {
        this.jobReplaceWithData = new ArrayList<>();
        this.jobReplaceWithData = jobReplaceWithData;
        notifyDataSetChanged();
    }

    public void setFilteredList(ArrayList<JobReplaceWithData> filteredList)
    {
        this.jobReplaceWithData = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_employee,parent,false);
        return new MultiViewHolder(view);     }

    @Override
    public void onBindViewHolder(@NonNull MultiViewHolder holder, int position) {
        holder.bind(jobReplaceWithData.get(position));
        holder.textView.setTextColor(Color.parseColor("#000000"));
    }

    @Override
    public int getItemCount() {
        return jobReplaceWithData.size();
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
        void bind(final JobReplaceWithData jobReplaceWithData) {
            imageView.setVisibility(jobReplaceWithData.isChecked() ? View.VISIBLE : View.GONE);
            textView.setText(jobReplaceWithData.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    jobReplaceWithData.setChecked(!jobReplaceWithData.isChecked());
                    imageView.setVisibility(jobReplaceWithData.isChecked() ? View.VISIBLE : View.GONE);
                }
            });
        }
    }
    public ArrayList<JobReplaceWithData> getAll() {
        return jobReplaceWithData;
    }

    public ArrayList<JobReplaceWithData> getSelected() {
        ArrayList<JobReplaceWithData> selected = new ArrayList<>();
        for (int i = 0; i < jobReplaceWithData.size(); i++) {
            if (jobReplaceWithData.get(i).isChecked()) {
                selected.add(jobReplaceWithData.get(i));
            }
        }
        return selected;
    }
    public  void filterList(ArrayList<JobReplaceWithData> filterlist){
        jobReplaceWithData = filterlist;
        notifyDataSetChanged();
    }
}