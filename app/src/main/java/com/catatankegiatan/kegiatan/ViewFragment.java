package com.catatankegiatan.kegiatan;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentLayout = inflater.inflate(R.layout.fragment_view,container, false);
        TextView judul = (TextView)fragmentLayout.findViewById(R.id.viewKgtJudul);
        TextView keterangan = (TextView)fragmentLayout.findViewById(R.id.viewKgtkegiatan);
        ImageView imageView = (ImageView)fragmentLayout.findViewById(R.id.viewKgticon);

        Intent intent = getActivity().getIntent();
        judul.setText(intent.getExtras().getString(MainActivity.CATATAN_JUDUL_EXTRA));
        keterangan.setText(intent.getExtras().getString(MainActivity.CATATAN_KETERANGAN_EXTRA));

        Catatan.Kategori kgtIcon = (Catatan.Kategori)intent.getSerializableExtra(MainActivity.CATATAN_KATEGORI_EXTRA);
        imageView.setImageResource(Catatan.kategoriKeGambar(kgtIcon));
        // Inflate the layout for this fragment
        return fragmentLayout;
    }

}
