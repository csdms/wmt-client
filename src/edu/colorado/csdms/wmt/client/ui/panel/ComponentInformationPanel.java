package edu.colorado.csdms.wmt.client.ui.panel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.colorado.csdms.wmt.client.data.ComponentJSO;

/**
 * A panel that displays WMT component metadata (id, url, author, etc.).
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class ComponentInformationPanel extends PopupPanel {

  private static String[] LABELS = {"title", "summary", "url", "author"};
  private Grid grid;
  private ComponentJSO componentJSO;

  /**
   * Makes a new {@link ComponentInformationPanel}.
   * 
   * @param componentJSO the panel displays information for this component
   */
  public ComponentInformationPanel(ComponentJSO componentJSO) {

    super(true); // autohide
    this.componentJSO = componentJSO;
    this.setStyleName("wmt-PopupPanel");

    grid = new Grid(LABELS.length, 1);
    grid.setStyleName("wmt-Label");
    grid.setCellPadding(5); // px
    
    VerticalPanel contents = new VerticalPanel();
    contents.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
    contents.setWidth("35em");
    contents.add(grid);    
    this.add(contents);
    
    // Populate the panel with component information.
    populatePanel();
  }

  public void populatePanel() {
    String title = componentJSO.getName();
    if (componentJSO.getVersion() != null) {
      title += " v" + componentJSO.getVersion();
    }
    if (componentJSO.getDoi() != null) {
      title += " (" + componentJSO.getDoi() + ")";
    }
    title = "<b>" + title + "</b>";
    String url = "<a href='" + componentJSO.getURL() + "'>" 
        + componentJSO.getURL() + "</a>";
    String author = (componentJSO.getAuthor() == null) 
        ? " " : componentJSO.getAuthor();
    String[] info = {title, componentJSO.getSummary(), url, 
        "Model developer: " + author};

    HTML urlHtml = null;
    for (int i = 0; i < LABELS.length; i++) {
      if (url.equals(info[i])) {
        urlHtml = new HTML(info[i]);
        grid.setWidget(i, 0, urlHtml);
      } else {
        grid.setWidget(i, 0, new HTML(info[i]));
      }
    }
    
    /*
     *  Intercepts the click on the component URL and opens it in a new tab.
     */
    urlHtml.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        event.preventDefault();
        Window.open(componentJSO.getURL(), "componentInfo", null);
      }
    });

  }
}
