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
package edu.colorado.csdms.wmt.client.ui.handler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.user.client.Window;

import edu.colorado.csdms.wmt.client.Constants;
import edu.colorado.csdms.wmt.client.control.DataManager;
import edu.colorado.csdms.wmt.client.control.DataTransfer;
import edu.colorado.csdms.wmt.client.ui.widgets.QuestionDialogBox;

/**
 * Handles login and logout events in the WMT client.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class AuthenticationHandler implements ClickHandler {

  private DataManager data;

  /**
   * Creates a new {@link AuthenticationHandler}.
   * 
   * @param data the DataManager object for the WMT session
   */
  public AuthenticationHandler(DataManager data) {
    this.data = data;
  }

  /**
   * Prepares and performs the sign-in action.
   */
  public void signIn() {

    // Get WMT username and password from the SignInScreen.
    String username = data.getSignInScreen().getEmailBox().getText();
    data.security.setWmtUsername(username);
    GWT.log("Email: " + data.security.getWmtUsername());
    String password = data.getSignInScreen().getPasswordBox().getText();
    data.security.setWmtPassword(password);
    GWT.log("Password: " + data.security.getWmtPassword());

    // A very basic input check.
    if (username.isEmpty() || password.isEmpty() || !username.contains("@")
        || !username.contains(".")) {
          Window.alert(Constants.LOGIN_ERR);
          return;
    }

    // Authenticate the user with the server.
    DataTransfer.login(data);
  }
  
  public void signOut() {
    ;
  }
  
  @Override
  public void onClick(ClickEvent event) {

    if (data.security.isLoggedIn()) {

      final QuestionDialogBox questionDialog =
          new QuestionDialogBox(Constants.QUESTION_SIGN_OUT);
      questionDialog.getChoicePanel().getOkButton().setHTML(Constants.SIGN_OUT);

      // Define handlers.
      ClickHandler okHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
          DataTransfer.logout(data);
          questionDialog.hide();
        }
      };
      DialogCancelHandler cancelHandler =
          new DialogCancelHandler(questionDialog);

      // Apply handlers to OK and Cancel buttons.
      questionDialog.getChoicePanel().getOkButton().addClickHandler(okHandler);
      questionDialog.getChoicePanel().getCancelButton().addClickHandler(
          cancelHandler);

      // Also apply handlers to "Enter" and "Esc" keys.
      questionDialog
          .addDomHandler(new ModalKeyHandler(okHandler, cancelHandler),
              KeyDownEvent.getType());

      questionDialog.center();
      questionDialog.getChoicePanel().getOkButton().setFocus(true);

    }
  }
}
