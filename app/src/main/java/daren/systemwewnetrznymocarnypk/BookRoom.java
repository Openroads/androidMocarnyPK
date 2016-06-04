package daren.systemwewnetrznymocarnypk;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;

public class BookRoom extends AppCompatActivity {

    private ListView list ;
    private RoomAdapter adapter ;
    RowRoom room[] = new RowRoom[]{new RowRoom("NAME", "DESCRIPTION", "Owner"),
            new RowRoom("NAME", "DESCRIPTION", "Owner"),
            new RowRoom("NAME", "DESCRIPTION", "Owner"),
            new RowRoom("NAME", "DESCRIPTION", "Owner"),
            new RowRoom("NAME", "DESCRIPTION", "Owner"),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_room);

        list = (ListView) findViewById(R.id.listView);
        //  http://192.168.1.150:8000/api/rest/v1/rooms/66b5f696-e01c-46f0-a55f-16b393f6ea84/
        new WebServiceHandler().execute("http://192.168.1.150:8000/api/rest/v1/rooms/66b5f696-e01c-46f0-a55f-16b393f6ea84/");




        adapter = new RoomAdapter(this, R.layout.row_list, room);

        list.setAdapter(adapter);
    }
        class WebServiceHandler extends AsyncTask<String, Void, String> {

            // okienko dialogowe, które każe użytkownikowi czekać
            private ProgressDialog dialog = new ProgressDialog(BookRoom.this);

            // metoda wykonywana jest zaraz przed główną operacją (doInBackground())
            // mamy w niej dostęp do elementów UI
            @Override
            protected void onPreExecute() {
                // wyświetlamy okienko dialogowe każące czekać
                dialog.setMessage("Czekaj...");
                dialog.show();
            }

            // główna operacja, która wykona się w osobnym wątku
            // nie ma w niej dostępu do elementów UI
            @Override
            protected String doInBackground(String... urls) {

                try {
                    // zakładamy, że jest tylko jeden URL
                    URL url = new URL("http://192.168.1.150:8000/api/rest/v1/rooms/66b5f696-e01c-46f0-a55f-16b393f6ea84/");
                    URLConnection connection = url.openConnection();

                    // pobranie danych do InputStream
                    InputStream in = new BufferedInputStream(
                            connection.getInputStream());

                    // konwersja InputStream na String
                    // wynik będzie przekazany do metody onPostExecute()
                    return streamToString(in);

                } catch (Exception e) {
                    // obsłuż wyjątek
                    Log.d(MainActivity.class.getSimpleName(), e.toString());
                    return null;
                }

            }

            // metoda wykonuje się po zakończeniu metody głównej,
            // której wynik będzie przekazany;
            // w tej metodzie mamy dostęp do UI
            @Override
            protected void onPostExecute(String result) {

                // chowamy okno dialogowe
                dialog.dismiss();

                try {
                    // reprezentacja obiektu JSON w Javie
                    JSONObject json = new JSONObject(result);
                    JSONArray jarray =   json.optJSONArray("data");
                    // pobranie pól obiektu JSON i wyświetlenie ich na ekranie
                    // for (JSONObject result : jarray.optJSONArray("data")){

                    // }
                    for(int i=0; i < jarray.length(); i++){
                        JSONObject obj = jarray.getJSONObject(i);
                        room[i]=new RowRoom(obj.optString("name"),obj.optString("description"),obj.optString("id"));

                    }

                } catch (Exception e) {
                    // obsłuż wyjątek
                    Log.d(MainActivity.class.getSimpleName(), e.toString());
                }
            }


            // konwersja z InputStream do String
            private  String streamToString(InputStream  is)
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder stringBuilder = new StringBuilder();
                String line = null;

                try {

                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }

                    reader.close();

                } catch (IOException e) {
                    // obsłuż wyjątek
                    Log.d(MainActivity.class.getSimpleName(), e.toString());
                }

                return stringBuilder.toString();
            }

        }
    }

