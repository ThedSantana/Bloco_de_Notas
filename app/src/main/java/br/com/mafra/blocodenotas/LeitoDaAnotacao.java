package br.com.mafra.blocodenotas;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class LeitoDaAnotacao extends AppCompatActivity {

    private EditText titulo;
    private EditText conteudo;
    private long idl=0;
    private RelativeLayout barra;
    private RelativeLayout main;
    private Context context;
    private ScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leito_da_anotacao);
        context = this;

        titulo = (EditText)findViewById(R.id.textTitulo);
        conteudo = (EditText)findViewById(R.id.textConteudo);

        Bundle bundle = getIntent().getExtras();

        if(bundle.containsKey("titulo")){
            String ti = bundle.getString("titulo");
            titulo.setText(ti);
        }

        if(bundle.containsKey("conteudo")){
            String ti = bundle.getString("conteudo");
            conteudo.setText(ti);
            conteudo.requestFocus();
        }

        if(bundle.containsKey("id")){
            long iid = bundle.getLong("id");
            idl = iid;
        }

        main = (RelativeLayout) findViewById(R.id.leitura);
        barra = (RelativeLayout) findViewById(R.id.barraleitura);

        scroll = (ScrollView) findViewById(R.id.leitorscrollView);
        scroll.setOnTouchListener(scrol);

    }

    public View.OnTouchListener scrol = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            conteudo.requestFocus();
            InputMethodManager inp = (InputMethodManager) getSystemService(context.INPUT_METHOD_SERVICE);
            inp.showSoftInput(conteudo, InputMethodManager.SHOW_FORCED);

            return false;
        }
    };

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_nova, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.nitem1){
            Intent it = new Intent(this, MainActivity.class);
            startActivity(it);
        }

        if(item.getItemId() == R.id.nitem2){

            if(titulo.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "O titulo é necessário para atualizar essa nota.", Toast.LENGTH_SHORT).show();
            }else{

                main.setVisibility(View.INVISIBLE);
                barra.setVisibility(View.VISIBLE);

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        String titu = titulo.getText().toString();
                        int id = (int) idl;
                        String conte;
                        if(conteudo.getText().toString().equals("")){
                            conte = "";
                        }else{
                            conte = conteudo.getText().toString();
                        }
                        ConsultasSQL.AtualizaBanco(id, titu, conte);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent it = new Intent(context, MainActivity.class);
                                startActivity(it);
                            }
                        });

                    }
                }).start();

            }


        }

        return super.onOptionsItemSelected(item);
    }
}
