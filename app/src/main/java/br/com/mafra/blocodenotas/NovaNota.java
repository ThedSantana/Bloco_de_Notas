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

public class NovaNota extends AppCompatActivity {

    private EditText titulo;
    private EditText conteudo;
    private RelativeLayout barra;
    private RelativeLayout main;
    private Context context;
    private ScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_nota);

        context = this;

        titulo = (EditText)findViewById(R.id.edittitulo);
        conteudo = (EditText)findViewById(R.id.editconteudo);

        barra = (RelativeLayout) findViewById(R.id.barraNova);

        main = (RelativeLayout) findViewById(R.id.nova);

        scroll = (ScrollView) findViewById(R.id.novascrollView);
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

            if( titulo.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "O titulo precisa conter algum texto.", Toast.LENGTH_SHORT).show();
            }else {

                main.setVisibility(View.INVISIBLE);
                barra.setVisibility(View.VISIBLE);

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        String titu=titulo.getText().toString();
                        String conte="";
                        if (!conteudo.getText().toString().equals("")){
                            conte = conteudo.getText().toString();
                        }
                        ConsultasSQL.EscreveNoBanco(titu, conte);

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
