package mei.weatherapp.basedados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.Principal;
import java.security.PublicKey;
import java.util.Date;

import mei.weatherapp.contratos.Condicoes;
import mei.weatherapp.contratos.Praia;
import mei.weatherapp.contratos.User;

//Estruturas
public class MyOpenHelper extends SQLiteOpenHelper {
  private static final String NAME = "praias.db";
  private static final int VERSAO = 1;

  //DADOS DA CRIAÇÃO DA TABELA PRAIAS
  private static final String _PRAIAS_NOME_TABELA_ = "praias";
  private static final String _PRAIAS_ID_ = "_id";
  private static final String _PRAIAS_PRAIA_ID_ = "praiaId";
  private static final String _PRAIAS_NOME_ = "nome";
  private static final String _PRAIAS_LATITUDE_ = "latitude";
  private static final String _PRAIAS_LONGITUDE_ = "longitude";
  private static final String _PRAIAS_RATE_ = "rate";
  private static final String _PRAIAS_TEMPERATURA_ = "temperatura";
  private static final String _PRAIAS_DATA_TEMPO_ = "dataTempo";

  //DADOS PARA CRIAÇÃO DA TABELA USERS
  private static final String _USERS_NOME_TABELA_ = "users";
  private static final String _USERS_ID_ = "_id";
  private static final String _USERS_USER_ID_ = "userId";
  private static final String _USERS_USERNAME_ = "username";
  private static final String _USERS_CREDITOS_ = "creditos";


  public MyOpenHelper(Context context) {
      super(context, NAME, null, VERSAO);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {

    String CreatePraisDbSql = "CREATE TABLE " + _PRAIAS_NOME_TABELA_ + " ( " +
      _PRAIAS_ID_ + " INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, " +
      _PRAIAS_PRAIA_ID_ + " TEXT  NOT NULL, " +
      _PRAIAS_NOME_ + " TEXT  NOT NULL, " +
      _PRAIAS_LONGITUDE_ + " DOUBLE  NOT NULL, " +
      _PRAIAS_LATITUDE_ + " DOUBLE  NOT NULL, " +
      _PRAIAS_RATE_ + " INTEGER DEFAULT '0' NOT NULL, " +
      _PRAIAS_TEMPERATURA_ + " DOUBLE DEFAULT '0' NOT NULL, " +
      _PRAIAS_DATA_TEMPO_ + " LONG NOT NULL " +
      ")";

    db.execSQL(CreatePraisDbSql);

    String CreateUsersDbSql = "CREATE TABLE " + _USERS_NOME_TABELA_ + " ( " +
      _USERS_ID_ + " INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, " +
      _USERS_USER_ID_ + " TEXT  NOT NULL, " +
      _USERS_USERNAME_ + " TEXT  NOT NULL, " +
      _USERS_CREDITOS_ + " INTEGER  NOT NULL " +
      ")";

    db.execSQL(CreateUsersDbSql);

    //TODO: Comentar este código antes de entregar
/*    String sqlInsertPraia = "INSERT INTO " + _PRAIAS_NOME_TABELA_ + "( "+ _PRAIAS_PRAIA_ID_ + "," + _PRAIAS_NOME_ + "," + _PRAIAS_LONGITUDE_ + "," +
      _PRAIAS_LATITUDE_ + "," + _PRAIAS_RATE_ + "," + _PRAIAS_TEMPERATURA_ + ") VALUES ('5835c5eb5bd8bf001095c17f', 'Praia da Luz', 37.0972979, " +
      "-8.778702, 3, 30, " + System.nanoTime() + ")";

    db.execSQL(sqlInsertPraia);
*/
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

    cvPraias.put(_PRAIAS_PRAIA_ID_, p.getPraiaId());
    cvPraias.put(_PRAIAS_NOME_, p.getNome());
    cvPraias.put(_PRAIAS_LONGITUDE_, p.getLongitude());
    cvPraias.put(_PRAIAS_LATITUDE_, p.getLatitude());
    cvPraias.put(_PRAIAS_RATE_, p.getRate());
    cvPraias.put(_PRAIAS_TEMPERATURA_, p.getTemperatura());


    db.insert(_PRAIAS_NOME_TABELA_, null, cvPraias);
  }

  public void insertIntoUsers (SQLiteDatabase db, User u){
    ContentValues cvUsers = new ContentValues();

    cvUsers.put(_USERS_USER_ID_, u.getUserId());
    cvUsers.put(_USERS_USERNAME_, u.getUsername());
    cvUsers.put(_USERS_CREDITOS_, u.getCreditos());

    db.insert(_USERS_NOME_TABELA_, null, cvUsers);
  }


  public void deleteFromPraias(SQLiteDatabase db){
    String sqlString = "DELETE FROM " + _PRAIAS_NOME_TABELA_;
    db.execSQL(sqlString);
  }

  public void deleteFromUsers(SQLiteDatabase db){
    String sqlString = "DELETE FROM " + _USERS_NOME_TABELA_;
    db.execSQL(sqlString);
  }

  public Praia getFromPraias(SQLiteDatabase db, String id){
    Praia p = new Praia();

    String sqlString = "SELECT * FROM "+ _PRAIAS_NOME_TABELA_ + " WHERE " + _PRAIAS_PRAIA_ID_ + " = '" + id + "'";

    try (Cursor cur = db.rawQuery(sqlString, null)) {
      cur.moveToFirst();
      p.setPraiaId(cur.getString(cur.getColumnIndex(_PRAIAS_PRAIA_ID_)));
      p.setNome(cur.getString(cur.getColumnIndex(_PRAIAS_NOME_)));
      p.setLongitude(cur.getDouble(cur.getColumnIndex(_PRAIAS_LONGITUDE_)));
      p.setLatitude(cur.getDouble(cur.getColumnIndex(_PRAIAS_LATITUDE_)));
      p.setRate(cur.getDouble(cur.getColumnIndex(_PRAIAS_RATE_)));
      p.setTemperatura(cur.getDouble(cur.getColumnIndex(_PRAIAS_TEMPERATURA_)));
      p.setDataTempo(cur.getLong(cur.getColumnIndex(_PRAIAS_TEMPERATURA_)));
    } catch (Exception e) {
      e.printStackTrace();
    }

    return p;
  }

  public User getFromUsers (SQLiteDatabase db){
    User u = new User();

    String sqlString = "SELECT * FROM " + _USERS_NOME_TABELA_;

    try (Cursor cur = db.rawQuery(sqlString, null)) {
      cur.moveToFirst();
      u.setUsername(cur.getString(cur.getColumnIndex(_USERS_USERNAME_)));
      u.setUserId(cur.getString(cur.getColumnIndex(_USERS_USER_ID_)));
      u.setCreditos(cur.getInt(cur.getColumnIndex(_USERS_CREDITOS_)));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return u;
  }
}
