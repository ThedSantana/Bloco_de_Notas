package br.com.mafra.blocodenotas;

/**
 * Created by Leandro on 08/02/2016.
 */
public class Nota {

    private int id;
    private String titulo;
    private String comteudo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getComteudo() {
        return comteudo;
    }

    public void setComteudo(String comteudo) {
        this.comteudo = comteudo;
    }
}
