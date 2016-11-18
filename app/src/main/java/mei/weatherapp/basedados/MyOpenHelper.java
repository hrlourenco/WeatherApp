package mei.weatherapp.basedados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.Principal;
import java.security.PublicKey;

import mei.weatherapp.contratos.Condicoes;
import mei.weatherapp.contratos.Praia;

//Estruturas
public class MyOpenHelper extends SQLiteOpenHelper {
  public static final String NAME = "praias.db";
  public static final int VERSAO = 1;

  //DADOS DA CRIAÇÃO DA TABELA PRAIAS
  public static final String _PRAIAS_NOME_TABELA_ = "praias";
  public static final String _PRAIAS_ID_ = "_id";
  public static final String _PRAIAS_PRAIA_ID_ = "praiaId";
  public static final String _PRAIAS_NOME_ = "nome";
  public static final String _PRAIAS_LATITUDE_ = "latitude";
  public static final String _PRAIAS_LONGITUDE_ = "longitude";
  public static final String _PRAIAS_RATE_ = "rate";
  public static final String _PRAIAS_TEMPERATURA_ = "temperatura";

  //DADOS PARA CRIAÇÃO DA TABELA USERS
  public static final String _USERS_NOME_TABELA_ = "users";
  public static final String _USERS_ID_ = "_id";
  public static final String _USERS_USER_ID_ = "userId";
  public static final String _USERS_USERNAME_ = "username";
  public static final String _USERS_CREDITOS_ = "creditos";


  public MyOpenHelper(Context context) {
      super(context, NAME, null, VERSAO);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {

    String CreatePraisDbSql = "CREATE TABLE " + _PRAIAS_NOME_TABELA_ + " ( " +
      _PRAIAS_ID_ + " INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, " +
      _PRAIAS_PRAIA_ID_ + " TEXT  NOT NULL, " +
      _PRAIAS_NOME_ + " TEXT  NOT NULL, " +
      _PRAIAS_LONGITUDE_ + " FLOAT  NOT NULL, " +
      _PRAIAS_LATITUDE_ + " TEXT  NOT NULL, " +
      _PRAIAS_RATE_ + " INTEGER DEFAULT '0' NOT NULL, " +
      _PRAIAS_TEMPERATURA_ + " FLOAT DEFAULT '0' NOT NULL, " +
      ")";

    db.execSQL(CreatePraisDbSql);

    String CreateUsersDbSql = "CREATE TABLE " + _USERS_NOME_TABELA_ + " ( " +
      _USERS_ID_ + " INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, " +
      _USERS_USER_ID_ + " TEXT  NOT NULL, " +
      _USERS_USERNAME_ + " TEXT  NOT NULL, " +
      _USERS_CREDITOS_ + " FLOAT  NOT NULL, " +
      ")";

    db.execSQL(CreateUsersDbSql);

  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    String sqlPraiasString = "DROP TABLE IF EXISTS " + _PRAIAS_NOME_TABELA_;
    db.execSQL(sqlPraiasString);

    String sqlUsersString = "DROP TABLE IF EXISTS " + _USERS_NOME_TABELA_;
    db.execSQL(sqlUsersString);
    onCreate(db);
  }

  public void insertIntoPaias (SQLiteDatabase db, Praia p){
    ContentValues cvPraias = new ContentValues();

    cvPraias.put(_PRAIAS_ID_, p.getPraiaId());
    cvPraias.put(_PRAIAS_NOME_, p.getNome());
    cvPraias.put(_PRAIAS_LONGITUDE_, p.getLongitude());
    cvPraias.put(_PRAIAS_LATITUDE_, p.getLatitude());
    cvPraias.put(_PRAIAS_RATE_, p.getRate());
    cvPraias.put(_PRAIAS_TEMPERATURA_, p.getTemperatura());


    db.insert(_PRAIAS_NOME_TABELA_, null, cvPraias);
  }


  public void deleteFromPraias(SQLiteDatabase db){
    String sqlString = "DELETE FROM " + _PRAIAS_NOME_TABELA_;
    db.execSQL(sqlString);
  }

  public Praia getFromPraias(SQLiteDatabase db){
    Praia p = new Praia();

    String sqlString = "SELECT * FROM "+ _PRAIAS_NOME_TABELA_;

    Cursor cur = db.rawQuery(sqlString, null);

    try {
      cur.moveToFirst();
      p.setPraiaId(cur.getString(cur.getColumnIndex(_PRAIAS_PRAIA_ID_)));
      p.setNome(cur.getString(cur.getColumnIndex(_PRAIAS_NOME_)));
      p.setLongitude(cur.getFloat(cur.getColumnIndex(_PRAIAS_LONGITUDE_)));
      p.setLatitude(cur.getFloat(cur.getColumnIndex(_PRAIAS_LATITUDE_)));
      p.setRate(cur.getInt(cur.getColumnIndex(_PRAIAS_RATE_)));
      p.setTemperatura(cur.getFloat(cur.getColumnIndex(_PRAIAS_TEMPERATURA_)));
    }catch (Exception e){

    }
    return p;
  }
}
