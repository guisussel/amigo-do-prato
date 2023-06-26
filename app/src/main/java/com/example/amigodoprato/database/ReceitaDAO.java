package com.example.amigodoprato.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.amigodoprato.Receita;

import java.util.List;

@Dao
public interface ReceitaDAO {

    @Insert
    long insert(Receita receita);

    @Delete
    void delete(Receita receita);

    @Update
    void update(Receita receita);

    @Query("SELECT * FROM receita WHERE id = :id")
    Receita queryForId(long id);

    @Query("SELECT * FROM receita ORDER BY nome ASC")
    List<Receita> queryAll();


}
