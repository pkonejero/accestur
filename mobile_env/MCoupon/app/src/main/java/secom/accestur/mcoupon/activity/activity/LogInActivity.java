package secom.accestur.mcoupon.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import secom.accestur.core.model.UserMCoupon;
import secom.accestur.mcoupon.activity.R;
import secom.accestur.mcoupon.activity.tasks.LogInTask;


public class LogInActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        findViewById(R.id.button_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("sdf","No problem");
                sendLogIn();
            }
        });

        findViewById(R.id.button_go_to_sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });

    }

    private void sendLogIn () {
        String userName = " ";
        String password = " ";
        boolean validUser = true;

        EditText editUser = (EditText) findViewById(R.id.log_in_user);
        if(!editUser.getText().toString().equals("")) {
            userName = editUser.getText().toString();
            Log.v("Content", editUser.getText().toString());

        } else {
            validUser = false;
            Log.v("Error", "No username");
            Toast toast1 = Toast.makeText(getApplicationContext(), "Username is empty", Toast.LENGTH_LONG);
            toast1.show();
        }

        EditText editPassword = (EditText) findViewById(R.id.log_in_password_in);
        if(!editPassword.getText().toString().equals("")) {
            password = editPassword.getText().toString();
            Log.v("Content", editPassword.getText().toString());

        } else {
            validUser = false;
            Log.v("Error", "No password");
            Toast toast1 = Toast.makeText(getApplicationContext(), "Password is empty", Toast.LENGTH_LONG);
            toast1.show();
        }
        if(validUser) {
            UserMCoupon userLogin = new UserMCoupon(userName, password );
            Log.v("Sign up", "Sending Sign In");
            LogInTask logInTask = new LogInTask(this);
            logInTask.execute(editUser.getText().toString(),editPassword.getText().toString());
        }

    }
}
