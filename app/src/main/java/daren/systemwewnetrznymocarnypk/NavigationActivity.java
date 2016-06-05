package daren.systemwewnetrznymocarnypk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        //setContentView(R.layout.app_bar_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //do ogarniecia
        final TextView text = (TextView) findViewById(R.id.text);
       // final TextView text1 = (TextView) findViewById(R.id.text1);
       // final TextView text2 = (TextView) findViewById(R.id.text2);
     //   final TextView text3 = (TextView) findViewById(R.id.text3);

        //http://192.168.1.150:8000/api/rest/v1/users/66b5f696-e01c-46f0-a55f-16b393f6ea84/1/
        new WebServiceHandler().execute("http://192.168.1.150:8000/api/rest/v1/users/66b5f696-e01c-46f0-a55f-16b393f6ea84/1/");
    }
    class WebServiceHandler extends AsyncTask<String, Void, String> {

        // okienko dialogowe, które każe użytkownikowi czekać
        private ProgressDialog dialog = new ProgressDialog(NavigationActivity.this);

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
                URL url = new URL("http://192.168.1.150:8000/api/rest/v1/users/66b5f696-e01c-46f0-a55f-16b393f6ea84/1/");
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

                // pobranie pól obiektu JSON i wyświetlenie ich na ekranie
                // for (JSONObject result : jarray.optJSONArray("data")){

                JSONObject json = new JSONObject(result);
               // JSONArray jarray =   json.optJSONArray("data");
                //JSONObject obj = jarray.getJSONObject(0);
                ((TextView) findViewById(R.id.text)).setText("Email: " + json.optString("name"));


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
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if (id == R.id.action_logout)
        {
            Intent intent = new Intent(NavigationActivity.this,MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_calendar) {
            Intent intent = new Intent(NavigationActivity.this,Calendar.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_projekt) {

        }else if (id == R.id.nav_sala_rezerwacja) {
            Intent intent = new Intent(NavigationActivity.this,BookRoom.class);
            startActivity(intent);

        }else if (id == R.id.nav_parking) {
            Intent intent = new Intent(NavigationActivity.this,Parking.class);
            startActivity(intent);
        }else if (id == R.id.nav_voucher) {

            Intent intent = new Intent(NavigationActivity.this,Vouchers.class);
            startActivity(intent);

        } else if (id == R.id.nav_benefit) {

            Intent intent = new Intent(NavigationActivity.this,BenefitSystem.class);
            startActivity(intent);

        } else if (id == R.id.nav_chat) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
