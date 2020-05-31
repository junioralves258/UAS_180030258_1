package com.aa183.alves;

import java.util.Date;

public class Film {

    private int idFilm;
    private String judul;
    private Date tanggal;
    private String gambar;
    private String sutradara;
    private String sinopsis;
    private String link;

    public Film(int idFilm, String judul, Date tanggal, String gambar, String sutradara, String sinopsis, String link) {
        this.idFilm = idFilm;
        this.judul = judul;
        this.tanggal = tanggal;
        this.gambar = gambar;
        this.sutradara = sutradara;
        this.sinopsis = sinopsis;
        this.link = link;
    }

    public int getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(int idFilm) {
        this.idFilm = idFilm;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getSutradara() {
        return sutradara;
    }

    public void setSutradara(String Sutradara) {
        this.sutradara = sutradara;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String Sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
