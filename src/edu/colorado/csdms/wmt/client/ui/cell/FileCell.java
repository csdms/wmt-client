package edu.colorado.csdms.wmt.client.ui.cell;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;

import edu.colorado.csdms.wmt.client.control.DataURL;
import edu.colorado.csdms.wmt.client.data.ParameterJSO;
import edu.colorado.csdms.wmt.client.ui.ParameterTable;
import edu.colorado.csdms.wmt.client.ui.dialog.UploadDialogBox;

/**
 * Creates a droplist and an upload button in a {@link ValueCell} for the "file"
 * parameter type.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class FileCell extends HorizontalPanel {

  private ValueCell parent;
  private ParameterJSO parameter;
  private ListBox fileDroplist;
  private UploadDialogBox uploadBox;

  /**
   * Makes a {@link FileCell} initialized with the default value of a "file"
   * parameter type.
   * 
   * @param parent the parent of the FileCell, a ValueCell
   */
  public FileCell(ValueCell parent) {

    this.parent = parent;
    this.parameter = this.parent.getParameter();
    this.setVerticalAlignment(ALIGN_MIDDLE);

    fileDroplist = new ListBox(false);  // no multi select
    this.add(fileDroplist);
    fileDroplist.addChangeHandler(new FileDroplistHandler());
    fileDroplist.setStyleName("wmt-DroplistBox");
    fileDroplist.addStyleDependentName("upload");
    
    Button uploadButton = new Button("<i class='fa fa-cloud-upload'></i>");
    this.add(uploadButton);
    uploadButton.addClickHandler(new UploadHandler());
    uploadButton.setStyleName("wmt-UploadButton");
    uploadButton.setTitle("Upload file to server");
    
    // Load the droplist. If the value of the incoming parameter isn't listed
    // in the component, append it to the end of the list and select it.
    // If there's only one file in the list, and it's not the default, also
    // display the default, in case the user wants to revert to the default.
    // (This has been a troublesome code block.)
    Integer nFiles = this.parameter.getValue().getFiles().length();
    Integer selectedIndex = -1;
    for (int i = 0; i < nFiles; i++) {
      fileDroplist.addItem(this.parameter.getValue().getFiles().get(i));
      if (fileDroplist.getItemText(i).matches(parameter.getValue().getDefault())) {
        selectedIndex = i;
      }
    }
    if (selectedIndex == -1) {
      fileDroplist.addItem(parameter.getValue().getDefault());
      fileDroplist.setSelectedIndex(fileDroplist.getItemCount() - 1);
    } else if (selectedIndex == 0) {
      fileDroplist.setSelectedIndex(fileDroplist.getItemCount() - 1);
    } else {
      fileDroplist.setSelectedIndex(selectedIndex);
    }
    fileDroplist.setVisibleItemCount(1);  // show one item -- a droplist
  }
  
  /**
   * The handler for a change event in the droplist of a {@link FileCell}.
   */
  public class FileDroplistHandler implements ChangeHandler {
    @Override
    public void onChange(ChangeEvent event) {
      GWT.log("(onChange)");
      String value = fileDroplist.getValue(fileDroplist.getSelectedIndex());
      parent.setParameterValue(value);
    }
  }
  
  /**
   * Handles a click on the Upload button.
   */
  public class UploadHandler implements ClickHandler {
    @Override
    public void onClick(ClickEvent event) {

      ParameterTable pt = (ParameterTable) parent.getParent();
      if (!pt.data.modelIsSaved()) {
        String msg =
            "The model must be saved to the server"
                + " before files can be uploaded.";
        Window.alert(msg);
        return;
      }

      uploadBox = new UploadDialogBox();
      uploadBox.setText("Upload File...");

      // Get the id of the model this file belongs to.
      String modelId = ((Integer) pt.data.getMetadata().getId()).toString(); 
      uploadBox.getHidden().setValue(modelId);

      // Where the form is to be submitted.
      uploadBox.getForm().setAction(DataURL.uploadFile(pt.data));
      
      uploadBox.getForm().addSubmitCompleteHandler(new UploadCompleteHandler());
      uploadBox.center();
    }
  }
  
  /**
   * When the upload is complete and successful, add the name of the uploaded
   * file to the {@link FileCell} fileDroplist, select it, and save it as the
   * value of this parameter.
   */
  public class UploadCompleteHandler implements FormPanel.SubmitCompleteHandler {
    @Override
    public void onSubmitComplete(SubmitCompleteEvent event) {
      
      uploadBox.hide();
      
      if (event.getResults() != null) {        
        
        // Strip the fakepath from the filename.
        String fileName =
            uploadBox.getUpload().getFilename().replace("C:\\fakepath\\", "");
        
        // Add the filename to the fileDroplist, but only if it's not there
        // already.
        Integer listIndex = -1;
        for (int i = 0; i < fileDroplist.getItemCount(); i++) {
          if (fileDroplist.getItemText(i).matches(fileName)) {
            listIndex = i;
          }
        }
        if (listIndex > 0) {
          fileDroplist.setSelectedIndex(listIndex);
        } else {
          fileDroplist.addItem(fileName);
          fileDroplist.setSelectedIndex(fileDroplist.getItemCount() - 1);
        }
        
        // Like, important.
        parent.setParameterValue(fileName);
        
        // Say everything is alright.
        Window.alert("File uploaded!");
        
        // Mark the model as unsaved.
        ParameterTable pt = (ParameterTable) parent.getParent();
        pt.data.updateModelSaveState(false);
      }
    }
  }
}
