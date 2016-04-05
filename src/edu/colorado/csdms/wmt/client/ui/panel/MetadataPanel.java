package edu.colorado.csdms.wmt.client.ui.panel;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;

import edu.colorado.csdms.wmt.client.Constants;

/**
 * Displays the owner, creation date, and internal id of a model.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class MetadataPanel extends HorizontalPanel {

  private Grid grid;
  private String owner = "";
  private String date = "";
  private Integer id = Constants.DEFAULT_MODEL_ID;
  
  public MetadataPanel() {

    this.setWidth("100%");
    this.setSpacing(5); // px
    this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
    
    grid = new Grid(3, 2);
    grid.setHTML(0, 0, "owner:");
    grid.setHTML(1, 0, "creation date:");
    grid.setHTML(2, 0, "id:");
    grid.setHTML(0, 1, owner);
    grid.setHTML(1, 1, date);
    grid.setHTML(2, 1, id.toString());
    grid.getCellFormatter().setHorizontalAlignment(0, 0,
        HasHorizontalAlignment.ALIGN_RIGHT);
    grid.getCellFormatter().setHorizontalAlignment(1, 0,
        HasHorizontalAlignment.ALIGN_RIGHT);
    grid.getCellFormatter().setHorizontalAlignment(2, 0,
        HasHorizontalAlignment.ALIGN_RIGHT);
    
    this.add(grid);
  }

  public String getOwner() {
    return grid.getText(0, 1);
  }

  public void setOwner(String owner) {
    grid.setHTML(0, 1, owner);
  }
  
  public String getDate() {
    return grid.getText(1, 1);
  }

  public void setDate(String date) {
    grid.setHTML(1, 1, date);
  }

  public String getId() {
    return grid.getText(2, 1);
  }

  public void setId(Integer id) {
    grid.setHTML(2, 1, id.toString());
  }
}
