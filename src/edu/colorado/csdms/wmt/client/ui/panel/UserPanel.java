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
package edu.colorado.csdms.wmt.client.ui.panel;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;

import edu.colorado.csdms.wmt.client.Constants;

/**
 * A HorizontalPanel with a box for displaying a username and a "Sign Out"
 * button.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class UserPanel extends HorizontalPanel {
  
  private HTML loginName;
  private Button signOutButton;
  
  /**
   * Makes a new {@link UserPanel}.
   */
  public UserPanel() {

    this.setStyleName("wmt-UserPanel");
    this.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
 
    loginName = new HTML();
    loginName.setStyleName("wmt-UserPanelButton");

    signOutButton = new Button(Constants.SIGN_OUT);
    signOutButton.setStyleName("wmt-UserPanelButton");
    signOutButton.addStyleDependentName("signOut");

    this.add(loginName);
    this.add(signOutButton);
  }

  public HTML getLoginName() {
    return loginName;
  }

  public void setLoginName(HTML loginName) {
    this.loginName = loginName;
  }

  public Button getSignOutButton() {
    return signOutButton;
  }

  public void setSignOutButton(Button signOutButton) {
    this.signOutButton = signOutButton;
  }
}
