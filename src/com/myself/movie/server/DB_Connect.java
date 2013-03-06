/**
 * 
 */
package com.myself.movie.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author jasonhotsauce
 *
 */
public abstract class DB_Connect {
  private final String mysqlURL = "jdbc:mysql://localhost:3306/";
  private final String username;
  private final String password;
  private final String dbName = "movie_recommandation";
  private final String dbDriver = "com.mysql.jdbc.Driver";
  
  public DB_Connect() {
    this.username = "root";
    this.password = "82277741";
  }
  
  protected Connection getConn() {
    Connection conn = null;
    String url = this.mysqlURL + this.dbName;
    try {
      Class.forName(dbDriver).newInstance();
      conn = DriverManager.getConnection(url, username, password);
      
    } catch (Exception e) {
      // Handle connection error,
      System.err.println("MySQL connection error:");
      
      // for debug
      e.printStackTrace();
    }
    
    if (conn == null)
      System.err.println("Cannot get connection");
    
    return conn;
  }
  
  /**
   * Get the resultSet size independently. This method can be used in any subclass and doesn't need to
   * be instantiated.
   * 
   * @param resultSet
   * @return int size
   */
  protected static int getResultSetSize(ResultSet resultSet) {
    int size = -1;

    try {
      resultSet.last();
      size = resultSet.getRow();
      resultSet.beforeFirst();
    } catch(SQLException e) {
      return size;
    }

    return size;
  }
}
