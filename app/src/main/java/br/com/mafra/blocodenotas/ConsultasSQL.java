package br.com.mafra.blocodenotas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leandro on 09/02/2016.
 */
public class ConsultasSQL {

    private static SQLiteDatabase conn;

    public ConsultasSQL(SQLiteDatabase conn) {

        this.conn = conn;
    }

    public List<Nota> LerDoBanco(){
        List<Nota> li = new ArrayList<Nota>();

        Cursor cursor = conn.query("NotaMafraSoft", null, null, null, null, null, null);

        cursor.moveToFirst();

        if(cursor.getCount() > 0) {

            do {
                Nota nota = new Nota();
                int col = cursor.getColumnIndex("titulo");
                int col1 = cursor.getColumnIndex("conteudo");
                nota.setId(cursor.getInt(0));
                nota.setTitulo(cursor.getString(col));
                nota.setComteudo(cursor.getString(col1));
                li.add(nota);
            } while (cursor.moveToNext());
        }
        return li;
    }

    public static void EscreveNoBanco(String titu, String conte) {

            ContentValues valuer = new ContentValues();
            valuer.put("titulo", titu);
            valuer.put("conteudo", conte);

        conn.insertOrThrow("NotaMafraSoft", null, valuer);

        }

    public void ApagaLinha(long id, Context contesto){
        try {
            conn.execSQL("delete from NotaMafraSoft where _id="+id+";");
        }catch (SQLException e){
            Toast.makeText(contesto.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static void AtualizaBanco(int id, String titu, String conte){
        try {
            conn.execSQL("update NotaMafraSoft set \"titulo\"=\""+titu+"\", \"conteudo\"=\""+conte+"\" where _id="+id+";");
        }catch (SQLException e){

        }
    }

}

