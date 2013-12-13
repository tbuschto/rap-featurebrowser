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
