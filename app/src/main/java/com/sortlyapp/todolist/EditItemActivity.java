package com.sortlyapp.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    static String EditItemKey = "EditItemKey";
    static String EditItemPosition = "EditItemPosition";

    private int position;
    private String editItem;

    private EditText etEditTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etEditTodo = (EditText)findViewById(R.id.etEditTodo);

        editItem = getIntent().getStringExtra(EditItemKey);
        position = getIntent().getIntExtra(EditItemPosition,0);

        etEditTodo.setText(editItem);
    }

    public void SaveEditItem(View view) {
        Intent data = new Intent();
        data.putExtra(EditItemKey, etEditTodo.getText().toString());
        data.putExtra(EditItemPosition, position);
        setResult(RESULT_OK, data);
        finish();
    }
}
