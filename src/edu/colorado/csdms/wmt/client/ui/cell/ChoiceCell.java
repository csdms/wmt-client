package edu.colorado.csdms.wmt.client.ui.cell;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ListBox;

import edu.colorado.csdms.wmt.client.data.ParameterJSO;

/**
 * Creates a droplist in a {@link ValueCell} for the "choice" parameter type.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class ChoiceCell extends ListBox {

  private ValueCell parent;
  private ParameterJSO parameter;
  
  /**
   * Makes a {@link ChoiceCell} initialized with the default value of a "choice"
   * parameter type.
   * 
   * @param parent the parent of the ChoiceCell, a ValueCell
   */
  public ChoiceCell(ValueCell parent) {

    super(false);  // no multi-select
    this.parent = parent;
    this.parameter = this.parent.getParameter();
    
    this.addChangeHandler(new ChoiceCellHandler());
    this.setStyleName("wmt-DroplistBox");

    Integer nChoices = this.parameter.getValue().getChoices().length();
    for (int i = 0; i < nChoices; i++) {
      this.addItem(this.parameter.getValue().getChoices().get(i));
      if (this.getItemText(i).matches(parameter.getValue().getDefault())) {
        this.setSelectedIndex(i);
      }
    }
    this.setVisibleItemCount(1);  // show one item -- a droplist
  }
  
  /**
   * The handler for a change event in a ChoiceCell.
   */
  public class ChoiceCellHandler implements ChangeHandler {
    @Override
    public void onChange(ChangeEvent event) {
      GWT.log("(onChange)");
      ChoiceCell cell = (ChoiceCell) event.getSource();
      String value = cell.getValue(cell.getSelectedIndex());
      parent.setParameterValue(value);
    }
  }
}
