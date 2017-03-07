package cmpt276.jade.carbontracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import cmpt276.jade.carbontracker.model.Route;
import cmpt276.jade.carbontracker.model.RouteCollection;

public class Route_List_Activity extends AppCompatActivity {

    public static final int RECEIVE_ROUTE = 1024; //intent numer for add
    public static final int EDIT_ROUTE = 1025; //intent number for edit/delete
    RouteCollection routes = new RouteCollection();

    public static Intent IntentForRouteList(Context context) {
        Intent intent = new Intent(context, Route_List_Activity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_route_list);

        setup_Add_Btn();

        populateListView();

        long_pressing_editAndDelete();
        //setup_Summary_Btn();
        Click_one_Route();
    }

    private void populateListView() {
        String[] lotsofRoute = routes.Detail();
        ArrayAdapter<String> ShowAllRoutes = new ArrayAdapter<String>(this, R.layout.route_list, lotsofRoute);
        ListView list = (ListView) findViewById(R.id.Route_list_routeList);
        list.setAdapter(ShowAllRoutes);
    }



    //long pressing for edit and delete
    private void long_pressing_editAndDelete() {
        ListView list = (ListView) findViewById(R.id.Route_list_routeList);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String strPosition = Integer.toString(position);
                Route clickedRoute = routes.getRouteByIndex(position);
                Intent Intent_for_editing = Route_Info_Activity.IntentForEditRoute(Route_List_Activity.this, clickedRoute, strPosition);
                startActivityForResult(Intent_for_editing, EDIT_ROUTE);
                return true;
            }
        });
    }

    private void setup_Add_Btn() {
        Button btn = (Button) findViewById(R.id.Route_List_add_btn);
        //btn.setBackgroundResource(R.drawable.xxx);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Route_Info_Activity.IntentForAddingRoute(Route_List_Activity.this);
                startActivityForResult(intent, RECEIVE_ROUTE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RECEIVE_ROUTE:
                if (resultCode == Activity.RESULT_OK) {
                    String RouteName = data.getStringExtra("pass back the route name");
                    String StrHighWay = data.getStringExtra("pass back the highway");
                    String StrCity = data.getStringExtra("pass back the city");
                    int highway = Integer.parseInt(StrHighWay);
                    int city = Integer.parseInt(StrCity);

                    Route addedRoute = new Route(RouteName, highway, city);

                    routes.addRoute(addedRoute);
                    populateListView();
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

                        int highway = Integer.parseInt(StrHighWay);
                        int city = Integer.parseInt(StrCity);

                        Route Clicked = new Route(RouteName, highway, city);
                        routes.changeRoute(Clicked, index);
                        populateListView();
                    }
                }
                break;
        }
    }



    private void Click_one_Route() {

        ListView list=(ListView) findViewById(R.id.Route_list_routeList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id)
            {
                Route Clicked_One=routes.getRouteByIndex(position);
                routes.SetJourneyName(Clicked_One.getName());
                Intent Intent_of_One_Route = JourneySummaryActivity.getJourneySummaryIntent
                        (Route_List_Activity.this, routes);
                startActivity(Intent_of_One_Route);
            }
        });
    }

}


//add all routes
   /*
    private void setup_Summary_Btn() {
        Button btn = (Button) findViewById(R.id.Route_List_summary_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText EditJourneyName = (EditText) findViewById(R.id.Route_List_enter_roues_name);
                routes.SetJourneyName(EditJourneyName.getText().toString());
                if(EditJourneyName.length()!=0 && (routes.getTotleHighWayDistance()+routes.getTotleCityDistance())!=0)
                {
                    Intent Intent_of_List_Routes = JourneySummaryActivity.getJourneySummaryIntent
                            (Route_List_Activity.this, routes);
                    startActivity(Intent_of_List_Routes);
                }
                if(EditJourneyName.length()==0)
                {
                    Toast.makeText(getApplicationContext(), "You haven't entered the Journey name " + " please try again", Toast.LENGTH_LONG).show();
                }
                if((routes.getTotleHighWayDistance()+routes.getTotleCityDistance())==0)
                {
                    Toast.makeText(getApplicationContext(), "You haven't entered any routes " + " please try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }*/





