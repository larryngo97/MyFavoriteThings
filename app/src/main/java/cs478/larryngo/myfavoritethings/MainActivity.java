package cs478.larryngo.myfavoritethings;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView tv_title;
    private ArrayList<FavoriteObject> list = new ArrayList<>();
    private FavoriteObject object;
    private GridView grid;
    private Button button_new;
    public static SQLite sqLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void setupGrid()
    {
        ImageAdapter adapter = new ImageAdapter(MainActivity.this, list);
        grid.setAdapter(adapter); //Adapter for the grid
        registerForContextMenu(grid);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                intent.putExtra("viewObject", list.get(position));
                startActivity(intent);
            }
        });

        Cursor cursor = sqLite.getData("SELECT * FROM EVENT");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            byte[] image = cursor.getBlob(2);
            String info = cursor.getString(3);
            String date = cursor.getString(4);

            list.add(new FavoriteObject(title, image, info, date, id));
            adapter.notifyDataSetChanged();
            tv_title.setVisibility(View.GONE); //removes the title screen
        }
    }

    public void init()
    {
        tv_title = findViewById(R.id.main_text_title);
        grid = findViewById(R.id.grid);
        button_new = findViewById(R.id.button_new);

        sqLite = new SQLite(this, "EventsDB.sqlite", null, 1);
        sqLite.queryData("CREATE TABLE IF NOT EXISTS EVENT(Id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR, image BLOB, desc VARCHAR, date VARCHAR)");

        if(getIntent().hasExtra("favorite_thing")){
            object = getIntent().getParcelableExtra("favorite_thing"); //receives object
            list.add(object); //adds to the list
            setupGrid(); //updates the grid
        }

        button_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class); //goes to next activity
                startActivity(intent);
            }
        });
        setupGrid(); //sets up grid if starting up the app anyway
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }
}
