package edu.colorado.csdms.wmt.client.ui.panel;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * Displays the owner and creation date of a model.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class MetadataPanel extends HorizontalPanel {

  private Grid grid;
  private String owner = "";
  private String date = "";
  
  public MetadataPanel() {

    this.setWidth("100%");
    this.setSpacing(5); // px
    this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
    
    grid = new Grid(2, 2);
    grid.setHTML(0, 0, "owner:");
    grid.setHTML(1, 0, "creation date:");
    grid.setHTML(0, 1, owner);
    grid.setHTML(1, 1, date);
    grid.getCellFormatter().setHorizontalAlignment(0, 0,
        HasHorizontalAlignment.ALIGN_RIGHT);
    grid.getCellFormatter().setHorizontalAlignment(1, 0,
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
}
