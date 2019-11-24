package in.droidparkz.wellness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

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

public class Pharmacy extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private PharmacyAdapter mpharmacyAdapter;
    private ArrayList<PharmacyItem> mPharmacyList;
    private RequestQueue mRequestQueue;

    String URL_PHARMACY = "http://192.168.1.101/wellness/pharmacyfetch.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mRecyclerView = findViewById(R.id.recycler_view_pharmacy);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPharmacyList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);

        loadProducts();

    }

    private void loadProducts() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PHARMACY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            mPharmacyList.clear();

                            for (int i = 0; i <= array.length()-1; i++) {

                                JSONObject pharmacy = array.getJSONObject(i);

                                    mPharmacyList.add(new PharmacyItem(
                                            pharmacy.getString("name"),
                                            pharmacy.getString("quantity"),
                                            pharmacy.getString("price")
                                    ));

                            }
                            //creating adapter object and setting it to recyclerview
                            PharmacyAdapter adapter = new PharmacyAdapter(Pharmacy.this, mPharmacyList);
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
}
