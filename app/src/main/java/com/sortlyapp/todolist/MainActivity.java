package com.sortlyapp.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;

    private ListView lvTodoList;
    private EditText etAdd;
    private Button btnAdd;

    static String EditItemKey = "EditItemKey";
    static String EditItemPosition = "EditItemPosition";

    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvTodoList = (ListView) findViewById(R.id.lvTodoList);
        etAdd = (EditText) findViewById(R.id.etAdd);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        readItems();
        itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, items);
        lvTodoList.setAdapter(itemsAdapter);

        setUpListViewListener();
    }

    public void setUpListViewListener()
    {
        lvTodoList.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View view, int pos, long id) {
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                }
        );

        lvTodoList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                        String editItem = items.get(position);
                        i.putExtra(EditItemKey, editItem);
                        i.putExtra(EditItemPosition, position);
                        startActivityForResult(i, REQUEST_CODE);
                    }
                }
        );
    }

    public void addTodo(View view) {
        String todoItem = etAdd.getText().toString();
        if (todoItem.length() > 0)
        {
            itemsAdapter.add(todoItem);
            etAdd.setText("");
            writeItems();
        }


    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        }
        catch (IOException e)
        {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE)
        {
            String editedString = data.getExtras().getString(EditItemKey);
            int position = data.getIntExtra(EditItemPosition, 0);
            items.set(position,editedString);
            itemsAdapter.notifyDataSetChanged();
        }
    }
}
