package com.example.guitar_center_wan.view;

import android.content.Context;
import android.widget.SearchView;

import com.example.guitar_center_wan.entity.Product;

import java.util.List;

public class View_Search {

    private final Context context;
    private final SearchView searchView;

    private List<Product> productList;
    private SearchListener searchListener;

    public View_Search(Context context, SearchView searchView) {
        this.context = context;
        this.searchView = searchView;

        // Thiết lập sự kiện cho SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Khi nội dung của SearchView thay đổi, gọi sự kiện onQueryTextChange của SearchListener
                if (searchListener != null) {
                    searchListener.onQueryTextChange(newText);
                }
                return true;
            }
        });
    }

    // Phương thức setter để thiết lập SearchListener từ bên ngoài
    public void setSearchListener(SearchListener searchListener) {
        this.searchListener = searchListener;
    }

    // Interface để định nghĩa sự kiện của SearchView
    public interface SearchListener {
        void onQueryTextChange(String newText);
        void onQueryTextSubmit(String query);
    }
}
