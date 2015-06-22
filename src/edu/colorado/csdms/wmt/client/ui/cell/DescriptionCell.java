package edu.colorado.csdms.wmt.client.ui.cell;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.HTML;

import edu.colorado.csdms.wmt.client.data.ParameterJSO;

/**
 * A cell for the first column in a ParameterTable, holding the parameter
 * description with its units.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class DescriptionCell extends HTML {

  private ArrayList<Integer> groupRows;
  private Boolean groupRowsVisible = false;

  /**
   * Makes a DescriptionCell from the information contained in the input
   * ParameterJSO object.
   * 
   * @param parameter a ParameterJSO object
   */
  public DescriptionCell(ParameterJSO parameter) {

    String units = parameter.getValue().getUnits();
    String description = parameter.getDescription();

    if (units != null)  {
      description += " [" + units + "]";
    }

    this.setHTML(description);
    this.setStylePrimaryName("wmt-ParameterDescription");
    if ((parameter.hasGroup()) && (!parameter.isGroupLeader())) {
      this.addStyleDependentName("group");
    }
    if (parameter.isGroupLeader()) {
      this.addStyleDependentName("groupLeader");
    }
  }

  public ArrayList<Integer> getGroupRows() {
    return groupRows;
  }

  public void setGroupRows(ArrayList<Integer> groupRows) {
    this.groupRows = groupRows;
  }

  public Boolean areGroupRowsVisible() {
    return groupRowsVisible;
  }

  public void areGroupRowsVisible(Boolean groupRowsVisible) {
    this.groupRowsVisible = groupRowsVisible;
  }
}
