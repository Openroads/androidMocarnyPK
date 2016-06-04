package daren.systemwewnetrznymocarnypk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class Parking extends AppCompatActivity {
  // TextView textView2 = (TextView) findViewById(R.id.textView2);
    String[] s = {"1","2","13", "34" ,"25","0", "19"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking);
        final TextView fortuneText = (TextView) findViewById(R.id.fortuneText);
        Button button =   (Button) findViewById(R.id.button3);
        final TextView text2 = (TextView) findViewById(R.id.text2);

        final int index = new Random().nextInt(s.length);
        fortuneText.setText(s[index]);

        assert button != null;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 5:
                int i= Integer.parseInt(s[index]);
                i-=1;
                s[index]=""+i;
                fortuneText.setText(s[index]);
                text2.setText("Zarezerwowano miejsce parkingowe!");
            }
        });


    }
}
