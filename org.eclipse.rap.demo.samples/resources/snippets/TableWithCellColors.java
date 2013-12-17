package snippets;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;


public class TableWithCellColors extends AbstractEntryPoint {

  private static final int COLUMN_COUNT = 3;
  private static final int ITEM_COUNT = 15;

  private static Color[] seriesColors;

  @Override
  protected void createContents( Composite parent ) {
    initColors( parent.getDisplay() );
    Table table = new Table( parent, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION );
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
        item.setBackground( j, seriesColors[ ( i + j ) % seriesColors.length ] );
      }
    }
  }

  private static void initColors( Display display ) {
    seriesColors = new Color[] {
      null,
      new Color( display, 239, 41, 41 ),
      new Color( display, 233, 185, 110 ),
      new Color( display, 252, 233, 79 ),
      new Color( display, 114, 159, 207 ),
      new Color( display, 173, 127, 168 ),
      new Color( display, 173, 127, 168 ),
      new Color( display, 252, 175, 62 ),
      new Color( display, 238, 238, 236 ),
      new Color( display, 156, 159, 153 ),
      new Color( display, 138, 226, 52 ),
    };
  }

}
