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

import cmpt276.jade.carbontracker.model.Journey;
import cmpt276.jade.carbontracker.model.OtherRoute;
import cmpt276.jade.carbontracker.model.Route;

public class Bike_and_Trans_Info_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bike_info);

        setupOKbtn();

        setupUI();
        setupCanselBtn();
        setupDeleteBtn();

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
        Button btn = (Button) findViewById(R.id.Bike_Info_ok_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Check_empty_input(R.id.Bike_Info_edit_name) == 0) {
                    Toast.makeText(getApplicationContext(), "You haven't enter the name" + " please try again", Toast.LENGTH_LONG).show();
                } else {
                    if (Check_empty_input(R.id.Bike_Info_edit_distance) == 0) {
                        Toast.makeText(getApplicationContext(), "You did not entered any number for Distance " + " please try again", Toast.LENGTH_LONG).show();
                    } else {
                        double distance = Double.parseDouble(getNameById(R.id.Bike_Info_edit_distance));
                        if (distance >= 0) {

                            pass_back_route();
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "You entered a invalid numer " + " please try again", Toast.LENGTH_LONG).show();
                            }}}}});
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

    private void pass_back_route() {
        Intent back_Route = new Intent();
        //back_Route.putExtra("Journey", journey);
        back_Route.putExtra("pass back the route name", getNameById(R.id.Bike_Info_edit_name));
        back_Route.putExtra("pass back the distance", getNameById(R.id.Bike_Info_edit_distance));
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


        OtherRoute ClickedRoute = getClickedRoute();

        int add;
        if (getClickedRoutePosition() == null) {
            add = 1;
        } else {
            EditText EditRouteName = (EditText) findViewById(R.id.Bike_Info_edit_name);
            EditRouteName.setText(ClickedRoute.getName());

            EditText EditRouteHighWay = (EditText) findViewById(R.id.Bike_Info_edit_distance);
            EditRouteHighWay.setText(Double.toString(ClickedRoute.getDistance()));
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


    public static Intent IntentForEditRoute(Context context, Route ClickedRoute, String position) {
        Intent intent = new Intent(context, Bike_and_Trans_Info_Activity.class);
        intent.putExtra("Passing Position", position);
        intent.putExtra("Passing Route name", ClickedRoute.getName());
        intent.putExtra("Passing Distance", ClickedRoute.getHighWayDistance());
        return intent;
    }
    private String getClickedRoutePosition() {
        Intent intent = getIntent();
        String routePos = intent.getStringExtra("Passing Position");
        return routePos;
    }
    private OtherRoute getClickedRoute() {
        Intent intent = getIntent();
        String name = intent.getStringExtra("Passing Route name");
        double distance = intent.getDoubleExtra("Passing Distance", 0);
        OtherRoute clicked= new OtherRoute(name, distance,getMode());
        return clicked;
    }


}
