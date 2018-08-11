package com.jurnalit.sekolahku;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;
import android.widget.Toast;
//import android.support.v7.app.ActionBar;
import com.jurnalit.sekolahku.adapter.StudentItemAdapter;
import com.jurnalit.sekolahku.database.StudentDataSource;
import com.jurnalit.sekolahku.model.Student;

import java.util.ArrayList;
import java.util.List;


public class ResultActivity extends AppCompatActivity {
    ArrayList<Student> students = new ArrayList<>();
    Student student = new Student();
    StudentDataSource dataSource = new StudentDataSource(this);
    SearchView searchView2;

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        listView = findViewById(R.id.list_view_result);
        Intent intent = getIntent();
//        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//            String query = intent.getStringExtra(SearchManager.QUERY);
//             }
        handleIntent(intent);

    }
    private void doMySearch(String keyword){
        Toast.makeText(this, "The keyword is " + keyword, Toast.LENGTH_SHORT).show();
        students.clear();
        List<Student> studentList =  dataSource.search(keyword);
        for (int i = 0; i < studentList.size(); i++) {
            Student student = studentList.get(i);
            students.add(student);
        }
        StudentItemAdapter adapter = new StudentItemAdapter(this, students);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView2 = (android.support.v7.widget.SearchView) menu.findItem(R.id.menu_search).getActionView();
//         Assumes current activity is the searchable activity
        searchView2.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return super.onCreateOptionsMenu(menu);
    }
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d("ListActivity", "The keyword is " + query);
            doMySearch(query);}
    }
}
