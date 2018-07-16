package com.totalbp.equipmentinspection;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.totalbp.equipmentinspection.config.AppConfig;
import com.totalbp.equipmentinspection.config.JSONParser;
import com.totalbp.equipmentinspection.config.SessionManager;
import com.totalbp.equipmentinspection.controller.MMController;
import com.totalbp.equipmentinspection.helper.NetworkHelper;
import com.totalbp.equipmentinspection.utils.MProgressDialog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.services.concurrency.AsyncTask;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Ezra.R on 28/07/2017.
 */


public class LoginActivity extends AppCompatActivity {

    private EditText edtTextNik, edtTextPassword;
    private Button btnLogin;
    private SessionManager session;
    private TextInputLayout txtInputLayoutNik,txtInputLayoutPassword;
    public static final int RequestPermissionCode = 1;
    private ProgressDialog pDialog;
    private NetworkHelper networkHelper;
    RequestQueue requestQueue;
    String nikparam, passwordparam;
    MMController controller;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE  = 99;
    private InputStream is = null;

    private String result = null, decodeResult = null;

    JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        controller = new MMController();
        txtInputLayoutNik = (TextInputLayout) findViewById(R.id.txtInputLayoutNik);
        txtInputLayoutPassword = (TextInputLayout) findViewById(R.id.txtInputLayoutPassword);

        edtTextNik = (EditText) findViewById(R.id.nik);
        edtTextPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.nik_sign_in_button);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            //Toast.makeText(getApplicationContext(), "CATCH s"+session.isLoggedIn(), Toast.LENGTH_LONG).show();
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    // SQLite database handler
//                    db = new UserHandler(getApplicationContext());

                    // Session manager
                    session = new SessionManager(getApplicationContext());
                    nikparam = edtTextNik.getText().toString().trim();
                    passwordparam = edtTextPassword.getText().toString().trim();
                    submitForm();
                }
                else{
                    if (checkSelfPermission(Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA,
                                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                    else{
                        // SQLite database handler
//                        db = new UserHandler(getApplicationContext());
                        // Session manager
                        session = new SessionManager(getApplicationContext());
                        nikparam = edtTextNik.getText().toString().trim();
                        passwordparam = edtTextPassword.getText().toString().trim();
                        submitForm();
                    }
                }




            }
        });

    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(LoginActivity.this, new String[]
                {

                        CAMERA,
                        READ_EXTERNAL_STORAGE,
                        WRITE_EXTERNAL_STORAGE
                }, RequestPermissionCode);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {


                    boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadExternalPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean WriteExternalPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    if (CameraPermission && ReadExternalPermission && WriteExternalPermission) {
//                        Toast toast=Toast.makeText(getApplicationContext(), "Permission Granted",Toast.LENGTH_LONG);
//                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//                        toast.show();
                    }
                    else {
//                        Toast toast=Toast.makeText(getApplicationContext(), "Permission Denied",Toast.LENGTH_LONG);
//                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//                        toast.show();
                    }
                }

                break;
        }
    }

    private void submitForm() {
        if (!validateNik()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        new CreateNewProduct().execute();

    }

    private boolean validateNik() {
        if (edtTextNik.getText().toString().trim().isEmpty()) {
            txtInputLayoutNik.setError("Username Required");
            edtTextNik.requestFocus();
            return false;
        } else {
            txtInputLayoutNik.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (edtTextPassword.getText().toString().trim().isEmpty()) {
            txtInputLayoutPassword.setError("Password Required");
            edtTextPassword.requestFocus();
            return false;
        } else {
            txtInputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    class CreateNewProduct extends AsyncTask<String, String, String> {
        int sess=0;
        String employeeid, userid, storeid, storename, employeename, stockopnameid;
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MProgressDialog.showProgressDialog(LoginActivity.this, true, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    MProgressDialog.dismissProgressDialog();
                }
            });
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {

            String username = edtTextNik.getText().toString();
            String password = edtTextPassword.getText().toString();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("password", password));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(AppConfig.URL_LOGIN,"POST",params);
            //JSONObject json = jsonParser.makeHttpRequest(url_login,"POST",params);


            // check log cat fro response
            Log.d("CreateResponse", json.toString());
            try
            {
                String success = json.getString("success");

                if (success.equals("true"))
                {
                    sess=1;
                    MProgressDialog.dismissProgressDialog();
                    session.createLoginSessionRts(true,username);
                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    MProgressDialog.dismissProgressDialog();
//                    Intent i=new Intent(getApplicationContext(),Login.class);
//                    startActivity(i);
//                    finish();
                }
            }
            catch (JSONException e)
            {
                //Intent i=new Intent(getApplicationContext(),Login.class);
                //startActivity(i);
                e.printStackTrace();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done

            if(sess==1)
            {
                Toast toast4=Toast.makeText(getApplicationContext(), "Login Success",Toast.LENGTH_LONG);//Creating Toast Object
                toast4.setGravity(Gravity.CENTER_VERTICAL, 0, 0);//positioning Toast at Center of the Mobile Screen
                toast4.show();
            }else{
                Toast toast5=Toast.makeText(getApplicationContext(), "Wrong Username Or Password",Toast.LENGTH_LONG);//Creating Toast Object
                toast5.setGravity(Gravity.CENTER_VERTICAL, 0, 0);//positioning Toast at Center of the Mobile Screen
                toast5.show();
            }
            MProgressDialog.dismissProgressDialog();

        }

    }//end class

}