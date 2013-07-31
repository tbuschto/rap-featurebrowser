package snippets;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;


public class ButtonPush extends AbstractEntryPoint {

  @Override
  protected void createContents( Composite parent ) {
    Image image = getImage( parent.getDisplay() );
    Button buttonOne = new Button( parent, SWT.PUSH );
    buttonOne.setText( "Button One!" );
    Button buttonTwo = new Button( parent, SWT.PUSH );
    buttonTwo.setImage( image );
    Button buttonThree = new Button( parent, SWT.PUSH );
    buttonThree.setText( "Button Three!" );
    buttonThree.setImage( image );
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
