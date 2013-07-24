package snippets;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;


public class GridLayoutSpan extends AbstractEntryPoint {

  @Override
  protected void createContents( Composite parent ) {
    parent.setLayout( new GridLayout( 3, true ) );
    createComposite( parent, 1, 1 );
    createComposite( parent, 1, 1 );
    createComposite( parent, 1, 3 );
    createComposite( parent, 2, 1 );
    createComposite( parent, 2, 1 );
  }

  private static void createComposite( Composite parent, int hspan, int vspan ) {
    Composite composite = new Composite( parent, SWT.BORDER );
    GridData layoutData = new GridData( SWT.FILL, SWT.FILL, true, true );
    layoutData.horizontalSpan = hspan;
    layoutData.verticalSpan = vspan;
    composite.setLayoutData( layoutData );
  }

}
