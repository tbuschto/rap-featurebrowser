package org.eclipse.rap.featurebrowser;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;


public class SnippetRegistry {

  private static final String UTF_8 = "UTF-8";
  private static List<Class<? extends AbstractEntryPoint>> classes
    = new ArrayList<Class<? extends AbstractEntryPoint>>( 50 );

  public static void register( Class<? extends AbstractEntryPoint> snippet ) {
    ClassLoader loader = snippet.getClassLoader();
    String path = snippet.getName().replaceAll( "\\.", "/" ) + ".java";
    String source;
    try {
      source = readTextContentChecked( loader, path );
      registerJavaFileResource( getFileName( snippet ), source );
      registerHtmlFileResource( getHtmlName( snippet ), source );
    } catch( IOException e ) {
      throw new RuntimeException( e );
    }
  }

  public static void registerJavaFileResource( String name, String source ) throws IOException {
    InputStream stream = new ByteArrayInputStream( source.getBytes() );
    RWT.getResourceManager().register( name, stream );
    stream.close();
  }

  public static void registerHtmlFileResource( String name, String source ) throws IOException {
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

  public static String getSnippetJavaUrl( Class<? extends AbstractEntryPoint> clazz ) {
    return RWT.getResourceManager().getLocation( getFileName( clazz ) );
  }

  public static String getSnippetHtmlUrl( Class<? extends AbstractEntryPoint> clazz ) {
    return RWT.getResourceManager().getLocation( getHtmlName( clazz ) );
  }

  public static boolean has( Class<? extends AbstractEntryPoint> clazz ) {
    return classes.contains( clazz );
  }

  private static String getFileName( Class<? extends AbstractEntryPoint> clazz ) {
    return clazz.getSimpleName() + ".java";
  }

  private static String getHtmlName( Class<? extends AbstractEntryPoint> clazz ) {
    return clazz.getSimpleName() + ".java.html";
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
}
