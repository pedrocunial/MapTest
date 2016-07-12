package br.com.pedrocunial.maptest;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class StatusActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button btn_send;
    EditText comment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        btn_send = (Button) findViewById(R.id.send_btn);
        //Set Dropdown List
        Spinner spinner = (Spinner) findViewById(R.id.status_op);
        spinner.setOnItemSelectedListener(this);

        List<String> categories = new ArrayList<String>();
        categories.add("Status");
        categories.add("Resolvido");
        categories.add("Precisa Retornar");
        categories.add("OS Errada");
        categories.add("Não Resolvido");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        btn_send.setEnabled(false);
        comment = (EditText)findViewById(R.id.comment_window);
        comment.setEnabled(false);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "OS Finalizada",Toast.LENGTH_SHORT).show();
                Intent it = new Intent(StatusActivity.this, MapsActivity.class);
                startActivity(it);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        // Showing selected spinner item
        switch (position){
            case 0:Toast.makeText(parent.getContext(), "Selecionado: " + item, Toast.LENGTH_LONG).show();
                   btn_send.setEnabled(false);
                   comment.setEnabled(false);
                   break;
            case 1:Toast.makeText(parent.getContext(), "Selecionado: " + item, Toast.LENGTH_LONG).show();
                   btn_send.setEnabled(true);
                   comment.setEnabled(true);
                   break;
            case 2:Toast.makeText(parent.getContext(), "Selecionado: " + item, Toast.LENGTH_LONG).show();
                   btn_send.setEnabled(true);
                   comment.setEnabled(true);
                   break;
            case 3:Toast.makeText(parent.getContext(), "Selecionado: " + item, Toast.LENGTH_LONG).show();
                   btn_send.setEnabled(true);
                   comment.setEnabled(true);
                   break;
            case 4:Toast.makeText(parent.getContext(), "Selecionado: " + item, Toast.LENGTH_LONG).show();
                   btn_send.setEnabled(true);
                   comment.setEnabled(true);
                   break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
