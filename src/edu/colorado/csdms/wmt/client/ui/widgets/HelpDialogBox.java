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
package edu.colorado.csdms.wmt.client.ui.widgets;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.colorado.csdms.wmt.client.Constants;
import edu.colorado.csdms.wmt.client.control.DataManager;
import edu.colorado.csdms.wmt.client.ui.handler.DialogCancelHandler;

/**
 * A dialog box that shows information about WMT.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class HelpDialogBox extends DialogBox {

  @SuppressWarnings("unused")
  private DataManager data;
  private ClosePanel closePanel;

  /**
   * Displays a {@link HelpDialogBox}.
   */
  public HelpDialogBox(DataManager data) {

    super(false); // autohide
    this.setModal(true);
    this.setStyleName("wmt-DialogBox");
    this.setText("Help / About WMT");
    this.data = data;

    VerticalPanel contents = new VerticalPanel();
    contents.setWidth("30em");
    contents.getElement().getStyle().setMargin(1.0, Unit.EM);

    // Anchors in the help text.
    String websiteAnchor = "<a href='" + Constants.CSDMS_HOME 
        + "' target=\"CSDMS\">website</a>";
    String emailAnchor = "<a href='mailto:" + Constants.CSDMS_EMAIL 
        + "'>email</a>";
    String helpAnchor = "<a href='" + Constants.WMT_HELP
        + "' target=\"WMT_help\">WMT Help</a>";
    String tutorialAnchor = "<a href='" + Constants.WMT_TUTORIAL 
        + "' target=\"WMT_tutorial\">WMT Tutorial</a>";
    String videoAnchor = "<a href='" + Constants.WMT_VIDEO 
        + "' target=\"WMT_video\">this</a>";
    
    // The help text.
    String websiteText = "<p>For more information on CSDMS, please visit our " 
        + websiteAnchor + ", or " + emailAnchor + " us.</p>";
    String helpText = "<p>For a detailed description of the WMT interface,"
        + " please see " + helpAnchor + ". For a brief tutorial,"
        + " see " + tutorialAnchor + ". For a demonstration of using WMT,"
        + " check out " + videoAnchor + " YouTube video.</p>";

    // The help text as HTML widgets.
    String wmtTitle = "<h2>WMT</h2><p><b>The CSDMS Web Modeling Tool</b></br>"
      + "Version: " + data.config.getVersion() 
      + " (" + data.config.getReleaseDate() + ")</p>";
    HTML titleHtml = new HTML(wmtTitle);
    HTML websiteHtml = new HTML(websiteText);
    HTML helpHtml = new HTML(helpText);

    titleHtml.setStyleName("wmt-Label");
    websiteHtml.setStyleName("wmt-Label");
    helpHtml.setStyleName("wmt-Label");
    
    contents.add(titleHtml);
    contents.add(websiteHtml);
    contents.add(helpHtml);

    contents.setCellHorizontalAlignment(titleHtml,
        HasHorizontalAlignment.ALIGN_CENTER);

    closePanel = new ClosePanel();
    contents.add(closePanel);

    this.setWidget(contents);

    /*
     * Hides the dialog box.
     */
    closePanel.getButton().addClickHandler(new DialogCancelHandler(this));
  }
}
