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

import java.util.ArrayList;
import java.util.List;

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

    // TWO ARRAYS MUST MATCH
    private int[] imageId = {
            R.drawable.car,
            R.drawable.skytrain,
            R.drawable.bus,
            R.drawable.plane,
            R.drawable.motor_bike
    };

    private int[] imageIdSelected = {
            R.drawable.car_clicked,
            R.drawable.skytrain_clicked,
            R.drawable.bus_clicked,
            R.drawable.plane_clicked,
            R.drawable.motor_bike_clicked
    };

    private List<ImageView> table;

    public ImageRowAdapter(Context ctx) {
        mContext = ctx;
        table = new ArrayList<>();
    }

    public TableRow getRow(){
        LayoutInflater inflater = LayoutInflater.from(mContext);

        TableRow row = new TableRow(mContext);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);

        for (final int id : imageId) {
            ImageView imageView = new ImageView(mContext);
            imageView.setPadding(10,0,10,0);
            Bitmap bMap = BitmapFactory.decodeResource(mContext.getResources(), id);
            Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 150, 150, true);
            imageView.setImageBitmap(bMapScaled);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setImage(id);
                    updateSelectedScreen();
                }
            });
            table.add(imageView);
            row.addView(imageView);
        }
        return row;
    }

    private Bitmap formatImage(int id){
        Bitmap bMap = BitmapFactory.decodeResource(mContext.getResources(), id);
        Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 150, 150, true);
        return bMapScaled;
    }

    public void updateSelectedScreen(){
        int i =0;
        for (ImageView v: table) {
            v.setImageBitmap(formatImage(imageId[i]));
            if(imageId[i] == selectedImage){
                v.setImageBitmap(formatImage(imageIdSelected[i]));
            }
            i++;
        }

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
