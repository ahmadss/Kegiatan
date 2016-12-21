package com.catatankegiatan.kegiatan;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditFragment extends Fragment {

    private EditText judul, pesan;
    private int kat2;
    private int kat = 0;
    private ImageView imageView;
    private Catatan.Kategori simpanGbrKategori;
    private AlertDialog kategoriDialogObjek, confirmDialogObjek;
    private static final String MODIFIKASI_KATEGORI="modifikasiKategori";
    private boolean kegiatanBaru = false;
    private long kegiatanId = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            kegiatanBaru = bundle.getBoolean(DetailActivity.KEGIATAN_BARU_EXTRA, false);
        }

        if (savedInstanceState != null) {
            simpanGbrKategori = (Catatan.Kategori) savedInstanceState.get(MODIFIKASI_KATEGORI);
            kat = savedInstanceState.getInt("kat");
        }
        View fragmentLayout = inflater.inflate(R.layout.fragment_edit, container, false);
        judul = (EditText)fragmentLayout.findViewById(R.id.editKgtJudul);
        pesan = (EditText)fragmentLayout.findViewById(R.id.editKgtkegiatan);
        imageView = (ImageView)fragmentLayout.findViewById(R.id.editKgticon);
        Button btn = (Button)fragmentLayout.findViewById(R.id.simpanKegiatan);

        Intent intent = getActivity().getIntent();
        judul.setText(intent.getExtras().getString(MainActivity.CATATAN_JUDUL_EXTRA));
        pesan.setText(intent.getExtras().getString(MainActivity.CATATAN_KETERANGAN_EXTRA));
        kegiatanId = intent.getExtras().getLong(MainActivity.CATATAN_ID_EXTRA, 0);

        if (simpanGbrKategori != null) {
            imageView.setImageResource(Catatan.kategoriKeGambar(simpanGbrKategori));
        } else if (!kegiatanBaru){
            Catatan.Kategori catatan = (Catatan.Kategori)intent.getSerializableExtra(MainActivity.CATATAN_KATEGORI_EXTRA);
            simpanGbrKategori = catatan;
            imageView.setImageResource(Catatan.kategoriKeGambar(catatan));
        }

        buildKategoriDialog();
        buildKonfirmDialog();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialogObjek.show();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kategoriDialogObjek.show();
            }
        });
        // Inflate the layout for this fragment
        return fragmentLayout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(MODIFIKASI_KATEGORI, simpanGbrKategori);
        outState.putInt("kat",kat);
    }

    private void buildKategoriDialog(){
        final String[] kategories = new String[]{"KERJA","RAPAT","SHOLAT","TIDUR"};
        AlertDialog.Builder kategoriBuilder = new AlertDialog.Builder(getActivity());
        if (kat > 0) {
           kat2 = kat;
        } else {
            kat2 = 0;
        }
        kategoriBuilder.setSingleChoiceItems(kategories, kat2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                kategoriDialogObjek.cancel();
                switch (i) {
                    case 0:
                        kat = i;
                        simpanGbrKategori = Catatan.Kategori.KERJA;
                        imageView.setImageResource(R.drawable.k);
                        break;
                    case 1:
                        kat = i;
                        simpanGbrKategori = Catatan.Kategori.RAPAT;
                        imageView.setImageResource(R.drawable.r);
                        break;
                    case 2:
                        kat = i;
                        simpanGbrKategori = Catatan.Kategori.SHOLAT;
                        imageView.setImageResource(R.drawable.s);
                        break;
                    case 3:
                        kat = i;
                        simpanGbrKategori = Catatan.Kategori.TIDUR;
                        imageView.setImageResource(R.drawable.t);
                        break;
                }
            }
        });
        kategoriDialogObjek = kategoriBuilder.create();
    }

    private void buildKonfirmDialog(){
        AlertDialog.Builder confirmDialog = new AlertDialog.Builder(getActivity());
        confirmDialog.setTitle("Apakah Anda Yakin?");
        confirmDialog.setMessage("apakah anda yakin mau simpan kegiatan?");

        confirmDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("simpan kegiatan", "onClick: okee"+judul.getText()+ " pesan: "+pesan.getText()+" Kegiatan Kategori: "+simpanGbrKategori);
                KegiatanDbAdapter dbAdapter = new KegiatanDbAdapter(getActivity().getBaseContext());
                dbAdapter.open();
                if (kegiatanBaru) {
                    dbAdapter.createKegiatan(judul.getText()+ "",pesan.getText()+"", (simpanGbrKategori == null)?Catatan.Kategori.KERJA : simpanGbrKategori);
                } else {
                    dbAdapter.updateKegiatan(kegiatanId,judul.getText() +"",pesan.getText()+"",simpanGbrKategori);
                }

                dbAdapter.close();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        confirmDialog.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        confirmDialogObjek = confirmDialog.create();

    }

}
