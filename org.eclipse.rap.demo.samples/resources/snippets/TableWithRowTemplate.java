package snippets;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.rap.rwt.client.service.UrlLauncher;
import org.eclipse.rap.rwt.template.ImageCell;
import org.eclipse.rap.rwt.template.ImageCell.ScaleMode;
import org.eclipse.rap.rwt.template.Template;
import org.eclipse.rap.rwt.template.TextCell;
import org.eclipse.rap.rwt.widgets.DialogUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;


public class TableWithRowTemplate extends AbstractEntryPoint {

  private static final ClassLoader CLASSLOADER = TableWithRowTemplate.class.getClassLoader();

  private Person[] persons;

  @Override
  protected void createContents( Composite parent ) {
    initPersons( parent.getDisplay() );
    createTable( parent );
  }

  private void initPersons( Display display ) {
    persons = new Person[] {
      new Person( "Adam", "Archer", loadImage( display, "PersonA.png" ), "555 123456", "adam@mail.domain" ),
      new Person( "Barabara", "Baker", loadImage( display, "PersonB.png" ), "555 123456", "barbara@mail.domain" ),
      new Person( "Casper", "Carter", loadImage( display, "PersonC.png" ), "555 123456", "casper@mail.domain" ),
      new Person( "Damien", "Dyer", loadImage( display, "PersonD.png" ), "555 123456", "damien@mail.domain" ),
      new Person( "Edward", "Evans", loadImage( display, "PersonE.png" ), "555 123456", "edward@mail.domain" ),
      new Person( "Frank", "Farmer", loadImage( display, "PersonF.png" ), "555 123456", "frank@mail.domain" ),
      new Person( "Gabriel", "Gardener", loadImage( display, "PersonG.png" ), "555 123456", "gabriel@mail.domain" ),
      new Person( "Hanna", "Hawkins", loadImage( display, "PersonH.png" ), "555 123456", "hawkins@mail.domain" ),
      new Person( "Ian", "Ivanov", loadImage( display, "PersonI.png" ), "555 123456", "ian@mail.domain" )
    };
  }

  private void createTable( Composite comp ) {
    Table table = new Table( comp, SWT.FULL_SELECTION | SWT.BORDER );
    table.setData( RWT.ROW_TEMPLATE, new PersonsTemplate() );
    table.setData( RWT.CUSTOM_ITEM_HEIGHT, Integer.valueOf( 56 ) );
    table.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
    table.addSelectionListener( new SelectionListener( comp ) );
    createColumn( table, "First Name", 200 );
    createColumn( table, "Last Name", 200 );
    createColumn( table, "Phone", 130 );
    createColumn( table, "E-Mail", 180 );
    createItems( table );
  }

  private static TableColumn createColumn( Table table, String name, int width ) {
    TableColumn column = new TableColumn( table, SWT.NONE );
    column.setText( name );
    column.setWidth( width );
    return column;
  }

  private void createItems( Table table ) {
    for( int i = 0; i < persons.length; i++ ) {
      TableItem item = new TableItem( table, SWT.NONE );
      item.setText( 0, persons[ i ].getFirstName() );
      item.setImage( 0, persons[ i ].getImage() );
      item.setText( 1, persons[ i ].getLastName() );
      item.setText( 2, persons[ i ].getPhone() );
      item.setText( 3, persons[ i ].getMail() );
    }
  }

  private static Image loadImage( Display display, String name ) {
    return new Image( display, CLASSLOADER.getResourceAsStream( "/resources/" + name ) );
  }

  private final class SelectionListener extends SelectionAdapter {

    private final Composite parent;

    private SelectionListener( Composite parent ) {
      this.parent = parent;
    }

    @Override
    public void widgetSelected( SelectionEvent e ) {
      if( "phone".equals( e.text ) ) {
        TableItem item = ( TableItem )e.item;
        alert( "Dialing...", "Calling " + item.getText( 2 ) + "!" );
      } else if( "mail".equals( e.text ) ) {
        String mail = ( ( TableItem )e.item ).getText( 3 );
        String firstName = ( ( TableItem )e.item ).getText( 0 );
        UrlLauncher launcher = RWT.getClient().getService( UrlLauncher.class );
        if( launcher != null ) {
          launcher.openURL( "mailto:" + mail + "?subject=RAP%20Rocks!&body=Hello%20" + firstName );
        } else {
          alert( "Now mailing to...", mail );
        }
      } else if( "arrow".equals( e.text ) ) {
        TableItem item = ( TableItem )e.item;
        String firstName = item.getText( 0 );
        alert( "Nothing here", "Lets edit " + firstName + "!" );
      }
    }

    private void alert( String title, String message ) {
      MessageBox messageBox = new MessageBox( parent.getShell(), SWT.ICON_INFORMATION );
      messageBox.setText( title );
      messageBox.setMessage( message );
      DialogUtil.open( messageBox, null );
    }
  }

  private class Person {

    private final String firstName;
    private final String lastName;
    private final Image image;
    private final String phone;
    private final String mail;

    public Person( String firstName, String lastName, Image image, String phone, String mail ) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.image = image;
      this.phone = phone;
      this.mail = mail;
    }

    public String getFirstName() {
      return firstName;
    }

    public String getLastName() {
      return lastName;
    }

    public Image getImage() {
      return image;
    }

