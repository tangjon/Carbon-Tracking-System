package cmpt276.jade.carbontracker.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import cmpt276.jade.carbontracker.R;

/**
 * Created by tangj on 3/16/2017.
 */

public class EditDialog extends DialogFragment {

    private EditDialogListener mHost;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_fragment_edit,null);
        builder.setView(v);

        Button btn_delete = (Button) v.findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHost.onEditDialogDelete();
                dismiss();
            }
        });
        Button btn_edit = (Button) v.findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHost.onEditDialogEdit();
                dismiss();
            }
        });

        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mHost = (EditDialogListener) context;
    }

    public interface EditDialogListener{
        public void onEditDialogDelete();
        public void onEditDialogEdit();
    }
}

