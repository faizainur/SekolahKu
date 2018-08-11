package com.jurnalit.sekolahku;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jurnalit.sekolahku.adapter.StudentItemAdapter;
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
//    ArrayList<Student> students = new ArrayList<>();


    ListView listView;
    long id;
    android.support.v7.widget.SearchView searchView;
    android.support.v7.widget.SearchView searchView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Casting view
        listView = findViewById(R.id.lv_students);
        searchView = findViewById(R.id.search_view);
        searchView2 = findViewById(R.id.menu_search);
        registerForContextMenu(listView);

        dataSource.open();
        studentList = dataSource.getAllStudent();
        Log.d("ListActivity", "studentList " + studentList.size());
        getData();
        dataSource.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView2 = (android.support.v7.widget.SearchView) menu.findItem(R.id.menu_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView2.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
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
                if (clearData()){
                    Toast.makeText(this, "Clear Data Successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Unable Clear Data", Toast.LENGTH_LONG).show();
                }
                dataSource.close();
                break;
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
                Toast.makeText(this, "Context Menu Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_context_edit :
                Toast.makeText(this, "Edit Context Menu Selected", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(menuItem);
    }
    private void getData(){
        studentList = dataSource.getAllStudent();

        // Changed to constant variable
        final StudentItemAdapter adapter = new StudentItemAdapter(ListActivity.this, studentList);

        // region listView OnClickListener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                id = adapter.getItemId(position);
                // Parsing id from long to integer caused by the result of getItemId() method is a long data
                // and the requirement for get method is an integer data
                Integer i = (int) (long) id;
                student = studentList.get(i);
//                Log.d("ListActivity", "id = " + id);
                intent.putExtra("id", student.getId());
                startActivity(intent);
            }
        });
        // endregion

        // region SearchView ActionBar
        Intent intent  = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d("ListActivity", "The keyword is " + query);
            doMySearch(query);}
        // endregion
        // region SearchView method
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
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
//                Toast.makeText(ListActivity.this, "S " + newText, Toast.LENGTH_SHORT).show();
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
        });
        // endregion

        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }
    private void doMySearch(String keyword){
        Toast.makeText(this, "The keyword is " + keyword, Toast.LENGTH_SHORT).show();
        studentList.clear();
        List<Student> students =  dataSource.search(keyword);
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            studentList.add(student);
        }
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

}

