package snippets;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;


public class BrowserRenderingHtml extends AbstractEntryPoint {

  @Override
  protected void createContents( Composite parent ) {
    String html = "<html><head></head><body>";
    for( int i = 0; i < 100; i++ ) {
      html += "<p>This is <b>line "+i+"</b></p>";
    }
    html += "</body></html>";

    Browser browser = new Browser( parent, SWT.NONE );
    browser.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
    browser.setText( html );
  }

}
