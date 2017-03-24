package cmpt276.jade.carbontracker.fragment;

import android.app.Dialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

        // retrieve display dimensions
        Rect displayRectangle = new Rect();
//        Window window = getActivity().getWindow();
//        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

        // Build new builder
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // Inflate Layout
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_tips, null);
//        v.setMinimumWidth((int)(displayRectangle.width() * 0.9f));
//        v.setMinimumHeight((int)(displayRectangle.height() * 0.9f));
        // Confirm
        builder.setView(v);

        final TextView tv = (TextView) v.findViewById(R.id.tv_tip_display);

        Button btn_next = (Button) v.findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHost.onNextClicked(tv);
            }
        });

        Button btn_close = (Button) v.findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHost.onCloseClicked(tv);
                dismiss();
            }
        });

        return builder.create();
    }

    public void setTipDialogListener(TipDialogListener e) {
        mHost = e;
    }

    public interface TipDialogListener{
        public void onNextClicked(TextView tv);

        public void onCloseClicked(TextView tv);
    }
}
