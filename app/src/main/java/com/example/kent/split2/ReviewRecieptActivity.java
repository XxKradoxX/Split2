package com.example.kent.split2;

import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.kent.split2.RecyclerViewReciept.ProductObject;
import com.example.kent.split2.RecyclerViewReciept.RCAdapterReciept;

import java.util.ArrayList;
import java.util.List;

public class ReviewRecieptActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Reciept mReciept;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_reciept);

        List<Product> listOfProducts = new ArrayList<>();
        Bitmap bitmap = this.getIntent().getParcelableExtra("BitmapImage");
        ArrayList<String> data = this.getIntent().getStringArrayListExtra("data");
        for (int i =0; i < data.size(); i = i+2) {
            Product p =new Product(data.get(i),Double.valueOf(data.get(i+1)));
            listOfProducts.add(p);
        }

        mReciept = new Reciept(listOfProducts,bitmap);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplication());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RCAdapterReciept(getDataSet(),getApplication());
        mRecyclerView.setAdapter(mAdapter);

    }

    private ArrayList<Product> results = new ArrayList<>();
    private ArrayList<Product> getDataSet() {
        listenForData();
        return results;
    }

    private void listenForData() {
        for (int i =0;i<mReciept.getItems().size(); i++) {
            results.add(mReciept.getItems().get(i));
        }
    }


}