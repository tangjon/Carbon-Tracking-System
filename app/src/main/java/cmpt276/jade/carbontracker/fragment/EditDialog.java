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
    private int position;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        position = getArguments().getInt("pos");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_fragment_edit,null);
        builder.setView(v);

        Button btn_delete = (Button) v.findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHost.onEditDialogDelete(position);
                dismiss();
            }
        });
        Button btn_edit = (Button) v.findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHost.onEditDialogEdit(position);
                dismiss();
            }
        });

        return builder.create();

    }
    public void setPosition(int pos){
        position = pos;
    }

    public void setEditDialogListener(EditDialogListener e){
        mHost = e;
    }

    public interface EditDialogListener{
        public void onEditDialogDelete(int pos);
        public void onEditDialogEdit(int pos);
    }
}

