package daren.systemwewnetrznymocarnypk;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.Random;

public class Parking extends AppCompatActivity {
  // TextView textView2 = (TextView) findViewById(R.id.textView2);
    String[] s = {"1","2","13", "34" ,"25","0", "19"};
    final String add_url ="http://192.168.1.150:8000/api/rest/v1/parkspots/66b5f696-e01c-46f0-a55f-16b393f6ea84/";
    HashMap<String,String> ars = new HashMap<String,String>();
    Button[] button=new Button[9];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking);
        new WebServiceHandler()
                .execute(add_url);

      // button = new Button[ars.size()];
        button[0] = ((Button) findViewById(R.id.button4));
        button[1] = ((Button) findViewById(R.id.button5));
        button[2] = ((Button) findViewById(R.id.button6));
        button[3] = ((Button) findViewById(R.id.button7));
        button[4] = ((Button) findViewById(R.id.button8));
        button[5] = ((Button) findViewById(R.id.button9));
        button[6] = ((Button) findViewById(R.id.button10));
        button[7] = ((Button) findViewById(R.id.button11));
        button[8] = ((Button) findViewById(R.id.button12));
        int miejsca = ars.size();

        for(int i =0;i<button.length;i++)
        {
            buttonListener(i+1,button[i]);
        }

  }
    public void buttonListener(final int i, Button b )
    {
        final String[] add_urls = new String[1];
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_urls[0] = add_url+String.valueOf(i)+"/take/";
                new WebServiceHandler()
                        .execute(add_urls[0]);
                Toast.makeText(Parking.this, "Zarezerwowano", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class WebServiceHandler extends AsyncTask<String, Void, String> {

            // okienko dialogowe, które każe użytkownikowi czekać
            private ProgressDialog dialog = new ProgressDialog(Parking.this);

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
                    URL url = new URL(urls[0]);

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
            @Override
            protected void onPostExecute(String result) {
                // chowamy okno dialogowe
                dialog.dismiss();
                try {
                    // reprezentacja obiektu JSON w Javie
                    JSONObject json = new JSONObject(result);
                    JSONArray jsonArray = json.optJSONArray("data");
                    // pobranie pól obiektu JSON i wyświetlenie ich na ekranie

                    for(int i=0; i < jsonArray.length(); i++){
                        JSONObject obj = jsonArray.getJSONObject(i);
                        button[i].setText(obj.optString("location"));
                        ars.put("spot",obj.optString("spot"));
                        ars.put("id",obj.optString("id"));
                        ars.put("location",obj.optString("location"));
                    }
                    //if(!json.optString("Status").equals("OK"))


                   // ((TextView) findViewById(R.id.fortuneText)).setText("Jest dostępne  miejsce nr: 1");
                   // else
                   // ((TextView) findViewById(R.id.fortuneText)).setText("Brak wolnych miejsc");
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
}}




