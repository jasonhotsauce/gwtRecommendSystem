/**
 * 
 */
package com.myself.movie.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Button;
import com.myself.movie.client.services.RecommandationClientInit;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
/**
 * @author jasonhotsauce
 *
 */
public class WatchedMovieView extends Composite {
  
  private RecommandationClientInit service;
  private FlexTable watchedList = new FlexTable();
  private ListBox listBox;
  final private User user;
  private final DoubleBox doubleBox = new DoubleBox();
  
  public WatchedMovieView(RecommandationClientInit service) {
    user = new User();
    // Hard coded to myself;
    user.setUserId(1);
    
    this.service = service;
    VerticalPanel verticalPanel = new VerticalPanel();
    initWidget(verticalPanel);
    
    final FormPanel formPanel = new FormPanel();
    
    verticalPanel.add(formPanel);
    formPanel.setHeight("84px");
    
    HorizontalPanel horizontalPanel = new HorizontalPanel();
    formPanel.setWidget(horizontalPanel);
    horizontalPanel.setSize("100%", "100%");
    formPanel.setMethod(FormPanel.METHOD_POST);
    formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
    
    
    Label lblMovie = new Label("Movie");
    horizontalPanel.add(lblMovie);
    
    listBox = new ListBox();
    horizontalPanel.add(listBox);
    listBox.setWidth("162px");
    listBox.setVisibleItemCount(1);
    listBox.setName("movie");
    
    
    horizontalPanel.add(doubleBox);
    doubleBox.setName("score");
    
    Button btnSubmit = new Button("Submit");
    horizontalPanel.add(btnSubmit);
    btnSubmit.addClickHandler(new ClickHandler() {
      
      @Override
      public void onClick(ClickEvent event) {
        Movie movie = new Movie();
        movie.setId(Integer.parseInt(listBox.getValue(listBox.getSelectedIndex())));
        movie.setName(listBox.getItemText(listBox.getSelectedIndex()));
        double score = doubleBox.getValue();
        WatchedMovieView.this.service.setMovieScoreForUser(user, movie, score);
      }
    });
    
    Label lblMoviesYouHave = new Label("Movies you have watched:");
    verticalPanel.add(lblMoviesYouHave);
    
    verticalPanel.add(watchedList);
    watchedList.setSize("449px", "25px");
    watchedList.setCellPadding(5);
    watchedList.setCellSpacing(10);
  }
  
  public void addWatchedMovie(Movie movie) {
    int row = this.watchedList.getRowCount();
    this.watchedList.setHTML(row, 0, movie.getName());
    this.doubleBox.setValue(null);
    this.listBox.removeItem(this.listBox.getSelectedIndex());
  }
  
  public void addWatchedMovie(Movie[] movies) {
    int lastRow = this.watchedList.getRowCount();
    for (Movie movie : movies) {
      this.watchedList.setHTML(lastRow, 0, movie.getName());
      lastRow++;
    }
  }
  
  public void addMoviesToList(Movie[] movies) {
    for (Movie movie : movies) {
      this.listBox.addItem(movie.getName(), Integer.toString(movie.getId()));
    }
  }

}
