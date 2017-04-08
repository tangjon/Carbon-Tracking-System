package cmpt276.jade.carbontracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.model.Journey;
import cmpt276.jade.carbontracker.model.Route;

/**
 * Create route to be used in journey or saved to list
 */

public class Route_Info_Activity extends AppCompatActivity {

    private Route RouteFromList;
    //  private Journey journey;

    public static Intent IntentForAddingRoute(Context context) {
        Intent intent = new Intent(context, Route_Info_Activity.class);
        return intent;
    }

    public static Intent IntentForEditRoute(Context context, Route ClickedRoute, String position) {
        Intent intent = new Intent(context, Route_Info_Activity.class);
        intent.putExtra("Passing Position", position);
        intent.putExtra("Passing Route name", ClickedRoute.getName());
        intent.putExtra("Passing HighWay", ClickedRoute.getHighWayDistance());
        intent.putExtra("Passing City", ClickedRoute.getCityDistance());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.route_info_hint));
        //getJourneyData();
        setContentView(R.layout.layout_route_infor);
        setupOKbtn();
        setupCanselBtn();
        setupDeleteBtn();
        setupUI_TextView();
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

    private void setupOKbtn() {
        Button btn = (Button) findViewById(R.id.Route_Info_ok_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check empty for route name
                if (Check_empty_input(R.id.editJourneyName) == 0) {
                    Toast.makeText(getApplicationContext(),
                        "You haven't enter the route name " + " please try again", Toast.LENGTH_LONG).show();
                }
                else {
                    if (Check_empty_input(R.id.Route_Info_edite_highway) == 0 && Check_empty_input(R.id.Route_Info_edite_city) == 0) {
                        Toast.makeText(getApplicationContext(),
                            "You did not entered any number for either HighWay or City" +
                                " please try again", Toast.LENGTH_LONG).show();
                    }
                    else if (Check_empty_input(R.id.Route_Info_edite_highway) == 0 )
                    {
                        String highway = "0";
                        String city = getNameById(R.id.Route_Info_edite_city);
                        if (Double.parseDouble(city) > 0 &&
                            Check_empty_input(R.id.Route_Info_edite_highway) == 0 ) {
                            //PASS name and weight BACK
                            pass_back_route(city,highway);
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),
                                "Your highway and city are both 0 km, " +
                                    " please enter some valid number", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        String highway = getNameById(R.id.Route_Info_edite_highway);
                        String city = "0";
                        if (Double.parseDouble(highway) > 0 &&
                            Check_empty_input(R.id.Route_Info_edite_city) == 0) {
                            //PASS name and weight BACK
                            pass_back_route(city,highway);
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),
                                "Your highway and city are both 0 km, " +
                                    " please enter some valid number", Toast.LENGTH_LONG).show();}
                    }}}});
    }


    private void pass_back_route(String city, String highway) {
        Intent back_Route = new Intent();
        back_Route.putExtra("pass back the route name", getNameById(R.id.editJourneyName));
        back_Route.putExtra("pass back the highway", highway);
        back_Route.putExtra("pass back the city", city);
        if (getClickedRoutePosition() != null) {
            back_Route.putExtra("delete is clicked", "0");
            back_Route.putExtra("pass back the route position", getClickedRoutePosition());
        }
        setResult(Activity.RESULT_OK, back_Route);
    }

    private int Check_empty_input(int ID) {
        EditText EditRouteName = (EditText) findViewById(ID);
        String StrInput = EditRouteName.getText().toString();
        Log.i("Check_empty_input","StrInput = "+StrInput+"; length = "+StrInput.length());
        if (StrInput.length() == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    private String getNameById(int ID) {
        EditText EditRouteName = (EditText) findViewById(ID);
        return EditRouteName.getText().toString();
    }

    private void setupCanselBtn() {
        Button btn = (Button) findViewById(R.id.Route_Info_back_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent entered_cansel = new Intent();
                setResult(Activity.RESULT_CANCELED, entered_cansel);
                finish();
            }
        });
    }

    private String getClickedRoutePosition() {
        Intent intent = getIntent();
        String routePos = intent.getStringExtra("Passing Position");
        return routePos;
    }

    private Route getClickedRoute() {
        Intent intent = getIntent();
        String name = intent.getStringExtra("Passing Route name");
        double highway = intent.getDoubleExtra("Passing HighWay", 0);
        double city = intent.getDoubleExtra("Passing City", 0);
        RouteFromList = new Route(name, highway, city);
        return RouteFromList;
    }


    //set up UI
    private int setupUI_TextView() {
        TextView Text_Mode = (TextView) findViewById(R.id.Route_Info_text_mode);
        TextView Text_name = (TextView) findViewById(R.id.Route_Info_text_enter_name);
        TextView Text_highway = (TextView) findViewById(R.id.Route_Info_text_enter_highway);
        TextView Text_city = (TextView) findViewById(R.id.RouRoute_Info_text_enter_city);

        Route ClickedRoute = getClickedRoute();

        //add mode =1,delete mode=0
        int add;
        if (getClickedRoutePosition() == null) {
            Text_Mode.setText(R.string.route_info_mode_hint);
            Text_name.setText(R.string.label_route_name);
            Text_highway.setText(R.string.label_highway);
            Text_city.setText(R.string.label_city);
            add = 1;
        } else {
            Text_Mode.setText(R.string.route_info_mode_hint2);

            EditText EditRouteName = (EditText) findViewById(R.id.editJourneyName);
            EditRouteName.setText(ClickedRoute.getName());

            EditText EditRouteHighWay = (EditText) findViewById(R.id.Route_Info_edite_highway);
            EditRouteHighWay.setText(Double.toString(ClickedRoute.getHighWayDistance()));

            EditText EditRouteCity = (EditText) findViewById(R.id.Route_Info_edite_city);
            EditRouteCity.setText(Double.toString(ClickedRoute.getCityDistance()));
            add = 0;
        }
        return add;
    }

    private void setupDeleteBtn() {
        Button btn = (Button) findViewById(R.id.Route_Info_delete_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // user can not clicked delete button if they are in add mode
                if (setupUI_TextView() == 1) {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.route_info_toast_delete_err),
                            Toast.LENGTH_LONG).show();
                } else {
                    Intent back_route2 = new Intent();
                    back_route2.putExtra("delete is clicked", "1");
                    back_route2.putExtra("pass back the route position", getClickedRoutePosition());
                    setResult(Activity.RESULT_OK, back_route2);
                    finish();
                }
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

/*
    public void getJourneyData() {
        Intent intent = getIntent();
        journey = (Journey)intent.getSerializableExtra("Journey");

    }
*/
}