package mei.weatherapp.contratos;

//Estruturas
public class Estrutura {
    public static final String NAME = "praias.db";
    public static final int VERSAO = 1;

    public static class TabelaPraias {
        //DADOS DA CRIAÇÃO DA TABELA PRAIAS
        public static final String _NOME_TABELA_ = "praias";
        public static final String _ID_ = "_id";
        public static final String _NOME_ = "nome";
        public static final String _LATITUDE_ = "latitude";
        public static final String _LONGITUDE_ = "longitude";
        public static final String _FAVORITA_ = "favorita";
        public static final String _MORADA_ = "morada";

        public static final String CreateDbSql = "CREATE TABLE " + _NOME_TABELA_ + " ( " +
                _ID_ + " INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                _NOME_ + " TEXT  NOT NULL, " +
                _LONGITUDE_ + " TEXT  NOT NULL, " +
                _LATITUDE_ + " TEXT  NOT NULL, " +
                _FAVORITA_ + " INTEGER DEFAULT '0' NOT NULL" +
                _MORADA_ + " TEXT  NOT NULL, " +
                ")";

        public static final String DropDbSql = "DROP TABLE IF EXISTS " + _NOME_TABELA_;
    }
}