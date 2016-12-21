package com.catatankegiatan.kegiatan;

/**
 * Created by ahmad on 27/11/2016.
 */
public class Catatan {
    private String judul, pesan;
    private long catatanId, dateCreatedMilli;
    private Kategori kategori;

    public enum Kategori{ KERJA, RAPAT, SHOLAT, TIDUR }

    public Catatan(String judul, String pesan, Kategori kategori) {
        this.judul = judul;
        this.pesan = pesan;
        this.kategori = kategori;
        this.dateCreatedMilli = 0;
        this.catatanId = 0;
    }

    public Catatan(String judul, String pesan,Kategori kategori, long catatanId, long dateCreatedMilli) {
        this.judul = judul;
        this.pesan = pesan;
        this.catatanId = catatanId;
        this.dateCreatedMilli = dateCreatedMilli;
        this.kategori = kategori;
    }

    public String getJudul() {
        return judul;
    }

    public String getPesan() {
        return pesan;
    }

    public long getId() {
        return catatanId;
    }

    public long getDate() {
        return dateCreatedMilli;
    }

    public Kategori getKategori() {
        return kategori;
    }

    @Override
    public String toString() {
        return "Catatan{" +
                "judul='" + judul + '\'' +
                ", pesan='" + pesan + '\'' +
                ", catatanId=" + catatanId +
                ", dateCreatedMilli=" + dateCreatedMilli +
                ", kategori=" + kategori +
                '}';
    }

    public int getAssosiasiGambar() {
        return kategoriKeGambar(kategori);
    }

    public static int kategoriKeGambar(Kategori catatanKategori) {
        switch (catatanKategori) {
            case KERJA:
                return R.drawable.k;
            case RAPAT:
                return R.drawable.r;
            case SHOLAT:
                return R.drawable.s;
            case TIDUR:
                return R.drawable.t;
        }

        return R.drawable.k;
    }
}
