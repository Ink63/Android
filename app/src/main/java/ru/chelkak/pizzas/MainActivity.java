package ru.chelkak.pizzas;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ShareActionProvider;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ShareActionProvider shareActionProvider;
    private Toolbar toolbar;

    private int[] products_id;
    private String[] products_Names;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        products_Names = new String[]{
                getResources().getString(R.string.menu_item_0),
                getResources().getString(R.string.menu_item_1),
                getResources().getString(R.string.menu_item_2),
                getResources().getString(R.string.menu_item_3),
                getResources().getString(R.string.menu_item_4)};

        products_id = new int[]{
                0,
                R.array.pizza_names,
                R.array.burgers_names,
                R.array.salads_names,
                R.array.drinks_names};

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                startActivity(intent);


            }
        });


        // меню вкладок
        //Attach the SectionsPagerAdapter to the ViewPager
        SectionsPagerAdapter pagerAdapter =
                new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);
        //Attach the ViewPager to the TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public int getCount() {
            return 5;
        }
        @Override
        public Fragment getItem(int position) {
            if ((position>=0) && (position<5)) {
                return getProductByPos(position);
            }
            return null;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            if ((position>=0) && (position<5)) {
                return getProductNames(position);
            }
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        setShareActionIntent("This is example text");

        return super.onCreateOptionsMenu(menu);
    }

    private void setShareActionIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }

    //
    private CharSequence getProductNames(int pos) {
        return products_Names[pos];
    }

    // возвращает новый фрагмент с заданными параметрами
    private Fragment getProductByPos(int num_of_prod) {
       if ((num_of_prod==0))
           return new TopFragment();
       else
       if ((num_of_prod>0) && (num_of_prod<5)) {
           Fragment fragment = new ProductsList();
           Bundle args = new Bundle();
           args.putInt(ProductsList.ID_LIST, products_id[num_of_prod]);
           args.putInt(ProductsList.POSITION,num_of_prod+1);
           args.putString(ProductsList.CATEGORY_NAME,products_Names[num_of_prod]);
           fragment.setArguments(args);
           return fragment;
       }
       else
         Toast.makeText(this,"Error create fragment. num_of_prod = "+num_of_prod,Toast.LENGTH_LONG)
         .show();
       return null;

    }



}
