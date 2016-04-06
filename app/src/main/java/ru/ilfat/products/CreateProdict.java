package ru.ilfat.products;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ru.ilfat.products.dummy.DummyContent;

public class CreateProdict extends AppCompatActivity {
    EditText edName,edPrice,edSum,edDescript;
    Button btnAdd;
    Button btnCancel;
    DBHelper dbHelper;

    @Override
    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_prodict);

        edName=(EditText) findViewById(R.id.edName);
        edPrice=(EditText) findViewById(R.id.edPrice);
        edSum=(EditText) findViewById(R.id.edSum);
        edDescript=(EditText) findViewById(R.id.edDescrip);
        btnAdd=(Button) findViewById(R.id.btnAdd);
        btnCancel=(Button) findViewById(R.id.btnCancel);
    }


    public void onAddClick (View v) {

        Intent intMainActiv;
        dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        //проверка на пустоту строк (Если они все пусты, то не совершать ничего)
        if (TextUtils.isEmpty(edName.getText().toString())
                || TextUtils.isEmpty(edPrice.getText().toString())
                || TextUtils.isEmpty(edSum.getText().toString())) {
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.GOODS_NAME_COLUMN, edName.getText().toString());
        contentValues.put(DBHelper.GOODS_PRICE_COLUMN, edPrice.getText().toString());
        contentValues.put(DBHelper.GOODS_SUM_COLUMN, edSum.getText().toString());
        contentValues.put(DBHelper.GOODS_DESCRIPTION_COLUMN, edDescript.getText().toString());

        database.insert(DBHelper.DATABASE_TABNAME, null, contentValues);
        DummyContent.ITEMS.clear();
        DummyContent.ITEM_MAP.clear();

        intMainActiv=new Intent(this,ItemListActivity.class);
        startActivity(intMainActiv);
            }



    public void onCancelClick (View v){
       Intent intMainActiv=new Intent(this,ItemListActivity.class);

        startActivity(intMainActiv);

    }

}
