package id.ac.unsyiah.android.selfreminderv2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private ArrayList<ViewConGetSet> viewConGetSetArrayList;
    private DBHelper dbHelper;
    private InsertAdapter insertAdapter;
    private RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        return true;

                    case R.id.navigation_list:
                        startActivity(new Intent(getApplicationContext(), MainActivity3.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        ImageButton ib1 = findViewById(R.id.ib_study);
        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), MainActivity4.class);
                overridePendingTransition(0,0);
                i.putExtra("key","Study");
                i.putExtra("key1", "list_icon");
                startActivity(i);
            }
        });

        ImageButton ib2 = findViewById(R.id.ib_sport);
        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), MainActivity4.class);
                overridePendingTransition(0,0);
                i.putExtra("key","Sport");
                i.putExtra("key1", "sport_icon");
                startActivity(i);
            }
        });

        ImageButton ib3 = findViewById(R.id.ib_pray);
        ib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), MainActivity4.class);
                overridePendingTransition(0,0);
                i.putExtra("key","Pray");
                i.putExtra("key1", "pray_icon");
                startActivity(i);
            }
        });

        ImageButton ib4 = findViewById(R.id.ib_custom);
        ib4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), MainActivity4.class);
                overridePendingTransition(0,0);
                i.putExtra("key","Custom");
                i.putExtra("key1", "customization_icon");
                startActivity(i);
            }
        });
        // initializing our all variables.
        viewConGetSetArrayList = new ArrayList<>();
        dbHelper = new DBHelper(MainActivity2.this);

        // getting our course array
        // list from db handler class.
        viewConGetSetArrayList = dbHelper.reminderRead();

        // on below line passing our array lost to our adapter class.
        insertAdapter = new InsertAdapter(viewConGetSetArrayList, MainActivity2.this);
        recyclerView = findViewById(R.id.rv_list);

        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity2.this, RecyclerView.VERTICAL, false);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        // setting our adapter to recycler view.
        recyclerView.setAdapter(insertAdapter);

    }
    boolean isPressed = false;
    @Override
    public void onBackPressed() {
        if(isPressed){
            finishAffinity();
            System.exit(0);
        }else{
            Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();
            isPressed = true;
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                isPressed = false;
            }
        };
        new Handler().postDelayed(runnable, 2000);
    }
}


