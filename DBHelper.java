package com.example.datavault;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "StudentDB";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE students (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "course TEXT, " +
                "marks INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS students");
        onCreate(db);
    }

    // INSERT
    public boolean insertStudent(String name, String course, int marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("name", name);
        cv.put("course", course);
        cv.put("marks", marks);

        long result = db.insert("students", null, cv);
        return result != -1;
    }

    // VIEW ALL
    public Cursor getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM students", null);
    }

    // UPDATE
    public boolean updateStudent(int id, String name, String course, int marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("name", name);
        cv.put("course", course);
        cv.put("marks", marks);

        int result = db.update("students", cv, "id=?", 
                new String[]{String.valueOf(id)});

        return result > 0;
    }

    // DELETE
    public boolean deleteStudent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("students", "id=?", 
                new String[]{String.valueOf(id)});
        return result > 0;
    }

    // QUERY 1: Top Students (Marks > 80)
    public Cursor getTopStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM students WHERE marks > 80", null);
    }

    // QUERY 2: Count Students
    public Cursor getStudentCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT COUNT(*) FROM students", null);
    }
}
