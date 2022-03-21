package com.example.sqlapplicationandroid;

import static android.text.TextUtils.isEmpty;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.nio.file.Paths;


public class MainActivity extends AppCompatActivity {

    EditText name, quantity, flowertype, color, price, search;
    Button insert, update, delete, view, btnSearchView;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=findViewById(R.id.name);
        quantity=findViewById(R.id.quantity);
        flowertype=findViewById(R.id.flowertype);
        color=findViewById(R.id.color);
        price=findViewById(R.id.price);

        insert= findViewById(R.id.btnInsert);
        update= findViewById(R.id.btnUpdate);
        delete= findViewById(R.id.btnDelete);
        view= findViewById(R.id.btnView);

        DB=new DBHelper(this);

        search=findViewById(R.id.search);
        btnSearchView=findViewById(R.id.btnSearchView);


            insert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (TextUtils.isEmpty(name.getText().toString()) || TextUtils.isEmpty(quantity.getText().toString()) || TextUtils.isEmpty(flowertype.getText().toString()) || TextUtils.isEmpty(color.getText().toString()) || TextUtils.isEmpty(price.getText().toString())) {
                        Toast.makeText(MainActivity.this, "Empty Fields not allowed", Toast.LENGTH_SHORT).show();
                    }else {
                        String nameTXT = name.getText().toString();
                        String quantityTXT = quantity.getText().toString();
                        String flowertypeTXT = flowertype.getText().toString();
                        String colorTXT = color.getText().toString();
                        String priceTXT = price.getText().toString();

                        Boolean checkinsertdata = DB.insertuserdata(nameTXT, quantityTXT, flowertypeTXT, colorTXT, priceTXT);

                        if (checkinsertdata == true)
                            Toast.makeText(MainActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(name.getText().toString()) || TextUtils.isEmpty(quantity.getText().toString()) || TextUtils.isEmpty(flowertype.getText().toString()) || TextUtils.isEmpty(color.getText().toString()) || TextUtils.isEmpty(price.getText().toString()))
                    {
                        Toast.makeText(MainActivity.this, "Empty Fields not allowed", Toast.LENGTH_SHORT).show();
                    } else {
                        String nameTXT = name.getText().toString();
                        String quantityTXT = quantity.getText().toString();
                        String flowertypeTXT = flowertype.getText().toString();
                        String colorTXT = color.getText().toString();
                        String priceTXT = price.getText().toString();

                        Boolean checkupdatedata = DB.updateuserdata(nameTXT, quantityTXT, flowertypeTXT, colorTXT, priceTXT);
                        if (checkupdatedata == true)
                            Toast.makeText(MainActivity.this, "Entry Updated", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "Entry Not Updated\n--------------------------------------------------------------------\nThe flower type you entered does not exist.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(name.getText().toString())) {
                        Toast.makeText(MainActivity.this, "Please input the flower name", Toast.LENGTH_SHORT).show();
                    } else {
                        String nameTXT = name.getText().toString();

                        Boolean checkdeletedata = DB.deletedata(nameTXT);
                        if (checkdeletedata == true)
                            Toast.makeText(MainActivity.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "Entry Not Deleted\n--------------------------------------------------------------------\nThe flower type you entered does not exist.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Cursor res = DB.getdata();
                    if (res.getCount() == 0) {
                        Toast.makeText(MainActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()) {
                        buffer.append("Flower Name : " + res.getString(0) + "\n");
                        buffer.append("Quantity : " + res.getString(1) + "\n");
                        buffer.append("Flower Type : " + res.getString(2) + "\n");
                        buffer.append("Color : " + res.getString(3) + "\n");
                        buffer.append("Price : " + res.getString(4) + "\n\n");
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Order Entries");
                    builder.setMessage(buffer.toString());
                    builder.show();
                }
            });

            btnSearchView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(name.getText().toString())) {
                        Toast.makeText(MainActivity.this, "Please input the flower name", Toast.LENGTH_SHORT).show();
                    } else {
                        String searchTXT = search.getText().toString();
                        Cursor res = DB.getsearchdata(searchTXT);
                        if (res.getCount() == 0) {
                            Toast.makeText(MainActivity.this, "The flower type you entered does not exist.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Flower Name : " + res.getString(0) + "\n");
                            buffer.append("Quantity : " + res.getString(1) + "\n");
                            buffer.append("Flower Type : " + res.getString(2) + "\n");
                            buffer.append("Color : " + res.getString(3) + "\n");
                            buffer.append("Price : " + res.getString(4) + "\n\n");
                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setCancelable(true);
                        builder.setTitle("Order Entries");
                        builder.setMessage(buffer.toString());
                        builder.show();
                    }
                }
            });
        }

}
//res=result