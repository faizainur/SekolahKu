package com.jurnalit.sekolahku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.jurnalit.sekolahku.database.StudentDataSource;
import com.jurnalit.sekolahku.model.Student;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        long id  = getIntent().getLongExtra("id", 0);
        Log.d("DetailActivity", "id = " + id);

        TextView etNama = findViewById(R.id.et_nama_detail);
        TextView etGender = findViewById(R.id.et_gender_detail);
        TextView etNohp = findViewById(R.id.et_nohp_detail);
        TextView etJenjang = findViewById(R.id.et_jenjang_detail);
        TextView etAlamat = findViewById(R.id.et_alamat_detail);
        TextView etHobi = findViewById(R.id.et_hobi_detail);

        StudentDataSource dataSource = new StudentDataSource(this);

        if (id > 0){
            dataSource.open();
            Student student = dataSource.getStudent(id);
            dataSource.close();

            setTitle(student.getNamaDepan());
            etNama.setText(student.getNamaDepan() + " " + student.getNamaBelakang());
            etGender.setText(student.getGender());
            etHobi.setText(student.getHobi());
            etNohp.setText(student.getNoHp());
            etJenjang.setText(student.getJenjang());
            etAlamat.setText(student.getAlamat());
        }


    }
}
