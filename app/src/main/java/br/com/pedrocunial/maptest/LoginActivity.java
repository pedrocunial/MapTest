package br.com.pedrocunial.maptest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
                //Checks Authentication
                if(username.getText().toString().equals("admin")&&password.getText().toString().equals("12345")){
                    Toast.makeText(getApplicationContext(), "Login...",Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(LoginActivity.this, MapsActivity.class);
                    startActivity(it);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Usuário ou senha incorreto, tente novamente",Toast.LENGTH_SHORT).show();
                    Intent itStatus = new Intent(LoginActivity.this,StatusActivity.class);
                    startActivity(itStatus);
                }
            }
        });
    }
    //Disable Android Back Button
    @Override
    public void onBackPressed() {
    }
}
