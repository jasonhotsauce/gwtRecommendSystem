/**
 * 
 */
package com.myself.movie.client.services;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.myself.movie.client.Movie;
import com.myself.movie.client.User;

/**
 * @author jasonhotsauce
 *
 */
@RemoteServiceRelativePath("recommand")
public interface RecommandService extends RemoteService {
  public User getUserForId(int id);
  
  public Movie[] getMovies();
  
  public Movie[] getWatchedMovies(User user);
  
  public double getScoreForUserForMovie(User user, Movie movie);
  
  public void setMovieScoreForUser(User user, Movie movie, double score);
  
  public HashMap<Movie, Float> recommandMoviesToUser(User user);
}
