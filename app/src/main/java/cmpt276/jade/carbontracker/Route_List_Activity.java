package cmpt276.jade.carbontracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import cmpt276.jade.carbontracker.adapter.RouteListAdapter;
import cmpt276.jade.carbontracker.database.DBAdapter;
import cmpt276.jade.carbontracker.enums.Transport;
import cmpt276.jade.carbontracker.fragment.EditDialog;
import cmpt276.jade.carbontracker.fragment.EditDialog.EditDialogListener;
import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.model.Route;
import cmpt276.jade.carbontracker.model.RouteCollection;

/**
 * List of routes that can be used in journey click route to proceed
 */

public class Route_List_Activity extends AppCompatActivity {

    private static final String Num_Routes = "num routes in list";
    private static final String Name_Route = "route name";
    private static final String City_Route = "city distance";
    private static final String HighWay_Route = "highway distance";
    private static final String Mode_Route = "route mode";
    private static final String OtherDistance_Route = "distance for bike,walk,trans";

    public static final int RECEIVE_ROUTE = 1024; //intent numer for add
    public static final int EDIT_ROUTE = 1025; //intent number for edit/delete
    // private Journey journey;
    public static final int EDIT_ROUTE2 = 1026; //otherroute intent number for edit/delete

    public static RouteCollection CarRoutesList = new RouteCollection();
    public static RouteCollection BikeNWalkRoutesList = new RouteCollection();
    public static RouteCollection BusRoutesList = new RouteCollection();
    public static RouteCollection SkytrainRoutesList = new RouteCollection();

    public static Intent IntentForRouteList(Context context, int mode) {
        Intent intent = new Intent(context, Route_List_Activity.class);
        intent.putExtra("Mode", mode);
        return intent;
    }

    // 1 for car, 2 for walk and bike, 3 for bus,4 for skytrain
    private int getMode() {
        Intent intent = getIntent();
        int mode = intent.getIntExtra("Mode", 0);
        return mode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle(getString(R.string.route_list_hint));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_route_list);

        if (getMode() == 3) {
            loadFromSharedPreference("bus");
        } else if (getMode() == 4) {
            loadFromSharedPreference("sky");
        } else if (getMode() == 2) {
            loadFromSharedPreference("bike");
        } else if (getMode() == 1) {
            loadFromSharedPreference("car");
        }


        // getCarListData();
        setup_Add_Btn();
        showDifferentListView();
        long_pressing_editAndDelete();
        populateListView(showDifferentListView());

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
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN;

        mDecorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (getMode() == 3) {
            writeToSharedPreference("bus");
        } else if (getMode() == 4) {
            writeToSharedPreference("sky");
        } else if (getMode() == 2) {
            writeToSharedPreference("bike");
        } else if (getMode() == 1) {
            writeToSharedPreference("car");
        }
    }


    private RouteCollection showDifferentListView() {
        int mode = getMode();
        if (mode == 1) {
            return CarRoutesList;
        } else if (mode == 2) {
            return BikeNWalkRoutesList;
        } else if (mode == 3) {
            return BusRoutesList;
        } else {
            return SkytrainRoutesList;
        }
    }


    private void populateListView(RouteCollection routes) {
        String[] lotsofRoute = routes.Detail();
        ListAdapter bucky = new RouteListAdapter(this, lotsofRoute, getMode());
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
        final ListView list = (ListView) findViewById(R.id.Route_list_routeList);

        //Sean - Adding method to send data back to journeylist
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            RouteCollection routes = showDifferentListView();
                                            Route route = routes.getRouteByIndex(position);
                                            //To Sean
                                            //for car           (nickname,highway,city)  getOtherDistance=0,getMode=1
                                            //for bike          (nickname,0,0)           getOtherDistance=distance,getMode=2
                                            //for walk          (nickname,0,0)           getOtherDistance=distance,getMode=5
                                            //for bus           (nickname,0,0)           getOtherDistance=distance,getMode=3
                                            //for skytrain      (nickname,0,0)           getOtherDistance=distance,getMode=4

                                            // TEMP FIX DIFFERENTIATE BIKE AND WALK
                                            if(route.getMode() == 2){
                                                Emission.getInstance().getJourneyBuffer().getTransType().setTransMode(Transport.BIKE);
                                            } else if( route.getMode() == 5){
                                                Emission.getInstance().getJourneyBuffer().getTransType().setTransMode(Transport.WALK);
                                            }
                                            Emission.getInstance().getJourneyBuffer().setRoute(route);
                                            Intent intent = JourneyReviewActivity.getJourneyReviewIntent(Route_List_Activity.this);
                                            startActivity(intent);
                                        }
                                    }
        );


        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                RouteCollection routes = showDifferentListView();
                Route clickedRoute = routes.getRouteByIndex(position);
                EditDialog dialog = EditDialog.newInstance(clickedRoute.getName(), getMode());
                dialog.setEditDialogListener(new EditDialogListener() {
                    @Override
                    public void onDeleteClicked(int pos) {
                        RouteCollection routes = showDifferentListView();
                        routes.deleteRoute(position);
                        populateListView(routes);
                    }

                    @Override
                    public void onEditClicked(int pos) {
                        RouteCollection routes = showDifferentListView();
                        String strPosition = Integer.toString(position);
                        Route clickedRoute = routes.getRouteByIndex(position);
                        if (getMode() == 1)//car
                        {
                            Intent Intent_for_editing = Route_Info_Activity.IntentForEditRoute(
                                    Route_List_Activity.this, clickedRoute, strPosition);
                            startActivityForResult(Intent_for_editing, EDIT_ROUTE);
                        } else {
                            Intent Intent_for_editing_other_route = Bike_and_Trans_Info_Activity.IntentForEditRoute(Route_List_Activity.this, clickedRoute, strPosition);
                            startActivityForResult(Intent_for_editing_other_route, EDIT_ROUTE2);
                        }
                    }


                });

                dialog.show(getSupportFragmentManager(), "EditDialog");

                return true;
            }
        });

    }

    private Button setup_Add_Btn() {
        int mode = getMode();
        //car
        if (mode == 1) {
            Button btn = (Button) findViewById(R.id.Route_List_add_btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = Route_Info_Activity.IntentForAddingRoute(Route_List_Activity.this);
                    startActivityForResult(intent, RECEIVE_ROUTE);
                }
            });
            return btn;
        }
        //bike and walk
        else {
            Button btn = (Button) findViewById(R.id.Route_List_add_btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = Bike_and_Trans_Info_Activity.IntentForAddingRoute(Route_List_Activity.this, getMode());
                    startActivityForResult(intent, RECEIVE_ROUTE);
                }
            });
            return btn;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RECEIVE_ROUTE:
                int mode = getMode();
                //car
                if (mode == 1) {
                    if (resultCode == Activity.RESULT_OK) {
                        RouteCollection routes = showDifferentListView();
                        String RouteName = data.getStringExtra("pass back the route name");
                        String StrHighWay = data.getStringExtra("pass back the highway");
                        String StrCity = data.getStringExtra("pass back the city");
                        double highway = Double.parseDouble(StrHighWay);
                        double city = Double.parseDouble(StrCity);
                        Route addedRoute = new Route(RouteName, highway, city);
                        addedRoute.setOtherDistance(0);
                        addedRoute.setMode(1);
                        routes.addRoute(addedRoute);
                        populateListView(routes);
                    }
                }
                //bike or walk
                else {
                    if (resultCode == Activity.RESULT_OK) {
                        RouteCollection routes = showDifferentListView();
                        String RouteName = data.getStringExtra("pass back the route name");
                        String StrWalkDistance = data.getStringExtra("pass back the distance");
                        int BikeOrWalk = data.getIntExtra("pass back the mode", 0);
                        double distance = Double.parseDouble(StrWalkDistance);
                        Route addedRoute = new Route(RouteName, 0, 0);
                        //set bus as name,0,0
                        //but will set distance and set mode
                        addedRoute.setOtherDistance(distance);
                        if (BikeOrWalk == 2) {
                            addedRoute.setMode(2);
                        }//bike
                        else if (BikeOrWalk == 5) {
                            addedRoute.setMode(5);
                        }//walk
                        else if (mode == 3) {
                            addedRoute.setMode(3);
                        } else if (mode == 4) {
                            addedRoute.setMode(4);
                        }
                        routes.addRoute(addedRoute);
                        populateListView(routes);
                    }
                }

                break;
            case EDIT_ROUTE:
                if (resultCode == Activity.RESULT_OK) {
                    RouteCollection routes = showDifferentListView();
                    String StrDelete = data.getStringExtra("delete is clicked");
                    String StrIndex = data.getStringExtra("pass back the route position");
                    int delete = Integer.parseInt(StrDelete);
                    int index = Integer.parseInt(StrIndex);
                    //if user want to delete,not edit
                    if (delete == 1) {
                        routes.deleteRoute(index);
                        populateListView(routes);
                    }
                    //if user want to edit route
                    else {
                        String RouteName = data.getStringExtra("pass back the route name");
                        String StrHighWay = data.getStringExtra("pass back the highway");
                        String StrCity = data.getStringExtra("pass back the city");
                        double highway = Double.parseDouble(StrHighWay);
                        double city = Double.parseDouble(StrCity);
                        Route Clicked = new Route(RouteName, highway, city);
                        routes.changeRoute(Clicked, index);
                        populateListView(routes);
                    }
                }
                break;

            case EDIT_ROUTE2:
                if (resultCode == Activity.RESULT_OK) {
                    RouteCollection routes = showDifferentListView();
                    String StrDelete = data.getStringExtra("delete is clicked");
                    String StrIndex = data.getStringExtra("pass back the route position");
                    int delete = Integer.parseInt(StrDelete);
                    int index = Integer.parseInt(StrIndex);
                    //if user want to delete,not edit
                    if (delete == 1) {
                        routes.deleteRoute(index);
                        populateListView(routes);
                    }
                    //if user want to edit route
                    else {
                        String RouteName = data.getStringExtra("pass back the route name");
                        String StrDistance = data.getStringExtra("pass back the distance");
                        double Distance = Double.parseDouble(StrDistance);
                        Route Clicked = new Route(RouteName, 0, 0);
                        Clicked.setOtherDistance(Distance);
                        Clicked.setMode(getMode());
                        routes.changeRoute(Clicked, index);
                        populateListView(routes);
                    }
                }
                break;
        }
    }


    public void loadFromSharedPreference(String str) {
        SharedPreferences prefs = getSharedPreferences(str, Context.MODE_PRIVATE);
        RouteCollection List_Rout = showDifferentListView();
        List_Rout.DeleteAll();
        int numRoute = prefs.getInt(Num_Routes, 0);
        for (int i = 0; i < numRoute; i++) {
            String name = prefs.getString(Name_Route + i, "Fails on load name");
            String Strcity = prefs.getString(City_Route + i, "Fails on load city");
            String Strhighway = prefs.getString(HighWay_Route + i, "Fails on load highway");
            int mode = prefs.getInt(Mode_Route + i, -1);
            String StrOtherRoute = prefs.getString(OtherDistance_Route + i, "Fails on load otherRoute");

            double city = Double.parseDouble(Strcity);
            double highway = Double.parseDouble(Strhighway);
            double OtherRoute = Double.parseDouble(StrOtherRoute);

            Route route = new Route(name, city, highway);
            route.setMode(mode);
            route.setOtherDistance(OtherRoute);
            List_Rout.addRoute(route);
        }
    }

    public void writeToSharedPreference(String str) {
        SharedPreferences share = getSharedPreferences(str, Context.MODE_PRIVATE);
        RouteCollection List_Rout = showDifferentListView();
        SharedPreferences.Editor editor = share.edit();
        editor.putInt(Num_Routes, List_Rout.countRoutes());
        for (int i = 0; i < List_Rout.countRoutes(); i++) {
            Route route = List_Rout.getRouteByIndex(i);
            String StrCity = Double.toString(route.getCityDistance());
            String StrHighWay = Double.toString(route.getHighWayDistance());
            String StrOtherRoute = Double.toString(route.getOtherDistance());

            editor.putString(Name_Route + i, route.getName());
            editor.putString(City_Route + i, StrCity);
            editor.putString(HighWay_Route + i, StrHighWay);
            editor.putInt(Mode_Route + i, route.getMode());
            editor.putString(OtherDistance_Route + i, StrOtherRoute);
        }
        editor.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                finish(); // close this activity and return to preview activity (if there is any)
                break;
            case R.id.action_add:
                // add method
                Button btn = setup_Add_Btn();
                btn.performClick();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//         Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }


}
