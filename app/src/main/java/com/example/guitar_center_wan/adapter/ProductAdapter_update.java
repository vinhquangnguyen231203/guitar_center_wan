package com.example.guitar_center_wan.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.guitar_center_wan.R;
import com.example.guitar_center_wan.entity.Product;
public class ProductAdapter_update implements ProductAdapter.ProductAdapterListener {

    private final Context context;
    private final UpdateProductListener updateProductListener;

    private EditText editTextProductID, editTextName, editTextUnit, editTextPrice, editTextImage, editTextDescription;

    private Button buttonUpdate, buttonCancel;

    public ProductAdapter_update(Context context, UpdateProductListener updateProductListener) {
        this.context = context;
        this.updateProductListener = updateProductListener;
    }

    @SuppressLint("MissingInflatedId")
    public void showUpdateProductPopup(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.popup_update_product, null);
        builder.setView(dialogView);

        editTextProductID = dialogView.findViewById(R.id.editTextProductId);
        editTextProductID.setFocusable(false);
        editTextProductID.setFocusableInTouchMode(false);
        editTextName = dialogView.findViewById(R.id.editTextProductName);
        editTextUnit = dialogView.findViewById(R.id.editTextProductQuantity);
        editTextPrice = dialogView.findViewById(R.id.editTextProductPrice);
        editTextImage = dialogView.findViewById(R.id.editTextImage);
        editTextDescription = dialogView.findViewById(R.id.editTextProductDescription);

        buttonUpdate = dialogView.findViewById(R.id.buttonUpdateProduct);
        buttonCancel = dialogView.findViewById(R.id.buttonCancel);

        // Hiển thị thông tin sản phẩm cần cập nhật
        editTextProductID.setText(product.getId_product());
        editTextName.setText(product.getName_product());
        editTextUnit.setText(String.valueOf(product.getUnit()));
        editTextPrice.setText(String.valueOf(product.getPrice()));
        editTextImage.setText(product.getImage());
        editTextDescription.setText(product.getDescription());

        AlertDialog alertDialog = builder.create();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin sản phẩm mới
                String id_product = editTextProductID.getText().toString().trim();
                String name = editTextName.getText().toString().trim();
                int unit = Integer.parseInt(editTextUnit.getText().toString().trim());
                Double price = Double.parseDouble(editTextPrice.getText().toString().trim());
                String image = editTextImage.getText().toString().trim();
                String description = editTextDescription.getText().toString().trim();

                // Kiểm tra tính hợp lệ của thông tin sản phẩm
                if (validateProductInfo(id_product, name, unit, price, description)) {
                    Product updatedProduct = new Product(id_product, name, unit, price, image, description);

                    // Gửi thông tin sản phẩm cập nhật
                    updateProductListener.onUpdateProduct(updatedProduct);

                    alertDialog.dismiss();
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đóng dialog khi người dùng nhấn nút "Hủy"
                alertDialog.dismiss();
            }
        });

        // Hiển thị dialog
        alertDialog.show();
    }

    private boolean validateProductInfo(String id, String name, int quantity, Double price, String description) {
        if (id.isEmpty() || name.isEmpty() || quantity <= 0 || price <= 0 || description.isEmpty()) {
            Toast.makeText(context, "Vui lòng điền đầy đủ thông tin sản phẩm", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onUpdateProduct(Product product) {
        showUpdateProductPopup(product);
    }

    public interface UpdateProductListener {
        void onUpdateProduct(Product product);
    }
}
