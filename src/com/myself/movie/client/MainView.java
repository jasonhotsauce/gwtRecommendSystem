/**
 * 
 */
package com.myself.movie.client;

import java.util.HashMap;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.myself.movie.client.services.RecommandationClientInit;

/**
 * @author jasonhotsauce
 *
 */
public class MainView extends Composite {
  
  private VerticalPanel verticalPanel = new VerticalPanel();
  private RecommandationClientInit serviceImpl;
  private RecommendView recommendView;
  
  public MainView(WatchedMovieView watchedListView, RecommandationClientInit serviceImpl) {
    this.serviceImpl = serviceImpl;
    this.verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
    this.verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
    initWidget(this.verticalPanel);
    this.verticalPanel.setSize("715px", "426px");
    this.verticalPanel.add(watchedListView);
    recommendView = new RecommendView();
    this.verticalPanel.add(recommendView);
    
    User user = new User();
    user.setUserId(1);
    
    this.serviceImpl.recommandMoviesToUser(user);
  }
  
  public void addRecommendedMovies(HashMap<Movie, Float> movies) {
    FlexTable recommendList = this.recommendView.getRecommendList();
    int row = recommendList.getRowCount();
    for (Movie movie : movies.keySet()) {
      recommendList.setHTML(row, 0, movie.getName()+"<br/>The score you might give: "+movies.get(movie));
      row++;
    }
  }
  
}
