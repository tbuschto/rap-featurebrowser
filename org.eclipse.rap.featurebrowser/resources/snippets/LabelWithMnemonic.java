package snippets;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;


public class LabelWithMnemonic extends AbstractEntryPoint {

  @Override
  protected void createContents( Composite parent ) {
    parent.getDisplay().setData( RWT.MNEMONIC_ACTIVATOR, "CTRL+ALT" );
    Label label = new Label( parent, SWT.NONE );
    label.setText( "Press CTRL+ALT+&R to focus:" );
    new Text( parent, SWT.BORDER );
  }

}
