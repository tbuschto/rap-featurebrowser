package snippets;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;


public class TableWithVirtual extends AbstractEntryPoint {

  private static final int ITEM_COUNT = 10000;

  @Override
  protected void createContents( Composite parent ) {
    final Table table = new Table( parent, SWT.BORDER | SWT.VIRTUAL );
    table.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
    table.setItemCount( ITEM_COUNT );
    table.addListener( SWT.SetData, new Listener() {

      public void handleEvent( Event event ) {
        TableItem item = ( TableItem )event.item;
        int index = table.indexOf( item );
        item.setText( "Item " + index );
      }
    } );
  }

}
