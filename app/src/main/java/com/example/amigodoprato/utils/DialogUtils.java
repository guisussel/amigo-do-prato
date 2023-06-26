package com.example.amigodoprato.utils;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.example.amigodoprato.R;

public class DialogUtils {

    public static void dialogAvisoErroPadrão(Context context, int idStringErro) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.aviso);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setMessage(idStringErro);

        builder.setNeutralButton(R.string.voltar,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void dialogConfirmacao(Context context, String mensagemConfirmacao, DialogInterface.OnClickListener listener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.confirmacao);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setMessage(mensagemConfirmacao);
        builder.setPositiveButton(context.getString(R.string.sim), listener);
        builder.setNegativeButton(context.getString(R.string.nao), listener);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
