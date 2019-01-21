package com.shubham.shopify.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shubham.shopify.R;
import com.shubham.shopify.dto.ProductDetailDto;

import java.util.List;

/**
 * Created by SHUBHAM on 1/18/2019.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    private Context mContext;
    private List<ProductDetailDto> productDetail;

    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_layout, parent, false);

        return new ProductAdapter.MyViewHolder(itemView);
    }

    public ProductAdapter(List<ProductDetailDto> productDetail, Context mContext) {
        this.mContext = mContext;
        this.productDetail = productDetail;
    }


    @Override
    public void onBindViewHolder(ProductAdapter.MyViewHolder holder, int position) {
        final ProductDetailDto productDetailDto = productDetail.get(position);
        holder.productName.setText(""+productDetailDto.getTitle());
        holder.collectionName.setText(""+productDetailDto.getCollectionTitle());
        holder.inventoryQuantity.setText(""+productDetailDto.getInventoryQuantity()+" "+"in stock");
        Glide.with(mContext).load(productDetailDto.getImageUrl()).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return productDetail.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView productName;
        private TextView collectionName;
        private TextView inventoryQuantity;
        public ImageView imageView;
        public LinearLayout linearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.product_name);
            collectionName = (TextView) itemView.findViewById(R.id.collection_name);
            inventoryQuantity = (TextView) itemView.findViewById(R.id.product_inventory);
            imageView = (ImageView) itemView.findViewById(R.id.item_imagec);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }
}
