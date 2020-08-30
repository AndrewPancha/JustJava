package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final int PRICE_FOR_ONE_CUP = 5;
    private final int PRICE_FOR_WHIPPED_CREAM = 1;
    private final int PRICE_FOR_CHOCOLATE = 2;
    private int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void decrement(View view) {
        quantity--;
        if (quantity < 0) {
            return;
        }
        displayQuantity(quantity);
    }

    public void increment(View view) {
        quantity++;
        if (quantity > 100) {
            return;
        }
        displayQuantity(quantity);
    }

    private void displayQuantity(int i) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(i);
    }


    private int getOrderPrice(boolean hasChocolate, boolean hasCream) {
        int price = quantity * PRICE_FOR_ONE_CUP;
        if (hasChocolate) price += PRICE_FOR_CHOCOLATE;
        if (hasCream) price += PRICE_FOR_WHIPPED_CREAM;

        return price;
    }


    public void submitOrder(View view) {
        TextView quantityTextView = (TextView) findViewById(R.id.counter_textView);
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);

        boolean hasChocolate = chocolateCheckBox.isChecked();
        boolean hasCream = whippedCreamCheckBox.isChecked();

        String name = nameEditText.toString();
        int price = getOrderPrice(hasChocolate, hasCream);

        String message = createMessage(name, hasCream, hasChocolate, price);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,
                getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, message);
    }

    public String createMessage(String name, boolean hasCream, boolean hasChocolate, int price) {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, hasCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, hasChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price, price);
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

}
