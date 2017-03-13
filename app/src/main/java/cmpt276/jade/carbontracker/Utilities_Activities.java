package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import cmpt276.jade.carbontracker.adapter.UtilitiesAdapter;
import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.model.Utilities;

public class Utilities_Activities extends AppCompatActivity {
    private Emission emission = Emission.getInstance();
    private Utilities utilities;

    private ArrayList<Object> dummyData1 = new ArrayList<>();       // ***
    private ArrayList<Object> dummyData2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utilities__activities);


        loadData();
        setupButtons();
        setupEditText();
        setupLists();
    }

    private void loadData() {
        utilities = emission.getUtilities();

        dummyData1.add("Dummy object");
        dummyData2.add("Another dummy object");

        ListView listElec = (ListView) findViewById(R.id.list_elec);
        ListView listGas = (ListView) findViewById(R.id.list_gas);
        UtilitiesAdapter adapterElec = new UtilitiesAdapter(this, dummyData1);
        UtilitiesAdapter adapterGas = new UtilitiesAdapter(this, dummyData2);
        listElec.setAdapter(adapterElec);
        listGas.setAdapter(adapterGas);
    }

    private void setupEditText() {
        final EditText editRes = (EditText) findViewById(R.id.edit_residents);

        editRes.setText(String.valueOf(utilities.getNumResidents()));

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = editRes.getText().toString();
                int i = 0;

                if (!text.isEmpty()) i = Integer.parseInt(text);
                if (i > 0) utilities.setNumResidents(i);
                else {
                    Toast.makeText(Utilities_Activities.this, R.string.toast_bad_residents,
                            Toast.LENGTH_SHORT).show();
                }
            }
        };
        editRes.addTextChangedListener(watcher);
    }

    private void setupLists() {
        ListView listElec = (ListView) findViewById(R.id.list_elec);
        ListView listGas = (ListView) findViewById(R.id.list_gas);

        listElec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: 12/03/17 edit elec bill
            }
        });

        listElec.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: 12/03/17 delete elec bill
                return false;
            }
        });

        listGas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: 12/03/17 edit gas bill
            }
        });

        listGas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: 12/03/17 delete gas bill
                return false;
            }
        });
    }

    private void setupButtons() {
        Button btnNewElec = (Button) findViewById(R.id.btn_new_electric);
        Button btnNewGas = (Button) findViewById(R.id.btn_new_gas);

        btnNewElec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 12/03/17 add elec bill
            }
        });

        btnNewGas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 12/03/17 add gas bill
            }
        });
    }

    public static Intent getUtilitiesIntent(Context context) {
        return new Intent(context, Utilities_Activities.class);
    }
}
