package com.example.eman.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whipperCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhipperCream = whipperCream.isChecked();
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolate.isChecked();
        EditText name = (EditText) findViewById(R.id.name_edit_text);
        String hasName = name.getText().toString();
        int price = calculatePrice(hasWhipperCream, hasChocolate);
        String message = createOrderSummery(price, hasWhipperCream, hasChocolate, hasName);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order Cup Of Coffee");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    private int calculatePrice(boolean addWhipperCream, boolean addChocolate) {
        int price;
        if (addChocolate && addWhipperCream) {
            price = (5 + 2 + 1) * quantity;
        } else if (addChocolate) {
            price = (5 + 2) * quantity;
        } else if (addWhipperCream) {
            price = (5 + 1) * quantity;
        } else {
            price = (5) * quantity;

        }
        return price;
    }


    private String createOrderSummery(int price, boolean addWhipperCream, boolean addChocolate, String hasName) {
        String message = "Name =" + hasName;
        message = message + "\n Quantity = " + quantity;
        message = message + "\n Add Whipped Cream ? " + addWhipperCream;
        message = message + "\n Add Chocolate ?" + addChocolate;
        message = message + "\n ToTal = $ " + price;
        message = message + "\n Thank You!";
        return message;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);

    }

    /**
     * This method displays the given price on the screen.
     */

    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.ORDER_SUMMARY_text_view);
        priceTextView.setText(message);

    }

    public void increment(View view) {
        if (quantity==100)
        {
            Toast.makeText(this,"You Can't have more than 100 cup",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity==1) {
            Toast.makeText(this, "You can not have less than 1 cup", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }
}
