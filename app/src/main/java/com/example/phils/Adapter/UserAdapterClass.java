package com.example.phils.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelUser;

import java.util.List;

public class UserAdapterClass extends RecyclerView.Adapter<UserAdapterClass.MyViewHolder>{

      private UserAdapterClass.RecycleViewClickListener listener;
      List<ResponseModelUser> data;

    public UserAdapterClass(RecycleViewClickListener listener, List<ResponseModelUser> data) {
        this.listener = listener;
        this.data = data;
    }

    public void setFilteredList(List<ResponseModelUser> filteredList)
    {
        this.data = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_user_activity,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.user_id.setText(data.get(position).getSn());
        holder.user_full_name.setText(data.get(position).getUser_full_name());
        holder.emp_type_name.setText(data.get(position).getEmp_type_name());
        holder.user_name.setText(data.get(position).getUser_name());
        holder.user_phone_number.setText(data.get(position).getUser_phone_number());
        holder.user_otp.setText(data.get(position).getUser_otp());
        holder.employee_type.setText(data.get(position).getEmployee_type());
        holder.user_status.setText(data.get(position).getUser_status());
        if(data.get(position).getUser_status().equals("Disable"))
        holder.user_status.setTextColor(Color.parseColor("#ed0e1d"));
        else
        holder.user_status.setTextColor(Color.parseColor("#0eed3e"));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView user_id,user_full_name,emp_type_name,user_name,user_phone_number,user_otp,employee_type,user_status;

        Button button;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            user_id = itemView.findViewById(R.id.user_id);
            user_full_name = itemView.findViewById(R.id.user_full_name);
            emp_type_name = itemView.findViewById(R.id.emp_type_name);
            user_name = itemView.findViewById(R.id.user_name);
            user_phone_number = itemView.findViewById(R.id.user_phone_number);
            user_otp = itemView.findViewById(R.id.user_otp);
            employee_type = itemView.findViewById(R.id.employee_type);
            user_status = itemView.findViewById(R.id.user_status);
            button = itemView.findViewById(R.id.id);
            button.setOnClickListener(this);
            button.setTextColor(Color.parseColor("#1ca6eb"));

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









//
//
//public class EventAdapters extends RecyclerView.Adapter<EventAdapters.ViewMyHolder> {
//
//    ArrayList<EventModels> list;
//    private RecycleViewClickListener listener;
//
//    public EventAdapters(ArrayList<EventModels> list, RecycleViewClickListener listener) {
//        this.list = list;
//        this.listener = listener;
//    }
//
//    @NonNull
//    @Override
//    public ViewMyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list,parent,false);
//        return new ViewMyHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewMyHolder holder, int position) {
//
//        EventModels models=list.get(position);
//
//        holder.names.setText(models.getName());
//        holder.ivalue.setText(list.get(position).getInitValue());
//        holder.fvalue.setText(list.get(position).getFinalValue());
//
//        holder.progressBar.setProgress(Integer.parseInt(list.get(position).getInitValue()));
//
//        holder.progressBar.setMax(Integer.parseInt(list.get(position).finalValue));
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public class ViewMyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//        TextView names, ivalue, fvalue;
//        ProgressBar progressBar;
//
//        public ViewMyHolder(@NonNull View itemView) {
//            super(itemView);
//
//            names = itemView.findViewById(R.id.itemName);
//            ivalue = itemView.findViewById(R.id.itemIvalue);
//            fvalue = itemView.findViewById(R.id.itemfvalue);
//            progressBar = itemView.findViewById(R.id.progressBar);
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View view) {
//            listener.onClick(view,getAdapterPosition());
//        }
//    }
//
//    public interface RecycleViewClickListener{
//        void onClick(View v,int position);
//    }
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//// in main Activity
//
//public class MainActivity extends AppCompatActivity {
//
//    RecyclerView recyclerView;
//    ImageView addEventBtn;
//    ArrayList<EventModels> list;
//    private EventAdapters.RecycleViewClickListener listener;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        recyclerView=findViewById(R.id.recycleView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//
//        recycleClickLister();
//
//        addEventBtn=findViewById(R.id.addEvent);
//
//        list=new ArrayList<>();
//
//        addEventBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this,AddNewTask.class));
//                finish();
//            }
//        });
//
//        EventDatabase database=new EventDatabase(this);
//        Cursor cursor=database.fetchTask();
//
//        if (cursor!=null){
//            while(cursor.moveToNext()){
//                EventModels models=new EventModels(cursor.getString(1),
//                        cursor.getString(2),
//                        cursor.getString(3));
//
//                list.add(models);
//            }
//        }else {
//            Toast.makeText(this, "empty", Toast.LENGTH_SHORT).show();
//        }
//        EventAdapters adapters=new EventAdapters(list,listener);
//        recyclerView.setAdapter(adapters);
//    }
//
//    private void recycleClickLister() {
//
//        listener=new EventAdapters.RecycleViewClickListener() {
//            @Override
//            public void onClick(View v, int position) {
//                String kk=list.get(position).getName();
//
//
//                // your code
//
//                Toast.makeText(MainActivity.this, kk, Toast.LENGTH_SHORT).show();
//            }
//        };
//    }
//
//
//}
