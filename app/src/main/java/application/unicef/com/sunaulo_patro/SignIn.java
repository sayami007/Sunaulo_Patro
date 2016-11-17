package application.unicef.com.sunaulo_patro;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class SignIn extends Activity {
    TextView phone;
    Button save, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        phone = (TextView) findViewById(R.id.phone);
        save = (Button) findViewById(R.id.signIn);
        register = (Button) findViewById(R.id.signUp);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = String.valueOf(phone.getText());
                Log.v("NUM", number);
                if (number.equals("9841")) {
                    Intent intent = new Intent(SignIn.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else
                    Toast.makeText(SignIn.this, "Error in sign in", Toast.LENGTH_SHORT).show();

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Dialog dialog = new Dialog(SignIn.this);
                    dialog.setContentView(R.layout.register);
                    dialog.show();
                } catch (Exception err) {
                    Toast.makeText(SignIn.this, "Error" + err.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
