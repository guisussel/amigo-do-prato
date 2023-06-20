package com.example.amigodoprato;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListaReceitasActivity extends AppCompatActivity {

    private ListView listViewReceitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recipes);

        listViewReceitas = findViewById(R.id.listViewReceitas);

        popularListaDeReceitas();

        listViewReceitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Receita receita = (Receita) listViewReceitas.getItemAtPosition(position);

                String mensagem = getString(R.string.a_receita) + receita.getNome()
                        + getString(R.string.eh) + receita.getComplexidade()
                        + getString(R.string.de_fazer_e_um_a) + receita.getCategoria()
                        + getString(R.string.que_levara) + receita.getTempoDePreparo() + getString(R.string.minutos_para_preparar);

                Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void popularListaDeReceitas() {

        String[] nomes = getResources().getStringArray(R.array.nomes);
        String[] categorias = getResources().getStringArray(R.array.categorias);
        String[] complexidades = getResources().getStringArray(R.array.complexidades);
        String[] temposDePreparo = getResources().getStringArray(R.array.tempos_de_preparo);

        ArrayList<Receita> arrayListReceitas = new ArrayList<>();

        for(int i = 0; i < nomes.length; i++) {
            arrayListReceitas.add(new Receita(nomes[i], complexidades[i], categorias[i], temposDePreparo[i]));
        }

        ReceitaAdapter receitaAdapter = new ReceitaAdapter(this, arrayListReceitas);

        listViewReceitas.setAdapter(receitaAdapter);

    }

}