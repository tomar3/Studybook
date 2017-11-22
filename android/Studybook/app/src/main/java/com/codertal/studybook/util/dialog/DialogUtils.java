/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.util.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.codertal.studybook.R;

public class DialogUtils {

    public static void displayTextInputDialog(Context context, String title, String message, String inputHint,
                                              View layoutView, TextSubmitListener textSubmitListener,
                                              DialogInterface.OnClickListener negativeListener) {

        final TextInputEditText input = layoutView.findViewById(R.id.et_input);
        final TextInputLayout inputLayout = layoutView.findViewById(R.id.input_layout);
        inputLayout.setHint(inputHint);

        AlertDialog inputDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setView(layoutView)
                .setPositiveButton(context.getString(R.string.okay_label), null)
                .setNegativeButton(context.getString(R.string.cancel_label), negativeListener)
                .create();

        inputDialog.show();

        inputDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String inputText = input.getText().toString();

            if(inputText.isEmpty()){
                input.setError(context.getString(R.string.required_label));
            }else {
                textSubmitListener.onTextSubmit(inputText);
                inputDialog.dismiss();
            }
        });

        inputDialog.setCanceledOnTouchOutside(false);
    }
}
