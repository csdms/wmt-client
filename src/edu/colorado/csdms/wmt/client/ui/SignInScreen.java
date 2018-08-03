package edu.colorado.csdms.wmt.client.ui;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
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
import edu.colorado.csdms.wmt.client.control.DataManager;

/**
 * The {@link SignInScreen} is the first thing a user sees on loading WMT. It
 * provides boxes for an email address and an obscured password, a "Sign In"
 * button, links for help, and a label for displaying error messages.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class SignInScreen extends HorizontalPanel {

  private DataManager data;
  private VerticalPanel contents;
  private MultiWordSuggestOracle oracle;
  private SuggestBox emailBox;
  private Button signInButton;
  private HTML errorMessage;
  private HTML registerMessage;

  /**
   * Makes a new {@link SignInScreen} for a user to sign in to WMT.
   * 
   * @param data the DataManager object for the WMT session
   */
  public SignInScreen(DataManager data) {

    this.data = data;
    this.data.setSignInScreen(this);
    
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
    Label title = new Label("Permafrost Benchmark System");
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

    // Push the button. (Galvanize!)
    signInButton = new Button(Constants.SIGN_IN);
    signInButton.setStyleName("wmt-SignInScreenButton");
    
    // Display error messages here.
    errorMessage = new HTML();
    errorMessage.setStyleName("wmt-SignInScreenError");
    
    registerMessage = new HTML("Please sign in with the email used for <a href='https://goo.gl/forms/soVkta4cjCXJFf3K2'>registration</a>.");
    registerMessage.setStyleName("wmt-SignInScreenLinks");

    // Load the contents of the SignInScreen.
    contents.add(title);
    contents.add(emailBox);
    contents.add(signInButton);
    contents.add(errorMessage);
    contents.add(registerMessage);

    /*
     * Perform sign-in action if user hits the "Sign In" button.
     */
    signInButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        SignInScreen.this.data.security.getAuthenticationHandler().signIn();
      }
    });
    
  }

  public SuggestBox getEmailBox() {
    return emailBox;
  }

  public void setEmailBox(SuggestBox emailBox) {
    this.emailBox = emailBox;
  }

  public Button getSignInButton() {
    return signInButton;
  }

  public void setSignInButton(Button signInButton) {
    this.signInButton = signInButton;
  }

  public HTML getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(HTML errorMessage) {
    this.errorMessage = errorMessage;
  }
}
