package snippets;

import java.text.Collator;
import java.util.Locale;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;


public class TableWithSortColumn extends AbstractEntryPoint {

  @Override
  protected void createContents( Composite parent ) {
    final Table table = new Table( parent, SWT.BORDER );
    table.setHeaderVisible( true );
    table.setLinesVisible( true );
    final TableColumn column1 = createColumn( table, "Column 1" );
    TableColumn column2 = createColumn( table, "Column 2" );
    createItem( table, "a", "3" );
    createItem( table, "d", "5" );
    createItem( table, "b", "4" );
    createItem( table, "e", "2" );
    createItem( table, "c", "1" );
    Listener sortListener = new Listener() {

      public void handleEvent( Event event ) {
        TableItem[] items = table.getItems();
        Collator collator = Collator.getInstance( Locale.getDefault() );
        TableColumn column = ( TableColumn )event.widget;
        int index = column == column1 ? 0 : 1;
        for( int i = 1; i < items.length; i++ ) {
          String value1 = items[ i ].getText( index );
          for( int j = 0; j < i; j++ ) {
            String value2 = items[ j ].getText( index );
            if( collator.compare( value1, value2 ) < 0 ) {
              String[] values = {
                items[ i ].getText( 0 ), items[ i ].getText( 1 )
              };
              items[ i ].dispose();
              TableItem item = new TableItem( table, SWT.NONE, j );
              item.setText( values );
              items = table.getItems();
              break;
            }
          }
        }
        table.setSortColumn( column );
      }
    };
    column1.addListener( SWT.Selection, sortListener );
    column2.addListener( SWT.Selection, sortListener );
    table.setSortDirection( SWT.UP );
  }

  private static TableColumn createColumn( Table table, String text ) {
    TableColumn column = new TableColumn( table, SWT.NONE );
    column.setWidth( 100 );
    column.setText( text );
    return column;
  }

  private static TableItem createItem( Table table, String... texts ) {
    TableItem item = new TableItem( table, SWT.NONE );
    item.setText( texts );
    return item;
  }

}
