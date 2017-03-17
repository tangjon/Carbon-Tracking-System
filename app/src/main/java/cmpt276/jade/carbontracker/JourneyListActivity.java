package cmpt276.jade.carbontracker;

import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.Button;
        import android.widget.ListAdapter;
        import android.widget.ListView;
        import android.widget.Toast;

        import java.nio.Buffer;

        import cmpt276.jade.carbontracker.adapter.RouteListAdapter;
        import cmpt276.jade.carbontracker.fragment.EditDialog;
        import cmpt276.jade.carbontracker.model.Car;
        import cmpt276.jade.carbontracker.model.Emission;
        import cmpt276.jade.carbontracker.model.Journey;
        import cmpt276.jade.carbontracker.model.JourneyCollection;
        import cmpt276.jade.carbontracker.model.Route;
        import cmpt276.jade.carbontracker.model.Transportation;
        import cmpt276.jade.carbontracker.utils.BillType;

/**
 *Journey List is your list of journeys and can either add a new journey going to car list or to the emissions overview
 * Can also edit journey entries or delete journey entries
 */
public class JourneyListActivity extends AppCompatActivity {
    public static JourneyCollection listOfJourneys = Emission.getInstance().getJourneyCollection();
    //  private Journey intentJourney;
    private int Mode = 0;

    public static Intent getJourneyListIntent(Context context) {
        return new Intent(context, JourneyListActivity.class);

    }

    //maybe have a get data from intent that can handle a journey being passed into it?
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(getString(R.string.JourneyListActivityHint));
        setContentView(R.layout.activity_journey_list);


        setupAddBtn();
        setupFootprintBtn();
        //getIntentData();
        setupClickJourneyList();
        populateList();
        checkFootprintBtn();
    }

    private void checkFootprintBtn() {
        Button button = (Button) findViewById(R.id.btnViewFootprint);
        if (listOfJourneys.countJourneys() == 0) button.setEnabled(false);
        else button.setEnabled(true);
    }

    private void setupFootprintBtn() {
        Button button = (Button) findViewById(R.id.btnViewFootprint);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CarbonFootprintActivity.getIntent(JourneyListActivity.this);
                startActivity(intent);
            }
        });
    }

    private void setupAddBtn() {
        Button button = (Button) findViewById(R.id.btnAddJourney);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transportation trans = new Transportation();
                Route route = new Route("TEMP ROUTE NAME AND DATA", -1, -1);
                Journey journey = new Journey("TEMP NAME", trans, route);
                Emission.getInstance().setJourneyBuffer(journey);
                //Intent intent = CarListActivity.getIntentFromActivity(JourneyListActivity.this);
                Intent intent = TransportSelectActivity.getTransportIntent(JourneyListActivity.this);
                //intent.putExtra("Journey", journey);
                startActivity(intent);
            }
        });

    }

    private void setupClickJourneyList() {
        //goto Journey Summary - short click
        ListView list = (ListView) findViewById(R.id.listviewJourney);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Emission.getInstance().setJourneyBuffer(listOfJourneys.getJourney(position));
                Intent intent = JourneySummaryActivity.getJourneySummaryIntent(JourneyListActivity.this);
                startActivity(intent);
            }
        });


        //edit - long click
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                EditDialog editDialog = new EditDialog();
                editDialog.setPosition(position);
                editDialog.setEditDialogListener(new EditDialog.EditDialogListener() {
                    @Override
                    public void onEditDialogDelete(int pos) {
                        setupDeleteAlert(pos);
                        populateList();
                    }

                    @Override
                    public void onEditDialogEdit(int pos) {
                        Intent intent = CarListActivity.getIntentFromActivity(JourneyListActivity.this);
                        Emission.getInstance().setJourneyBuffer(listOfJourneys.getJourney(pos));
                        Emission.getInstance().getJourneyBuffer().setPosition(pos);
                        Emission.getInstance().getJourneyBuffer().setMode(1);
                        startActivity(intent);
                    }
                });
                editDialog.show(getSupportFragmentManager(),"EditDialog");


                return true;
            }
        });
    }

    private void populateList() {
        //ListAdapter bucky=new RouteListAdapter(this,listOfJourneys.getJourneyDetails(),getMode());
        ListAdapter bucky=new RouteListAdapter(this,listOfJourneys.getJourneyDetails(),1);
        ListView list = (ListView) findViewById(R.id.listviewJourney);
        list.setAdapter(bucky);
    }
    // Inspired by Raz
    private void setupDeleteAlert( final int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Journey thisJourney = listOfJourneys.getJourney(index);
        builder.setMessage(getString(R.string.journey_list_confirm_delete_message, thisJourney.getName()));
        builder.setCancelable(true);

        builder.setPositiveButton(getString(R.string.label_delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listOfJourneys.deleteJourney(index);
                Emission.getInstance().setJourneyCollection(listOfJourneys);
                populateList();
            }
        });

        builder.setNegativeButton(getString(R.string.label_cancel), null);

        AlertDialog alert = builder.create();
        alert.show();
    }
}
