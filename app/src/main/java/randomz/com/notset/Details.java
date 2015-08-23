package randomz.com.notset;



import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Details extends AppCompatActivity {
    private Toolbar toolbar;
    //private TextView txtAmount;
    //private TextView txtCategory;
    //private Button btnSave;
    private FloatingActionButton btnF;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_view);
        toolbar=(Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayShowHomeEnabled(true); // this will add a home button to the navigation drawer



        //calling the Fragment
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navifation_drawer);

        //self-Defined Method
        drawerFragment.setUp(R.id.fragment_navifation_drawer, (DrawerLayout) findViewById(R.id.Drawer_Layout), toolbar);



        //  txtAmount = (TextView)findViewById(R.id.txtAmount);
        //txtCategory = (TextView)findViewById(R.id.txtCategory);
        //btnSave = (Button) findViewById(R.id.btnSave);
        //btnSave.setOnClickListener(new View.OnClickListener() {
        btnF = (FloatingActionButton) findViewById(R.id.btnFab);
        btnF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(Details.this,MainActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }
}
