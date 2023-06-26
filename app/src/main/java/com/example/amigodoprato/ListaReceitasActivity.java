package com.example.amigodoprato;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.view.ActionMode;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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

import com.example.amigodoprato.database.ReceitaDatabase;
import com.example.amigodoprato.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;

public class ListaReceitasActivity extends AppCompatActivity {

    private ListView listViewReceitas;
    private ArrayList<Receita> listaDeReceitas;
    private ReceitaAdapter receitaAdapter;
    private int posicaoSelecionada;

    private ActionMode actionMode;
    private View viewSelecionada;

    private static final String ARQUIVO = "com.amigodoprato.sp.PREFERENCIA_MODO";

    private static final String MODO = "MODO";

    private String modo = "claro";

    private static MenuItem menuItemModo;

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

            Receita receita = (Receita) listViewReceitas.getItemAtPosition(posicaoSelecionada);

            if (item.getItemId() == R.id.contextMenuItemEditar) {
                editarReceita(ListaReceitasActivity.this, NovaReceitaActivity.ALTERAR, receita);
                mode.finish();
                return true;
            }

            if (item.getItemId() == R.id.contextMenuItemExcluir) {
                excluirReceita(receita);
                mode.finish();
                return true;
            }

            return false;
        }

        @Override
        public void onDestroyActionMode(androidx.appcompat.view.ActionMode mode) {
            if (viewSelecionada != null) {
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

                if (actionMode != null) {
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

        ReceitaDatabase receitaDatabase = ReceitaDatabase.getDatabase(this);
        List<Receita> listaDeReceitas = receitaDatabase.receitaDAO().queryAll();

        receitaAdapter = new ReceitaAdapter(this, listaDeReceitas);
        listViewReceitas.setAdapter(receitaAdapter);
    }

    private void excluirReceita(Receita receita) {

        String mensagemExclusaoReceita = getString(R.string.deseja_realmente_excluir_a_receita) + receita.getNome();

        DialogInterface.OnClickListener dialogInterfaceListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int opcaoClicada) {
                if (opcaoClicada == DialogInterface.BUTTON_POSITIVE) {

                    ReceitaDatabase receitaDatabase = ReceitaDatabase.getDatabase(ListaReceitasActivity.this);

                    receitaDatabase.receitaDAO().delete(receita);

//                    receitaAdapter.notifyDataSetChanged();
                    popularListaDeReceitas();
                    Toast.makeText(getApplicationContext(), R.string.receita_excluida_com_sucesso, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (opcaoClicada == DialogInterface.BUTTON_NEGATIVE) {
                    return;
                }
            }
        };

        DialogUtils.dialogConfirmacao(this, mensagemExclusaoReceita, dialogInterfaceListener);

    }

    private void editarReceita(Activity activity, int requestCode, Receita receita) {

        NovaReceitaActivity.alterarReceita(activity, requestCode, receita);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == NovaReceitaActivity.NOVO || requestCode == NovaReceitaActivity.ALTERAR) &&
                resultCode == Activity.RESULT_OK) {

            popularListaDeReceitas();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lista_receitas_activity_opcoes, menu);
        menuItemModo = menu.findItem(R.id.menuSwitch);

        carregarPreferenciaModo();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menuItemAdicionar) {
            NovaReceitaActivity.novaReceita(this, NovaReceitaActivity.NOVO);
            return true;
        }

        if (item.getItemId() == R.id.menuItemSobre) {
            SobreActivity.abrirActivitySobre(this);
            return true;
        }

        if (item.getItemId() == R.id.menuSwitch) {
            int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                item.setIcon(R.drawable.modo_claro);
                salvarPreferenciaModo("claro");
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                item.setIcon(R.drawable.modo_escuro);
                salvarPreferenciaModo("escuro");
            }
            recreate();
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
    public boolean onContextItemSelected(MenuItem item) {

//        AdapterView.AdapterContextMenuInfo info;
//
//        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Receita receita = (Receita) listViewReceitas.getItemAtPosition(posicaoSelecionada);

        if (item.getItemId() == R.id.contextMenuItemEditar) {
            editarReceita(this, NovaReceitaActivity.ALTERAR, receita);
        }

        if (item.getItemId() == R.id.contextMenuItemExcluir) {
            excluirReceita(receita);
        }

        return super.onContextItemSelected(item);
    }

    private void carregarPreferenciaModo() {
        SharedPreferences shared = getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE);

        modo = shared.getString(MODO, modo);

        alteraModo();
    }

    private void salvarPreferenciaModo(String novoValorModo) {
        SharedPreferences shared = getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = shared.edit();

        editor.putString(MODO, novoValorModo);

        editor.apply();

        modo = novoValorModo;

        alteraModo();
    }

    private void alteraModo() {
        if (modo.equals("claro")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            menuItemModo.setIcon(R.drawable.modo_escuro);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            menuItemModo.setIcon(R.drawable.modo_claro);
        }
    }
}