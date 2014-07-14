package edu.colorado.csdms.wmt.client.ui;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.colorado.csdms.wmt.client.Constants;

/**
 * The {@link SignInScreen} is the first thing a user sees on loading WMT. It
 * provides boxes for an email address and an obscured password, a "Sign In"
 * button, links for help, and a label for displaying error messages.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class SignInScreen extends HorizontalPanel {

  private VerticalPanel contents;
  private MultiWordSuggestOracle oracle;
  private SuggestBox emailBox;
  private PasswordTextBox passwordBox;
  private Button signInButton;

  /**
   * Makes a new {@link SignInScreen} for a user to sign in to WMT.
   */
  public SignInScreen() {

    this.setStyleName("wmt-SignInScreen");
    
    // Alignments that apply to all widgets hereafter.
    this.setHorizontalAlignment(ALIGN_CENTER); 
    this.setVerticalAlignment(ALIGN_MIDDLE);
    
    // Organizer for the contents of the SignInScreen.
    contents = new VerticalPanel();
    contents.setHorizontalAlignment(ALIGN_CENTER);
    this.add(contents);
    
    Label title = new Label("The CSDMS Web Modeling Tool");
    title.setStyleName("wmt-SignInScreenTitle");
    
    // Use a Cookie and a SuggestBox to help autocomplete the user's login.
    // TODO Replace with the browser's login autocomplete mechanism.
    oracle = new MultiWordSuggestOracle();
    String storedUsername = Cookies.getCookie(Constants.USERNAME_COOKIE);
    if (storedUsername != null) {
      oracle.add(storedUsername);
    }

    // TextBoxes for entering email and password.
    emailBox = new SuggestBox(oracle);
    emailBox.setStyleName("wmt-SignInScreenBox");
    emailBox.getElement().setAttribute("placeholder", "Email");
    passwordBox = new PasswordTextBox();
    passwordBox.setStyleName("wmt-SignInScreenBox");
    passwordBox.getElement().setAttribute("placeholder", "Password");

    // Push the button. (Galvanize!)
    signInButton = new Button(Constants.SIGN_IN);
    signInButton.setStyleName("wmt-SignInScreenButton");

    // Question links.
    HTML newUser = new HTML("<i class='fa fa-arrow-right'></i> New User?");
    newUser.setStyleName("wmt-SignInScreenLinks");
    HTML forgotPassword = new HTML("<i class='fa fa-arrow-right'></i> Forgot Password?");    
    forgotPassword.setStyleName("wmt-SignInScreenLinks");
    VerticalPanel linksPanel = new VerticalPanel();
    linksPanel.setStyleName("wmt-SignInScreenLinksPanel");
    linksPanel.add(newUser);
    linksPanel.add(forgotPassword);
    
    // Load the contents of the SignInScreen.
    contents.add(title);
    contents.add(emailBox);
    contents.add(passwordBox);
    contents.add(signInButton);
    contents.add(linksPanel);
  }
}
