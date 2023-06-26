package com.example.amigodoprato;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Receita {

    public static final String INICIANTE = "Iniciante";
    public static final String INTERMEDIARIO = "Intermediário";
    public static final String EXPERIENTE = "Experiente";

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String nome;
    @NonNull
    private String complexidade;
    @NonNull
    private String tipoDeIngredientes;
    @NonNull
    private String categoria;


    public Receita() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getComplexidade() {
        return complexidade;
    }

    public void setComplexidade(String complexidade) {
        this.complexidade = complexidade;
    }

    public String getTipoDeIngredientes() {
        return tipoDeIngredientes;
    }

    public void setTipoDeIngredientes(String tipoDeIngredientes) {
        this.tipoDeIngredientes = tipoDeIngredientes;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


    @Override
    public String toString() {
        return "Nome: " + getNome() +
                "\nCategoria: " + getCategoria() +
                "\nComplexidade : " + getComplexidade() +
                "\nTipo de ingredientes: " + getTipoDeIngredientes();
    }
}
