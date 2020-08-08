package cs478.larryngo.myfavoritethings;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<FavoriteObject> list;

    public ImageAdapter(Context c, ArrayList<FavoriteObject> object){
        this.mContext = c;
        this.list = object;
    }


    public int getCount() {
        return list.size();
    }

    public long getItemId(int position) {
        return position;
    }

    public Object getItem(int id)
    {
        return null;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //grabs inflate info
            gridView = inflater.inflate(R.layout.layout_grid, null); //inflates the grid

        }

        ImageView iconView = gridView.findViewById(R.id.grid_image);
        TextView titleView = gridView.findViewById(R.id.grid_title);
        TextView infoView = gridView.findViewById(R.id.grid_date);

        byte[] image = list.get(position).getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

        iconView.setImageBitmap(bitmap); //sets the picture
        titleView.setText(list.get(position).getTitle()); //displays the name of the phone to the grid
        infoView.setText(list.get(position).getDate()); //displays the info of the phone to the grid

        return gridView;
    }

}