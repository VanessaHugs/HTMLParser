package com.example.vanessaperry.htmlparser;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class MainActivity extends Activity {

    TextView html;
    MyAsyncTask async = new MyAsyncTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        html = findViewById(R.id.html);

        async.execute();
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, String> {

        // This is what we are going to be returning.
        String innerGeneralAnnouncements = "";

        @Override
        protected String doInBackground(Void... arg0) {
            try {
                // Create a URL for the desired page
                String httpsURL = "https://agora.cs.wcu.edu/~veperry1/";
                URL url = new URL(httpsURL);
                Scanner s = new Scanner(url.openStream());
                while (s.hasNextLine()) {
                    innerGeneralAnnouncements =
                            innerGeneralAnnouncements.concat(s.nextLine());
                }
                s.close();
            } catch (MalformedURLException a) {
                System.out.println("Couldn't read from URL");
            } catch (IOException b) {
                System.out.println("IO EXCEPTION");
            }
            return innerGeneralAnnouncements;
        }

        protected void onPostExecute(String result) {
            Spanned resultNotHtml = Html.fromHtml(result);
            html.setText(resultNotHtml);
        }
    }
}

