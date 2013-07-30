package org.eclipse.rap.featurebrowser;

import static org.eclipse.rap.featurebrowser.GridDataUtil.applyGridData;
import static org.eclipse.rap.featurebrowser.GridLayoutUtil.applyGridLayout;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.rap.json.JsonArray;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

public class FeatureBrowser extends AbstractEntryPoint {

    @Override
    protected void createContents( Composite parent ) {
      applyGridLayout( parent );
      createHeader( parent );
      Composite main = new Composite( parent, SWT.NONE );
      applyGridLayout( main ).cols( 2 );
      applyGridData( main ).fill();
      InputStream resource = getClass().getClassLoader().getResourceAsStream( "features.json" );
      InputStreamReader reader = new InputStreamReader( resource );
      JsonArray jsonObject = null;
      try {
        jsonObject = JsonArray.readFrom( reader );
        reader.close();
        Feature features = new Feature( jsonObject );
        FeatureTree featureTree = new FeatureTree( main, features );
        Navigation.getInstance().init( featureTree );
      } catch( IOException e ) {
        throw new RuntimeException( e );
      }
    }

    public void createHeader( Composite parent ) {
      Composite header = new Composite( parent, SWT.NONE );
      header.setData( RWT.CUSTOM_VARIANT, "header" );
      applyGridData( header ).fill().vGrab( false ).height( 45 );
      applyGridLayout( header ).cols( 2 ).marginLeft( 10 );
      header.setBackgroundMode( SWT.INHERIT_FORCE );
      Label headerLabel = new Label( header, SWT.NONE );
      headerLabel.setText( ">>  REMOTE APPLICATION PLATFORM FEATURE BROWSER" );
      headerLabel.setData( RWT.CUSTOM_VARIANT, "headerLabel" );
      applyGridData( headerLabel ).fill().hAlign( SWT.LEFT ).vAlign( SWT.CENTER );
      Button help = new Button( header, SWT.PUSH );
      help.addListener( SWT.Selection, new Listener() {
        public void handleEvent( Event event ) {
          new Help( getShell() );
        }
      } );
      help.setText( "Getting started..." );
      applyGridData( help ).hAlign( SWT.RIGHT ).vAlign( SWT.CENTER ).vGrab();
      help.setData( RWT.CUSTOM_VARIANT, "helpButton" );
      Composite subheader = new Composite( parent, SWT.NONE );
      subheader.setData( RWT.CUSTOM_VARIANT, "subheader" );
      applyGridData( subheader ).horizontalFill().height( 20 );
    }

}
