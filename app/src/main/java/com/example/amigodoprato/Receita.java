package com.example.amigodoprato;

import java.util.List;

public class Receita {

    private int id;
    private String nome;
    private String complexidade;
    private List<String> tipoDeIngredientes;
    private String categoria;
    private int criadoPor;
    private List<String> ingredientes;
    private String tempoDePreparo;
    private String modoDePreparo;

    public Receita(int id, String nome, String complexidade, List<String> tipoDeIngredientes, String categoria, int criadoPor, List<String> ingredientes, String tempoDePreparo, String modoDePreparo) {
        this.id = id;
        this.nome = nome;
        this.complexidade = complexidade;
        this.tipoDeIngredientes = tipoDeIngredientes;
        this.categoria = categoria;
        this.criadoPor = criadoPor;
        this.ingredientes = ingredientes;
        this.tempoDePreparo = tempoDePreparo;
        this.modoDePreparo = modoDePreparo;
    }

    public Receita(String nome, String complexidade, String categoria, String tempoDePreparo) {
        this.nome = nome;
        this.complexidade = complexidade;
        this.categoria = categoria;
        this.tempoDePreparo = tempoDePreparo;
    }

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

    public List<String> getTipoDeIngredientes() {
        return tipoDeIngredientes;
    }

    public void setTipoDeIngredientes(List<String> tipoDeIngredientes) {
        this.tipoDeIngredientes = tipoDeIngredientes;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getCriadoPor() {
        return criadoPor;
    }

    public void setCriadoPor(int criadoPor) {
        this.criadoPor = criadoPor;
    }

    public List<String> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<String> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getTempoDePreparo() {
        return tempoDePreparo;
    }

    public void setTempoDePreparo(String tempoDePreparo) {
        this.tempoDePreparo = tempoDePreparo;
    }

    public String getModoDePreparo() {
        return modoDePreparo;
    }

    public void setModoDePreparo(String modoDePreparo) {
        this.modoDePreparo = modoDePreparo;
    }


    @Override
    public String toString() {
        return "Nome: " + getNome() +
                "\nCategoria: " + getCategoria() +
                "\nComplexidade : " + getComplexidade() +
                "\nTempo de preparo: " + getTempoDePreparo() + " minutos";
      }
}