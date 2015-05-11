package edu.colorado.csdms.wmt.client.ui.panel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.colorado.csdms.wmt.client.Constants;

/**
 * Makes a {@link VerticalPanel} holding "question" and "answer" panels, with
 * the "answer" panel initially hidden. Clicking the "question" panel toggles
 * the visibility of the answer.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class SignInInfoPanel extends VerticalPanel implements ClickHandler {

  private HTML question;
  private HTML answer;
  private HorizontalPanel answerPanel;
  
  /**
   * Makes a new {@link SignInInfoPanel}.
   */
  public SignInInfoPanel(String questionText, String answerText) {

    this.setWidth("100%");
    this.setHorizontalAlignment(ALIGN_CENTER);
    this.addDomHandler(this, ClickEvent.getType());
    
    question = new HTML(Constants.FA_RARROW + questionText);
    question.setStyleName("wmt-SignInScreenLinks");
    HorizontalPanel questionPanel = new HorizontalPanel();
    questionPanel.add(question);
    
    answer = new HTML(answerText);
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
   * Handler to toggle the visibility of the answer text.
   */
  @Override
  public void onClick(ClickEvent event) {
    answerPanel.setVisible(!answerPanel.isVisible());
  }
}
