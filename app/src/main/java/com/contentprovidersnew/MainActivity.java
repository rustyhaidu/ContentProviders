package com.contentprovidersnew;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    LinearLayout linearLayout;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        Button addButton = (Button) findViewById(R.id.addButton);
        final EditText editText = (EditText) findViewById(R.id.nameEditText);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText.getText().toString();
                if (name.length() > 0) {
                    dbHelper.addContact(name);
                    updateList();
                    editText.setText("");
                }
            }
        });

        updateList();
    }

    public void updateList() {
        linearLayout.removeAllViews();

        Row[] rows = dbHelper.getContacts();
        for (Row row : rows) {
            TextView newEntry = getNewTextView(row.id, row.name);
            linearLayout.addView(newEntry);
        }
    }

    private TextView getNewTextView(String id, String name) {
        TextView textView = new TextView(this);
        textView.setText(id + "  " + name);
        textView.setTextSize(24f);
        return textView;
    }
}