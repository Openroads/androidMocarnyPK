package daren.systemwewnetrznymocarnypk;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class Vouchers extends AppCompatActivity {



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vouchers);
        Button button =   (Button) findViewById(R.id.button_1);
        Button button1 =   (Button) findViewById(R.id.button_2);
        TextView text2 = (TextView) findViewById(R.id.text_2);

        button.setOnClickListener(new View.OnClickListener() {
                              @Override

                               public void onClick(View  v){
                                      new WebServiceHandler().execute("http://192.168.1.150:8000/api/rest/v1/food/66b5f696-e01c-46f0-a55f-16b393f6ea84/1/");
                                 }
                           });

        button1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View  v){
                new WebServiceHandler().execute("http://192.168.1.150:8000/api/rest/v1/food/66b5f696-e01c-46f0-a55f-16b393f6ea84/2/");
            }
        });

    }

    class WebServiceHandler extends AsyncTask<String, Void, String> {

        // okienko dialogowe, które każe użytkownikowi czekać
        private ProgressDialog dialog = new ProgressDialog(Vouchers.this);

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

        // metoda wykonuje się po zakończeniu metody głównej,
        // której wynik będzie przekazany;
        // w tej metodzie mamy dostęp do UI
        @Override
        protected void onPostExecute(String result) {

            // chowamy okno dialogowe
            dialog.dismiss();

            try {

                JSONObject json = new JSONObject(result);
                JSONObject jarray =   json.optJSONObject("data");
                String aJsonString = jarray.getString("code");
                ((TextView) findViewById(R.id.text_2)).setText(aJsonString);




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
