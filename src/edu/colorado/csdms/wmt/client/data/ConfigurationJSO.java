package edu.colorado.csdms.wmt.client.data;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * A GWT JavaScript overlay (JSO) type that describes the JSON returned on a
 * HTTP GET call to the WMT client configuration file <b>war/config.json</b>. 
 * Declares JSNI methods to access attributes.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class ConfigurationJSO extends JavaScriptObject {

  // Overlay types always have protected, zero-arg constructors.
  protected ConfigurationJSO() {
  }
  
  /**
   * Gets the version string for the WMT client. This is a JSNI method.
   */
  public final native String getVersion() /*-{
		return this.version;
  }-*/;

  /**
   * Gets the release date string for the WMT client. This is a JSNI method.
   */
  public final native String getReleaseDate() /*-{
		return this.release_date;
  }-*/;

  /**
   * Gets the API URL used by the WMT client, a String. This is a JSNI method.
   */
  public final native String getApiUrl() /*-{
		return this.api_url;
  }-*/;
}
