package utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by bunny on 26/05/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "student";

    private static final String TABLE_STUDENT = "studentDetail";

    /*SQL Table Columns */
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_AGE = "age";
    private static final String KEY_GENDER = "gender";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_STUDENT + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_AGE + " INTEGER,"
                + KEY_GENDER + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);


        initializeTable(db);
        Log.d(TAG, "onCreate: Initialized database ");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);

        onCreate(db);
    }


    public void addStudent(Student student ,SQLiteDatabase db) {
        //SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, student.getmName());
        values.put(KEY_AGE, student.getmAge());
        values.put(KEY_GENDER, student.getmGender());

        db.insert(TABLE_STUDENT, null, values);
        //db.close();
    }

    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> studentArrayList = new ArrayList<Student>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_STUDENT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Student student = Student.createStudent(cursor.getString(1) , Integer.parseInt(cursor.getString(2)) ,cursor.getString(3) );


                studentArrayList.add(student);
            } while (cursor.moveToNext());
        }

        return studentArrayList;
    }

    private void initializeTable(SQLiteDatabase database) {

        Student student = Student.createStudent("Priyank Choudhary" , 14 , "Male");
        this.addStudent(student ,database);

        student = Student.createStudent("Vaishali Malviya" , 13 , "Female");
        this.addStudent(student ,database);


        student = Student.createStudent("Ayush Rai" , 14 , "Male");
        this.addStudent(student ,database);


        student = Student.createStudent("Jayant Choudhary" , 15 , "Male");
        this.addStudent(student ,database);



        student = Student.createStudent("Aisha Patel" , 15 , "Female");
        this.addStudent(student ,database);


        student = Student.createStudent("Kapil Sharma" , 14 , "Male");
        this.addStudent(student ,database);


        student = Student.createStudent("Shubham Jain" , 15 , "Male");
        this.addStudent(student ,database);


        student = Student.createStudent("Somi Pathak" , 13 , "Female");
        this.addStudent(student ,database);


        student = Student.createStudent("Shruti Jain" , 15 , "Female");
        this.addStudent(student ,database);



        student = Student.createStudent("Sumeet Garg" , 13 , "Male");
        this.addStudent(student ,database);







    }
}
