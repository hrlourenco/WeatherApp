package mei.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import mei.weatherapp.asynctasks.LoginAsyncTask;
import mei.weatherapp.contratos.User;

public class Login extends AppCompatActivity {


  TextView txtPassword2;
  TextView lblPassword2;
  TextView lblTitle;
  TextView txtUsername;
  TextView txtPassword;
  TextView txtUserId;
  Button btnRegistar;
  Button btnLogin;
  Button btnRegistarOk;
  Button btnRegistarCancelar;
  User user = new User();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    txtPassword2 = (TextView) findViewById(R.id.txt_passord2);
    lblPassword2 = (TextView) findViewById(R.id.lbl_password2);
    lblTitle = (TextView) findViewById(R.id.lbl_title);
    btnRegistar = (Button) findViewById(R.id.btn_registar);
    btnLogin = (Button) findViewById(R.id.btn_login);
    btnRegistarCancelar = (Button) findViewById(R.id.btn_registar_cancelar);
    btnRegistarOk = (Button) findViewById(R.id.btn_registar_ok);
    txtUsername = (TextView) findViewById(R.id.txt_username);
    txtPassword = (TextView) findViewById(R.id.txt_password);
    txtUserId = (TextView) findViewById(R.id.txt_userID);


    btnRegistar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        txtPassword2.setVisibility(View.VISIBLE);
        lblPassword2.setVisibility(View.VISIBLE);
        btnLogin.setVisibility(View.GONE);
        btnRegistar.setVisibility(View.GONE);
        btnRegistarOk.setVisibility(View.VISIBLE);
        btnRegistarCancelar.setVisibility(View.VISIBLE);
        lblTitle.setText("Novo utilizador");
      }
    });

    btnRegistarCancelar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        txtPassword2.setVisibility(View.GONE);
        lblPassword2.setVisibility(View.GONE);
        btnLogin.setVisibility(View.VISIBLE);
        btnRegistar.setVisibility(View.VISIBLE);
        btnRegistarOk.setVisibility(View.GONE);
        btnRegistarCancelar.setVisibility(View.GONE);
        lblTitle.setText("Login");
      }
    });

    btnLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        LoginAsyncTask login = new LoginAsyncTask(Login.this);
        login.execute(txtUsername.getText().toString() + txtPassword.getText().toString());
      }
    });
  }
}
