package design.cyoung;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

public class SaveCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_card);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
    }

    public void saveCard(View view) {
        String barcodeText = getIntent().getStringExtra("BarcodeText");
        Gson gson = new Gson();
        BarcodeFormat barcodeFormat = gson.fromJson(getIntent().getStringExtra("BarcodeFormat"), BarcodeFormat.class);

        EditText title = findViewById(R.id.edit_card_name);
        EditText description = findViewById(R.id.edit_card_description);


        //Save scanned card to list
        SharedPreferences mPrefs = getApplicationContext().getSharedPreferences("CardStore", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();

        ArrayList<Card> cards = gson.fromJson(mPrefs.getString("CardList", gson.toJson(new ArrayList<Card>())), new TypeToken<List<Card>>(){}.getType());

        cards.add(new Card(title.getText().toString(), description.getText().toString(), barcodeText, barcodeFormat));

        prefsEditor.putString("CardList", gson.toJson(cards));
        prefsEditor.apply();
        finish();
    }
}
