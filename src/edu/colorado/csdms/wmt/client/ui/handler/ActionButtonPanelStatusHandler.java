/**
 * <License>
 */
package edu.colorado.csdms.wmt.client.ui.handler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;

import edu.colorado.csdms.wmt.client.control.DataManager;
import edu.colorado.csdms.wmt.client.control.DataURL;

/**
 * Handles click on the "Run status" button in the ActionButtonPanel. Displays the
 * API "run/show" page showing the status of all currently running models.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class ActionButtonPanelStatusHandler implements ClickHandler {

  private DataManager data;
  
  /**
   * Creates a new instance of {@link ActionButtonPanelStatusHandler}.
   * 
   * @param data the DataManager object for the WMT session
   */
  public ActionButtonPanelStatusHandler(DataManager data) {
    this.data = data;
  }
  
  @Override
  public void onClick(ClickEvent event) {
    Window.open(DataURL.showModelRun(data), "runInfoDialog", null);
  }
}
