package com.example.amigodoprato;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class NovaReceitaActivity extends AppCompatActivity {

    public static final String MODO = "MODO";
    public static final String NOME = "NOME";
    public static final String COMPLEXIDADE = "COMPLEXIDADE";
    public static final String TIPO_INGREDIENTES = "TIPO_INGREDIENTES";
    public static final String CATEGORIA = "CATEGORIA";

    private EditText editTextNomeReceita;
    private RadioGroup radioGroupComplexidade;
    private CheckBox checkBoxFrescos, checkBoxCongelados;
    private Spinner spinnerCategoria;

    public static final int NOVO = 1;
    public static final int ALTERAR = 2;

    private int modo;

    public static void novaReceitaActivity(AppCompatActivity activity){

        Intent intent = new Intent(activity, NovaReceitaActivity.class);

        intent.putExtra(MODO, NOVO);

        activity.startActivityForResult(intent, NOVO);
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
            } else {
                //editar
                setTitle(getString(R.string.editor_receita));
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

        arrayListCategorias.add("Selecione...");
        arrayListCategorias.add("Alimentação saudável");
        arrayListCategorias.add("Aves");
        arrayListCategorias.add("Bebidas");
        arrayListCategorias.add("Bolos e tortas salgados");
        arrayListCategorias.add("Carnes");
        arrayListCategorias.add("Doces e sobremesas");
        arrayListCategorias.add("Lanches");
        arrayListCategorias.add("Massas");
        arrayListCategorias.add("Peixes e frutos do mar");
        arrayListCategorias.add("Saladas, molhos e acompanhamentos");

        ArrayAdapter<String> arrayAdapterSpinnerCategorias = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayListCategorias);

        spinnerCategoria.setAdapter(arrayAdapterSpinnerCategorias);
    }

    public void limparCampos(View view) {
        editTextNomeReceita.setText(null);
        radioGroupComplexidade.clearCheck();
        checkBoxCongelados.setChecked(false);
        checkBoxFrescos.setChecked(false);
        spinnerCategoria.setSelection(0, true);
        editTextNomeReceita.requestFocus();
        Toast.makeText(this, getString(R.string.campos_limpos_com_sucesso), Toast.LENGTH_SHORT).show();
    }

    public void salvarNovaReceita(View view) {
        if(editTextNomeReceita.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.o_nome_do_prato_nao_pode_ser_vazio), Toast.LENGTH_SHORT).show();
            editTextNomeReceita.requestFocus();
            return;
        }

        if(radioGroupComplexidade.getCheckedRadioButtonId() == -1) { //também pode validar por switch
            Toast.makeText(this, getString(R.string.selecione_o_nivel_de_complexidade_da_receita), Toast.LENGTH_SHORT).show();
            return;
        }

        if(!checkBoxFrescos.isChecked() && !checkBoxCongelados.isChecked()) {
            Toast.makeText(this, getString(R.string.selecione_o_tipo_de_ingrediente), Toast.LENGTH_SHORT).show();
            checkBoxFrescos.requestFocus();
            return;
        }

        if(spinnerCategoria.getSelectedItemId() == 0) {
            Toast.makeText(this, getString(R.string.selecione_a_categoria_da_receita), Toast.LENGTH_SHORT).show();
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

        Intent intent = new Intent();
        intent.putExtra(NOME, nomeReceita);
        intent.putExtra(COMPLEXIDADE, complexidade);
        intent.putExtra(TIPO_INGREDIENTES, tipoIngredientes.toString());
        intent.putExtra(CATEGORIA, categoria);

        setResult(Activity.RESULT_OK, intent);

        Toast.makeText(this, R.string.receita_salva_com_sucesso, Toast.LENGTH_SHORT).show();

        finish();
    }


    public void cancelar(View view){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

}