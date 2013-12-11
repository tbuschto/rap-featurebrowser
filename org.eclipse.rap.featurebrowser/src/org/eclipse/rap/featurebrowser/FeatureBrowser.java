package org.eclipse.rap.featurebrowser;

import static org.eclipse.rap.featurebrowser.util.GridDataUtil.*;
import static org.eclipse.rap.featurebrowser.util.GridLayoutUtil.*;
import static org.eclipse.rap.featurebrowser.util.StyleUtil.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.rap.featurebrowser.ui.DemoArea;
import org.eclipse.rap.featurebrowser.ui.FeatureTree;
import org.eclipse.rap.featurebrowser.ui.ResourcesArea;
import org.eclipse.rap.json.JsonArray;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;

public class FeatureBrowser extends AbstractEntryPoint {

    private static final String STR_SHOW_SOURCE = "Show Source";
    private static final String STR_HIDE_SOURCE = "Hide Source";
    private FeatureTree featureTree;
    private Feature features;
    private Feature currentFeature;
    private Composite main;
    private DemoArea demoArea;
    private ResourcesArea resourcesArea;
    private Sash mainSash;
    private int demoWidth = 0;
    private boolean showSource = false;

    @Override
    protected void createContents( Composite parent ) {
      createHeader( parent );
      style( parent ).as( "app" );
      applyGridLayout( parent ).margin( 8 ).verticalSpacing( 8 );
      main = new Composite( parent, SWT.NONE );
      applyGridData( main ).fill();
      applyGridLayout( main ).cols( 5 );
      style( main ).as( "main" );
      loadFeatures();
      createFeatureTree();
      createSash( main );
      createDemoArea();
      mainSash = createSash( main );
      createResourcesArea();
      setResoucesVisible( false );
      demoWidth = 500;
    }

    public Composite getMainComposite() {
      return main;
    }

    public DemoArea getDemoArea() {
      return demoArea;
    }

    public ResourcesArea getResourcesArea() {
      return resourcesArea;
    }

    public Feature getFeatures() {
      return features;
    }

    public Feature getCurrentFeature() {
      return currentFeature;
    }

    public void previousFeature() {
      featureTree.select( currentFeature.getPrevious() );
    }

    public void nextFeature() {
      featureTree.select( currentFeature.getNext() );
    }

    public void setCurrentFeature( Feature feature ) {
      if( feature == null ) {
        throw new IllegalArgumentException();
      }
      if( currentFeature != feature ) {
        demoArea.setFeature( feature );
        resourcesArea.setFeature( feature );
        currentFeature = feature;
        main.layout( true, true );
      }
    }

    private void createDemoArea() {
      demoArea = new DemoArea( this );
      applyGridData( demoArea.getControl() ).verticalFill().width( 500 );
    }

    private void createResourcesArea() {
      resourcesArea = new ResourcesArea( this );
      applyGridData( resourcesArea.getControl() ).fill();
    }

    private Sash createSash( Composite main ) {
      // do not use a SashForm since the tree width should be independent from the parent width
      final Control resizable = main.getChildren()[ main.getChildren().length - 1 ];
      final Sash sash = new Sash( main, SWT.VERTICAL );
      applyGridData( sash ).verticalFill().width( 8 );
      sash.addListener( SWT.Selection, new Listener() {
        public void handleEvent( Event event ) {
          if( event.detail != SWT.DRAG ) {
            int left = resizable.getBounds().x;
            int newWidth = event.x - left;
            applyGridData( resizable ).verticalFill().width( newWidth );
            sash.getParent().layout();
          }
        }
      } );
      return sash;
    }

    private void createFeatureTree() {
      featureTree = new FeatureTree( this );
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
//      Button help = new Button( header, SWT.PUSH );
//      help.addListener( SWT.Selection, new Listener() {
//        public void handleEvent( Event event ) {
//          new HelpOverlay( getShell() );
//        }
//      } );
//      help.setText( "Getting started" );
//      applyGridData( help ).hAlign( SWT.RIGHT ).vAlign( SWT.CENTER ).vGrab();
//      style( help ).as( "helpButton" );
      final Button sourceButton = new Button( header, SWT.PUSH );
      sourceButton.addListener( SWT.Selection, new Listener() {
        public void handleEvent( Event event ) {
          showSource = !showSource;
          setResoucesVisible( showSource && resourcesArea.hasContent() );
          sourceButton.setText( showSource ? STR_HIDE_SOURCE : STR_SHOW_SOURCE );
        }
      } );
      sourceButton.setText( STR_SHOW_SOURCE );
      applyGridData( sourceButton ).hAlign( SWT.RIGHT ).vAlign( SWT.CENTER ).vGrab();
      style( sourceButton ).as( "helpButton" );
    }

    public void setResoucesVisible( boolean visible ) {
      boolean computedVisible = showSource && visible;
      if( resourcesArea.getControl().getVisible() != computedVisible ) {
        resourcesArea.getControl().setVisible( computedVisible );
        GridData gridData = ( GridData )resourcesArea.getControl().getLayoutData();
        gridData.exclude = !computedVisible;
        mainSash.setVisible( computedVisible );
        GridData sashGridData = ( GridData )mainSash.getLayoutData();
        sashGridData.exclude = !computedVisible;
        if( computedVisible ) {
          applyGridData( demoArea.getControl() ).verticalFill().width( demoWidth );
        } else {
          demoWidth = demoArea.getControl().getSize().x;
          applyGridData( demoArea.getControl() ).fill();
        }
        main.layout( true, true );
      }
    }

}
