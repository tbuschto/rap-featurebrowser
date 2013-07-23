package org.eclipse.rap.featurebrowser;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.Application;
import org.eclipse.rap.rwt.application.ApplicationConfiguration;
import org.eclipse.rap.rwt.client.WebClient;


public class ApplicationConfig implements ApplicationConfiguration {

  public void configure( Application application ) {
    Map<String, String> properties = new HashMap<String, String>();
    properties.put( WebClient.PAGE_TITLE, "RAP Feature Browser" );
    application.addStyleSheet( RWT.DEFAULT_THEME_ID, "/css/app.css" );
    application.addEntryPoint( "/featurebrowser", EntryPoint.class, properties );
    application.addResource( "prettify.css", FeatureResourceLoader.getInstance() );
    application.addResource( "prettify.js", FeatureResourceLoader.getInstance() );
  }

}
