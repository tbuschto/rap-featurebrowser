package snippets;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;


public class BrowserEmbeddingWebsite extends AbstractEntryPoint {

  @Override
  protected void createContents( Composite parent ) {
    Browser browser = new Browser( parent, SWT.NONE );
    browser.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
    browser.setUrl( "http://www.eclipse.org/rap" );
  }

}
