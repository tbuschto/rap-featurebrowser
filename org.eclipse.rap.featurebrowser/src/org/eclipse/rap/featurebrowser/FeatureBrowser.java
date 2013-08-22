package org.eclipse.rap.featurebrowser;

import static org.eclipse.rap.featurebrowser.util.GridDataUtil.*;
import static org.eclipse.rap.featurebrowser.util.GridLayoutUtil.*;
import static org.eclipse.rap.featurebrowser.util.StyleUtil.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.rap.featurebrowser.ui.FeatureTree;
import org.eclipse.rap.featurebrowser.ui.HelpOverlay;
import org.eclipse.rap.json.JsonArray;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;

public class FeatureBrowser extends AbstractEntryPoint {

    private FeatureTree featureTree;
    private Feature features;
    private Button vbar;
    private Sash sash;

    @Override
    protected void createContents( Composite parent ) {
      applyGridLayout( parent );
      createHeader( parent );
      Composite main = new Composite( parent, SWT.NONE );
      main.setData( RWT.CUSTOM_VARIANT, "featuretree" );
      applyGridData( main ).fill();
      applyGridLayout( main ).cols( 4 ).margin( 5 );
      loadFeatures();
      createFeatureTree( main );
      createVBar( main );
      createSash( main );
    }

    private void createSash( Composite main ) {
      sash = new Sash( main, SWT.VERTICAL );
      applyGridData( sash ).verticalFill().width( 1 );
      sash.addListener( SWT.Selection, new Listener() {
        public void handleEvent( Event event ) {
          if( event.detail != SWT.DRAG ) {
            int treeWidth = event.x - vbar.getSize().x;
            applyGridData( featureTree.getControl() ).verticalFill().width( treeWidth );
            sash.getParent().layout();
          }
        }
      } );
    }

    private void createVBar( Composite main ) {
      vbar = new Button( main, SWT.PUSH | SWT.CENTER );
      vbar.setText( "<" );
      applyGridData( vbar ).verticalFill().width( 20 );
      vbar.setData( RWT.CUSTOM_VARIANT, "vbar" );
      vbar.addListener( SWT.Selection, new Listener() {
        public void handleEvent( Event event ) {
          boolean visible = !featureTree.getControl().getVisible();
          vbar.setText( visible ? "<" : ">" );
          GridData data = ( GridData )featureTree.getControl().getLayoutData();
          data.exclude = !visible;
          sash.setEnabled( visible );
          featureTree.getControl().setVisible( visible );
          featureTree.getControl().getParent().layout();
        }
      } );
    }

    private void createFeatureTree( Composite main ) {
      featureTree = new FeatureTree( main, features );
      Navigation.getInstance().init( featureTree );
      applyGridData( featureTree.getControl() ).verticalFill().width( 160 );
    }

    private void loadFeatures() {
      InputStream resource = getClass().getClassLoader().getResourceAsStream( "features.json" );
      InputStreamReader reader = new InputStreamReader( resource );
      try {
        JsonArray jsonObject = JsonArray.readFrom( reader );
        reader.close();
        features = new Feature( jsonObject );
      } catch( IOException e ) {
        throw new RuntimeException( e );
      }
    }

    public void createHeader( Composite parent ) {
      Composite header = new Composite( parent, SWT.NONE );
      style( header ).as( "header" );
      applyGridData( header ).fill().vGrab( false ).height( 45 );
      applyGridLayout( header ).cols( 2 ).marginLeft( 10 );
      header.setBackgroundMode( SWT.INHERIT_FORCE );
      Label headerLabel = new Label( header, SWT.NONE );
      headerLabel.setText( ">>  REMOTE APPLICATION PLATFORM FEATURE BROWSER" );
      style( headerLabel ).as( "headerLabel" );
      applyGridData( headerLabel ).fill().hAlign( SWT.LEFT ).vAlign( SWT.CENTER );
      Button help = new Button( header, SWT.PUSH );
      help.addListener( SWT.Selection, new Listener() {
        public void handleEvent( Event event ) {
          new HelpOverlay( getShell() );
        }
      } );
      help.setText( "Getting started..." );
      applyGridData( help ).hAlign( SWT.RIGHT ).vAlign( SWT.CENTER ).vGrab();
      style( help ).as( "helpButton" );
      Composite subheader = new Composite( parent, SWT.NONE );
      style( subheader ).as( "subheader" );
      applyGridData( subheader ).horizontalFill().height( 20 );
    }

}
