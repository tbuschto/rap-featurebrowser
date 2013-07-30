package org.eclipse.rap.featurebrowser;

import static org.eclipse.rap.featurebrowser.GridDataUtil.applyGridData;
import static org.eclipse.rap.featurebrowser.GridLayoutUtil.applyGridLayout;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;


public class FeaturePage {

  private SashForm page;

  FeaturePage( Composite parent, Feature feature ) {
    page = new SashForm( parent, SWT.HORIZONTAL );
    applyGridData( page ).fill();
    page.setSashWidth( 1 );
    if( feature.getSnippet() != null ) {
      createSnippetInstance( feature );
      createSnippetSource( feature );
      page.setWeights( new int[]{ 40, 60 } );
    }
  }

  public void createSnippetSource( Feature feature ) {
    Browser browser = new Browser( page, SWT.NONE );
    browser.setUrl( feature.getSnippetHtmlUrl() );
  }

  public void createSnippetInstance( Feature feature ) {
    Composite snippetParent = new Composite( page, SWT.NONE );
    applyGridLayout( snippetParent );
    try {
      createContents( feature.getSnippet(), snippetParent );
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
    }
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
    applyGridData( msg ).fill();
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
