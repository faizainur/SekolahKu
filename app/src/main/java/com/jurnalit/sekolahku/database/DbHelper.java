package com.jurnalit.sekolahku.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// Membuat class database
public class DbHelper extends SQLiteOpenHelper{

    // Constructor DbHelper ketika ingin memanggil classs dari class lain
    public DbHelper(Context context) {
        // super untuk menentukan nilai awal dari parameter
        super(context, "sekolahku", null, 1);
    }

    // Override method onCreate untuk mengeksekusi perintah query sql kerika apps
    // pertama kali dibuat di smartphone
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE student (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nama_depan TEXT," +
                "nama_belakang TEXT," +
                "no_hp TEXT," +
                "gender TEXT," +
                "jenjang TEXT," +
                "hobi TEXT," +
                "alamat TEXT," +
                "email TEXT," +
                "tanggal_lahir TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Syntax untuk mengupgrade database dengan mengubah versi (version) dari database
        // Setiap penambahan column pada database diikuti dengan menambah column baru pada
        // Method onCreate
//        if (oldVersion < 2){
//            db.execSQL("ALTER TABLE student ADD COLUMN email TEXT");
//            db.execSQL("ALTER TABLE student ADD COLUMN tanggal_lahir");
//        }
    }
}
