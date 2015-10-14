package edu.colorado.csdms.wmt.client.ui.panel;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;

import edu.colorado.csdms.wmt.client.Constants;

/**
 * A HorizontalPanel with a box for displaying a username and a "Sign Out"
 * button.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class UserPanel extends HorizontalPanel {
  
  private Button loginName;
  private Button signOutButton;
  
  /**
   * Makes a new {@link UserPanel}.
   */
  public UserPanel() {

    this.setStyleName("wmt-UserPanel");
    this.setVerticalAlignment(ALIGN_MIDDLE);
 
    loginName = new Button();
    loginName.setStyleName("wmt-UserPanelButton");
    loginName.setEnabled(false);  // soon, preferences!

    signOutButton = new Button(Constants.SIGN_OUT);
    signOutButton.setStyleName("wmt-UserPanelButton");
    signOutButton.addStyleDependentName("signOut");

    this.add(loginName);
    this.add(signOutButton);
  }

  public Button getLoginName() {
    return loginName;
  }

  public void setLoginName(Button loginName) {
    this.loginName = loginName;
  }

  public Button getSignOutButton() {
    return signOutButton;
  }

  public void setSignOutButton(Button signOutButton) {
    this.signOutButton = signOutButton;
  }
}
