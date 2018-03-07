package secom.accestur.mcoupon.activity.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import secom.accestur.core.model.UserMCoupon;
import secom.accestur.mcoupon.activity.R;
import secom.accestur.mcoupon.activity.tasks.SignUpTask;

public class SignUpActivity extends AppCompatActivity {
    Button logIn;
    Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //logIn = (Button) findViewById(R.id.button_sign_in);
        findViewById(R.id.button_sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSignUp();
            }
        });

    }

    private void sendSignUp() {
        String userName = " ";
        String password = " ";
        boolean validUser = true;
        EditText editUser = (EditText) findViewById(R.id.sign_up_username_in);

        if (!editUser.getText().toString().equals("")) {
            userName = editUser.getText().toString();
            Log.v("Content", editUser.getText().toString());

        } else {
            validUser = false;
            Log.v("Error", "No username");
            Toast toast1 = Toast.makeText(getApplicationContext(), "Username is empty", Toast.LENGTH_LONG);
            toast1.show();
        }


        EditText editPassword = (EditText) findViewById(R.id.sign_up_password1_in);
        EditText editPassword2 = (EditText) findViewById(R.id.sign_up_password2_in);

        if (!editPassword.getText().toString().equals("") && !editPassword2.getText().toString().equals("")) {
            if (editPassword.getText().toString().equals(editPassword2.getText().toString())) {
                Log.v("Content", editPassword.getText().toString());
                if (editPassword.getText().toString().length() >= 6) {
                    password = editPassword.getText().toString();
                } else {
                    validUser = false;
                    Log.v("Error", "Passwords not long enough");
                    Toast toast5 = Toast.makeText(getApplicationContext(), "Password has to be at least 6 characters", Toast.LENGTH_LONG);
                    toast5.show();
                }

            } else {
                validUser = false;
                Log.v("Error", "Passwords don't match");
                Toast toast5 = Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_LONG);
                toast5.show();
            }
        } else {
            validUser = false;
            Log.v("Error", "No password");
            Toast toast6 = Toast.makeText(getApplicationContext(), "No password", Toast.LENGTH_LONG);
            toast6.show();
        }
        if (validUser) {
            //user = new UserMCoupon(userName, password);
            Log.v("Sign up", "Sending Sign Up");
            SignUpTask signUpTask = new SignUpTask(this);
//            int response = signUpQuery.doInBackground();
            Log.v("Sign up", "doInBackground Launched");
           signUpTask.execute(editUser.getText().toString(),editPassword.getText().toString());
        }

    }

}