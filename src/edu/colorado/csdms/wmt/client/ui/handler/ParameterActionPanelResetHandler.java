package edu.colorado.csdms.wmt.client.ui.handler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import edu.colorado.csdms.wmt.client.Constants;
import edu.colorado.csdms.wmt.client.control.DataManager;
import edu.colorado.csdms.wmt.client.ui.cell.ComponentCell;
import edu.colorado.csdms.wmt.client.ui.dialog.QuestionDialogBox;

/**
 * Handles a click on the "Reset" button in the ParameterActionPanel. Calls
 * {@link DataManager#replaceModelComponent(edu.colorado.csdms.wmt.client.data.ComponentJSO)}
 * to replace the current model component with the default component, then
 * displays its parameters in the ParameterTable.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class ParameterActionPanelResetHandler implements ClickHandler {

  private DataManager data;
  private String componentId;

  /**
   * Creates a new instance of {@link ParameterActionPanelResetHandler}.
   * 
   * @param data the DataManager object for the WMT session
   * @param componentId the id of the component to be replaced
   */
  public ParameterActionPanelResetHandler(DataManager data, String componentId) {
    this.data = data;
    this.componentId = componentId;
  }

  @Override
  public void onClick(ClickEvent event) {
    final QuestionDialogBox questionDialog =
        new QuestionDialogBox(Constants.QUESTION_PARAMETER_RESET);
    questionDialog.getChoicePanel().getOkButton().addClickHandler(
        new ClickHandler() {
          @Override
          public void onClick(ClickEvent event) {
            questionDialog.hide();
            data.replaceModelComponent(data.getComponent(componentId));
            data.getPerspective().getParameterTable().clearTable();
            ComponentCell cell = data.getShowingParameters();
            data.getPerspective().getParameterTable().loadTable(cell);
            data.updateModelSaveState(false);
          }
        });
    questionDialog.getChoicePanel().getCancelButton().addClickHandler(
        new DialogCancelHandler(questionDialog));
    questionDialog.center();
  }
}
