package com.example.uriya_hofesh_2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    Button btn,btn2;
    Random random;
    EditText et;
    ArrayList<String> inspirationalSentences = new ArrayList<>();
    SharedPreferences sharedPreferences;
    static final String PREF_NAME = "MyAppPrefs";
    static final String KEY_LAST_SENTENCE = "lastSentence";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        inspirationalSentences.add("Believe in yourself and all that you are.");
        inspirationalSentences.add("Success is not final, failure is not fatal: It is the courage to continue that counts.");
        inspirationalSentences.add("Don't watch the clock; do what it does. Keep going.");
        inspirationalSentences.add("Hardships often prepare ordinary people for an extraordinary destiny.");
        inspirationalSentences.add("Start where you are. Use what you have. Do what you can.");
        random = new Random();
        et = findViewById(R.id.editText);
        tv = findViewById(R.id.textView);
        btn = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String lastSentence = sharedPreferences.getString(KEY_LAST_SENTENCE, null);
        if (lastSentence != null) {
            tv.setText(lastSentence);
            if (!inspirationalSentences.contains(lastSentence)) {
                inspirationalSentences.add(lastSentence);
            }
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inspirationalSentences.isEmpty()) {
                    Toast.makeText(MainActivity.this, "No sentences available!", Toast.LENGTH_SHORT).show();
                    return;
                }
                int index = random.nextInt(inspirationalSentences.size());
                String randomQuote = inspirationalSentences.get(index);
                tv.setText(randomQuote);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_LAST_SENTENCE, randomQuote);
                editor.apply();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputText = et.getText().toString().trim();
                if (inputText.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter some text!", Toast.LENGTH_SHORT).show();
                } else {
                    inspirationalSentences.add(inputText);
                    et.setText(""); // Clear the EditText
                    Toast.makeText(MainActivity.this, "Text added!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}