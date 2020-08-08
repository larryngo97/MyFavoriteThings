package cs478.larryngo.myfavoritethings;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {
    private Button button_setTitle;
    private Button button_save;
    private TextView tv_title;
    private ImageView iv_image;
    private EditText et_desc;
    private TextView et_input;
    private Context context = this;
    private static ArrayList<FavoriteObject> list = new ArrayList<>();
    private final int IMAGE_PICK_CODE = 1000;
    private final int EXTERNAL_STORAGE_CODE = 1001;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        init();

        button_setTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.layout_dialog_title, null);
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);

                dialog.setView(view);

                et_input = (EditText) view.findViewById(R.id.edit_title);

                //new dialog sequence
                dialog.setTitle("Enter a new title")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(et_input.getText().toString().isEmpty())
                        {
                            dialog.cancel(); //does nothing if no title is inputted, also retains old title
                        }
                        else
                        {
                            tv_title.setText(et_input.getText().toString()); //new title
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel(); //goes back
                            }
                        });

                dialog.show();
            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    String title = tv_title.getText().toString();

                    //converting image to bytes to save memory
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    ((BitmapDrawable)iv_image.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.JPEG, 50, stream);
                    byte[] bytes = stream.toByteArray();

                    String info = et_desc.getText().toString();
                    String date = (DateFormat.format("yyyy-MM-dd hh:mm:ss a", new java.util.Date()).toString()); //gets current day, hour, and AM/PM

                    MainActivity.sqLite.insertData(title, bytes, info, date); //inserts into database

                    FavoriteObject obj = new FavoriteObject(title, bytes, info, date);
                    Intent intent = new Intent(AddActivity.this, MainActivity.class);
                    intent.putExtra("favorite_thing", obj);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                    {
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, EXTERNAL_STORAGE_CODE);
                    }
                    else
                    {
                        selectImage(); //granted new permission
                    }
                }
                else
                {
                    selectImage(); //lower version
                }
            }
        });
    }

    public void selectImage()
    {
        Intent intent = new Intent (Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    public void init()
    {
        tv_title = findViewById(R.id.add_text_title);
        button_setTitle = findViewById(R.id.add_button_title);
        button_save = findViewById(R.id.add_button_save);
        iv_image = findViewById(R.id.add_imagebutton);
        et_desc = findViewById(R.id.add_editText_desc);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case EXTERNAL_STORAGE_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    selectImage();
                }
                else
                {
                    Toast.makeText(this, "No permission to storage!", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE)
        {
            iv_image.setImageURI(data.getData());
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
