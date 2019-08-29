package com.example.bmc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class GeneralInfoAdapter extends BaseAdapter {
    private final ArrayList<Map.Entry<String, String>> data;

    public GeneralInfoAdapter( Map< String, String > myMap ) {
        data = new ArrayList<>();
        data.addAll( myMap.entrySet() );
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Map.Entry< String, String > getItem(int position) {
        return data.get( position );
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view;

        if( convertView == null ) {
            view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.general_info_list_row, parent, false );
        } else {
            view = convertView;
        }

        Map.Entry< String, String > item = getItem( position );

        ( ( TextView ) view.findViewById( R.id.list_title ) ).setText( item.getKey() );
        ( ( TextView ) view.findViewById( R.id.contents ) ).setText( item.getValue() );

        return view;
    }
}
