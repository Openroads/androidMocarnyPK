package daren.systemwewnetrznymocarnypk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class BenefitSystem extends AppCompatActivity {
    TextView text;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benefit_system);
         button = ((Button) findViewById(R.id.button4));
         text = (TextView) findViewById(R.id.textView_4);
        button.setOnClickListener(new View.OnClickListener(){
        public void onClick(View view){
            text.setText("Zamówiono kartę multisport");


        }


    });
}}
