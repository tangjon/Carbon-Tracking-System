package cmpt276.jade.carbontracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cmpt276.jade.carbontracker.model.Car;
import cmpt276.jade.carbontracker.utils.CarManager;

public class MainActivity extends AppCompatActivity {

    private Car[] carList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        loadCarList();
	}

    private void loadCarList() {
        carList = CarManager.readCarData(this,R.raw.vehicle_trimmed);
    }


}
