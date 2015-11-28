package edu.colorado.csdms.wmt.client.data;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

/**
 * A GWT JavaScript overlay (JSO) type that describes the JSON returned on a
 * HTTP GET call to <a
 * href="http://csdms.colorado.edu/wmt/models/list">models/list</a>. Declares
 * JSNI methods to access attributes.
 * <p>
 * For more on GWT JSO types, see <a href=
 * "http://www.gwtproject.org/doc/latest/DevGuideCodingBasicsOverlay.html"
 * >DevGuideCodingBasicsOverlay</a> and <a
 * href="http://www.gwtproject.org/doc/latest/DevGuideCodingBasicsJSNI.html"
 * >DevGuideCodingBasicsJSNI</a>.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class ModelListJSO extends JavaScriptObject {

  // Overlay types always have protected, zero-arg constructors.
  protected ModelListJSO() {
  }

  /**
   * Gets the name of the model, a String. This is a JSNI method.
   */
  public final native String getName() /*-{
		return this.name;
  }-*/;

  /**
   * A JSNI method to get the id of the model, an int used to uniquely
   * identify it in the database. The user can't modify this id -- it's set by
   * the API. 
   */
  public final native int getId() /*-{
		return this.id;
  }-*/;    

  /**
   * Gets the owner of the model, a String. If an owner hasn't been set, null 
   * is returned. This is a JSNI method.
   */
  public final native String getOwner() /*-{
		return (typeof this.owner == 'undefined') ? null : this.owner;
  }-*/;

  /**
   * Gets the ISO 8601 date of the model, a String. If a date hasn't been set, 
   * null is returned. This is a JSNI method.
   */
  public final native String getDate() /*-{
    return (typeof this.date == 'undefined') ? null : this.date;
  }-*/;

  /**
   * Gets the JsArray of models. This is a JSNI method.
   */
  public final native JsArray<ModelListJSO> getModels() /*-{
		return this;
  }-*/;  
}
