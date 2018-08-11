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
import com.jurnalit.sekolahku.adapter.StudentItemAdapter;
import com.jurnalit.sekolahku.database.StudentDataSource;
import com.jurnalit.sekolahku.model.Student;

import java.util.List;


public class ResultActivity extends AppCompatActivity {
    StudentDataSource dataSource = new StudentDataSource(this);
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        listView = findViewById(R.id.list_view_result);
        handleIntent(getIntent());
    }
    private void doMySearch(String keyword){
        List<Student> studentList =  dataSource.search(keyword);
        StudentItemAdapter adapter = new StudentItemAdapter(this, studentList);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent){
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            CharSequence title = "Searching for " + "'" + query + "'";
            setTitle(title);
            dataSource.open();
            doMySearch(query);
            dataSource.close();
        }
    }
}

