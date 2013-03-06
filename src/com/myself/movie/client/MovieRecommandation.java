/**
 * 
 */
package com.myself.movie.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.myself.movie.client.services.RecommandationClientInit;

/**
 * @author jasonhotsauce
 *
 */
public class MovieRecommandation implements EntryPoint {

  /* (non-Javadoc)
   * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
   */
  @Override
  public void onModuleLoad() {
    RecommandationClientInit clientInit = new RecommandationClientInit(GWT.getModuleBaseURL() + "recommand");
    RootPanel.get("wrapper").add(clientInit.getMainView());
    User user = new User();
    user.setUserId(1);
    clientInit.getWatchedMovies(user);
    clientInit.getMovies();
  }

}
