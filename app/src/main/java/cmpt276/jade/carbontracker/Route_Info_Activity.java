package cmpt276.jade.carbontracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cmpt276.jade.carbontracker.model.Journey;
import cmpt276.jade.carbontracker.model.Route;

public class Route_Info_Activity extends AppCompatActivity {

    private Route RouteFromList;
    private Journey journey;

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
        getSupportActionBar().setTitle(getString(R.string.RouteInfoActivityHint));
        getJourneyData();
        setContentView(R.layout.layout_route_infor);
        setupOKbtn();
        setupUSEbtn();
        setupCanselBtn();
        setupDeleteBtn();
        setupUI_TextView();
    }

    private void setupOKbtn() {
        Button btn = (Button) findViewById(R.id.Route_Info_ok_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check empty for route name
                if (Check_empty_input(R.id.editJourneyName) == 0) {
                    Toast.makeText(getApplicationContext(), "You haven't enter the route name " + " please try again", Toast.LENGTH_LONG).show();
                } else {
                    //check empty input for highway
                    if (Check_empty_input(R.id.Route_Info_edite_highway) == 0) {
                        Toast.makeText(getApplicationContext(), "You did not entered any number for HighWay " + " please try again", Toast.LENGTH_LONG).show();
                    } else {
                        //check empty input for city
                        if (Check_empty_input(R.id.Route_Info_edite_city) == 0) {
                            Toast.makeText(getApplicationContext(), "You did not entered any number for City " + " please try again", Toast.LENGTH_LONG).show();
                        } else {
                            double highway = Double.parseDouble(getNameById(R.id.Route_Info_edite_highway));
                            double city = Double.parseDouble(getNameById(R.id.Route_Info_edite_city));
                            if (highway >= 0 && city >= 0) {
                                //PASS name and weight BACK
                                pass_back_route();
                                finish();
                            }
                            //highway or city numer are <0
                            else {
                                Toast.makeText(getApplicationContext(), "You entered a invalid numer " + " please try again", Toast.LENGTH_LONG).show();
                            }}}}}});
    }



    private void setupUSEbtn() {
        Button btn = (Button) findViewById(R.id.Route_Info_USE_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (Check_empty_input(R.id.Route_Info_edite_highway) == 0) {
                        Toast.makeText(getApplicationContext(), "You did not entered any number for HighWay " + " please try again", Toast.LENGTH_LONG).show();
                    }
                    else {
                        if (Check_empty_input(R.id.Route_Info_edite_city) == 0) {
                            Toast.makeText(getApplicationContext(), "You did not entered any number for City " + " please try again", Toast.LENGTH_LONG).show();
                        }
                        else {
                            double highway = Double.parseDouble(getNameById(R.id.Route_Info_edite_highway));
                            double city = Double.parseDouble(getNameById(R.id.Route_Info_edite_city));
                            if (highway >= 0 && city >= 0) {
                                String nickname=getNameById(R.id.editJourneyName);
                                //No nickname, so set name ""
                                if(nickname.length()==0){nickname="no name";}
                                Route route = new Route(nickname,highway,city);
                                journey.setRoute(route);
                                Intent intent = JourneyReviewActivity.getJourneyReviewIntent(Route_Info_Activity.this);
                                intent.putExtra("Journey", journey);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "You entered a invalid numer " + " please try again", Toast.LENGTH_LONG).show();
                            }}}}});
    }

    private void pass_back_route() {
        Intent back_Route = new Intent();
        back_Route.putExtra("Journey", journey);
        back_Route.putExtra("pass back the route name", getNameById(R.id.editJourneyName));
        back_Route.putExtra("pass back the highway", getNameById(R.id.Route_Info_edite_highway));
        back_Route.putExtra("pass back the city", getNameById(R.id.Route_Info_edite_city));
        if (getClickedRoutePosition() != null) {
            back_Route.putExtra("delete is clicked", "0");
            back_Route.putExtra("pass back the route position", getClickedRoutePosition());
        }
        setResult(Activity.RESULT_OK, back_Route);
    }

    private int Check_empty_input(int ID) {
        EditText EditRouteName = (EditText) findViewById(ID);
        String StrInput = EditRouteName.getText().toString();
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
        String Routepos = intent.getStringExtra("Passing Position");
        return Routepos;
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
            Text_Mode.setText("You are now in add mode");
            Text_name.setText("Route Name: ");
            Text_highway.setText("HighWay: ");
            Text_city.setText("City: ");
            add = 1;
        } else {
            Text_Mode.setText("You are now in edit and deletion mode");

            EditText EditRouteName = (EditText) findViewById(R.id.editJourneyName);
            EditRouteName.setText(""+ClickedRoute.getName());

            EditText EditRouteHighWay = (EditText) findViewById(R.id.Route_Info_edite_highway);
            EditRouteHighWay.setText(""+ClickedRoute.getHighWayDistance());

            EditText EditRouteCity = (EditText) findViewById(R.id.Route_Info_edite_city);
            EditRouteCity.setText(""+ClickedRoute.getCityDistance());
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
                            "You can not use delete now"
                                    + ",since you are in add mode", Toast.LENGTH_LONG).show();
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

    public void getJourneyData() {
        Intent intent = getIntent();
        journey = (Journey)intent.getSerializableExtra("Journey");

    }

}