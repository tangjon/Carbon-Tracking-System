package cmpt276.jade.carbontracker.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import cmpt276.jade.carbontracker.R;

/**
 * Created by tangj on 3/23/2017.
 * Class: Tip Dialog
 * Description: Dialog used for displaying tips
 * Bugs:
 */



public class TipDialog extends DialogFragment {

    private TipDialogListener mHost;
    private int position;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Build new builder
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // Inflate Layout
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_tips, null);
        // Confirm
        builder.setView(v);

        TextView tv = (TextView) v.findViewById(R.id.tv_tip_display);

        Button btn_next = (Button) v.findViewById(R.id.btn_close);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHost.onNextClicked();
            }
        });

        Button btn_close = (Button) v.findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHost.onCloseClicked();
                dismiss();
            }
        });

        return builder.create();
    }

    public interface TipDialogListener{
        public void onNextClicked();

        public void onCloseClicked();
    }
}
