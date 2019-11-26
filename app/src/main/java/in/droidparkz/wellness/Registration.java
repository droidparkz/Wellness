package in.droidparkz.wellness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Registration extends AppCompatActivity implements View.OnClickListener {

    EditText ename,econtact,eheight,eweight;

    Spinner spinner1;

    String name,contact,height,weight,email,blood;

    GoogleSignInClient mGoogleSignInClient;

    String Getname,Getemail,Getcontact,Getheight,Getweight,Getblood;

    Button submit;

    String DataParseUrl = "http://192.168.1.101/wellness/userset.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null) {

            email = account.getEmail();
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        ename = (EditText) findViewById(R.id.userinfoname);
        econtact = (EditText) findViewById(R.id.userinfocontact);
        eheight = (EditText) findViewById(R.id.userinfoheight);
        eweight = (EditText) findViewById(R.id.userinfoweight);

        submit = (Button) findViewById(R.id.userinfobutton);
        submit.setOnClickListener(this);

        addItemsOnSpinner1();
    }

    public void addItemsOnSpinner1() {

        spinner1 = (Spinner) findViewById(R.id.bloodgroupspinner);
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
        name = ename.getText().toString();
        contact = econtact.getText().toString();
        height = eheight.getText().toString();
        weight = eweight.getText().toString();

        blood = String.valueOf(spinner1.getSelectedItem());

        if(name == null)
        {
            Toast.makeText(Registration.this, "Enter Reminder Name", Toast.LENGTH_LONG).show();
        }

        else if(contact == null)
        {
            Toast.makeText(Registration.this, "Select Category", Toast.LENGTH_LONG).show();
        }

        else if(height == null)
        {
            Toast.makeText(Registration.this, "Select Date", Toast.LENGTH_LONG).show();
        }

        else if(weight == null)
        {
            Toast.makeText(Registration.this, "Select Time", Toast.LENGTH_LONG).show();
        }

        else if(blood == null || blood.equals("Select Blood Group"))
        {
            Toast.makeText(Registration.this, "Select Blood Group", Toast.LENGTH_LONG).show();
        }

        else
        {
            GetData();
        }

    }

    public void GetData(){

        Getname = name;
        Getemail = email;
        Getcontact = contact;
        Getheight = height;
        Getweight = weight;
        Getblood = blood;

        SendDataToServer();

    }

    public void SendDataToServer(){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("name", Getname));
                nameValuePairs.add(new BasicNameValuePair("email", Getemail));
                nameValuePairs.add(new BasicNameValuePair("contact", Getcontact));
                nameValuePairs.add(new BasicNameValuePair("height", Getheight));
                nameValuePairs.add(new BasicNameValuePair("weight", Getweight));
                nameValuePairs.add(new BasicNameValuePair("blood", Getblood));

                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(DataParseUrl);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "Data Submit Successfully";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                Toast.makeText(Registration.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Registration.this,Homewindow.class));

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }


    @Override
    public void onClick(View view) {

        if (view == submit)
        {
            getInput();
        }

    }
}
