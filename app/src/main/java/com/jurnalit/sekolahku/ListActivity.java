package com.jurnalit.sekolahku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jurnalit.sekolahku.database.StudentDataSource;
import com.jurnalit.sekolahku.model.Student;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    // Deklarasi class StudentDatasource untuk mengakses CRUD yang telah dibuat
    StudentDataSource dataSource = new StudentDataSource(this);
    // Deklarasi class Student untuk mengakses setter dan getter
    Student student = new Student();
    // Deklarasi List<Student> untuk memgang value array dari database
    List<Student> studentList = new ArrayList<>();
    //Deklarasi view
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Casting list view
        listView = findViewById(R.id.lv_students);
        dataSource.open();
        studentList = dataSource.getAllStudent();
        Log.d("ListActivity", "studentList " + studentList.size());
        getData();
        dataSource.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add :
                Intent intent = new Intent(getApplicationContext(), FormActvity.class);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    private void getData(){
        List<Student>studentList = dataSource.getAllStudent();

        ArrayList<Student> students = new ArrayList<>();
        List<String> studentData = new ArrayList<>();
        for (int i = 0; i < studentList.size(); i++){
            student = studentList.get(i);
            studentData.add(student.getNamaDepan() + " " + student.getNamaBelakang());
            studentData.add(student.getGender());
            studentData.add(student.getNoHp());
            studentData.add(student.getJenjang());
            students.add(student);
        }

        StudentItemAdapter adapter = new StudentItemAdapter(ListActivity.this, students);
        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(this);
    }
}

