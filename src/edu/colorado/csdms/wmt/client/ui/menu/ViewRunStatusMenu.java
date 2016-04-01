package edu.colorado.csdms.wmt.client.ui.menu;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.colorado.csdms.wmt.client.control.DataManager;
import edu.colorado.csdms.wmt.client.control.DataURL;

/**
 * Displays a menu that allows a user to choose whether to display information
 * about the current simulation, or all simulations.
 *
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class ViewRunStatusMenu extends PopupPanel {

  private DataManager data;
  private VerticalPanel menu;
  
  /**
   * Creates a new {@link ViewRunStatusMenu}.
   * 
   * @param data the DataManager object for the WMT session
   */
  public ViewRunStatusMenu(DataManager data) {

    super(true); // autohide
    this.data = data;
    this.setStyleName("wmt-PopupPanel");

    // A VerticalPanel for the menu items. (PopupPanels have only one child.)
    menu = new VerticalPanel();
    this.add(menu);

    populateMenu();
  }

  /**
   * A helper that fills in the {@link ViewRunStatusMenu}.
   */
  public void populateMenu() {

    menu.clear();
    
    // Current model run
    final HTML currentRunButton = new HTML("Current model run");
    currentRunButton.setStyleName("wmt-PopupPanelItem");
    currentRunButton.addClickHandler(new RunStatusClickHandler("current"));
    menu.add(currentRunButton);

    // All model runs
    final HTML allRunsButton = new HTML("All model runs");
    allRunsButton.setStyleName("wmt-PopupPanelItem");
    allRunsButton.addClickHandler(new RunStatusClickHandler("all"));
    menu.add(allRunsButton);
  }
  
  /**
   * Handles a click in the {@link ViewRunStatusMenu}.
   */
  public class RunStatusClickHandler implements ClickHandler {

    private String type;
    
    public RunStatusClickHandler(String type) {
      this.type = type;
    }
    
    @Override
    public void onClick(ClickEvent event) {
      ViewRunStatusMenu.this.hide();
      data.getPerspective().getActionButtonPanel().getMoreMenu().hide();
      if (type.matches("current")) {
        Window.open(DataURL.showCurrentModelRun(data), "WMT_currentRun", null);
      } else {
        Window.open(DataURL.showAllModelRuns(data), "WMT_allRuns", null);
      }
    }
  }
}
