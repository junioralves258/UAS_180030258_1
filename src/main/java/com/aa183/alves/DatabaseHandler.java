package com.aa183.alves;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "db_filmku";
    private final static String TABLE_FILM = "t_film";
    private final static String KEY_ID_FILM = "ID_Film";
    private final static String KEY_JUDUL = "Judul";
    private final static String KEY_TGL = "Tanggal";
    private final static String KEY_GAMBAR = "Gambar";
    private final static String KEY_SINOPSIS = "Sinopsis";
    private final static String KEY_SUTRADARA = "Sutradara";
    private final static String KEY_LINK = "Link";
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault());
    private Context context;

    public DatabaseHandler (Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_TABLE_FILM = "CREATE TABLE " + TABLE_FILM
                    + "(" + KEY_ID_FILM + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_JUDUL + " TEXT, " + KEY_TGL + " DATE, "
                    + KEY_GAMBAR + " TEXT, " + KEY_SINOPSIS + " TEXT, " + KEY_SUTRADARA + " TEXT, "
                    + KEY_LINK + " TEXT);";

            db.execSQL(CREATE_TABLE_FILM);
            inialisasiFilmAwal(db);

        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_FILM;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void tambahFilm (Film dataFilm){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_SINOPSIS, dataFilm.getSinopsis());
        cv.put(KEY_SUTRADARA, dataFilm.getSutradara());
        cv.put(KEY_LINK, dataFilm.getLink());

        db.insert(TABLE_FILM, null, cv);
        db.close();
    }

    public void tambahFilm (Film dataFilm, SQLiteDatabase db){
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_SINOPSIS, dataFilm.getSinopsis());
        cv.put(KEY_SUTRADARA, dataFilm.getSutradara());
        cv.put(KEY_LINK, dataFilm.getLink());

        db.insert(TABLE_FILM, null, cv);
    }

    public void editFilm (Film dataFilm){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_ID_FILM, dataFilm.getIdFilm());
        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_SINOPSIS, dataFilm.getSinopsis());
        cv.put(KEY_SUTRADARA, dataFilm.getSutradara());
        cv.put(KEY_LINK, dataFilm.getLink());

        db.update(TABLE_FILM, cv, KEY_ID_FILM + "=?", new String[]{String.valueOf(dataFilm.getIdFilm())});
        db.close();
    }

    public void hapusFilm (int idFilm){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_FILM, KEY_ID_FILM + "=?", new String[]{String.valueOf(idFilm)});
        db.close();
    }

    public ArrayList<Film> getAllFilm(){
        ArrayList<Film> dataFilm = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_FILM;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr =  db.rawQuery(query, null);
        if (csr.moveToFirst()){
            do {
                Date tempDate = new Date();
                try {
                    tempDate = sdFormat.parse(csr.getString(2));
                } catch (ParseException er){
                    er.printStackTrace();
                }

                Film tempFilm = new Film(
                        csr.getInt ( 0),
                        csr.getString( 1),
                        tempDate,
                        csr.getString( 3),
                        csr.getString( 4),
                        csr.getString( 5),
                        csr.getString( 6)
                );

                dataFilm.add(tempFilm);
            } while (csr.moveToNext());
        }
        return dataFilm;
    }
    private String storeImagesFiles(int id){
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private void inialisasiFilmAwal(SQLiteDatabase db) {
        int idFilm = 0;
        Date tempDate = new Date();

        try {
            tempDate = sdFormat.parse("24/04/2019 00:00");
        } catch (ParseException er) {
            er.printStackTrace();
        }



        Film film1 = new Film(
                idFilm,
                "Avenger End Game",
                tempDate,
                storeImagesFiles(R.drawable.film1),
                "Melanjutkan Avengers Infinity War, dimana kejadian setelah Thanos berhasil mendapatkan semua infinity stones dan memusnahkan 50% semua mahluk hidup di alam semesta. Akankah para Avengers berhasil mengalahkan Thanos?",
                "Joe Russo, Anthony Russo",
                "https://www.youtube.com/watch?v=hA6hldpSTF8&t=2s"
        );

        tambahFilm(film1, db);
        idFilm++;

        try {
            tempDate = sdFormat.parse("28/05/2018 00:00");
        }catch (ParseException er){
            er.printStackTrace();
        }

        Film Film2 = new Film(
                idFilm,
                "Edge of Tomorrow",
                tempDate,
                storeImagesFiles(R.drawable.film2),
                "Dengan bantuan prajurit Rita Vrataski Mayor William Cage harus menyelamatkan Planet Bumi dari makhluk asing, setelah terjebak dalam lingkaran waktu. Apakah ia memiliki keahlian untuk membasmi alien?",
                "Doug Liman",
                "https://www.youtube.com/watch?v=vw61gCe2oqI"

        );

        tambahFilm(Film2, db);
        idFilm++;

        try {
            tempDate = sdFormat.parse("21/06/2019 00:00");
        }catch (ParseException er){
            er.printStackTrace();
        }

        Film Film3 = new Film(
                idFilm,
                "Parasite",
                tempDate,
                storeImagesFiles(R.drawable.film3),
                "Keluarga Ki-taek beranggotakan empat orang pengangguran dengan masa depan suram menanti mereka. Suatu hari Ki-woo anak laki-laki tertua direkomendasikan oleh sahabatnya yang merupakan seorang mahasiswa dari universitas bergengsi agar Ki-woo menjadi guru les yang dibayar mahal dan membuka secercah harapan penghasilan tetap. Dengan penuh restu serta harapan besar dari keluarga, Ki-woo menuju ke rumah keluarga Park untuk wawancara. Setibanya di rumah Mr. Park pemilik perusahaan IT global, Ki-woo bertemu dengan Yeon-kyo, wanita muda yang cantik di rumah itu. Setelah pertemuan itu, serangkaian kejadian dimulai.",
                "Bong Joon-ho",
                "https://www.youtube.com/watch?v=5xH0HfJHsaY"
        );

        tambahFilm(Film3, db);
        idFilm++;

        try {
            tempDate = sdFormat.parse("17/11/2017 00:00");
        }catch (ParseException er){
            er.printStackTrace();
        }

        Film Film4 = new Film(
                idFilm,
                "Justice League",
                tempDate,
                storeImagesFiles(R.drawable.film4),
                "Dipicu oleh kepercayaannya terhadap kemanusiaan dan terinspirasi oleh tindakan tanpa pamrih Superman (Henry Cavill), Bruce Wayne (Ben Affleck) mengumpulkan sekutu baru Diana Prince untuk menghadapi ancaman yang lebih besar lagi. Bersama-sama, Batman dan Wonder Woman bekerja sama untuk merekrut tim untuk melawan musuh yang baru dibangun ini. Meskipun terbentuknya liga pahlawan yang belum pernah terjadi sebelumnya - Batman, Wonder Woman, Aquaman, Cyborg dan Flash - mungkin sudah terlambat untuk menyelamatkan planet ini dari serangan dengan proporsi bencana.",
                "Zack Snyder, Joss Whedon",
                "https://www.youtube.com/watch?v=3cxixDgHUYw"
        );

        tambahFilm(Film4, db);
    }

}
