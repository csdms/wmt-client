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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayInteger;

/**
 * A GWT JavaScript overlay (JSO) type that describes the JSON returned on a
 * HTTP GET call to <a
 * href="http://csdms.colorado.edu/wmt/api/tag/model/query">tag/model/query</a>
 * or to <a href="http://csdms.colorado.edu/wmt/api/tag/model/">tag/model/</a>.
 * 
 * @see <a
 *      href="http://www.gwtproject.org/doc/latest/DevGuideCodingBasicsOverlay.html">http://www.gwtproject.org/doc/latest/DevGuideCodingBasicsOverlay.html</a>
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class LabelQueryJSO extends JavaScriptObject {

  // Overlay types always have protected, zero-arg constructors.
  protected LabelQueryJSO() {
  }

  /**
   * Gets a JsArrayInteger of model or label ids. This is a JSNI method.
   */
  public final native JsArrayInteger getIds() /*-{
		return this;
  }-*/;
}
