package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import cmpt276.jade.carbontracker.adapter.UtilitiesAdapter;
import cmpt276.jade.carbontracker.database.DBAdapter;
import cmpt276.jade.carbontracker.model.Bill;
import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.model.Tip;
import cmpt276.jade.carbontracker.model.Utilities;
import cmpt276.jade.carbontracker.enums.BillEditMode;
import cmpt276.jade.carbontracker.enums.BillType;

/*
      Shows list of bills
 */
public class Utilities_Activities extends AppCompatActivity {
    private Emission emission = Emission.getInstance();
    private Utilities utilities;

    private BillEditMode mode;
    private BillType type;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.utilitiesactivitytoolbarhint);
        setContentView(R.layout.activity_utilities__activities);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        utilities = emission.getUtilities();

        loadData();
        setupButtons();
        setupEditText();
        setupLists();
        getElectionTips();
    }

    private void setupDeleteAlert(final BillType type, final int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getString(R.string.label_delete_confirm));
        builder.setCancelable(true);

        builder.setPositiveButton(getString(R.string.label_delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                utilities.deleteBill(type, index);
                loadData();
            }
        });

        builder.setNegativeButton(getString(R.string.label_cancel), null);

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadData();
    }

    private void dbRefreshBillTable() {
        DBAdapter db = new DBAdapter(this);
        db.open();
        db.deleteAll(DBAdapter.DB_TABLE.BILL);

        for (Bill b : Emission.getInstance().getUtilities().getListBillElec()) {
            db.insertRow(b);
        }
        for (Bill b : Emission.getInstance().getUtilities().getListBillGas()) {
            db.insertRow(b);
        }
        db.close();
    }

    private void loadData() {

        dbRefreshBillTable();

        ListView listElec = (ListView) findViewById(R.id.list_elec);
        ListView listGas = (ListView) findViewById(R.id.list_gas);
        UtilitiesAdapter adapterElec = new UtilitiesAdapter(this, emission.getUtilities().getListBillElec());
        UtilitiesAdapter adapterGas = new UtilitiesAdapter(this, emission.getUtilities().getListBillGas());
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
                    i = 1;
                    editRes.setText(String.valueOf(i));
                    utilities.setNumResidents(i);
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
                mode = BillEditMode.EDIT;
                type = BillType.ELECTRIC;
                index = position;

                Intent intent = UtilityEditActivity.getUtilityEditIntent(Utilities_Activities.this);
                intent.putExtra("mode", mode);
                intent.putExtra("type", type);
                intent.putExtra("index", position);
                emission.setBufferBill(utilities.getListBillElec().get(index));
                startActivity(intent);
            }
        });

        listElec.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                setupDeleteAlert(BillType.ELECTRIC, position);
                return true;
            }
        });

        listGas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mode = BillEditMode.EDIT;
                type = BillType.GAS;
                index = position;

                Intent intent = UtilityEditActivity.getUtilityEditIntent(Utilities_Activities.this);
                intent.putExtra("mode", mode);
                intent.putExtra("type", type);
                intent.putExtra("index", position);
                emission.setBufferBill(utilities.getListBillGas().get(index));
                startActivity(intent);
            }
        });

        listGas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                setupDeleteAlert(BillType.GAS, position);
                return true;
            }
        });
    }

    private void setupButtons() {
        Button btnNewElec = (Button) findViewById(R.id.btn_new_electric);
        Button btnNewGas = (Button) findViewById(R.id.btn_new_gas);

        btnNewElec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = UtilityEditActivity.getUtilityEditIntent(Utilities_Activities.this);
                intent.putExtra("mode", BillEditMode.ADD);
                intent.putExtra("type", BillType.ELECTRIC);
                startActivity(intent);
            }
        });

        btnNewGas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = UtilityEditActivity.getUtilityEditIntent(Utilities_Activities.this);
                intent.putExtra("mode", BillEditMode.ADD);
                intent.putExtra("type", BillType.GAS);
                startActivity(intent);
            }
        });
    }

    public static Intent getUtilitiesIntent(Context context) {
        return new Intent(context, Utilities_Activities.class);
    }


    Tip tipForElection = new Tip();

    private void getElectionTips() {
        final TextView tv = (TextView) findViewById(R.id.utility_tips);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tip1 = tipForElection.tipsForElectricityEmissions();
                tv.setText(tip1);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }


}