    public String getPhone() {
      return phone;
    }

    public String getMail() {
      return mail;
    }

  }

  private class PersonsTemplate extends Template {
    private static final String MY_FONT = "Tahoma, Geneva, sans-serif";

    public PersonsTemplate() {
      super();
      createImageCell();
      createLastNameCell();
      createFirstNameCell();
      createMailLabelCell();
      createMailCell();
      createPhoneLabelCell();
      createSeparatorCell();
      createPhoneCell();
      createArrowIconCell();
    }

    private void createArrowIconCell() {
      ImageCell arrow = new ImageCell( this );
      arrow.setHorizontalAlignment( SWT.RIGHT );
      arrow.setImage( loadImage( Display.getCurrent(), "right.png" ) );
      arrow.setTop( 8 );
      arrow.setWidth( 48 );
      arrow.setRight( 8 );
      arrow.setBottom( 8 );
      arrow.setName( "arrow" );
      arrow.setSelectable( true );
    }

    private void createFirstNameCell() {
      TextCell lastNameCell = new TextCell( this );
      lastNameCell.setHorizontalAlignment( SWT.LEFT );
      lastNameCell.setVerticalAlignment( SWT.TOP );
      lastNameCell.setBindingIndex( 0 );
      lastNameCell.setLeft( 60 );
      lastNameCell.setTop( 5 );
      lastNameCell.setWidth( 180 );
      lastNameCell.setHeight( 40 );
      Font font = new Font( Display.getCurrent(), new FontData( MY_FONT, 20, SWT.NORMAL ) );
      lastNameCell.setFont( font );
    }

    private void createLastNameCell() {
      TextCell firstNameCell = new TextCell( this );
      Font font = new Font( Display.getCurrent(), new FontData( MY_FONT, 14, SWT.NORMAL ) );
      firstNameCell.setFont( font );
      firstNameCell.setHorizontalAlignment( SWT.LEFT );
      firstNameCell.setBindingIndex( 1 );
      firstNameCell.setLeft( 60 );
      firstNameCell.setTop( 30 );
      firstNameCell.setWidth( 180 );
      firstNameCell.setBottom( 8 );
    }

    private void createPhoneLabelCell() {
      TextCell phoneLabelCell = new TextCell( this );
      Font font = new Font( Display.getCurrent(), new FontData( MY_FONT, 14, SWT.BOLD ) );
      phoneLabelCell.setFont( font );
      phoneLabelCell.setHorizontalAlignment( SWT.LEFT );
      phoneLabelCell.setText( "Phone:" );
      phoneLabelCell.setLeft( 250 );
      phoneLabelCell.setTop( 30 );
      phoneLabelCell.setRight( 8 );
      phoneLabelCell.setBottom( 8 );
    }

    private void createPhoneCell() {
      TextCell phoneCell = new TextCell( this );
      Font font = new Font( Display.getCurrent(), new FontData( MY_FONT, 14, SWT.NORMAL ) );
      phoneCell.setFont( font );
      phoneCell.setHorizontalAlignment( SWT.LEFT );
      phoneCell.setBindingIndex( 2 );
      phoneCell.setLeft( 310 );
      phoneCell.setTop( 30 );
      phoneCell.setWidth( 150 );
      phoneCell.setBottom( 8 );
      phoneCell.setName( "phone" );
      phoneCell.setSelectable( true );
    }

    private void createMailLabelCell() {
      TextCell phoneLabelCell = new TextCell( this );
      Font font = new Font( Display.getCurrent(), new FontData( MY_FONT, 14, SWT.BOLD ) );
      phoneLabelCell.setFont( font );
      phoneLabelCell.setHorizontalAlignment( SWT.LEFT );
      phoneLabelCell.setText( "E-Mail:" );
      phoneLabelCell.setLeft( 250 );
      phoneLabelCell.setTop( 8 );
      phoneLabelCell.setRight( 8 );
      phoneLabelCell.setBottom( 8 );
    }

    private void createMailCell() {
      TextCell phoneCell = new TextCell( this );
      Font font = new Font( Display.getCurrent(), new FontData( MY_FONT, 14, SWT.NORMAL ) );
      phoneCell.setFont( font );
      phoneCell.setHorizontalAlignment( SWT.LEFT );
      phoneCell.setBindingIndex( 3 );
      phoneCell.setLeft( 310 );
      phoneCell.setTop( 8 );
      phoneCell.setWidth( 150 );
      phoneCell.setBottom( 8 );
      phoneCell.setName( "mail" );
      phoneCell.setSelectable( true );
    }

    private void createImageCell() {
      ImageCell imageCell = new ImageCell( this );
      imageCell.setBindingIndex( 0 );
      imageCell.setTop( 4 );
      imageCell.setLeft( 4 );
      imageCell.setWidth( 48 );
      imageCell.setHeight( 48 );
      imageCell.setSelectable( true );
      imageCell.setName( "face" );
      imageCell.setScaleMode( ScaleMode.FIT );
    }

    private void createSeparatorCell() {
      TextCell cell = new TextCell( this );
      cell.setLeft( 0 );
      cell.setBottom( 0 );
      cell.setRight( 0 );
      cell.setHeight( 1 );
      cell.setBackground( new Color( Display.getCurrent(), 130, 130, 130 ) );
    }

  }

}
