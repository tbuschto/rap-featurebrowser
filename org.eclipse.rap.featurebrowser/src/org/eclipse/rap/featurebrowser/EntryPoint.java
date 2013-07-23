package org.eclipse.rap.featurebrowser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.rap.json.JsonArray;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;


public class EntryPoint extends AbstractEntryPoint {

    @Override
    protected void createContents( Composite parent ) {
      parent.setLayout( LayoutUtil.createVerticalLayout() );
      createHeader( parent );
      Composite main = LayoutUtil.createHorizontalComposite( parent, 2 );
      main.setLayoutData( LayoutUtil.createFillData() );
      InputStream resource = getClass().getClassLoader().getResourceAsStream( "features.json" );
      InputStreamReader reader = new InputStreamReader( resource );
      JsonArray jsonObject = null;
      try {
        jsonObject = JsonArray.readFrom( reader );
      } catch( IOException e ) {
        e.printStackTrace();
      }
      try {
        reader.close();
      } catch( IOException e ) {
        e.printStackTrace();
      }
      if( jsonObject != null ) {
        new FeatureTree( main, jsonObject );
      } else {
        throw new RuntimeException( "Could not read features" );
      }
    }

    public void createHeader( Composite parent ) {
      Composite header = new Composite( parent, SWT.NONE );
      header.setData( RWT.CUSTOM_VARIANT, "header" );
      header.setLayoutData( LayoutUtil.createVerticalLayoutData( 45 ) );
      header.setLayout( new GridLayout( 2, false ) );
      header.setBackgroundMode( SWT.INHERIT_FORCE );
      Label headerLabel = new Label( header, SWT.NONE );
      headerLabel.setText( ">>  REMOTE APPLICATION PLATFORM FEATURE BROWSER" );
      headerLabel.setData( RWT.CUSTOM_VARIANT, "headerLabel" );
      headerLabel.setLayoutData( new GridData( SWT.LEFT, SWT.CENTER, true, true ) );
      Button help = new Button( header, SWT.PUSH );
      help.addListener( SWT.Selection, new Listener() {
        public void handleEvent( Event event ) {
          new Help( getShell() );
        }
      } );
      help.setText( "Getting started..." );
      help.setLayoutData( new GridData( SWT.RIGHT, SWT.BOTTOM, false, true ) );
      help.setData( RWT.CUSTOM_VARIANT, "helpButton" );
      Composite subheader = new Composite( parent, SWT.NONE );
      subheader.setData( RWT.CUSTOM_VARIANT, "subheader" );
      subheader.setLayoutData( LayoutUtil.createVerticalLayoutData( LayoutUtil.BARWIDTH ) );
    }

}
