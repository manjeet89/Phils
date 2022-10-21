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

import com.example.phils.MultipleItemSelectInSpinner.JobIncompleteData;
import com.example.phils.R;

import java.util.ArrayList;

public class MultiJobInCompleteAdapter extends RecyclerView.Adapter<MultiJobInCompleteAdapter.MultiViewHolder> {

    private Context context;
    private ArrayList<JobIncompleteData> jobIncompleteData;

    public MultiJobInCompleteAdapter(Context context, ArrayList<JobIncompleteData> jobIncompleteData) {
        this.context = context;
        this.jobIncompleteData = jobIncompleteData;
    }

    public void setJobIncompleteData(ArrayList<JobIncompleteData> jobIncompleteData) {
        this.jobIncompleteData = new ArrayList<>();
        this.jobIncompleteData = jobIncompleteData;
        notifyDataSetChanged();
    }
    public void setFilteredList(ArrayList<JobIncompleteData> filteredList)
    {
        this.jobIncompleteData = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_employee,parent,false);
        return new MultiViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MultiViewHolder holder, int position) {
        holder.bind(jobIncompleteData.get(position));
        holder.textView.setTextColor(Color.parseColor("#000000"));
    }

    @Override
    public int getItemCount() {
        return jobIncompleteData.size();
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
        void bind(final JobIncompleteData jobIncompleteData) {
            imageView.setVisibility(jobIncompleteData.isChecked() ? View.VISIBLE : View.GONE);
            textView.setText(jobIncompleteData.getName());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    jobIncompleteData.setChecked(!jobIncompleteData.isChecked());
                    imageView.setVisibility(jobIncompleteData.isChecked() ? View.VISIBLE : View.GONE);
                }
            });
        }
    }

    public ArrayList<JobIncompleteData> getAll() {
        return jobIncompleteData;
    }

    public ArrayList<JobIncompleteData> getSelected() {
        ArrayList<JobIncompleteData> selected = new ArrayList<>();
        for (int i = 0; i < jobIncompleteData.size(); i++) {
            if (jobIncompleteData.get(i).isChecked()) {
                selected.add(jobIncompleteData.get(i));
            }
        }
        return selected;
    }
    public  void filterList(ArrayList<JobIncompleteData> filterlist){
        jobIncompleteData = filterlist;
        notifyDataSetChanged();
    }

}
