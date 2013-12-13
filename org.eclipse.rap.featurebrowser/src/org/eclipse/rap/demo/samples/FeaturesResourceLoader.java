package org.eclipse.rap.demo.samples;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.rap.rwt.service.ResourceLoader;


public class FeaturesResourceLoader implements ResourceLoader {

  private static ResourceLoader instance = null;

  public static ResourceLoader getInstance() {
    if( instance == null ) {
      instance = new FeaturesResourceLoader();
    }
    return instance;
  }

  public InputStream getResourceAsStream( String resourceName ) throws IOException {
    return getClass().getClassLoader().getResourceAsStream( "resources/" + resourceName );
  }

}
