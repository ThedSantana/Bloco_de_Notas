package br.com.mafra.blocodenotas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Leandro on 08/02/2016.
 */
public class AdapterDaNota extends BaseAdapter {

    private Context contesto;
    private List<Nota> lista;

    public AdapterDaNota(Context contesto, List<Nota> lista){
        this.contesto = contesto;
        this.lista = lista;
    }
    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return lista.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Nota nota = lista.get(position);

        View layout;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) contesto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.layout_da_lista, null);
        }else {
            layout = convertView;
        }


        TextView testo = (TextView) layout.findViewById(R.id.TestoDaView);
        testo.setText(nota.getTitulo());

        return layout;
    }


}
