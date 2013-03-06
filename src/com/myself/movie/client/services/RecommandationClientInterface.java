/**
 * 
 */
package com.myself.movie.client.services;

import com.myself.movie.client.Movie;
import com.myself.movie.client.User;

/**
 * @author jasonhotsauce
 *
 */
public interface RecommandationClientInterface {
  public void getUserForId(int id);

  public void getMovies();

  public void setMovieScoreForUser(User user, Movie movie, double score);

  public void getScoreForUserForMovie(User user, Movie movie);

  public void recommandMoviesToUser(User user);
  
  public void getWatchedMovies(User user);
}
