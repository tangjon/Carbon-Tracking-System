package cmpt276.jade.carbontracker.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import cmpt276.jade.carbontracker.R;

/**
 * Created by tangj on 3/23/2017.
 * Class: Tip Dialog
 * Description: Dialog used for displaying tips
 * Bugs:
 */



public class TipDialog extends DialogFragment {

    private EditDialog.EditDialogListener mHost;
    private int position;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Build new builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // Inflate Layout
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_tips, null);
        // Confirm
        builder.setView(v);








        return builder.create();
    }

    public interface TipDialogListener{
        public void onNextClicked();

        public void onCloseClicked();
    }
}
