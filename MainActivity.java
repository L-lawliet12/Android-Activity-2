package com.example.datavault;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etId, etName, etCourse, etMarks;
    Button btnInsert, btnUpdate, btnDelete, btnView;
    TextView tvResult;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etId = findViewById(R.id.etId);
        etName = findViewById(R.id.etName);
        etCourse = findViewById(R.id.etCourse);
        etMarks = findViewById(R.id.etMarks);

        btnInsert = findViewById(R.id.btnInsert);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnView = findViewById(R.id.btnView);

        tvResult = findViewById(R.id.tvResult);

        dbHelper = new DBHelper(this);

        // INSERT
        btnInsert.setOnClickListener(v -> {

            String name = etName.getText().toString();
            String course = etCourse.getText().toString();
            int marks = Integer.parseInt(etMarks.getText().toString());

            boolean result = dbHelper.insertStudent(name, course, marks);

            if(result){
                tvResult.setText("Inserted:\n" +
                        "Name: " + name +
                        "\nCourse: " + course +
                        "\nMarks: " + marks);
            } else {
                Toast.makeText(this, "Insert Failed", Toast.LENGTH_SHORT).show();
            }
        });

        // UPDATE
        btnUpdate.setOnClickListener(v -> {

            int id = Integer.parseInt(etId.getText().toString());
            String name = etName.getText().toString();
            String course = etCourse.getText().toString();
            int marks = Integer.parseInt(etMarks.getText().toString());

            boolean result = dbHelper.updateStudent(id, name, course, marks);

            if(result)
                Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
        });

        // DELETE
        btnDelete.setOnClickListener(v -> {

            int id = Integer.parseInt(etId.getText().toString());
            boolean result = dbHelper.deleteStudent(id);

            if(result)
                Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Delete Failed", Toast.LENGTH_SHORT).show();
        });

        // VIEW ALL
        btnView.setOnClickListener(v -> {

            Cursor cursor = dbHelper.getAllStudents();
            String data = "";

            while(cursor.moveToNext()){
                data = data +
                        "ID: " + cursor.getInt(0) + "\n" +
                        "Name: " + cursor.getString(1) + "\n" +
                        "Course: " + cursor.getString(2) + "\n" +
                        "Marks: " + cursor.getInt(3) + "\n\n";
            }

            tvResult.setText(data);
        });
    }
}
