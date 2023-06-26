package com.example.amigodoprato.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.amigodoprato.Receita;

@Database(entities = {Receita.class}, version = 1, exportSchema = false)
public abstract class ReceitaDatabase extends RoomDatabase {

    public abstract ReceitaDAO receitaDAO();

    private static ReceitaDatabase receitaDatabaseInstance;

    public static ReceitaDatabase getDatabase(final Context context) {
        if (receitaDatabaseInstance == null) {
            synchronized (ReceitaDatabase.class) {
                if (receitaDatabaseInstance == null) {
                    receitaDatabaseInstance = Room.databaseBuilder(context, ReceitaDatabase.class, "receitas.db").allowMainThreadQueries().build();
                }
            }
        }
        return receitaDatabaseInstance;
    }

}
