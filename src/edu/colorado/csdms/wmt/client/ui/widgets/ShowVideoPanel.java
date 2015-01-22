package edu.colorado.csdms.wmt.client.ui.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.colorado.csdms.wmt.client.Constants;

/**
 * Makes a {@link VerticalPanel} displaying a question "What is WMT?". When
 * clicked, it expands to show a link to an explanatory YouTube video.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class ShowVideoPanel extends VerticalPanel implements ClickHandler {

  private HTML question;
  private HTML answer;
  private HorizontalPanel answerPanel;
  
  /**
   * Makes a new {@link ShowVideoPanel}.
   */
  public ShowVideoPanel() {

    this.setWidth("100%");
    this.setHorizontalAlignment(ALIGN_CENTER);
    this.addDomHandler(this, ClickEvent.getType());
    
    question = new HTML(Constants.FA_RARROW + "What is WMT?");
    question.setStyleName("wmt-SignInScreenLinks");
    HorizontalPanel questionPanel = new HorizontalPanel();
    questionPanel.add(question);
    
    answer = new HTML(Constants.SEE_VIDEO_INFO);
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
