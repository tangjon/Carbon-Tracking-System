package cmpt276.jade.carbontracker.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import cmpt276.jade.carbontracker.R;
import cmpt276.jade.carbontracker.model.Car;
import cmpt276.jade.carbontracker.model.Journey;

import static android.content.ContentValues.TAG;

/**
 * Created by tangj on 3/16/2017.
 */

public class EditDialog extends DialogFragment {

    private EditDialogListener mHost;
    private int position;

    // Members
    String objectName;

    // KEY
    public static String KEY_NAME = "name";


    public static EditDialog newInstance(Car car) {
        Bundle args = new Bundle();
        EditDialog fragment = new EditDialog();
        args.putString(KEY_NAME, car.getName());
        fragment.setArguments(args);
        return fragment;
    }

    public static EditDialog newInstance(Journey journey) {
        Bundle args = new Bundle();
        EditDialog fragment = new EditDialog();
        args.putString(KEY_NAME, journey.getName());
        fragment.setArguments(args);
        return fragment;
    }

    public static EditDialog newInstance() {
        
        Bundle args = new Bundle();
        
        EditDialog fragment = new EditDialog();
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
        View v = inflater.inflate(R.layout.dialog_fragment_edit,null);
        // Display Custom Contents
        setUpTextView(v);

        // Confirm
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

    private void setUpTextView(View v){
        TextView tv_name = (TextView) v.findViewById(R.id.tv_item);
        if(!(objectName == null)){
            TableRow r = (TableRow) v.findViewById(R.id.extra_info);
            r.setVisibility(View.VISIBLE);
            tv_name.setText(objectName);
        }
    }


    private void readArguments(){
        objectName = getArguments().getString(KEY_NAME,null);
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

