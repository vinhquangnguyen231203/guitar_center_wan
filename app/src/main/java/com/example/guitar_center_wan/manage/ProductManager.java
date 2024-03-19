package com.example.guitar_center_wan.manage;

import androidx.annotation.NonNull;

import com.example.guitar_center_wan.entity.Product;
import com.example.guitar_center_wan.services.ProductAPIServices;
import com.example.guitar_center_wan.services.Retrofit_Adapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductManager {
    private final ProductAPIServices productAPIServices;

    public ProductManager() {
        productAPIServices = Retrofit_Adapter.getRetrofitInstance().create(ProductAPIServices.class);
    }

    public void getAllProduct(final ListCallback callback) {
        Call<List<Product>> call = productAPIServices.getAllProduct();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    String errorMessage = "Failed to get list of products";
                    if (response.errorBody() != null) {
                        errorMessage = response.errorBody().toString();
                    }
                    callback.onFailure(errorMessage);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public void deleteProduct(String productID, final ProductCallback callback) {
        Call<Boolean> call = productAPIServices.deleteProduct(productID);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                    getAllProduct(new ListCallback() {
                        @Override
                        public void onSuccess(List<Product> productList) {

                        }

                        @Override
                        public void onFailure(String errorMessage) {

                        }
                    });
                } else {
                    String errorMessage = "Failed to delete product";
                    if (response.errorBody() != null) {
                        errorMessage = response.errorBody().toString();
                    }
                    callback.onFailure(errorMessage);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }
    public  void insertProduct(Product product,final ProductCallback callback)
    {
        Call<Boolean> call = productAPIServices.insertProduct(product);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                    getAllProduct(new ListCallback() {
                        @Override
                        public void onSuccess(List<Product> productList) {

                        }

                        @Override
                        public void onFailure(String errorMessage) {

                        }
                    });
                } else {
                    String errorMessage = "Failed to insert product";
                    if (response.errorBody() != null) {
                        errorMessage = response.errorBody().toString();
                    }
                    callback.onFailure(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }
    public void updateProduct(Product product,final ProductCallback callback)
    {
        Call<Boolean> call = productAPIServices.updateProduct(product);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                    getAllProduct(new ListCallback() {
                        @Override
                        public void onSuccess(List<Product> productList) {

                        }

                        @Override
                        public void onFailure(String errorMessage) {

                        }
                    });
                } else {
                    String errorMessage = "Failed to insert product";
                    if (response.errorBody() != null) {
                        errorMessage = response.errorBody().toString();
                    }
                    callback.onFailure(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public interface ListCallback {
        void onSuccess(List<Product> productList);

        void onFailure(String errorMessage);

    }

    //thêm,xóa ,suawr
    public interface ProductCallback{
        void onSuccess(Boolean isSuccess);

        void onFailure(String errorMessage);
    }
}
