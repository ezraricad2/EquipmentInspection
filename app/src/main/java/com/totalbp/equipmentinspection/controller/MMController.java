package com.totalbp.equipmentinspection.controller;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.totalbp.equipmentinspection.R;
import com.totalbp.equipmentinspection.config.AppConfig;
import com.totalbp.equipmentinspection.config.SessionManager;
import com.totalbp.equipmentinspection.interfaces.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ezra.R on 27/07/2017.
 */

public class MMController extends Application {

    public static final String TAG = MMController.class
            .getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static MMController mInstance;
    private SessionManager session;
    RequestQueue requestQueue;
    JSONArray items = new JSONArray();

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized MMController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }



    public void SaveGeneralObject(final Context context,
                                  String request,
                                  final VolleyCallback callback){
        session = new SessionManager(context);
        Log.d("request",request);

        requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, session.getUrlConfig()+AppConfig.URL_CRUD_INSPECTION, request ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d("requestFin",response.toString());
                        callback.onSave(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(context,
                                    context.getString(R.string.error_timeout),
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            //TODO
                        } else if (error instanceof ServerError) {
                            Toast.makeText(context,
                                    context.getString(R.string.error_timeout),
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            //TODO
                        } else if (error instanceof ParseError) {
                            //TODO
                        }

                    }
                });
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                9000,
                5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(strReq);
    }

    public void SaveInspectionObject(final Context context,
                                  String request,
                                  final VolleyCallback callback){
        session = new SessionManager(context);
        Log.d("request",request);

        requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, session.getUrlConfig()+AppConfig.URL_CRUD_INSPECTION_MAIN, request ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d("requestFin",response.toString());
                        callback.onSave(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(context,
                                    context.getString(R.string.error_timeout),
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            //TODO
                        } else if (error instanceof ServerError) {
                            Toast.makeText(context,
                                    context.getString(R.string.error_timeout),
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            //TODO
                        } else if (error instanceof ParseError) {
                            //TODO
                        }

                    }
                });
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                9000,
                5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(strReq);
    }

    public void InqGeneralPagingFullEzra(final Context context, final String keyInquiry,
                                         final String paramName1, final String paramVal1,
                                         final String paramName2, final String paramVal2,
                                         final String paramName3, final String paramVal3,
                                         final String paramName4, final String paramVal4,
                                         final String paramName5, final String paramVal5,
                                         final String paramName6, final String paramVal6,
                                         final String paramName7, final String paramVal7,
                                         final String paramName8, final String paramVal8,
                                         final VolleyCallback callback){
        session = new SessionManager(context);
        //JSONObject request = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {

            JSONObject object = new JSONObject();
            object.put("Params", "UniqueDesc");
            object.put("ParamsValue", keyInquiry);

            JSONObject object1 = new JSONObject();
            object1.put("Params", paramName1);
            object1.put("ParamsValue", paramVal1);

            JSONObject object2 = new JSONObject();
            object2.put("Params", paramName2);
            object2.put("ParamsValue", paramVal2);

            JSONObject object3 = new JSONObject();
            object3.put("Params", paramName3);
            object3.put("ParamsValue", paramVal3);

            JSONObject object4 = new JSONObject();
            object4.put("Params", paramName4);
            object4.put("ParamsValue", paramVal4);

            JSONObject object5 = new JSONObject();
            object5.put("Params", paramName5);
            object5.put("ParamsValue", paramVal5);

            JSONObject object6 = new JSONObject();
            object6.put("Params", paramName6);
            object6.put("ParamsValue", paramVal6);

            JSONObject object7 = new JSONObject();
            object7.put("Params", paramName7);
            object7.put("ParamsValue", paramVal7);

            JSONObject object8 = new JSONObject();
            object8.put("Params", paramName8);
            object8.put("ParamsValue", paramVal8);

            jsonArray.put(object);
            jsonArray.put(object1);
            jsonArray.put(object2);
            jsonArray.put(object3);
            jsonArray.put(object4);
            jsonArray.put(object5);
            jsonArray.put(object6);
            jsonArray.put(object7);
            jsonArray.put(object8);


        } catch (Exception e) {
            e.printStackTrace();

        }

        Log.d("WOI2",jsonArray.toString());

        requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, session.getUrlConfig()+AppConfig.URL_PAGING_RESTFULL_NEWDLL, jsonArray.toString() ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        JSONObject json = null;
                        try {
                            Log.d("calleddd",response.toString());
                            json = new JSONObject(response.getString("Message"));
                            JSONArray jData = json.getJSONArray("Table");

                            Log.d("calleddd1",jData.toString());
                            callback.onSuccess(jData);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Log.d("called",response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(context,
                                    context.getString(R.string.error_timeout),
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            //TODO
                        } else if (error instanceof ServerError) {
                            Toast.makeText(context,
                                    context.getString(R.string.error_timeout),
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            //TODO
                        } else if (error instanceof ParseError) {
                            //TODO
                        }

                    }
                });

        strReq.setRetryPolicy(new DefaultRetryPolicy(
                9000,
                5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(strReq);
    }
}