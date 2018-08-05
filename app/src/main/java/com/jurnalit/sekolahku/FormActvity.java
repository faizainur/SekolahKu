package com.jurnalit.sekolahku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.jurnalit.sekolahku.database.StudentDataSource;
import com.jurnalit.sekolahku.model.Student;

import java.nio.charset.CharacterCodingException;
import java.util.ArrayList;
import java.util.List;


public class FormActvity extends AppCompatActivity {

    // Deklarasi untuk memanggil metod CRUD yang telah dibuat
    StudentDataSource dataSource = new StudentDataSource(this);
    // Deklarasi Student untuk menggunakan setter getter pada java
    // Class POJO
    Student student = new Student();
    // region Deklarasi
    Button btnSimpan;
    EditText etNamaDepan;
    EditText etNamaBelakang;
    EditText etNoHP;
    EditText etAlamat;
    RadioButton rbPria;
    RadioButton rbWanita;
    Spinner spinnerPendidikan;
    CheckBox cbMembaca;
    CheckBox cbMenulis;
    CheckBox cbMenggambar;
    String namaDepan;
    String namaBelakang;
    String noHP;
    String alamat;
    String gender = "";
    String hobi = "";
    String jenjang;
    // endregion
    List<Integer> angka = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_actvity);

        // Casting Variable View
//      btnSimpan = findViewById(R.id.btn_simpan);
        etNamaDepan = findViewById(R.id.et_front_name);
        etNamaBelakang = findViewById(R.id.et_back_name);
        etNoHP = findViewById(R.id.et_phone);
        etAlamat = findViewById(R.id.et_alamat);
        rbPria = findViewById(R.id.rb_pria);
        rbWanita = findViewById(R.id.rb_wanita);
        spinnerPendidikan = findViewById(R.id.spinner_jenjang);
        cbMembaca = findViewById(R.id.cb_membaca);
        cbMenggambar = findViewById(R.id.cb_menggambar);
        cbMenulis = findViewById(R.id.cb_menulis);

        // region Method onClickListener untuk membaca button
//        btnSimpan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String namaDepan = etNamaDepan.getText().toString();
//                String namaBelakang = etNamaBelakang.getText().toString();
//                String noHP = etNoHP.getText().toString();
//                String gender;
//                if(rbPria.isChecked()){
//                    gender = rbPria.getText().toString().trim();
//                }else if(rbWanita.isChecked()){
//                    gender = rbWanita.getText().toString().trim();
//                } else{
//                    gender = "Error !!";
//                }
//                String hobi = "";
//                if (cbMembaca.isChecked()){
//                    hobi += "Membaca, ";
//                }
//                if (cbMenggambar.isChecked()){
//                    hobi += "Menggambar, ";
//                }
//                if (cbMenulis.isChecked()){
//                    hobi += "Menulis";
//                }
//                String jenjang;
//                jenjang = spinnerPendidikan.getSelectedItem().toString();
//                String alamat = etAlamat.getText().toString();
//                Toast.makeText(FormActvity.this, "Selamat Datang " + namaDepan + namaBelakang + " !!\n"
//                        + "No HP anda adalah : " + noHP + "\n"
//                        + "Gender : " + gender + "\n"
//                        + "Hobi : " + hobi + "\n"
//                        + "Jenjang : " + jenjang + "\n"
//                        + "Alamat : " + alamat + ".", Toast.LENGTH_LONG).show();
//            }
//        });
        // endregion
    }

    // Memanggil method onCreatOptionsMenu untuk menampilkan menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Membuat menu_form tampil pada FormActivity
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Memanggil methon onOptionsItemSelected untuk membaca pilihan user pada menu serta melakukan action
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            // Memanggil id pada menu menggunakan switch case statement
            case R.id.action_simpan :
                simpan();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void simpan(){
        namaDepan = etNamaDepan.getText().toString().trim();
        namaBelakang = etNamaBelakang.getText().toString().trim();
        noHP = etNoHP.getText().toString().trim();

        if(rbPria.isChecked()){
            gender = rbPria.getText().toString().trim();
        }else if(rbWanita.isChecked()){
            gender = rbWanita.getText().toString().trim();
        }
        if (cbMembaca.isChecked()){
            hobi += "Membaca, ";
        }
        if (cbMenggambar.isChecked()){
            hobi += "Menggambar, ";
        }
        if (cbMenulis.isChecked()){
            hobi += "Menulis";
        }
        jenjang = spinnerPendidikan.getSelectedItem().toString();
        alamat = etAlamat.getText().toString();
        if (validateForm()) {
            // Set data pada class Student ( POJO )
            student.setNamaDepan(namaDepan);
            student.setNamaBelakang(namaBelakang);
            student.setNoHp(noHP);
            student.setGender(gender);
            student.setJenjang(jenjang);
            student.setHobi(hobi);
            student.setAlamat(alamat);

            // Eksekusi perintah addStudent
            boolean getSuccess = dataSource.addStudent(student);
            if (getSuccess){
                Toast.makeText(this, "Data Berhasil Tersimpan", Toast.LENGTH_SHORT).show();
                // Mengosongkan semua field
                etNamaDepan.setText("");
                etNamaBelakang.setText("");
                etAlamat.setText("");
                etNoHP.setText("");
                rbWanita.setChecked(false);
                rbPria.setChecked(false);
                cbMenulis.setChecked(false);
                cbMenggambar.setChecked(false);
                cbMembaca.setChecked(false);
            } else {
                Toast.makeText(this, "Data Gagal Tersimpan", Toast.LENGTH_SHORT).show();
                // Mengosongkan semua field
                etNamaDepan.setText("");
                etNamaBelakang.setText("");
                etAlamat.setText("");
                etNoHP.setText("");
                rbWanita.setChecked(false);
                rbPria.setChecked(false);
                cbMenulis.setChecked(false);
                cbMenggambar.setChecked(false);
                cbMembaca.setChecked(false);
            }
        }
    }
    private boolean validateForm(){
        if (namaDepan.isEmpty()){
            etNamaDepan.setError("This field is required");
            etNamaDepan.requestFocus();
            return false;
        }else if (namaBelakang.isEmpty()){
            etNamaBelakang.setError("This field is required");
            etNamaDepan.requestFocus();
            return false;
        }else if (noHP.isEmpty()){
            etNoHP.setError("This field is required");
            etNamaDepan.requestFocus();
            return false;
        }else if (noHP.length() > 12){
            etNoHP.setError("Max 12 digit");
            etNoHP.setText("");
            etNoHP.requestFocus();
            return false;
        }else if (alamat.isEmpty()){
            etAlamat.setError("This field is required");
            etAlamat.requestFocus();
            return false;
        } else if (gender.isEmpty()){
            Toast.makeText(FormActvity.this, "This field must be chosen", Toast.LENGTH_SHORT).show();
            return false;
        } else if (spinnerPendidikan.getSelectedItemPosition() == 0){
            Toast.makeText(FormActvity.this, "This field must be chosen", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
