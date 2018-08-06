package com.jurnalit.sekolahku.database;

import android.accessibilityservice.GestureDescription;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.jurnalit.sekolahku.model.Student;

import java.util.ArrayList;
import java.util.List;

// Berguna untuk menyimpan semua metode CRUD database untuk table student
// CRUD ( CREATE, READ, UPDATE, DELETE)
public class StudentDataSource {

    // Deklarasi dbHelper untuk memanggil database SekolahKu
    private DbHelper dbHelper;
    // Deklarasi SqliteDatabase untuk koneksi database
    private SQLiteDatabase database;

    // Constructor untuk memberikan parameter yang dibutuhkan untuk dbHelper dari class Activity
    public StudentDataSource(Context context){
        dbHelper = new DbHelper(context);
    }
    // Untuk membuka koneksi dengan database
    public void open(){
        database = dbHelper.getWritableDatabase();
    }
    // Untuk menutup koneksi dengan database
    public void close(){
        database.close();
    }
    // Membuat method CREATE data
    public boolean addStudent(Student student){
        // Menggunakan ContentValues untuk memasukkkan data ke dalam field yang ditentukan
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama_depan", student.getNamaDepan());
        contentValues.put("nama_belakang", student.getNamaBelakang());
        contentValues.put("no_hp", student.getNoHp());
        contentValues.put("gender", student.getGender());
        contentValues.put("jenjang", student.getJenjang());
        contentValues.put("hobi", student.getHobi());
        contentValues.put("alamat", student.getAlamat());
        contentValues.put("email", student.getEmail());
        contentValues.put("tanggal_lahir", student.getTanggalLahir());

        // open();
        // Perintah untuk memasukkan data
        long id = database.insert("student", null, contentValues);
        // close();
        return id > 0;
    }

    public List<Student> getAllStudent(){
        // Deklarasi ArrayList untuk menyimpan value dari student
        List<Student> studentList =  new ArrayList<>();

        // Perintah query untuk mengambil seluruh data di table student
        Cursor cursor = database.rawQuery("SELECT * FROM student", new String[]{});
        // Membuat cursor ada di posisi index pertama
        cursor.moveToFirst();

        // Melakukan perulangan apabila cursor belum mencapai index terakhir
        while (!cursor.isAfterLast()) {
            Student student = fetchRow(cursor);
            // Memasukkan data yang dimiliki POJO ke dalam ArrayList
            studentList.add(student);
            // Memerintahkan cursor untuk naik 1 index /  menuju index berikutnya
            cursor.moveToNext();
        }
        // Menuutup proses cursor
        cursor.close();
        // Mengembalikan nilai ArrayList agar dapat diambil ketika method digunakan
        return studentList;

    }

    // region Added after Sunday, July 5th 2018
    private Student fetchRow (Cursor cursor){
        // Memberikan record yang didapat ke masing-masing POJO
        Student student = new Student();
        student.setId(cursor.getInt(0));
        student.setNamaDepan(cursor.getString(1));
        student.setNamaBelakang(cursor.getString(2));
        student.setNoHp(cursor.getString(3));
        student.setGender(cursor.getString(4));
        student.setJenjang(cursor.getString(5));
        student.setHobi(cursor.getString(6));
        student.setAlamat(cursor.getString(7));
        student.setEmail(cursor.getString(8));
        student.setTanggalLahir(cursor.getString(9));
        return student;
    }
    // endregion
}
