package com.example.vamshikbd.movieapplication;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Vamshik B D on 05/07/2018.
 */

/*
    This class executes the http request by opening the connection with the respective server.
    It uses GET request Method.
 */
public class HttpHandler {
    private static final String TAG = HttpHandler.class.getSimpleName();

    public HttpHandler() {}

    /*
        This method executes the http connection using GET request method.
     */
    public String makeServiceCall(String reqUrl) {
        Log.i(TAG, "Make Service call");
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            response = convertStreamToString(inputStream);
        } catch (MalformedURLException exception) {
            exception.printStackTrace();
            Log.e(TAG, "MalformedURLException occurred");
        } catch (ProtocolException exception) {
            exception.printStackTrace();
            Log.e(TAG, "ProtocolException occurred");
        } catch (IOException exception) {
            exception.printStackTrace();
            Log.e(TAG, "IOException occurred");
        }  catch (Exception exception) {
            exception.printStackTrace();
            Log.e(TAG, "Exception occurred");
        }
        return response;
    }

    /*
        This method converts the response to string.
     */
    private String convertStreamToString(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();

        String line = "";

        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
        } catch (IOException exception) {
            exception.printStackTrace();
            Log.e(TAG, "IOException occurred");
        } finally {
            try {
                inputStream.close();
            } catch (IOException exception) {
                exception.printStackTrace();
                Log.e(TAG, "IOException occurred");
            }
        }
        return stringBuilder.toString();
    }
}