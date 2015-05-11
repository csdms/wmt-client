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
import com.google.gwt.user.client.Window;

import edu.colorado.csdms.wmt.client.Constants;
import edu.colorado.csdms.wmt.client.control.DataManager;
import edu.colorado.csdms.wmt.client.control.DataTransfer;
import edu.colorado.csdms.wmt.client.ui.dialog.SaveDialogBox;

/**
 * Handles click on the "OK" button in the save dialog that appears when the
 * "Save Model As..." button is clicked in the ModelActionPanel. Uses
 * {@link DataManager#serialize()} to serialize the model, then posts it to the
 * server with {@link DataTransfer#postModel(DataManager)}.
 */
public class SaveModelHandler implements ClickHandler {

  private DataManager data;
  private SaveDialogBox box;
  private String saveType;

  /**
   * Creates a new {@link SaveModelHandler}.
   * 
   * @param data the DataManager object for the WMT session
   * @param box the dialog box
   * @param saveType new model, edit existing model, or model save as
   */
  public SaveModelHandler(DataManager data, SaveDialogBox box, String saveType) {
    this.data = data;
    this.box = box;
    this.saveType = saveType;
  }

  @Override
  public void onClick(ClickEvent event) {

    // Retrieve the name the user gave the model.
    String modelName = box.getNamePanel().getField().getText();

    // If the name is null, or if the name is a dup, issue a message and return.
    if (modelName.isEmpty()) {
      String msg = "Please supply a name for this model, or select"
          + " the \"Cancel\" button.";
      Window.alert(msg);
      return;
    }
    if (saveType == Constants.MODELS_NEW_PATH
        || saveType == Constants.MODELS_SAVEAS_PATH) {
      String msg = "A model with the name \"" + modelName + "\" exists."
          + " Please choose a different name for this model.";
      for (int i = 0; i < data.modelList.getModels().length(); i++) {
        if (data.modelList.getModels().get(i).getName().matches(modelName)) {
          Window.alert(msg);
          return;
        }
      }
    }

    box.hide();

    // Set the model name in the DataManager.
    if (!data.getModel().getName().matches(modelName)) {
      data.getModel().setName(modelName);
      data.saveAttempts++;
    }

    // If this is a new model, reset the model id. (The "models/new" API call
    // will return a new id for the model.)
    if (saveType.matches(Constants.MODELS_NEW_PATH)) {
      data.getMetadata().setId(Constants.DEFAULT_MODEL_ID);
    }

    // Serialize the model from the GUI and post it to the server.
    data.serialize();
    DataTransfer.postModel(data, saveType);
  }
}
