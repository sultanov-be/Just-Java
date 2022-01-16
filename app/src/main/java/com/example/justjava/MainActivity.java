package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int number = 2;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitOrder(View view) {
        EditText textField = (EditText) findViewById(R.id.edit_name);
        String name = textField.getText().toString();

        CheckBox whippedCreamCheck = (CheckBox) findViewById(R.id.whipped_cream_check);
        boolean hasWhippedCream = whippedCreamCheck.isChecked();
        Log.v("MainActivity", "Has WhippedCream " + hasWhippedCream);

        CheckBox chocolateCheck = (CheckBox) findViewById(R.id.chocolate_check);
        boolean hasChocolate = whippedCreamCheck.isChecked();
        Log.v("MainActivity", "Has Chocolate " + hasWhippedCream);
        createOrderSummary(name, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "sultanov_b@auca.kg" });
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private int calculatePrice(boolean choco, boolean whippedCream) {
        int price = number * 5;
        if (choco) price += number * 2;
        if (whippedCream) price += number * 1;
        return price;
    }

    private String createOrderSummary(String name, boolean addWhippedCream, boolean addChocolate) {
        message = name;
        message += "\nAdd Whipped cream? " + addWhippedCream;
        message += "\nAdd Chocolate topping? " + addChocolate;
        message += "\nQuantity: " + number;
        message += "\nPrice $" + calculatePrice(addChocolate, addWhippedCream);
        message += "\nThank you!";

        return message;
    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    public void increment(View view) {
        if(number == 100) {
            Toast.makeText(this, "You cannot have more than 100 cups of coffees", Toast.LENGTH_LONG).show();
            return;
        }
        number++;
        message = "Price $" + (number * 5);
        display(number);
    }

    public void decrement(View view) {
        if(number == 1) {
            Toast.makeText(this, "You cannot have less than 1 cup of coffees", Toast.LENGTH_LONG).show();
            return;
        }
        number--;
        message = "Price $" + number * 5;
        display(number);
    }
}