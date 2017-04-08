package cmpt276.jade.carbontracker.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import cmpt276.jade.carbontracker.R;
import cmpt276.jade.carbontracker.enums.Transport;
import cmpt276.jade.carbontracker.model.Car;
import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.model.Journey;

/**
 * Created by tangj on 3/16/2017.
 * <p>
 * For a nice uniform edit and delete function for lists dialog
 */

public class EditDialog extends DialogFragment {

    // KEY
    private static String KEY_NAME = "name";
    private static String KEY_MODE = "mode";
    // Members
    String objectName;
    Transport objectMode;
    String thisMode;
    private EditDialogListener mHost;
    private int position;

    // Todo pass image id instead of transport
    public static EditDialog newInstance(String title, Transport mode) {
        Bundle args = new Bundle();
        EditDialog fragment = new EditDialog();
        args.putString(KEY_NAME, title);
        args.putSerializable(KEY_MODE, mode);
        fragment.setArguments(args);
        return fragment;
    }

    // Todo pass image id instead of transport
    public static EditDialog newInstance(String title, int thisMode) {
        Transport mode = null;
        switch (thisMode) {
            case 1:
                mode = Transport.CAR;
                break;
            case 2:
                mode = Transport.BIKE;
                break;
            case 3:
                mode = Transport.BUS;
                break;

            case 4:
                mode = Transport.SKYTRAIN;
                break;
            case 5:
                mode = Transport.WALK;
                break;

        }
        Bundle args = new Bundle();
        EditDialog fragment = new EditDialog();
        args.putString(KEY_NAME, title);
        args.putSerializable(KEY_MODE, mode);
        fragment.setArguments(args);
        return fragment;
    }

    public static EditDialog newInstance(Journey journey) {
        Transport mode = journey.getTransType().getTransMode();
        Bundle args = new Bundle();
        EditDialog fragment = new EditDialog();
        args.putString(KEY_NAME, journey.getName());
        args.putSerializable(KEY_MODE, mode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Read Arguments
        readArguments();

        // Build new builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Inflate Layout
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_fragment_edit, null);
        // Display Custom Contents

//        if (objectName != null) {
//            setUpTextView(v);
//        }
//        if (objectMode != null) {
//            setUpImageView(v);
//        }

        // Confirm
        builder.setView(v);
        Button btn_delete = (Button) v.findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHost.onDeleteClicked(position);
                dismiss();
            }
        });
        Button btn_edit = (Button) v.findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHost.onEditClicked(position);
                dismiss();
            }
        });
        return builder.create();

    }

    private void setUpTextView(View v) {
        TextView tv_name = (TextView) v.findViewById(R.id.tv_item);
        if (!(objectName == null)) {
            TableRow r = (TableRow) v.findViewById(R.id.extra_info);
            r.setVisibility(View.VISIBLE);
            tv_name.setText(objectName);
        }
    }

    private void setUpImageView(View v) {
        ImageView iv = (ImageView) v.findViewById(R.id.dialog_image);
        Journey j = Emission.getInstance().getJourneyCollection().getJourney(position);
        switch (objectMode) {
            case CAR:
//                iv.setImageResource(R.drawable.car);
                iv.setImageResource(j.getTransType().getCar().getImageId());
                break;
            case BUS:
//                iv.setImageResource(R.drawable.bus);
                iv.setImageResource(j.getTransType().getBus().getImageId());
                break;
            case SKYTRAIN:
//                iv.setImageResource(R.drawable.skytrain);
                iv.setImageResource(j.getTransType().getSkytrain().getImageId());
                break;
            case WALK:
                iv.setImageResource(R.drawable.walksymbol);
                break;
            case BIKE:
                iv.setImageResource(R.drawable.bike);
                break;

        }
    }


    private void readArguments() {
        objectName = getArguments().getString(KEY_NAME, null);
        objectMode = (Transport) getArguments().getSerializable(KEY_MODE);
    }


    public void setPosition(int pos) {
        position = pos;
    }


    public void setEditDialogListener(EditDialogListener e) {
        mHost = e;
    }


    public interface EditDialogListener {
        public void onDeleteClicked(int pos);

        public void onEditClicked(int pos);
    }
}

