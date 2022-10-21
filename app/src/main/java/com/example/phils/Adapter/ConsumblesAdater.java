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

import com.example.phils.MultipleItemSelectInSpinner.ConsumblesiItemData;
import com.example.phils.R;

import java.util.ArrayList;

public class ConsumblesAdater extends RecyclerView.Adapter<ConsumblesAdater.MultiViewHolder>{
    private Context context;
    private ArrayList<ConsumblesiItemData> consumblesiItemData;

    public ConsumblesAdater(Context context, ArrayList<ConsumblesiItemData> consumblesiItemData) {
        this.context = context;
        this.consumblesiItemData = consumblesiItemData;
    }

    public void setConsumblesiItemData(ArrayList<ConsumblesiItemData> consumblesiItemData) {
        this.consumblesiItemData = new ArrayList<>();
        this.consumblesiItemData = consumblesiItemData;
        notifyDataSetChanged();
    }

    public void setFilteredList(ArrayList<ConsumblesiItemData> filteredList)
    {
        this.consumblesiItemData = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_employee,parent,false);
        return new MultiViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MultiViewHolder holder, int position) {
        holder.bind(consumblesiItemData.get(position));
        holder.textView.setTextColor(Color.parseColor("#000000"));

    }

    @Override
    public int getItemCount() {
        return consumblesiItemData.size();
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
        void bind(final ConsumblesiItemData consumblesiItemData) {
            imageView.setVisibility(consumblesiItemData.isChecked() ? View.VISIBLE : View.GONE);
            textView.setText(consumblesiItemData.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    consumblesiItemData.setChecked(!consumblesiItemData.isChecked());
                    imageView.setVisibility(consumblesiItemData.isChecked() ? View.VISIBLE : View.GONE);
                }
            });
        }
    }
    public ArrayList<ConsumblesiItemData> getAll() {
        return consumblesiItemData;
    }

    public ArrayList<ConsumblesiItemData> getSelected() {
        ArrayList<ConsumblesiItemData> selected = new ArrayList<>();
        for (int i = 0; i < consumblesiItemData.size(); i++) {
            if (consumblesiItemData.get(i).isChecked()) {
                selected.add(consumblesiItemData.get(i));
            }
        }
        return selected;
    }
    public  void filterList(ArrayList<ConsumblesiItemData> filterlist){
        consumblesiItemData = filterlist;
        notifyDataSetChanged();
    }
}