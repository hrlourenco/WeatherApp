package mei.weatherapp.webservice;
  import android.net.Uri;

  import java.io.BufferedReader;
  import java.io.DataOutputStream;
  import java.io.IOException;
  import java.io.InputStream;
  import java.io.InputStreamReader;
  import java.net.HttpURLConnection;
  import java.net.MalformedURLException;
  import java.net.URL;
  import java.util.Map;

/**
 * Created by nunooliveira on 20/10/15.
 *
 * Classe para criar ligacao a um servico REST generico.
 * Tem metodo para obter resposta do servico via chamada GET
 */
public abstract class WebserviceConnector {

  HttpURLConnection connection;
  URL webservice_call;

  //constroi um URL de chamada a um serviço.
  //usa Uri.Builder (como StringBuilder) para construir um URL com parametros e etc.
  //usa o contrutor new URL (que no fundo recebe uma string e transforma num URL)

  protected void buildWebserviceCall(String endpoint, Map<String, String> params){
    Uri.Builder builtUri = Uri.parse(endpoint).buildUpon();
    if(params != null) {
      for (String key : params.keySet()) {
        String value = params.get(key);
        builtUri.appendQueryParameter(key, value);
      }
    }
    try{
      this.webservice_call = new URL(builtUri.build().toString());
    }
    catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }

  //constroi URL com base numa string
  protected void buildWebserviceCallURLString(String url){
    try{
      this.webservice_call = new URL(url);
    }
    catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }


  //efetua a ligação ao serviço/site
  //usa HttpURLConnection para definir uma ligação a um website
  //define qual o tipo de qequest a usar: GET no caso
  private void connect(String method) throws IOException {
    this.connection = (HttpURLConnection) this.webservice_call.openConnection();
    this.connection.setRequestMethod(method);
    if(method.equals("GET")) {
      this.connection.connect();
    }
  }

  //processa a resposta
  //faz a ligação ao site/serviço e obtem a resposta
  // getResponseCode -> devolve o codigo da resposta (200 = OK)
  // getInputStream -> devolve o "texto" da resposta
  // no final, desligamos a ligação
  protected String getWebserviceResponse(){
    String result = null;
    try{
      this.connect("GET"); //faz a ligacao ao servico
      int rc = this.connection.getResponseCode();
      if( rc == HttpURLConnection.HTTP_OK){
        //le o resultado da resposta do servico
        InputStream inputStream = this.connection.getInputStream();
        result = StreamToString(inputStream);
      }
    }
    catch (IOException e1) {
      e1.printStackTrace();
    }
    finally {
      if(this.connection!=null){
        this.connection.disconnect();
      }

    }

    return result;
  }


  protected String postToWebservice(Map<String, String> parameters) {

    String result = "";
    DataOutputStream wr = null;
    Uri.Builder input_builder = new Uri.Builder();
    String input_data = "";

    if(parameters != null) {
      for(String key : parameters.keySet()) {
        String value = parameters.get(key);
        input_builder.appendQueryParameter(key,value);
      }
      input_data = input_builder.toString().substring(1);
    }

    try {
      this.connect("POST");
      //to force a post
      this.connection.setDoOutput(true);
      //to avoid buffering data before sending data
      this.connection.setChunkedStreamingMode(0);

      wr = new DataOutputStream(this.connection.getOutputStream());
      wr.writeBytes(input_data);
      wr.flush();
      wr.close();

      //int rc = this.connection.getResponseCode();
      //le o resultado da resposta do servico
      InputStream inputStream = this.connection.getInputStream();
      result = StreamToString(inputStream);

      BufferedReader in = new BufferedReader(
        new InputStreamReader(this.connection.getInputStream()));

    } catch (IOException e) {
      e.printStackTrace();
    }
    finally {
      if(wr!=null) {
        try {
          wr.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    return result;

  }


  private String StreamToString(InputStream inputStream) {
    String result = "";
    BufferedReader reader = null;
    try {

      StringBuffer buffer = new StringBuffer();

      if (inputStream != null) {
        reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
          buffer.append(line).append("\n");
        }

        result = buffer.toString();
        reader.close();
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    finally {
      if(reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    return result;
  }




}



