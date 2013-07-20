package org.eclipse.rap.featurebrowser.features;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.rap.featurebrowser.FeatureTree;
import org.eclipse.rap.featurebrowser.LayoutUtil;
import org.eclipse.rap.featurebrowser.snippetregistry.SnippetRegistry;
import org.eclipse.rap.featurebrowser.snippets.LabelSnippet;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;


public class LabelFeature extends AbstractFeature {

  public LabelFeature( Composite parent ) {
    SashForm sashf = new SashForm( parent, SWT.HORIZONTAL );
    sashf.setLayoutData( LayoutUtil.createFillData() );
    sashf.setSashWidth( 1 );
    Composite snippetParent = new Composite( sashf, SWT.NONE );
    Browser browser = new Browser( sashf, SWT.NONE );
    snippetParent.setLayout( new GridLayout( 1, false ) );
    LabelSnippet snippet = new LabelSnippet( "LabelSnippet" );
    try {
      SnippetRegistry.addSnippet( snippet );
      try {
        Class<?> clazz = SnippetRegistry.getClass( "LabelSnippet" );
        createContents( clazz, snippetParent );
      } catch( Exception e ) {
        showError( "Coud not run snippet:", snippetParent, e );
      }
    } catch( Exception e ) {
      showError( "Coud not compile snippet:", snippetParent, e );
    }
    browser.setUrl( SnippetRegistry.getSnippetHtmlUrl( "LabelSnippet" ) );
  }

  public void showError( String reason, Composite snippetParent, Exception fail ) {
    Text msg = new Text( snippetParent, SWT.MULTI );
    msg.setEditable( false );
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream( baos );
    ps.append( reason );
    ps.append( "\n" );
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

  public void createContents( Class<?> clazz, Composite parent )
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
