package com.blackscreen.beertypecontrol.useful;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.blackscreen.beertypecontrol.R;

public class CustomAlert {

    public static void errorWarning(Context context, int idText){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.warning);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setMessage(idText);

        builder.setNegativeButton(context.getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public static void actionConfirmation(Context context,
                                          String msg,
                                          DialogInterface.OnClickListener listener){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.confirmation);
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setMessage(msg);

        builder.setPositiveButton(R.string.yes, listener);
        builder.setNegativeButton(R.string.no, listener);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
