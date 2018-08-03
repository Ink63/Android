package ru.chelkak.pizzas;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ProductsList extends ListFragment {
    private int position;

    public static final String CATEGORY_NAME = "category";
    public static final String ID_LIST = "id_list";
    public static final String POSITION = "pos";
    public static final String NAME = "name";

    private String name_category;
    private int id_names;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        if (getArguments() != null) {
            id_names = getArguments().getInt(ID_LIST); // id списка в ресурсах
            position = getArguments().getInt(POSITION); // позиция в списке категорий
            name_category = getArguments().getString(CATEGORY_NAME);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    inflater.getContext(),
                    android.R.layout.simple_list_item_1,
                    getResources().getStringArray(id_names));
            setListAdapter(adapter);
            return super.onCreateView(inflater, container, savedInstanceState);
        }
        return null;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String name = (String) l.getItemAtPosition(position).toString();
        /*try {
            Toast.makeText(getContext(),name_category+" : " +name,Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {
        } */

        Intent intent = new Intent(getContext(),OrderActivity.class);

        intent.putExtra(ProductsList.CATEGORY_NAME,name_category);
        intent.putExtra(ProductsList.NAME,name);
        getContext().startActivity(intent);

    }
}