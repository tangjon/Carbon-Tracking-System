package cmpt276.jade.carbontracker.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.Toast;

import cmpt276.jade.carbontracker.R;

/**
 * Created by tangj on 3/25/2017.
 * Class: ImageRowAdapter
 * Description: used for displaying row of icons
 * Bugs:
 */
public class ImageRowAdapter {

    private int selectedImage = 0;


    Context mContext;
    private int[] imageId = {
            R.drawable.walksymbol,
            R.drawable.bike,
            R.drawable.car,
            R.drawable.skytrain,
            R.drawable.bus
    };

    public ImageRowAdapter(Context ctx) {
        mContext = ctx;
    }

    public TableRow getRow(){
        LayoutInflater inflater = LayoutInflater.from(mContext);

        TableRow row = new TableRow(mContext);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);

        for (final int id :
                imageId) {
            ImageView imageView = new ImageView(mContext);
            imageView.setPadding(10,0,10,0);
            Bitmap bMap = BitmapFactory.decodeResource(mContext.getResources(), id);
            Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 150, 150, true);
            imageView.setImageBitmap(bMapScaled);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedImage = id;
                }
            });
            row.addView(imageView);
        }
        return row;
    }

    public boolean isImageSelected(){
        if(selectedImage == 0){
            return false;
        } else{
            return true;
        }
    }

    public int getSelectedImage() {
        return selectedImage;
    }

    public void setImage(int imageId) {
        this.selectedImage = imageId;
    }
}
