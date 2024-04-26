package com.example.projectsatrio;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class TopupActivity extends AppCompatActivity {
    private EditText inputMataUang, inputKeterangan;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topup);

        dbHelper = new DBHelper(this);

        inputMataUang = findViewById(R.id.matauang);
        inputKeterangan = findViewById(R.id.keterangan);

        Button topupButton = findViewById(R.id.topup1);
        topupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mataUang = inputMataUang.getText().toString();
                String keterangan = inputKeterangan.getText().toString();

                dbHelper.insertData(mataUang, keterangan);

                Intent intent = new Intent(TopupActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();

                // Read data after insertion
                Cursor cursor = dbHelper.getAllTopupData();
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        String retrievedMataUang = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_MATAUANG));
                        String retrievedKeterangan = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_KETERANGAN));
                        Log.d("TopupActivity", "Mata Uang: " + retrievedMataUang + ", Keterangan: " + retrievedKeterangan);
                    } while (cursor.moveToNext());
                    cursor.close();
                } else {
                    Log.d("TopupActivity", "No data found in matauang table");
                }
            }
        });
    }
}
