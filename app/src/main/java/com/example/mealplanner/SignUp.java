package com.example.mealplanner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class SignUp extends AppCompatActivity {

    EditText username,email,mobileno,password;
    Button signupbtn;

    private String stringUrl="https://eatup-mealplanner.000webhostapp.com/phpfiles/getdata.php";
    private RequestQueue requestQueue;
    private static final String TAG=SignUp.class.getSimpleName();
    int success;
    private String TAG_SUCCESS="success";
    private String TAG_MESSAGE="message";
    private String tag_json_obj="json_obj_req";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TextView signtolog=(TextView)findViewById(R.id.signintologintext);

        email=(EditText)findViewById(R.id.email);
        username=(EditText)findViewById(R.id.username);
        mobileno=(EditText)findViewById(R.id.mobileno);
        password=(EditText)findViewById(R.id.password);
        signupbtn=(Button)findViewById(R.id.signin_btn);
        requestQueue= Volley.newRequestQueue(getApplicationContext());

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    sendData();

            }
        });

        signtolog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signintent=new Intent(SignUp.this,Login.class);
                startActivity(signintent);
            }
        });
    }

    private void sendData()
    {
        StringRequest request=new StringRequest(Request.Method.POST, stringUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    success = jsonObject.getInt(TAG_SUCCESS);
                    if (success == 1)
                    {
                        //Toast.makeText(getApplicationContext(), jsonObject.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                        Toasty.success(getApplicationContext(),"Successfully Register",Toasty.LENGTH_LONG).show();
                        Intent signtomain=new Intent(SignUp.this,Usersmain.class);
                        signtomain.putExtra("passusername",username.getText().toString());
                        startActivity(signtomain);
                        finish();
                    }
                    else
                    {
                        Toasty.error(getApplicationContext(), jsonObject.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    Toasty.error(getApplicationContext(), "Error "+e, Toast.LENGTH_LONG).show();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(getApplicationContext(),error.getMessage().toString(),Toast.LENGTH_LONG).show();

            }
        }){
            public Map<String, String> getParams()
            {

                Map<String, String> params = new HashMap<String, String>();
                params.put("username",username.getText().toString().trim());
                params.put("email",email.getText().toString().trim());
                params.put("mobileno",mobileno.getText().toString().trim() );
                params.put("password",password.getText().toString().trim());
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(10000,1,1.0f));
        requestQueue.add(request);
    }


}