package ru.chelkak.pizzas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        this.setTitleColor(R.color.colorPrimaryDark);
        this.setTitle(R.string.title_order_activity);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


    }

    public void onClickDone(View view) {
        CharSequence text = "Your order has been updated";
        int duration = Snackbar.LENGTH_LONG;
        Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinator),text,duration );
        snackbar.setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(OrderActivity.this, "Undone!",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        snackbar.show();
    }
}