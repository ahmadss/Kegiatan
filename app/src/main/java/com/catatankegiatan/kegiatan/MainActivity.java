package com.catatankegiatan.kegiatan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    public static final String CATATAN_ID_EXTRA = "com.catatankegiatan.kegiatan.Identifier";
    public static final String CATATAN_JUDUL_EXTRA = "com.catatankegiatan.kegiatan.Judul";
    public static final String CATATAN_KETERANGAN_EXTRA = "com.catatankegiatan.kegiatan.Keterangan";
    public static final String CATATAN_KATEGORI_EXTRA = "com.catatankegiatan.kegiatan.Kategori";
    public static final String KEGIATAN_FRAGMENT_TO_LOAD_EXTRA = "com..catatankegiatan.kegiatan.Fragment_To_Load";

    public enum FragmentToLaunch{VIEW, EDIT, TAMBAH};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadPreferences();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, Apppreferences.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.tambah) {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra(MainActivity.KEGIATAN_FRAGMENT_TO_LOAD_EXTRA, FragmentToLaunch.TAMBAH);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadPreferences(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        boolean isBackground = sharedPreferences.getBoolean("background_color", false);
        if (isBackground){
            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.mainLayout);
            linearLayout.setBackgroundColor(Color.parseColor("#3f3f3f"));
        }

        String kegiatanJudul = sharedPreferences.getString("judul","Buku Kegiatan");
        setTitle(kegiatanJudul);
    }
}
