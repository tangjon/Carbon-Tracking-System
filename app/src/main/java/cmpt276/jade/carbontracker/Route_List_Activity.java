package cmpt276.jade.carbontracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import cmpt276.jade.carbontracker.adapter.RouteListAdapter;
import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.model.Journey;
import cmpt276.jade.carbontracker.model.OtherRoute;
import cmpt276.jade.carbontracker.model.Route;
import cmpt276.jade.carbontracker.model.RouteCollection;

/**
 * List of routes that can be used in journey click route to proceed
 */

public class Route_List_Activity extends AppCompatActivity {

    public static final int RECEIVE_ROUTE = 1024; //intent numer for add
    public static final int EDIT_ROUTE = 1025; //intent number for edit/delete
   // private Journey journey;
    public static final int EDIT_ROUTE2 = 1026; //otherroute intent number for edit/delete

    public static RouteCollection routes = new RouteCollection();

    public static Intent IntentForRouteList(Context context,int mode) {
        Intent intent = new Intent(context, Route_List_Activity.class);
        intent.putExtra("Mode", mode);
        return intent;
    }
    // 1 for car, 2 for walk and bike, 3 for trans
    private int getMode() {
        Intent intent = getIntent();
        int mode = intent.getIntExtra("Mode",0);
        return mode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle(getString(R.string.route_list_hint));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_route_list);
       // getCarListData();
        setup_Add_Btn();
        populateListView();
        long_pressing_editAndDelete();


    }

    private void populateListView() {
        String[] lotsofRoute = routes.Detail();
        ListAdapter bucky=new RouteListAdapter(this,lotsofRoute);
        ListView list = (ListView) findViewById(R.id.Route_list_routeList);
        list.setAdapter(bucky);
    }
