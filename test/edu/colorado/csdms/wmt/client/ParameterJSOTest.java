package edu.colorado.csdms.wmt.client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

import edu.colorado.csdms.wmt.client.data.ParameterJSO;
import edu.colorado.csdms.wmt.client.data.ValueJSO;

/**
 * Tests for {@link ParameterJSO}. JUnit integration is provided by extending
 * {@link GWTTestCase}.
 * 
 * For more information, see
 * <a href="http://www.gwtproject.org/doc/latest/DevGuideTesting.html">
 * DevGuideTesting</a> and
 * <a href="http://www.gwtproject.org/doc/latest/tutorial/JUnit.html">
 * JUnit</a>.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class ParameterJSOTest extends GWTTestCase {

  private ParameterJSO basicParam;
  private ParameterJSO groupParam;
  private String key;
  private String name;
  private String description;
  private Boolean visible;
  private String groupName;
  private Boolean groupLeader;
  private Integer groupMembers;
  private ValueJSO value;

  // The module that sources this class. Must be present.
  @Override
  public String getModuleName() {
     return "edu.colorado.csdms.wmt.WMT";
  }

  /**
   * A JSNI method that defines a fixture for testing. Returns a basic
   * {@link ParameterJSO} object.
   */
  private native ParameterJSO testBasicParameterJSO(String key, String name,
      String description, Boolean visible, ValueJSO value) /*-{
		return {
			"key" : key,
			"name" : name,
			"description" : description,
			"visible" : visible,
			"value" : value
		};
  }-*/;

  /**
   * A JSNI method that defines a fixture for testing. Returns a
   * {@link ParameterJSO} object with a group.
   */
  private native ParameterJSO testGroupParameterJSO(String key, String name,
      String description, String groupName, Boolean groupLeader,
      Integer groupMembers, ValueJSO value) /*-{
		return {
			"key" : key,
			"name" : name,
			"description" : description,
			"group" : {
				"name" : groupName,
				"leader" : groupLeader,
				"members" : groupMembers
			},
			"value" : value
		};
  }-*/;

  /**
   * A JSNI method that defines a fixture for the tests. Returns a
   * {@link ValueJSO} object for testing.
   */
  private native ValueJSO testValueJSO() /*-{
		return {
			"default" : 500,
			"range" : {
				"max" : 2147483647,
				"min" : 0
			},
			"type" : "int"
		};
  }-*/;
  
  @Before
  @Override
  protected void gwtSetUp() throws Exception {
    key = "number_of_rows";
    name = "Number of rows";
    description = "Number of rows in the computational grid";
    groupName = "parameter1";
    groupLeader = true;
    groupMembers = 3;
    visible = true;
    value = testValueJSO();
    basicParam = testBasicParameterJSO(key, name, description, visible, value);
    groupParam =
        testGroupParameterJSO(key, name, description, groupName, groupLeader,
            groupMembers, value);
  }

  @After
  @Override
  protected void gwtTearDown() throws Exception {
  }

  @Test
  public void testGetKey() {
    assertEquals(key, basicParam.getKey());
  }

  @Test
  public void testGetName() {
    assertEquals(name, basicParam.getName());
  }

  @Test
  public void testGetDescription() {
    assertEquals(description, basicParam.getDescription());
  }

  @Test
  public void testHasGroup() {
    assertTrue(groupParam.hasGroup());
  }

  @Test
  public void testGetGroupName() {
    assertEquals(groupName, groupParam.getGroupName());
  }

  @Test
  public void testIsGroupLeader() {
    assertEquals(groupLeader, (Boolean) groupParam.isGroupLeader());
  }

  @Test
  public void testNGroupMembers() {
    assertEquals(groupMembers, (Integer) groupParam.nGroupMembers());
  }

  @Test
  public void testGetIsVisible() {
    assertTrue(basicParam.isVisible());
  }

  @Test
  public void testSetIsVisible() {
    basicParam.isVisible(false);
    assertFalse(basicParam.isVisible());
  }

  @Test
  public void testGetValue() {
    assertSame(value, basicParam.getValue());
  }
}
