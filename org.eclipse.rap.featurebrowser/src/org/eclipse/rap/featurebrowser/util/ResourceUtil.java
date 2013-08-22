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
package org.eclipse.rap.featurebrowser.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;


public class ResourceUtil {


  public interface ResourceHandler {
    public void handleResource( InputStream stream );
  }

  public static void load( String path, ResourceHandler resourceHandler ) {
    InputStream stream = getStream( path );
    resourceHandler.handleResource( stream );
    try {
      stream.close();
    } catch( IOException e ) {
      throw new RuntimeException( e );
    }
  }

  public static Image getImage( String path ) {
    Map<String,Image> images = ( Map<String, Image> )RWT.getUISession().getAttribute( "Images" );
    if( images == null ) {
      images = new HashMap<String,Image>();
      RWT.getUISession().setAttribute( "Imags", images );
    }
    Image result = images.get( path );
    if( result == null ) {
      InputStream stream = getStream( path );
      result = new Image( Display.getCurrent(), stream );
      try {
        stream.close();
      } catch( IOException e ) {
        throw new RuntimeException( e );
      }
    }
    return result;
  }

  private static InputStream getStream( String path ) {
    InputStream stream = ResourceHandler.class.getClassLoader().getResourceAsStream( path );
    return stream;
  }

}

