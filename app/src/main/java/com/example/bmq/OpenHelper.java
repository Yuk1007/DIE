package com.example.bmq;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenHelper extends SQLiteOpenHelper
{
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "QUIZ.db";
    private String TABLE_NAME = "quizarray";

    private String SQL_CREATE_ENTRIES =
            "create table " + TABLE_NAME + " ("
                    + "quiz0 text, "
                    + "quiz1 text, "
                    + "quiz2 text, "
                    + "quiz3 text, "
                    + "quiz4 text, "
                    + "explanation text);";


    OpenHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onUpgrade(db, oldVersion, newVersion);
    }
}
