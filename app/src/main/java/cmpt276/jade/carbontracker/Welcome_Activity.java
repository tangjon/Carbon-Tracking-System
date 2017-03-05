package cmpt276.jade.carbontracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Welcome_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_welcome_);
        setupBtnToMainMenu();
        setupMoving(R.id.welcome_car1,500,2800); //#1 car
        setupMoving(R.id.welcome_car3,-700,2300);//#2 car
        setupMoving(R.id.welcome_person1,-500,3500); //#1 person
        setupMoving(R.id.welcome_person2,500,3500);  //#2 person
        setupMoving(R.id.welcome_person3,100,3000);  //#3 person
        setupAppName();
    }

    private void setupBtnToMainMenu()
    {
        Button btn = (Button) findViewById(R.id.welcome_btn_to_main);
        //btn.setBackgroundResource(R.drawable.xxx);
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = JourneyListActivity.IntentForJourneyList(Welcome_Activity.this);
                startActivity(intent);
                Welcome_Activity.this.finish();
            }
        });
    }

    private void setupMoving(int id,int distance,int speed) {
        ImageView imageBTN = (ImageView) findViewById(id);
        imageBTN.animate().translationX(imageBTN.getTranslationX()+distance).setDuration(speed);
    }

    //Set a image over the title, then it will disappear after 5000ms
    //Then the App Name will show off
    private void setupAppName() {
        ImageView imageBTN=(ImageView) findViewById(R.id.welcome_back_image);
        Animation animation=new AlphaAnimation(1.0f,0.0f);
        animation.setDuration(5000); //time
        animation.setFillAfter(true);
        imageBTN.startAnimation(animation);
    }
}
