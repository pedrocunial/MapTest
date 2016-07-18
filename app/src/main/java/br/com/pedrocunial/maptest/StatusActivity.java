package br.com.pedrocunial.maptest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StatusActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //View Parameters
    Button btn_send, btn_next;
    EditText comment_text;
    ImageButton image_btn;
    //Email Parameters
    Bitmap thumbnail;
    File pic;
    boolean foto=false;
    protected static final int CAMERA_PIC_REQUEST = 0;

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        index = getIntent().getExtras().getInt("index");

        //Get View Components
        btn_send = (Button) findViewById(R.id.send_btn);
        btn_next = (Button) findViewById(R.id.next_btn);
        image_btn = (ImageButton) findViewById(R.id.camera_btn);
        comment_text=(EditText) findViewById(R.id.comment_window);
        //sets spinner list
        setSpinnerList();

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });

        image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Opens camera app
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
                foto=true;
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Back to Maps Activity
                Toast.makeText(getApplicationContext(), "OS Finalizada",Toast.LENGTH_SHORT).show();
                Intent it = new Intent(StatusActivity.this, MapsActivity.class)
                        .putExtra("index", index);
                startActivity(it);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        // Selects dropdown item
        String item = parent.getItemAtPosition(position).toString();
        switch (position){
            case 0:Toast.makeText(parent.getContext(), "Selecionado: " + item, Toast.LENGTH_LONG).show();
                   enableViewComponents(false);
                   break;
            case 1:Toast.makeText(parent.getContext(), "Selecionado: " + item, Toast.LENGTH_LONG).show();
                   enableViewComponents(true);
                   break;
            case 2:Toast.makeText(parent.getContext(), "Selecionado: " + item, Toast.LENGTH_LONG).show();
                   enableViewComponents(true);
                   break;
            case 3:Toast.makeText(parent.getContext(), "Selecionado: " + item, Toast.LENGTH_LONG).show();
                   enableViewComponents(true);
                   break;
            case 4:Toast.makeText(parent.getContext(), "Selecionado: " + item, Toast.LENGTH_LONG).show();
                    enableViewComponents(true);
                   break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Converts picture to PNG
        if (requestCode == CAMERA_PIC_REQUEST) {
            thumbnail = (Bitmap) data.getExtras().get("data");
            ImageView image = (ImageView) findViewById(R.id.image_comment);
            image.setImageBitmap(thumbnail);


            try {
                File root = Environment.getExternalStorageDirectory();
                if (root.canWrite()){
                    pic = new File(root, "pic.png");
                    FileOutputStream out = new FileOutputStream(pic);
                    thumbnail.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                Log.e("BROKEN", "Could not write file " + e.getMessage());
            }

        }
    }
    public void setSpinnerList(){
        //Set Spinner Dropdown List
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
        enableViewComponents(false);
    }
    public void enableViewComponents(boolean b){
        //Enables Send button and Comment Edit Text to user
        btn_send.setEnabled(b);
        comment_text.setEnabled(b);
        btn_next.setEnabled(b);
    }
    public void sendEmail(){
        Intent i = new Intent(Intent.ACTION_SEND);
        if(foto) { //if user attachs a pic
            i.setType("image/png");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"cflavs.7@gmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "Assistencia Tecnica Sky");
            i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(pic));
            i.putExtra(Intent.EXTRA_TEXT, comment_text.getText());
            try {
                startActivity(Intent.createChooser(i, "Enviando Email..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(StatusActivity.this, "Comunicaçaõ Falhou", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            i.setType("plane/text");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"cflavs.7@gmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "Assistencia Tecnica Sky");
            i.putExtra(Intent.EXTRA_TEXT, comment_text.getText());
            try {
                startActivity(Intent.createChooser(i, "Enviando Email..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(StatusActivity.this, "Comunicaçaõ Falhou", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
