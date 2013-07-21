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

  private SashForm sashf;

  FeaturePage( Composite parent, JsonObject feature ) {
    sashf = new SashForm( parent, SWT.HORIZONTAL );
    sashf.setLayoutData( LayoutUtil.createFillData() );
    sashf.setSashWidth( 1 );
    Composite snippetParent = new Composite( sashf, SWT.NONE );
    Browser browser = new Browser( sashf, SWT.NONE );
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
      showError( sashf, e );
    } catch( IllegalAccessException e ) {
      showError( sashf, e );
    } catch( InvocationTargetException e ) {
      showError( sashf, e );
    } catch( NoSuchMethodException e ) {
      showError( sashf, e );
    } catch( ClassCastException e ) {
      showError( sashf, e );
    } catch( ClassNotFoundException e ) {
      showError( sashf, e );
    }
  }

  // TODO : add classloader param
  @SuppressWarnings("unchecked")
  private Class<? extends AbstractEntryPoint> getSnippetClass( String classname )
      throws ClassNotFoundException, ClassCastException
  {
    ClassLoader loader = getClass().getClassLoader();
    return ( ( Class<? extends AbstractEntryPoint> )loader.loadClass( classname ) );
  }

  private void showError( Composite snippetParent, Exception fail ) {
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
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    msg.setLayoutData( LayoutUtil.createFillData() );
  }

  public void dispose() {
    sashf.dispose();
  }

  private void createContents( Class<?> clazz, Composite parent )
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
