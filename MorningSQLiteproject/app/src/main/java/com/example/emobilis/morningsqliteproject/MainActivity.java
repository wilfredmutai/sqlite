package com.example.emobilis.morningsqliteproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText mName,mEmail,mId;
    Button mBtnSave,mBtnView,mBtnDelete;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mName = findViewById(R.id.edtName);
        mEmail = findViewById(R.id.edtMail);
        mId = findViewById(R.id.edtId);
        mBtnView = findViewById(R.id.btnView);
        mBtnSave = findViewById(R.id.btnSave);
        mBtnDelete = findViewById(R.id.btnDelete);
        db = openOrCreateDatabase("VotersDb",MODE_PRIVATE,null);
        //Query to create a table
        db.execSQL("CREATE TABLE IF NOT EXISTS voterreg(name VARCHAR,emial VARCHAR,idno VARCHAR)");
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if user has filled in all records
                String name = mName.getText().toString();
                String email = mEmail.getText().toString();
                String id = mId.getText().toString().trim();
                if (name.isEmpty()){
                    messageDisplay("NAME ERROR","Kindly fill your Name");
                }else if (email.isEmpty()){
                    messageDisplay("EMAIL ERROR","Kindly fill in your email");
                }else if (id.isEmpty()){
                    messageDisplay("ID ERROR","Kindly fill in your Id number");
                }else {
                    //Query to insert into db
                    db.execSQL("INSERT INTO voterreg VALUES('"+name+"','"+email+"','"+id+"')");
                    messageDisplay("QUERY SUCCESS","Data saved successfully");
                    clear();
                }
            }
        });
        mBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Query to view db records
                Cursor cursor = db.rawQuery("SELECT * FROM voterreg",null);
                //Check if there is any records
                if (cursor.getCount()==0){
                    messageDisplay("NO RECORDS","Sorry,no records were found");
                }
                //use buffer to append records
                StringBuffer buffer = new StringBuffer();
                while (cursor.moveToNext()){
                    buffer.append("\n"+cursor.getString(0));
                    buffer.append("\t"+cursor.getString(1));
                    buffer.append("\t"+cursor.getString(2));
                    buffer.append("\n");
                }
                messageDisplay("DATABASE RECORDS",buffer.toString());
            }
        });



    }
    //Message display function

    private void messageDisplay(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.create().show();
    }
    //Clear edit text after saving
    public  void clear(){
        mName.setText("");
        mEmail.setText("");
        mId.setText("");
    }
}
