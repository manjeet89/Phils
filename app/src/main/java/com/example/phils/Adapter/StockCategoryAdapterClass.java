package com.example.phils.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.phils.Add_Stock_Category_Activity;
import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelStockCategory;
import com.example.phils.StockCategoryActivity;
import com.example.phils.StockUomActivity;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class StockCategoryAdapterClass extends RecyclerView.Adapter<StockCategoryAdapterClass.MyViewHolder> {

    private Context context;
    private List<ResponseModelStockCategory> data;

    public void setFilteredList(List<ResponseModelStockCategory> filteredList)
    {
        this.data = filteredList;
        notifyDataSetChanged();
    }

    public StockCategoryAdapterClass(Context context, List<ResponseModelStockCategory> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_stock_category_activity,parent,false);
       return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.sn.setText(data.get(position).getSn());
        holder.category.setText(data.get(position).getStock_category_name());
        holder.cat_group.setText(data.get(position).getEmp_type_name());
        holder.status.setText(data.get(position).getStock_category_status());
        holder.refrance.setText(data.get(position).getStock_category_id());
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   String f = holder.refrance.getText().toString();
//                Toast.makeText(context, f, Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent();
//                intent.putExtra("id",f);
            //    mm(f);



                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.cat_group.getContext()).
                        setContentHolder(new ViewHolder(R.layout.update_stock_category)).
                        setExpanded(true,1400).create();

                ArrayList arrayList=new ArrayList<>();
                ArrayList arrayList1=new ArrayList<>();

                // set value in array list
                arrayList.add("Others");
                arrayList.add("WELDER");
                arrayList.add("GRINDER");

                arrayList1.add("Enable");
                arrayList1.add("Disable");

                View myview = dialogPlus.getHolderView();
                TextView testView = myview.findViewById(R.id.testView);
                EditText category_insert = myview.findViewById(R.id.category_insert);
                TextView status_check = myview.findViewById(R.id.status_check);
                Button btn = myview.findViewById(R.id.insert_cat);

//
//                testView.setText(t);
                testView.setText(data.get(position).getEmp_type_name());
                category_insert.setText(data.get(position).getStock_category_name());
                status_check.setText(data.get(position).getStock_category_status());




                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        String emptype =testView.getText().toString();
                        String category = category_insert.getText().toString().toUpperCase(Locale.ROOT);
                        String status = status_check.getText().toString();
                        String id = holder.refrance.getText().toString();


                         if(TextUtils.isEmpty(category))
                        {
                            category_insert.setError("Please Enter your Category Name");
                            Toast.makeText(context, "Please Enter your Category Name", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        else {

                            if(emptype.equals("WELDER"))
                            {
                                emptype = "5";
                            }
                            else if(emptype.equals("GRINDER"))
                            {
                                emptype = "4";
                            }
                            else
                            {
                                emptype = "0";
                            }


                            if(status.equals("Enable"))
                            {
                                status = "1";
                            }
                            else
                            {
                                status = "0";
                            }

                            String e4 =  emptype;
                            String e5 = category;
                            String e6 = status;
                            //Toast.makeText(context, e4+"/"+e5+"/"+e6, Toast.LENGTH_SHORT).show();

                             StringRequest request = new StringRequest(Request.Method.POST, "https://investment-wizards.com/manjeet/Phils_Stock/update_category/update_stock_category.php",
                                     new Response.Listener<String>() {
                                         @Override
                                         public void onResponse(String response) {
                                             Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                         }
                                     }, new Response.ErrorListener() {
                                 @Override
                                 public void onErrorResponse(VolleyError error) {
                                     Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

                                 }
                             })
                             {
                                 @Nullable
                                 @Override
                                 protected Map<String, String> getParams() throws AuthFailureError {
                                     Map<String,String> params = new HashMap<String,String>();
                                     params.put("stock_category_id",id);
                                     params.put("stock_category_name",e5);
                                     params.put("stock_emp_category",e4);
                                     params.put("stock_category_status",e6);
                                     return  params;
                                 }
                             };
                             RequestQueue  requestQueue = Volley.newRequestQueue(context);
                             requestQueue.add(request);

                        }
                    }

                });


                testView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Initialize dialog
                       Dialog dialog=new Dialog(context);

                        // set custom dialog
                        dialog.setContentView(R.layout.dialog_searchable_spinner);

                        // set custom height and width
                        dialog.getWindow().setLayout(650,800);

                        // set transparent background
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        // show dialog
                        dialog.show();

                        // Initialize and assign variable
                        EditText editText=dialog.findViewById(R.id.edit_text);
                        ListView listView=dialog.findViewById(R.id.list_view);

                        // Initialize array adapter
                        ArrayAdapter<String> adapter=new ArrayAdapter<>(context, android.R.layout.simple_list_item_1,arrayList);

                        // set adapter
                        listView.setAdapter(adapter);
                        editText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                adapter.getFilter().filter(s);
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // when item selected from list
                                // set selected item on textView
                                testView.setText(adapter.getItem(position));

                                // Dismiss dialog
                                dialog.dismiss();
                            }
                        });
                    }
                });


                status_check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.dialog_searchable_spinner_status);

                        // set custom height and width
                        dialog.getWindow().setLayout(650,800);

                        // set transparent background
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        // show dialog
                        dialog.show();

                        // Initialize and assign variable
                        EditText editText1=dialog.findViewById(R.id.edit_text1);
                        ListView listViewstatus=dialog.findViewById(R.id.list_view_status);

                        ArrayAdapter<String> adapter=new ArrayAdapter<>(context, android.R.layout.simple_list_item_1,arrayList1);
                        listViewstatus.setAdapter(adapter);
                        editText1.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                adapter.getFilter().filter(charSequence);

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {

                            }
                        });
                        listViewstatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                status_check.setText(adapter.getItem(i));

                                // Dismiss dialog
                                dialog.dismiss();
                            }
                        });
                    }
                });



                dialogPlus.show();

            }
        });

    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView sn,category,cat_group,status,refrance;
        Button btn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sn = itemView.findViewById(R.id.sn);
            category = itemView.findViewById(R.id.category);
            cat_group = itemView.findViewById(R.id.cat_group);
            status = itemView.findViewById(R.id.status);
            btn = itemView.findViewById(R.id.id);
            refrance = itemView.findViewById(R.id.refrance);

        }
    }

}