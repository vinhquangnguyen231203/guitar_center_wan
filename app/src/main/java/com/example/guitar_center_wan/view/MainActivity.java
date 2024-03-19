package com.example.guitar_center_wan.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guitar_center_wan.R;
import com.example.guitar_center_wan.adapter.ProductAdapter;
import com.example.guitar_center_wan.adapter.ProductAdapter_add;
import com.example.guitar_center_wan.adapter.ProductAdapter_update;
import com.example.guitar_center_wan.entity.Product;
import com.example.guitar_center_wan.manage.ProductManager;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View_Search.SearchListener, ProductAdapter_add.AddProductListener,ProductAdapter_update.UpdateProductListener{

    private RecyclerView recyclerView;

    private ProductManager productManager;

    private ProductAdapter productAdapter;

    private SearchView searchView;

    private View_Search viewSearch;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        productManager = new ProductManager();


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

       //

        searchView = findViewById(R.id.searchView);
        viewSearch = new View_Search(this,searchView);
        viewSearch.setSearchListener(this);

        //

        Button buttonAdd = findViewById(R.id.buttonAddProduct);

        buttonAdd.setOnClickListener(v -> {
            ProductAdapter_add productAdapterAdd = new ProductAdapter_add(MainActivity.this,
                    MainActivity.this);
            productAdapterAdd.showAddProductPopup();
        });



        loadProduct();
    }


    private void loadProduct()
    {
        productManager.getAllProduct(new ProductManager.ListCallback() {
            @Override
            public void onSuccess(List<Product> productList) {
                updateUI(productList);
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(MainActivity.this, "Failed to get product list: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private  void updateUI(List<Product>productList)
    {
        productAdapter = new ProductAdapter(MainActivity.this,productList,productManager);
        recyclerView.setAdapter(productAdapter);
    }


    @Override
    public void onQueryTextChange(String newText) {
        productAdapter.filter(newText);
    }

    @Override
    public void onQueryTextSubmit(String query) {
        //null
    }

    @Override
    public void onAddProduct(Product product) {
        productManager.insertProduct(product, new ProductManager.ProductCallback() {
            @Override
            public void onSuccess(Boolean isSuccess) {
                if(isSuccess)
                {
                    loadProduct();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Failed to add product", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(MainActivity.this, "Failed to add product " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public void onUpdateProduct(Product product) {
        productManager.updateProduct(product, new ProductManager.ProductCallback() {
            @Override
            public void onSuccess(Boolean isSuccess) {
                if(isSuccess)
                {
                    loadProduct();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Failed to update product", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(MainActivity.this, "Failed to update product " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
