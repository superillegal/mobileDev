package com.example.application051;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class SecondActivity extends AppCompatActivity {
    String[] subcategories = { "Intel", "AMD", "Apple", "Qualcomm", "Samsung", "Huawei", "Nvidia", "IBM", "ARM", "MediaTek"};
    ArrayList<String> elements = new ArrayList<String>();
    ArrayList<String> selectedElements = new ArrayList<String>();
    ArrayAdapter<String> subcategoriesAdapter;
    ListView subcategoriesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Collections.addAll(elements, subcategories);
        subcategoriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, elements);
        subcategoriesListView = (ListView) findViewById(R.id.subcategories);
        subcategoriesListView.setAdapter(subcategoriesAdapter);
        subcategoriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String el = subcategoriesAdapter.getItem(position);
                if (subcategoriesListView.isItemChecked(position))
                    selectedElements.add(el);
            }
        });

    }
    public void Plus(View v){
        EditText editText = findViewById(R.id.edit_text);
        String str_el = editText.getText().toString();
        if(!str_el.isEmpty()){
            subcategoriesAdapter.add(str_el);
            editText.setText("");
            subcategoriesAdapter.notifyDataSetChanged();
        }


    }

    public void Minus(View v){
        for(int i=0; i< selectedElements.size();i++){
            subcategoriesAdapter.remove(selectedElements.get(i));
        }
        subcategoriesListView.clearChoices();
        selectedElements.clear();
        subcategoriesAdapter.notifyDataSetChanged();

    }
}