package mei.weatherapp;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import mei.weatherapp.asynctasks.AddUserAsyncTask;
import mei.weatherapp.asynctasks.LoginAsyncTask;
import mei.weatherapp.basedados.MyOpenHelper;
import mei.weatherapp.contratos.User;

public class Login extends AppCompatActivity {


  TextView txtPassword2;
  TextView lblPassword2;
  TextView lblTitle;
  TextView txtUsername;
  TextView txtPassword;
  Button btnRegistar;
  Button btnLogin;
  Button btnLoggout;
  Button btnRegistarOk;
  Button btnRegistarCancelar;
  CheckBox chkManter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    txtPassword2 = (TextView) findViewById(R.id.txt_passord2);
    lblPassword2 = (TextView) findViewById(R.id.lbl_password2);
    lblTitle = (TextView) findViewById(R.id.lbl_title);
    btnRegistar = (Button) findViewById(R.id.btn_registar);
    btnLogin = (Button) findViewById(R.id.btn_login);
    btnLoggout = (Button) findViewById(R.id.btnLoggout);
    btnRegistarCancelar = (Button) findViewById(R.id.btn_registar_cancelar);
    btnRegistarOk = (Button) findViewById(R.id.btn_registar_ok);
    txtUsername = (TextView) findViewById(R.id.txt_username);
    txtPassword = (TextView) findViewById(R.id.txt_password);
    chkManter = (CheckBox) findViewById(R.id.chk_manter);


    btnRegistar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        txtPassword2.setVisibility(View.VISIBLE);
        lblPassword2.setVisibility(View.VISIBLE);
        btnLogin.setVisibility(View.GONE);
        btnLoggout.setVisibility(View.GONE);
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
        btnLoggout.setVisibility(View.VISIBLE);
        btnRegistarOk.setVisibility(View.GONE);
        btnRegistarCancelar.setVisibility(View.GONE);
        lblTitle.setText("Login");
      }
    });

    btnLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (txtUsername.getText().equals("")){
          Toast.makeText(Login.this, "Username obrigatório", Toast.LENGTH_SHORT).show();
        } else if (txtPassword.getText().toString().equals("")){
          Toast.makeText(Login.this, "Password obrigatória", Toast.LENGTH_SHORT).show();
        } else {
          LoginAsyncTask login = new LoginAsyncTask(Login.this, chkManter.isChecked());
          login.execute(txtUsername.getText().toString().toString() + txtPassword.getText().toString());
        }
      }
    });

    btnLoggout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        MyOpenHelper moh = new MyOpenHelper(Login.this);
        SQLiteDatabase db = moh.getWritableDatabase();
        moh.deleteFromUsers(db);
        Toast.makeText(Login.this, "Removido Login", Toast.LENGTH_LONG).show();
        finish();
      }
    });

    btnRegistarOk.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (txtUsername.getText().toString().equals("")){
          Toast.makeText(Login.this, "Username obrigatório", Toast.LENGTH_SHORT).show();
        } else if (txtPassword.getText().toString().equals("")){
          Toast.makeText(Login.this, "Password obrigatória", Toast.LENGTH_SHORT).show();
        } else if (txtPassword2.getText().toString().equals("")){
          Toast.makeText(Login.this, "Confirmação obrigatória", Toast.LENGTH_SHORT).show();
        } else if (!txtPassword.getText().toString().equals(txtPassword2.getText().toString())) {
          Toast.makeText(Login.this, "Passwords diferentes", Toast.LENGTH_SHORT).show();
        } else {
          AddUserAsyncTask add = new AddUserAsyncTask(Login.this, txtUsername.getText().toString(), txtPassword.getText().toString(), chkManter.isChecked());
          add.execute();
        }
      }
    });
  }
}
