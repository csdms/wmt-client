package edu.colorado.csdms.wmt.client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

import edu.colorado.csdms.wmt.client.data.ConfigurationJSO;

/**
 * Tests for {@link ConfigurationJSO}. JUnit integration is provided by 
 * extending {@link GWTTestCase}.
 * 
 * @see http://www.gwtproject.org/doc/latest/DevGuideTesting.html
 * @see http://www.gwtproject.org/doc/latest/tutorial/JUnit.html
 * @see http://blog.danielwellman.com/2008/08/testing-json-parsing-using-javascript-overlay-types-in-gwt-15.html
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class ConfigurationJSOTest extends GWTTestCase {

  private ConfigurationJSO configurationJSO;
  private String version;
  private String releaseDate;
  private String apiUrl;  
  
  /**
   * The module that sources this class. Must be present.
   */
  @Override
  public String getModuleName() {
     return "edu.colorado.csdms.wmt.WMT";
  }
  
  /**
   * A JSNI method that defines a fixture for the tests. Returns a
   * {@link ConfigurationJSO} object for testing.
   * 
   * @param version
   * @param releaseDate
   * @param apiUrl
   */
  private native ConfigurationJSO testConfigurationJSO(String version, 
      String releaseDate, String apiUrl) /*-{
		return {
			"version" : version,
			"release_date" : releaseDate,
			"api_url" : apiUrl
		}
  }-*/;  
  
  @Before
  @Override
  protected void gwtSetUp() throws Exception {
    version = "0.9.3";
    releaseDate = "2014-07-27";
    apiUrl = "https://csdms.colorado.edu/wmt/api-dev/";
    configurationJSO = testConfigurationJSO(version, releaseDate, apiUrl);
  }

  @After
  @Override
  protected void gwtTearDown() throws Exception {
  }

  @Test
  public void testGetId() {
    assertEquals(version, configurationJSO.getVersion());
  }

  @Test
  public void testGetReleaseDate() {
    assertEquals(releaseDate, configurationJSO.getReleaseDate());
  }
  
  @Test
  public void testGetApiUrl() {
    assertEquals(apiUrl, configurationJSO.getApiUrl());
  }
}
