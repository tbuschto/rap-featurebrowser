package snippets;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.rap.rwt.service.ResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;


public class TableWithMarkup extends AbstractEntryPoint {

  private static final ClassLoader CLASSLOADER = TableWithMarkup.class.getClassLoader();

  private Car[] cars;
  private String carImageLocation;
  private String fuelImageLocation;
  private String gearImageLocation;

  @Override
  protected void createContents( Composite parent ) {
    initCars();
    registerImages();
    createTable( parent );
  }

  private void initCars() {
    cars = new Car[] {
      new Car( "Nissan GT-R 3.8 Premium Edition", "2009", "42 000<br/>EUR", "Public Sale" ),
      new Car( "Audi R8 5.2 Gt Quattro", "2011", "300 000<br/>EUR", "Cars Shop Ltd" ),
      new Car( "Ferrari 599 GTB Fiorano F1 2dr", "2010", "169 000<br/>EUR", "Automob GmbH" ),
      new Car( "Ford Focus ZETEC AUTO", "2002", "189 000<br/>EUR", "Motcomp Ltd" ),
      new Car( "Austin 1300 1300", "1972", "145 000<br/>EUR", "Cars Shop Ltd" ),
      new Car( "Mini Cooper 1.6", "2008", "95 000<br/>EUR", "Public Sale" ),
      new Car( "Aston Martin V8 (420)", "2011", "101 000<br/>EUR", "Automob GmbH" ),
      new Car( "Nissan GT-R 3.8 Premium Edition", "2009", "42 000<br/>EUR", "Public Sale" ),
      new Car( "Audi R8 5.2 Gt Quattro", "2011", "300 000<br/>EUR", "Cars Shop Ltd" ),
      new Car( "Ferrari 599 GTB Fiorano F1 2dr", "2010", "169 000<br/>EUR", "Automob GmbH" ),
      new Car( "Ford Focus ZETEC AUTO", "2002", "189 000<br/>EUR", "Motcomp Ltd" ),
      new Car( "Austin 1300 1300", "1972", "145 000<br/>EUR", "Cars Shop Ltd" ),
      new Car( "Mini Cooper 1.6", "2008", "95 000<br/>EUR", "Public Sale" ),
      new Car( "Aston Martin V8 (420)", "2011", "101 000<br/>EUR", "Automob GmbH" ),
      new Car( "Nissan GT-R 3.8 Premium Edition", "2009", "42 000<br/>EUR", "Public Sale" ),
      new Car( "Audi R8 5.2 Gt Quattro", "2011", "300 000<br/>EUR", "Cars Shop Ltd" ),
      new Car( "Ferrari 599 GTB Fiorano F1 2dr", "2010", "169 000<br/>EUR", "Automob GmbH" ),
      new Car( "Ford Focus ZETEC AUTO", "2002", "189 000<br/>EUR", "Motcomp Ltd" ),
      new Car( "Austin 1300 1300", "1972", "145 000<br/>EUR", "Cars Shop Ltd" ),
      new Car( "Mini Cooper 1.6", "2008", "95 000<br/>EUR", "Public Sale" ),
      new Car( "Aston Martin V8 (420)", "2011", "101 000<br/>EUR", "Automob GmbH" ),
      new Car( "Nissan GT-R 3.8 Premium Edition", "2009", "42 000<br/>EUR", "Public Sale" ),
      new Car( "Audi R8 5.2 Gt Quattro", "2011", "300 000<br/>EUR", "Cars Shop Ltd" ),
      new Car( "Ferrari 599 GTB Fiorano F1 2dr", "2010", "169 000<br/>EUR", "Automob GmbH" ),
      new Car( "Ford Focus ZETEC AUTO", "2002", "189 000<br/>EUR", "Motcomp Ltd" ),
      new Car( "Austin 1300 1300", "1972", "145 000<br/>EUR", "Cars Shop Ltd" ),
      new Car( "Mini Cooper 1.6", "2008", "95 000<br/>EUR", "Public Sale" ),
      new Car( "Aston Martin V8 (420)", "2011", "101 000<br/>EUR", "Automob GmbH" )
    };
  }

  private void registerImages() {
    try {
      fuelImageLocation = registerImage( "resources/fuel.png" );
      gearImageLocation = registerImage( "resources/gear.png" );
      carImageLocation = registerImage( "resources/car.png" );
    } catch( IOException exception ) {
      throw new RuntimeException( "Failed to register images", exception );
    }
  }

