package ru.ilfat.products;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper implements BaseColumns {

    //имя базы данных
    private static final String DATABASE_NAME="MyDBList1.db";
    //версия базы данных
    private static final int DATABASE_VERSION=1;
    //имя таблицы
    public static final String DATABASE_TABNAME="ListOfGoods";
    //названия столбцов

    public static final String GOODS_NAME_COLUMN="product_name";
    public static final String GOODS_PRICE_COLUMN="product_price";
    public static final String GOODS_SUM_COLUMN="product_sum";
    public static final String GOODS_DESCRIPTION_COLUMN="product_description";




    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DATABASE_TABNAME + " ("
                + BaseColumns._ID + " integer primary key autoincrement, "
                + GOODS_NAME_COLUMN + " text not null, "
                + GOODS_PRICE_COLUMN + " text not null, "
                + GOODS_SUM_COLUMN + " text not null, "
                + GOODS_DESCRIPTION_COLUMN + " text not null);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.w("SQLite", "Обновление с версии " + oldVersion + " на версию" + newVersion);

        db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABNAME);

        onCreate(db);

    }
}
