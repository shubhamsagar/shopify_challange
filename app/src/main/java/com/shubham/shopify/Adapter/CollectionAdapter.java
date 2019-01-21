package com.shubham.shopify.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.shubham.shopify.ProductDetail;
import com.shubham.shopify.R;
import com.shubham.shopify.dto.CollectionRequestDto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by SHUBHAM on 1/15/2019.
 */

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.MyViewHolder> {
    private Context mContext;
    private List<CollectionRequestDto> collectionList;
    private String[] productList;
    private String collectionName;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.collection_card, parent, false);

        return new CollectionAdapter.MyViewHolder(itemView);
    }

    public CollectionAdapter(List<CollectionRequestDto> collectionList, Context mContext) {
        this.mContext = mContext;
        this.collectionList = collectionList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final CollectionRequestDto collection = collectionList.get(position);
        holder.title.setText(collection.getTitle());
        // loading album cover using Glide library
        Glide.with(mContext).load(collection.getImageUrl()).into(holder.thumbnail);

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://shopicruit.myshopify.com/admin/collects.json?collection_id=" + collection.getId() + "&page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("collects");
                            int size = array.length();
                            productList = new String[size];
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                String id = o.getString("product_id");
                                if (id != null) {
                                    productList[i] = id;
                                }
                            }
                            collectionName = collection.getTitle();

                            Intent intent = new Intent(mContext, ProductDetail.class);
                            intent.putExtra("productList", productList);
                            intent.putExtra("collectionName", collectionName);
                            mContext.startActivity(intent);

                        } catch (JSONException e) {

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error == null || error.networkResponse == null) {
                            final String statusCode = String.valueOf(error.networkResponse.statusCode);
                            return;
                        }

                        String body;
                        //get status code here
                        //get response body and parse with appropriate encoding
                        try {
                            body = new String(error.networkResponse.data, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            // exception
                        }
                    }
                }
                );
                RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                requestQueue.add(stringRequest);


            }
        });

    }

    @Override
    public int getItemCount() {
        return collectionList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;
        public LinearLayout linearLayout;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
        }
    }
}
