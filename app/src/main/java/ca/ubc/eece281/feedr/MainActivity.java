package ca.ubc.eece281.feedr;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends Activity {
    public static final String TAG = "Feedr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Declare and assign our buttons and text
        final Button feedButton = (Button) findViewById(R.id.button);
        View.OnClickListener feedListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PostClient().execute("food");
            }
        };
        feedButton.setOnClickListener(feedListener);

        final Button breakfastButton = (Button) findViewById(R.id.breakfast_button);
        View.OnClickListener breakfastListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PostClient().execute("breakfast");
            }
        };
        breakfastButton.setOnClickListener(breakfastListener);

        final Button lunchButton = (Button) findViewById(R.id.lunch_button);
        View.OnClickListener lunchListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PostClient().execute("lunch");
            }
        };
        lunchButton.setOnClickListener(lunchListener);

        final Button dinnerButton = (Button) findViewById(R.id.dinner_button);
        View.OnClickListener dinnerListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PostClient().execute("dinner");
            }
        };
        dinnerButton.setOnClickListener(dinnerListener);

        final Button automodeButton = (Button) findViewById(R.id.automode_button);
        View.OnClickListener automodeListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PostClient().execute("automode");
            }
        };
        automodeButton.setOnClickListener(automodeListener);

        final Button foreverButton = (Button) findViewById(R.id.forever_button);
        View.OnClickListener foreverListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PostClient().execute("forever");
            }
        };
        foreverButton.setOnClickListener(foreverListener);

        final Button stopButton = (Button) findViewById(R.id.stop_button);
        View.OnClickListener stopListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PostClient().execute("stop");
            }
        };
        stopButton.setOnClickListener(stopListener);
    }


 /*
  * POST EXAMPLE
  */
    // We must do this as a background task, elsewhere our app crashes
    class PostClient extends AsyncTask<String, Void, String> {
        String toastOutput;
        public String doInBackground(String... IO) {

            // Predefine variables
            String io = new String(IO[0]);
            URL url;

            try {
                // Stuff variables
                url = new URL("https://api.spark.io/v1/devices/54ff6c066678574956320167/PetFeeder");
                String param = "access_token=986d62b893e6c266eea1eb68d12dc9d8953b79bc&params="+io;
                Log.d(TAG, "param:" + param);

                // Open a connection using HttpURLConnection
                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

                con.setReadTimeout(7000);
                con.setConnectTimeout(7000);
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setInstanceFollowRedirects(false);
                con.setRequestMethod("POST");
                con.setFixedLengthStreamingMode(param.getBytes().length);
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                // Send
                PrintWriter out = new PrintWriter(con.getOutputStream());
                out.print(param);
                out.close();

                con.connect();

                BufferedReader in = null;
                if (con.getResponseCode() != 200) {
                    in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                    Log.d(TAG, "!=200: " + in);
                    toastOutput = "There was a problem...";
                } else {
                    in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    Log.d(TAG, "POST request send successful: " + in);
                    toastOutput = "Pawzler command: " + io + " issued!";
                }


            } catch (Exception e) {
                Log.d(TAG, "Exception");
                e.printStackTrace();
                return null;
            }
            // Set null and we´e good to go
            return null;
        }

     @Override
     protected void onPostExecute(String s) {
         super.onPostExecute(s);
         Toast.makeText(MainActivity.this, toastOutput, Toast.LENGTH_SHORT).show();
     }
 }
}