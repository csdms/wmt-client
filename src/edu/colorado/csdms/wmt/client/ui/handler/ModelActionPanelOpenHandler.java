package edu.colorado.csdms.wmt.client.ui.handler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;

import edu.colorado.csdms.wmt.client.control.DataManager;
import edu.colorado.csdms.wmt.client.ui.dialog.OpenDialogBox;

/**
 * Handles click on the "Open Model..." button in the ModelActionPanel. Displays
 * an instance of {@link OpenDialogBox} to prompt the user for a model to open.
 * Events are sent to {@link OpenModelHandler} (on clicking "OK" button or
 * hitting <code>Enter</code> key) and {@link DialogCancelHandler} (on clicking
 * "Cancel" or hitting <code>Esc</code> key).
 */
public class ModelActionPanelOpenHandler implements ClickHandler {

  private DataManager data;
  private OpenDialogBox openDialog;

  /**
   * Creates a new instance of {@link ModelActionPanelOpenHandler}.
   * 
   * @param data the DataManager object for the WMT session
   */
  public ModelActionPanelOpenHandler(DataManager data) {
    this.data = data;
  }

  @Override
  public void onClick(ClickEvent event) {

    // Make a new open dialog and populate its droplist with the models owned 
    // by the user.
    openDialog = new OpenDialogBox(data);
    openDialog.populateDroplist();
    
    // Define handlers.
    final OpenModelHandler openHandler = new OpenModelHandler(data, openDialog);
    final DialogCancelHandler cancelHandler =
        new DialogCancelHandler(openDialog);

    // Apply handlers to OK and Cancel buttons.
    openDialog.getChoicePanel().getOkButton().addClickHandler(openHandler);
    openDialog.getChoicePanel().getCancelButton()
        .addClickHandler(cancelHandler);

    // Also apply handlers to "Enter" and "Esc" keys.    
    openDialog.addDomHandler(new ModalKeyHandler(openHandler, cancelHandler),
        KeyDownEvent.getType());

    openDialog.center();

    // Give the droplist focus.
    openDialog.getDroplistPanel().getDroplist().setFocus(true);
  }
}
