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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ShareActionProvider shareActionProvider;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private NavigationView navigationView;

    private int currentPosition = 0;
    private final String TAG_ACTIVE_FRAGMENT = "visible_fragment";

    private int[] products_id;

    private String[] products_Names;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        products_Names = new String[]{
                getResources().getString(R.string.menu_item_1),
                getResources().getString(R.string.menu_item_2),
                getResources().getString(R.string.menu_item_3),
                getResources().getString(R.string.menu_item_4)};

        products_id = new int[]{
                R.array.pizza_names,
                R.array.burgers_names,
                R.array.salads_names,
                R.array.drinks_names};

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // обеспечивает закрытие навигационной панели при нажатии на ее заголовок(картинку)
        View headerView = navigationView.getHeaderView(0);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
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

    //
    private CharSequence getProductNames(int pos) {
        return products_Names[pos];
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public int getCount() {
            return 4;
        }
        @Override
        public Fragment getItem(int position) {
            if ((position>=0) && (position<4)) {
                return getProductByPos(position);
            }
            return null;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            if ((position>=0) && (position<4)) {
                return getProductNames(position);
            }
            return null;
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

        //Toast.makeText(this,""+navigationView.getCheckedItem().getItemId(),Toast.LENGTH_SHORT).show();
        ProductsList fragment = (ProductsList) getSupportFragmentManager().findFragmentByTag(TAG_ACTIVE_FRAGMENT);
        if (fragment!=null) {
            int pos = fragment.getPosition();
            if (currentPosition!=pos) currentPosition=pos;
            else currentPosition=0;
            //currentPosition = bundle.getInt(TopFragment.POSITION)-1;
            //Bundle bundle = fragment.getArguments();
        }
        else currentPosition=0;

        setActionBarTitle();

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // возвращает новый фрагмент с заданными параметрами
    private Fragment getProductByPos(int num_of_prod) {
       if ((num_of_prod>=0) && (num_of_prod<4)) {
           Fragment fragment = new ProductsList();
           Bundle args = new Bundle();
           args.putInt(ProductsList.ID_LIST, products_id[num_of_prod]);
           args.putInt(ProductsList.POSITION,num_of_prod+1);
           args.putString(ProductsList.CATEGORY_NAME,products_Names[num_of_prod]);
           fragment.setArguments(args);
           return fragment;
       }
       Toast.makeText(this,"Error: fragment not created! pos="+num_of_prod,Toast.LENGTH_LONG)
               .show();
       return null;

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int position=-1;
        int id = item.getItemId();

        if (id == R.id.menu_item_1) {
            position = 1;
        } else if (id == R.id.menu_item_2) {
            position = 2;
        } else if (id == R.id.menu_item_3) {
            position = 3;
        } else if (id == R.id.menu_item_4) {
            position = 4;
        }

        if (position > 0) {
            Fragment fragment = getProductByPos(position-1);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, fragment,TAG_ACTIVE_FRAGMENT)
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
            // меняем заголовок
            currentPosition = position;
            setActionBarTitle();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setActionBarTitle(){
            if (currentPosition==0)
                toolbar.setTitle(R.string.main_activity_name);
            else
                toolbar.setTitle(products_Names[currentPosition-1]);

    }


}
