package ru.chelkak.pizzas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class OrderActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.activity_order);

        String name = getIntent().getExtras().getString(ProductsList.NAME);
        String category = getIntent().getExtras().getString(ProductsList.CATEGORY_NAME);
        textView = findViewById(R.id.order_text1);
        textView.setText(category + " " + name);

        getSupportActionBar().setTitle(category+" : " + name);




        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
    }


}