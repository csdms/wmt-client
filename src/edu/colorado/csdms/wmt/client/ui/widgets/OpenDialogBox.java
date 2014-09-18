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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.colorado.csdms.wmt.client.Constants;
import edu.colorado.csdms.wmt.client.control.DataManager;
import edu.colorado.csdms.wmt.client.control.DataTransfer;

/**
 * A customized DialogBox with a droplist for selecting a model and a "Labels"
 * button for selecting labels, used to filter the list of models displayed in
 * the droplist. "OK" and "Cancel" buttons are shown on the bottom of the
 * dialog.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class OpenDialogBox extends DialogBox {

  private DataManager data;
  private DroplistPanel droplistPanel;
  private ChoicePanel choicePanel;
  private LabelsOpenModelMenu labelsMenu;
  
  /**
   * Makes an {@link OpenDialogBox}.
   * 
   * @param data the DataManager object for the WMT session
   */
  public OpenDialogBox(DataManager data) {
    
    super(false); // autohide
    this.setModal(true);
    this.setStyleName("wmt-DialogBox");
    this.setText("Open Model...");
    this.data = data;
    data.getPerspective().setOpenDialogBox(this);
    
    droplistPanel = new DroplistPanel();

    final Button labelsButton = new Button(Constants.FA_TAGS + "Labels");
    labelsButton.setStyleName("wmt-Button");
    labelsMenu = new LabelsOpenModelMenu(data, this);
    labelsButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        labelsMenu.setPopupPositionAndShow(new PositionCallback() {
          final Integer x = labelsButton.getElement().getAbsoluteRight();
          final Integer y = labelsButton.getAbsoluteTop();
          @Override
          public void setPosition(int offsetWidth, int offsetHeight) {
            labelsMenu.setPopupPosition(x, y);
          }
        });
      }
    });
    
    HorizontalPanel row1 = new HorizontalPanel();
    row1.setSpacing(5); // px
    row1.add(droplistPanel);
    row1.add(labelsButton);
    row1.setCellVerticalAlignment(labelsButton,
        HasVerticalAlignment.ALIGN_MIDDLE);

    Grid descriptionGrid = new Grid(2, 2);
    descriptionGrid.setHTML(0, 0, "owner:");
    descriptionGrid.setHTML(1, 0, "creation date:");
    descriptionGrid.setHTML(0, 1, "mark.piper@colorado.edu");
    descriptionGrid.setHTML(1, 1, "2014-05-16T10:06:07");
    descriptionGrid.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
    descriptionGrid.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);    
    
    HorizontalPanel row2 = new HorizontalPanel();
    row2.setSpacing(5); // px
    row2.setWidth("100%");
    row2.add(descriptionGrid);
    row2.setCellHorizontalAlignment(descriptionGrid, HasHorizontalAlignment.ALIGN_CENTER);
    
    choicePanel = new ChoicePanel();
    choicePanel.getOkButton().setHTML(Constants.FA_OPEN + "Open");

    VerticalPanel contents = new VerticalPanel();
    contents.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
    contents.add(row1);
    contents.add(row2);
    contents.add(choicePanel);

    this.setWidget(contents);
  }

  /**
   * A helper that loads the {@link OpenDialogBox} droplist with model names.
   * By default, only models that are owned by the current user are displayed.
   */
  public void populateDroplist() {
    DataTransfer.queryModelLabels(data, labelsMenu.getSelectedLabelIds());
  }
  
  public DroplistPanel getDroplistPanel() {
    return droplistPanel;
  }

  public void setDroplistPanel(DroplistPanel droplistPanel) {
    this.droplistPanel = droplistPanel;
  }

  public ChoicePanel getChoicePanel() {
    return choicePanel;
  }

  public void setChoicePanel(ChoicePanel choicePanel) {
    this.choicePanel = choicePanel;
  }

  public LabelsOpenModelMenu getLabelsMenu() {
    return labelsMenu;
  }

  public void setLabelsMenu(LabelsOpenModelMenu labelsMenu) {
    this.labelsMenu = labelsMenu;
  }
}
