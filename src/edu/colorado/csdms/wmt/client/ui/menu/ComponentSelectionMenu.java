package edu.colorado.csdms.wmt.client.ui.menu;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.colorado.csdms.wmt.client.Constants;
import edu.colorado.csdms.wmt.client.control.DataManager;
import edu.colorado.csdms.wmt.client.ui.cell.ComponentCell;
import edu.colorado.csdms.wmt.client.ui.handler.ComponentSelectionCommand;

/**
 * A {@link PopupPanel} menu that displays a scrollable list of components
 * available in WMT. This is the initial menu displayed in a
 * {@link ComponentCell}.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class ComponentSelectionMenu extends PopupPanel {

  private DataManager data;
  private ComponentCell cell;
  private VerticalPanel componentSelectionPanel;
  private ScrollPanel scroller;
  private MenuItem componentItem;

  /**
   * Makes a new {@link ComponentSelectionMenu}, which shows a list of
   * components; the user chooses one to populate the {@link ComponentCell}.
   * 
   * @param data the DataManager object for the WMT session
   * @param cell the {@link ComponentCell} this menu depends on
   */
  public ComponentSelectionMenu(DataManager data, ComponentCell cell) {

    super(true); // autohide
    this.data = data;
    this.cell = cell;
    this.setStyleName("wmt-PopupPanel");
    
    // A VerticalPanel for the menu items. (PopupPanels have only one child.)
    VerticalPanel menu = new VerticalPanel();
    this.add(menu);

    // Components are listed on the componentSelectionPanel, situated on a
    // ScrollPanel.
    componentSelectionPanel = new VerticalPanel();
    scroller = new ScrollPanel(componentSelectionPanel);
    menu.add(scroller);

    updateComponents(cell.getPortId());
  }

  /**
   * A worker for adding a new MenuItem to the bottom of the
   * {@link ComponentSelectionMenu}.
   * 
   * @param componentId the id of the component to add to the menu
   */
  private void insertComponentMenuItem(String componentId) {
    insertComponentMenuItem(componentId, componentSelectionPanel.getWidgetCount());
  }

  /**
   * A worker for adding a new MenuItem to the {@link ComponentSelectionMenu} at
   * the given index.
   * 
   * @param componentId the id of the component to add to the menu
   * @param index where to add the component to the menu
   */
  private void insertComponentMenuItem(String componentId, Integer index) {

    HTML item = new HTML(data.getComponent(componentId).getName());
    item.setStyleName("wmt-ComponentSelectionMenuItem");
    item.addClickHandler(new ComponentSelectionHandler(componentId));
    componentSelectionPanel.insert(item, index);

    // If the number of components in the componentSelectionPanel exceeds
    // a threshold value, turn on the scrollPanel.
    if (componentSelectionPanel.getWidgetCount() >= Constants.SCROLL_THRESHOLD) {
      scroller.setSize(Constants.MENU_WIDTH, Constants.MENU_HEIGHT);
    }
  }

  private void insertComponentMenuMessage() {
//    HTML item = new HTML("No matching components");
    HTML item = new HTML("No matches found");
    item.setStyleName("wmt-EmptyComponentSelectionMenu");
    componentSelectionPanel.insert(item, 0);
  }

  /**
   * Loads the names of the components that match the uses port of the displayed
   * component into the {@link ComponentCell} menu.
   * 
   * @param usesId the id of the uses port displayed in the ComponentCell
   */
  public void updateComponents(String usesId) {

    componentSelectionPanel.clear();

    // Display a wait message in the componentMenu.
    if (usesId.matches(Constants.DRIVER)) {
      HTML item = new HTML("Loading...");
      componentSelectionPanel.add(item);
      return;
    }

    // Load all available components into the componentMenu!
    if (usesId.matches(Constants.ALL_COMPONENTS)) {
      for (int i = 0; i < data.componentIdList.size(); i++) {
        String componentId = data.componentIdList.get(i);
        insertComponentMenuItem(componentId);
      }
      return;
    }

    // Load only those components with provides ports matching the input portId.
    Boolean hasMatch = false;
    for (int i = 0; i < data.componentIdList.size(); i++) {
      String componentId = data.componentIdList.get(i);
      Integer nProvidesPorts =
          data.getComponent(componentId).getProvidesPorts().length();
      for (int j = 0; j < nProvidesPorts; j++) {
        String providesId =
            data.getComponent(componentId).getProvidesPorts().get(j).getId();
        if (providesId.matches(usesId)) {
          insertComponentMenuItem(componentId);
          hasMatch = true;
        }
      }
    }
    if (!hasMatch) {
//      HTML item = new HTML("No matching components");
//      componentSelectionPanel.add(item);
      insertComponentMenuMessage();
    }

  }

  /**
   * Loads the names of all available components into the {@link ComponentCell}
   * menu.
   */
  public void updateComponents() {
    updateComponents(Constants.ALL_COMPONENTS);
  }

  /**
   * This method builds the initial list of MenuItems in the driver
   * {@link ComponentCell}. This list functions as a placeholder. Only the ids
   * of the components are displayed, and there is no command associated with
   * the MenuItems. These MenuItems are replaced with fully functional MenuItems
   * when their associated component is successfully loaded from the server.
   */
  public void initializeComponents() {
    componentSelectionPanel.clear();
    for (int i = 0; i < data.componentIdList.size(); i++) {
      HTML item = new HTML(data.componentIdList.get(i));
      item.setStyleName("wmt-ComponentSelectionMenuItem");
      item.addStyleDependentName("missing");
      componentSelectionPanel.add(item);
    };
  }
  
  /**
   * Replaces a placeholder MenuItem, created by
   * {@link ComponentSelectionMenu#initializeComponents()}, with a fully
   * functional MenuItem showing the component name and having an associated
   * action.
   * 
   * @param componentId the id of the component associated with the MenuItem to
   *          replace
   */
  public void replaceMenuItem(String componentId) {
    for (int i = 0; i < componentSelectionPanel.getWidgetCount(); i++) {
      HTML currentItem = (HTML) componentSelectionPanel.getWidget(i);
      if (currentItem.getText().matches(componentId)) {
        insertComponentMenuItem(componentId, i);
        componentSelectionPanel.remove(currentItem);
        return;
      }
    }
  }

  public MenuItem getComponentItem() {
    return componentItem;
  }

  public void setComponentItem(MenuItem componentItem) {
    this.componentItem = componentItem;
  }

  /**
   * Handles a click on a menu item in the {@link ComponentSelectionMenu}.
   * <p>
   * <b>Note:</b> This class wraps {@link ComponentSelectionCommand}. It might
   * be helpful to port the code from there to this handler.
   */
  public class ComponentSelectionHandler implements ClickHandler {

    private String componentId;
    
    /**
     * Makes a new {@link ComponentSelectionHandler}.
     * 
     * @param componentId the id of the component of the selected menu item
     */
    public ComponentSelectionHandler(String componentId) {
      this.componentId = componentId;
    }
    
    @Override
    public void onClick(ClickEvent event) {
      ComponentSelectionMenu.this.hide();
      if (!data.security.isLoggedIn()) {
        return;
      }
      ComponentSelectionCommand cmd =
          new ComponentSelectionCommand(data, cell, data.getComponent(
              componentId).getId());
      cmd.execute();
    }
  }
}
