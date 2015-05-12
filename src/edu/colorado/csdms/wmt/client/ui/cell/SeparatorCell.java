package edu.colorado.csdms.wmt.client.ui.cell;

import com.google.gwt.user.client.ui.HTML;

import edu.colorado.csdms.wmt.client.data.ParameterJSO;

/**
 * A cell for separators between groups of parameters in a ParameterTable.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class SeparatorCell extends HTML {

  /**
   * Makes a SeparatorCell from the information contained in the input
   * ParameterJSO object.
   * 
   * @param parameter a ParameterJSO object
   */
  public SeparatorCell(ParameterJSO parameter) {

    String description = parameter.getDescription();
    this.setHTML(description);
    this.setStylePrimaryName("wmt-ParameterSeparator");
  }
}
