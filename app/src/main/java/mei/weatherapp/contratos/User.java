package mei.weatherapp.contratos;

import java.io.Serializable;

public class User implements Serializable {
  private String userId;
  private String username;
  private int creditos;

  public User() {
  }

  public User(String userId, String username, int creditos) {
    this.userId = userId;
    this.username = username;
    this.creditos = creditos;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getCreditos() {
    return creditos;
  }

  public void setCreditos(int creditos) {
    this.creditos = creditos;
  }
}
