package com.jurnalit.sekolahku;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jurnalit.sekolahku.adapter.StudentItemAdapter;
import com.jurnalit.sekolahku.database.StudentDataSource;
import com.jurnalit.sekolahku.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    // Deklarasi class StudentDatasource untuk mengakses CRUD yang telah dibuat
    StudentDataSource dataSource = new StudentDataSource(this);
    // Deklarasi class Student untuk mengakses setter dan getter
    Student student = new Student();
    // Deklarasi List<Student> untuk memgang value array dari database
    List<Student> studentList = new ArrayList<>();


    ListView listView;
    StudentItemAdapter adapter;
    long id;
    android.support.v7.widget.SearchView searchView;
    android.support.v7.widget.SearchView searchView2;

    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        preferences = getSharedPreferences("loginStatus", MODE_PRIVATE);

        // Casting view
        listView = findViewById(R.id.lv_students);
        searchView2 = findViewById(R.id.search_student);
//        searchView = findViewById(R.id.menu_search);
        registerForContextMenu(listView);
    }

    @Override
    protected void onResume() {
        dataSource.open();
        studentList = dataSource.getAllStudent();
        Log.d("ListActivity", "studentList " + studentList.size());
        getData();
        searchView2.setOnQueryTextListener(searchData);
        listView.setOnItemClickListener(studentItemClicked);
        dataSource.close();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
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
                if (clearData()){
                    Toast.makeText(this, "Clear Data Successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Unable to Clear Data", Toast.LENGTH_LONG).show();
                }
                dataSource.close();
                break;
            case R.id.menu_logout :
                preferences = getSharedPreferences("loginStatus", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.apply();
                boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);
                if (isLoggedIn){
                    Toast.makeText(this, "Failed to logout", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show();
                    finish();
                }

                break;
//            case R.id.menu_search :
//                onSearchRequested();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo){
        getMenuInflater().inflate(R.menu.menu_context, menu);
        super.onCreateContextMenu(menu, view, contextMenuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem menuItem){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo();
        student = studentList.get(menuInfo.position);
        switch (menuItem.getItemId()){
            case R.id.menu_context_delete :
                dataSource.open();
                if (dataSource.removeData(student)){
                    studentList.remove(student);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(this, "Delete Data Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Error Deleting Data", Toast.LENGTH_SHORT).show();
                }
                dataSource.close();
                break;
            case R.id.menu_context_edit :
                Intent intent = new Intent(getApplicationContext(), FormActvity.class);
                if (student != null) {
                    intent.putExtra("id", student.getId());
                    Log.d("Edit", "Student id is " + student.getId());
                }
                startActivity(intent);
                break;
        }
        return super.onContextItemSelected(menuItem);
    }
    public void getData(){
        studentList = dataSource.getAllStudent();

        // Parsing int ke string
        // String.valueOf(int value)

        // Melakukan sortir studentList dengan tipe ascending
        Collections.sort(studentList, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                String proggress1 = o1.getNamaDepan();
                String proggress2 = o2.getNamaDepan();
                return proggress1.compareTo(proggress2);
            }
        });
        adapter = new StudentItemAdapter(ListActivity.this, studentList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private boolean clearData(){
        dataSource.deleteAllData();
        studentList.clear();
        getData();
        if (studentList.size() == 0){
            return true;
        }
        return false;
    }

    private SearchView.OnQueryTextListener searchData = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            Toast.makeText(ListActivity.this, "Searching for "+ "'" + query + "'", Toast.LENGTH_SHORT).show();
            dataSource.open();
            studentList.clear();
            List<Student> students = dataSource.search(query);

            for (int i = 0; i < students.size(); i++){
                Student student = students.get(i);
                studentList.add(student);
            }
            adapter.notifyDataSetChanged();
            dataSource.close();
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            dataSource.open();
            studentList.clear();
            List<Student> students = dataSource.search(newText);

            for (int i = 0; i < students.size(); i++){
                Student student = students.get(i);
                studentList.add(student);
            }
            adapter.notifyDataSetChanged();
            dataSource.close();
            return false;
        }
    };
    private ListView.OnItemClickListener studentItemClicked = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            Student student = adapter.getItem(position);
            intent.putExtra("id", student.getId());
            startActivity(intent);
        }
    };
}

