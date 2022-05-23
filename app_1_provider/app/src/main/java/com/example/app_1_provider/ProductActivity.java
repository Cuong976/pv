package com.example.app_1_provider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    static final String uri = "content://com.example.app_1_provider";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        EditText edt_id = findViewById(R.id.edt_id);
        EditText edt_name = findViewById(R.id.edt_name);
        EditText edt_unit = findViewById(R.id.edt_unit);
        EditText edt_madein = findViewById(R.id.edt_madein);
        GridView gv_display = findViewById(R.id.gridview_productlist);

        Button btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("id",edt_id.getText().toString());
                contentValues.put("name",edt_name.getText().toString());
                contentValues.put("unit",edt_unit.getText().toString());
                contentValues.put("madein",edt_madein.getText().toString());
                Uri product = Uri.parse(uri);
                Uri insert_uri = getContentResolver().insert(product, contentValues);
                Toast.makeText(getApplicationContext(), "Lưu thành công", Toast.LENGTH_SHORT).show();
            }
        });

        Button btn_select = findViewById(R.id.btn_select);
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> string_list = new ArrayList<>();
                Uri product = Uri.parse(uri);
                Cursor cursor = getContentResolver().query(product, null, null, null, "name");
                if(cursor != null){
                    cursor.moveToFirst();
                    do {
                        string_list.add(cursor.getInt(0)+"");
                        string_list.add(cursor.getString(1));
                        string_list.add(cursor.getString(2));
                        string_list.add(cursor.getString(3));
                    }while (cursor.moveToNext());
                        ArrayAdapter<String> Adapter = new ArrayAdapter<>(ProductActivity.this,
                                android.R.layout.simple_list_item_1, string_list);
                    gv_display.setAdapter(Adapter);
                }else
                    Toast.makeText(ProductActivity.this, "Không có kết quả", Toast.LENGTH_SHORT).show();
            }
        });

        Button btn_update = findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put("id", edt_id.getText().toString());
                values.put("name", edt_name.getText().toString());
                values.put("unit", edt_unit.getText().toString());
                values.put("madein", edt_madein.getText().toString());
                Uri product = Uri.parse(uri);
                String id = edt_id.getText().toString();
                int update_uri = getContentResolver().update(product, values,"id = ?", new String[]{id});
                Toast.makeText(getApplicationContext(), "cập nhật thành công", Toast.LENGTH_SHORT).show();
            }
        });

        Button btn_delete = findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri product = Uri.parse(uri);
                String id = edt_id.getText().toString();
                int delete_uri = getContentResolver().delete(product,"id = ?", new String[]{id});
                Toast.makeText(getApplicationContext(), "xóa thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }
}