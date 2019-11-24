package in.droidparkz.wellness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Lifeline extends AppCompatActivity implements View.OnClickListener {

    Spinner spinner1;

    ImageView search,nullimage;

    String keyvalue;

    private RecyclerView mRecyclerView;
    private LifelineAdapter mLifelineAdapter;
    private ArrayList<LifelineItem> mLifelineList;
    private RequestQueue mRequestQueue;

    String URL_LIFELINE = "http://192.168.1.101/wellness/lifelinefetch.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifeline);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        addItemsOnSpinner1();

        search = (ImageView) findViewById(R.id.lifelinesearchbutton);
        search.setOnClickListener(this);

        mRecyclerView = findViewById(R.id.recycler_view_lifeline);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mLifelineList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);

        nullimage = (ImageView) findViewById(R.id.lifelinenullimage);
        nullimage.setVisibility(View.INVISIBLE);

    }

    public void addItemsOnSpinner1() {

        spinner1 = (Spinner) findViewById(R.id.lifelinesearchspinner);
        List<String> list3 = new ArrayList<String>();
        list3.add("Select Blood Group");
        list3.add("A+");
        list3.add("B+");
        list3.add("AB+");
        list3.add("O+");
        list3.add("A-");
        list3.add("B-");
        list3.add("AB-");
        list3.add("O-");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list3);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }

    public void getInput()
    {
        keyvalue = String.valueOf(spinner1.getSelectedItem());

        loadProducts();
    }

    private void loadProducts() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_LIFELINE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            mLifelineList.clear();

                            for (int i = 0; i <= array.length()-1; i++) {

                                JSONObject lifeline = array.getJSONObject(i);

                                if (lifeline.getString("blood").equals(keyvalue)) {

                                    mLifelineList.add(new LifelineItem(
                                            lifeline.getString("name"),
                                            lifeline.getString("email"),
                                            lifeline.getString("contact")
                                    ));
                                }
                            }
                                if(mLifelineList.isEmpty())
                                {
                                    mRecyclerView.setVisibility(View.GONE);
                                    nullimage.setVisibility(View.VISIBLE);
                                }
                                else
                                {
                                    mRecyclerView.setVisibility(View.VISIBLE);
                                    nullimage.setVisibility(View.GONE);
                                }
                            //creating adapter object and setting it to recyclerview
                            LifelineAdapter adapter = new LifelineAdapter(Lifeline.this, mLifelineList);
                            mRecyclerView.setAdapter(adapter);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public void onClick(View view) {

        if (view == search)
        {
            getInput();
        }

    }
}
