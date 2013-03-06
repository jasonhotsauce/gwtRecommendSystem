/**
 * 
 */
package com.myself.movie.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlexTable;

/**
 * @author jasonhotsauce
 *
 */
public class RecommendView extends Composite {
  
  private final FlexTable recommendList = new FlexTable();
  public RecommendView() {
    
    VerticalPanel verticalPanel = new VerticalPanel();
    initWidget(verticalPanel);
    
    Label lblMoviesRecommendedTo = new Label("Movies Recommended to You");
    verticalPanel.add(lblMoviesRecommendedTo);
    
    
    verticalPanel.add(recommendList);
    recommendList.setWidth("449px");
    recommendList.setCellPadding(5);
    recommendList.setCellSpacing(10);
    
  }
  
  public FlexTable getRecommendList() {
    return this.recommendList;
  }

}
