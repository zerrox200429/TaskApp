package com.taskapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FormActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTitle;
    private EditText editDesc;
    SharedPreferences preferences;
    final String SAVE_TEXT="saved_text";
    final String LOAD_TEXT="load_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        editTitle = findViewById(R.id.editTitle);
        editDesc = findViewById(R.id.editDesc);
        loadText();
    }
    public void onClick(View view) {
        String title = editTitle.getText().toString();
        String desc = editDesc.getText().toString();
        if (TextUtils.isEmpty(title)) {
            editTitle.setError("Заполните это поле");
            return;
        }
        if (TextUtils.isEmpty(desc)) {
            editDesc.setError("Заполните это поле");
            return;
        }
        Task task = new Task(title, desc);
        Intent intent = new Intent();
        intent.putExtra("task", task);
        setResult(RESULT_OK, intent);
        finish();

    }



    private void loadText() {
        preferences=getPreferences(MODE_PRIVATE);
        String savedText=preferences.getString(SAVE_TEXT,"");
        String loadText=preferences.getString(LOAD_TEXT,"");
        editTitle.setText(savedText);
        editDesc.setText(loadText);
        Toast.makeText(FormActivity.this, "Данные считаны", Toast.LENGTH_SHORT).show();
    }

    private void saveText() {
        preferences=getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed=preferences.edit();
        ed.putString(SAVE_TEXT,editTitle.getText().toString());
        ed.putString(LOAD_TEXT,editDesc.getText().toString());
        ed.commit();
        Toast.makeText(FormActivity.this, "Text was saved!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveText();
    }
}
