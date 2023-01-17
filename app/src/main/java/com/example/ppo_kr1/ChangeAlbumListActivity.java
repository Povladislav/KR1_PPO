package com.example.ppo_kr1;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ChangeAlbumListActivity extends AppCompatActivity {

    private static EditText editTextNameOfNewAlbum;
    private static EditText editTextGroupOfNewAlbum;
    private static EditText editTextYearOfNewAlbum;
    private static TextView textViewPicturePath;
    private TextView textViewWarningName;
    private TextView textViewWarningGroup;
    private TextView textViewWarningYear;
    private Button buttonOkChangeAlbumList;
    private Button buttonBackToMainMenu;
    private ImageButton imageButtonLoadImage;
    private static ImageButton imageButtonCancelImage;
    private static ImageView imageViewGetImage;
    private String stdEditTextNameOfNewAlbum = "";
    private String stdEditTextGroupOfNewAlbum = "";
    private String stdEditTextYearOfNewAlbum = "";
    private Album newAlbum = new Album("", "", 0, -1);
    private Uri selectedImage;
    private static String pathToImage;
    private boolean imageIsNotEmpty = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_album_list_activity);

        editTextNameOfNewAlbum = findViewById(R.id.editTextNameOfNewAlbum);
        editTextGroupOfNewAlbum = findViewById(R.id.editTextGroupOfNewAlbum);
        editTextYearOfNewAlbum = findViewById(R.id.editTextYearOfNewAlbum);
        textViewWarningName = findViewById(R.id.textViewWarningName);
        textViewWarningGroup = findViewById(R.id.textViewWarningGroup);
        textViewWarningYear = findViewById(R.id.textViewWarningYear);
        buttonOkChangeAlbumList = findViewById(R.id.buttonOkChangeAlbumList);
        buttonBackToMainMenu = findViewById(R.id.buttonBackToMainMenu);
        imageButtonLoadImage = findViewById(R.id.imageButtonLoadImage);
        textViewPicturePath = findViewById(R.id.textViewPicturePath);
        imageViewGetImage = findViewById(R.id.imageViewGetImage);
        imageButtonCancelImage = findViewById(R.id.imageButtonCancelImage);

        buttonOkChangeAlbumList.setText(R.string.button_ok);
        buttonBackToMainMenu.setText(R.string.button_back);
        textViewWarningName.setText(R.string.warning);
        textViewWarningGroup.setText(R.string.warning);
        textViewWarningYear.setText(R.string.warning);

        if (MainActivity.changeAlbumPosition != -1) {
            editTextNameOfNewAlbum.setText(MainActivity.changeAlbum.getName());
            editTextGroupOfNewAlbum.setText(MainActivity.changeAlbum.getGroup());
            editTextYearOfNewAlbum.setText(Integer.toString(MainActivity.changeAlbum.getYear()));
            stdEditTextNameOfNewAlbum = MainActivity.changeAlbum.getName();
            stdEditTextGroupOfNewAlbum = MainActivity.changeAlbum.getGroup();
            stdEditTextYearOfNewAlbum = Integer.toString(MainActivity.changeAlbum.getYear());
            if ((MainActivity.changeAlbum.getDrawable() == -1 && MainActivity.changeAlbum.getPathToImage() == "") ||
                    MainActivity.changeAlbum.getDrawable() == R.drawable.standart_album_icon) {
                imageViewGetImage.setImageResource(R.drawable.standart_album_icon);
            } else if (MainActivity.changeAlbum.getPathToImage() == "") {
                imageViewGetImage.setImageResource(MainActivity.changeAlbum.getDrawable());
                imageIsNotEmpty = true;
                imageButtonCancelImage.setVisibility(View.VISIBLE);
                pathToImage = "";
            } else {
                imageViewGetImage.setImageURI(MainActivity.changeAlbum.getUri());
                textViewPicturePath.setText(MainActivity.changeAlbum.getPathToImage());
                pathToImage = MainActivity.changeAlbum.getPathToImage();
                imageButtonCancelImage.setVisibility(View.VISIBLE);
            }
        }

        editTextNameOfNewAlbum.setHint(R.string.name_of_the_new_album);
        editTextGroupOfNewAlbum.setHint(R.string.group_of_the_new_album);
        editTextYearOfNewAlbum.setHint(R.string.year_of_the_new_album);

        imageButtonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1);
            }
        });

        editTextNameOfNewAlbum.addTextChangedListener(new com.example.ppo_kr1.TextValidator(editTextNameOfNewAlbum) {
            @Override
            public void validate(TextView textView, String text) {
                checkInput();
                textViewWarningName.setVisibility(View.INVISIBLE);
            }
        });
        editTextGroupOfNewAlbum.addTextChangedListener(new TextValidator(editTextNameOfNewAlbum) {
            @Override
            public void validate(TextView textView, String text) {
                checkInput();
                textViewWarningGroup.setVisibility(View.INVISIBLE);
            }
        });
        editTextYearOfNewAlbum.addTextChangedListener(new TextValidator(editTextNameOfNewAlbum) {
            @Override
            public void validate(TextView textView, String text) {
                checkInput();
                textViewWarningYear.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void buttonOkChangeAlbumListClick(View view) {
        if (checkInput()) {
            newAlbum.setName(editTextNameOfNewAlbum.getText().toString());
            newAlbum.setGroup(editTextGroupOfNewAlbum.getText().toString());
            newAlbum.setYear(Integer.parseInt(editTextYearOfNewAlbum.getText().toString()));
            if (pathToImage == "") {
                newAlbum.setDrawable(R.drawable.standart_album_icon);
            } else {
                newAlbum.setUri(selectedImage);
                newAlbum.setPathToImage(pathToImage);
            }
            if (MainActivity.changeAlbumPosition != -1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChangeAlbumListActivity.this);
                builder.setTitle(R.string.confirm)
                        .setMessage(R.string.are_you_sure_you_want_to_save_the_changes)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Albums.albums.set(MainActivity.changeAlbumPosition, newAlbum);
                                MainActivity.changeAlbumPosition = -1;
                                MainActivity.changeAlbum = null;
                                pathToImage = "";
                                backToMainMenu();
                            }
                        }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                Albums.albums.add(newAlbum);
                backToMainMenu();
            }
        } else {
            if (MainActivity.changeAlbumPosition == -1) {
                if (editTextNameOfNewAlbum.getText().toString().equals(stdEditTextNameOfNewAlbum)) {
                    textViewWarningName.setVisibility(View.VISIBLE);
                }
                if (editTextGroupOfNewAlbum.getText().toString().equals(stdEditTextGroupOfNewAlbum)) {
                    textViewWarningGroup.setVisibility(View.VISIBLE);
                }
                if (editTextYearOfNewAlbum.getText().toString().equals(stdEditTextYearOfNewAlbum)) {
                    textViewWarningYear.setVisibility(View.VISIBLE);
                }
                Toast.makeText(getApplicationContext(), R.string.there_should_be_no_empty_fields, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), R.string.you_need_to_make_some_changess, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void buttonBackToMainMenuClick(View view) {
        MainActivity.changeAlbumPosition = -1;
        MainActivity.changeAlbum = null;
        clearInput();
        backToMainMenu();
    }

    public void imageButtonCancelImageClick(View view) {
        clearInput();
        checkInput();
    }

    public void clearInput() {
        imageIsNotEmpty = false;
        editTextNameOfNewAlbum.clearComposingText();
        editTextGroupOfNewAlbum.clearComposingText();
        editTextYearOfNewAlbum.clearComposingText();
        imageButtonCancelImage.setVisibility(View.INVISIBLE);
        textViewPicturePath.setText("");
        pathToImage = "";
        imageViewGetImage.setImageResource(R.drawable.standart_album_icon);
    }

    public boolean checkInput() {
        if (MainActivity.changeAlbumPosition == -1) {
            if (!editTextNameOfNewAlbum.getText().toString().equals(stdEditTextNameOfNewAlbum)
                    && !editTextGroupOfNewAlbum.getText().toString().equals(stdEditTextGroupOfNewAlbum)
                    && !editTextYearOfNewAlbum.getText().toString().equals(stdEditTextYearOfNewAlbum)
            ) {
                buttonOkChangeAlbumList.setBackgroundColor(Color.parseColor("#FFFFC107"));
                return true;
            } else {
                buttonOkChangeAlbumList.setBackgroundColor(Color.parseColor("#9F9F9F"));
                return false;
            }
        } else {
            if (!editTextNameOfNewAlbum.getText().toString().equals(stdEditTextNameOfNewAlbum)
                    || !editTextGroupOfNewAlbum.getText().toString().equals(stdEditTextGroupOfNewAlbum)
                    || !editTextYearOfNewAlbum.getText().toString().equals(stdEditTextYearOfNewAlbum)
                    || !MainActivity.changeAlbum.getPathToImage().equals(textViewPicturePath.getText().toString())
                    || (MainActivity.changeAlbum.getDrawable() != -1 &&
                    MainActivity.changeAlbum.getDrawable() != R.drawable.standart_album_icon && !imageIsNotEmpty)
                    || ((MainActivity.changeAlbum.getDrawable() == R.drawable.standart_album_icon ||
                    MainActivity.changeAlbum.getDrawable() == -1) && pathToImage != "")) {
                buttonOkChangeAlbumList.setBackgroundColor(Color.parseColor("#FFFFC107"));
                return true;
            } else {
                buttonOkChangeAlbumList.setBackgroundColor(Color.parseColor("#9F9F9F"));
                return false;
            }
        }
    }

    public void backToMainMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        clearInput();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case 1:
                    selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().
                            query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    pathToImage = cursor.getString(columnIndex);
                    cursor.close();
                    textViewPicturePath.setText(pathToImage);
                    imageViewGetImage.setImageURI(selectedImage);
                    imageButtonCancelImage.setVisibility(View.VISIBLE);
                    checkInput();
                    break;
            }
        }
    }
}