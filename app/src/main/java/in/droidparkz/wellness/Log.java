package in.droidparkz.wellness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

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

public class Log extends AppCompatActivity implements View.OnClickListener {

    EditText txt1;

    ImageView search,nullimage;

    String keyvalue;

    private RecyclerView mRecyclerView;
    private LogAdapter mLogAdapter;
    private ArrayList<LogItem> mLogList;
    private RequestQueue mRequestQueue;

    String URL_LOG = "http://192.168.1.101/wellness/logfetch.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        txt1 = (EditText) findViewById(R.id.logsearchtxt);

        search = (ImageView) findViewById(R.id.logsearchbutton);
        search.setOnClickListener(this);

        mRecyclerView = findViewById(R.id.recycler_view_log);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mLogList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);

        nullimage = (ImageView) findViewById(R.id.lognullimage);
        nullimage.setVisibility(View.INVISIBLE);

    }

    public void getInput()
    {
        keyvalue = txt1.getText().toString().trim();

        loadProducts();
    }

    private void loadProducts() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_LOG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            mLogList.clear();

                            for (int i = 0; i <= array.length()-1; i++) {

                                JSONObject log = array.getJSONObject(i);

                                if (log.getString("id").equals(keyvalue)) {

                                    mLogList.add(new LogItem(
                                            log.getString("name"),
                                            log.getString("date"),
                                            log.getString("link")
                                    ));
                                }
                            }
                            if(mLogList.isEmpty())
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
                            LogAdapter adapter = new LogAdapter(Log.this, mLogList);
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