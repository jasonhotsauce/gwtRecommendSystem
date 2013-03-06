/**
 * 
 */
package com.myself.movie.client.services;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.myself.movie.client.Movie;
import com.myself.movie.client.User;

/**
 * @author jasonhotsauce
 *
 */
public interface RecommandServiceAsync {

  void getMovies(AsyncCallback<Movie[]> callback);

  void setMovieScoreForUser(User user, Movie movie, double score, AsyncCallback<Void> callback);

  void getScoreForUserForMovie(User user, Movie movie, AsyncCallback<Double> callback);

  void recommandMoviesToUser(User user, AsyncCallback<HashMap<Movie, Float>> callback);

  void getUserForId(int id, AsyncCallback<User> callback);

  void getWatchedMovies(User user, AsyncCallback<Movie[]> callback);

}
