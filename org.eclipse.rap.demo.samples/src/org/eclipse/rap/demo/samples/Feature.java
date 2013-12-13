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

import static org.eclipse.rap.demo.samples.util.HtmlDocument.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.rap.demo.samples.util.FeatureVisitor;
import org.eclipse.rap.demo.samples.util.HtmlDocument;
import org.eclipse.rap.json.JsonArray;
import org.eclipse.rap.json.JsonObject;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.rap.rwt.service.ResourceManager;


public class Feature {

  private Feature[] children;
  private static final String UTF_8 = "UTF-8";
  private String name;
  private Class<? extends AbstractEntryPoint> snippet;
  private Feature parent;
  private Feature previous;
  private Feature next;
  private String preview;
  private String url;
  private String description;
  private String target;
  private int width = -1;
  private int height = -1;

  public Feature( JsonObject obj, Feature parent, Feature previous ) {
    this( obj.get( "children" ) != null ? obj.get( "children" ).asArray() : null, false );
    this.parent = parent;
    this.previous = previous;
    if( previous != null ) {
      previous.setNext( this );
    }
    url = obj.get( "url" ) != null ? obj.get( "url" ).asString() : null;
    name = obj.get( "name" ).asString();
    target = obj.get( "target" ) != null ? obj.get( "target" ).asString() : null;
    if( obj.get( "width" ) != null ) {
      width = obj.get( "width" ).asInt();
    }
    if( obj.get( "height" ) != null ) {
      height = obj.get( "height" ).asInt();
    }
    if( obj.get( "snippet" ) != null ) {
      registerSnippet( obj.get( "snippet" ).asString() );
    }
    if( obj.get( "description" ) != null ) {
      readDescription( obj.get( "description" ).asString() );
    }
    if( obj.get( "preview" ) != null ) {
      registerPreview( obj.get( "preview" ).asString() );
    }
    Navigation.getInstance().register( this );
  }

  private void setNext( Feature next ) {
    this.next = next;
  }

  public Feature( JsonArray json, boolean root ) {
    if( json != null ) {
      List<Feature> childrenList = new ArrayList<Feature>( json.size() );
      Feature prev = root ? null : this;
      for( int i = 0; i < json.size(); i++ ) {
        JsonObject obj = json.get( i ).asObject();
        Feature child = new Feature( obj, this, prev );
        if( child.getTarget() == null || child.getTarget().equals( getClientName() ) ) {
          childrenList.add( child );
          prev = child.getLastChild();
        }
      }
      children = childrenList.toArray( new Feature[ childrenList.size() ] );
    }
  }

  private Feature getLastChild() {
    if( children == null || children.length == 0 ) {
      return this;
    }
    return children[ children.length - 1 ].getLastChild();
  }

  private static String getClientName() {
    return RWT.getClient() != null ? RWT.getClient().getClass().getSimpleName() : "SWT";
  }


  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public String getPreview() {
    return preview;
  }

  public Feature[] getChildren() {
    return children;
  }

  public Feature getParent() {
    return parent;
  }

  public Feature getPrevious() {
    return previous;
  }

  public Feature getNext() {
    return next;
  }

  public String getTarget() {
    return target;
  }

  public String getUrl() {
    return url;
  }

  public String getSnippetJavaUrl() {
    if( snippet == null ) {
      return null;
    }
    return RWT.getResourceManager().getLocation( getFileName() );
  }

  public String getSnippetHtmlUrl() {
    if( snippet == null ) {
      return null;
    }
    return RWT.getResourceManager().getLocation( getHtmlName() );
  }

  public Class<? extends AbstractEntryPoint> getSnippet() {
    return snippet;
  }

  public void forEach( FeatureVisitor featureVisitor ) {
    if( children != null ) {
      for( Feature child : children ) {
        featureVisitor.visit( child );
        child.forEach( featureVisitor );
      }
    }
  }

  public Object[] getPath() {
    ArrayList<Object> list = new ArrayList<Object>( 3 );
    Object element = this;
    while( element != null ) {
      list.add( element );
      if( element instanceof Feature ) {
        element = ( ( Feature )element ).getParent();
      } else if( element instanceof Feature ) {
        element = ( ( Feature )element ).getParent();
      }
    }
    Object[] result = new Object[ list.size() ];
    for( int i = 0; i < list.size(); i++ ) {
      result[ list.size() - 1 - i ] = list.get( i );
    }
    return result;
  }

  private void registerPreview( String path ) {
    ResourceManager manager = RWT.getResourceManager();
    try {
      manager.register( path, FeaturesResourceLoader.getInstance().getResourceAsStream( path ) );
    } catch( IOException e ) {
      throw new RuntimeException( e );
    }
    preview = path;
  }

  private void registerSnippet( String snippetName ) {
    try {
      snippet = getSnippetClass( snippetName );
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

  private void readDescription( String path ) {
    try {
      String raw = readTextContentChecked( Feature.class.getClassLoader(), path );
      description = raw.trim();
    } catch( IOException e ) {
      throw new RuntimeException( e );
    }
  }

  private static void registerJavaFileResource( String name, String source ) throws IOException {
    InputStream stream = new ByteArrayInputStream( source.getBytes() );
    RWT.getResourceManager().register( name, stream );
    stream.close();
  }

  private static void registerHtmlFileResource( String name, String source ) throws IOException {
    HtmlDocument doc = new HtmlDocument();
    doc.head.content(
      link().type( "text/css" ).href( locationOf( "prettify.css" ) ).rel( "stylesheet" ),
      script().type( "text/javascript" ).src( locationOf( "prettify.js" ) )
    );
    doc.body.attr( "onload", "prettyPrint()" );
    doc.body.style( "padding-left:6px" );
    doc.body.content(
      pre().cssClass( "prettyprint lang-java" ).content(
        source.replaceAll( "\\<", "&lt;" ).replaceAll( "\\>", "&gt;" )
      )
    );
    InputStream stream = new ByteArrayInputStream( doc.toString().getBytes() );
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

  @Override
  public String toString() {
    if( getSnippet() != null ) {
      return getSnippet().getSimpleName();
    }
    return getName();
  }


}
