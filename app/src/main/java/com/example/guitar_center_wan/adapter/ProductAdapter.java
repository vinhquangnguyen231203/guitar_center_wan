package com.example.guitar_center_wan.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guitar_center_wan.R;
import com.example.guitar_center_wan.entity.Product;
import com.example.guitar_center_wan.manage.ProductManager;
import com.example.guitar_center_wan.view.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> implements FilterableAdapter {

    private final Context context;
    private final List<Product> productList;

    private List<Product> productListFull; //  // Danh sách sản phẩm gốc, không bị ảnh hưởng bởi bộ lọc

    private final ProductManager productManager;

    private  ProductAdapterListener listener;


    public ProductAdapter(Context context, List<Product> productList, ProductManager productManager) {
        this.context = context;
        this.productList = productList;
        this.productManager = productManager;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product product = productList.get(position);

        // Set data to views
        holder.textViewID.setText(product.getId_product());
        holder.textViewName.setText(product.getName_product());
        holder.textViewUnit.setText(String.valueOf(product.getUnit()));
        holder.textViewPrice.setText(String.valueOf(product.getPrice()));
        holder.textViewDescription.setText(product.getDescription());

        // Load hình ảnh từ URL sử dụng Picasso
        String imagePath = "http://10.0.2.2:8080/guitar_center/img/" + product.getImage();
        Picasso.get()
                .load(imagePath)
//                .placeholder(R.drawable.placeholder_image) // Placeholder image khi đang tải
//                .error(R.drawable.error_image) // Ảnh lỗi nếu không tải được
                .into(holder.imageView);

        // Set click listeners for buttons
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Xóa sản phẩm");
                    builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này");

                    //Nếu ấn đồng ý
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            productManager.deleteProduct(product.getId_product(), new ProductManager.ProductCallback() {
                                @Override
                                public void onSuccess(Boolean isSuccess) {
                                    if (isSuccess) {
                                        // Xóa sản phẩm thành công

                                        productList.remove(position);
                                        if (productListFull != null) {
                                            for (int i = 0; i < productListFull.size(); i++) {
                                                if (productListFull.get(i).getId_product().equals(product.getId_product())) {
                                                    productListFull.remove(i);
                                                    break;
                                                }
                                            }
                                        }
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, productList.size());// Xóa sản phẩm khỏi danh sách// Cập nhật giao diện
                                        Toast.makeText(context, "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Xóa sản phẩm không thành công
                                        Toast.makeText(context, "Xóa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(String errorMessage) {
                                    // Xử lý khi có lỗi xảy ra
                                    Toast.makeText(context, "Lỗi: " + errorMessage, Toast.LENGTH_SHORT).show();
                                }

                            });
                        }
                    });

                    //Nếu ấn từ chối
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
        });

        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductAdapter_update productAdapterUpdate = new ProductAdapter_update(context, (ProductAdapter_update.UpdateProductListener) context);
                productAdapterUpdate.showUpdateProductPopup(product);

            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public void filter(String text) {
        // Nếu danh sách sản phẩm gốc chưa được khởi tạo, sao chép từ danh sách hiện tại
        if (productListFull == null) {
            productListFull = new ArrayList<>(productList);
        }

        productList.clear(); // Xóa danh sách sản phẩm hiện tại

        // Nếu key truyền vào là rỗng, hiển thị toàn bộ danh sách sản phẩm
        if (text.isEmpty()) {
            productList.addAll(productListFull);
        } else {
            // Nếu không, lọc danh sách sản phẩm theo văn bản đầu vào
            String searchText = text.toLowerCase().trim();
            for (Product product : productListFull) {
                if (product.getName_product().toLowerCase().contains(searchText)) {
                    productList.add(product);
                }
            }

        }


        notifyDataSetChanged(); // Cập nhật giao diện RecyclerView
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewID, textViewName, textViewUnit, textViewPrice, textViewDescription;
        Button buttonDelete, buttonEdit;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewID = itemView.findViewById(R.id.textViewID);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewUnit = itemView.findViewById(R.id.textViewUnit);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
        }
    }
    public interface ProductAdapterListener
    {
        void onUpdateProduct(Product product);
    }
    public void setUpdateListener(ProductAdapterListener listener)
    {
        this.listener = listener;
    }


}

