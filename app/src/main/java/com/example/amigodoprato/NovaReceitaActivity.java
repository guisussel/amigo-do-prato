package com.example.amigodoprato;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.amigodoprato.database.ReceitaDatabase;
import com.example.amigodoprato.utils.DialogUtils;

import java.util.ArrayList;

public class NovaReceitaActivity extends AppCompatActivity {

    public static final String MODO = "MODO";
    public static final String NOME = "NOME";
    public static final String COMPLEXIDADE = "COMPLEXIDADE";
    public static final String TIPO_INGREDIENTES = "TIPO_INGREDIENTES";
    public static final String CATEGORIA = "CATEGORIA";


    public static final String ID_RECEITA = "ID_RECEITA";

    private EditText editTextNomeReceita;
    private RadioGroup radioGroupComplexidade;
    private CheckBox checkBoxFrescos, checkBoxCongelados;
    private Spinner spinnerCategoria;

    private String nomeOriginal, complexidadeOriginal, tipoIngredientesOriginal, categoriaOriginal;

    public static final int NOVO = 1;
    public static final int ALTERAR = 2;

    private int modo;
    private Receita receita;

    public static void novaReceita(Activity activity, int requestCode) {

        Intent intent = new Intent(activity, NovaReceitaActivity.class);

        intent.putExtra(MODO, NOVO);

        activity.startActivityForResult(intent, requestCode);
    }

    public static void alterarReceita(Activity activity, int requestCode, Receita receita){

        Intent intent = new Intent(activity, NovaReceitaActivity.class);

        intent.putExtra(MODO, ALTERAR);
        intent.putExtra(ID_RECEITA, receita.getId());

        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_receita);

        carregarComponentes();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            modo = bundle.getInt(MODO, NOVO);

