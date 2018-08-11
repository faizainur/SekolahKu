package com.jurnalit.sekolahku;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import com.jurnalit.sekolahku.database.StudentDataSource;
import com.jurnalit.sekolahku.model.Student;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


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
    EditText etTanggalLahir;
    EditText etEmail;
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
    String tanggalLahir;
    String email;
    int mYear, mMonth, mDay;
    Calendar c;
    // endregion
    List<Integer> angka = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_actvity);

        // region Casting Variable View
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
        etTanggalLahir = findViewById(R.id.et_date_picker);
        etEmail = findViewById(R.id.et_email);
        c = Calendar.getInstance();
        // endregion
        datePickMethod();


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
        switch (item.getItemId()) {
            // Memanggil id pada menu menggunakan switch case statement
            case R.id.action_simpan:
                simpan();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void simpan() {
        namaDepan = etNamaDepan.getText().toString().trim();
        namaBelakang = etNamaBelakang.getText().toString().trim();
        noHP = etNoHP.getText().toString().trim();
        tanggalLahir = etTanggalLahir.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        if (rbPria.isChecked()) {
            gender = rbPria.getText().toString().trim();
        } else if (rbWanita.isChecked()) {
            gender = rbWanita.getText().toString().trim();
        }
        if (cbMembaca.isChecked()) {
            hobi += "Membaca ";
        }
        if (cbMenulis.isChecked()) {
            hobi += "Menulis ";
        }
        if (cbMenggambar.isChecked()) {
            hobi += "Menggambar ";
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
            student.setEmail(email);
            student.setTanggalLahir(tanggalLahir);

            dataSource.open();
            // Eksekusi perintah addStudent
            boolean getSuccess = dataSource.addStudent(student);
            // Menutup koneksi Database
            dataSource.close();
            if (getSuccess) {
                Toast.makeText(this, "Data Berhasil Tersimpan", Toast.LENGTH_SHORT).show();
                // Mengosongkan semua field
                etNamaDepan.setText("");
                etNamaBelakang.setText("");
                etAlamat.setText("");
                etNoHP.setText("");
                etEmail.setText("");
                etTanggalLahir.setText("");
                rbWanita.setChecked(false);
                rbPria.setChecked(false);
                cbMenulis.setChecked(false);
                cbMenggambar.setChecked(false);
                cbMembaca.setChecked(false);
                finish();
            } else {
                Toast.makeText(this, "Data Gagal Tersimpan", Toast.LENGTH_SHORT).show();
                // Mengosongkan semua field
                etNamaDepan.setText("");
                etNamaBelakang.setText("");
                etAlamat.setText("");
                etNoHP.setText("");
                etEmail.setText("");
                etTanggalLahir.setText("");
                rbWanita.setChecked(false);
                rbPria.setChecked(false);
                cbMenulis.setChecked(false);
                cbMenggambar.setChecked(false);
                cbMembaca.setChecked(false);
            }
        }
    }

    private boolean validateForm() {
        if (namaDepan.isEmpty()) {
            etNamaDepan.setError("This field is required");
            etNamaDepan.requestFocus();
            return false;
        } else if (namaBelakang.isEmpty()) {
            etNamaBelakang.setError("This field is required");
            etNamaDepan.requestFocus();
            return false;
        } else if (noHP.isEmpty()) {
            etNoHP.setError("This field is required");
            etNamaDepan.requestFocus();
            return false;
        } else if (noHP.length() > 12) {
            etNoHP.setError("Max 12 digit");
            etNoHP.setText("");
            etNoHP.requestFocus();
            return false;
        } else if (alamat.isEmpty()) {
            etAlamat.setError("This field is required");
            etAlamat.requestFocus();
            return false;
        } else if (gender.isEmpty()) {
            Toast.makeText(FormActvity.this, "This field must be chosen", Toast.LENGTH_SHORT).show();
            return false;
        } else if (spinnerPendidikan.getSelectedItemPosition() == 0) {
            Toast.makeText(FormActvity.this, "This field must be chosen", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isValidEmail(email)) {
            etEmail.setError("Not a valid email address");
            etEmail.setText("");
            etEmail.requestFocus();
            return false;
        } else if (email.isEmpty()) {
            etEmail.setError("This is required");
            etEmail.setText("");
            etEmail.requestFocus();
        } else if (tanggalLahir.isEmpty()) {
            etTanggalLahir.setError("This field is required");
            etTanggalLahir.setText("");
            etTanggalLahir.requestFocus();
            return false;
        }

        return true;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    // region Fungsi DataPicker
    public void datePickMethod() {

        etTanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // region DatePicker Sumber https://www.journaldev.com/9976/android-date-time-picker-dialog
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(FormActvity.this, onDateSetListener, mYear, mMonth, mDay);
                datePickerDialog.show();
                // endregion
            }
        });
    }

    // endregion
    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            etTanggalLahir.setText(simpleDateFormat.format(c.getTime()));
        }

    };

}
