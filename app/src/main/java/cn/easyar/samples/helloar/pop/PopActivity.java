package cn.easyar.samples.helloar.pop;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import cn.easyar.samples.helloar.R;

public class PopActivity extends AppCompatActivity {
    private LetterDialog letterOpenView;

    private ImageView btnLetter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);
        btnLetter = findViewById(R.id.btn_bubble);

        letterOpenView = new LetterDialog(this);
        //  letterOpenView.addInitialPoint(btnLetter);
        btnLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                letterOpenView.show();
            }
        });
    }
}
