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
import edu.colorado.csdms.wmt.client.ui.menu.LabelsOpenModelMenu;
import edu.colorado.csdms.wmt.client.ui.panel.ChoicePanel;
import edu.colorado.csdms.wmt.client.ui.panel.DroplistPanel;
import edu.colorado.csdms.wmt.client.ui.panel.MetadataPanel;

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
    this.setText("Open Saved Tool...");
    this.data = data;
    data.getPerspective().setOpenDialogBox(this);
    
    droplistPanel = new DroplistPanel();
    droplistPanel.getDroplist().addChangeHandler(new ChangeHandler() {
      @Override
      public void onChange(ChangeEvent event) {
        ListBox droplist = (ListBox) event.getSource();
        populateMetadataPanel(droplist.getItemText(droplist.getSelectedIndex()));
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
  
  /**
   * A helper that loads information into the MetadataPanel of the
   * {@link OpenDialogBox}.
   * 
   * @param modelName the name of the saved model displayed in the dialog box.
   */
  public void populateMetadataPanel(String modelName) {
    metadataPanel.setOwner(data.findModel(modelName).getOwner());
    metadataPanel.setDate(data.findModel(modelName).getDate());
    metadataPanel.setId(data.findModel(modelName).getId());
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
