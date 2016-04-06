package ru.ilfat.products;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import ru.ilfat.products.dummy.DummyContent;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class ItemListActivity extends AppCompatActivity implements View.OnClickListener {


    private boolean mTwoPane;

    AlertDialog.Builder ad;
    Context context;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.item_detail_container) != null) {

            mTwoPane = true;
        }
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(DBHelper.DATABASE_TABNAME, null, null, null, null, null, null);

        DummyContent.ITEMS.clear();
        DummyContent.ITEM_MAP.clear();

        if (cursor != null && cursor.moveToFirst()) {
            do {

                String id1 = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper._ID));
                String content1 = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.GOODS_NAME_COLUMN));
                String price1 = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.GOODS_PRICE_COLUMN));
                String sum1 = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.GOODS_SUM_COLUMN));
                String description1 = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.GOODS_DESCRIPTION_COLUMN));
                DummyContent.DummyItem dummyItem=new DummyContent.DummyItem(id1,content1,price1,sum1,description1);

                DummyContent.ITEMS.add(dummyItem);
                DummyContent.ITEM_MAP.put(dummyItem.id,dummyItem);
                //Log.d("myLogs","ID="+ id1+"  "+content1);
            } while (cursor.moveToNext());
        }
        cursor.close();


    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DummyContent.ITEMS));


    }

    @Override
    public void onClick(View v) {

        Intent intent=new Intent(this,CreateProdict.class);
        startActivity(intent);
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<DummyContent.DummyItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }




        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);
            holder.mPriceView.setText(mValues.get(position).price);
            holder.mSumView.setText(mValues.get(position).sum);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    context = ItemListActivity.this;
                    final String title = "Товары";
                    String message = "Выберите действие";
                    String btn2 = "Удалить";
                    String btn1 = "Редактировать";

                    ad = new AlertDialog.Builder(context);
                    ad.setTitle(title);
                    ad.setMessage(message);
                    ad.setPositiveButton(btn1, new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mTwoPane) {
                                Bundle arguments = new Bundle();
                                arguments.putString(ItemDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                                ItemDetailFragment fragment = new ItemDetailFragment();
                                fragment.setArguments(arguments);
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.item_detail_container, fragment)
                                        .commit();
                            } else {
                                Context context = holder.mView.getContext();
                                Intent intent = new Intent(context, ItemDetailActivity.class);
                                intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                                context.startActivity(intent);
                            }

                        }
                    });
                    ad.setNegativeButton(btn2, new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DBHelper dbHelper = new DBHelper(context);
                            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

                            Cursor cursor = sqLiteDatabase.query(DBHelper.DATABASE_TABNAME, null, null, null, null, null, null);


                            sqLiteDatabase.delete(DBHelper.DATABASE_TABNAME, BaseColumns._ID + " = " + holder.mItem.id, null);


                            Intent i=getIntent();
                            finish();
                            startActivity(i);
                        }
                    });
                    ad.show();
                }
            });
        }





        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public final TextView mPriceView;
            public final TextView mSumView;
            public DummyContent.DummyItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
                mPriceView = (TextView) view.findViewById(R.id.price);
                mSumView = (TextView) view.findViewById(R.id.sum);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'"
                        + " '" + mPriceView.getText() + "'"
                        + " '" + mSumView.getText() + "'";
            }
        }
    }


}
