package snippets;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


public class LabelAsSeparator extends AbstractEntryPoint {

  @Override
  protected void createContents( Composite parent ) {
    parent.setLayout( new GridLayout( 2, false ) );
    new Label( parent, SWT.NONE ).setText( "A Label can be a separator:" );
    Label hSeparator = new Label( parent, SWT.SEPARATOR | SWT.HORIZONTAL );
    hSeparator.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
    new Label( parent, SWT.NONE ).setText( "Also vertical:" );
    Label vSeparator = new Label( parent, SWT.SEPARATOR | SWT.VERTICAL );
    vSeparator.setLayoutData( new GridData( SWT.LEFT, SWT.FILL, false, true ) );
  }

}
