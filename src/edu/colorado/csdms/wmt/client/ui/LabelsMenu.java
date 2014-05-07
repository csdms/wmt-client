/**
 * <License>
 */
package edu.colorado.csdms.wmt.client.ui;

import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.colorado.csdms.wmt.client.Constants;
import edu.colorado.csdms.wmt.client.control.DataManager;
import edu.colorado.csdms.wmt.client.control.DataTransfer;
import edu.colorado.csdms.wmt.client.data.LabelJSO;
import edu.colorado.csdms.wmt.client.ui.handler.DialogCancelHandler;
import edu.colorado.csdms.wmt.client.ui.widgets.LabelDialogBox;

/**
 * Encapsulates an alphabetized, scrollable list of labels used to tag and
 * classify models. This menu is modeled on the "Labels" menu in Gmail.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class LabelsMenu extends PopupPanel {

  private DataManager data;
  private VerticalPanel labelPanel;
  private HTML addNewHtml;
  private HTML deleteHtml;
  private Boolean buttonsUnchecked = false; // show selected labels
  
  /**
   * Makes a new {@link LabelsMenu}.
   * 
   * @param data the DataManager object for the WMT session
   */
  public LabelsMenu(DataManager data) {

    super(true); // autohide
    this.data = data;
    this.setStyleName("wmt-PopupPanel");
    data.getPerspective().setLabelsMenu(this);

    // A VerticalPanel for the menu items. (PopupPanels have only one child.)
    VerticalPanel menu = new VerticalPanel();
    this.add(menu);
    
    // All labels are listed on the labelPanel, which sits on a ScrollPanel.
    labelPanel = new VerticalPanel();
    ScrollPanel scroller = new ScrollPanel(labelPanel);
    scroller.setSize(Constants.MENU_WIDTH, Constants.MENU_HEIGHT);
    menu.add(scroller);

    // Populate the menu with the stored model labels and their values.
    populateMenu();

    // These items are always visible on the bottom of the menu.
    HTML separator = new HTML("");
    separator.setStyleName("wmt-PopupPanelSeparator");
    addNewHtml = new HTML("Add new label");
    addNewHtml.setStyleName("wmt-PopupPanelItem");
    deleteHtml = new HTML("Delete label");
    deleteHtml.setStyleName("wmt-PopupPanelItem");
    menu.add(separator);
    menu.add(addNewHtml);
    menu.add(deleteHtml);
    
    // Show a SuggestBox to add or delete a label.
    addNewHtml.addClickHandler(new LabelHandler(data, "Add"));
    deleteHtml.addClickHandler(new LabelHandler(data, "Delete"));
  }
  
  /**
   * A helper that loads the {@link LabelsMenu} with {@link CheckBox} labels.
   * Each CheckBox has a handler that maps the selection state of the box to the
   * labels variable stored in the {@link DataManager}.
   */
  public void populateMenu() {
    labelPanel.clear();
    for (final Map.Entry<String, LabelJSO> entry : data.modelLabels.entrySet()) {
      final CheckBox labelBox = new CheckBox(entry.getKey());
      labelBox.setWordWrap(false);
      labelBox.setStyleName("wmt-PopupPanelCheckBoxItem");
      if (!buttonsUnchecked) {
        labelBox.setValue(entry.getValue().isSelected());
      }
      labelBox.addClickHandler(new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
          entry.getValue().isSelected(labelBox.getValue());
        }
      });
      labelPanel.add(labelBox);      
    }
  }

  /**
   * Gets the state of the buttonsUnchecked member.
   */
  public Boolean areButtonsUnchecked() {
    return buttonsUnchecked;
  }

  /**
   * Sets the state of the buttonsUnchecked member, used to toggle whether the 
   * states of the {@link LabelsMenu} buttons are shown or are turned off.
   * 
   * @param buttonsUnchecked true if all buttons are to be shown unselected
   */
  public void areButtonsUnchecked(Boolean buttonsUnchecked) {
    this.buttonsUnchecked = buttonsUnchecked;
  }

  /**
   * Handles adding and deleting model labels using a {@link LabelDialogBox}.
   */
  public class LabelHandler implements ClickHandler {

    private DataManager data;
    private String type;

    /**
     * Creates a new {@link LabelHandler}.
     * 
     * @param data the DataManager object for the WMT session
     * @param type the event type, currently "Add" or "Delete"
     */
    public LabelHandler(DataManager data, String type) {
      this.data = data;
      this.type = type; // use sentence case
    }
    
    @Override
    public void onClick(ClickEvent event) {
      
      final LabelDialogBox box = new LabelDialogBox(data);
      
      box.getChoicePanel().getCancelButton().addClickHandler(
          new DialogCancelHandler(box));
      box.getChoicePanel().getOkButton().setHTML(Constants.FA_TAGS + type);
      box.getChoicePanel().getOkButton().addClickHandler(new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
          String label = box.getSuggestBox().getText();
          if (type.equalsIgnoreCase("add")) {
            DataTransfer.addLabel(data, label);
          } else if (type.equalsIgnoreCase("delete")) {
            LabelJSO jso = data.modelLabels.get(label);
            if (jso != null) {
              if (!data.security.getWmtUsername().matches(jso.getOwner())) {
                Window.alert("You can't delete a label that you don't own.");
              } else {
                DataTransfer.deleteLabel(data, jso.getId());
              }
            }
          }
          box.hide();
        }
      });

      if (type.equalsIgnoreCase("add")) {
        box.showRelativeTo(addNewHtml);
      } else if (type.equalsIgnoreCase("delete")) {
        box.showRelativeTo(deleteHtml);
      }
      box.getSuggestBox().setFocus(true);
    }
  }
}
