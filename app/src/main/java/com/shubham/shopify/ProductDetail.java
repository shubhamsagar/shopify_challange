package com.shubham.shopify;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shubham.shopify.Adapter.ProductAdapter;
import com.shubham.shopify.dto.ProductDetailDto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ProductDetail extends AppCompatActivity {

    private String title;
    private String imageUrl;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter productDetailAdapter;
    List<ProductDetailDto> productDetailList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        recyclerView = (RecyclerView) findViewById(R.id.product_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        checkInternet();

    }


    void checkInternet()
    {
        ConnectivityManager cn=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf=cn.getActiveNetworkInfo();
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(nf != null && nf.isConnected()==true|| wifi!=null && wifi.isConnected()==true )
        {
            loadRecyclerViewData();
        }
        else
        {
           Toast.makeText(getApplicationContext(),"Check Internet Connection...",Toast.LENGTH_LONG).show();
        }
    }


    private void loadRecyclerViewData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String[] array = getIntent().getStringArrayExtra("productList");
        final String collectionName = getIntent().getStringExtra("collectionName");
        productDetailList = new ArrayList<>();
        int size = array.length;
        for (int i = 0; i < size; i++) {
            System.out.println(array[i]);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://shopicruit.myshopify.com/admin/products.json?ids=" + array[i] + "&page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        progressDialog.dismiss();
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray array = jsonObject.getJSONArray("products");
                        for (int j = 0; j < array.length(); j++) {
                            JSONObject o = array.getJSONObject(j);
                            title = o.getString("title");
                            System.out.println("" + title);
                            int inventory = 0;
                            JSONArray jsonArray = o.getJSONArray("variants");
                            for (int k = 0; k < jsonArray.length(); k++) {
                                JSONObject p = jsonArray.getJSONObject(k);
                                int inventory_data = Integer.valueOf(p.getString("inventory_quantity"));
                                inventory += inventory_data;
                            }
                            System.out.println("" + inventory);
                            JSONArray images = o.getJSONArray("images");
                            for (int l = 0; l < images.length(); l++) {
                                JSONObject q = images.getJSONObject(l);
                                imageUrl = q.getString("src");
                                System.out.println("" + imageUrl);
                            }
                            ProductDetailDto productDetailDto = new ProductDetailDto(collectionName, title, inventory, imageUrl);
                            productDetailList.add(productDetailDto);
                            productDetailAdapter = new ProductAdapter(productDetailList, getApplicationContext());
                            recyclerView.setAdapter(productDetailAdapter);
                            productDetailAdapter.notifyDataSetChanged();
                        }

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
                        Toast.makeText(getApplicationContext(), body, Toast.LENGTH_LONG).show();
                    } catch (UnsupportedEncodingException e) {
                        // exception
                    }
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }
}

