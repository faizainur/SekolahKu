package com.jurnalit.sekolahku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
    long id;
    ArrayList<Student> students = new ArrayList<>();


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
                break;
            case R.id.menu_delete_all_data :
                dataSource.open();
                clearData();
//                if (getSuccesDelete){
//                    Toast.makeText(this, "Clear Data Successfully", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(this, "Unable Clear Data", Toast.LENGTH_LONG).show();
//                }
                dataSource.close();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    private void getData(){
        List<Student>studentList = dataSource.getAllStudent();
//        ArrayList<Student> students2 = new ArrayList<>();


        for (int i = 0; i < studentList.size(); i++){
            student = studentList.get(i);
            // region UNNECESSERY CODE HERE
            // Writen in the course documentation (ppt file)
//            studentData.add(student.getNamaDepan() + " " + student.getNamaBelakang());
//            studentData.add(student.getGender());
//            studentData.add(student.getNoHp());
//            studentData.add(student.getJenjang());
            // endregion
            students.add(student);
        }
        // Changed to constant variable

        final StudentItemAdapter adapter = new StudentItemAdapter(ListActivity.this, students);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                id = adapter.getItemId(position);
                // Parsing id from long to integer caused by the result of getItemId() method is a long data
                // and the requirement for get method is an integer data
                Integer i = (int) (long) id;
                student = students.get(i);
//                Log.d("ListActivity", "id = " + id);
                intent.putExtra("id", student.getId());
                startActivity(intent);
            }
        });


        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(this);
    }
    private void clearData(){
        dataSource.deleteAllData();
        students.clear();
        dataSource.open();
        studentList = dataSource.getAllStudent();
        Log.d("ListActivity", "studentList " + studentList.size());
        getData();
        dataSource.close();

    }
//    public void studentItemsClicked(){
        // TODO
//    }
}

