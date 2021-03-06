/**
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014 mcflugen
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package edu.colorado.csdms.wmt.client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

import edu.colorado.csdms.wmt.client.data.ValueJSO;

/**
 * Tests for {@link ValueJSO} of type "int". JUnit integration is provided by
 * extending {@link GWTTestCase}.
 * 
 * @see http://www.gwtproject.org/doc/latest/DevGuideTesting.html
 * @see http://www.gwtproject.org/doc/latest/tutorial/JUnit.html
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class ValueJSOIntTest extends GWTTestCase {

  private ValueJSO jso;
  private String type = "int";
  private int defaultValue;
  private int max;
  private int min;

  /**
   * The module that sources this class. Must be present.
   */
  @Override
  public String getModuleName() {
     return "edu.colorado.csdms.wmt.WMT";
  }

  /**
   * A JSNI method that defines a fixture for the tests. Returns a
   * {@link ValueJSO} object for testing.
   * 
   * @param defaultValue
   * @param max
   * @param min
   * @param type
   */
  private native ValueJSO testValueJSO(int defaultValue, int max, int min,
      String type) /*-{
		return {
			"default" : defaultValue,
			"range" : {
				"max" : max,
				"min" : min
			},
			"type" : type
		};
  }-*/;
  
  @Before
  @Override
  protected void gwtSetUp() throws Exception {
    defaultValue = 500;
    max = 2147483647;
    min = 0;
    jso = testValueJSO(defaultValue, max, min, type);
  }

  @After
  @Override
  protected void gwtTearDown() throws Exception {
  }

  /*
   * Test getting type.
   */
  @Test
  public void testGetType() {
    assertEquals(type, jso.getType());
  }

  /*
   * Test getting default.
   */
  @Test
  public void testGetDefault() {
    int theDefault = Integer.valueOf(jso.getDefault());
    assertEquals(defaultValue, theDefault);    
  }

  /*
   * Test setting default.
   */
  @Test
  public void testSetDefault() {
    int newDefault = 1000;
    String newDefaultString = Integer.toString(newDefault);
    jso.setDefault(newDefault);
    assertEquals(newDefaultString, jso.getDefault());
  }

  /*
   * Test getting units. Should return null if not present.
   */
  @Test
  public void testGetUnits() {
    assertNull(jso.getUnits());
  }
  
  /*
   * Test getting min.
   */
  @Test
  public void testGetMin() {
    int theMin = Integer.valueOf(jso.getMin());
    assertEquals(min, theMin);
  }

  /*
   * Test getting max.
   */
  @Test
  public void testGetMax() {
    int theMax = Integer.valueOf(jso.getMax());
    assertEquals(max, theMax);
  }
  
  /*
   * Test getting choices. Should return null if not present.
   */
  @Test
  public void testGetChoices() {
    assertNull(jso.getChoices());
  }
  
  /*
   * Test getting files. Should return null if not present.
   */
  @Test
  public void testGetFiles() {
    assertNull(jso.getFiles());
  }
}
