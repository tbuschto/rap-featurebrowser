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
package org.eclipse.rap.featurebrowser;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.eclipse.rap.json.JsonObject;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;


public class Feature {

  private static final String UTF_8 = "UTF-8";
  private String name;
  private boolean exclusive;
  private Class<? extends AbstractEntryPoint> snippet;
  private Object parent;

  public Feature( JsonObject obj, Object parent ) {
    this.parent = parent;
    name = obj.get( "feature" ).asString();
    exclusive = obj.get( "exclusive" ) != null && obj.get( "exclusive" ).asBoolean();
    try {
      snippet = getSnippetClass( obj.get( "snippet" ).asString() );
      Navigation.getInstance().register( this );
      ClassLoader loader = snippet.getClassLoader();
      String path = snippet.getName().replaceAll( "\\.", "/" ) + ".java";
      String source = readTextContentChecked( loader, path );
      registerJavaFileResource( getFileName(), source );
      registerHtmlFileResource( getHtmlName(), source );
    } catch( IOException e ) {
      throw new RuntimeException( e );
    } catch( ClassNotFoundException e ) {
      throw new RuntimeException( e );
    }
  }

  @Override
  public String toString() {
    return name;
  }

  public Object getParent() {
    return parent;
  }

  public boolean isExclusive() {
    return exclusive;
  }

  public String getSnippetJavaUrl() {
    return RWT.getResourceManager().getLocation( getFileName() );
  }

  public String getSnippetHtmlUrl() {
    return RWT.getResourceManager().getLocation( getHtmlName() );
  }

  public Class<? extends AbstractEntryPoint> getSnippet() {
    return snippet;
  }

  private static void registerJavaFileResource( String name, String source ) throws IOException {
    InputStream stream = new ByteArrayInputStream( source.getBytes() );
    RWT.getResourceManager().register( name, stream );
    stream.close();
  }

  private static void registerHtmlFileResource( String name, String source ) throws IOException {
    StringBuilder html = new StringBuilder();
    html.append( "<!DOCTYPE>" );
    html.append( "<html><head>" );
    html.append( "<link href=\"/" );
    html.append( RWT.getResourceManager().getLocation( "prettify.css" ) );
    html.append( "\" type=\"text/css\" rel=\"stylesheet\" />" );
    html.append( "<script src=\"/" );
    html.append( RWT.getResourceManager().getLocation( "prettify.js" ) );
    html.append( "\" type=\"text/javascript\"></script></head>" );
    html.append( "<body onload=\"prettyPrint()\" style='padding-left:6px' >" );
    html.append( "<pre class=\"prettyprint lang-java\">" );
    html.append( source.replaceAll( "\\<", "&lt;" ).replaceAll( "\\>", "&gt;" ) );
    html.append( "</pre></body></html>" );
    InputStream stream = new ByteArrayInputStream( html.toString().getBytes() );
    RWT.getResourceManager().register( name, stream );
    stream.close();
  }

  private Class<? extends AbstractEntryPoint> getSnippetClass( String classname ) throws ClassNotFoundException {
    ClassLoader loader = getClass().getClassLoader();
    return ( ( Class<? extends AbstractEntryPoint> )loader.loadClass( classname ) );
  }

  private static String readTextContentChecked( ClassLoader loader, String resource ) throws IOException {
    InputStream stream = loader.getResourceAsStream( resource );
    if( stream == null ) {
      throw new IllegalArgumentException( "Resource not found: " + resource );
    }
    try {
      BufferedReader reader = new BufferedReader( new InputStreamReader( stream, UTF_8 ) );
      return readLines( reader );
    } finally {
      stream.close();
    }
  }

  private static String readLines( BufferedReader reader ) throws IOException {
    StringBuilder builder = new StringBuilder();
    String line = reader.readLine();
    while( line != null ) {
      builder.append( line );
      builder.append( '\n' );
      line = reader.readLine();
    }
    return builder.toString();
  }

  private String getFileName() {
    return snippet.getSimpleName() + ".java";
  }

  private String getHtmlName() {
    return snippet.getSimpleName() + ".java.html";
  }

  public Object[] getPath() {
    ArrayList<Object> list = new ArrayList<Object>( 3 );
    Object element = this;
    while( element != null ) {
      list.add( element );
      if( element instanceof Feature ) {
        element = ( ( Feature )element ).getParent();
      } else if( element instanceof Category ) {
        element = ( ( Category )element ).getParent();
      }
    }
    Object[] result = new Object[ list.size() ];
    for( int i = 0; i < list.size(); i++ ) {
      result[ list.size() - 1 - i ] = list.get( i );
    }
    return result;
  }

}
