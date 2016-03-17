package edu.colorado.csdms.wmt.client.data;

import java.util.Vector;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * A GWT JavaScript overlay (JSO) type that describes a parameter for a WMT
 * component model, with "key", "name", "description" and "value" attributes.
 * Declares JSNI methods to access these attributes from a JSON and modify
 * them in memory.
 * <p>
 * For more on GWT JSO types, see <a href=
 * "http://www.gwtproject.org/doc/latest/DevGuideCodingBasicsOverlay.html"
 * >DevGuideCodingBasicsOverlay</a> and <a
 * href="http://www.gwtproject.org/doc/latest/DevGuideCodingBasicsJSNI.html"
 * >DevGuideCodingBasicsJSNI</a>.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class ParameterJSO extends JavaScriptObject {

  // Overlay types have protected, no-arg, constructors.
  protected ParameterJSO() {
  }

  /**
   * A JSNI method to access the "key" attribute of a ParameterJSO. This
   * attribute is always present, and is a string.
   */
  public final native String getKey() /*-{
		return this.key;
  }-*/;

  /**
   * A JSNI method to access the "name" attribute of a ParameterJSO. This
   * attribute is always present, and is a string.
   */
  public final native String getName() /*-{
		return this.name;
  }-*/;

  /**
   * A JSNI method to access the "description" attribute of a ParameterJSO. This
   * attribute is always present, and is a string.
   */
  public final native String getDescription() /*-{
		return this.description;
  }-*/;

  /**
   * A JSNI method to access the "visible" attribute of a ParameterJSO,
   * controlling whether the parameter is shown in the WMT Parameters panel. If
   * this attribute is not present, true is returned. Note that the return is a
   * JS boolean, not a J Boolean.
   */
  public final native boolean isVisible() /*-{
		return (typeof this.visible == 'undefined') ? true : this.visible;
  }-*/;

  /**
   * A JSNI method for setting the "visible" attribute of a ParameterJSO,
   * controlling whether the parameter is shown in the WMT Parameters panel.
   * 
   * @param visible JS boolean, true if visible
   */
  public final native void isVisible(boolean visible) /*-{
		this.visible = visible;
  }-*/;

  /**
   * A JSNI method for checking whether a ParameterJSO has a "group" attribute.
   * Note that the return is a JS boolean, not a J Boolean.
   */
  public final native boolean hasGroup() /*-{
		return 'group' in this;
  }-*/;

  /**
   * A JSNI method to get the "name" attribute of a group, a String. If the
   * group or the name doesn't exist, null is returned.
   */
  public final native String getGroupName() /*-{
		return this.group && this.group.name || null;
  }-*/;

  /**
   * A JSNI method to get the "leader" attribute of a group, a JS boolean. If
   * the group or the leader doesn't exist, false is returned.
   */
  public final native boolean isGroupLeader() /*-{
		return this.group && this.group.leader || false;
  }-*/;

  /**
   * A JSNI method to get the number of members in a group, a JS int. If the
   * group or the members attribute doesn't exist, 0 is returned.
   */
  public final native int nGroupMembers() /*-{
		return this.group && this.group.members || 0;
  }-*/;

  /**
   * A JSNI method for checking whether a ParameterJSO has a "selection"
   * attribute. A JS boolean is returned.
   */
  public final native boolean hasSelection() /*-{
		return 'selection' in this;
  }-*/;

  /**
   * A JSNI method to get the "name" attribute of a selection, a String. If the
   * selection or the name doesn't exist, null is returned.
   */
  public final native String getSelectionName() /*-{
		return this.selection && this.selection.name || null;
  }-*/;

  /**
   * A JSNI method to get the "selector" attribute of a group, a JS boolean. If
   * the selection or the leader doesn't exist, false is returned.
   */
  public final native boolean isSelector() /*-{
		return this.selection && this.selection.selector || false;
  }-*/;

  /**
   * A JSNI method to get the number of members in a selection, a JS int. If the
   * selection or the members attribute doesn't exist, 0 is returned.
   */
  public final native int nSelectionMembers() /*-{
		return this.selection && this.selection.members || 0;
  }-*/;

  /**
   * A JSNI method to get the mapping of a choice in the selector to the
   * appropriate member in the selection. Both the input choice and the output
   * mapping are Strings.
   */
  public final native String getSelectionMapping(String choice) /*-{
		return this.selection.mapping[choice];
  }-*/;

  /**
   * JSNI method to get the "value" attribute of a ParameterJSO. This attribute
   * is a ValueJSO object. It's always present, but it may be empty.
   */
  public final native ValueJSO getValue() /*-{
		return this.value;
  }-*/;

  /**
   * A non-JSNI method for stringifying the attributes of a ParameterJSO. Must
   * be final.
   */
  public final Vector<String> toStringVector() {

    Vector<String> retVal = new Vector<String>();
    retVal.add("key: " + getKey());
    retVal.add("name: " + getName());
    retVal.add("description: " + getDescription());
    for (int i = 0; i < getValue().toStringVector().size(); i++) {
      retVal.add(getValue().toStringVector().get(i));
    }
    return retVal;
  }
}
