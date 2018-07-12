package edu.colorado.csdms.wmt.client.ui.menu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.colorado.csdms.wmt.client.Constants;
import edu.colorado.csdms.wmt.client.control.DataManager;
import edu.colorado.csdms.wmt.client.ui.ModelTree;
import edu.colorado.csdms.wmt.client.ui.cell.ComponentCell;
import edu.colorado.csdms.wmt.client.ui.panel.ComponentInformationPanel;

/**
 * A {@link PopupPanel} menu that defines actions that can be performed on a
 * component. Shown after a component has been selected in a
 * {@link ComponentCell}.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class ComponentActionMenu extends PopupPanel {

  private DataManager data;
  private ComponentCell cell;

  /**
   * Makes a new {@link ComponentActionMenu}, showing actions that can be
   * performed on a component in a {@link ComponentCell}.
   * 
   * @param data the DataManager object for the WMT session
   * @param cell the {@link ComponentCell} this menu depends on
   */
  public ComponentActionMenu(DataManager data, ComponentCell cell) {

    super(true); // autohide
    this.data = data;
    this.cell = cell;
    this.setStyleName("wmt-PopupPanel");

    // A VerticalPanel for the menu items. (PopupPanels have only one child.)
    VerticalPanel menu = new VerticalPanel();
    this.add(menu);

    HTML showParameters = new HTML(Constants.FA_WRENCH + "Show parameters");
    showParameters.addClickHandler(new ShowParametersHandler());
    showParameters.setStyleName("wmt-PopupPanelItem");
    showParameters.setTitle(Constants.COMPONENT_SHOW);
    menu.add(showParameters);

    HTML getInformation = new HTML(Constants.FA_INFO + "Get information");
    getInformation.addClickHandler(new GetInformationHandler());
    getInformation.setStyleName("wmt-PopupPanelItem");
    getInformation.setTitle(Constants.COMPONENT_INFO);
    menu.add(getInformation);

    HTML separator = new HTML();
    separator.setStyleName("wmt-PopupPanelSeparator");
    menu.add(separator);

    HTML closeComponent = new HTML(Constants.FA_CLOBBER + "Close");
    closeComponent.addClickHandler(new CloseComponentHandler());
    closeComponent.setStyleName("wmt-PopupPanelItem");
    closeComponent.setTitle(Constants.COMPONENT_CLOSE);
    menu.add(closeComponent);
  }

  /**
   * Removes a component, and anything beneath it, from the {@link ModelTree}.
   */
  private void closeComponent(ComponentCell cell) {
    GWT.log("Delete: " + data.getComponent(cell.getComponentId()).getName());

    // For convenience, get the ModelTree reference and the reference to the
    // targeted TreeItem.
    ModelTree tree = data.getPerspective().getModelTree();
    TreeItem target = cell.getEnclosingTreeItem();

    // Delete all children of the target TreeItem.
    target.removeItems();

    // If the parameters of the about-to-be-deleted component, or any of its
    // children, are displayed, clear the ParameterTable.
    String showing = data.getPerspective().getParameterTable().getComponentId();
    if ((showing != null)
        && (cell.getComponentId().contains(showing) || !tree
            .isComponentPresent(showing))) {
      data.getPerspective().getParameterTable().clearTable();
    }

    // If this isn't the driver, delete the target TreeItem and replace it
    // with a new one sporting the appropriate open uses port. If it is the
    // driver, reinitialize the ModelTree, update the available components,
    // deselect all labels and rebuild the labels menu.
    if (target.getParentItem() != null) {
      TreeItem parent = target.getParentItem();
      Integer targetIndex = parent.getChildIndex(target);
      parent.removeItem(target);
      tree.insertTreeItem(cell.getPortId(), parent, targetIndex);
    } else {
      tree.initializeTree();
      data.resetModelComponents(); // revert any mods to params; issue #28
      ((ComponentSelectionMenu) tree.getDriverComponentCell()
          .getComponentMenu()).updateComponents();
      try {
        data.getPerspective().getLabelsMenu().resetSelections(); // issue #27
        data.getPerspective().getLabelsMenu().populateMenu();
      } catch (Exception e) {
        GWT.log(e.toString());
      }
      data.saveAttempts++;
    }

    // If the deleted cell is not an alias, check the rest of the ModelTree
    // for aliases of this cell's component and delete them all.
    if (!cell.isAlias()) {
      Boolean keepLooping = true;
      while (keepLooping) {
        ComponentCell alias = tree.getAliasedComponent(cell.getComponentId());
        if (alias == null) {
          keepLooping = false;
        } else {
          closeComponent(alias); // recursive
        }
      }
    }

    // Update the title of the Model tab.
    data.updateModelSaveState(false);
  }

  /**
   * Handles a click on the "Show parameters" button in the
   * {@link ComponentActionMenu}. Shows the parameters for the currently
   * selected component in the ParameterTable.
   */
  public class ShowParametersHandler implements ClickHandler {

    @Override
    public void onClick(ClickEvent event) {
      GWT.log("Show parameters for: "
          + data.getComponent(cell.getComponentId()).getName());
      ComponentActionMenu.this.hide();
      data.getPerspective().getParameterTable().loadTable(cell);
      ComponentCell wasShowingParameters = data.getShowingParameters();
      wasShowingParameters.removeStyleDependentName("showingParameters");
      data.setShowingParameters(cell);
    }
  }

  /**
   * Handles a click on the "Get information" button in the
   * {@link ComponentActionMenu}. Displays information about the currently
   * selected component in a popup.
   */
  public class GetInformationHandler implements ClickHandler {

    @Override
    public void onClick(ClickEvent event) {
      final HTML getInformation = (HTML) event.getSource();
      final ComponentInformationPanel panel =
          new ComponentInformationPanel(ComponentActionMenu.this.data
              .getComponent(ComponentActionMenu.this.cell.getComponentId()));
      panel.setPopupPositionAndShow(new PositionCallback() {
        final Integer x = getInformation.getElement().getAbsoluteRight();
        final Integer y = getInformation.getAbsoluteTop();

        @Override
        public void setPosition(int offsetWidth, int offsetHeight) {
          panel.setPopupPosition(x, y);
        }
      });
    }
  }

  /**
   * Handles a click on the "Close" button in the {@link ComponentActionMenu}.
   * Removes the component, and anything beneath it, from the {@link ModelTree}.
   */
  public class CloseComponentHandler implements ClickHandler {

    @Override
    public void onClick(ClickEvent event) {
      ComponentActionMenu.this.hide();
      closeComponent(cell);
    }
  }
}
