package com.example.billcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btCalculate, btClear;

    EditText etKwh, etRebate;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //button
        btCalculate = findViewById(R.id.btCalculate);
        btClear = findViewById(R.id.btClear);


        //edit text
        etKwh = findViewById(R.id.etKwh);
        etRebate = findViewById(R.id.etRebate);

        //textview
        textView = findViewById(R.id.textView);

        btCalculate.setOnClickListener(this);
        btClear.setOnClickListener(new View.OnClickListener() {

            //error handling (clear)
            @Override
            public void onClick(View v) {

                    String kwh = etKwh.getText().toString();
                    String rebate = etRebate.getText().toString();

               if (kwh.isEmpty() && rebate.isEmpty()){
                   Toast.makeText(MainActivity.this,"Already Empty",Toast.LENGTH_SHORT).show();
               }
               else {
                   etKwh.setText("");
                   etRebate.setText("");
                   textView.setText("RM 00.00");
               }
            }
        });


    }
    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    //menu options
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int selected = item.getItemId();

        if (selected == R.id.menuAbout){
            startActivity(new Intent(this, About.class));
            return true;
        } else if (selected == R.id.menuSettings) {

            Toast.makeText(MainActivity.this,"Settings Clicked",Toast.LENGTH_SHORT).show();
            return true;
            
        }

        return super.onOptionsItemSelected(item);
    }

    //calculation
    @Override
    public void onClick(View view){
        if (view == btCalculate){
            String kwh = etKwh.getText().toString();
            String rebate = etRebate.getText().toString();

            double kilowatt, rebatee;
            double total1 = 0, total2= 0;

            try {
                kilowatt = Double.parseDouble(kwh);
                rebatee = Double.parseDouble(rebate);

                final double FIRST_200_RATE = 0.218;
                final double NEXT_100_RATE = 0.334;
                final double NEXT_300_RATE = 0.516;
                final double NEXT_300_ONWARDS_RATE = 0.546;

                if (kilowatt <= 200) {
                    total1 = kilowatt * FIRST_200_RATE;
                }

                else if (kilowatt <= 300) {
                    total1 = 200 * FIRST_200_RATE + (kilowatt - 200) * NEXT_100_RATE;
                }

                else if (kilowatt <= 600) {
                    total1 = 200 * FIRST_200_RATE + 100 * NEXT_100_RATE + (kilowatt - 300) * NEXT_300_RATE;
                }

                else {
                    total1 = 200 * FIRST_200_RATE + 100 * NEXT_100_RATE + 300 * NEXT_300_RATE + (kilowatt - 600) * NEXT_300_ONWARDS_RATE;
                }

                total2 = total1 - (total1*(rebatee/100));



            } catch (NumberFormatException nfe){

                Toast.makeText(this,"Please enter number in the input fields", Toast.LENGTH_SHORT).show();
            } catch (Exception exception){

                Toast.makeText(this,"Please enter number in the input fields - unspecified error", Toast.LENGTH_SHORT).show();
            }


            textView.setText("RM " + total2);


        }


    }





}