package ca.ubc.eece281.feedr;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Declare and assign our buttons and text
        final Button postButton = (Button) findViewById(R.id.button);

        View.OnClickListener postListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Post d7,HIGH to spark core
                Toast.makeText(MainActivity.this, "Feeding your pet!", Toast.LENGTH_SHORT).show();
                new PostClient().execute("HIGH");
            }
        };
        postButton.setOnClickListener(postListener);
    }

    /*
     * POST EXAMPLE
     */
    // We must do this as a background task, elsewhere our app crashes
    class PostClient extends AsyncTask<String, Void, String> {
        public String doInBackground(String... IO) {

            // Predefine variables
            URL url;

            try {
                // Stuff variables
                url = new URL("https://api.spark.io/v1/devices/54ff73066667515145091567/SCL/"); // core id after devices
                String param = "access_token=986d62b893e6c266eea1eb68d12dc9d8953b79bc&params=food";

                // Open a connection using HttpURLConnection
                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

                con.setReadTimeout(7000);
                con.setConnectTimeout(7000);
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setInstanceFollowRedirects(false);
                con.setRequestMethod("PetFeeder");
                con.setFixedLengthStreamingMode(param.getBytes().length);
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                // Send
                PrintWriter out = new PrintWriter(con.getOutputStream());
                out.print(param);
                out.close();

                con.connect();

                BufferedReader in;
                if (con.getResponseCode() != 200) {
                    in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                } else {
                    in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                }

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            // Set null and we´e good to go
            return null;
        }
    }
}