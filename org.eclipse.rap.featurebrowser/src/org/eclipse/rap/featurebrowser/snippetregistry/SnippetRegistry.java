package org.eclipse.rap.featurebrowser.snippetregistry;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;


public class SnippetRegistry {

  private static final String CLASSPATH = "C:\\dev\\gitreps\\rap\\org.eclipse.rap_2_1\\bundles\\org.eclipse.rap.rwt\\bin";
  private static Map<String, Class<?>> classes = new HashMap< String, Class<?>>();
  private final static String OUTPUT = "/c:/dev";

  // TODO : should not throw "Exception"
  public static void addSnippet( SnippetBuilder snippet ) throws Exception {
    String name = snippet.getName();
    String text = snippet.generate();
    // TODO [tb] share classes across session, files within session
    registerJavaFileResource( name, text );
    registerHtmlFileResource( name, text );
    compileClass( name, text );
  }

  public static void compileClass( String name, String text ) throws Exception {
    JavaFileObject file = new InMemoryJavaFileObject( "snippets." + name, text );
    Iterable<? extends JavaFileObject> files = Arrays.asList( file );
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    CustomDiagnosticListener c = new CustomDiagnosticListener();
    StandardJavaFileManager fileManager
      = compiler.getStandardFileManager( c, Locale.ENGLISH, null );
    Iterable< String > options = Arrays.asList( "-d", OUTPUT, "-classpath", CLASSPATH );
    JavaCompiler.CompilationTask task
      = compiler.getTask( null, fileManager, c, options, null, files );
    task.call();
    @SuppressWarnings("deprecation")
    URL url = new File( OUTPUT ).toURL();
    URL[] urls = new URL[] { url };
    ClassLoader loader = new URLClassLoader( urls, SWT.class.getClassLoader() );
    classes.put( name, loader.loadClass( "snippets." + name ) );
  }

  public static void registerJavaFileResource( String name, String text ) {
    InputStream stream = new ByteArrayInputStream( text.getBytes() );
    RWT.getResourceManager().register( name + ".java", stream );
    try {
      stream.close();
    } catch( IOException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static void registerHtmlFileResource( String name, String text ) {
    StringBuilder html = new StringBuilder();
    html.append( "<!DOCTYPE>" );
    html.append( "<html><head></head><body><pre style='padding:6px'>" );
    html.append( text.replaceAll( "\\<", "&lt;" ).replaceAll( "\\>", "&gt;" ) );
    html.append( "</pre></body></html>" );
    InputStream stream = new ByteArrayInputStream( html.toString().getBytes() );
    RWT.getResourceManager().register( name + ".java.html", stream );
    try {
      stream.close();
    } catch( IOException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static String getSnippetJavaUrl( String name ) {
    return RWT.getResourceManager().getLocation( name + ".java" );
  }

  public static String getSnippetHtmlUrl( String name ) {
    return RWT.getResourceManager().getLocation( name + ".java.html" );
  }

  public static Class<?> getClass( String name ) {
    return classes.get( name );
  }

}
