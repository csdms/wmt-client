package edu.colorado.csdms.wmt.client.ui.handler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;

import edu.colorado.csdms.wmt.client.control.DataManager;
import edu.colorado.csdms.wmt.client.control.DataTransfer;

/**
 * Handles click on the "Reload" button in the ModelActionPanel. It calls
 * DataTransfer.getComponent to fetch all components from the server.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */

public class ModelActionPanelReloadHandler implements ClickHandler {

  private DataManager data;

  /**
   * Creates a new instance of {@link ModelActionPanelReloadHandler}.
   * 
   * @param data the DataManager object for the WMT session
   */
  public ModelActionPanelReloadHandler(DataManager data) {
    this.data = data;
  }

  @Override
  public void onClick(ClickEvent event) {

    // Hide the MoreActionsMenu.
    data.getPerspective().getActionButtonPanel().getMoreMenu().hide();

    // Reload each component from the original list.
    for (String componentId : data.componentIdList) {
      DataTransfer.reloadComponent(data, componentId);
    }

    // Not really, since the above is asynchronous.
    Window.alert("Components reloaded.");
  }
}
