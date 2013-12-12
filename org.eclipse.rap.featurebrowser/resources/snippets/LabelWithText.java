package snippets;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


public class LabelWithText extends AbstractEntryPoint {

  @Override
  protected void createContents( Composite parent ) {
    Label label = new Label( parent, SWT.NONE );
    label.setText( "Hello RAP!" );
  }

}
