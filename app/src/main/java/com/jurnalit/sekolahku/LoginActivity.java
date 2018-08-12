package com.jurnalit.sekolahku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    //Deklarasi SharedPreference bernama loginStatus untuk menyimpan status login
    SharedPreferences preferences;
    EditText etUsername;
    EditText etPassword;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);

        preferences = getSharedPreferences("loginStatus", MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

        if(isLoggedIn){
            Intent intent = new Intent(getApplicationContext(), ListActivity.class);
            startActivity(intent);
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username;
                String password;

                username = etUsername.getText().toString().trim();
                password = etPassword.getText().toString();

                if (username.equals("admin") && password.equals("admin")){
                    preferences = getSharedPreferences("loginStatus", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();

                    Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
