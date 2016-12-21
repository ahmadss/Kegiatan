package com.catatankegiatan.kegiatan;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {

    public static final String KEGIATAN_BARU_EXTRA = "Kegiatan_baru";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        buatDanTambahFragment();
    }

    private void buatDanTambahFragment() {
        Intent intent = getIntent();
        MainActivity.FragmentToLaunch fragmentToLaunch = (MainActivity.FragmentToLaunch)intent.getSerializableExtra(MainActivity.KEGIATAN_FRAGMENT_TO_LOAD_EXTRA);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (fragmentToLaunch){
            case EDIT:
                EditFragment editFragment = new EditFragment();
                setTitle(R.string.editfragmentJudul);
                fragmentTransaction.add(R.id.kegiatan_container, editFragment, "KEGIATAN_EDIT_FRAGMENT");
                break;
            case VIEW:
                ViewFragment viewFragment = new ViewFragment();
                setTitle(R.string.viewfragmentjudul);
                fragmentTransaction.add(R.id.kegiatan_container, viewFragment, "KEGIATAN_VIEW_FRAGMENT");
                break;
            case TAMBAH:
                EditFragment tambahFragment = new EditFragment();
                setTitle(R.string.tambahfragmentJudul);

                Bundle bundle = new Bundle();
                bundle.putBoolean(KEGIATAN_BARU_EXTRA, true);
                tambahFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.kegiatan_container, tambahFragment, "KEGIATAN_TAMBAH_FRAGMENT");
                break;
        }

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }
}
