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
 * server with {@link DataTransfer#postModel(DataManager, String)}.
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
