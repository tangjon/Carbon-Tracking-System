package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


/**
 * The about us activity
 *
 * tells us some extra information about the application and the developers
 */
public class AboutUsActivity extends AppCompatActivity {

  public static Intent getIntent(Context ctx) {
    Intent intent = new Intent(ctx, AboutUsActivity.class);
    return intent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getSupportActionBar().setTitle(R.string.label_about_us);
    setContentView(R.layout.activity_about_us);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    setUpLinks();


    hideSystemUI();
  }

  private void hideSystemUI() {
    View mDecorView = getWindow().getDecorView();
    // Set the IMMERSIVE flag.
    // Set the content to appear under the system bars so that the content
    // doesn't resize when the system bars hide and show.

    int uiOptions =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;

    mDecorView.setSystemUiVisibility(uiOptions);

//        mDecorView.setSystemUiVisibility(
//            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
////                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
//                | View.SYSTEM_UI_FLAG_IMMERSIVE);
  }


  private void setUpLinks() {
    TextView cmpt_home = (TextView) findViewById(R.id.link_cmpt_home);
    cmpt_home.setMovementMethod(LinkMovementMethod.getInstance());
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      finish(); // close this activity and return to preview activity (if there is any)
    }
    return super.onOptionsItemSelected(item);
  }

}

