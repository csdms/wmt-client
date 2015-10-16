package edu.colorado.csdms.wmt.client.ui.cell;

import com.google.gwt.user.client.ui.HorizontalPanel;

import edu.colorado.csdms.wmt.client.data.ParameterJSO;
import edu.colorado.csdms.wmt.client.ui.ParameterTable;

/**
 * Used to display the value of a parameter in a {@link ParameterTable}, a
 * ValueCell renders as a ListBox (droplist) if the parameter type = "choice" or
 * "file"; otherwise, it renders as an editable TextBox. Changes to the value in
 * a ValueCell are stored in the WMT DataManager.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class ValueCell extends HorizontalPanel {

  private ParameterJSO parameter;

  /**
   * Makes a ValueCell from the information contained in the input
   * {@link ParameterJSO} object.
   * 
   * @param parameter a ParameterJSO object
   */
  public ValueCell(ParameterJSO parameter) {

    this.parameter = parameter;
    this.setStyleName("wmt-ValueCell");

    // If the parameter is a separator, short-circuit the method and return.
    if (this.parameter.getKey().matches("separator")) {
      return;
    }

    // Add a cell to match the parameter type.
    String type = this.parameter.getValue().getType();
    if (type.matches("choice")) {
      this.add(new ChoiceCell(this));
    } else if (type.matches("file")) {
      this.add(new FileCell(this));
    } else if (type.matches("int")) {
      this.add(new IntegerCell(this));
    } else if (type.matches("float")) {
      this.add(new DoubleCell(this));
    } else {
      this.add(new TextCell(this));
    }
  }

  public ParameterJSO getParameter() {
    return parameter;
  }

  public void setParameter(ParameterJSO parameter) {
    this.parameter = parameter;
  }

  /**
   * Passes the modified value up to
   * {@link ParameterTable#setValue(ParameterJSO, String)}. This isn't an
   * elegant solution, but ParameterTable knows the component this parameter
   * belongs to and it has access to the DataManager object for storage.
   * 
   * @param value the value read from the ValueCell
   */
  public void setParameterValue(String value) {
    ParameterTable pt = (ParameterTable) ValueCell.this.getParent();
    pt.setValue(parameter, value);
  }
}
