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

import edu.colorado.csdms.wmt.client.Constants;
import edu.colorado.csdms.wmt.client.control.DataManager;
import edu.colorado.csdms.wmt.client.ui.dialog.QuestionDialogBox;

/**
 * Handles a click on the "Reset" button in the ParameterActionPanel. Calls
 * {@link DataManager#replaceModelComponent()} to replace the current model 
 * component with the default component, then displays its parameters in
 * the ParameterTable.
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
            data.getPerspective().getParameterTable().loadTable(componentId);
            data.updateModelSaveState(false);
          }
        });
    questionDialog.getChoicePanel().getCancelButton().addClickHandler(
        new DialogCancelHandler(questionDialog));
    questionDialog.center();
  }
}
