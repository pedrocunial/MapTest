package br.com.pedrocunial.maptest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Created by summerjob on 07/07/16.
 */
public class LoginActivity extends AppCompatActivity {
    // Atributes
    private EditText username;
    private EditText password;

    private CollapsingToolbarLayout collapsingToolbarLayout;

    private static final String EXTRA_IMAGE = "br.com.pedrocunial.maptest.extraImage";
    private static final String EXTRA_TITLE = "br.com.pedrocunial.maptest.extraTitle";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), EXTRA_IMAGE);
        String itemTitle = getIntent().getStringExtra(EXTRA_TITLE);

        // Defining Collapsing ToolBar values
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(itemTitle);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        collapsingToolbarLayout.setCollapsedTitleTextColor(0xffffffff);
        collapsingToolbarLayout.setExpandedTitleColor(0xffffffff);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShown     = false;
            int     scrollRange = -1;
            // Set title only when the bar is collapsed
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if(scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("ZeusTV");
                    isShown = true;
                } else if(isShown) {
                    collapsingToolbarLayout.setTitle("");
                    isShown = false;
                }
            }
        });

        username = (MaterialEditText) findViewById(R.id.loginText);
        password = (MaterialEditText) findViewById(R.id.senhaText);

        Button enterButton = (Button) findViewById(R.id.enter_btn);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Checks Authentication
                if(username.getText().toString().equals("admin")&&password.getText().toString().equals("12345")){
                    Toast.makeText(getApplicationContext(), "Login...",Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(LoginActivity.this, MapsActivity.class);
                    startActivity(it);
                } else {
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
    // Do nothing
    }
}