/*
    //Sean - Gets the journey object
    private void getCarListData() {
        Intent intent = getIntent();
        this.journey = (Journey)intent.getSerializableExtra("Journey");
    }
*/
    //long pressing for edit and delete
    private void long_pressing_editAndDelete() {
        ListView list = (ListView) findViewById(R.id.Route_list_routeList);

        //Sean - Adding method to send data back to journeylist
        //Alex - changed journey by mode
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Route route = routes.getRouteByIndex(position);
                if(getMode()==1 && route.getCityDistance()<0)
                {
                    Toast.makeText(getApplicationContext(),
                          "You can't click other routes "
                                  + "Except Car route,"
                                  + "Since you are now in add car route mode"
                                  , Toast.LENGTH_LONG).show();

                }
                if(getMode()==1 && route.getCityDistance()>0)
                {
                Emission.getInstance().getJourneyBuffer().setRoute(route);
                Intent intent = JourneyReviewActivity.getJourneyReviewIntent(Route_List_Activity.this);
                //Should clear the whole back stack besides main menu
                startActivity(intent);}
                else
                {
                    OtherRoute newroute= new OtherRoute
                            (route.getName(),route.getHighWayDistance(),getMode());
                    //TODO TO Sean I have created new class: OtherRoute
                    //which is for bike,walk,bus and skytrain
                    //OtherRoute(string nickname,double distance, mode)
                    //mode 1 for car
                    //mode 2 for bike and walk
                    //mode 3 for bus
                    //mode 4 for skytrain
                    //journey.setRoute(route);
                    //Intent intent = JourneyReviewActivity.getJourneyReviewIntent(Route_List_Activity.this);
                    //intent.putExtra("Journey", journey);
                    //Should clear the whole back stack besides main menu
                    //startActivity(intent);
                }
            }
        });


        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String strPosition = Integer.toString(position);
                Route clickedRoute = routes.getRouteByIndex(position);

                if(getMode()==1 && clickedRoute.getCityDistance()<0)
                {
                    Toast.makeText(getApplicationContext(),
                            "You can't Edit other routes "
                                    + "Except Car route,"
                                    + "Since you are now in add car route mode"
                            , Toast.LENGTH_LONG).show();

                }
                if(getMode()==1 && clickedRoute.getCityDistance()>0)
                {Intent Intent_for_editing = Route_Info_Activity.IntentForEditRoute(Route_List_Activity.this, clickedRoute, strPosition);
                startActivityForResult(Intent_for_editing, EDIT_ROUTE);}
                if(getMode()!=1 && clickedRoute.getCityDistance()<0)
                {
                    Intent Intent_for_editing_other_route = Bike_and_Trans_Info_Activity.IntentForEditRoute(Route_List_Activity.this, clickedRoute, strPosition);
                    startActivityForResult(Intent_for_editing_other_route, EDIT_ROUTE2);
                    //Toast.makeText(getApplicationContext(),
                     //       "mode: " + getMode(), Toast.LENGTH_LONG).show();

                }
                if(getMode()!=1 && clickedRoute.getCityDistance()>0)
                {Toast.makeText(getApplicationContext(),
                           "You can't edit car routes,"
                                   +"Since you are in mode adding:"
                        +getMode(), Toast.LENGTH_LONG).show();

                }
                return true;
            }
        });

    }

    private void setup_Add_Btn() {
        int mode=getMode();
        //car
        if(mode==1)
        {
        Button btn = (Button) findViewById(R.id.Route_List_add_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Route_Info_Activity.IntentForAddingRoute(Route_List_Activity.this);
                startActivityForResult(intent, RECEIVE_ROUTE);
            }
        });}
        //bike and walk
        if(mode==2)
        {
            Button btn = (Button) findViewById(R.id.Route_List_add_btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = Bike_and_Trans_Info_Activity.IntentForAddingRoute(Route_List_Activity.this,2);
                    startActivityForResult(intent, RECEIVE_ROUTE);
                }
            });}

        //BUS

        if(mode==3)
        {
            Button btn = (Button) findViewById(R.id.Route_List_add_btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = Bike_and_Trans_Info_Activity.IntentForAddingRoute(Route_List_Activity.this,3);
                    startActivityForResult(intent, RECEIVE_ROUTE);
                }
            });}

         //skytrain

        if(mode==4)
        {
            Button btn = (Button) findViewById(R.id.Route_List_add_btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = Bike_and_Trans_Info_Activity.IntentForAddingRoute(Route_List_Activity.this,4);
                    startActivityForResult(intent, RECEIVE_ROUTE);
                }
            });}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RECEIVE_ROUTE:
                int mode=getMode();
                //car
                if(mode==1) {
                    if (resultCode == Activity.RESULT_OK) {
                        String RouteName = data.getStringExtra("pass back the route name");
                        String StrHighWay = data.getStringExtra("pass back the highway");
                        String StrCity = data.getStringExtra("pass back the city");
                        double highway = Double.parseDouble(StrHighWay);
                        double city = Double.parseDouble(StrCity);
                        Route addedRoute = new Route(RouteName, highway, city);
                        routes.addRoute(addedRoute);
                        populateListView();
                    }
                }
                //bike or walk
                if(mode==2) {
                    if (resultCode == Activity.RESULT_OK) {
                        String RouteName = data.getStringExtra("pass back the route name");
                        String StrWalkDistance = data.getStringExtra("pass back the distance");
                        double distance = Double.parseDouble(StrWalkDistance);
                        Route addedRoute = new Route(RouteName, distance, -1);
                        routes.addRoute(addedRoute);
                        populateListView();

                    }
                }

                //bus
                if(mode==3) {
                    if (resultCode == Activity.RESULT_OK) {
                        String RouteName = data.getStringExtra("pass back the route name");
                        String StrWalkDistance = data.getStringExtra("pass back the distance");
                        double distance = Double.parseDouble(StrWalkDistance);
                        Route addedRoute = new Route(RouteName, distance, -2);
                        routes.addRoute(addedRoute);
                        populateListView();

                    }
                }

                //skytrain
                if(mode==4) {
                    if (resultCode == Activity.RESULT_OK) {
                        String RouteName = data.getStringExtra("pass back the route name");
                        String StrWalkDistance = data.getStringExtra("pass back the distance");
                        double distance = Double.parseDouble(StrWalkDistance);
                        Route addedRoute = new Route(RouteName, distance, -3);
                        routes.addRoute(addedRoute);
                        populateListView();

                    }
                }






                break;
            case EDIT_ROUTE:
                if (resultCode == Activity.RESULT_OK) {
                    String StrDelete = data.getStringExtra("delete is clicked");
                    String StrIndex = data.getStringExtra("pass back the route position");
                    int delete = Integer.parseInt(StrDelete);
                    int index = Integer.parseInt(StrIndex);
                    //if user want to delete,not edit
                    if (delete == 1) {
                        routes.deleteRoute(index);
                        populateListView();
                    }
                    //if user want to edit route
                    else {
                        String RouteName = data.getStringExtra("pass back the route name");
                        String StrHighWay = data.getStringExtra("pass back the highway");
                        String StrCity = data.getStringExtra("pass back the city");
                        double highway =  Double.parseDouble(StrHighWay);
                        double city =  Double.parseDouble(StrCity);
                        Route Clicked = new Route(RouteName, highway, city);
                        routes.changeRoute(Clicked, index);
                        populateListView();
                    }}
                break;

            case EDIT_ROUTE2:
                if (resultCode == Activity.RESULT_OK) {
                    String StrDelete = data.getStringExtra("delete is clicked");
                    String StrIndex = data.getStringExtra("pass back the route position");
                    int delete = Integer.parseInt(StrDelete);
                    int index = Integer.parseInt(StrIndex);
                    //if user want to delete,not edit
                    if (delete == 1) {
                        routes.deleteRoute(index);
                        populateListView();
                    }
                    //if user want to edit route
                    else {

                        int dacity=getDAcity();

                        String RouteName = data.getStringExtra("pass back the route name");
                        String StrDistance = data.getStringExtra("pass back the distance");
                        double Distance =  Double.parseDouble(StrDistance);
                        Route Clicked = new Route(RouteName, Distance, dacity);
                        routes.changeRoute(Clicked, index);
                        populateListView();
                    }}
                break;
        }}

    private int getDAcity() {
        int damode=getMode();
        if(damode==2)
        {return -1;}
        if(damode==3){return -2;}
        if(damode==4){return -3;}
        return 0;
    }


}
