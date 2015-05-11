/**
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014 mcflugen
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package edu.colorado.csdms.wmt.client.ui.widgets;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.colorado.csdms.wmt.client.Constants;
import edu.colorado.csdms.wmt.client.control.DataManager;
import edu.colorado.csdms.wmt.client.data.LabelJSO;
import edu.colorado.csdms.wmt.client.ui.dialog.OpenDialogBox;

/**
 * Encapsulates an alphabetized, scrollable list of labels used to tag and
 * classify models. This menu is modeled on the "Labels" menu in Gmail.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class LabelsOpenModelMenu extends PopupPanel {

  private DataManager data;
  private OpenDialogBox openDialog;
  private VerticalPanel labelPanel;
  private List<Integer> selectedLabelIds;
  
  /**
   * Makes a new {@link LabelsOpenModelMenu}, optionally specifying whether the
   * menu is used in the context of opening a saved model.
   * 
   * @param data the DataManager object for the WMT session
   * @param openDialog the reference of an enclosing {@link OpenDialogBox}
   */
  public LabelsOpenModelMenu(DataManager data, OpenDialogBox openDialog) {
    
    super(true); // autohide
    this.data = data;
    this.openDialog = openDialog;
    this.selectedLabelIds = new ArrayList<Integer>();
    this.setStyleName("wmt-PopupPanel");

    // A VerticalPanel for the menu items. (PopupPanels have only one child.)
    VerticalPanel menu = new VerticalPanel();
    this.add(menu);
    
    // All labels are listed on the labelPanel, which sits on a ScrollPanel.
    labelPanel = new VerticalPanel();
    ScrollPanel scroller = new ScrollPanel(labelPanel);
    scroller.setSize(Constants.MENU_WIDTH, Constants.MENU_HEIGHT);
    menu.add(scroller);

    // Populate the menu with the stored model labels.
    populateMenu();
  }
  
  /**
   * A helper that loads the {@link LabelsOpenModelMenu} with {@link CheckBox}
   * labels. Each CheckBox has a handler that maps the selection state of the
   * labels menu to the droplist in the {@link OpenDialogBox}.
   */
  public void populateMenu() {
    labelPanel.clear();
    for (Map.Entry<String, LabelJSO> entry : data.modelLabels.entrySet()) {
      CheckBox labelBox = new CheckBox(entry.getKey());
      labelBox.setWordWrap(false);
      labelBox.setStyleName("wmt-PopupPanelCheckBoxItem");
      if (!data.security.getWmtUsername().equals(entry.getValue().getOwner())) {
        labelBox.addStyleDependentName("public");
      }
      labelBox.addClickHandler(new LabelSelectionHandler());

      // Force the "public" and username labels to the top, in either order.
      if (entry.getKey().equals("public")
          || entry.getKey().equals(data.security.getWmtUsername())) {
        labelBox.addStyleDependentName("header");
        labelPanel.insert(labelBox, 0);
      } else {
        labelPanel.add(labelBox);
      }
      
      // Select the username label, by default.
      if (entry.getKey().equals(data.security.getWmtUsername())) {
        labelBox.setValue(true);
        entry.getValue().isSelected(true);
        selectedLabelIds.add(entry.getValue().getId());
      }
    }
  }
  
  /**
   * Makes an ArrayList of label ids that are selected in the labelPanel.
   * 
   * Note: If the "public" or username labels are not selected, then clear the
   * list of ids, regardless of whether other labels are selected; i.e., either
   * the "public" or username label must be selected.
   */
  private void makeSelectedLabelIdsList() {
    selectedLabelIds.clear();
    Boolean isPublicOrUserLabelSelected = false;
    for (int i = 0; i < labelPanel.getWidgetCount(); i++) {
      CheckBox labelBox = (CheckBox) labelPanel.getWidget(i);
      if (labelBox.getValue()) {
        if (labelBox.getText().matches("public") 
            || labelBox.getText().matches(data.security.getWmtUsername())) {
          isPublicOrUserLabelSelected = true;
        }
        LabelJSO jso = data.modelLabels.get(labelBox.getText());
        selectedLabelIds.add(jso.getId());
      }
    }
    if (!isPublicOrUserLabelSelected) {
      selectedLabelIds.clear();
    }
  }
  
  /**
   * Handles actions when the selection state of a label changes.
   */
  public class LabelSelectionHandler implements ClickHandler {

    public LabelSelectionHandler() {
    }

    @Override
    public void onClick(ClickEvent event) {
      // Determine what labels are selected and use them to filter the list
      // of models shown in the droplist in the open dialog box. If there are
      // no labels selected, clear the droplist.
      makeSelectedLabelIdsList();
      if (!selectedLabelIds.isEmpty()) {
        openDialog.populateDroplist();
      } else {
        openDialog.getDroplistPanel().getDroplist().clear();
      }
    } 
  }

  public List<Integer> getSelectedLabelIds() {
    return selectedLabelIds;
  }

  public void setSelectedLabelIds(List<Integer> selectedLabelIds) {
    this.selectedLabelIds = selectedLabelIds;
  }

}
