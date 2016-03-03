package br.com.mafra.blocodenotas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.RelativeLayout;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ListView lista;
    private Context contesto;
    private List<Nota> li;
    private AdapterDaNota adap;
    private RelativeLayout main;
    private RelativeLayout barra;
    private boolean umclick=true;


    private ConsultasSQL consql;
    private ChamaDoSQL sql;
    private SQLiteDatabase conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contesto = this;

        main = (RelativeLayout) findViewById(R.id.listarelative);
        barra = (RelativeLayout) findViewById(R.id.barralista);

        lista = (ListView) findViewById(R.id.listView);

        try {
            /************** conecta ao banco **************/
            sql = new ChamaDoSQL(this);
            conn = sql.getWritableDatabase();
            consql = new ConsultasSQL(conn);

            /***************** ler o banco *****************/
            consql = new ConsultasSQL(conn);

            ListaSQL();

        }catch (SQLException e){
            AlertDialog.Builder alerta = new AlertDialog.Builder(contesto);
            alerta.setTitle("Falha");
            alerta.setMessage(e.getMessage());
            alerta.show();
        }

        lista.setOnItemClickListener(lister);
        lista.setOnItemLongClickListener(LLister);
        
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_pri, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent it = new Intent(this, NovaNota.class);
        startActivity(it);
        return super.onOptionsItemSelected(item);
    }

    public AdapterView.OnItemClickListener lister = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(umclick) {
                Intent it = new Intent(contesto, LeitoDaAnotacao.class);
                it.putExtra("id", id);
                it.putExtra("titulo", li.get(position).getTitulo());
                it.putExtra("conteudo", li.get(position).getComteudo());
                startActivity(it);
            }
        }
    };


    public AdapterView.OnItemLongClickListener LLister = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {
            umclick = false;
            AlertDialog.Builder alerta = new AlertDialog.Builder(contesto);
            alerta.setTitle("Excluir");
            alerta.setMessage("Realmente deseja excluir essa anotação?");
            alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    umclick = true;

                    main.setVisibility(View.INVISIBLE);
                    barra.setVisibility(View.VISIBLE);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            consql.ApagaLinha(id, contesto);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ListaSQL();

                                    main.setVisibility(View.VISIBLE);
                                    barra.setVisibility(View.GONE);
                                }
                            });
                        }
                    }).start();

                }
            });
            alerta.setNegativeButton("não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    umclick = true;
                }
            });
            alerta.show();
            return false;
        }
    };

    public void ListaSQL(){
        li = consql.LerDoBanco();
        adap = new AdapterDaNota(this,li);
        lista.setAdapter(adap);
    }
}
