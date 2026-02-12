package com.example.datavault;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etName, etCourse, etMarks;
    Button btnInsert, btnUpdate, btnDelete, btnView, btnTop, btnCount;
    TextView tvResult;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect XML to Java
        etName = findViewById(R.id.etName);
        etCourse = findViewById(R.id.etCourse);
        etMarks = findViewById(R.id.etMarks);

        btnInsert = findViewById(R.id.btnInsert);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnView = findViewById(R.id.btnView);
        btnTop = findViewById(R.id.btnTop);
        btnCount = findViewById(R.id.btnCount);

        tvResult = findViewById(R.id.tvResult);

        dbHelper = new DBHelper(this);

        // INSERT
        btnInsert.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String course = etCourse.getText().toString();
            int marks = Integer.parseInt(etMarks.getText().toString());

            boolean inserted = dbHelper.insertStudent(name, course, marks);

            if (inserted)
                Toast.makeText(this, "Inserted Successfully", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Insert Failed", Toast.LENGTH_SHORT).show();
        });

        // VIEW ALL
        btnView.setOnClickListener(v -> {
            Cursor cursor = dbHelper.getAllStudents();
            StringBuilder data = new StringBuilder();

            while (cursor.moveToNext()) {
                data.append("ID: ").append(cursor.getInt(0)).append("\n");
                data.append("Name: ").append(cursor.getString(1)).append("\n");
                data.append("Course: ").append(cursor.getString(2)).append("\n");
                data.append("Marks: ").append(cursor.getInt(3)).append("\n\n");
            }

            tvResult.setText(data.toString());
        });

        // DELETE (Enter ID in Marks field to delete)
        btnDelete.setOnClickListener(v -> {
            int id = Integer.parseInt(etMarks.getText().toString());
            boolean deleted = dbHelper.deleteStudent(id);

            if (deleted)
                Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Delete Failed", Toast.LENGTH_SHORT).show();
        });

        // UPDATE (Enter ID in Marks field for update)
        btnUpdate.setOnClickListener(v -> {
            int id = Integer.parseInt(etMarks.getText().toString());
            String name = etName.getText().toString();
            String course = etCourse.getText().toString();
            int marks = Integer.parseInt(etMarks.getText().toString());

            boolean updated = dbHelper.updateStudent(id, name, course, marks);

            if (updated)
                Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
        });

        // TOP STUDENTS (Marks > 80)
        btnTop.setOnClickListener(v -> {
            Cursor cursor = dbHelper.getTopStudents();
            StringBuilder data = new StringBuilder();

            while (cursor.moveToNext()) {
                data.append(cursor.getString(1))
                        .append(" - ")
                        .append(cursor.getInt(3))
                        .append("\n");
            }

            tvResult.setText(data.toString());
        });

        // COUNT STUDENTS
        btnCount.setOnClickListener(v -> {
            Cursor cursor = dbHelper.getStudentCount();

            if (cursor.moveToFirst()) {
                int count = cursor.getInt(0);
                tvResult.setText("Total Students: " + count);
            }
        });
    }
}
