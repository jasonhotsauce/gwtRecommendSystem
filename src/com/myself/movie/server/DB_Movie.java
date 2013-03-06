/**
 * 
 */
package com.myself.movie.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.myself.movie.client.Movie;
import com.myself.movie.client.User;

/**
 * @author jasonhotsauce
 *
 */
public class DB_Movie extends DB_Connect {

  public DB_Movie() {
    // Do nothing
  }
  
  public Movie[] getMovies() throws SQLException {
    String query = "SELECT * FROM movie";
    Connection conn = null;
    Statement select = null;
    ResultSet result = null;
    Movie[] movies = null;
    try {
      conn = this.getConn();
      select = conn.createStatement();
      result = select.executeQuery(query);
      
      int size = DB_Movie.getResultSetSize(result);
      movies = new Movie[size];
      int i = 0;
      
      while (result.next()) {
        movies[i] = new Movie();
        movies[i].setId(result.getInt("id"));
        movies[i].setName(result.getString("name"));
        i++;
      }
    } catch (Exception e) {
      System.err.println("Mysql Statement Error: " + query);
      e.printStackTrace();
    } finally {
      select.close();
      conn.close();
    }
    
    return movies;
  }
  
  public Movie[] getWatchedMovies(User user) throws SQLException {
    String query = "SELECT movie.* FROM movie LEFT JOIN user_has_movie ON movie.id = user_has_movie.movie_id WHERE user_has_movie.user_id = "+user.getUserId();
    Connection conn = null;
    Statement select = null;
    ResultSet result = null;
    Movie[] movies = null;
    try {
      conn = this.getConn();
      select = conn.createStatement();
      result = select.executeQuery(query);
      
      int size = DB_Movie.getResultSetSize(result);
      movies = new Movie[size];
      int i = 0;
      
      while (result.next()) {
        movies[i] = new Movie();
        movies[i].setId(result.getInt("id"));
        movies[i].setName(result.getString("name"));
        i++;
      }
    } catch (Exception e) {
      System.err.println("Mysql Statement Error: " + query);
      e.printStackTrace();
    } finally {
      select.close();
      conn.close();
    }
    
    return movies;
  }
}
