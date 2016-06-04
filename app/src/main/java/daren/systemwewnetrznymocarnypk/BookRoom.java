package daren.systemwewnetrznymocarnypk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class BookRoom extends AppCompatActivity {

    private ListView list ;
    private RoomAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_room);

        list = (ListView) findViewById(R.id.listView);

        RowRoom room[] = new RowRoom[]{new RowRoom("NAME","DESCRIPTION","Owner"),
                new RowRoom("NAME","DESCRIPTION","Owner"),
                new RowRoom("NAME","DESCRIPTION","Owner"),
                new RowRoom("NAME","DESCRIPTION","Owner"),
                new RowRoom("NAME","DESCRIPTION","Owner")};



        adapter = new RoomAdapter(this, R.layout.row_list, room);

        list.setAdapter(adapter);
    }
}
