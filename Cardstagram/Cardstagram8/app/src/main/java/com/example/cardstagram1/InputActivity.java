package com.example.cardstagram1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class InputActivity extends AppCompatActivity {

    EditText titleEdit,descriptionEdit;
    ImageView imageView;
    String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<Model> models = (ArrayList<Model>) args.getSerializable("MODELKEY");
        titleEdit = findViewById(R.id.titleEditText);
        descriptionEdit = findViewById(R.id.descriptionEditText);




        Button addImageButton = (Button)findViewById(R.id.add_picture);
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });

        Button addCardButton = (Button) findViewById(R.id.add_card);
        addCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEdit.getText().toString();
                String description = descriptionEdit.getText().toString();
                if(title.equals("")  || description.equals("")  || imageView.getDrawable() == null) {
                    Toast.makeText(getApplicationContext(),"Not Enough Information", Toast.LENGTH_SHORT).show();

                } else {
                    models.add(new Model(temp,title,description));
                    Intent intent = new Intent(InputActivity.this,HomeScreen.class);
                    Bundle args = new Bundle();
                    args.putSerializable("MODELKEY1",models);
                    intent.putExtra("BUNDLE1", args);
                    startActivity(intent);
                };

            }

        });
        Button backButton = (Button) findViewById(R.id.backBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            temp = selectedImage.toString();
            imageView = findViewById(R.id.imageView4);
            imageView.setImageURI(selectedImage);
        }


    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}