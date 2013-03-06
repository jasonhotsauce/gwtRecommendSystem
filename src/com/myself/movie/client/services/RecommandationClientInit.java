/**
 * 
 */
package com.myself.movie.client.services;

import java.util.HashMap;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.myself.movie.client.MainView;
import com.myself.movie.client.Movie;
import com.myself.movie.client.User;
import com.myself.movie.client.WatchedMovieView;

/**
 * @author jasonhotsauce
 *
 */
public class RecommandationClientInit implements RecommandationClientInterface {
  
  private static RecommandServiceAsync service;
  private MainView mainView;
  private WatchedMovieView watchedList;
  
  public RecommandationClientInit(String url) {
    ServiceDefTarget endPoint = (ServiceDefTarget) getService();
    endPoint.setServiceEntryPoint(url);
    this.watchedList = new WatchedMovieView(this);
    this.mainView = new MainView(this.watchedList, this);
  }
  
  public static RecommandServiceAsync getService() {
    if (service == null) {
      service = GWT.create(RecommandService.class);
    }
    return service;
  }
  
  public MainView getMainView() {
    return this.mainView;
  }
  /* (non-Javadoc)
   * @see com.myself.movie.client.services.RecommandationClientInterface#getUsers()
   */
  @Override
  public void getUserForId(int id) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see com.myself.movie.client.services.RecommandationClientInterface#getMovies()
   */
  @Override
  public void getMovies() {
    RecommandationClientInit.getService().getMovies(new AsyncCallback<Movie[]>() {

      @Override
      public void onFailure(Throwable caught) {
        // TODO Auto-generated method stub
        
      }

      @Override
      public void onSuccess(Movie[] result) {
        watchedList.addMoviesToList(result);
      }
    });
  }

  /* (non-Javadoc)
   * @see com.myself.movie.client.services.RecommandationClientInterface#setMovieScoreForUser(com.myself.movie.client.User, double)
   */
  @Override
  public void setMovieScoreForUser(User user, Movie movie, double score) {
    final Movie watchedMovie = movie;
    RecommandationClientInit.getService().setMovieScoreForUser(user, movie, score, new AsyncCallback<Void>() {

      @Override
      public void onFailure(Throwable caught) {
        // TODO Auto-generated method stub
        
      }

      @Override
      public void onSuccess(Void result) {
        watchedList.addWatchedMovie(watchedMovie);
      }
    });
  }

  /* (non-Javadoc)
   * @see com.myself.movie.client.services.RecommandationClientInterface#getScoreForUserForMovie(com.myself.movie.client.User, com.myself.movie.client.Movie)
   */
  @Override
  public void getScoreForUserForMovie(User user, Movie movie) {
    RecommandationClientInit.getService().getScoreForUserForMovie(user, movie, new AsyncCallback<Double>() {

      @Override
      public void onFailure(Throwable caught) {
        // TODO Auto-generated method stub
        
      }

      @Override
      public void onSuccess(Double result) {
        // TODO Auto-generated method stub
        
      }
    });
  }

  /* (non-Javadoc)
   * @see com.myself.movie.client.services.RecommandationClientInterface#recommandMoviesToUser(com.myself.movie.client.User)
   */
  @Override
  public void recommandMoviesToUser(User user) {
    RecommandationClientInit.getService().recommandMoviesToUser(user, new AsyncCallback<HashMap<Movie, Float>>() {

      @Override
      public void onFailure(Throwable caught) {
        // TODO Auto-generated method stub
        
      }

      @Override
      public void onSuccess(HashMap<Movie, Float> result) {
        mainView.addRecommendedMovies(result);
      }
    });

  }

  /* (non-Javadoc)
   * @see com.myself.movie.client.services.RecommandationClientInterface#getWatchedMovies()
   */
  @Override
  public void getWatchedMovies(User user) {
    RecommandationClientInit.getService().getWatchedMovies(user, new AsyncCallback<Movie[]>() {
      
      @Override
      public void onSuccess(Movie[] result) {
        watchedList.addWatchedMovie(result);
      }
      
      @Override
      public void onFailure(Throwable caught) {
        // TODO Auto-generated method stub
        
      }
    });
  }
  

}
