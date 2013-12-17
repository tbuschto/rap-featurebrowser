package snippets;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;


public class TableWithFixedColumns extends AbstractEntryPoint {

  private static final int COLUMN_COUNT = 5;
  private static final int ITEM_COUNT = 15;

  @Override
  protected void createContents( Composite parent ) {
    Table table = new Table( parent, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION );
    table.setData( RWT.FIXED_COLUMNS, Integer.valueOf( 2 ) );
    table.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
    table.setLinesVisible( true );
    table.setHeaderVisible( true );
    for( int i = 0; i < COLUMN_COUNT; i++ ) {
      TableColumn column = new TableColumn( table, SWT.NONE );
      column.setText( "Column " + i );
      column.setWidth( 150 );
    }
    for( int i = 0; i < ITEM_COUNT; i++ ) {
      TableItem item = new TableItem( table, SWT.NONE );
      for( int j = 0; j < COLUMN_COUNT; j++ ) {
        item.setText( j, "Item " + i + "," + j );
        if( j < 2 ) {
          item.setBackground( j, parent.getDisplay().getSystemColor( SWT.COLOR_GRAY ) );
        }
      }
    }
  }

}
