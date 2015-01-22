package edu.colorado.csdms.wmt.client.ui.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.colorado.csdms.wmt.client.Constants;

/**
 * Makes a {@link VerticalPanel} displaying a question "Forgot Password?". When
 * clicked, it expands to show instructions for retrieving a lost WMT password.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class ForgotPasswordPanel extends VerticalPanel implements ClickHandler {

  private HTML question;
  private HTML answer;
  private HorizontalPanel answerPanel;
  
  /**
   * Makes a new {@link ForgotPasswordPanel}.
   */
  public ForgotPasswordPanel() {
    
    this.setWidth("100%");
    this.setHorizontalAlignment(ALIGN_CENTER);
    this.addDomHandler(this, ClickEvent.getType());
    
    question = new HTML(Constants.FA_RARROW + "Forgot Password?");
    question.setStyleName("wmt-SignInScreenLinks");
    HorizontalPanel questionPanel = new HorizontalPanel();
    questionPanel.add(question);
    
    answer = new HTML(Constants.FORGOT_PASSWORD_INFO);
    answer.setStyleName("wmt-SignInScreenLinksInfo");
    answerPanel = new HorizontalPanel();
    answerPanel.setStyleName("wmt-SignInScreenLinksInfoPanel");
    answerPanel.setVisible(false);
    answerPanel.add(answer);

    this.add(questionPanel);
    this.add(answerPanel);
  }

  public HorizontalPanel getAnswerPanel() {
    return answerPanel;
  }

  public void setAnswerPanel(HorizontalPanel answerPanel) {
    this.answerPanel = answerPanel;
  }

  /*
   * Handler to toggle the visibility of the answer to "Forgot Password?" text.
   */
  @Override
  public void onClick(ClickEvent event) {
    answerPanel.setVisible(!answerPanel.isVisible());
  }
}
