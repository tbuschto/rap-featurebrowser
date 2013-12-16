package snippets;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;


public class TableWithColumns extends AbstractEntryPoint {

  private static final int ITEM_COUNT = 100;

  @Override
  protected void createContents( Composite parent ) {
    Display display = parent.getDisplay();
    Image images[] = new Image[] {
      display.getSystemImage( SWT.ICON_INFORMATION ),
      display.getSystemImage( SWT.ICON_ERROR ),
      display.getSystemImage( SWT.ICON_QUESTION ),
      display.getSystemImage( SWT.ICON_WARNING ),
    };
    String[] titles = {
      "Information", "Error", "Question", "Warning"
    };
    String[] questions = {
      "who?", "what?", "where?", "when?", "why?"
    };
    Table table = new Table( parent, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION );
    table.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
    table.setLinesVisible( true );
    table.setHeaderVisible( true );
    for( int i = 0; i < titles.length; i++ ) {
      TableColumn column = new TableColumn( table, SWT.NONE );
      column.setText( titles[ i ] );
      column.setImage( images[ i ] );
    }
    for( int i = 0; i < ITEM_COUNT; i++ ) {
      TableItem item = new TableItem( table, SWT.NONE );
      item.setText( 0, "some info" );
      item.setText( 1, "error #" + i );
      item.setText( 2, questions[ i % questions.length ] );
      item.setText( 3, "look out!" );
    }
    for( int i = 0; i < titles.length; i++ ) {
      table.getColumn( i ).pack();
    }
  }

}
