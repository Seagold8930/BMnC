//**************************************************************************************************
// Copyright <2019> <DAN MOTA>
//
// Permission to use, copy, modify, and/or distribute this software for any purpose with or without
// fee is hereby granted, provided that the above copyright notice and this permission notice appear
// in all copies.
//
// THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS
// SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE
// AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
// WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT,
// NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE
// OF THIS SOFTWARE.
//**************************************************************************************************
package com.example.bmc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class GeneralInfoAdapter extends BaseAdapter {
    private final ArrayList< Map.Entry< String, String > > data;

    public GeneralInfoAdapter( Map< String, String > myMap ) {
        data = new ArrayList<>();
        data.addAll( myMap.entrySet() );
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Map.Entry< String, String > getItem( int position ) {
        return data.get( position );
    }

    @Override
    public long getItemId( int position ) {
        return position;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent ) {
        final View view;

        if( convertView == null ) {
            view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.
                    general_info_list_row, parent, false );
        } else {
            view = convertView;
        }

        Map.Entry< String, String > item = getItem( position );

        ( ( TextView ) view.findViewById( R.id.list_title ) ).setText( item.getKey() );
        ( ( TextView ) view.findViewById( R.id.contents ) ).setText( item.getValue() );

        return view;
    }
}
