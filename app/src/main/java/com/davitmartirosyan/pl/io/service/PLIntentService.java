package com.davitmartirosyan.pl.io.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.davitmartirosyan.pl.R;
import com.davitmartirosyan.pl.db.entity.Product;
import com.davitmartirosyan.pl.db.entity.ProductResponse;
import com.davitmartirosyan.pl.io.rest.HttpRequestManager;
import com.davitmartirosyan.pl.io.rest.HttpResponseUtil;
import com.davitmartirosyan.pl.util.Constant;
import com.davitmartirosyan.pl.util.NetworkUtil;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.ArrayList;

public class PLIntentService extends IntentService {

    // ===========================================================
    // Constants
    // ===========================================================

    private static final String LOG_TAG = PLIntentService.class.getSimpleName();

    private class Extra {
        static final String URL = "URL";
        static final String POST_ENTITY = "POST_ENTITY";
        static final String REQUEST_TYPE = "REQUEST_TYPE";
    }

    // ===========================================================
    // Fields
    // ===========================================================

    // ===========================================================
    // Constructors
    // ===========================================================

    public PLIntentService() {
        super(PLIntentService.class.getName());
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Listeners, methods for/from Interfaces
    // ===========================================================

    // ===========================================================
    // Start/stop commands
    // ===========================================================

    /**
     * @param url         - calling api url
     * @param requestType - string constant that helps us to distinguish what request it is
     * @param postEntity  - POST request entity (json string that must be sent on server)
     */

    public static void start(Context context, String url, String postEntity,
                             int requestType) {
        Intent intent = new Intent(context, PLIntentService.class);
        intent.putExtra(Extra.URL, url);
        intent.putExtra(Extra.REQUEST_TYPE, requestType);
        intent.putExtra(Extra.POST_ENTITY, postEntity);
        context.startService(intent);
    }

    public static void start(Context context, String url,
                             int requestType) {
        Intent intent = new Intent(context, PLIntentService.class);
        intent.putExtra(Extra.URL, url);
        intent.putExtra(Extra.REQUEST_TYPE, requestType);
        context.startService(intent);
    }

    // ===========================================================
    // Methods for/from SuperClass
    // ===========================================================

    @Override
    protected void onHandleIntent(Intent intent) {
        String url = intent.getExtras().getString(Extra.URL);
        String data = intent.getExtras().getString(Extra.POST_ENTITY);
        int requestType = intent.getExtras().getInt(Extra.REQUEST_TYPE);
        Log.i(LOG_TAG, requestType + Constant.Symbol.SPACE + url);

        switch (requestType) {
            case HttpRequestManager.RequestType.PRODUCT_LIST:
                if(NetworkUtil.getInstance().isConnected(this)) {
                    HttpURLConnection connection = HttpRequestManager.executeRequest(
                            url,
                            HttpRequestManager.RequestMethod.GET,
                            null);
                    String json = HttpResponseUtil.parseResponse(connection);
                    Log.d(LOG_TAG, json);

                    ProductResponse productResponse = new Gson().fromJson(json, ProductResponse.class);
                    ArrayList<Product> products = productResponse.getProducts();
                    Log.d(LOG_TAG, "Products amount " + products.size());
                    // Experimenting with products
                    Log.d(LOG_TAG,products.get(0).toString());
                }
                else {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), R.string.msg_connection_error,Toast.LENGTH_LONG).show();
                        }
                    });
                }
                break;
            case HttpRequestManager.RequestType.PRODUCT_ITEM:
                break;
        }
    }

    // ===========================================================
    // Methods
    // ===========================================================
    // ===========================================================
    // Util
    // ===========================================================

}