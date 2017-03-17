package cmpt276.jade.carbontracker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cmpt276.jade.carbontracker.model.Bill;
import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.model.Utilities;
import cmpt276.jade.carbontracker.utils.BillEditMode;
import cmpt276.jade.carbontracker.utils.BillType;

public class UtilityEditActivity extends AppCompatActivity {
    private Emission emission;
    private Bill buffer;
    private BillEditMode mode;
    private BillType type;
    private int index;
    private DatePickerDialog datePickerStart;
    private DatePickerDialog datePickerEnd;
    private EditText editDateStart;
    private EditText editDateEnd;
    private Calendar calendar;
    public static SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utility_edit);

        emission = Emission.getInstance();
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        getIntentData();
        setupUI();
        setupButtons();
        setupDatePickers();
    }

    private void setupDatePickers() {
        datePickerStart = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar dateNew = Calendar.getInstance();
                dateNew.set(year, month, dayOfMonth);

                if (dateNew.getTime().before(buffer.getEndDate())) {
                    editDateStart.setText(dateFormat.format(dateNew.getTime()));
                    buffer.setStartDate(dateNew.getTime());
                } else {
                    Toast.makeText(UtilityEditActivity.this,
                            getString(R.string.toast_bad_date), Toast.LENGTH_SHORT).show();
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerEnd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar dateNew = Calendar.getInstance();
                dateNew.set(year, month, dayOfMonth);

                if (dateNew.getTime().after(buffer.getStartDate())) {
                    editDateEnd.setText(dateFormat.format(dateNew.getTime()));
                    buffer.setEndDate(dateNew.getTime());
                } else {
                    Toast.makeText(UtilityEditActivity.this,
                            getString(R.string.toast_bad_date), Toast.LENGTH_SHORT).show();
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void setupButtons() {
        Button btnPreview = (Button) findViewById(R.id.btnPreview);
        Button btnAccept = (Button) findViewById(R.id.btnAccept);
        Button btnCancel = (Button) findViewById(R.id.btnCancel);

        btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previewEmissions();
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editInput = (EditText) findViewById(R.id.editBillInput);
                if (!editInput.getText().toString().isEmpty()) {
                    Utilities utils = emission.getUtilities();

                    if (mode == BillEditMode.ADD)
                        utils.addBill(type, buffer);
                    else utils.editBill(type, buffer, index);
                    finish();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // TODO: 14/03/17 extract strings
    private void previewEmissions() {
        EditText editInput = (EditText) findViewById(R.id.editBillInput);
        if (!editInput.getText().toString().isEmpty()) {
            double input = Double.parseDouble(editInput.getText().toString());
            buffer.setInput(input);

            TextView tvPreview = (TextView) findViewById(R.id.txt_bill_preview);
            String text = "Total Emissions: " + Math.round(buffer.getEmissionTotal()) +
                    "kg CO2, Average Emissions: " + Math.round(buffer.getEmissionAvg()) + "kg CO2";

            tvPreview.setText(text);
        }
    }

    private void setupUI() {
        editDateStart = (EditText) findViewById(R.id.editBillStart);
        editDateEnd = (EditText) findViewById(R.id.editBillEnd);

        editDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerStart.show();
            }
        });

        editDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerEnd.show();
            }
        });

        TextView tvInput = (TextView) findViewById(R.id.txt_input);
        String[] inputPrompt = getResources().getStringArray(R.array.label_bill_input);

        if (mode == BillEditMode.EDIT) {
            EditText editInput = (EditText) findViewById(R.id.editBillInput);

            editDateStart.setText(dateFormat.format(buffer.getStartDate()));
            editDateEnd.setText(dateFormat.format(buffer.getEndDate()));
            editInput.setText(String.valueOf(buffer.getInput()));
        }

        if (type == BillType.ELECTRIC) tvInput.setText(inputPrompt[0]);
        else tvInput.setText(inputPrompt[1]);
    }

    public static Intent getUtilityEditIntent(Context context) {
        return new Intent(context, UtilityEditActivity.class);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        mode = (BillEditMode) intent.getSerializableExtra("mode");
        type = (BillType) intent.getSerializableExtra("type");
        index = intent.getIntExtra("index", 0);

        if (mode == BillEditMode.EDIT) buffer = emission.getBufferBill();
        else buffer = new Bill(type, new Date(), new Date(), 0);
    }

}