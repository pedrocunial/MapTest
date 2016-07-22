package br.com.pedrocunial.maptest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by summerjob on 07/07/16.
 */
public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button entrar_btn;
    boolean flag;
    private static final String EXTRA_IMAGE = "br.com.pedrocunial.maptest.extraImage";
    private static final String EXTRA_TITLE = "br.com.pedrocunial.maptest.extraTitle";
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //

        this.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), EXTRA_IMAGE);
        String itemTitle = getIntent().getStringExtra(EXTRA_TITLE);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(itemTitle);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        username = (EditText)findViewById(R.id.loginText);
        password = (EditText)findViewById(R.id.senhaText);
        entrar_btn = (Button)findViewById(R.id.enter_btn);

        entrar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String file_url = "http://192.168.43.208:3000/login";
                final String name = username.getText().toString();
                final String pass = password.getText().toString();
                final String[] userData = {name, pass, file_url};

                LoginRequestActivity asyncTask =new LoginRequestActivity(new LoginRequestActivity.AsyncResponse() {
                    @Override
                    public void processFinish(Object output) {
                        Log.d("Response", (String) output);
                        if(output.equals("ok")){
                            Toast.makeText(getApplicationContext(), "Login...",Toast.LENGTH_SHORT).show();
                            Intent it = new Intent(LoginActivity.this, MapsActivity.class);
                            startActivity(it);
                        }else{
                            Toast.makeText(getApplicationContext(), "Usuário ou senha incorreto, tente novamente",Toast.LENGTH_SHORT).show();
                            Intent itStatus = new Intent(LoginActivity.this,StatusActivity.class);
                            startActivity(itStatus);
                        }

                    }
                });
                asyncTask.execute(userData);
            //    LoginRequestActivity httpRequest = new LoginRequestActivity();
             //   String status="falhou";
              //  new LoginRequestActivity().execute(userData);
               // System.out.println(status);
                //if(status.equals("ok")){
                 //   System.out.println("autenticado");
               // }
                //Checks Authentication
                /*if(username.getText().toString().equals("admin")&&password.getText().toString().equals("12345")){
                    Toast.makeText(getApplicationContext(), "Login...",Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(LoginActivity.this, MapsActivity.class);
                    startActivity(it);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Usuário ou senha incorreto, tente novamente",Toast.LENGTH_SHORT).show();
                    Intent itStatus = new Intent(LoginActivity.this,StatusActivity.class);
                    startActivity(itStatus);
                }*/
            }
        });
    }
    //Disable Android Back Button
    @Override
    public void onBackPressed() {
    }
}
