package application.unicef.com.sunaulo_patro;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Bibesh on 11/17/16.
 */

public class Spalsh extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        CountDownTimer time = new CountDownTimer(2000, 500) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(Spalsh.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        time.start();
    }
}
