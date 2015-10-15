package edu.colorado.csdms.wmt.client.ui.cell;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.TextBox;

import edu.colorado.csdms.wmt.client.data.ParameterJSO;

/**
 * Displays an editable text box in a {@link ValueCell}. It's initialized with
 * the default value of a "string" parameter type.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class TextCell extends TextBox {

  private ValueCell parent;
  private ParameterJSO parameter;
  
  public TextCell(ValueCell parent) {

    this.parent = parent;
    this.parameter = this.parent.getParameter();
    
    this.addKeyUpHandler(new TextEditHandler());
    this.setStyleName("wmt-TextBoxen");
    this.setText(parameter.getValue().getDefault());
  }

  /**
   * The handler for keyboard events in a TextCell.
   */
  public class TextEditHandler implements KeyUpHandler {
    @Override
    public void onKeyUp(KeyUpEvent event) {
      GWT.log("(onKeyUp:text)");
      TextCell cell = (TextCell) event.getSource();
      parent.setParameterValue(cell.getText());
    }
  }
}
