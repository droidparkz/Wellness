package in.droidparkz.wellness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;


public class Homewindow extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout lifeline,pharmacy,services,log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homewindow);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        lifeline = (RelativeLayout) findViewById(R.id.homewindowcontent1);
        lifeline.setOnClickListener(this);

        pharmacy = (RelativeLayout) findViewById(R.id.homewindowcontent2);
        pharmacy.setOnClickListener(this);

        services = (RelativeLayout) findViewById(R.id.homewindowcontent3);
        services.setOnClickListener(this);

        log = (RelativeLayout) findViewById(R.id.homewindowcontent4);
        log.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view == lifeline)
        {
            Intent intent = new Intent(Homewindow.this,Lifeline.class);
            startActivity(intent);
        }

        if (view == pharmacy)
        {
            Intent intent = new Intent(Homewindow.this,Pharmacy.class);
            startActivity(intent);
        }

        if (view == services)
        {
            Intent intent = new Intent(Homewindow.this,Services.class);
            startActivity(intent);
        }

        if (view == log)
        {
            Intent intent = new Intent(Homewindow.this,Log.class);
            startActivity(intent);
        }

    }
}
