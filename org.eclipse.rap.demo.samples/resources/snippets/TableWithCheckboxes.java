package snippets;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.rap.rwt.widgets.DialogUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;


public class TableWithCheckboxes extends AbstractEntryPoint {

  private static final ClassLoader CLASSLOADER = TableWithCheckboxes.class.getClassLoader();
  private static final int ITEM_COUNT = 15;

  @Override
  protected void createContents( Composite parent ) {
    Table table = new Table( parent, SWT.BORDER | SWT.CHECK );
    table.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
    for( int i = 0; i < ITEM_COUNT; i++ ) {
      TableItem item = new TableItem( table, SWT.NONE );
      item.setText( "Item " + i );
      item.setImage( loadImage( parent.getDisplay(), "icons/internal_browser.gif" ) );
    }
    table.addListener( SWT.Selection, new Listener() {

      public void handleEvent( Event event ) {
        TableItem item = ( TableItem )event.item;
        String checked = item.getChecked() ? "Checked" : "Unchecked";
        MessageBox messageBox = new MessageBox( item.getParent().getShell() );
        messageBox.setMessage( item + " " + ( event.detail == SWT.CHECK ? checked : "Selected" ) );
        DialogUtil.open( messageBox, null );
      }
    } );
  }

  private static Image loadImage( Display display, String name ) {
    return new Image( display, CLASSLOADER.getResourceAsStream( "/resources/" + name ) );
  }

}
