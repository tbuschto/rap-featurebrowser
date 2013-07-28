package snippets;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


public class LabelWithImage extends AbstractEntryPoint {

  @Override
  protected void createContents( Composite parent ) {
    Label label = new Label( parent, SWT.NONE );
    label.setImage( getImage( label ) );
  }

  private Image getImage( Label label ) {
    InputStream stream = getClass().getClassLoader().getResourceAsStream( "system.png" );
    Image image = new Image( label.getDisplay(), stream );
    try {
      stream.close();
    } catch( IOException e ) {
      throw new RuntimeException( e );
    }
    return image;
  }

}
