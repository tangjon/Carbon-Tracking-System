package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import cmpt276.jade.carbontracker.adapter.ImageRowAdapter;
import cmpt276.jade.carbontracker.model.Bus;
import cmpt276.jade.carbontracker.model.Emission;

/**
 * Can add some info for the bus here
 */
public class BusInfoActivity extends AppCompatActivity {

    private Bus incomingBus;
    private Bus outgoingBus;

    private ImageRowAdapter imgRowAdapter = new ImageRowAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_bus_info);
        getSupportActionBar().setTitle(R.string.businfoactivitytoolbarhint);

        setUpImageSelectionRow();
        getBusData();
        setupPage();
        setupNextBtn();

        // Quick fix
        if (incomingBus != null && incomingBus.getMode() == 1){
            imgRowAdapter.setImage(incomingBus.getImageId());
            imgRowAdapter.updateSelectedScreen();
        }
        hideSystemUI();
    }

    private void hideSystemUI() {
//        RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_menu);
//        layout.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideSystemUI();
//            }
//        });

        View mDecorView = getWindow().getDecorView();
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.

        int uiOptions =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE;

        mDecorView.setSystemUiVisibility(uiOptions);
    }

    private void setUpImageSelectionRow() {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.layout_table_img_select);
        tableLayout.addView(imgRowAdapter.getRow());
    }


    public static Intent getIntent(Context context) {
        return new Intent(context, BusInfoActivity.class);
    }

    private void setupPage() {
        if (incomingBus != null && incomingBus.getMode() == 1) {
            EditText inputName = (EditText) findViewById(R.id.editTextBusNickname);
            inputName.setText(incomingBus.getNickName());
            EditText inputRouteNumber = (EditText) findViewById(R.id.editTextRouteNumber);
            inputRouteNumber.setText(incomingBus.getRouteNumber());
        }
    }

    private void setupNextBtn() {
        Button btn = (Button) findViewById(R.id.btnBusInfoNext);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EditText inputName = (EditText) findViewById(R.id.editTextBusNickname);
                EditText inputRouteNumber = (EditText) findViewById(R.id.editTextRouteNumber);
                if(!imgRowAdapter.isImageSelected()){
                    TextView tv = (TextView) findViewById(R.id.tv_pick_icon_label);
                    tv.setError("");
                }
                else if (inputName.getText().toString().trim().length() == 0) {
                    inputName.setError(getString(R.string.enter_a_nickname));
                } else if (inputRouteNumber.getText().toString().trim().length() == 0) {
                    inputRouteNumber.setError(getString(R.string.enter_a_route_number));
                } else {
                    outgoingBus.setNickName(inputName.getText().toString().trim());
                    outgoingBus.setRouteNumber(inputRouteNumber.getText().toString().trim());
                    outgoingBus.setImageId(imgRowAdapter.getSelectedImage());
                    if (incomingBus == null || incomingBus.getMode() == 0) {
                        BusListActivity.recentBusList.addBus(outgoingBus);
                    } else if (incomingBus != null && incomingBus.getMode() == 1) {
                        BusListActivity.recentBusList.editBus(outgoingBus, incomingBus.getPosition());
                    }
                    finish();
                }

            }
        });
    }

    public void getBusData() {
        this.incomingBus = Emission.getInstance().getJourneyBuffer().getTransType().getBus();
//        imgRowAdapter.setImage(this.incomingBus.getImageId());
        this.outgoingBus = new Bus();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
