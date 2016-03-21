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
  private String cellType;

  /**
   * Makes a ValueCell from the information contained in the input
   * {@link ParameterJSO} object.
   * 
   * @param parameter a ParameterJSO object
   */
  public ValueCell(ParameterJSO parameter) {

    this.parameter = parameter;
    this.cellType = this.parameter.getValue().getType();
    this.setStyleName("wmt-ValueCell");

    // Add a cell to match the parameter type.
    if (isChoice()) {
      this.add(new ChoiceCell(this));
    } else if (isFile()) {
      this.add(new FileCell(this));
    } else if (isInt()) {
      this.add(new IntegerCell(this));
    } else if (isFloat()) {
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

  /**
   * States whether parameter is of type "choice".
   * 
   * @return true if the parameter type is "choice"
   */
  public Boolean isChoice() {
    return cellType.matches("choice");
  }

  /**
   * States whether parameter is of type "file".
   * 
   * @return true if the parameter type is "file"
   */
  public Boolean isFile() {
    return cellType.matches("file");
  }

  /**
   * States whether parameter is of type "int".
   * 
   * @return true if the parameter type is "int"
   */
  public Boolean isInt() {
    return cellType.matches("int");
  }

  /**
   * States whether parameter is of type "float".
   * 
   * @return true if the parameter type is "float"
   */
  public Boolean isFloat() {
    return cellType.matches("float");
  }
}
