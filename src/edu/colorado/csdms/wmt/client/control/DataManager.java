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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import com.google.gwt.dom.client.Style.Cursor;

import edu.colorado.csdms.wmt.client.data.ComponentJSO;
import edu.colorado.csdms.wmt.client.data.ConfigurationJSO;
import edu.colorado.csdms.wmt.client.data.LabelJSO;
import edu.colorado.csdms.wmt.client.data.ModelJSO;
import edu.colorado.csdms.wmt.client.data.ModelListJSO;
import edu.colorado.csdms.wmt.client.data.ModelMetadataJSO;
import edu.colorado.csdms.wmt.client.security.Security;
import edu.colorado.csdms.wmt.client.ui.ModelTree;
import edu.colorado.csdms.wmt.client.ui.Perspective;
import edu.colorado.csdms.wmt.client.ui.SignInScreen;
import edu.colorado.csdms.wmt.client.ui.cell.ComponentCell;

/**
 * A class for storing and sharing data, as well as the state of UI elements,
 * within WMT. This is the main controller for the application.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class DataManager {

  private Boolean developmentMode;

  // The initial sign-in screen. Either this or the Perspective are always
  // attached to the RootLayoutPanel of the application.
  private SignInScreen signInScreen;
  
  // Get the state of UI elements through the Perspective. 
  private Perspective perspective;

  private List<ComponentJSO> components; // "class" components
  private List<ComponentJSO> modelComponents; // "instance" components
  private ComponentCell componentShowingParameters;
  
  private ModelJSO model;
  private ModelMetadataJSO metadata;
  private Boolean modelIsSaved = false;
  private String modelString; // stringified JSON
  
  private String simulationId; // the uuid of a submitted run
  
  // Experiment with public members, for convenience.
  public Security security;
  public ConfigurationJSO config;
  public List<String> componentIdList;
  public HashMap<String, Integer> retryComponentLoad;
  public ModelListJSO modelList;
  public TreeMap<String, LabelJSO> modelLabels; // maintains sort
  public Integer saveAttempts = 0;

  /**
   * Initializes the DataManager object used in a WMT session.
   */
  public DataManager() {
    security = new Security(this);
    config = ConfigurationJSO.createObject().cast();
    componentIdList = new ArrayList<String>();
    retryComponentLoad = new HashMap<String, Integer>();
    components = new ArrayList<ComponentJSO>();
    modelComponents = new ArrayList<ComponentJSO>();
    modelLabels = new TreeMap<String, LabelJSO>();
    modelList = ModelListJSO.createObject().cast();
  }

  /**
   * Returns true if GWT is running in development mode; false for production
   * mode.
   */
  public Boolean isDevelopmentMode() {
    return developmentMode;
  }

  /**
   * Stores the GWT mode: true if in development, false if in production.
   * 
   * @param developmentMode a Boolean, set to true for development mode
   */
  public void isDevelopmentMode(Boolean developmentMode) {
    this.developmentMode = developmentMode;
  }

  /**
   * Shows the "wait" cursor.
   */
  public void showWaitCursor() {
    perspective.getElement().getStyle().setCursor(Cursor.WAIT);
  }
  
  /**
   * Shows the default cursor.
   */
  public void showDefaultCursor() {
    perspective.getElement().getStyle().setCursor(Cursor.DEFAULT);
  }

  public SignInScreen getSignInScreen() {
    return signInScreen;
  }

  public void setSignInScreen(SignInScreen signInScreen) {
    this.signInScreen = signInScreen;
  }

  /**
   * Returns the {@link Perspective} object used to organize the WMT views.
   */
  public Perspective getPerspective() {
    return perspective;
  }

  /**
   * Sets the {@link Perspective} object used to organize the WMT views.
   * 
   * @param perspective the perspective to set
   */
  public void setPerspective(Perspective perspective) {
    this.perspective = perspective;
  }

  /**
   * A convenience method that returns the {@link ComponentJSO} object
   * matching the given component id.
   * 
   * @param componentId the id of the desired component, a String
   */
  public ComponentJSO getComponent(String componentId) {
    Iterator<ComponentJSO> iter = components.iterator();
    while (iter.hasNext()) {
      ComponentJSO component = (ComponentJSO) iter.next();
      if (component.getId().matches(componentId)) {
        return component;
      }
    }
    return null;
  }

  /**
   * A convenience method that returns the {@link ComponentJSO} object at the
   * given position in the ArrayList of components.
   * 
   * @param index an offset into the ArrayList of components
   */
  public ComponentJSO getComponent(Integer index) {
    return components.get(index);
  }

  /**
   * A convenience method that adds a component to the ArrayList of components.
   * 
   * @param component the component to add, a ComponentJSO object
   */
  public void addComponent(ComponentJSO component) {
    this.components.add(component);
  }

  /**
   * Returns the <em>all</em> the components in the ArrayList of
   * {@link ComponentJSO} objects.
   */
  public List<ComponentJSO> getComponents() {
    return this.components;
  }

  /**
   * Sets an ArrayList of ComponentJSOs representing <em>all</em> the
   * components.
   * 
   * @param components all your components are belong to us
   */
  public void setComponents(List<ComponentJSO> components) {
    this.components = components;
  }

  /**
   * A convenience method that returns the {@link ComponentJSO} object
   * matching the given model component id, or null if no match is found.
   * <p>
   * Compare with {@link #getComponent(String)} for "class" components.
   * 
   * @param modelComponentId the id of the desired model component, a String
   */
  public ComponentJSO getModelComponent(String modelComponentId) {
    if (modelComponentId != null) {
      Iterator<ComponentJSO> iter = modelComponents.iterator();
      while (iter.hasNext()) {
        ComponentJSO component = (ComponentJSO) iter.next();
        if (component.getId().matches(modelComponentId)) {
          return component;
        }
      }
    }
    return null;
  }

  /**
   * A convenience method that returns the {@link ComponentJSO} object at the
   * given position in the ArrayList of model components.
   * <p>
   * Compare with {@link #getComponent(Integer)} for "class" components.
   * 
   * @param index an offset into the ArrayList of model components
   */
  public ComponentJSO getModelComponent(Integer index) {
    return modelComponents.get(index);
  }

  /**
   * A convenience method that adds a component to the ArrayList of model
   * components.
   * <p>
   * Compare with {@link #addComponent(ComponentJSO)} for "class" components.
   * 
   * @param modelComponent the model component to add, a {@link ComponentJSO}
   */
  public void addModelComponent(ComponentJSO modelComponent) {
    this.modelComponents.add(modelComponent);
  }

  /**
   * Replaces the item in DataManager's ArrayList of model components with a
   * copy of the input component. A regex on the id of the model component is
   * used to identify the component to replace.
   * 
   * @param component the replacement component, a {@link ComponentJSO}
   */
  public void replaceModelComponent(ComponentJSO component) {
    for (int i = 0; i < modelComponents.size(); i++) {
      if (modelComponents.get(i).getId().matches(component.getId())) {
        ComponentJSO copy = DataTransfer.copy(component);
        modelComponents.set(i, copy);
        return;
      }
    }
  }

  /**
   * Replaces <em>all</em> of the model components with copies of the (class)
   * components using {@link DataTransfer#copy()}.
   */
  public void resetModelComponents() {
    for (int i = 0; i < modelComponents.size(); i++) {
      ComponentJSO copy = DataTransfer.copy(components.get(i));
      modelComponents.set(i, copy);
    }
  }

  /**
   * Returns the <em>all</em> the model components in the ArrayList of
   * {@link ComponentJSO} objects.
   * <p>
   * Compare with {@link #getComponents()} for "class" components.
   */
  public List<ComponentJSO> getModelComponents() {
    return this.modelComponents;
  }

  /**
   * Sets an ArrayList of ComponentJSOs representing <em>all</em> the model
   * components.
   * 
   * @param components all your components are belong to us
   */
  public void setModelComponents(List<ComponentJSO> modelComponents) {
    this.modelComponents = modelComponents;
  }

  /**
   * Returns the model displayed in the {@link ModelTree}, a {@link ModelJSO}
   * object.
   */
  public ModelJSO getModel() {
    return model;
  }

  /**
   * Sets the model displayed in the {@link ModelTree}.
   * 
   * @param model the model to set, a ModelJSO object
   */
  public void setModel(ModelJSO model) {
    this.model = model;
  }

  /**
   * Returns the ModelMetadataJSO object used to store the metadata for a
   * model.
   */
  public ModelMetadataJSO getMetadata() {
    return metadata;
  }

  /**
   * Stores the ModelMetadataJSO object that holds a model's metadata.
   * 
   * @param metadata the model's ModelMetadataJSO object
   */
  public void setMetadata(ModelMetadataJSO metadata) {
    this.metadata = metadata;
  }

  /**
   * A convenience method that iterates through the list of available models to
   * locate the model that matches the input model name. The ModelListJSO for
   * the matched model is returned.
   * 
   * @param modelName the name of the model to locate
   */
  public ModelListJSO findModel(String modelName) {
    ModelListJSO jso = ModelListJSO.createObject().cast();
    for (int i = 0; i < modelList.getModels().length(); i++) {
      if (modelList.getModels().get(i).getName().equals(modelName)) {
        jso = modelList.getModels().get(i);
      }
    }
    return jso;
  }

  /**
   * A convenience method that iterates through the list of available models to
   * locate the model that has the input model id. The ModelListJSO for the
   * matched model is returned.
   * 
   * @param modelId the id of the model to locate
   */
  public ModelListJSO findModel(Integer modelId) {
    ModelListJSO jso = ModelListJSO.createObject().cast();
    for (int i = 0; i < modelList.getModels().length(); i++) {
      if (modelList.getModels().get(i).getId() == modelId) {
        jso = modelList.getModels().get(i);
      }
    }
    return jso;
  }

  /**
   * Returns the save state of the model. (True = saved)
   */
  public Boolean modelIsSaved() {
    return modelIsSaved;
  }

  /**
   * Stores the save state of the model. (True = saved)
   * 
   * @param modelIsSaved the model save state, a Boolean
   */
  public void modelIsSaved(Boolean modelIsSaved) {
    this.modelIsSaved = modelIsSaved;
  }

  /**
   * A helper method that updates the current model's save state and title.
   * 
   * @param state true if saved
   */
  public void updateModelSaveState(Boolean state) {
    modelIsSaved(state);
    perspective.setModelPanelTitle();
  }
  
  /**
   * Gets the stringified model JSON created by {@link #serialize()}.
   */
  public String getModelString() {
    return modelString;
  }

  /**
   * Stores the stringified model JSON created by {@link #serialize()}.
   * 
   * @param modelString the modelString to set
   */
  public void setModelString(String modelString) {
    this.modelString = modelString;
  }

  /**
   * Returns the uuid of a run sumbmitted to a server.
   */
  public String getSimulationId() {
    return simulationId;
  }

  /**
   * Stores the uuid of a run submitted to a server.
   * 
   * @param simulationId the uuid, a String
   */
  public void setSimulationId(String simulationId) {
    this.simulationId = simulationId;
  }

  /**
   * Returns the {@link ComponentCell} that's currently displaying its 
   * parameters in the Parameters view.
   */
  public ComponentCell getShowingParameters() {
    return componentShowingParameters;
  }

  /**
   * Sets the {@link ComponentCell} that's showing its parameters in the 
   * Parameters view.
   * 
   * @param componentShowingParameters
   */
  public void setShowingParameters(ComponentCell componentShowingParameters) {
    this.componentShowingParameters = componentShowingParameters;
    this.componentShowingParameters.addStyleDependentName("showingParameters");
  }

  /**
   * Translates the model displayed in WMT into a {@link ModelJSO} object,
   * which completely describes the state of the model. This object is
   * converted to a string (with {@link DataTransfer#stringify()}) which can
   * be uploaded to a server.
   */
  public void serialize() {
    ModelSerializer serializer = new ModelSerializer(this);
    serializer.serialize();
    modelString = DataTransfer.stringify(model);
  }

  /**
   * Extracts the information contained in the {@link ModelJSO} object
   * returned from opening a model (model menu > "Open Model...") and uses it
   * to populate the {@link ModelTree}.
   */
  public void deserialize() {
    perspective.setModelPanelTitle();
    ModelSerializer serializer = new ModelSerializer(this);
    serializer.deserialize();
  }
}
