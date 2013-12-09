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
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;

public class FeatureBrowser extends AbstractEntryPoint {

    private FeatureTree featureTree;
    private Feature features;
    private Sash sash;

    @Override
    protected void createContents( Composite parent ) {
      applyGridLayout( parent );
      createHeader( parent );
      Composite main = new Composite( parent, SWT.NONE );
      applyGridData( main ).fill();
      // TODO : apply margin within the main widgets, otherwise shadows get cut off
      applyGridLayout( main ).margin( 8 ).cols( 3 );
      style( main ).as( "main" );
      loadFeatures();
      createFeatureTree( main );
      createSash( main );
    }

    private void createSash( Composite main ) {
      // do not use a SashForm since the tree width should be independent from the parent width
      sash = new Sash( main, SWT.VERTICAL );
      applyGridData( sash ).verticalFill().width( 8 );
      sash.addListener( SWT.Selection, new Listener() {
        public void handleEvent( Event event ) {
          if( event.detail != SWT.DRAG ) {
            int treeWidth = event.x;
            applyGridData( featureTree.getControl() ).verticalFill().width( treeWidth );
            sash.getParent().layout();
          }
        }
      } );
    }

    private void createFeatureTree( Composite main ) {
      featureTree = new FeatureTree( main, features );
      Navigation.getInstance().init( featureTree );
      applyGridData( featureTree.getControl() ).verticalFill().width( 200 );
    }

    private void loadFeatures() {
      // TODO : when deployed it should suffice to read these once on application start
      InputStream resource = getClass().getClassLoader().getResourceAsStream( "features.json" );
      InputStreamReader reader = new InputStreamReader( resource );
      try {
        JsonArray jsonObject = JsonArray.readFrom( reader );
        reader.close();
        features = new Feature( jsonObject, true );
      } catch( IOException e ) {
        throw new RuntimeException( e );
      }
    }

    public void createHeader( Composite parent ) {
      Composite header = new Composite( parent, SWT.NONE );
      style( header ).as( "header" );
      applyGridData( header ).fill().vGrab( false ).height( 50 );
      applyGridLayout( header ).cols( 2 ).marginLeft( 15 ).marginTop( 4 );
      header.setBackgroundMode( SWT.INHERIT_FORCE );
      Label headerLabel = new Label( header, SWT.NONE );
      headerLabel.setText( "RAP Feature Browser " );
      style( headerLabel ).as( "headerLabel" );
      applyGridData( headerLabel ).fill().hAlign( SWT.LEFT ).vAlign( SWT.CENTER );
      Button help = new Button( header, SWT.PUSH );
      help.addListener( SWT.Selection, new Listener() {
        public void handleEvent( Event event ) {
          new HelpOverlay( getShell() );
        }
      } );
      help.setText( "Getting started" );
      applyGridData( help ).hAlign( SWT.RIGHT ).vAlign( SWT.CENTER ).vGrab();
      style( help ).as( "helpButton" );
    }

}
