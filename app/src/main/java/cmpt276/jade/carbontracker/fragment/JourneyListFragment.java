package cmpt276.jade.carbontracker.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import cmpt276.jade.carbontracker.CarListActivity;
import cmpt276.jade.carbontracker.CarbonFootprintActivity;
import cmpt276.jade.carbontracker.JourneyListActivity;
import cmpt276.jade.carbontracker.JourneySummaryActivity;
import cmpt276.jade.carbontracker.R;
import cmpt276.jade.carbontracker.TransportSelectActivity;
import cmpt276.jade.carbontracker.adapter.RouteListAdapter;
import cmpt276.jade.carbontracker.model.Car;
import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.model.Journey;
import cmpt276.jade.carbontracker.model.JourneyCollection;
import cmpt276.jade.carbontracker.model.Route;
import cmpt276.jade.carbontracker.model.Transportation;

/**
 * unused based of journey list activity
 */
public class JourneyListFragment extends Fragment {
    public static JourneyCollection listOfJourneys = Emission.getInstance().getJourneyCollection();
    private Journey intentJourney;
    private int Mode = 0;

    // View for scope
    View v = null;

    public JourneyListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Set ToolBar Title
        getActivity().setTitle(R.string.nav_journeys);

        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_journey_list, container, false);

        setupAddBtn();
        setupFootprintBtn();
        getIntentData();
        setupClickJourneyList();
        setupDeletebtn();
        populateList();
        checkFootprintBtn();
        return v;
    }

    private void checkFootprintBtn() {
        Button button = (Button) v.findViewById(R.id.btnViewFootprint);
        if (listOfJourneys.countJourneys() == 0) button.setEnabled(false);
        else button.setEnabled(true);
    }

    private void setupDeletebtn() {
        final Button button = (Button) v.findViewById(R.id.btnMode);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Mode == 1){
                    Mode = 0;
                    setupClickJourneyList();
                    button.setText(getString(R.string.label_edit));
                    Toast.makeText(getContext(), "Edit mode enabled.", Toast.LENGTH_SHORT).show();
                }
                else if(Mode == 0){
                    Mode = 1;
                    toggleDeleteMode();
                    button.setText(getString(R.string.label_delete));
                    Toast.makeText(getContext(), "Delete mode enabled.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private void setupFootprintBtn() {
        Button button = (Button) v.findViewById(R.id.btnViewFootprint);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CarbonFootprintActivity.getIntent(getContext());
                startActivity(intent);
            }
        });
    }

    private void setupAddBtn() {
        Button button = (Button) v.findViewById(R.id.btnAddJourney);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transportation trans = new Transportation();
                Route route = new Route("TEMP ROUTE NAME AND DATA", -1, -1);
                Journey journey = new Journey("TEMP", trans, route);
                //Intent intent = CarListActivity.getIntentFromActivity(getActivity());
                Intent intent = TransportSelectActivity.getTransportIntent(getContext());
                intent.putExtra("Journey", journey);
                startActivity(intent);
            }
        });

    }

    private void setupClickJourneyList() {
        //goto Journey Summary - short click
        ListView list = (ListView) v.findViewById(R.id.listviewJourney);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = JourneySummaryActivity.getJourneySummaryIntent(getContext());
                Journey journey = listOfJourneys.getJourney(position);
                intent.putExtra("Journey", journey);
                startActivity(intent);
            }
        });


        //edit - long click
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = CarListActivity.getIntentFromActivity(getContext());
                Journey journey = listOfJourneys.getJourney(position);
                journey.setPosition(position);
                journey.setMode(1);
                intent.putExtra("Journey", journey);
                startActivity(intent);
                return true;
            }
        });
    }

    private void populateList() {
        //the adapter have one more mode type
        //ListAdapter bucky=new RouteListAdapter(getContext(),listOfJourneys.getJourneyDetails(),getmode());
        ListAdapter bucky=new RouteListAdapter(getContext(),listOfJourneys.getJourneyDetails(),1);
        ListView list = (ListView) v.findViewById(R.id.listviewJourney);
        list.setAdapter(bucky);
    }

    public void getIntentData() {
        Intent intent = getActivity().getIntent();
        Journey journey = (Journey)intent.getSerializableExtra("Journey");
        if(journey != null) {
            if (journey.getMode() == 0) {
                this.intentJourney = journey;
                listOfJourneys.addJourney(journey);
                Emission.getInstance().setJourneyCollection(listOfJourneys);
            } else if (journey.getMode() == 1) {
                this.intentJourney = journey;
                listOfJourneys.editJourney(intentJourney, intentJourney.getPosition());
                Emission.getInstance().setJourneyCollection(listOfJourneys);
            }
        }
    }

    private void toggleDeleteMode() {
        //goto Journey Summary - short click
        ListView list = (ListView) v.findViewById(R.id.listviewJourney);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = JourneySummaryActivity.getJourneySummaryIntent(getContext());
                Journey journey = listOfJourneys.getJourney(position);
                intent.putExtra("Journey", journey);
                startActivity(intent);
            }
        });


        //edit - long click
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                listOfJourneys.deleteJourney(position);
                Emission.getInstance().setJourneyCollection(listOfJourneys);
                populateList();
                return true;
            }
        });
    }
}
