/**
 * 
 */
package com.myself.movie.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.myself.movie.client.Movie;
import com.myself.movie.client.User;

/**
 * @author jasonhotsauce
 *
 */
public class DB_User extends DB_Connect {
  
  public DB_User() {
    // Bypass, do nothing
  }
  
  public User getUserForId(int id) throws SQLException {
    String query = "SELECT * FROM user WHERE id ="+id;
    User user = null;
    Connection conn = null;
    Statement select = null;
    ResultSet result = null;
    try {
      conn = this.getConn();
      select = conn.createStatement();
      result = select.executeQuery(query);
      user = new User();
      user.setUserId(id);
      user.setFirstName(result.getString("first_name"));
      user.setLastName(result.getString("last_name"));
    } catch (Exception e) {
      System.err.println("Mysql Statement Error: " + query);
      e.printStackTrace();
    } finally {
      // clean up
      select.close();
      conn.close();
    }
    return user;
  }
  
  public void insertScoreForMovieForUser(User user, Movie movie, double score) throws SQLException {
    String query = "INSERT INTO user_has_movie VALUES ("+user.getUserId()+", "+movie.getId()+", "+score+")";
    Connection conn = null;
    Statement statement = null;
    try {
      conn = this.getConn();
      statement = conn.createStatement();
      statement.executeUpdate(query);
    } catch (Exception e) {
      System.err.println("Mysql Statement Error: " + query);
      e.printStackTrace();
    } finally {
      statement.close();
      conn.close();
    }
  }
  
  public void insertScoresForMoviesForUser(User user, Movie[] movies, double[] scores) throws SQLException {
    String[] segment = new String[scores.length];
    for (int i = 0; i < scores.length; i++) {
      Movie movie = movies[i];
      segment[i] = String.format("(%d, %d, %.1f)", user.getUserId(), movie.getId(), scores[i]);
    }
    String query = StringUtils.join(segment,",");
    Connection conn = null;
    Statement statement = null;
    try {
      conn = this.getConn();
      statement = conn.createStatement();
      statement.executeUpdate(query);
    } catch (Exception e) {
      System.err.println("Mysql Statement Error: " + query);
      e.printStackTrace();
    } finally {
      statement.close();
      conn.close();
    }
  }
  
  public HashMap<User, HashMap<Movie, Double>> getMovieScoresForUsers() throws SQLException {
    String query = "SELECT user.*, movie.id as movieId, movie.name, user_has_movie.score" +
        " FROM user INNER JOIN user_has_movie ON user.id = user_has_movie.user_id INNER JOIN" +
        " movie ON user_has_movie.movie_id = movie.id";
    Connection conn = null;
    Statement select = null;
    ResultSet result = null;
    HashMap<User, HashMap<Movie, Double>> scores = null;
    try {
      conn = this.getConn();
      select = conn.createStatement();
      result = select.executeQuery(query);
      int size = DB_User.getResultSetSize(result);
      if (size == 0) {
        return null;
      }

      scores = new HashMap<User, HashMap<Movie,Double>>();
      
      while (result.next()) {
        User user = new User();
        user.setUserId(result.getInt("id"));
        user.setFirstName(result.getString("first_name"));
        user.setLastName(result.getString("last_name"));
        Movie movie = new Movie();
        movie.setId(result.getInt("movieId"));
        movie.setName(result.getString("name"));
        if (scores.containsKey(user)) {
          scores.get(user).put(movie, result.getDouble("score"));
        } else {
          HashMap<Movie, Double> userHasMovie = new HashMap<Movie,Double>();
          userHasMovie.put(movie, result.getDouble("score"));
          scores.put(user, userHasMovie);
        }
      }
      
    } catch (Exception e) {
      System.err.println("Mysql Statement Error: " + query);
      e.printStackTrace();
    } finally {
      select.close();
      conn.close();
    }
    return scores;
  }
}
