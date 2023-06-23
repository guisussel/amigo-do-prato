package com.example.amigodoprato;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ReceitaAdapter extends BaseAdapter {


    private Context context;
    private List<Receita> listaDeReceitas;

    private static class ReceitaHolder {
        public TextView textViewNome;
        public TextView textViewCategoria;
        public TextView textViewComplexidade;
        public TextView textViewTipoDeIngredientes;
    }

    public ReceitaAdapter(Context context, List<Receita> listaDeReceitas) {
        this.context = context;
        this.listaDeReceitas = listaDeReceitas;
    }

    @Override
    public int getCount() {
        return listaDeReceitas.size();
    }

    @Override
    public Object getItem(int i) {
        return listaDeReceitas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ReceitaHolder receitaHolder;

        if(view == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_lista_receita, viewGroup, false);
            receitaHolder = new ReceitaHolder();
            receitaHolder.textViewNome = view.findViewById(R.id.textViewValorNome);
            receitaHolder.textViewCategoria = view.findViewById(R.id.textViewValorCategoria);
            receitaHolder.textViewComplexidade = view.findViewById(R.id.textViewValorComplexidade);
            receitaHolder.textViewTipoDeIngredientes = view.findViewById(R.id.textViewValorTipoDeIngredientes);

            view.setTag(receitaHolder);

        } else {

            receitaHolder = (ReceitaHolder) view.getTag();

        }
            receitaHolder.textViewNome.setText(listaDeReceitas.get(i).getNome());
            receitaHolder.textViewCategoria.setText(listaDeReceitas.get(i).getCategoria());
            receitaHolder.textViewComplexidade.setText(listaDeReceitas.get(i).getComplexidade());
            String tipoDeIngredientes = listaDeReceitas.get(i).getTipoDeIngredientes();
            receitaHolder.textViewTipoDeIngredientes.setText(tipoDeIngredientes);

        return view;

    }

}
