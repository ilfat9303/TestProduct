package ru.ilfat.products.dummy;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class DummyContent extends AppCompatActivity {

        public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

        public static final Map<String, DummyItem> ITEM_MAP = new HashMap<>();


    public static class DummyItem {
        public final String id;
        public final String content;
        public final String price;
        public final String sum;

        public final String details;

        public DummyItem(String id, String content, String price, String sum, String details) {
            this.id = id;
            this.content = content;
            this.price = price;
            this.sum = sum;
            this.details = details;


        }


    }
    }
