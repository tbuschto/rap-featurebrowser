package org.eclipse.rap.featurebrowser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.rap.json.JsonArray;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Composite;


public class EntryPoint extends AbstractEntryPoint {

    @Override
    protected void createContents( Composite parent ) {
      parent.setLayout( LayoutUtil.createVerticalLayout() );
      CLabel header = new CLabel( parent, SWT.NONE );
      header.setText( ">>  REMOTE APPLICATION PLATFORM FEATURE BROWSER" );
      header.setMargins( 16, 16, 30, 10 );
      header.setData( RWT.CUSTOM_VARIANT, "header" );
      header.setLayoutData( LayoutUtil.createVerticalLayoutData( 45 ) );
      Composite subheader = new Composite( parent, SWT.NONE );
      subheader.setData( RWT.CUSTOM_VARIANT, "subheader" );
      subheader.setLayoutData( LayoutUtil.createVerticalLayoutData( LayoutUtil.BARWIDTH ) );
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

}
