package console;

import fachada.Fachada;
import model.Artista;
import model.Apresentacao;
import model.Cidade;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cadastrar {
    public static void main(String[] args) throws ParseException {
        Fachada fachada = new Fachada();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Cadastrar artistas
        List<Apresentacao> listaApresentacao1 = new ArrayList<>();
        Artista artista1 = new Artista("Artista 1", "Rock", 25, listaApresentacao1);

        List<Apresentacao> listaApresentacao2 = new ArrayList<>();
        Artista artista2 = new Artista("Artista 2", "Pop", 30, listaApresentacao2);

        // Cadastrar cidades
        List<Apresentacao> listaApresentacaoCidade1 = new ArrayList<>();
        Cidade cidade1 = new Cidade("Cidade 1", listaApresentacaoCidade1);

        List<Apresentacao> listaApresentacaoCidade2 = new ArrayList<>();
        Cidade cidade2 = new Cidade("Cidade 2", listaApresentacaoCidade2);

        // Cadastrar apresentações
        Date data1 = sdf.parse("01/08/2024");
        Apresentacao apresentacao1 = new Apresentacao(1, data1, artista1, cidade1, 50.0, 120, 100);
        listaApresentacao1.add(apresentacao1);

        Date data2 = sdf.parse("02/08/2024");
        Apresentacao apresentacao2 = new Apresentacao(2, data2, artista2, cidade2, 70.0, 90, 200);
        listaApresentacao2.add(apresentacao2);

        fachada.createArtista(artista1);
        fachada.createCidade(cidade1);
        fachada.createApresentacao(apresentacao1);

        fachada.createArtista(artista2);
        fachada.createCidade(cidade2);
        fachada.createApresentacao(apresentacao2);

        fachada.close();
    }
}