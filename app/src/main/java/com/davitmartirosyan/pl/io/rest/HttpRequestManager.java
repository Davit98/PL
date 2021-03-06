package com.davitmartirosyan.pl.io.rest;


import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.davitmartirosyan.pl.util.Constant.Util.UTF_8;

public class HttpRequestManager {

    public class RequestType {
        public static final int PRODUCT_LIST = 1;
        public static final int PRODUCT_ITEM = 2;
    }

    public class RequestMethod {
        public static final String POST = "POST";
        public static final String GET = "GET";
        public static final String PUT = "PUT";
    }

    public static HttpURLConnection executeRequest(String apiUrl, String requestMethod, String data) {
        HttpURLConnection connection = null;

        try {
            URL ulr = new URL(apiUrl);
            connection = (HttpURLConnection) ulr.openConnection();
            connection.setRequestMethod(requestMethod);
            connection.setUseCaches(false);
            Log.d("testt","executeRequest");

            switch (requestMethod) {
                case RequestMethod.GET:
                    connection.connect();
                    break;
                case RequestMethod.PUT:
                case RequestMethod.POST:
                    // I don't really understand lines 41-46
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.connect();
                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write(data != null ? data.getBytes(UTF_8) : new byte[]{0});
                    outputStream.flush();
                    outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
