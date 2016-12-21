package com.catatankegiatan.kegiatan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ahmad on 27/11/2016.
 */
public class CatatanAdapter extends ArrayAdapter<Catatan> {

    public static class ViewHolder{
        TextView judul;
        TextView catatan;
        ImageView catatanIcon;
    }

    public CatatanAdapter(Context context, ArrayList<Catatan> cataten) {
        super(context,0, cataten);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Catatan catatan = getItem(position);

        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row,parent,false);
            viewHolder.judul = (TextView)convertView.findViewById(R.id.listKgtJudul);
            viewHolder.catatan = (TextView)convertView.findViewById(R.id.listKgtketerangan);
            viewHolder.catatanIcon = (ImageView)convertView.findViewById(R.id.listKgtIcon);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.judul.setText(catatan.getJudul());
        viewHolder.catatan.setText(catatan.getPesan());
        viewHolder  .catatanIcon.setImageResource(catatan.getAssosiasiGambar());

        return convertView;
    }
}
