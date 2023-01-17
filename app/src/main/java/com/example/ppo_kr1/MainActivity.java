package com.example.ppo_kr1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private TextView textViewToName;
    private TextView textViewToGroup;
    private TextView textViewToYear;
    private ImageButton imageButtonDelete;
    private ImageButton imageButtonEdit;
    private ImageButton imageButtonCancel;
    private ImageButton imageButtonAllSelect;
    private ListView listView;
    private ArrayAdapter<Album> adapter;
    private static boolean isChecked = false;
    private boolean isAllChecked = false;
    private boolean isAllCheckedChange = false;
    private boolean isOneChecked = false;
    private boolean isNoNullChecked = false;
    public static int changeAlbumPosition = -1;
    public static Album changeAlbum = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        textViewToName = findViewById(R.id.textViewToName);
        textViewToGroup = findViewById(R.id.textViewToGroup);
        textViewToYear = findViewById(R.id.textViewToYear);
        imageButtonAllSelect = findViewById(R.id.imageButtonAllSelected);
        imageButtonDelete = findViewById(R.id.imageButtonDelete);
        imageButtonEdit = findViewById(R.id.imageButtonEdit);
        imageButtonCancel = findViewById(R.id.imageButtonCancel);
        listView = findViewById(R.id.listView);
        adapter = new AlbumAdapter(this);
        listView.setAdapter(adapter);

        textView.setText(R.string.list_of_music_albums);
        textViewToName.setText(R.string.to_name);
        textViewToGroup.setText(R.string.to_group);
        textViewToYear.setText(R.string.to_year);

        if (Albums.albums.isEmpty()) {
            Albums.albums.add(new Album("A Night at the Opera", "Queen", 1975, R.drawable.a_night_at_the_opera));
            Albums.albums.add(new Album("Blackout", "Scorpions", 1982, R.drawable.blackout));
            Albums.albums.add(new Album("Stay Hungry", "Twisted Sister", 1984, R.drawable.stay_hungry));
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (isChecked) {
                    Albums.albums.get(position).setChecked(!Albums.albums.get(position).getChecked());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                changeIsChecked();
                return true;
            }
        });
    }

    public void imageButtonAddClick(View view) {
        isChecked = false;
        Intent intent = new Intent(this, ChangeAlbumListActivity.class);
        startActivity(intent);
    }

    public void imageButtonDeleteClick(View view) {
        if (isNoNullChecked) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.confirm)
                    .setMessage(R.string.are_you_sure_you_want_to_delete_the_selected_albums)
                    .setCancelable(false)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int size = Albums.albums.size();
                            for (int i = 0; i < size; i++) {
                                if (Albums.albums.get(i).getChecked()) {
                                    Albums.albums.remove(i);
                                    i--;
                                    size--;
                                }
                            }
                            adapter.notifyDataSetChanged();
                            if (Albums.albums.isEmpty()) {
                                changeIsChecked();
                            }
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
            Toast.makeText(getApplicationContext(), R.string.you_must_select_at_least_one_album, Toast.LENGTH_SHORT).show();
        }
    }

    public void imageButtonEditClick(View view) {
        if (isOneChecked) {
            for (int i = 0; i < Albums.albums.size(); i++) {
                if (Albums.albums.get(i).getChecked()) {
                    changeAlbum = Albums.albums.get(i);
                    changeAlbumPosition = i;
                }
            }
            isChecked = false;
            Intent intent = new Intent(this, ChangeAlbumListActivity.class);
            startActivity(intent);
        } else {
            if (isNoNullChecked) {
                Toast.makeText(getApplicationContext(), R.string.you_can_t_change_multiple_albums_at_once, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), R.string.you_must_select_at_least_one_album, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void imageButtonCancelClick(View view) {
        changeIsChecked();
    }

    public void imageButtonAllSelectClick(View view) {
        int counterChecked = 0;
        for (int i = 0; i < Albums.albums.size(); i++) {
            if (Albums.albums.get(i).getChecked()) {
                counterChecked++;
            }
        }
        if (!(counterChecked == 0 && isAllChecked) &&
                !(counterChecked == Albums.albums.size() && !isAllChecked)) {
            isAllChecked = !isAllChecked;
        }
        isAllCheckedChange = true;
        for (int i = 0; i < Albums.albums.size(); i++) {
            Albums.albums.get(i).setChecked(isAllChecked);
        }
        adapter.notifyDataSetChanged();

        if(isAllChecked) {
            imageButtonAllSelect.setImageResource(R.drawable.image_button_all_select);
        } else {
            imageButtonAllSelect.setImageResource(R.drawable.image_button_all_select_off);
        }
    }

    public void changeIsChecked() {
        if (imageButtonCancel.getVisibility() == View.VISIBLE) {
            imageButtonDelete.setVisibility(View.INVISIBLE);
            imageButtonEdit.setVisibility(View.INVISIBLE);
            imageButtonCancel.setVisibility(View.INVISIBLE);
            imageButtonAllSelect.setVisibility(View.INVISIBLE);
        } else {
            imageButtonDelete.setVisibility(View.VISIBLE);
            imageButtonEdit.setVisibility(View.VISIBLE);
            imageButtonCancel.setVisibility(View.VISIBLE);
            imageButtonAllSelect.setVisibility(View.VISIBLE);
        }
        isChecked = !isChecked;
        isAllChecked = false;
        for (int i = 0; i < Albums.albums.size(); i++) {
            Albums.albums.get(i).setChecked(isAllChecked);
        }
        isAllCheckedChange = true;
        adapter.notifyDataSetChanged();
    }

    private class AlbumAdapter extends ArrayAdapter<Album> {

        public AlbumAdapter(Context context) {
            super(context, android.R.layout.simple_list_item_1, Albums.albums);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Album album = getItem(position);
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.add_new_field, null);
            TextView textViewName = (TextView) view.findViewById(R.id.textViewName);
            textViewName.setText(album.getName());
            TextView textViewGroup = (TextView) view.findViewById(R.id.textViewGroup);
            textViewGroup.setText(album.getGroup());
            TextView textViewYear = (TextView) view.findViewById(R.id.textViewYear);
            textViewYear.setText(Integer.toString(album.getYear()));
            ImageView iconImageView = (ImageView) view.findViewById(R.id.imageViewIcon);
            CheckBox checkBoxSelected = (CheckBox) view.findViewById(R.id.checkBoxSelected);

            if (album.getDrawable() == -1) {
                iconImageView.setImageURI(album.getUri());
            } else {
                iconImageView.setImageResource(album.getDrawable());
            }

            if (isChecked) {
                checkBoxSelected.setVisibility(View.VISIBLE);
                imageButtonAllSelect.setVisibility(View.VISIBLE);
            } else {
                checkBoxSelected.setVisibility(View.INVISIBLE);
                imageButtonAllSelect.setVisibility(View.INVISIBLE);
            }

            checkBoxSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    album.setChecked(isChecked);
                    setChecked();
                }
            });
            checkBoxSelected.setChecked(album.getChecked());
            setChecked();

            if (isAllCheckedChange) {
                isAllCheckedChange = false;
                checkBoxSelected.setChecked(isAllChecked);
            }

            return view;
        }

        public void setChecked() {
            int counterChecked = 0;
            for (int i = 0; i < Albums.albums.size(); i++) {
                if (Albums.albums.get(i).getChecked()) {
                    counterChecked++;
                }
            }
            if (counterChecked == 1) {
                isOneChecked = true;
                imageButtonEdit.setImageResource(R.drawable.image_button_edit);
            } else {
                isOneChecked = false;
                imageButtonEdit.setImageResource(R.drawable.image_button_edit_off);
            }
            if (counterChecked > 0) {
                isNoNullChecked = true;
                imageButtonDelete.setImageResource(R.drawable.image_button_delete);
            } else {
                isNoNullChecked = false;

                imageButtonDelete.setImageResource(R.drawable.image_button_delete_off);
            }
            if (counterChecked == Albums.albums.size()) {
                imageButtonAllSelect.setImageResource(R.drawable.image_button_all_select);
            } else {
                imageButtonAllSelect.setImageResource(R.drawable.image_button_all_select_off);
            }
        }
    }
}