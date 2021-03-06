package edu.colorado.csdms.wmt.client.ui.menu;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.colorado.csdms.wmt.client.Constants;
import edu.colorado.csdms.wmt.client.control.DataManager;
import edu.colorado.csdms.wmt.client.data.ComponentJSO;
import edu.colorado.csdms.wmt.client.ui.panel.ComponentInformationPanel;

/**
 * Encapsulates an alphabetized, scrollable list of components.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class ComponentInformationMenu extends PopupPanel {
  
  private DataManager data;
  private VerticalPanel componentsPanel;
  
  /**
   * Makes a new {@link ComponentInformationMenu}.
   * 
   * @param data the DataManager object for the WMT session
   */
  public ComponentInformationMenu(DataManager data) {

    super(true); // autohide
    this.data = data;
    this.setStyleName("wmt-PopupPanel");

    // A VerticalPanel for the menu items. (PopupPanels have only one child.)
    VerticalPanel menu = new VerticalPanel();
    this.add(menu);
    
    // Components are listed on the componentsPanel, situated on a ScrollPanel.
    componentsPanel = new VerticalPanel();
    ScrollPanel scroller = new ScrollPanel(componentsPanel);
    menu.add(scroller);
    
    // Populate the menu with the components.
    populateMenu();

    // If the number of components in the componentSelectionPanel exceeds
    // a threshold value, turn on the scrollPanel.
    if (componentsPanel.getWidgetCount() >= Constants.SCROLL_THRESHOLD) {
      scroller.setSize(Constants.MENU_WIDTH, Constants.MENU_HEIGHT);
    }
  }

  /**
   * A helper that loads the {@link ComponentInformationMenu} with components.
   */
  private void populateMenu() {
    componentsPanel.clear();    
    for (int i = 0; i < data.getComponents().size(); i++) {
      String componentId = data.componentIdList.get(i);
      final ComponentJSO componentJSO = data.getComponent(componentId);
      final HTML item = new HTML(componentJSO.getName());
      item.setStyleName("wmt-ComponentSelectionMenuItem");
      item.addClickHandler(new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
          final ComponentInformationPanel panel =
              new ComponentInformationPanel(componentJSO);
          panel.setPopupPositionAndShow(new PositionCallback() {
            final Integer x = item.getElement().getAbsoluteRight();
            final Integer y = item.getAbsoluteTop();
            @Override
            public void setPosition(int offsetWidth, int offsetHeight) {
              panel.setPopupPosition(x, y);
            }
          });
        }
      });
      componentsPanel.add(item);
    }
  }
}
