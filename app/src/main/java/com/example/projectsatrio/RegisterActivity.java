package com.example.projectsatrio;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        dbHelper = new DBHelper(this);

        editTextUsername = findViewById(R.id.username);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextConfirmPassword = findViewById(R.id.confirmpassword);
        Button registerButton = findViewById(R.id.register);

        registerButton.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString();
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();
            String confirmPassword = editTextConfirmPassword.getText().toString();

            if (!password.equals(confirmPassword)) {
                Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save user data into the database
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DBHelper.COLUMN_USERNAME, username);
            values.put(DBHelper.COLUMN_EMAIL, email);
            values.put(DBHelper.COLUMN_PASSWORD, password);
            db.insert(DBHelper.TABLE_USERS, null, values);
            db.close();

            Toast.makeText(RegisterActivity.this, "Registrasi Akun Berhasil", Toast.LENGTH_SHORT).show();
            Intent intent2 = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent2);
            finish(); // Close Login activity
        });

    }
}