  private void createTable( Composite comp ) {
    Table table = new Table( comp, SWT.FULL_SELECTION | SWT.BORDER );
    table.setData( RWT.MARKUP_ENABLED, Boolean.TRUE );
    table.setData( RWT.CUSTOM_ITEM_HEIGHT, Integer.valueOf( 80 ) );
    table.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
    table.setHeaderVisible( true );
    table.setLinesVisible( true );
    createColumn( table, "Brand/Model", 410, SWT.LEFT );
    createColumn( table, "Year", 100, SWT.CENTER );
    createColumn( table, "Price", 150, SWT.CENTER );
    createColumn( table, "Distributor", 300, SWT.LEFT );
    createItems( table );
  }

  private void createItems( Table table ) {
    for( int i = 0; i < cars.length; i++ ) {
      TableItem item = new TableItem( table, SWT.NONE );
      item.setText( 0, formatData( cars[ i ].getBrand(), 0 ) );
      item.setText( 1, formatData( cars[ i ].getYear(), 1 ) );
      item.setText( 2, formatData( cars[ i ].getPrice(), 2 ) );
      item.setText( 3, formatData( cars[ i ].getDistributor(), 3 ) );
    }
  }

  private String formatData( String text, int column ) {
    StringBuilder builder = new StringBuilder();
    String imageUrl = getImageUrl( carImageLocation );
    switch( column ) {
      case 0:
        builder.append( "<img src=\"" );
        builder.append( imageUrl );
        builder.append( "\" style=\"float:left;padding:5px\" width=\"130\" height=\"70\" />" );
        builder.append( "<b>" );
        builder.append( text );
        builder.append( "</b>" );
        builder.append( "<small><br/>" );
        builder.append( createGearboxImageTag() );
        builder.append( "<i>Gearbox: Manual</i><br/>" );
        builder.append( createFuelImageTag() );
        builder.append( "<i>Fuel Type: Petrol</i>" );
        builder.append( "<br/><a style='color:#' href=\"https://www.google.com/search?q=" );
        builder.append( text );
        builder.append( "\" target=\"_blank\">More Info</a>" );
        builder.append( "</small>" );
      break;
      case 1:
        builder.append( "<b>" );
        builder.append( text );
        builder.append( "</b>" );
      break;
      case 2:
        builder.append( "<span>" );
        builder.append( text );
        builder.append( "</span>" );
      break;
      case 3:
        builder.append( text );
        builder.append( "<br/><em><small>" );
        builder.append( "Contacts: none" );
        builder.append( "</small></em>" );
      break;
    }
    return builder.toString();
  }

  private String createGearboxImageTag() {
    String imageUrl = getImageUrl( gearImageLocation );
    return "<img src='" + imageUrl + "' width='10' height='10' style='padding-right:5px'/>";
  }

  private String createFuelImageTag() {
    String imageUrl = getImageUrl( fuelImageLocation );
    return "<img src='" + imageUrl + "' width='10' height='10' style='padding-right:5px'/>";
  }

  private static TableColumn createColumn( Table table, String name, int width, int alignment ) {
    TableColumn column = new TableColumn( table, SWT.NONE );
    column.setText( name );
    column.setWidth( width );
    column.setAlignment( alignment );
    return column;
  }

  private static String registerImage( String resourceName ) throws IOException {
    ResourceManager resourceManager = RWT.getResourceManager();
    if( !resourceManager.isRegistered( resourceName ) ) {
      InputStream inputStream = CLASSLOADER.getResourceAsStream( resourceName );
      if( inputStream == null ) {
        throw new RuntimeException( "Resource not found" );
      }
      try {
        resourceManager.register( resourceName, inputStream );
      } finally {
        inputStream.close();
      }
    }
    return resourceManager.getLocation( resourceName );
  }

  private static String getImageUrl( String location ) {
    return RWT.getRequest().getContextPath() + "/" + location;
  }

  private class Car {

    private final String brand;
    private final String year;
    private final String price;
    private final String distributor;

    public Car( String brand, String year, String price, String distributor ) {
      this.brand = brand;
      this.year = year;
      this.price = price;
      this.distributor = distributor;
    }

    public String getBrand() {
      return brand;
    }

    public String getYear() {
      return year;
    }

    public String getPrice() {
      return price;
    }

    public String getDistributor() {
      return distributor;
    }

  }

}
