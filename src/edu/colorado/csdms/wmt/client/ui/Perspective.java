/**
 * <License>
 */
package edu.colorado.csdms.wmt.client.ui;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;

import edu.colorado.csdms.wmt.client.control.DataManager;

/**
 * Defines the initial layout of views (a perspective, in Eclipse parlance)
 * for a WMT instance in a browser window. The Perspective holds four views,
 * named North, West, Center and South. The top-level organizing panel for the
 * GUI is a DockLayoutPanel.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class Perspective extends DockLayoutPanel {

  private DataManager data;

  // Fractional sizes of views.
  private final static Double VIEW_EAST_FRACTION = 0.40;

  // Browser window dimensions (in px) used for setting up UI views.
  private Integer browserWindowWidth;

  // Width (in px) of splitter grabby bar.
  private final static Integer SPLITTER_SIZE = 3;

  // Height (in px) of tab bars.
  private final static Double TAB_BAR_HEIGHT = 2.0;

  // Primary UI panels.
  private ViewNorth viewNorth;
  private ViewCenter viewCenter;
  private ViewEast viewEast;

  // Secondary UI panels/widgets.
  private ScrollPanel scrollModel;
  private ScrollPanel scrollParameters;
  private ModelGrid modelGrid;
  private ModelMenu modelMenu;
  private ParameterTable parameterTable;  

  /**
   * Draws the panels and their children that compose the basic WMT GUI.
   */
  public Perspective(DataManager data) {

    super(Unit.PX);
    this.addStyleName("wmt-DockLayoutPanel");
    this.data = data;
    this.data.setPerspective(this);

    // Determine initial view sizes based on browser window dimensions.
    browserWindowWidth = Window.getClientWidth();
    Integer viewEastInitialWidth =
        (int) Math.round(VIEW_EAST_FRACTION * browserWindowWidth);
    Integer headerHeight = 70; // TODO diagnose from largest header elt

    // The Perspective has two children, a header in the north panel
    // and a SplitLayoutPanel below.
    viewNorth = new ViewNorth();
    this.addNorth(viewNorth, headerHeight);
    SplitLayoutPanel splitter = new SplitLayoutPanel(SPLITTER_SIZE);
    splitter.addStyleName("wmt-SplitLayoutPanel");
    this.add(splitter);

    // The SplitLayoutPanel defines panels which translate to the West, Center
    // and South views of WMT.
    viewEast = new ViewEast();
    splitter.addEast(viewEast, viewEastInitialWidth);
    viewCenter = new ViewCenter();
    splitter.add(viewCenter); // must be last
  }

  /**
   * An inner class to define the header (North view) of the WMT GUI.
   * <p>
   * Login info could be located here, like in the GWT Mail example.
   */
  private class ViewNorth extends Grid {

    /**
     * Makes the Header (North) view of the WMT GUI.
     */
    public ViewNorth() {

      super(1, 2);
      this.setWidth("100%");

      // Associate a ModelMenu.
      modelMenu = new ModelMenu(data);

      Image logo = new Image("images/CSDMS_Logo_1.jpg");
      logo.setTitle("http://csdms.colorado.edu");

      this.setWidget(0, 0, logo);
      this.setWidget(0, 1, modelMenu.getMenuButton());
      this.getCellFormatter()
          .setAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT,
              HasVerticalAlignment.ALIGN_MIDDLE);

      // Clicking the CSDMS logo opens the CSDMS website in a new browser tab.
      logo.addClickHandler(new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
          Window.open("http://csdms.colorado.edu", "_blank", null);
        }
      });
    }
  } // end ViewNorth

  /**
   * An inner class to define the Center panel of the WMT GUI.
   */
  private class ViewCenter extends TabLayoutPanel {

    /**
     * Makes the Center view of the WMT GUI. It displays the arena.
     */
    public ViewCenter() {
      super(TAB_BAR_HEIGHT, Unit.EM);
      setModelPanel(new ScrollPanel());
      String tabTitle = data.tabPrefix("model") + "Model";
      this.add(scrollModel, tabTitle, true);
    }
  } // end ViewCenter

  /**
   * An inner class to define the East panel of the WMT GUI.
   */
  private class ViewEast extends TabLayoutPanel {

    /**
     * Makes the East view of the WMT GUI. It displays the parameters of the
     * currently selected model.
     */
    public ViewEast() {
      super(TAB_BAR_HEIGHT, Unit.EM);
      setParametersPanel(new ScrollPanel());
      String tabTitle = data.tabPrefix("parameter") + "Parameters";
      this.add(scrollParameters, tabTitle, true);
    }
  } // end ViewEast

  public ScrollPanel getModelPanel() {
    return scrollModel;
  }

  public void setModelPanel(ScrollPanel scrollModel) {
    this.scrollModel = scrollModel;
  }

  /**
   * A convenience method for setting the tab title of the Model panel. If the
   * model isn't saved, prepend an asterisk to its name.
   */
  public void setModelPanelTitle() {
    String tabTitle = data.tabPrefix("model") + "Model";
    if (data.getModel().getName() != null) {
      String marker = data.modelIsSaved() ? "" : "*";
      tabTitle += " (" + marker + data.getModel().getName() + ")";
    } else {
      data.getModel().setName("Model " + data.saveAttempts.toString());
    }
    viewCenter.setTabHTML(0, tabTitle);
  }

  public ModelGrid getModelGrid() {
    return modelGrid;
  }

  public void setModelGrid(ModelGrid modelGrid) {
    this.modelGrid = modelGrid;
  }

  public ScrollPanel getParametersPanel() {
    return scrollParameters;
  }

  public void setParametersPanel(ScrollPanel scrollParameters) {
    this.scrollParameters = scrollParameters;
  }

  /**
   * A convenience method for setting the title of the Parameters panel.
   * 
   * @param componentId the id of the component whose parameters are displayed
   */
  public void setParameterPanelTitle(String componentId) {
    String tabTitle = data.tabPrefix("parameter") + "Parameters";
    if (componentId != null) {
      String componentName = data.getModelComponent(componentId).getName();
      tabTitle += " (" + componentName + ")";
    }
    viewEast.setTabHTML(0, tabTitle);
  }

  /**
   * Returns a reference to the {@link ParameterTable} used in the
   * "Parameters" tab of a WMT session.
   */
  public ParameterTable getParameterTable() {
    return parameterTable;
  }

  /**
   * Stores a reference to the {@link ParameterTable} used in the "Parameters"
   * tab of a WMT session.
   * 
   * @param parameterTable the parameterTable to set
   */
  public void setParameterTable(ParameterTable parameterTable) {
    this.parameterTable = parameterTable;
  }
  
  public TabLayoutPanel getViewEast() {
    return viewEast;
  }

  public TabLayoutPanel getViewCenter() {
    return viewCenter;
  }

  /**
   * @return the modelMenu
   */
  public ModelMenu getModelMenu() {
    return modelMenu;
  }

  /**
   * @param modelMenu the modelMenu to set
   */
  public void setModelMenu(ModelMenu modelMenu) {
    this.modelMenu = modelMenu;
  }

  /**
   * Sets up the default starting ModelTree in the "Model" tab, showing only
   * the open port for the driver of the model.
   */
  public void initializeModel() {
    modelGrid = new ModelGrid(data);
    scrollModel.add(modelGrid);

//    modelTree.addHandler(new ClickHandler() {
//      @Override
//      public void onClick(ClickEvent event) {
//        if (data.getSelectedComponent() != null) {
//          ParameterTable table = (ParameterTable) scrollParameters.getWidget();
//          table.removeAllRows(); // FlexTable
//          table.loadTable();
//        }
//      }
//    }, ClickEvent.getType());
  }

  /**
   * Creates an empty ParameterTable to display in the "Parameters" tab.
   */
  public void initializeParameterTable() {
    ParameterTable parameterTable = new ParameterTable(data);
    scrollParameters.add(parameterTable);
  }

  /**
   * Resets WMT to an approximation of its startup state.
   */
  public void reset() {
    data.setSelectedComponent(null);
    data.resetModelComponents();

    parameterTable.clearTable();
    data.getModelTree().initializeTree();

    setModelPanelTitle();
  }
}
