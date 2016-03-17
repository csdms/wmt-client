package edu.colorado.csdms.wmt.client.ui;

import java.util.ArrayList;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

import edu.colorado.csdms.wmt.client.control.DataManager;
import edu.colorado.csdms.wmt.client.data.ParameterJSO;
import edu.colorado.csdms.wmt.client.ui.cell.ChoiceCell;
import edu.colorado.csdms.wmt.client.ui.cell.DescriptionCell;
import edu.colorado.csdms.wmt.client.ui.cell.SeparatorCell;
import edu.colorado.csdms.wmt.client.ui.cell.ValueCell;
import edu.colorado.csdms.wmt.client.ui.panel.ParameterActionPanel;

/**
 * Builds a table of parameters for a single WMT model component. The value of
 * the parameter is editable.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class ParameterTable extends FlexTable {

  public DataManager data;
  private String componentId; // the id of the displayed component
  private ParameterActionPanel actionPanel;
  private Integer tableRowIndex; // where we are in table
  private Integer parameterIndex; // where we are in list of parameters

  /**
   * Initializes a table of parameters for a single WMT model component. The
   * table is empty until {@link #loadTable(String)} is called.
   * 
   * @param data the DataManager instance for the WMT session
   */
  public ParameterTable(DataManager data) {

    this.data = data;
    this.tableRowIndex = 0;
    this.parameterIndex = 0;
    this.setWidth("100%");
  }

  /**
   * A worker that loads the ParameterTable with parameter values for the
   * selected model component.
   * 
   * @param componentId the id of the component whose parameters are to be
   *  displayed
   */
  public void loadTable(String componentId) {

    // Always clear the table before building it.
    this.clearTable();

    // Return if the selected component doesn't have parameters.
    if (data.getModelComponent(componentId).getParameters() == null) {
      Window.alert("No parameters defined for this component.");
      return;
    }

    // The component whose parameters are to be displayed.
    this.setComponentId(componentId);

    // Set the component name on the tab holding the ParameterTable.
    data.getPerspective().setParameterPanelTitle(componentId);

    // Add the ParameterActionPanel and align it with the ModelActionPanel.
    addActionPanel();

    // Build the parameter table.
    Integer nParameters = data.getModelComponent(componentId).nParameters();
    while (parameterIndex < nParameters) {
      addTableEntry();
      parameterIndex++;
    }
  }

  /**
   * Adds the {@link ParameterActionPanel} to the top of the
   * {@link ParameterTable}.
   */
  private void addActionPanel() {
    actionPanel = new ParameterActionPanel(data, componentId);
    actionPanel.getElement().getStyle().setMarginTop(-3.0, Unit.PX);
    this.setWidget(tableRowIndex, 0, actionPanel);
    tableRowIndex++;
  }

  /**
   * Adds an entry (a heading or a parameter) into the {@link ParameterTable}.
   * 
   * XXX Refactor. Extract ParameterTableEntry as a class?
   */
  private void addTableEntry() {
    ParameterJSO parameter =
        data.getModelComponent(componentId).getParameters().get(parameterIndex);

    // Short-circuit if the parameter isn't visible.
    if (!parameter.isVisible()) {
      return;
    }

    // Short circuit if the parameter is a separator.
    if (parameter.getKey().matches("separator")) {
      this.setWidget(tableRowIndex, 0, new SeparatorCell(parameter));
      this.getFlexCellFormatter().setColSpan(tableRowIndex, 0, 3);
      tableRowIndex++;
      return;
    }

    // The basic setup: display the parameter's description and value.
    DescriptionCell descriptionCell = new DescriptionCell(parameter);
    ValueCell valueCell = new ValueCell(parameter);
    this.setWidget(tableRowIndex, 0, descriptionCell);
    this.setWidget(tableRowIndex, 2, valueCell);
    this.getFlexCellFormatter().setHorizontalAlignment(tableRowIndex, 2,
        HasHorizontalAlignment.ALIGN_RIGHT);

    // Some parameters may be grouped. Keep track of the group leader and its
    // members.
    if ((parameter.hasGroup()) && (!parameter.isGroupLeader())) {
      this.getRowFormatter().setVisible(tableRowIndex, false);
    }
    if (parameter.isGroupLeader()) {
      ArrayList<Integer> groupRows = new ArrayList<Integer>();
      for (int i = 0; i < (parameter.nGroupMembers() - 1); i++) {
        groupRows.add(tableRowIndex + i + 1);
      }
      descriptionCell.setGroupRows(groupRows);
      descriptionCell.addClickHandler(new GroupVisibilityHandler());
    }

    // Other parameters may belong to a selection group. Selections display
    // *two* parameters per table entry. Keep track of the selector and its
    // members.
    if (parameter.hasSelection()) {
      ArrayList<ValueCell> selections = new ArrayList<ValueCell>();
      for (int i = 0; i < parameter.nSelectionMembers() - 1; i++) {
        parameterIndex++;
        selections.add(new ValueCell(data.getModelComponent(componentId)
            .getParameters().get(parameterIndex)));
      }
      this.setWidget(tableRowIndex, 1, valueCell);
      this.setWidget(tableRowIndex, 2, selections.get(0));
      valueCell.addDomHandler(new SelectionChangeHandler(tableRowIndex,
          selections), ChangeEvent.getType());
    }

    tableRowIndex++;
  }

  /**
   * Stores the modified value of a parameter of a model component in the WMT
   * {@link DataManager}.
   * 
   * @param parameter the ParameterJSO object for the parameter being modified
   * @param newValue the new parameter value, a String
   */
  public void setValue(ParameterJSO parameter, String newValue) {

    String key = parameter.getKey();
    String previousValue =
        data.getModelComponent(componentId).getParameter(key).getValue()
            .getDefault();
    GWT.log(componentId + ": " + key + ": " + newValue);

    // Don't update state when tabbing between fields or moving within field.
    // XXX Would be better to handle this further upstream.
    if (!newValue.matches(previousValue)) {
      data.getModelComponent(componentId).getParameter(key).getValue()
          .setDefault(newValue);
      data.updateModelSaveState(false);
    }
  }

  /**
   * Deletes the contents of the ParameterTable and resets the tab title to
   * "Parameters".
   */
  public void clearTable() {
    this.setComponentId(null);
    data.getPerspective().setParameterPanelTitle(null);
    this.tableRowIndex = 0;
    this.parameterIndex = 0;
    this.removeAllRows();
    this.clear(true);
  }

  /**
   * Returns the id of the model component (a String) whose parameters are
   * displayed in the ParameterTable.
   */
  public String getComponentId() {
    return componentId;
  }

  /**
   * Stores the id of the model component (a String) whose parameters are
   * displayed in the ParameterTable.
   * 
   * @param componentId a component id, a String
   */
  public void setComponentId(String componentId) {
    this.componentId = componentId;
  }

  /**
   * Handles a click on the DescriptionCell of a parameter group. Toggles the
   * visibility of the parameters in the group.
   */
  public class GroupVisibilityHandler implements ClickHandler {

    @Override
    public void onClick(ClickEvent event) {
      DescriptionCell descriptionCell = (DescriptionCell) event.getSource();
      RowFormatter rowFormatter = ParameterTable.this.getRowFormatter();
      ArrayList<Integer> theRows = descriptionCell.getGroupRows();
      for (Integer row : theRows) {
        rowFormatter.setVisible(row, !rowFormatter.isVisible(row));
      }
      descriptionCell.areGroupRowsVisible(!descriptionCell.areGroupRowsVisible());
      descriptionCell.setStyleDependentName("groupLeader-open",
              descriptionCell.areGroupRowsVisible());
    }
  }

  /**
   * Handles a click on a ValueCell that's the selector parameter of a selection
   * group. Applies the mapping of the selected value to display a target
   * ValueCell.
   */
  public class SelectionChangeHandler implements ChangeHandler {

    private Integer rowIndex;
    private ArrayList<ValueCell> selections;

    public SelectionChangeHandler(Integer rowIndex, ArrayList<ValueCell> selections) {
      this.rowIndex = rowIndex;
      this.selections = selections;
    }

    @Override
    public void onChange(ChangeEvent event) {
      ValueCell selector = (ValueCell) event.getSource();
      ChoiceCell cell = (ChoiceCell) selector.getWidget(0);
      String value = cell.getValue(cell.getSelectedIndex());
      String mappedKey = selector.getParameter().getSelectionMapping(value);

      for (ValueCell target : selections) {
        if (mappedKey.equalsIgnoreCase(target.getParameter().getKey())) {
          ParameterTable.this.setWidget(rowIndex, 2, target);
        }
      }
    }
  }
}
