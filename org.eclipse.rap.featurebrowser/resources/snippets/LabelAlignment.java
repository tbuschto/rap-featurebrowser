package snippets;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


public class LabelAlignment extends AbstractEntryPoint {

  @Override
  protected void createContents( Composite parent ) {
    createLabel( parent, SWT.LEFT );
    createLabel( parent, SWT.CENTER );
    createLabel( parent, SWT.RIGHT );
  }

  private static void createLabel( Composite parent, int alignment ) {
    Label label = new Label( parent, SWT.BORDER );
    label.setText( "Hello RAP!" );
    label.setLayoutData( new GridData( 150, 30 ) );
    label.setAlignment( alignment );
  }

}
