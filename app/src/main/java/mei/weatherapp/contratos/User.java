package mei.weatherapp.contratos;

/**
 * Created by joaofaria on 17/11/16.
 */
public class User {
  private int id;
  private String userId;
  private String username;

  public User(String userId, String username) {
    this.userId = userId;
    this.username = username;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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
}
