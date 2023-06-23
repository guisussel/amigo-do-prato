package com.example.amigodoprato;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListaReceitasActivity extends AppCompatActivity {

    private ListView listViewReceitas;
    private ArrayList<Receita> listaDeReceitas;
    private ReceitaAdapter receitaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_receitas);

        listViewReceitas = findViewById(R.id.listViewReceitas);

        popularListaDeReceitas();

        listViewReceitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Receita receita = (Receita) listViewReceitas.getItemAtPosition(position);

                String mensagem = getString(R.string.a_receita) + receita.getNome()
                        + getString(R.string.e_de_nivel) + receita.getComplexidade()
                        + getString(R.string.da_categoria) + receita.getCategoria()
                        + getString(R.string.e_seus_ingredientes_sao) + receita.getTipoDeIngredientes();

                Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void popularListaDeReceitas() {

        listaDeReceitas = new ArrayList<>();

        receitaAdapter = new ReceitaAdapter(this, listaDeReceitas);

        listViewReceitas.setAdapter(receitaAdapter);

    }

    public void adicionarNovaReceita(View view) {
        NovaReceitaActivity.novaReceitaActivity(this);
    }

    public void abrirSobre(View view) {
        SobreActivity.abrirActivitySobre(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            Bundle bundle = data.getExtras();

            String nomeReceita = bundle.getString(NovaReceitaActivity.NOME);
            String complexidade = bundle.getString(NovaReceitaActivity.COMPLEXIDADE);
            String tipoIngredientes = bundle.getString(NovaReceitaActivity.TIPO_INGREDIENTES);
            String categoria = bundle.getString(NovaReceitaActivity.CATEGORIA);

            Receita receita = new Receita(nomeReceita, complexidade, categoria, tipoIngredientes);

            listaDeReceitas.add(receita);

        }
    }
}