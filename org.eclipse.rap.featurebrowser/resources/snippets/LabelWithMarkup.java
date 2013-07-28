package snippets;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


public class LabelWithMarkup extends AbstractEntryPoint {

  @Override
  protected void createContents( Composite parent ) {
    Label label = new Label( parent, SWT.NONE );
    label.setData( RWT.MARKUP_ENABLED, Boolean.TRUE );
    label.setText(   "This <i>is</i> <b>text</b> <big>with</big> "
                   + "<span style='color:red'>some</span> "
                   + "<code>HTML</code> tags." );
  }

}
