package com.example.projectsatrio;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        dbHelper = new DBHelper(this);

        editTextUsername = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.login);

        TextView registerTextView = findViewById(R.id.daftar);
        registerTextView.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();

            // Check if username and password match in the database
            if (validateUser(username, password)) {
                Toast.makeText(LoginActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                 Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                 startActivity(intent);
                 finish();
            } else {
                Toast.makeText(LoginActivity.this, "Username atau password salah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateUser(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                DBHelper.COLUMN_USERNAME,
                DBHelper.COLUMN_PASSWORD
        };
        String selection = DBHelper.COLUMN_USERNAME + " = ? AND " + DBHelper.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(
                DBHelper.TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }
}
