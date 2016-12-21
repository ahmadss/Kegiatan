package com.catatankegiatan.kegiatan;


import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainListFragment extends ListFragment {

    private ArrayList<Catatan> catatens;
    private CatatanAdapter catatanAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        catatens = new ArrayList<Catatan>();
//        catatens.add(new Catatan("ini judul catatan kegiatan anda","ini adalah penjelasan keterangan dari catatan kegiatan anda",Catatan.Kategori.KERJA));
//        catatens.add(new Catatan("ini judul catatan kegiatan anda","ini adalah penjelasan keterangan dari catatan kegiatan anda",Catatan.Kategori.KERJA));
//        catatens.add(new Catatan("ini judul catatan kegiatan anda","ini adalah penjelasan keterangan dari catatan kegiatan anda",Catatan.Kategori.KERJA));
//        catatens.add(new Catatan("ini judul catatan kegiatan anda","ini adalah penjelasan keterangan dari catatan kegiatan anda",Catatan.Kategori.KERJA));

        KegiatanDbAdapter dbAdapter = new KegiatanDbAdapter(getActivity().getBaseContext());
        dbAdapter.open();
        catatens = dbAdapter.getAllKegiatan();
        dbAdapter.close();

        catatanAdapter = new CatatanAdapter(getActivity(),catatens);
        setListAdapter(catatanAdapter);

        registerForContextMenu(getListView());

//        getListView().setDivider(ContextCompat.getDrawable(getActivity(), android.R.color.black));
//        getListView().setDividerHeight(1);
//        String[] values = new String[] {"Android","iphone","windowsphone"};
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
//        setListAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.long_press_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int rowPosisi = info.position;
        Catatan catatan = (Catatan) getListAdapter().getItem(rowPosisi);

        switch (item.getItemId()) {
            case R.id.edit:
                Log.d("klik", "onContextItemSelected: "+item.getItemId());
                launchDetailActivity(MainActivity.FragmentToLaunch.EDIT, rowPosisi);
                return true;
            case R.id.delete:
                KegiatanDbAdapter kegiatanDbAdapter = new KegiatanDbAdapter(getActivity().getBaseContext());
                kegiatanDbAdapter.open();
                kegiatanDbAdapter.deleteKegiatan(catatan.getId());

                catatens.clear();
                catatens.addAll(kegiatanDbAdapter.getAllKegiatan());
                catatanAdapter.notifyDataSetChanged();
                kegiatanDbAdapter.close();

                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        launchDetailActivity(MainActivity.FragmentToLaunch.VIEW, position);
    }

    private void launchDetailActivity(MainActivity.FragmentToLaunch ftl, int posisi) {
        Catatan catatan = (Catatan) getListAdapter().getItem(posisi);
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(MainActivity.CATATAN_JUDUL_EXTRA, catatan.getJudul());
        intent.putExtra(MainActivity.CATATAN_KETERANGAN_EXTRA, catatan.getPesan());
        intent.putExtra(MainActivity.CATATAN_KATEGORI_EXTRA, catatan.getKategori());
        intent.putExtra(MainActivity.CATATAN_ID_EXTRA, catatan.getId());

        switch (ftl) {
            case VIEW:
                intent.putExtra(MainActivity.KEGIATAN_FRAGMENT_TO_LOAD_EXTRA, MainActivity.FragmentToLaunch.VIEW);
                break;
            case EDIT:
                intent.putExtra(MainActivity.KEGIATAN_FRAGMENT_TO_LOAD_EXTRA, MainActivity.FragmentToLaunch.EDIT);
                break;

        }

        startActivity(intent);
    }
}
