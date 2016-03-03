package br.com.mafra.blocodenotas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Leandro on 09/02/2016.
 */
public class ChamaDoSQL extends SQLiteOpenHelper {

    public ChamaDoSQL(Context context) {
        super(context, "NotaMafraSoft", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE NotaMafraSoft (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, titulo VARCHAR(250), conteudo VARCHAR(1000));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
