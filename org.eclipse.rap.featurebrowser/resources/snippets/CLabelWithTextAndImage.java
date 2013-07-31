package snippets;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;


public class CLabelWithTextAndImage extends AbstractEntryPoint {

  @Override
  protected void createContents( Composite parent ) {
    CLabel cLabel = new CLabel( parent, SWT.NONE );
    cLabel.setText( "Hello RAP!" );
    cLabel.setImage( getImage( cLabel.getDisplay() ) );
  }

  private Image getImage( Display display ) {
    InputStream stream = getClass().getClassLoader().getResourceAsStream( "system.png" );
    Image image = new Image( display, stream );
    try {
      stream.close();
    } catch( IOException e ) {
      throw new RuntimeException( e );
    }
    return image;
  }

}
