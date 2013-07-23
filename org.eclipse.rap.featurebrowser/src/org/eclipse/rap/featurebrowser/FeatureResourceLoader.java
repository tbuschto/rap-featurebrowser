package org.eclipse.rap.featurebrowser;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.rap.rwt.service.ResourceLoader;


public class FeatureResourceLoader implements ResourceLoader {

  private static ResourceLoader instance = null;

  public static ResourceLoader getInstance() {
    if( instance == null ) {
      instance = new FeatureResourceLoader();
    }
    return instance;
  }

  public InputStream getResourceAsStream( String resourceName ) throws IOException {
    return getClass().getClassLoader().getResourceAsStream( "resources/" + resourceName );
  }

}
