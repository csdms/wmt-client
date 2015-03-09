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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;

import edu.colorado.csdms.wmt.client.Constants;

/**
 * A class defining static methods that return URLs for accessing components and
 * models. Works in GWT development mode and in production mode, accessing the
 * WMT API.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class DataURL {

  // The base URL used in GWT development mode.
  public static final String LOCAL_URL = GWT.getHostPageBaseURL();
  
  /**
   * A wrapper around Window.Location that returns the application URL in either
   * development or production mode.
   * 
   * @param data the DataManager object for the WMT session
   */
  public static String applicationURL(DataManager data) {
    return Window.Location.getHref();
  }

  /**
   * Returns the new user account login URL provided by the API.
   * 
   * @param data the DataManager object for the WMT session
   */
  public static String newUserLogin(DataManager data) {
    if (data.isDevelopmentMode()) {
      return LOCAL_URL + "save/authenticate.json";
    } else {
      return data.config.getApiUrl() + Constants.NEW_USER_LOGIN_PATH;
    }
  }

  /**
   * Returns the account login URL provided by the API.
   * 
   * @param data the DataManager object for the WMT session
   */
  public static String login(DataManager data) {
    if (data.isDevelopmentMode()) {
      return LOCAL_URL + "save/authenticate.json";
    } else {
      return data.config.getApiUrl() + Constants.LOGIN_PATH;
    }
  }

  /**
   * Returns the account logout URL provided by the API.
   * 
   * @param data the DataManager object for the WMT session
   */
  public static String logout(DataManager data) {
    if (data.isDevelopmentMode()) {
      return LOCAL_URL + "save/authenticate.json";
    } else {
      return data.config.getApiUrl() + Constants.LOGOUT_PATH;
    }
  }

  /**
   * Returns the URL that gives the username of the user, if the user is
   * currently logged in.
   * 
   * @param data the DataManager object for the WMT session
   */
  public static String loginState(DataManager data) {
    if (data.isDevelopmentMode()) {
      return LOCAL_URL + "save/authenticate.json";
    } else {
      return data.config.getApiUrl() + Constants.USERNAME_PATH;
    }
  }

  /**
   * Returns the API URL for adding a new label to WMT.
   * 
   * @param data the DataManager object for the WMT session
   */
  public static String addLabel(DataManager data) {
    if (data.isDevelopmentMode()) {
      return LOCAL_URL + "save/labels.json";
    } else {
      return data.config.getApiUrl() + Constants.LABELS_NEW_PATH;
    }
  }

  /**
   * Returns the API URL for deleting a label from WMT.
   * 
   * @param data the DataManager object for the WMT session
   * @param labelId the id of the label to delete, an Integer
   */
  public static String deleteLabel(DataManager data, Integer labelId) {
    if (data.isDevelopmentMode()) {
      return LOCAL_URL + "save/labels.json";
    } else {
      return data.config.getApiUrl() + Constants.LABELS_DELETE_PATH
          + labelId.toString();
    }
  }

  /**
   * Returns the API URL for listing all labels belonging to the current user,
   * as well as all public labels, in WMT.
   * 
   * @param data the DataManager object for the WMT session
   */
  public static String listLabels(DataManager data) {
    if (data.isDevelopmentMode()) {
      return LOCAL_URL + "save/labels.json";
    } else {
      return data.config.getApiUrl() + Constants.LABELS_LIST_PATH;
    }
  }

  /**
   * Returns the API URL for attaching a new label to a model.
   * 
   * @param data the DataManager object for the WMT session
   */
  public static String addModelLabel(DataManager data) {
    if (data.isDevelopmentMode()) {
      return LOCAL_URL + "save/labels.json";
    } else {
      return data.config.getApiUrl() + Constants.LABELS_MODEL_ADD_PATH;
    }
  }

  /**
   * Returns the API URL for detaching a label from a model.
   * 
   * @param data the DataManager object for the WMT session
   */
  public static String removeModelLabel(DataManager data) {
    if (data.isDevelopmentMode()) {
      return LOCAL_URL + "save/labels.json";
    } else {
      return data.config.getApiUrl() + Constants.LABELS_MODEL_REMOVE_PATH;
    }
  }

  /**
   * Returns the API URL for finding what models use a given set of labels.
   * 
   * @param data the DataManager object for the WMT session
   */
  public static String queryModelLabel(DataManager data) {
    if (data.isDevelopmentMode()) {
      return LOCAL_URL + "save/labels.json";
    } else {
      return data.config.getApiUrl() + Constants.LABELS_MODEL_QUERY_PATH;
    }
  }

  /**
   * Returns the API URL for getting the labels used by a particular model.
   * 
   * @param data the DataManager object for the WMT session
   * @param modelId the id of the model, an Integer set by the API
   */
  public static String getModelLabel(DataManager data, Integer modelId) {
    if (data.isDevelopmentMode()) {
      return LOCAL_URL + "save/labels.json";
    } else {
      return data.config.getApiUrl() + Constants.LABELS_MODEL_GET_PATH
          + modelId.toString();
    }
  }

  /**
   * Returns the URL for the list of available components on the server.
   * 
   * @param data the DataManager object for the WMT session
   */
  public static String listComponents(DataManager data) {
    if (data.isDevelopmentMode()) {
      return LOCAL_URL + "data/components.json";
    } else {
      return data.config.getApiUrl() + Constants.COMPONENTS_LIST_PATH;
    }
  }

  /**
   * Returns the URL to access a specific component by its id.
   * 
   * @param data the DataManager object for the WMT session
   * @param componentId the id of the desired component
   */
  public static String showComponent(DataManager data, String componentId) {
    if (data.isDevelopmentMode()) {
      return LOCAL_URL + "data/" + componentId + ".json";
    } else {
      return data.config.getApiUrl() + Constants.COMPONENTS_SHOW_PATH 
          + componentId;
    }
  }

  /**
   * Returns the URL to format the parameters of a component, given its id.
   * 
   * @param data the DataManager object for the WMT session
   * @param componentId the id of the desired component
   * @param format the output format: HTML, text or JSON
   * @param useDefaults set to true to use the defaults for the component
   */
  public static String formatComponent(DataManager data, String componentId,
      String format, Boolean useDefaults) {
    if (data.isDevelopmentMode()) {
      return LOCAL_URL + "data/" + componentId + ".json";
    } else {
      String modelId = ((Integer) data.getMetadata().getId()).toString();
      if (useDefaults) {
        modelId = "0";
      }
      String url =
          data.config.getApiUrl() + "models/" + modelId + "/" + componentId
              + "/format?format=" + format;
      return url;
    }
  }

  /**
   * Returns the URL for the list of available models from the server.
   * 
   * @param data the DataManager object for the WMT session
   */
  public static String listModels(DataManager data) {
    if (data.isDevelopmentMode()) {
      return LOCAL_URL + "save/model_list.json";
    } else {
      return data.config.getApiUrl() + Constants.MODELS_LIST_PATH;
    }
  }

  /**
   * Returns the URL to access the metadata for a model, given its id.
   * 
   * @param data the DataManager object for the WMT session
   * @param modelId the id of the model, an Integer set by the API
   */
  public static String openModel(DataManager data, Integer modelId) {
    if (data.isDevelopmentMode()) {
      return LOCAL_URL + "save/open" + modelId.toString() + ".json";
    } else {
      return data.config.getApiUrl() + Constants.MODELS_OPEN_PATH 
          + modelId.toString();
    }
  }

  /**
   * Returns the URL to access the data (connections, parameters) for a model,
   * given its id.
   * 
   * @param data the DataManager object for the WMT session
   * @param modelId the id of the model, an Integer set by the API
   */
  public static String showModel(DataManager data, Integer modelId) {
    if (data.isDevelopmentMode()) {
      return LOCAL_URL + "save/show" + modelId.toString() + ".json";
    } else {
      return data.config.getApiUrl() + Constants.MODELS_SHOW_PATH 
          + modelId.toString();
    }
  }

  /**
   * Returns the URL for posting a new model to the server.
   * <p>
   * Note that this appears to work only in production mode.
   * 
   * @param data the DataManager object for the WMT session
   */
  public static String newModel(DataManager data) {
    if (data.isDevelopmentMode()) {
      return LOCAL_URL + "save/saved.json";
    } else {
      return data.config.getApiUrl() + Constants.MODELS_NEW_PATH;
    }
  }

  /**
   * Returns the URL for updating an existing model on the server, given its id.
   * 
   * @param data the DataManager object for the WMT session
   * @param modelId the id of the model, an Integer set by the API
   */
  public static String editModel(DataManager data, Integer modelId) {
    if (data.isDevelopmentMode()) {
      return LOCAL_URL + "save/saved.json";
    } else {
      return data.config.getApiUrl() + Constants.MODELS_EDIT_PATH 
          + modelId.toString();
    }
  }

  /**
   * Returns the URL for duplicating an existing model on the server, given its
   * id.
   * 
   * @param data the DataManager object for the WMT session
   * @param modelId the id of the model, an Integer set by the API
   */
  public static String saveasModel(DataManager data, Integer modelId) {
    if (data.isDevelopmentMode()) {
      return LOCAL_URL + "save/saved.json";
    } else {
      return data.config.getApiUrl() + Constants.MODELS_SAVEAS_PATH 
          + modelId.toString();
    }
  }

  /**
   * Returns the URL for deleting an existing model from the server, given its
   * id.
   * 
   * @param data the DataManager object for the WMT session
   * @param modelId the id of the model, an Integer set by the API
   */
  public static String deleteModel(DataManager data, Integer modelId) {
    if (data.isDevelopmentMode()) {
      return LOCAL_URL + "save/saved.json";
    } else {
      return data.config.getApiUrl() + Constants.MODELS_DELETE_PATH
          + modelId.toString();
    }
  }

  /**
   * Returns the URL for posting a file associated with a model to the server.
   * 
   * @param data the DataManager object for the WMT session
   */
  public static String uploadFile(DataManager data) {
    if (data.isDevelopmentMode()) {
      return LOCAL_URL + "save/saved.json";
    } else {
      return data.config.getApiUrl() + Constants.MODELS_UPLOAD_PATH;
    }
  }

  /**
   * Returns the URL used to create a new model run.
   * 
   * @param data the DataManager object for the WMT session
   */
  public static String newModelRun(DataManager data) {
    if (data.isDevelopmentMode()) {
      return LOCAL_URL + "save/saved.json";
    } else {
      return data.config.getApiUrl() + Constants.RUN_NEW_PATH;
    }
  }

  /**
   * Returns the URL for API page displaying the status of all current model
   * runs on the server.
   * 
   * @param data the DataManager object for the WMT session
   */
  public static String showModelRun(DataManager data) {
    return data.config.getApiUrl() + Constants.RUN_SHOW_PATH;
  }

  /**
   * Returns the URL used to stage a model run.
   * 
   * @param data the DataManager object for the WMT session
   */
  public static String stageModelRun(DataManager data) {
    if (data.isDevelopmentMode()) {
      return LOCAL_URL + "save/saved.json";
    } else {
      return data.config.getApiUrl() + Constants.RUN_STAGE_PATH;
    }
  }

  /**
   * Returns the URL used to launch a model run. Note that the URL uses HTTPS
   * because a username and password are being transferred.
   * 
   * @param data the DataManager object for the WMT session
   */
  public static String launchModelRun(DataManager data) {
    if (data.isDevelopmentMode()) {
      return LOCAL_URL + "save/saved.json";
    } else {
      return data.config.getApiUrl() + Constants.RUN_LAUNCH_PATH;
    }
  }
}
