package cs478.larryngo.myfavoritethings;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewActivity extends MainActivity{
    private TextView tv_title;
    private ImageView iv_image;
    private TextView tv_desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view);
        tv_title = findViewById(R.id.view_title);
        iv_image = findViewById(R.id.view_image);
        tv_desc = findViewById(R.id.view_desc);

        if(getIntent().hasExtra("viewObject"))
        {
            FavoriteObject object = getIntent().getParcelableExtra("viewObject"); //receives object

            tv_title.setText(object.getTitle()); //new title

            //decompressing image
            byte[] image = object.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            iv_image.setImageBitmap(bitmap); //new image

            tv_desc.setText(object.getDesc()); //new description
        }
        else //SHOULD NOT HAPPEN
        {
            Toast.makeText(getApplicationContext(), "Error: Could not find object!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ViewActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
