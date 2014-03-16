/**
 * <License>
 */
package edu.colorado.csdms.wmt.client.control;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;

/**
 * A class defining static methods that return URLs for accessing components
 * and models. Works in GWT development mode and in production mode, accessing
 * the WMT API.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class DataURL {

  // By same-origin policy, must use HTTPS consistently across all URLs.
  private static final String BASE_URL = "https://csdms.colorado.edu/";
  private static final String API_URL = BASE_URL + "wmt-server/";
  private static final String LOCAL_URL = GWT.getHostPageBaseURL();

  private static final String COMPONENT_LIST_URL = API_URL + "components/list";
  private static final String COMPONENT_SHOW_URL = API_URL + "components/show/";
  private static final String COMPONENT_FORMAT_URL = API_URL
      + "components/format/";

  private static final String MODEL_LIST_URL = API_URL + "models/list";
  private static final String MODEL_OPEN_URL = API_URL + "models/open/";
  private static final String MODEL_SHOW_URL = API_URL + "models/show/";
  private static final String MODEL_NEW_URL = API_URL + "models/new";
  private static final String MODEL_EDIT_URL = API_URL + "models/edit/";
  private static final String MODEL_DELETE_URL = API_URL + "models/delete/";
  private static final String MODEL_UPLOAD_URL = API_URL + "models/upload";

  private static final String RUN_NEW_URL = API_URL + "run/new";
  private static final String RUN_SHOW_URL = API_URL + "run/show";
  private static final String RUN_STAGE_URL = API_URL + "run/stage";
  private static final String RUN_LAUNCH_URL = API_URL + "run/launch";

  /**
   * A wrapper around Window.Location that returns the application URL in
   * either development or production mode.
   * 
   * @param data the DataManager object for the WMT session
   */
  public static String applicationURL(DataManager data) {
    return Window.Location.getHref();
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
      return COMPONENT_LIST_URL;
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
      return COMPONENT_SHOW_URL + componentId;
    }
  }

  /**
   * Returns the URL to format the parameters of a component, given its id.
   * 
   * @param data the DataManager object for the WMT session
   * @param componentId the id of the desired component
   */
  public static String formatComponent(DataManager data, String componentId) {
    if (data.isDevelopmentMode()) {
      return LOCAL_URL + "data/" + componentId + ".json";
    } else {
      return COMPONENT_FORMAT_URL + componentId;
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
      return MODEL_LIST_URL;
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
      return MODEL_OPEN_URL + modelId.toString();
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
      return MODEL_SHOW_URL + modelId.toString();
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
      return MODEL_NEW_URL;
    }
  }

  /**
   * Returns the URL for updating an existing model on the server, given its
   * id.
   * 
   * @param data the DataManager object for the WMT session
   * @param modelId the id of the model, an Integer set by the API
   */
  public static String editModel(DataManager data, Integer modelId) {
    if (data.isDevelopmentMode()) {
      return LOCAL_URL + "save/saved.json";
    } else {
      return MODEL_EDIT_URL + modelId.toString();
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
      return MODEL_DELETE_URL + modelId.toString();
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
      return MODEL_UPLOAD_URL;
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
      return RUN_NEW_URL;
    }
  }

  /**
   * Returns the URL for API page displaying the status of all current model
   * runs on the server.
   */
  public static String showModelRun() {
    return RUN_SHOW_URL;
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
      return RUN_STAGE_URL;
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
      return RUN_LAUNCH_URL;
    }
  }
}
