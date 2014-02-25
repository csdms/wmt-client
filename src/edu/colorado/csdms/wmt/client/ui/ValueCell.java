/**
 * <License>
 */
package edu.colorado.csdms.wmt.client.ui;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

import edu.colorado.csdms.wmt.client.data.ParameterJSO;

/**
 * Used to display the value of a parameter in a {@link ParameterTable}, a
 * ValueCell renders as a ListBox (droplist) if the parameter type = "choice"
 * or "file"; otherwise, it renders as an editable TextBox. Changes to the
 * value in a ValueCell are stored in the WMT DataManager.
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

    // If the parameter is a separator, short-circuit the method and return.
    if (this.parameter.getKey().matches("separator")) {
      return;
    }

    // Helpful locals.
    String type = this.parameter.getValue().getType();
    String value = this.parameter.getValue().getDefault();
    String range = "";

    // Match the type -- choice, file or other.
    if (type.matches("choice")) {

      ListBox choiceDroplist = new ListBox(false); // no multi select
      choiceDroplist.addChangeHandler(new ListSelectionHandler());

      Integer nChoices = this.parameter.getValue().getChoices().length();
      for (int i = 0; i < nChoices; i++) {
        choiceDroplist.addItem(this.parameter.getValue().getChoices().get(i));
        if (choiceDroplist.getItemText(i).matches(value)) {
          choiceDroplist.setSelectedIndex(i);
        }
      }
      choiceDroplist.setVisibleItemCount(1); // show one item -- a droplist
      this.add(choiceDroplist);

    } else if (type.matches("file")) {

      ListBox fileDroplist = new ListBox(false); // no multi select
      fileDroplist.addChangeHandler(new ListSelectionHandler());

      Integer nFiles = this.parameter.getValue().getFiles().length();
      for (int i = 0; i < nFiles; i++) {
        fileDroplist.addItem(this.parameter.getValue().getFiles().get(i));
        if (fileDroplist.getItemText(i).matches(value)) {
          fileDroplist.setSelectedIndex(i);
        }
      }
      fileDroplist.setVisibleItemCount(1); // show one item -- a droplist
      this.add(fileDroplist);

      Button uploadButton = new Button("<i class='fa fa-cloud-upload'></i>");
      uploadButton.addClickHandler(new UploadHandler());

      uploadButton.setTitle("Upload file to server");
      this.add(uploadButton);

      this.setCellVerticalAlignment(fileDroplist, ALIGN_MIDDLE);
      uploadButton.getElement().getStyle().setMarginLeft(3, Unit.PX);

    } else {

      TextBox valueTextBox = new TextBox();
      valueTextBox.addKeyUpHandler(new TextEditHandler());

      valueTextBox.setText(value);
      valueTextBox.getElement().getStyle().setBackgroundColor("#ffc");
      this.add(valueTextBox);

    }

    // If appropriate, add a tooltip showing the valid range of the value.
    if (!type.matches("string") && !type.matches("choice")) {
      range +=
          "Valid range = ( " + parameter.getValue().getMin() + ", "
              + parameter.getValue().getMax() + " )";
      this.setTitle(range);
    }
  }

  /**
   * Passes the modified value up to
   * {@link ParameterTable#setValue(ParameterJSO, String)}. This isn't an
   * elegant solution, but ParameterTable knows the component this parameter
   * belongs to and it has access to the DataManager object for storage.
   * 
   * @param value
   */
  public void setValue(String value) {
    ParameterTable pt = (ParameterTable) ValueCell.this.getParent();
    pt.setValue(parameter, value);
  }

  /**
   * A class to handle selection in the "choices" ListBox.
   */
  public class ListSelectionHandler implements ChangeHandler {
    @Override
    public void onChange(ChangeEvent event) {
      GWT.log("(onChange)");
      ListBox listBox = (ListBox) event.getSource();
      String value = listBox.getValue(listBox.getSelectedIndex());
      setValue(value);
    }
  }

  /**
   * A class to handle keyboard events in the TextBox -- every key press, so
   * there could be many. Might consider acting on only Tab or Enter.
   */
  public class TextEditHandler implements KeyUpHandler {
    @Override
    public void onKeyUp(KeyUpEvent event) {
      GWT.log("(onKeyUp)");
      TextBox textBox = (TextBox) event.getSource();
      String value = textBox.getText();
      setValue(value);
    }
  }

  /**
   * A class to handle selection in the "files" ListBox.
   */
  public class UploadHandler implements ClickHandler {
    @Override
    public void onClick(ClickEvent event) {
      Window.alert("Upload");
    }
  }

}
