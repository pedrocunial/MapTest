package br.com.pedrocunial.maptest;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StatusActivity extends AppCompatActivity {

    //View Parameters
    private ImageView            image;
    private FloatingActionButton buttonNext;
    private FloatingActionButton imageButton;
    private MaterialEditText     commentText;

    //Email Parameters
    private FileOutputStream out;
    private Bitmap  thumbnail;
    private File    problemPicture;
    private boolean foto = false;

    private Uri fileUri;
    private int index;

    private final int    REQUEST_IMAGE_CAPTURE = 1;
    private final int    EMAIL_SEND_SUCCESS    = 2;
    private final int    EMAIL_SEND_FAIL       = 3;
    private final String TAG                   = "StatusActivity";
    private final String EMAIL_SUBJECT         = "Assistência técnica ZeusTV";

    protected static final int CAMERA_PIC_REQUEST    = 0;

    private boolean mailClientOpened = false;

    // Knowing if the user left the activity to enter an email client
    @Override
    protected void onResume() {
        super.onResume();
        mailClientOpened = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mailClientOpened = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);


        if(getIntent().getExtras() == null) {
            index = 0;
        } else {
            index = getIntent().getExtras().getInt("index");
        }

        //Get View Components
        buttonNext  = (FloatingActionButton) findViewById(R.id.next_btn);
        imageButton = (FloatingActionButton) findViewById(R.id.camera_btn);
        commentText = (MaterialEditText) findViewById(R.id.comment_window);
        image       = (ImageView) findViewById(R.id.image_comment);

        //sets spinner list
        setSpinnerList();

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Opens camera app
                foto = true;
                Intent takePictureIntent = new Intent(android.provider.
                        MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    problemPicture = null;
                    File outputDir = view.getContext().getCacheDir();
                    try {
                        problemPicture = File.createTempFile("visit", ".png", outputDir);
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                    // Continue only if the File was successfully created
                    if (problemPicture != null) {
                        // create a file to save the image
                        fileUri = Uri.fromFile(problemPicture);
                        // set the image file name
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        // Process it!
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }

            }
        });
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail2();
             }
        });

    }

    private void endActivity() {
        //Back to Maps Activity
        boolean success = false;
        try {
            success = problemPicture.delete();
        } catch(NullPointerException e) {
            Log.d(TAG, "No picture found");
        }
        if(success) {
            Log.i(TAG, "Picture deleted");
        } else {
            Log.i(TAG, "Picture could not be deleted");
        }

        success = deleteFile("problemPicture.png");

        if(success) {
            Log.d(TAG, "File deleted");
        } else {
            Log.d(TAG, "File could not be deleted");
        }

        Toast.makeText(getApplicationContext(), "OS Finalizada",Toast.LENGTH_SHORT).show();
        Intent it = new Intent(StatusActivity.this, MapsActivity.class)
                .putExtra("index", index);
        startActivity(it);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Converts picture to PNG
        Log.d(TAG, "inside activityResult");
        if ((requestCode == REQUEST_IMAGE_CAPTURE) && (resultCode == RESULT_OK)) {
            try {
                thumbnail = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);
            } catch(IOException e) {
                e.printStackTrace();
                Log.i(TAG, "Could not set thumbnail");
            }
            image.setImageBitmap(thumbnail);
        } else if(requestCode == EMAIL_SEND_SUCCESS && mailClientOpened) {
            endActivity();
        } else if(requestCode == EMAIL_SEND_FAIL && mailClientOpened) {
            endActivity();
        }
    }
    public void setSpinnerList(){
        //Set Spinner Dropdown List
        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.status_op);

        // Items
        List<String> categories = new ArrayList<String>();
        categories.add("Status");
        categories.add("Resolvido");
        categories.add("Precisa Retornar");
        categories.add("OS Errada");
        categories.add("Não Resolvido");

        // Setting them in the spinner
        spinner.setItems(categories);

        // Adding the listener to the spinner
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long l, String item) {
                // Selects dropdown item
                switch (position){
                    case 0:
                        enableViewComponents(false);
                        break;
                    case 1:
                        enableViewComponents(true);
                        break;
                    case 2:
                        enableViewComponents(true);
                        break;
                    case 3:
                        enableViewComponents(true);
                        break;
                    case 4:
                        enableViewComponents(true);
                        break;
                }
            }
        });
    }
    public void enableViewComponents(boolean b){
        //Enables Send button and Comment Edit Text to user
        commentText.setEnabled(b);
        buttonNext.setEnabled(b);
    }

    public void sendEmail(){
        Intent i = new Intent(Intent.ACTION_SEND);
        if(foto) { //if user attached a pic
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"cflavs.7@gmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, EMAIL_SUBJECT);
            i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(problemPicture));
            i.putExtra(Intent.EXTRA_TEXT, commentText.getText());
            try {
                startActivityForResult(Intent.createChooser(i, "Enviando Email..."),
                                       EMAIL_SEND_SUCCESS);
            } catch (android.content.ActivityNotFoundException ex) {
                Log.i(TAG, "Communication failed");
            }
            try {
                // We try to delete the picture
                boolean success = problemPicture.delete();
                if(success) {
                    Log.i(TAG, "File deleted successfully");
                } else {
                    if(problemPicture.isDirectory()) {
                        success = deleteDirectory(problemPicture);
                        if(success) {
                            Log.i(TAG, "Directory deleted successfully!");
                        } else {
                            Log.i(TAG, "Could not delete directory");
                        }
                    }
                    Log.i(TAG, "File could not be deleted");
                }
            } catch (NullPointerException e) {
                // In case we don't find it
                Log.i(TAG, "File not found");
            }

            try {
                deleteFile("problemPicture.png");
            } catch(NullPointerException e) {
                Log.i(TAG, "Context file not found");
            }
        }
        else {
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"cflavs.7@gmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, EMAIL_SUBJECT);
            i.putExtra(Intent.EXTRA_TEXT, commentText.getText());
            try {
                startActivityForResult(Intent.createChooser(i, "Enviando Email..."),
                                       EMAIL_SEND_FAIL);
            } catch (android.content.ActivityNotFoundException ex) {
                Log.i(TAG, "Communication Failed");
            }
        }
    }

    private void sendEmail2() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rcf822");
        intent.putExtra(Intent.EXTRA_EMAIL,
                        new String[] {"pcc@cesar.org.br"});
        intent.putExtra(Intent.EXTRA_SUBJECT, EMAIL_SUBJECT);
        intent.putExtra(Intent.EXTRA_TEXT, commentText.getText());
        if(foto) {
            intent.putExtra(Intent.EXTRA_STREAM,
                            Uri.fromFile(problemPicture));
            try {
                startActivityForResult(intent, EMAIL_SEND_SUCCESS);
            } catch(ActivityNotFoundException e) {
                Log.i(TAG, "Communication failed");
            }
        } else {
            try {
                startActivityForResult(intent, EMAIL_SEND_FAIL);
            } catch(ActivityNotFoundException e) {
                Log.i(TAG, "Communication failed");
            }
        }
    }

    private boolean deleteDirectory(File file) {
        File[] files = file.listFiles(); // get files inside dir
        for(File f : files) {
            if(f.isDirectory()) {
                deleteDirectory(f);
            } else {
                f.delete();
            }
        }
        return file.delete();
    }

}
