package snippets;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;


public class GridLayoutBasic extends AbstractEntryPoint {

  @Override
  protected void createContents( Composite parent ) {
    parent.setLayout( new GridLayout( 3, true ) );
    for( int i = 0; i < 9; i++ ) {
      Composite composite = new Composite( parent, SWT.BORDER );
      composite.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
    }
  }

}
