package cmpt276.jade.carbontracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cmpt276.jade.carbontracker.enums.Transport;
import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.model.Journey;

import cmpt276.jade.carbontracker.model.Route;

/**
 * Can choose between a bike or walk while setting the route and distance
 */
public class Bike_and_Trans_Info_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bike_info);
        getMode();
        setupUI();
        setupCanselBtn();
        setupDeleteBtn();
        if(getMode()==2){setupTwoBtn();}//add bike btn and add walk btn
        else if (getMode()!=2){setupOKbtn();}//otherwise, just have a ok btn
    }


    public static Intent IntentForAddingRoute(Context context,int mode) {
        Intent intent = new Intent(context, Bike_and_Trans_Info_Activity.class);
        intent.putExtra("Mode", mode);
        return intent;
    }

    private int getMode() {
        Intent intent = getIntent();
        int mode = intent.getIntExtra("Mode",0);
        return mode;
    }



    private void setupOKbtn() {
        //hide the bike and walk btn
        Button bike = (Button) findViewById(R.id.Bike_Info_bike_btn);
        Button walk = (Button) findViewById(R.id.Bike_Info_walk_btn);
        bike.getBackground().setAlpha(00);
        walk.getBackground().setAlpha(00);
        bike.setText("");
        walk.setText("");

        Button btn = (Button) findViewById(R.id.Bike_Info_ok_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_and_add_to_list(getMode());
            }
        });
    }

    private void setupTwoBtn() {
        //hide ok btn
        Button bike = (Button) findViewById(R.id.Bike_Info_bike_btn);
        Button walk = (Button) findViewById(R.id.Bike_Info_walk_btn);
        Button ok = (Button) findViewById(R.id.Bike_Info_ok_btn);
        ok.getBackground().setAlpha(00);
        ok.setText("");
        bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_and_add_to_list(2);
                Emission.getInstance().getJourneyBuffer().getTransType().setTransMode(Transport.BIKE);
            }});
        walk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    check_and_add_to_list(5);
                    Emission.getInstance().getJourneyBuffer().getTransType().setTransMode(Transport.WALK);
                }
            });

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

    private void pass_back_route(int mode) {
        Intent back_Route = new Intent();
        //back_Route.putExtra("Journey", journey);
        back_Route.putExtra("pass back the route name", getNameById(R.id.Bike_Info_edit_name));
        back_Route.putExtra("pass back the distance", getNameById(R.id.Bike_Info_edit_distance));
        back_Route.putExtra("pass back the mode", mode);
        if (getClickedRoutePosition() != null) {
            back_Route.putExtra("delete is clicked", "0");
            back_Route.putExtra("pass back the route position", getClickedRoutePosition());
        }
        setResult(Activity.RESULT_OK, back_Route);
    }


    private int setupUI() {
        TextView tv= (TextView) findViewById(R.id.Bike_info_mode);
        if(getMode()==2) {tv.setText("Bike/Walk");}
        if(getMode()==3) {tv.setText("Bus");}
        if(getMode()==4) {tv.setText("Skytrain");}


        Route ClickedRoute = getClickedRoute();

        int add;
        if (getClickedRoutePosition() == null) {
            add = 1;
        } else {
            EditText EditRouteName = (EditText) findViewById(R.id.Bike_Info_edit_name);
            EditRouteName.setText(ClickedRoute.getName());

            EditText EditRouteDistance = (EditText) findViewById(R.id.Bike_Info_edit_distance);
            EditRouteDistance.setText(Double.toString(ClickedRoute.getOtherDistance()));
            add = 0;
        }
        return add;
    }

    private void setupCanselBtn() {
        Button btn = (Button) findViewById(R.id.Bike_Info_cansel_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent entered_cansel = new Intent();
                setResult(Activity.RESULT_CANCELED, entered_cansel);
                finish();
            }
        });
    }

    private void setupDeleteBtn() {
        Button btn = (Button) findViewById(R.id.Bike_Info_delete_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // user can not clicked delete button if they are in add mode
                if (setupUI() == 1) {
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

    private void check_and_add_to_list(int mode) {
        if (Check_empty_input(R.id.Bike_Info_edit_name) == 0) {
            Toast.makeText(getApplicationContext(), "You haven't enter the name" + " please try again", Toast.LENGTH_LONG).show();
        } else {
            if (Check_empty_input(R.id.Bike_Info_edit_distance) == 0) {
                Toast.makeText(getApplicationContext(), "You did not entered any number for Distance " + " please try again", Toast.LENGTH_LONG).show();
            } else {
                double distance = Double.parseDouble(getNameById(R.id.Bike_Info_edit_distance));
                if (distance >= 0) {
                    pass_back_route(mode);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "You entered a invalid numer " + " please try again", Toast.LENGTH_LONG).show();
                }}}
    }


    public static Intent IntentForEditRoute(Context context, Route ClickedRoute, String position) {
        Intent intent = new Intent(context, Bike_and_Trans_Info_Activity.class);
        intent.putExtra("Passing Position", position);
        intent.putExtra("Passing Route name", ClickedRoute.getName());
        intent.putExtra("Passing Distance", ClickedRoute.getOtherDistance());
        intent.putExtra("ModeForEdit", ClickedRoute.getMode());
        return intent;
    }
    private String getClickedRoutePosition() {
        Intent intent = getIntent();
        String routePos = intent.getStringExtra("Passing Position");
        return routePos;
    }
    private Route getClickedRoute() {
        Intent intent = getIntent();
        String name = intent.getStringExtra("Passing Route name");
        double distance = intent.getDoubleExtra("Passing Distance", 0);
        int mode=intent.getIntExtra("ModeForEdit",0);
        Route clicked= new Route(name, 0,0);
        clicked.setOtherDistance(distance);
        clicked.setMode(mode);
        return clicked;
    }


}
