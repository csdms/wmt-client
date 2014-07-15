package edu.colorado.csdms.wmt.client.ui;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
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

    // Determine offset of contents from top of browser window.
    Integer browserHeight = Window.getClientHeight();
    Double offset = 0.20; // arbitrary, aesthetic
    Double contentOffset = offset * browserHeight;

    // Set width of contents based on browser window width.
    Integer browserWidth = Window.getClientWidth();
    Double widthFraction = 0.40; // arbitrary, aesthetic
    Integer contentWidth = (int) Math.round(widthFraction * browserWidth);

    // Styles and alignments that apply to all widgets hereafter.
    this.setStyleName("wmt-SignInScreen");
    this.setHorizontalAlignment(ALIGN_CENTER);

    // Organizer panel for the contents of the SignInScreen.
    contents = new VerticalPanel();
    contents.setHorizontalAlignment(ALIGN_CENTER);
    contents.setWidth(contentWidth.toString() + "px");
    this.add(contents);

    // The title's margin is used to offset content from the top of the window.
    Label title = new Label("The CSDMS Web Modeling Tool");
    title.setStyleName("wmt-SignInScreenTitle");
    title.getElement().getStyle().setMarginTop(contentOffset, Unit.PX);

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

    // Is this a new user?
    HTML newUser = new HTML(Constants.FA_RARROW + "New User?");
    newUser.setStyleName("wmt-SignInScreenLinks");
    HTML newUserInfo = new HTML(Constants.NEW_USER_INFO);
    newUserInfo.setStyleName("wmt-SignInScreenLinksInfo");
    final HorizontalPanel newUserInfoPanel = new HorizontalPanel();
    newUserInfoPanel.setStyleName("wmt-SignInScreenLinksInfoPanel");
    newUserInfoPanel.add(newUserInfo);
    newUserInfoPanel.setVisible(false);

    // Has the user forgotten their password?
    HTML forgotPassword = new HTML(Constants.FA_RARROW + "Forgot Password?");
    forgotPassword.setStyleName("wmt-SignInScreenLinks");
    HTML forgotPasswordInfo = new HTML(Constants.FORGOT_PASSWORD_INFO);
    forgotPasswordInfo.setStyleName("wmt-SignInScreenLinksInfo");
    final HorizontalPanel forgotPasswordInfoPanel = new HorizontalPanel();
    forgotPasswordInfoPanel.setStyleName("wmt-SignInScreenLinksInfoPanel");
    forgotPasswordInfoPanel.add(forgotPasswordInfo);
    forgotPasswordInfoPanel.setVisible(false);

    // Add the question links above to a panel.
    VerticalPanel linksPanel = new VerticalPanel();
    linksPanel.setHorizontalAlignment(ALIGN_CENTER);
    linksPanel.setStyleName("wmt-SignInScreenLinksPanel");
    linksPanel.add(newUser);
    linksPanel.add(newUserInfoPanel);
    linksPanel.add(forgotPassword);
    linksPanel.add(forgotPasswordInfoPanel);

    // Load the contents of the SignInScreen.
    contents.add(title);
    contents.add(emailBox);
    contents.add(passwordBox);
    contents.add(signInButton);
    contents.add(linksPanel);

    /*
     * Handler to display answer to "New User?" link.
     */
    newUser.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        newUserInfoPanel.setVisible(!newUserInfoPanel.isVisible());
      }
    });

    /*
     * Handler to display answer to "Forgot Password?" link.
     */
    forgotPassword.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        forgotPasswordInfoPanel
            .setVisible(!forgotPasswordInfoPanel.isVisible());
      }
    });
  }
}
