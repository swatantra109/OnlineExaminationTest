package com.xentaqsys.oet;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;


public class AlertDialogManager {

    /**
     * Function to display simple Alert Dialog
     * @param context - application context
     * @param title - alert dialog title
     * @param message - alert message
     * @param status - success/failure (used to set icon)
     *               - pass null if you don't want icon
     * */
    public void showAlertDialog(final Context context, String title, String message,
                                Boolean status) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        if(status != null)
            // Setting alert dialog icon
            alertDialog.setIcon((status) ? R.drawable.ic_wifi_grey600_48dp : R.drawable.ic_wifi_grey600_48dp);

        // Setting OK Button



        // set positive button: Yes message



        // set negative button: No message

        alertDialog.setNegativeButton("OK",new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                // cancel the alert box and put a Toast to the user
                int p = android.os.Process.myPid();
                android.os.Process.killProcess(p);
                dialog.cancel();



            }

        });


        // Showing Alert Message
        alertDialog.show();
    }
}
