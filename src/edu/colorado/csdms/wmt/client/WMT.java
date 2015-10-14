package edu.colorado.csdms.wmt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import edu.colorado.csdms.wmt.client.control.DataManager;
import edu.colorado.csdms.wmt.client.control.DataTransfer;
import edu.colorado.csdms.wmt.client.ui.Perspective;
import edu.colorado.csdms.wmt.client.ui.SignInScreen;

/**
 * WMT is the CSDMS Web Modeling Tool.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class WMT implements EntryPoint {

  /**
   * This is the entry point method. Initially, it displays the
   * {@link SignInScreen}. In the background, it draws the views that make up
   * the WMT GUI in the {@link Perspective}. It loads information about
   * component models from a set of JSON files on the server, then populates the
   * GUI with this information. After the user passes through the sign-in
   * screen, the WMT GUI is displayed.
   */
  public void onModuleLoad() {

    // Initialize the DataManager object.
    DataManager data = new DataManager();

    // Get the WMT client configuration values.
    DataTransfer.getConfiguration(data);
    
    // Is GWT in development or production mode?
    data.isDevelopmentMode(!GWT.isProdMode() && GWT.isClient());

    // Load WMT's CSS rules. 
    Resources.INSTANCE.css().ensureInjected();

    // Show the sign-in screen.
    SignInScreen signIn = new SignInScreen(data);
    RootLayoutPanel.get().add(signIn);
    
    // Set up the basic framework of views for the GUI. However, don't show the
    // GUI until the user passes through the sign-in screen.
    Perspective perspective = new Perspective(data);
    perspective.initializeModel();
    perspective.initializeParameterTable();
  }
}
