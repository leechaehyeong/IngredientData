package com.example.ingredientdata;

import static com.example.ingredientdata.R.id.editText1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //SQl 객체
    mySQLiteHelper mySQLiteHelper;
    SQLiteDatabase sqlDB;

    TextView textView2, textView3, textView4;
    EditText editText1, editText2, editText3;
    Button button, button2, button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);

        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);

        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        //SQL
        mySQLiteHelper = new mySQLiteHelper(this);
        sqlDB = mySQLiteHelper.getWritableDatabase();
        mySQLiteHelper.onUpgrade(sqlDB, 1, 2);
        sqlDB.close();

        //추가버튼//
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //입력한 거 데이터베이스에 넣기
                sqlDB = mySQLiteHelper.getWritableDatabase();
                sqlDB.execSQL("INSERT INTO itemData VALUES ( '"+editText1.getText().toString()+ "' , '"+editText2.getText().toString()+ "', '"+editText3.getText().toString()+ "');");
                sqlDB.close();

                //db 가져와 표시
                sqlDB = mySQLiteHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM itemData;", null);

                String itemName = "재료명" +"\r\n";
                String itemCount = "수량" +"\r\n";
                String itemDate = "유통기한" +"\r\n";


                while (cursor.moveToNext()){
                    itemName += cursor.getString(0)+"\r\n";
                    itemCount += cursor.getString(1)+"\r\n";
                    itemDate += cursor.getString(2)+"\r\n";

                }

                textView2.setText(itemName);
                textView3.setText(itemCount);
                textView4.setText(itemDate);

                cursor.close();
                sqlDB.close();
            }
        });

        //삭제//
        button2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                sqlDB = mySQLiteHelper.getWritableDatabase();
                sqlDB.execSQL("DELETE FROM itemData WHERE itemName='"+editText1.getText().toString()+"'");
                sqlDB.close();
                Toast.makeText(getApplicationContext(),"삭제됨",0).show();

                //db 가져와 표시
                sqlDB = mySQLiteHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM itemData;", null);

                String itemName = "재료명" +"\r\n";
                String itemCount = "수량" +"\r\n";
                String itemDate = "유통기한" +"\r\n";


                while (cursor.moveToNext()){
                    itemName += cursor.getString(0)+"\r\n";
                    itemCount += cursor.getString(1)+"\r\n";
                    itemDate += cursor.getString(2)+"\r\n";

                }

                textView2.setText(itemName);
                textView3.setText(itemCount);
                textView4.setText(itemDate);

                cursor.close();
                sqlDB.close();
            }
        });

        //수정버튼
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sql 수정
                sqlDB = mySQLiteHelper.getWritableDatabase();
                sqlDB.execSQL("UPDATE itemData SET itemCount='"+editText2.getText().toString()+"', itemDate='"+editText3.getText().toString()+"' WHERE itemName='"+editText1.getText().toString()+"'");
                sqlDB.close();
                Toast.makeText(getApplicationContext(),"수정됨", Toast.LENGTH_SHORT).show();

                //db 가져와 표시
                sqlDB = mySQLiteHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM itemData;", null);

                String itemName = "재료명" +"\r\n";
                String itemCount = "수량" +"\r\n";
                String itemDate = "유통기한" +"\r\n";


                while (cursor.moveToNext()){
                    itemName += cursor.getString(0)+"\r\n";
                    itemCount += cursor.getString(1)+"\r\n";
                    itemDate += cursor.getString(2)+"\r\n";

                }

                textView2.setText(itemName);
                textView3.setText(itemCount);
                textView4.setText(itemDate);

                cursor.close();
                sqlDB.close();
            }
        });


    }




    //SQL 객체 선언
    public class mySQLiteHelper extends SQLiteOpenHelper {
        public mySQLiteHelper(Context context) {
            super(context, "itemData", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE itemData (itemName TEXT, itemCount TEXT, itemDate TEXT );");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS itemData");
            onCreate(db);

        }
    }
}