package snippets;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


public class GridLayoutAlignment extends AbstractEntryPoint {

  @Override
  protected void createContents( Composite parent ) {
    parent.setLayout( new GridLayout( 3, true ) );
    createLabel( parent, SWT.LEFT, SWT.TOP, "LEFT/TOP" );
    createLabel( parent, SWT.CENTER, SWT.TOP, "CENTER/TOP" );
    createLabel( parent, SWT.RIGHT, SWT.TOP, "RIGHT/TOP" );
    createLabel( parent, SWT.LEFT, SWT.CENTER, "LEFT/CENTER" );
    createLabel( parent, SWT.CENTER, SWT.CENTER, "CENTER/CENTER" );
    createLabel( parent, SWT.RIGHT, SWT.CENTER, "RIGHT/CENTER" );
    createLabel( parent, SWT.LEFT, SWT.BOTTOM, "LEFT/BOTTOM" );
    createLabel( parent, SWT.CENTER, SWT.BOTTOM, "CENTER/BOTTOM" );
    createLabel( parent, SWT.RIGHT, SWT.BOTTOM, "RIGHT/BOTTOM" );
  }

  public void createLabel( Composite parent, int hAlign, int vAlign, String text ) {
    Label label = new Label( parent, SWT.BORDER );
    label.setLayoutData( new GridData( hAlign, vAlign, true, true ) );
    label.setText( text );
  }

}
