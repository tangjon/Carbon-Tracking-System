package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AboutUsActivity extends AppCompatActivity {

  public static Intent getIntent(Context ctx){
    Intent intent = new Intent(ctx, AboutUsActivity.class);
    return intent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getSupportActionBar().setTitle(R.string.label_about_us);
    setContentView(R.layout.activity_about_us);
  }
}
