/**
 * 
 */
package com.myself.movie.server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.myself.movie.server.DB_User;
import com.myself.movie.client.Movie;
import com.myself.movie.client.User;
import com.myself.movie.client.services.RecommandService;

/**
 * @author jasonhotsauce
 *
 */
public class RecommandationServiceImpl extends RemoteServiceServlet implements RecommandService {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private static HashMap<User, HashMap<Movie, Double>> data = new HashMap<User, HashMap<Movie,Double>>();
 
  
  /* (non-Javadoc)
   * @see com.myself.movie.client.services.RecommandService#getUsers()
   */
  @Override
  public User getUserForId(int id) {
    DB_User db = new DB_User();
    User user = null;
    try {
      
      user = db.getUserForId(id);
      
    } catch (SQLException e) {
      System.err.println("MySQL ERROR: "+e.getMessage());
      e.printStackTrace();
    }
    
    return user;
  }

  /* (non-Javadoc)
   * @see com.myself.movie.client.services.RecommandService#getMovies()
   */
  @Override
  public Movie[] getMovies() {
    // TODO Auto-generated method stub
    DB_Movie db = new DB_Movie();
    Movie[] movies = null;
    try {
      movies = db.getMovies();
    } catch (SQLException e) {
      System.err.println("MySQL ERROR: "+e.getMessage());
      e.printStackTrace();
    }
    
    return movies;
  }

  /* (non-Javadoc)
   * @see com.myself.movie.client.services.RecommandService#getScoreForUserForMovie(com.myself.movie.client.User, com.myself.movie.client.Movie)
   */
  @Override
  public double getScoreForUserForMovie(User user, Movie movie) {
    // TODO Auto-generated method stub
    return 0;
  }

  /* (non-Javadoc)
   * @see com.myself.movie.client.services.RecommandService#setMovieScoreForUser(com.myself.movie.client.User, double)
   */
  @Override
  public void setMovieScoreForUser(User user, Movie movie, double score) {
    DB_User db = new DB_User();
    try {
      db.insertScoreForMovieForUser(user, movie, score);
    } catch (SQLException e) {
      System.err.println("MySQL ERROR: "+e.getMessage());
      e.printStackTrace();
    }
  }
  
  private void setData() {
    DB_User db = new DB_User();
    try {
      data = db.getMovieScoresForUsers();
    } catch (SQLException e) {
      System.err.println("MySQL ERROR: "+e.getMessage());
      e.printStackTrace();
    }
  }

  /* (non-Javadoc)
   * @see com.myself.movie.client.services.RecommandService#recommandMoviesToUser(com.myself.movie.client.User)
   */
  @Override
  public HashMap<Movie, Float> recommandMoviesToUser(User user) {
    this.setData();
    HashMap<Movie, Float> totalMovies = new HashMap<Movie, Float>();
    HashMap<Movie, Float> simSum = new HashMap<Movie, Float>();
    for (User other : data.keySet()) {
      if (other != user) {
        float sim = this.pearsonDistance(user, other);
        if (sim <= 0) {
          continue;
        }
        for (Movie movie : data.get(other).keySet()) {
          // only score the movie I haven't watched.
          if (!data.get(user).containsKey(movie) || data.get(user).get(movie) == 0) {
            // Calculate the total score for a movie which will be recommended to me.
            if (totalMovies.containsKey(movie)) {
              totalMovies.put(movie, (float) (totalMovies.get(movie)+data.get(other).get(movie)*sim));
            } else {
              totalMovies.put(movie, (float) (data.get(other).get(movie)*sim));
            }
            
            // sum the sim
            if (simSum.containsKey(movie)) {
              simSum.put(movie, simSum.get(movie)+sim);
            } else {
              simSum.put(movie, sim);
            }
          }
        }
      }
    }
 // create ranking
    HashMap<Movie, Float> unsortedRank = new HashMap<Movie, Float>();
    for (Movie movie : totalMovies.keySet()) {
      unsortedRank.put(movie, totalMovies.get(movie)/simSum.get(movie));
    }
    // Reverse sort the map, so the first item will be the best match.
    
    return unsortedRank;
  }
  
  private float pearsonDistance(User user1, User user2) {
    ArrayList<Movie> sim = new ArrayList<Movie>();
    HashMap<Movie, Double> dataSet1 = data.get(user1);
    HashMap<Movie, Double> dataSet2 = data.get(user2);
    
    // Get the common movies.
    for (Movie movie : dataSet1.keySet()) {
      if (dataSet2.keySet().contains(movie)) {
        sim.add(movie);
      }
    }
    if (sim.size() == 0) {
      return 0;
    }
    
    // Calculate sum for all movies
    float sum1 = 0;
    float sum2 = 0;
    float sum1sq = 0;
    float sum2sq = 0;
    float mSum = 0;
    for (Movie movie : sim) {
      sum1 += dataSet1.get(movie);
      sum2 += dataSet2.get(movie);
    //calculate sum of square
      sum1sq += Math.pow(dataSet1.get(movie), 2);
      sum2sq += Math.pow(dataSet2.get(movie), 2);
    //Sum the movies
      mSum += dataSet1.get(movie) * dataSet2.get(movie);
    }
    
    int n = sim.size();
    float num = (float) (mSum - (sum1 * sum2) / n);
    float den = (float) Math.sqrt((sum1sq - Math.pow(sum1, 2)/n)*(sum2sq - Math.pow(sum2, 2)/n));
    if (den == 0) {
      return 0;
    }
    float r = num/den;
    return r;
  }

  /* (non-Javadoc)
   * @see com.myself.movie.client.services.RecommandService#getWatchedMovies(com.myself.movie.client.User)
   */
  @Override
  public Movie[] getWatchedMovies(User user) {
    DB_Movie db = new DB_Movie();
    Movie[] movies = null;
    try {
      movies = db.getWatchedMovies(user);
    } catch (SQLException e) {
      System.err.println("MySQL ERROR: "+e.getMessage());
      e.printStackTrace();
    }
    return movies;
  }

}
