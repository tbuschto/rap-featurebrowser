package org.eclipse.rap.featurebrowser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.rap.json.JsonObject;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;


public class FeaturePage {

  private SashForm page;

  FeaturePage( Composite parent, JsonObject feature ) {
    page = new SashForm( parent, SWT.HORIZONTAL );
    page.setLayoutData( LayoutUtil.createFillData() );
    page.setSashWidth( 1 );
    Composite snippetParent = new Composite( page, SWT.NONE );
    Browser browser = new Browser( page, SWT.NONE );
    page.setWeights( new int[]{ 40, 60 } );
    snippetParent.setLayout( new GridLayout( 1, false ) );
    try {
      Class<? extends AbstractEntryPoint> clazz
        = getSnippetClass( feature.get( "snippet" ).asString() );
      if( !SnippetRegistry.has( clazz ) ) {
        SnippetRegistry.register( clazz );
      }
      browser.setUrl( SnippetRegistry.getSnippetHtmlUrl( clazz ) );
      createContents( clazz, snippetParent );
    } catch( InstantiationException e ) {
      showError( page, e );
    } catch( IllegalAccessException e ) {
      showError( page, e );
    } catch( InvocationTargetException e ) {
      showError( page, e.getCause() != null ? e.getCause() : e );
    } catch( NoSuchMethodException e ) {
      showError( page, e );
    } catch( ClassCastException e ) {
      showError( page, e );
    } catch( ClassNotFoundException e ) {
      showError( page, e );
    }
  }

  // TODO : add classloader param
  private Class<? extends AbstractEntryPoint> getSnippetClass( String classname )
      throws ClassNotFoundException, ClassCastException
  {
    ClassLoader loader = getClass().getClassLoader();
    return ( ( Class<? extends AbstractEntryPoint> )loader.loadClass( classname ) );
  }

  private static void showError( Composite snippetParent, Throwable fail ) {
    Text msg = new Text( snippetParent, SWT.MULTI );
    msg.setEditable( false );
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream( baos );
    ps.append( fail.getMessage() + "\n" );
    fail.printStackTrace( ps );
    fail.printStackTrace();
    try {
      msg.setText( baos.toString( "UTF-8" ) );
    } catch( UnsupportedEncodingException e ) {
      e.printStackTrace();
    }
    msg.setLayoutData( LayoutUtil.createFillData() );
  }

  public void dispose() {
    page.dispose();
  }

  private static void createContents( Class<?> clazz, Composite parent )
      throws InstantiationException, IllegalAccessException, InvocationTargetException,
      NoSuchMethodException
    {
      Object instance = clazz.newInstance();
      Class<?> params[] = { Composite.class };
      Object paramsObj[] = { parent };
      Method declaredMethod = clazz.getDeclaredMethod( "createContents", params );
      declaredMethod.setAccessible( true );
      declaredMethod.invoke( instance, paramsObj );
    }

}
