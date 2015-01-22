package edu.colorado.csdms.wmt.client.ui.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.colorado.csdms.wmt.client.Constants;

/**
 * Makes a {@link VerticalPanel} displaying a question "New User?". When
 * clicked, it expands to show instructions for a new user to sign in to WMT.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class NewUserPanel extends VerticalPanel implements ClickHandler {

  private HTML question;
  private HTML answer;
  private HorizontalPanel answerPanel;
  
  /**
   * Makes a new {@link NewUserPanel}.
   */
  public NewUserPanel() {

    this.setWidth("100%");
    this.setHorizontalAlignment(ALIGN_CENTER);
    this.addDomHandler(this, ClickEvent.getType());
    
    question = new HTML(Constants.FA_RARROW + "New User?");
    question.setStyleName("wmt-SignInScreenLinks");
    HorizontalPanel questionPanel = new HorizontalPanel();
    questionPanel.add(question);
    
    answer = new HTML(Constants.NEW_USER_INFO);
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
   * Handler to toggle the visibility of the answer to "New User?" text.
   */
  @Override
  public void onClick(ClickEvent event) {
    answerPanel.setVisible(!answerPanel.isVisible());
  }
}
