package com.example.projectsatrio;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectsatrio.DBHelper;
import com.example.projectsatrio.R;
import com.example.projectsatrio.TopupCursorAdapter;

public class HistoryActivity extends AppCompatActivity implements TopupCursorAdapter.OnTopupClickListener {

    private DBHelper dbHelper;
    private RecyclerView recyclerView;
    private TopupCursorAdapter adapter;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        dbHelper = new DBHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cursor = dbHelper.getAllTopupData();
        adapter = new TopupCursorAdapter(cursor, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onTopupClick(int position) {
        cursor.moveToPosition(position);
        final int topupId = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_IDUANG));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Action");
        String[] options = {"Edit", "Delete"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    // Edit option clicked
                    editTopup(topupId);
                } else if (which == 1) {
                    // Delete option clicked
                    deleteTopup(topupId);
                }
            }
        });
        builder.create().show();
    }

    private void editTopup(final int topupId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Mata Uang");

        // Set up the input
        final EditText inputMataUang = new EditText(this);
        inputMataUang.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_MATAUANG)));
        builder.setView(inputMataUang);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newMataUang = inputMataUang.getText().toString().trim();
                if (!newMataUang.isEmpty()) {
                    dbHelper.editTopup(topupId, newMataUang, cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_KETERANGAN)));
                    refreshCursor();
                } else {
                    Toast.makeText(HistoryActivity.this, "Mata Uang cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void deleteTopup(final int topupId) {
        dbHelper.deleteTopup(topupId);
        refreshCursor();
    }

    private void refreshCursor() {
        cursor = dbHelper.getAllTopupData();
        adapter.swapCursor(cursor);
    }
}
