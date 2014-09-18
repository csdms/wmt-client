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
package edu.colorado.csdms.wmt.client.ui.widgets;

import com.google.gwt.user.client.ui.HTML;

import edu.colorado.csdms.wmt.client.data.ParameterJSO;

/**
 * A cell for the first column in a ParameterTable, holding the parameter
 * description with its units.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class DescriptionCell extends HTML {

  /**
   * Makes a DescriptionCell from the information contained in the input
   * ParameterJSO object.
   * 
   * @param parameter a ParameterJSO object
   */
  public DescriptionCell(ParameterJSO parameter) {

    String units = parameter.getValue().getUnits();
    String description = parameter.getDescription();

    if (units != null) {
      description += " (" + units + ")";
    }

    this.setHTML(description);
  }
}
