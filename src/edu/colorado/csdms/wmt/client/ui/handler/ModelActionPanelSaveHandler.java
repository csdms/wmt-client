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
package edu.colorado.csdms.wmt.client.ui.handler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.user.client.Window;

import edu.colorado.csdms.wmt.client.Constants;
import edu.colorado.csdms.wmt.client.control.DataManager;
import edu.colorado.csdms.wmt.client.control.DataTransfer;
import edu.colorado.csdms.wmt.client.ui.widgets.SaveDialogBox;

/**
 * Handles click on the "Save" or "Save As..." buttons in the ModelActionPanel.
 * Saves a not-previously-saved model or a new model displayed in WMT to the
 * server with a call to {@link DataTransfer#postModel(DataManager)}.
 */
public class ModelActionPanelSaveHandler implements ClickHandler {

  private DataManager data;
  private Boolean isSaveAs;
  private SaveDialogBox saveDialog;

  public ModelActionPanelSaveHandler(DataManager data) {
    this(data, false);
  }

  public ModelActionPanelSaveHandler(DataManager data, Boolean isSaveAs) {
    this.data = data;
    this.isSaveAs = isSaveAs;
  }

  @Override
  public void onClick(ClickEvent event) {

    // Hide the MoreActionsMenu.
    data.getPerspective().getActionButtonPanel().getMoreMenu().hide();

    // If this is a new model, show the save dialog box and return immediately.
    if (data.getMetadata().getId() == Constants.DEFAULT_MODEL_ID) {
        showSaveDialogBox(Constants.MODELS_NEW_PATH);
        return;
    }
    
    // If this is the "Save As" action on an existing model, or if this is an 
    // attempt to save a public model that the user doesn't own, then show the 
    // save dialog box. If this is a save of an existing model that the user 
    // owns, skip the dialog and save the model to the server. If this is the
    // "Save" action on a model that is already saved, do nothing.
    if (isSaveAs) {
      showSaveDialogBox(Constants.MODELS_SAVEAS_PATH);
    } else {
      if (!data.modelIsSaved()) {

        // Don't allow a user to save a model that doesn't belong to them.
        // Give them the option to save a copy with their username.
        if (data.getMetadata().getOwner() != data.security.getWmtUsername()) {
          String msg =
              "This model cannot be saved because the current user is not"
                  + " the model owner. Would you like to save a copy of"
                  + " this model with the current user as the owner?";
          Boolean saveCopy = Window.confirm(msg);
          if (saveCopy) {
            showSaveDialogBox(Constants.MODELS_NEW_PATH);
          }
        } else {
          data.serialize();
          DataTransfer.postModel(data, Constants.MODELS_EDIT_PATH);
        }

      }
    }
  }

  /**
   * Pops up an instance of {@link SaveDialogBox} to prompt the user to save the
   * model. Events are sent to {@link SaveModelHandler} and
   * {@link DialogCancelHandler}.
   * 
   * @param saveType new model, edit existing model, or model save as
   */
  private void showSaveDialogBox(String saveType) {

    // If the model has been saved previously, append "copy" to the name.
    String modelName = data.getModel().getName();
    if (data.getMetadata().getId() != Constants.DEFAULT_MODEL_ID) {
      modelName += " copy";
    }
    
    saveDialog = new SaveDialogBox(data, modelName);
    saveDialog.getNamePanel().setTitle(
        "Enter a name for the model. No file extension is needed.");
    
    // Define handlers.
    final SaveModelHandler saveHandler =
        new SaveModelHandler(data, saveDialog, saveType);
    final DialogCancelHandler cancelHandler =
        new DialogCancelHandler(saveDialog);

    // Apply handlers to OK and Cancel buttons.
    saveDialog.getChoicePanel().getOkButton().addClickHandler(saveHandler);
    saveDialog.getChoicePanel().getCancelButton()
        .addClickHandler(cancelHandler);

    // Also apply handlers to "Enter" and "Esc" keys.    
    saveDialog.addDomHandler(new ModalKeyHandler(saveHandler, cancelHandler),
        KeyDownEvent.getType());
        
    saveDialog.center();
    saveDialog.getNamePanel().getField().setFocus(true);
  }
}
