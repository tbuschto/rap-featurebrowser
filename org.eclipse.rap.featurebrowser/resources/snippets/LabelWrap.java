package snippets;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


public class LabelWrap extends AbstractEntryPoint {

  @Override
  protected void createContents( Composite parent ) {
    createNormalLabel( parent );
    createWrappingLabel( parent );
  }

  public void createNormalLabel( Composite parent ) {
    String text = "This Label does not Wrap! The Content is cut off. ";
    Label label = new Label( parent, SWT.BORDER );
    label.setText( text + text );
    label.setLayoutData( new GridData( 400, SWT.DEFAULT ) );
  }

  public void createWrappingLabel( Composite parent ) {
    String text = "This Label does Wrap! The Content is not cut off. ";
    Label label = new Label( parent, SWT.BORDER | SWT.WRAP );
    label.setText( text + text );
    label.setLayoutData( new GridData( 400, SWT.DEFAULT ) );
  }

}
