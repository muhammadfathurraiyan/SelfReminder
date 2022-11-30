package id.ac.unsyiah.android.selfreminderv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private ArrayList<ViewConGetSet> viewConGetSetArrayList;
    private DBHelper dbHelper;
    private InsertAdapter insertAdapter;
    private RecyclerView recyclerView;
    private ImageButton del;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.navigation_list);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        startActivity(new Intent(getApplicationContext(), MainActivity2.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.navigation_list:
                        return true;
                }
                return false;
            }
        });
        // initializing our all variables.
        viewConGetSetArrayList = new ArrayList<>();
        dbHelper = new DBHelper(MainActivity3.this);

        // getting our course array
        // list from db handler class.
        viewConGetSetArrayList = dbHelper.reminderRead();

        // on below line passing our array lost to our adapter class.
        insertAdapter = new InsertAdapter(viewConGetSetArrayList, MainActivity3.this);
        recyclerView = findViewById(R.id.rv_list);

        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity3.this, RecyclerView.VERTICAL, false);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        // setting our adapter to recycler view.
        recyclerView.setAdapter(insertAdapter);

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity2.class));
        overridePendingTransition(0,0);
    }
}