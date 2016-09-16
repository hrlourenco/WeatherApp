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
    public static final String _NOME_TABELA_ = "praias";
    public static final String _ID_ = "_id";
    public static final String _NOME_ = "nome";
    public static final String _LATITUDE_ = "latitude";
    public static final String _LONGITUDE_ = "longitude";
    public static final String _FAVORITA_ = "favorita";
    public static final String _MORADA_ = "morada";
    public static final String _LOCATION_KEY_ = "locationKey";
    public static final String _HUMIDADE_ = "humidade";
    public static final String _VENTO_ = "vento";
    public static final String _RAJADAS_ = "rajadas";
    public static final String _UV_ = "uv";
    public static final String _NUVENS_ = "nuvens";
    public static final String _PRESSAO_ = "pressao";
    public static final String _PRECIPITACAO_ = "precipitacao";
    public static final String _WEATHERTEXT_ = "text";
    public static final String _TEMP_ = "temperatura";
    public static final String _ICON_ = "icon";

    public MyOpenHelper(Context context) {
        super(context, NAME, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CreateDbSql = "CREATE TABLE " + _NOME_TABELA_ + " ( " +
          _ID_ + " INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, " +
          _NOME_ + " TEXT  NOT NULL, " +
          _LOCATION_KEY_ + " TEXT  NOT NULL, " +
          _LONGITUDE_ + " TEXT  NOT NULL, " +
          _LATITUDE_ + " TEXT  NOT NULL, " +
          _FAVORITA_ + " INTEGER DEFAULT '0' NOT NULL, " +
          _MORADA_ + " TEXT  NOT NULL, " +
          _HUMIDADE_ + " TEXT  NOT NULL, " +
          _VENTO_ + " TEXT  NOT NULL, " +
          _RAJADAS_ + " TEXT  NOT NULL, " +
          _UV_ + " TEXT  NOT NULL, " +
          _NUVENS_ + " TEXT  NOT NULL, " +
          _PRESSAO_ + " TEXT  NOT NULL, " +
          _PRECIPITACAO_ + " TEXT  NOT NULL, " +
          _WEATHERTEXT_ + " TEXT  NOT NULL, " +
          _TEMP_ + " TEXT  NOT NULL, " +
          _ICON_ + " TEXT  NOT NULL " +
          ")";

        db.execSQL(CreateDbSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sqlString = "DROP TABLE IF EXISTS " + _NOME_TABELA_;
        db.execSQL(sqlString);
        onCreate(db);
    }

    public void insertIntoPaias (SQLiteDatabase db, Praia p){
       ContentValues cv = new ContentValues();

        cv.put(_NOME_, p.getNome());
        cv.put(_LOCATION_KEY_, p.getLocationKey());
        cv.put(_LONGITUDE_, p.getLongitude());
        cv.put(_LATITUDE_, p.getLatitude());
        cv.put(_FAVORITA_, 1);
        cv.put(_MORADA_, p.getMorada());
        cv.put(_HUMIDADE_, p.getCondicoesActuais().getRelativeHumidity());
        cv.put(_VENTO_, p.getCondicoesActuais().getWindSpeed());
        cv.put(_RAJADAS_, p.getCondicoesActuais().getWindGust());
        cv.put(_UV_, p.getCondicoesActuais().getUVIndex());
        cv.put(_NUVENS_, p.getCondicoesActuais().getCloudCover());
        cv.put(_PRESSAO_, p.getCondicoesActuais().getPressure());
        cv.put(_PRECIPITACAO_, p.getCondicoesActuais().getPrecipitationSummary());
        cv.put(_WEATHERTEXT_, p.getCondicoesActuais().getWeatherText());
        cv.put(_TEMP_, p.getCondicoesActuais().getTemperature());
        cv.put(_ICON_, p.getCondicoesActuais().getWeatherIcon());


        db.insert(_NOME_TABELA_, null, cv);
    }


    public void deleteFromPraias(SQLiteDatabase db){
      String sqlString = "DELETE FROM " + _NOME_TABELA_;
      db.execSQL(sqlString);
    }

    public Praia getFromPraias(SQLiteDatabase db){
      Praia p = new Praia();
      Condicoes cond = new Condicoes();

      String sqlString = "SELECT * FROM "+ _NOME_TABELA_;

      Cursor cur = db.rawQuery(sqlString, null);

      try {
        cur.moveToFirst();
        cond.setRelativeHumidity(cur.getString(cur.getColumnIndex(_HUMIDADE_)));
        cond.setWindSpeed(cur.getString(cur.getColumnIndex(_VENTO_)));
        cond.setWindGust(cur.getString(cur.getColumnIndex(_RAJADAS_)));
        cond.setUVIndex(cur.getString(cur.getColumnIndex(_UV_)));
        cond.setCloudCover(cur.getString(cur.getColumnIndex(_NUVENS_)));
        cond.setPressure(cur.getString(cur.getColumnIndex(_PRESSAO_)));
        cond.setPrecipitationSummary(cur.getString(cur.getColumnIndex(_PRECIPITACAO_)));
        cond.setWeatherText(cur.getString(cur.getColumnIndex(_WEATHERTEXT_)));
        cond.setTemperature(cur.getString(cur.getColumnIndex(_TEMP_)));
        int i = Integer.parseInt(cur.getString(cur.getColumnIndex(_ICON_)));
        cond.setWeatherIcon(i);

        p.setCondicoesActuais(cond);
        p.setNome(cur.getString(cur.getColumnIndex(_NOME_)));
        p.setLocationKey(cur.getString(cur.getColumnIndex(_LOCATION_KEY_)));
        p.setLongitude(cur.getString(cur.getColumnIndex(_LONGITUDE_)));
        p.setLatitude(cur.getString(cur.getColumnIndex(_LATITUDE_)));
        p.setFavorita(cur.getInt(cur.getColumnIndex(_FAVORITA_)));
        p.setMorada(cur.getString(cur.getColumnIndex(_MORADA_)));
      }catch (Exception e){


      }





      return p;
    }
}
