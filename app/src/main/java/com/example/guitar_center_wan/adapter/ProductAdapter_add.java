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



public class
ProductAdapter_add {
    private final Context context;


    private final AddProductListener addProductListener;

    private EditText editTextProductID, editTextName, editTextUnit, editTextPrice, editTextImage, editTextDescription;

    private Button buttonAdd, buttonCancel;

    public ProductAdapter_add(Context context, AddProductListener addProductListener) {
        this.context = context;
        this.addProductListener = addProductListener;
    }

    @SuppressLint("MissingInflatedId")
    public void showAddProductPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.popup_add_product, null);
        builder.setView(dialogView);

        editTextProductID = dialogView.findViewById(R.id.addTextProductId);
        editTextName = dialogView.findViewById(R.id.addTextProductName);
        editTextUnit = dialogView.findViewById(R.id.addTextProductQuantity);
        editTextPrice = dialogView.findViewById(R.id.addTextProductPrice);
        editTextImage = dialogView.findViewById(R.id.addTextProductImage);
        editTextDescription = dialogView.findViewById(R.id.addTextProductDescription);

        buttonAdd = dialogView.findViewById(R.id.buttonAddProduct);
        buttonCancel = dialogView.findViewById(R.id.buttonCancel);

        AlertDialog alertDialog = builder.create();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_product = editTextProductID.getText().toString().trim();
                String name = editTextName.getText().toString().trim();
                int unit = Integer.parseInt(editTextUnit.getText().toString().trim());
                Double price = Double.parseDouble(editTextPrice.getText().toString().trim());
                String image = editTextImage.getText().toString().trim();
                String description = editTextDescription.getText().toString().trim();

                // Kiểm tra thông tin sản phẩm trước khi thêm vào
                if (validateProductInfo(id_product, name, unit, price, description)) {
                    Product product = new Product(id_product,name,unit,price,image,description);

                    addProductListener.onAddProduct(product);

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
        if (id.isEmpty() || name.isEmpty() || quantity <=0 || price <=0|| description.isEmpty()) {
            Toast.makeText(context, "Vui lòng điền đầy đủ thông tin sản phẩm", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Kiểm tra định dạng và tính hợp lệ của các trường dữ liệu khác nếu cần

        return true;
    }

    public interface AddProductListener {
        void onAddProduct(Product product);
    }
}