            if (modo == NOVO) {
                setTitle(getString(R.string.nova_receita));
                receita = new Receita();
            } else {
                setTitle(getString(R.string.editor_receita));

                int idReceita = bundle.getInt(ID_RECEITA);

                ReceitaDatabase receitaDatabase = ReceitaDatabase.getDatabase(this);

                receita = receitaDatabase.receitaDAO().queryForId(idReceita);

                editTextNomeReceita.setText(receita.getNome());

                int count = radioGroupComplexidade.getChildCount();
                for (int i = 0; i < count; i++) {
                    View view = radioGroupComplexidade.getChildAt(i);
                    if (view instanceof RadioButton) {
                        RadioButton radioButton = (RadioButton) view;
                        if (radioButton.getText().toString().equals(receita.getComplexidade())) {
                            radioButton.setChecked(true);
                            break;
                        }
                    }
                }

                if (receita.getTipoDeIngredientes().equals(getString(R.string.frescos_e_congelados))) {
                    checkBoxFrescos.setChecked(true);
                    checkBoxCongelados.setChecked(true);
                } else if (receita.getTipoDeIngredientes().equals(getString(R.string.frescos))) {
                    checkBoxFrescos.setChecked(true);
                } else {
                    checkBoxCongelados.setChecked(true);
                }

                int itemCount = spinnerCategoria.getCount();
                for (int posicao = 0; posicao < itemCount; posicao++) {
                    String item = (String) spinnerCategoria.getItemAtPosition(posicao);
                    if (item.equals(receita.getCategoria())) {
                        spinnerCategoria.setSelection(posicao);
                        break;
                    }
                }

            }
        }
        editTextNomeReceita.requestFocus();
    }

    private void carregarComponentes() {
        editTextNomeReceita = findViewById(R.id.editTextNomeReceita);
        radioGroupComplexidade = findViewById(R.id.radioGroupComplexidade);
        checkBoxFrescos = findViewById(R.id.checkBoxFrescos);
        checkBoxCongelados = findViewById(R.id.checkBoxCongelados);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        popularSpinnerCategorias();
    }

    private void popularSpinnerCategorias() {
        ArrayList<String> arrayListCategorias = new ArrayList<>();

        arrayListCategorias.add(getString(R.string.selecione));
        arrayListCategorias.add(getString(R.string.alimentacao_saudavel));
        arrayListCategorias.add(getString(R.string.aves));
        arrayListCategorias.add(getString(R.string.bebidas));
        arrayListCategorias.add(getString(R.string.bolos_tortas_salgados));
        arrayListCategorias.add(getString(R.string.carnes));
        arrayListCategorias.add(getString(R.string.doces_sobremesas));
        arrayListCategorias.add(getString(R.string.lanches));
        arrayListCategorias.add(getString(R.string.massas));
        arrayListCategorias.add(getString(R.string.peixes_frutos_mar));
        arrayListCategorias.add(getString(R.string.saladas_molhos_acompanhamentos));

        ArrayAdapter<String> arrayAdapterSpinnerCategorias = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayListCategorias);

        spinnerCategoria.setAdapter(arrayAdapterSpinnerCategorias);
    }

    public void limparCampos() {
        editTextNomeReceita.setText(null);
        radioGroupComplexidade.clearCheck();
        checkBoxCongelados.setChecked(false);
        checkBoxFrescos.setChecked(false);
        spinnerCategoria.setSelection(0, true);
        editTextNomeReceita.requestFocus();
        Toast.makeText(this, getString(R.string.campos_limpos_com_sucesso), Toast.LENGTH_SHORT).show();
    }

    public void salvarNovaReceita() {
        if (editTextNomeReceita.getText().toString().trim().equals("")) {
            DialogUtils.dialogAvisoErroPadrão(this, R.string.o_nome_do_prato_nao_pode_ser_vazio);
            editTextNomeReceita.requestFocus();
            return;
        }

        if (radioGroupComplexidade.getCheckedRadioButtonId() == -1) {
            DialogUtils.dialogAvisoErroPadrão(this, R.string.selecione_o_nivel_de_complexidade_da_receita);
            return;
        }

        if (!checkBoxFrescos.isChecked() && !checkBoxCongelados.isChecked()) {
            DialogUtils.dialogAvisoErroPadrão(this, R.string.selecione_o_tipo_de_ingrediente);
            checkBoxFrescos.requestFocus();
            return;
        }

        if (spinnerCategoria.getSelectedItemId() == 0) {
            DialogUtils.dialogAvisoErroPadrão(this, R.string.selecione_a_categoria_da_receita);
            spinnerCategoria.requestFocus();
            return;
        }

        String complexidade = null;

        int checkedRadioButtonId = radioGroupComplexidade.getCheckedRadioButtonId();

        if (checkedRadioButtonId == R.id.radioButtonIniciante) {
            complexidade = Receita.INICIANTE;
        } else if (checkedRadioButtonId == R.id.radioButtonIntermediario) {
            complexidade = Receita.INTERMEDIARIO;
        } else if (checkedRadioButtonId == R.id.radioButtonExperiente) {
            complexidade = Receita.EXPERIENTE;
        }

        String nomeReceita = editTextNomeReceita.getText().toString();

        StringBuilder tipoIngredientes = new StringBuilder();
        if (checkBoxFrescos.isChecked()) {
            if (checkBoxCongelados.isChecked()) {
                tipoIngredientes.append(getString(R.string.frescos_e_congelados));
            } else {
                tipoIngredientes.append(getString(R.string.frescos));
            }
        } else {
            tipoIngredientes.append(getString(R.string.congelados));
        }

        String categoria = (String) spinnerCategoria.getSelectedItem();

        receita.setNome(nomeReceita);
        receita.setComplexidade(complexidade);
        receita.setTipoDeIngredientes(tipoIngredientes.toString());
        receita.setCategoria(categoria);


        ReceitaDatabase receitaDatabase = ReceitaDatabase.getDatabase(this);

        if (modo == NOVO) {

            receitaDatabase.receitaDAO().insert(receita);

        } else {

            receitaDatabase.receitaDAO().update(receita);
        }

        setResult(Activity.RESULT_OK);

        Toast.makeText(this, R.string.receita_salva_com_sucesso, Toast.LENGTH_SHORT).show();

        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nova_receita_activity_opcoes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menuItemSalvarReceita) {
            salvarNovaReceita();
            return true;
        }

        if (item.getItemId() == R.id.menuItemLimparReceita) {
            limparCampos();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}