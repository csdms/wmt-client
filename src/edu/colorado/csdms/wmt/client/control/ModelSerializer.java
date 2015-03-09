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
package edu.colorado.csdms.wmt.client.control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.TreeItem;

import edu.colorado.csdms.wmt.client.data.ComponentJSO;
import edu.colorado.csdms.wmt.client.data.ModelComponentConnectionsJSO;
import edu.colorado.csdms.wmt.client.data.ModelComponentJSO;
import edu.colorado.csdms.wmt.client.data.ModelComponentParametersJSO;
import edu.colorado.csdms.wmt.client.data.ModelJSO;
import edu.colorado.csdms.wmt.client.ui.ModelTree;
import edu.colorado.csdms.wmt.client.ui.handler.ComponentSelectionCommand;
import edu.colorado.csdms.wmt.client.ui.widgets.ComponentCell;

/**
 * Serializes the model built in a WMT session.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class ModelSerializer {

  private DataManager data;
  private ModelTree modelTree;
  private TreeItem treeItem;
  private Integer nModelComponents;
  private List<ModelComponentJSO> modelComponents;

  /**
   * Instatiates a ModelSerializer and stores a reference to the
   * {@link DataManager} and the {@link ModelTree}.
   * 
   * @param data the DataManager for the WMT session.
   */
  public ModelSerializer(DataManager data) {
    this.data = data;
    this.modelTree = this.data.getPerspective().getModelTree();
    nModelComponents = this.data.getModel().nComponents(); // dev vs. prod mode!
    modelComponents = new ArrayList<ModelComponentJSO>();
  }

  /**
   * Translates the model displayed in WMT into a {@link ModelJSO} object, which
   * completely describes the state of the model, and stores it in the
   * {@link DataManager}.
   */
  public void serialize() {

    // Create a JsArray of ModelComponentJSO objects for the components that
    // make up the model.
    @SuppressWarnings("unchecked")
    JsArray<ModelComponentJSO> componentsArray =
        (JsArray<ModelComponentJSO>) ModelComponentJSO.createArray();

    // Iterate through the leaves of the ModelTree. For each leaf, create a
    // ModelComponentJSO object to hold the component, its ports and its
    // parameters. When loaded with information from the GUI, push the
    // ModelComponentJSO into the components JsArray and move to the next leaf.
    Iterator<TreeItem> iter = modelTree.treeItemIterator();
    while (iter.hasNext()) {

      treeItem = (TreeItem) iter.next();
      ComponentCell cell = (ComponentCell) treeItem.getWidget();

      // Skip aliased components and empty components.
      if (cell.isAlias()) {
        continue;
      }
      if (cell.getComponentId() == null) {
        continue;
      }

      // Serialize this model component.
      ComponentJSO componentJso = data.getModelComponent(cell.getComponentId());
      ModelComponentJSO modelComponent = serializeComponent(componentJso);
      if (cell.getPortId().matches("driver")) {
        modelComponent.setDriver();
      }

      // Push the component into the components JsArray.
      componentsArray.push(modelComponent);
    }

    // Set the component JsArray into the model.
    data.getModel().setComponents(componentsArray);
  }

  /**
   * Serializes a single model component.
   * 
   * @param componentJso a {@link ComponentJSO} representing the model component
   * @return a {@link ModelComponentJSO} representing the model component
   */
  public ModelComponentJSO serializeComponent(ComponentJSO componentJso) {

    // Make a new ModelComponentJSO object to hold the component.
    ModelComponentJSO modelComponent =
        (ModelComponentJSO) ModelComponentJSO.createObject();

    modelComponent.setId(componentJso.getId());
    modelComponent.setClassName(componentJso.getId()); // XXX Check this.
    modelComponent.setParameters(serializeParameters(componentJso));
    modelComponent.setConnections(serializeConnections(componentJso));

    return modelComponent;
  }

  /**
   * Serializes the parameters of a model component.
   * 
   * @param componentJso a {@link ComponentJSO} representing the model component
   * @return a {@link ModelComponentParametersJSO} representing the parameters
   */
  public ModelComponentParametersJSO serializeParameters(
      ComponentJSO componentJso) {

    // Make a new ModelComponentParametersJSO object to hold the parameters.
    ModelComponentParametersJSO modelComponentParameters =
        (ModelComponentParametersJSO) ModelComponentParametersJSO
            .createObject();

    // Add the parameters and their values as key-value pairs.
    Integer nParameters = componentJso.getParameters().length();
    if (nParameters > 0) {
      for (int i = 0; i < nParameters; i++) {
        String key = componentJso.getParameters().get(i).getKey();
        if (key.matches("separator")) {
          continue;
        }
        String value =
            componentJso.getParameters().get(i).getValue().getDefault();
        modelComponentParameters.addParameter(key, value);
      }
    }
    return modelComponentParameters;
  }

  /**
   * Serializes the ports/connections of the model component.
   * 
   * @param componentJso a {@link ComponentJSO} representing the model component
   * @return a {@link ModelComponentConnectionsJSO} representing the connections
   */
  public ModelComponentConnectionsJSO serializeConnections(
      ComponentJSO componentJso) {

    // Make a new ModelComponentConnectionsJSO object to hold the connections.
    ModelComponentConnectionsJSO modelComponentConnections =
        (ModelComponentConnectionsJSO) ModelComponentConnectionsJSO
            .createObject();

    // Add the ports for the model component.
    for (int i = 0; i < treeItem.getChildCount(); i++) {
      TreeItem child = treeItem.getChild(i);
      ComponentCell childCell = (ComponentCell) child.getWidget();
      String portId = childCell.getPortId();
      String componentId = childCell.getComponentId();
      modelComponentConnections.addConnection(portId, componentId);
    }
    return modelComponentConnections;
  }

  /**
   * Extracts the information contained in the {@link ModelJSO} object returned
   * from opening a model (model menu > "Open Model...") and uses it to populate
   * the {@link ModelTree}.
   */
  public void deserialize() {

    // Load the model components list. (It's a convenience.)
    for (int i = 0; i < nModelComponents; i++) {
      modelComponents.add(data.getModel().getComponents().get(i));
    }

    // Locate and deserialize the driver.
    ModelComponentJSO driver = deserializeComponent("driver", null);

    // Deserialize the components connected to the driver.
    matchConnections(driver);

    // Loop to fill remaining open ports in ModelTree, checking the connections
    // of all the components in the model.
    Iterator<ModelComponentJSO> iter = modelComponents.iterator();
    while (iter.hasNext()) {
      ModelComponentJSO modelComponent = (ModelComponentJSO) iter.next();
      matchConnections(modelComponent);
    }
  }

  /**
   * Deserializes a single model component.
   * 
   * @param componentId the id of the model component
   * @param cell the {@link ComponentCell} in which to place the deserialized
   *          component
   */
  private ModelComponentJSO deserializeComponent(String componentId,
      ComponentCell cell) {

    // Locate the model component.
    ModelComponentJSO modelComponent = getComponent(componentId);

    // Set a new component in the open ModelTree node.
    TreeItem node = null;
    if (modelComponent.isDriver()) {
      node = modelTree.getItem(0);
      cell = modelTree.getDriverComponentCell();
      GWT.log("Model driver: " + modelComponent.getClassName());
    } else {
      node = cell.getEnclosingTreeItem();
    }
    ComponentSelectionCommand cmd = 
        new ComponentSelectionCommand(data, cell, modelComponent.getId());
    cmd.execute(true); // use setComponent
    node.setState(true);
    
    // Load the component's parameters.
    deserializeParameters(modelComponent);
    
    // If this is the driver, show its parameters in the ParameterTable. Also
    // display the name of the model.
    if (modelComponent.isDriver()) {
      data.getPerspective().getParameterTable().loadTable(
          modelComponent.getId());
      data.getPerspective().setModelPanelTitle();
    }

    return modelComponent;
  }

  /**
   * Deserializes the all the parameters of a single incoming model component
   * and stores them with the model in the {@link DataManager}.
   * 
   * @param modelComponent a model component with parameters
   */
  private void deserializeParameters(ModelComponentJSO modelComponent) {
    if (modelComponent.nParameters() > 0) {
      for (int j = 0; j < modelComponent.nParameters(); j++) {
        String key = modelComponent.getParameters().getKeys().get(j);
        String value = modelComponent.getParameters().getValues().get(j);
        data.getModelComponent(modelComponent.getId()).getParameter(key)
            .getValue().setDefault(value);
      }
    }
  }

  /**
   * A worker that locates and returns the requested {@link ModelComponentJSO}
   * object. Returns null if the component is not found.
   * <p>
   * If "driver" is passed as the componentId, the driver component is located
   * and returned.
   * 
   * @param componentId the id of a model component
   */
  private ModelComponentJSO getComponent(String componentId) {
    Iterator<ModelComponentJSO> iter = modelComponents.iterator();
    while (iter.hasNext()) {
      ModelComponentJSO modelComponent = (ModelComponentJSO) iter.next();
      if ((componentId.matches("driver") && modelComponent.isDriver())
          || modelComponent.getId().matches(componentId)) {
        return modelComponent;
      }
    }
    return null;
  }

  /**
   * Deserializes the listed connections of a model component.
   * 
   * @param modelComponent a {@link ModelComponentJSO} object.
   */
  private void matchConnections(ModelComponentJSO modelComponent) {

    // If the model component has no connections, exit.
    if (modelComponent.nConnections() == 0) {
      return;
    }

    // Get a list of open ComponentCells in the ModelTree. For the driver,
    // consider only its immediate children.
    List<ComponentCell> openCells = new ArrayList<ComponentCell>();
    if (modelComponent.isDriver()) {
      openCells = modelTree.findOpenComponentCells(modelTree.getItem(0));
    } else {
      openCells = modelTree.findOpenComponentCells();
    }

    // Find matches for the open ComponentCells with components supplied by the
    // model.
    for (int i = 0; i < modelComponent.nConnections(); i++) {

      // Get the "portId @ componentId" of the connection.
      String portId = modelComponent.getConnections().getPortIds().get(i);
      String componentId =
          modelComponent.getConnections().getConnection(portId);
      GWT.log(modelComponent.getId() + ": " + portId + "@" + componentId);
      if (componentId == null) {
        continue;
      }

      // Match the connection with an open ComponentCell through its port.
      for (int j = 0; j < openCells.size(); j++) {
        ComponentCell cell = openCells.get(j);
        if (cell.getPortId().matches(portId)) {
          deserializeComponent(componentId, cell);
        }
      }
    }
  }
}
