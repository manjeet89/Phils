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

import com.example.phils.MultipleItemSelectInSpinner.JobReplaceWelderGrinderData;
import com.example.phils.R;

import java.util.ArrayList;

public class JobReplaceWelderGrinederAdapter extends RecyclerView.Adapter<JobReplaceWelderGrinederAdapter.MultiViewHolder>  {

    private Context context;
    private ArrayList<JobReplaceWelderGrinderData> jobReplaceWelderGrinderData;

    public JobReplaceWelderGrinederAdapter(Context context, ArrayList<JobReplaceWelderGrinderData> jobReplaceWelderGrinderData) {
        this.context = context;
        this.jobReplaceWelderGrinderData = jobReplaceWelderGrinderData;
    }

    public void setJobReplaceWelderGrinderData(ArrayList<JobReplaceWelderGrinderData> jobReplaceWelderGrinderData) {
        this.jobReplaceWelderGrinderData = new ArrayList<>();
        this.jobReplaceWelderGrinderData = jobReplaceWelderGrinderData;
        notifyDataSetChanged();
    }

    public void setFilteredList(ArrayList<JobReplaceWelderGrinderData> filteredList)
    {
        this.jobReplaceWelderGrinderData = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_employee,parent,false);
        return new MultiViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MultiViewHolder holder, int position) {
        holder.bind(jobReplaceWelderGrinderData.get(position));
        holder.textView.setTextColor(Color.parseColor("#000000"));
    }

    @Override
    public int getItemCount() {
        return jobReplaceWelderGrinderData.size();
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
        void bind(final JobReplaceWelderGrinderData jobReplaceWelderGrinderData) {
            imageView.setVisibility(jobReplaceWelderGrinderData.isChecked() ? View.VISIBLE : View.GONE);
            textView.setText(jobReplaceWelderGrinderData.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    jobReplaceWelderGrinderData.setChecked(!jobReplaceWelderGrinderData.isChecked());
                    imageView.setVisibility(jobReplaceWelderGrinderData.isChecked() ? View.VISIBLE : View.GONE);
                }
            });
        }
    }
    public ArrayList<JobReplaceWelderGrinderData> getAll() {
        return jobReplaceWelderGrinderData;
    }

    public ArrayList<JobReplaceWelderGrinderData> getSelected() {
        ArrayList<JobReplaceWelderGrinderData> selected = new ArrayList<>();
        for (int i = 0; i < jobReplaceWelderGrinderData.size(); i++) {
            if (jobReplaceWelderGrinderData.get(i).isChecked()) {
                selected.add(jobReplaceWelderGrinderData.get(i));
            }
        }
        return selected;
    }
    public  void filterList(ArrayList<JobReplaceWelderGrinderData> filterlist){
        jobReplaceWelderGrinderData = filterlist;
        notifyDataSetChanged();
    }
}