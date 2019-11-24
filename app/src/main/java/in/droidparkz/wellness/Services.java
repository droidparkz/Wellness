package in.droidparkz.wellness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class Services extends AppCompatActivity implements View.OnClickListener {

    EditText ename,econtact,edate,esession;

    String name,contact,date,session;

    Button submit;

    String Getname,Getcontact,Getdate,Getsession;

    String DataParseUrl = "http://192.168.1.101/wellness/booking.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ename = (EditText) findViewById(R.id.servicesname);
        econtact = (EditText) findViewById(R.id.servicescontact);
        edate = (EditText) findViewById(R.id.servicesdate);
        esession = (EditText) findViewById(R.id.servicessession);

        submit = (Button) findViewById(R.id.servicesbutton);
        submit.setOnClickListener(this);

    }

    public void getInput()
    {
        name = ename.getText().toString();
        contact = econtact.getText().toString();
        date = edate.getText().toString();
        session = esession.getText().toString();

        if(name == null)
        {
            Toast.makeText(Services.this, "Enter Reminder Name", Toast.LENGTH_LONG).show();
        }

        else if(contact == null)
        {
            Toast.makeText(Services.this, "Select Category", Toast.LENGTH_LONG).show();
        }

        else if(date == null)
        {
            Toast.makeText(Services.this, "Select Date", Toast.LENGTH_LONG).show();
        }

        else if(session == null)
        {
            Toast.makeText(Services.this, "Select Time", Toast.LENGTH_LONG).show();
        }
        else
        {
            GetData();
        }

    }

    public void GetData(){

        Getname = name;
        Getcontact = contact;
        Getdate = date;
        Getsession = session;

        SendDataToServer();

    }

    public void SendDataToServer(){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("name", Getname));
                nameValuePairs.add(new BasicNameValuePair("contact", Getcontact));
                nameValuePairs.add(new BasicNameValuePair("date", Getdate));
                nameValuePairs.add(new BasicNameValuePair("session", Getsession));

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

                Toast.makeText(Services.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Services.this,Homewindow.class));

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
