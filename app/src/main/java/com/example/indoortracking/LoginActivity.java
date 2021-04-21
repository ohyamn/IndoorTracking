package com.example.indoortracking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

/*import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;*/

public class LoginActivity extends AppCompatActivity {
    EditText inputUsername, inputPassword;
    ProgressBar progressBar;
    Button logInBtn;
    private static final String LOGIN_KEY = "LOGIN_KEY";
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }*/

        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        inputUsername = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        logInBtn = findViewById(R.id.btn_login);

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = inputUsername.getText().toString();
                final String password = inputPassword.getText().toString();

                progressBar.setVisibility(View.VISIBLE);

                Request<String> request = APIRequests.login(LoginActivity.this, MyApp.Domain,username,password, new APICallback(){

                    @Override
                    public void onSuccess(String result) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onSuccess(JSONArray result) {
                    }

                    @Override
                    public void onError(VolleyError result) throws Exception {
                    }

                    @Override
                    public void onError(String result) {
                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(request);
            }
        });
    }
}

