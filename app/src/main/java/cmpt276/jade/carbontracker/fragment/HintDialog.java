package cmpt276.jade.carbontracker.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import cmpt276.jade.carbontracker.R;

public class HintDialog extends Dialog implements android.view.View.OnClickListener {

    public Button btnNext;
    public TextView txtHint;
    public Activity activity;
    public Dialog dialog;

    public HintDialog(Activity a) {
        super(a);
        activity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_hint_dialog);

        btnNext = (Button) findViewById(R.id.btn_hint_next);
        txtHint = (TextView) findViewById(R.id.tv_hint);

        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // do your stuff

        dismiss();
    }

}
