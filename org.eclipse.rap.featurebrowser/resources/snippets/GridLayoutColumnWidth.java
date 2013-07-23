package snippets;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

public class GridLayoutColumnWidth extends AbstractEntryPoint {

  private boolean equalWidth = false;

  @Override
  protected void createContents( Composite parent ) {
    parent.setLayout( new GridLayout( 3, equalWidth ) );
    createLabel( parent, 110, 50, SWT.COLOR_YELLOW );
    createLabel( parent, 170, 150, SWT.COLOR_CYAN );
    createLabel( parent, 120, 30, SWT.COLOR_GREEN );
    createLabel( parent, 90, 250, SWT.COLOR_YELLOW );
    createLabel( parent, 80, 150, SWT.COLOR_CYAN );
    createLabel( parent, 190, 240, SWT.COLOR_GREEN );
    createCheckBox( parent );
  }

  private static void createLabel( Composite parent, int width, int height, int color ) {
    Label label = new Label( parent, SWT.NONE );
    label.setLayoutData( new GridData( width, height ) );
    label.setText( width + " * " + height );
    label.setBackground( label.getDisplay().getSystemColor( color ) );
  }

  private void createCheckBox( final Composite parent ) {
    final Button button = new Button( parent, SWT.CHECK );
    button.setSelection( equalWidth );
    button.setText( "Equal columns" );
    button.addListener( SWT.Selection, new Listener() {
      public void handleEvent( Event event ) {
        equalWidth = button.getSelection();
        Control[] children = parent.getChildren();
        for( Control child : children ) {
          child.dispose();
        }
        createContents( parent );
        parent.layout();
      }
    } );
  }

}
