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
package edu.colorado.csdms.wmt.client.ui.dialog;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.colorado.csdms.wmt.client.Constants;
import edu.colorado.csdms.wmt.client.control.DataManager;
import edu.colorado.csdms.wmt.client.control.DataTransfer;
import edu.colorado.csdms.wmt.client.ui.panel.ChoicePanel;
import edu.colorado.csdms.wmt.client.ui.panel.DroplistPanel;
import edu.colorado.csdms.wmt.client.ui.panel.MetadataPanel;
import edu.colorado.csdms.wmt.client.ui.widgets.LabelsOpenModelMenu;

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
  private MetadataPanel metadataPanel;
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
    droplistPanel.getDroplist().addChangeHandler(new ChangeHandler() {
      @Override
      public void onChange(ChangeEvent event) {
        ListBox droplist = (ListBox) event.getSource();
        String modelName = droplist.getItemText(droplist.getSelectedIndex());
        metadataPanel.setOwner(OpenDialogBox.this.data.findModel(modelName).getOwner());
        metadataPanel.setDate(OpenDialogBox.this.data.findModel(modelName).getDate());
      }
    });

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
    
    HorizontalPanel row = new HorizontalPanel();
    row.setSpacing(5); // px
    row.add(droplistPanel);
    row.add(labelsButton);
    row.setCellVerticalAlignment(labelsButton,
        HasVerticalAlignment.ALIGN_MIDDLE);

    metadataPanel = new MetadataPanel();
    
    choicePanel = new ChoicePanel();
    choicePanel.getOkButton().setHTML(Constants.FA_OPEN + "Open");

    VerticalPanel contents = new VerticalPanel();
    contents.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
    contents.add(row);
    contents.add(metadataPanel);
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

  public MetadataPanel getMetadataPanel() {
    return metadataPanel;
  }

  public void setMetadataPanel(MetadataPanel metadataPanel) {
    this.metadataPanel = metadataPanel;
  }
}
