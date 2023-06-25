package com.example.amigodoprato;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListaReceitasActivity extends AppCompatActivity {

    private ListView listViewReceitas;
    private ArrayList<Receita> listaDeReceitas;
    private ReceitaAdapter receitaAdapter;
    private int posicaoSelecionada;

    private ActionMode actionMode;
    private View viewSelecionada;

    private androidx.appcompat.view.ActionMode.Callback mActionModeCallback = new androidx.appcompat.view.ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(androidx.appcompat.view.ActionMode mode, Menu menu) {
            MenuInflater inflate = mode.getMenuInflater();
            inflate.inflate(R.menu.receita_selecionada_contexto_opcoes, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(androidx.appcompat.view.ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(androidx.appcompat.view.ActionMode mode, MenuItem item) {
            if(item.getItemId() == R.id.contextMenuItemEditar) {
                editarReceita();
                mode.finish();
                return true;
            }

            if(item.getItemId() == R.id.contextMenuItemExcluir) {
                excluirReceita();
                mode.finish();
                return true;
            }

            return false;
        }

        @Override
        public void onDestroyActionMode(androidx.appcompat.view.ActionMode mode) {
            if (viewSelecionada != null){
                viewSelecionada.setBackgroundColor(Color.TRANSPARENT);
            }

            actionMode = null;
            viewSelecionada = null;

            listViewReceitas.setEnabled(true);
        }
    };


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

        listViewReceitas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (actionMode != null){
                    return false;
                }

                posicaoSelecionada = i;

                view.setBackgroundColor(Color.LTGRAY);

                viewSelecionada = view;

                listViewReceitas.setEnabled(false);

                actionMode = startSupportActionMode(mActionModeCallback);

                return true;
            }
        });

        registerForContextMenu(listViewReceitas);

    }

    private void popularListaDeReceitas() {
        listaDeReceitas = new ArrayList<>();
        receitaAdapter = new ReceitaAdapter(this, listaDeReceitas);
        listViewReceitas.setAdapter(receitaAdapter);
    }

    private void excluirReceita() {
        listaDeReceitas.remove(posicaoSelecionada);
        receitaAdapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(), R.string.receita_excluida_com_sucesso, Toast.LENGTH_SHORT).show();
    }

    private void editarReceita() {
        Receita receita = listaDeReceitas.get(posicaoSelecionada);

        NovaReceitaActivity.alterarReceita(this, receita);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lista_receitas_activity_opcoes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menuItemAdicionar) {
            NovaReceitaActivity.novaReceitaActivity(this);
            return true;
        }

        if (item.getItemId() == R.id.menuItemSobre) {
            SobreActivity.abrirActivitySobre(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.receita_selecionada_contexto_opcoes, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.contextMenuItemEditar) {
            editarReceita();
        }

        if(item.getItemId() == R.id.contextMenuItemExcluir) {
            excluirReceita();
        }

        return super.onContextItemSelected(item);
    }
}