/*
public class Route_List_Activity extends AppCompatActivity {

    RouteCollection routes= new RouteCollection();
    RouteCollection SelectRoutes= new RouteCollection();

    public static final int RECEIVE_ROUTE = 1024; //intent numer for add
    public static final int EDIT_ROUTE = 1025; //intent number for edit/delete

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_route_list);

        setup_Add_Btn();

        populateListView();

        long_pressing_editAndDelete();

        setup_graph_Btn();

    }

    private void setup_graph_Btn() {
        Button btn = (Button) findViewById(R.id.Route_List_summary_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<routes.countRoutes();i++)
                {
                    Route route= routes.getRouteByIndex(i);
                    if(route.getSelection()=="NO")
                    {

                    }
                }
                Intent intent = JourneySummaryActivity.getJourneySummaryIntent(Route_List_Activity.this);
                startActivity(intent);
            }
        });
    }




    private void populateListView() {
        String[] lotsofRoute= routes.Detail();
        ArrayAdapter<String> ShowAllRoutes=new ArrayAdapter<String>(this, R.layout.route_list, lotsofRoute);
        ListView list=(ListView) findViewById(R.id.Route_list_routeList);
        list.setAdapter(ShowAllRoutes);
    }

    //long pressing for edit and delete
    private void long_pressing_editAndDelete() {
        ListView list=(ListView) findViewById(R.id.Route_list_routeList);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String strPosition=Integer.toString(position);
                Route clickedRoute= routes.getRouteByIndex(position);
                Intent Intent_for_editing = Route_Info_Activity.IntentForEditRoute(Route_List_Activity.this,clickedRoute,strPosition);
                startActivityForResult(Intent_for_editing,EDIT_ROUTE);
                return true;
            }});
    }

    private void setup_Add_Btn() {
        Button btn = (Button) findViewById(R.id.Route_List_add_btn);
        //btn.setBackgroundResource(R.drawable.xxx);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Route_Info_Activity.IntentForAddingRoute(Route_List_Activity.this);
                startActivityForResult(intent, RECEIVE_ROUTE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case RECEIVE_ROUTE:
                if(resultCode== Activity.RESULT_OK) {
                    String RouteName= data.getStringExtra("pass back the route name");
                    String StrHighWay= data.getStringExtra("pass back the highway");
                    String StrCity= data.getStringExtra("pass back the city");
                    int highway= Integer.parseInt(StrHighWay);
                    int city= Integer.parseInt(StrCity);

                    Route addedRoute=new Route(RouteName,highway,city,"NO");

                    routes.addRoute(addedRoute);
                    populateListView();
                }
                break;
            case EDIT_ROUTE:
                if(resultCode== Activity.RESULT_OK) {
                    String StrDelete = data.getStringExtra("delete is clicked");
                    String StrIndex = data.getStringExtra("pass back the route position");
                    int delete = Integer.parseInt(StrDelete);
                    int index = Integer.parseInt(StrIndex);
                    //if user want to delete,not edit
                    if(delete==1) {
                        Route Clicked=routes.getRouteByIndex(index);
                        Clicked.setSelection("YES");
                        populateListView();
                    }
                    //if user want to edit route
                    else {
                        String RouteName= data.getStringExtra("pass back the route name");
                        String StrHighWay= data.getStringExtra("pass back the highway");
                        String StrCity= data.getStringExtra("pass back the city");

                        int highway= Integer.parseInt(StrHighWay);
                        int city= Integer.parseInt(StrCity);

                        Route Clicked=new Route(RouteName,highway,city,"NO");
                        routes.changeRoute(Clicked,index);
                        populateListView();
                    }}break;}
    }

    public static Intent IntentForRouteList(Context context) {
        Intent intent = new Intent(context, Route_List_Activity.class);
        return intent;
    }
}*/

