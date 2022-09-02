package com.example.phils.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phils.R;
import com.example.phils.ResponseModels.ResponseModelStockType;
import java.util.List;

public class StockTypeAdapterClass extends RecyclerView.Adapter<StockTypeAdapterClass.MyViewHolder>{

    private StockTypeAdapterClass.RecycleViewClickListener listener;
    private List<ResponseModelStockType> data;

    public StockTypeAdapterClass(RecycleViewClickListener listener, List<ResponseModelStockType> data) {
        this.listener = listener;
        this.data = data;
    }

    public void setFilteredList(List<ResponseModelStockType> filteredList)
    {
        this.data = filteredList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_stock_type_activity,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.sn.setText(data.get(position).getStock_type_id());
        holder.category.setText(data.get(position).getStock_category_id());
        holder.type.setText(data.get(position).getStock_type_name());
        holder.status.setText(data.get(position).getStock_type_status());
        holder.refrance.setText(data.get(position).getStock_type_id());
//        holder.btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                String f = holder.refrance.getText().toString();
////                Toast.makeText(context, f, Toast.LENGTH_SHORT).show();
//
//                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.refrance.getContext()).
//                        setContentHolder(new ViewHolder(R.layout.update_stock_type)).
//                        setExpanded(true,1400).create();
//
//                ArrayList arrayList1=new ArrayList<>();
//                ArrayList<String> category = new ArrayList<>();
//                //final ArrayAdapter<String>[] categoryAdapter = new ArrayAdapter<String>[1];
//
//
//                RequestQueue requestQueue;
//                requestQueue = Volley.newRequestQueue(this);
//                String url = "https://investment-wizards.com/manjeet/Phils_Stock/insert_category/fatch_spinner_stock/fatch_spinner_category_data_in_tbl_type.php";
//
//
//                arrayList1.add("Enable");
//                arrayList1.add("Disable");
//
//                View myview = dialogPlus.getHolderView();
//                TextView categoryname = myview.findViewById(R.id.category);
//                EditText typeinsert = myview.findViewById(R.id.typeinsert);
//                TextView status_check = myview.findViewById(R.id.status_check);
//                Button btn = myview.findViewById(R.id.insert_cat);
//
////
////                testView.setText(t);
//                categoryname.setText(data.get(position).getStock_category_id());
//                typeinsert.setText(data.get(position).getStock_type_name());
//                status_check.setText(data.get(position).getStock_type_status());
//
//
//                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url
//                        , null, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                         int j=0;
//                            JSONArray jsonArray = response.getJSONArray("data");
//
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                j++;
//                                Toast.makeText(context, j, Toast.LENGTH_SHORT).show();
////                                String category_name = jsonObject.optString("stock_category_name");
////                                String category_id = jsonObject.optString("stock_category_id");
////                                category.add(category_name);
////
////                                categoryAdapter[0] = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1, category);
////                                categoryAdapter[0].setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
////                                categoryAdapter = new ArrayAdapter<>(context,
////                                        android.R.layout.simple_list_item_1, category);
////                                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });
//                requestQueue.add(jsonObjectRequest);
//
////
//
//
////                categoryname.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View view) {
////                        // Initialize dialog
////                        Dialog dialog = new Dialog(context);
////
////                        // set custom dialog
////                        dialog.setContentView(R.layout.dialog_searchable_spinner_stock_type);
////
////                        // set custom height and width
////                        dialog.getWindow().setLayout(650, 800);
////
////                        // set transparent background
////                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
////
////                        // show dialog
////                        dialog.show();
////
////                        // Initialize and assign variable
////                        EditText editText = dialog.findViewById(R.id.edit_text);
////                        ListView listView = dialog.findViewById(R.id.list_view);
////
////                        listView.setAdapter(categoryAdapter);
////                        editText.addTextChangedListener(new TextWatcher() {
////                                                            @Override
////                                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
////
////                                                            }
////
////                                                            @Override
////                                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
////                                                                categoryAdapter.getFilter().filter(charSequence);
////                                                            }
////
////                                                            @Override
////                                                            public void afterTextChanged(Editable editable) {
////
////                                                            }
////
////                                                        }
////
////                        );
////
////
////                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////                            @Override
////                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                                // when item selected from list
////                                // set selected item on textView
////                                categoryname.setText(categoryAdapter.getItem(position));
////
////                                // Dismiss dialog
////                                dialog.dismiss();
////
////
//////                            String s = select_type.getText().toString();
//////                            Toast.makeText(Add_Stock_Type_Activity.this, s, Toast.LENGTH_SHORT).show();
////
//////                                JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, url
//////                                        , null, new Response.Listener<JSONObject>() {
//////                                    @Override
//////                                    public void onResponse(JSONObject response) {
//////                                        try {
//////                                            String ss = select_type.getText().toString();
//////                                            JSONArray jsonArray = response.getJSONArray("data");
//////
//////                                            for (int i = 0; i < jsonArray.length(); i++) {
//////                                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//////                                                String category_name = jsonObject.optString("stock_category_name");
//////                                                String category_id = jsonObject.optString("stock_category_id");
//////                                                if(ss.equals(category_name)){
//////                                                    String idea = category_id;
//////                                                    id12.setText(idea);
//////                                                    //Toast.makeText(Add_Stock_Type_Activity.this, idea, Toast.LENGTH_SHORT).show();
//////
//////                                                }
//////                                            }
//////
//////                                        } catch (JSONException e) {
//////                                            e.printStackTrace();
//////                                        }
//////
//////                                    }
//////
//////                                }, new Response.ErrorListener() {
//////                                    @Override
//////                                    public void onErrorResponse(VolleyError error) {
//////
//////                                    }
//////                                });
//////
//////                                requestQueue.add(jsonObjectRequest1);
////
////                            }
////
////                        });
////
////
////
////                    }
////                });
//
//
//
//                status_check.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Dialog dialog = new Dialog(context);
//                        dialog.setContentView(R.layout.dialog_searchable_spinner_status);
//
//                        // set custom height and width
//                        dialog.getWindow().setLayout(650,800);
//
//                        // set transparent background
//                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//                        // show dialog
//                        dialog.show();
//
//                        // Initialize and assign variable
//                        EditText editText1=dialog.findViewById(R.id.edit_text1);
//                        ListView listViewstatus=dialog.findViewById(R.id.list_view_status);
//
//                        ArrayAdapter<String> adapter=new ArrayAdapter<>(context, android.R.layout.simple_list_item_1,arrayList1);
//                        listViewstatus.setAdapter(adapter);
//                        editText1.addTextChangedListener(new TextWatcher() {
//                            @Override
//                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                            }
//
//                            @Override
//                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                                adapter.getFilter().filter(charSequence);
//
//                            }
//
//                            @Override
//                            public void afterTextChanged(Editable editable) {
//
//                            }
//                        });
//                        listViewstatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                status_check.setText(adapter.getItem(i));
//
//                                // Dismiss dialog
//                                dialog.dismiss();
//                            }
//                        });
//                    }
//                });
//
//                dialogPlus.show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        TextView sn,category,type,status,refrance;
        Button btn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sn = itemView.findViewById(R.id.sn);
            category = itemView.findViewById(R.id.category);
            type = itemView.findViewById(R.id.type);
            status = itemView.findViewById(R.id.status);
            btn = itemView.findViewById(R.id.id);
            refrance = itemView.findViewById(R.id.refrance);
            btn.setOnClickListener(this);


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
