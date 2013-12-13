/*******************************************************************************
 * Copyright (c) 2013 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/

package org.eclipse.rap.demo.samples;

import static org.eclipse.rap.demo.samples.util.GridDataUtil.*;
import static org.eclipse.rap.demo.samples.util.GridLayoutUtil.*;
import static org.eclipse.rap.demo.samples.util.StyleUtil.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.rap.demo.samples.ui.DemoArea;
import org.eclipse.rap.demo.samples.ui.DescriptionArea;
import org.eclipse.rap.demo.samples.ui.FeatureTree;
import org.eclipse.rap.demo.samples.ui.ResourcesArea;
import org.eclipse.rap.json.JsonArray;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.rap.rwt.internal.RWTProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;

@SuppressWarnings( "restriction" )
public class FeatureBrowser extends AbstractEntryPoint {

    private static final String TEXT_TITLE = "RAP Feature Browser";
    private static final String TEXT_SHOW_SOURCE = "Show Source...";
    private static final String TEXT_HIDE_SOURCE = "Hide Source...";
    private static String APPSTORE_FEATURES = "org.eclipse.rap.samples.Features";
    private FeatureTree featureTree;
    private Feature features;
    private Feature currentFeature;
    private Composite main;
    private DemoArea demoArea;
    private ResourcesArea resourcesArea;
    private Sash mainSash;
    private int demoWidth;
    private boolean userShowSource;
    private DescriptionArea descriptionArea;

    @Override
    protected void createContents( Composite parent ) {
      int displayWidth = parent.getDisplay().getBounds().width;
      userShowSource = displayWidth > 1500;
      demoWidth = displayWidth / 3;
      createHeader( parent );
      style( parent ).as( "app" );
      applyGridLayout( parent ).margin( 8 ).verticalSpacing( 8 );
      main = new Composite( parent, SWT.NONE );
      applyGridData( main ).fill();
      applyGridLayout( main );
      style( main ).as( "main" );
      loadFeatures();
      createFeatureTree();
      createSash( new Control[]{ featureTree.getControl() } );
      createDemoArea();
      Control[] middleControls = new Control[]{ demoArea.getControl(), null };
      mainSash = createSash( middleControls );
      createResourcesArea();
      createDescriptionArea();
      middleControls[ 1 ] = descriptionArea.getControl();
      featureTree.select( features.getChildren()[ 0 ] );
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

    public boolean getUserShowSource() {
      return userShowSource;
    }

    public void setCurrentFeature( Feature feature ) {
      if( feature == null ) {
        throw new IllegalArgumentException();
      }
      if( currentFeature != feature ) {
        currentFeature = feature;
        demoArea.setFeature( feature );
        resourcesArea.setFeature( feature );
        descriptionArea.setFeature( feature );
        configureLayout();
      }
    }

    private void configureLayout() {
      boolean descriptionVisible = currentFeature.getDescription() != null;
      boolean resourcesVisible = userShowSource && resourcesArea.hasContent();
      GridLayout mainLayout = ( GridLayout )main.getLayout();
      mainLayout.numColumns = resourcesVisible ? 5 : 3;
      configureDemoLayoutData( resourcesVisible, descriptionVisible );
      configureDescLayoutData( resourcesVisible, descriptionVisible );
      configureMainSashLayoutData( resourcesVisible );
      configureResourcesLayoutData( resourcesVisible );
      main.layout( true, true );
    }

    private void configureResourcesLayoutData( boolean resourcesVisible ) {
      resourcesArea.getControl().setVisible( resourcesVisible );
      GridData resoucesData = ( GridData )resourcesArea.getControl().getLayoutData();
      resoucesData.exclude = !resourcesVisible;
    }

    private void configureMainSashLayoutData( boolean resourcesVisible ) {
      GridData sashData = ( GridData )mainSash.getLayoutData();
      mainSash.setVisible( resourcesVisible );
      sashData.exclude = !resourcesVisible;
    }

    private void configureDescLayoutData( boolean resourcesVisible, boolean descriptionVisible ) {
      Control control = descriptionArea.getControl();
      control.setVisible( descriptionVisible );
      if( !descriptionVisible ) {
        applyGridData( control ).exclude( true );
      } else if( resourcesVisible ) {
        applyGridData( control ).verticalFill().vGrab( false ).width( demoWidth ).vIndent( 8 );
      } else {
        applyGridData( control ).fill().vGrab( false ).vIndent( 8 );
      }
    }

    private void configureDemoLayoutData( boolean resourcesVisible, boolean descriptionVisible ) {
      int vSpan = descriptionVisible ? 1 : 2;
      if( resourcesVisible ) {
        applyGridData( demoArea.getControl() ).verticalFill().width( demoWidth ).vSpan( vSpan );
      } else {
        int widthHint = ( ( GridData )demoArea.getControl().getLayoutData() ).widthHint;
        if( widthHint > 0 ) {
          demoWidth = widthHint;
        }
        applyGridData( demoArea.getControl() ).fill().vSpan( vSpan );
      }
    }


    ////////////
    // internals

    private void createHeader( Composite parent ) {
      Composite header = new Composite( parent, SWT.NONE );
      style( header ).as( "header" );
      applyGridData( header ).fill().vGrab( false ).height( 50 );
      applyGridLayout( header ).cols( 2 ).marginLeft( 15 ).marginTop( 4 );
      header.setBackgroundMode( SWT.INHERIT_FORCE );
      Label headerLabel = new Label( header, SWT.NONE );
      headerLabel.setText( TEXT_TITLE );
      style( headerLabel ).as( "headerLabel" );
      applyGridData( headerLabel ).fill().hAlign( SWT.LEFT ).vAlign( SWT.CENTER );
      final Button sourceButton = new Button( header, SWT.PUSH );
      sourceButton.addListener( SWT.Selection, new Listener() {
        public void handleEvent( Event event ) {
          userShowSource = !userShowSource;
          sourceButton.setText( userShowSource ? TEXT_HIDE_SOURCE : TEXT_SHOW_SOURCE );
          configureLayout();
        }
      } );
      sourceButton.setText( userShowSource ? TEXT_HIDE_SOURCE : TEXT_SHOW_SOURCE );
      applyGridData( sourceButton ).hAlign( SWT.RIGHT ).vAlign( SWT.CENTER ).vGrab();
      style( sourceButton ).as( "headerButton" );
    }

    private void createFeatureTree() {
      featureTree = new FeatureTree( this );
      Navigation.getInstance().init( featureTree );
      applyGridData( featureTree.getControl() ).verticalFill().width( 200 ).vSpan( 2 );
    }

    private void createDemoArea() {
      demoArea = new DemoArea( this );
      applyGridData( demoArea.getControl() ).verticalFill().width( demoWidth );
    }

    private void createDescriptionArea() {
      descriptionArea = new DescriptionArea( this );
      applyGridData( descriptionArea.getControl() ).verticalFill().vGrab( false ).width( demoWidth );
    }

    private void createResourcesArea() {
      resourcesArea = new ResourcesArea( this );
      applyGridData( resourcesArea.getControl() ).fill().vSpan( 2 );
    }

    private Sash createSash( final Control[] resizeable ) {
      // do not use a SashForm since the tree width should be independent from the parent width
      // and the hierarchy should be as flat as possible
      final Sash sash = new Sash( main, SWT.VERTICAL );
      applyGridData( sash ).verticalFill().width( 8 ).vSpan( 2 );
      sash.addListener( SWT.Selection, new Listener() {
        public void handleEvent( Event event ) {
          if( event.detail != SWT.DRAG ) {
            for( int i = 0; i < resizeable.length; i++ ) {
              int left = resizeable[ i ].getBounds().x;
              int newWidth = event.x - left;
              ( ( GridData )resizeable[ i ].getLayoutData() ).widthHint = newWidth;
            }
            sash.getParent().layout();
          }
        }
      } );
      return sash;
    }

    private void loadFeatures() {
      features = ( Feature )RWT.getApplicationContext().getAttribute( APPSTORE_FEATURES );
      if( features != null ) {
        return;
      }
      InputStream resource = getClass().getClassLoader().getResourceAsStream( "features.json" );
      InputStreamReader reader = new InputStreamReader( resource );
      try {
        JsonArray jsonObject = JsonArray.readFrom( reader );
        reader.close();
        features = new Feature( jsonObject, true );
      } catch( IOException e ) {
        throw new RuntimeException( e );
      }
      if( !RWTProperties.isDevelopmentMode() ) {
        RWT.getApplicationContext().setAttribute( APPSTORE_FEATURES, features );
      }
    }

}
