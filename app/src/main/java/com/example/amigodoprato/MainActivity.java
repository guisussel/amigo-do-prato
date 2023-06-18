package com.example.amigodoprato;

import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {

    private EditText editTextNomePrato;
    private RadioGroup radioGroupComplexidade;
    private RadioButton radioButtonIniciante;
    private RadioButton radioButtonIntermediario;
    private RadioButton radioButtonExperiente;
    private CheckBox checkBoxFrescos;
    private CheckBox checkBoxCongelados;
    private Spinner spinnerCategoria;
    private Button buttonSalvar;
    private Button buttonLimpar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carregarComponentes();
    }

    private void carregarComponentes() {
        editTextNomePrato = findViewById(R.id.editTextNomePrato);
        radioGroupComplexidade = findViewById(R.id.radioGroupComplexidade);
        radioButtonIniciante = findViewById(R.id.radioButtonIniciante);
        radioButtonIntermediario = findViewById(R.id.radioButtonIntermediario);
        radioButtonExperiente = findViewById(R.id.radioButtonExperiente);
        checkBoxFrescos = findViewById(R.id.checkBoxFrescos);
        checkBoxCongelados = findViewById(R.id.checkBoxCongelados);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        buttonSalvar = findViewById(R.id.buttonSalvar);
        buttonLimpar = findViewById(R.id.buttonLimpar);
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
        editTextNomePrato.setText(null);
        radioGroupComplexidade.clearCheck();
        checkBoxCongelados.setChecked(false);
        checkBoxFrescos.setChecked(false);
        spinnerCategoria.setSelection(0, true);
        editTextNomePrato.requestFocus();
        Toast.makeText(this, getString(R.string.campos_limpos_com_sucesso), Toast.LENGTH_SHORT).show();
    }

    public void salvarDados(View view) {
        if(editTextNomePrato.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.o_nome_do_prato_nao_pode_ser_vazio), Toast.LENGTH_SHORT).show();
            editTextNomePrato.requestFocus();
        }

        if(radioGroupComplexidade.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, getString(R.string.selecione_o_nivel_de_complexidade_da_receita), Toast.LENGTH_SHORT).show();
        }

        if(!checkBoxFrescos.isChecked() && !checkBoxCongelados.isChecked()) {
            Toast.makeText(this, getString(R.string.selecione_o_tipo_de_ingrediente), Toast.LENGTH_SHORT).show();
            checkBoxFrescos.requestFocus();
        }

        if(spinnerCategoria.getSelectedItemId() == 0) {
            Toast.makeText(this, getString(R.string.selecione_a_categoria_da_receita), Toast.LENGTH_SHORT).show();
            spinnerCategoria.requestFocus();
        }
    }

}