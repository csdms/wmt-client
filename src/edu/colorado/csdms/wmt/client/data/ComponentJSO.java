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
package edu.colorado.csdms.wmt.client.data;

import java.util.Vector;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

/**
 * A GWT JavaScript overlay (JSO) type that describes a WMT component model,
 * with "id", "name", "url", "provides" and "uses" attributes. Declares JSNI
 * methods to access these attributes from a JSON and modify them in memory.
 * 
 * @see <a
 *      href="http://www.gwtproject.org/doc/latest/DevGuideCodingBasicsOverlay.html">http://www.gwtproject.org/doc/latest/DevGuideCodingBasicsOverlay.html</a>
 * @author Mark Piper (mark.piper@colorado.edu)
 * 
 */
public class ComponentJSO extends JavaScriptObject {

  // Overlay types have protected, no-arg, constructors.
  protected ComponentJSO() {
  }

  /**
   * A JSNI method to access the "id" attribute of a WMT component model. This
   * attribute is always present, and is a string.
   */
  public final native String getId() /*-{
		return this.id;
  }-*/;

  /**
   * A JSNI method to access the "class" attribute of a WMT component model, a
   * string.
   */
  public final native String getComponentClass() /*-{
		return (typeof this["class"] != 'undefined') ? this["class"] : null;
  }-*/;

  /**
   * A JSNI method to access the "name" attribute of a WMT component model.
   * This attribute is always present, and is a string.
   */
  public final native String getName() /*-{
		return this.name;
  }-*/;

  /**
   * A JSNI method to access the "url" attribute of a WMT component model.
   * This attribute is always present, and is a string.
   */
  public final native String getURL() /*-{
		return this.url;
  }-*/;

  /**
   * A JSNI method to get the "summary" attribute of a component.
   */
  public final native String getSummary() /*-{
		return (typeof this.summary != 'undefined') ? this.summary : null;
  }-*/;

  /**
   * A JSNI method to get the "author" attribute of a component.
   */
  public final native String getAuthor() /*-{
		return (typeof this.author != 'undefined') ? this.author : null;
  }-*/;

  /**
   * A JSNI method to get the "email" attribute of a component.
   */
  public final native String getEmail() /*-{
		return (typeof this.email != 'undefined') ? this.email : null;
  }-*/;

  /**
   * A JSNI method to get the "version" attribute of a component.
   */
  public final native String getVersion() /*-{
		return (typeof this.version != 'undefined') ? this.version : null;
  }-*/;

  /**
   * A JSNI method to get the "license" attribute of a component.
   */
  public final native String getLicense() /*-{
		return (typeof this.license != 'undefined') ? this.license : null;
  }-*/;

  /**
   * A JSNI method to get the "doi" attribute of a component.
   */
  public final native String getDoi() /*-{
		return (typeof this.doi != 'undefined') ? this.doi : null;
  }-*/;

  /**
   * A convenience method that returns the number of CCA provides ports a
   * component has.
   */
  public final native int nProvidesPorts() /*-{
    var n = 0;
		if (typeof this.provides != 'undefined') {
			n = Object.keys(this.provides).length;
		} 
    return n;
  }-*/;

  /**
   * A JSNI method to access the "provides" attribute of a WMT component
   * model. This attribute is a JsArray of PortJSO objects. It's always
   * present, but it may be empty.
   */  
  public final native JsArray<PortJSO> getProvidesPorts() /*-{
		return this.provides;
  }-*/;

  /**
   * A convenience method that returns the number of CCA uses ports a
   * component has.
   */
  public final native int nUsesPorts() /*-{
    var n = 0;
		if (typeof this.uses != 'undefined') {
			n = Object.keys(this.uses).length;
		}
    return n;
  }-*/;

  /**
   * A JSNI method to access the "uses" attribute of a WMT component model.
   * This attribute is a JsArray of PortJSO objects. It's always present, but
   * it may be empty.
   */
  public final native JsArray<PortJSO> getUsesPorts() /*-{
		return this.uses;
  }-*/;

  /**
   * Counts the parameters of a component.
   * 
   * @return the number of parameters as an int
   */
  public final native int nParameters() /*-{
    var n = 0;
		if (typeof this.parameters != 'undefined') {
			n = Object.keys(this.parameters).length;
		}
    return n;
  }-*/;

  /**
   * A JSNI method that returns the JsArray of component parameters.
   */
  public final native JsArray<ParameterJSO> getParameters() /*-{
		return this.parameters;
  }-*/;

  /**
   * A JSNI method that returns a ParameterJSO by its "key" attribute.
   * 
   * @param key The key of the desired parameter, a String.
   */
  public final native ParameterJSO getParameter(String key) /*-{
		var parameterArray = this.parameters;
		for (var i = 0; i < parameterArray.length; i++) {
			parameter = parameterArray[i]
			if (parameter.key === key) {
				return parameter;
			}
		}
		return null;
  }-*/;

  /**
   * A non-JSNI method for stringifying the attributes of a Component. Must be
   * final.
   */
  public final Vector<String> toStringVector() {

    Vector<String> retVal = new Vector<String>();
    retVal.add("id: " + getId());
    retVal.add("name: " + getName());
    retVal.add("url: " + getURL());
    for (int i = 0; i < getProvidesPorts().length(); i++) {
      retVal.add("provides: "
          + getProvidesPorts().get(i).toStringVector().toString());
    }
    for (int i = 0; i < getUsesPorts().length(); i++) {
      retVal.add("uses: " + getUsesPorts().get(i).toStringVector().toString());
    }
    return retVal;
  }
}
