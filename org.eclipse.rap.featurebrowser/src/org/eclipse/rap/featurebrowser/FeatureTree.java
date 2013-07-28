package org.eclipse.rap.featurebrowser;

import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.rap.json.JsonArray;
import org.eclipse.rap.json.JsonObject;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;


public class FeatureTree {

  private Tree tree;
  private FeaturePage featurePage;

  public FeatureTree( final Composite parent, JsonArray features  ) {
    Composite outer = LayoutUtil.createHorizontalComposite( parent, 2 );
    outer.setLayoutData( new GridData( SWT.FILL, SWT.FILL, false, true ) );
    createTree( outer, parent, features );
    final Button bar = new Button( outer, SWT.PUSH | SWT.CENTER );
    tree.setLayoutData( LayoutUtil.createHorizontalLayoutData( 150 ) );
    tree.setData( RWT.CUSTOM_VARIANT, "featuretree" );
    outer.setData( RWT.CUSTOM_VARIANT, "featuretree" );
    bar.setText( "<" );
    bar.setLayoutData( LayoutUtil.createHorizontalLayoutData( LayoutUtil.BARWIDTH ) );
    bar.setData( RWT.CUSTOM_VARIANT, "vbar" );
    bar.addListener( SWT.Selection, new Listener() {
      public void handleEvent( Event event ) {
        boolean visible = !tree.getVisible();
        bar.setText( visible ? "<" : ">" );
        GridData data = ( GridData )tree.getLayoutData();
        data.exclude = !visible;
        tree.setVisible( visible );
        parent.layout();
      }
    } );
  }

  public void createTree( Composite outer, final Composite parent, JsonArray features ) {
    tree = new Tree( outer, SWT.FULL_SELECTION );
    TreeViewer treeViewer = new TreeViewer( tree );
    treeViewer.setContentProvider( new ITreeContentProvider() {
      public void inputChanged( Viewer viewer, Object oldInput, Object newInput ) {}
      public void dispose() { }
      public boolean hasChildren( Object element ) {
        JsonObject json = ( JsonObject )element;
        return json.get( "children" ) != null;
      }
      public Object getParent( Object element ) {
        return null;
      }
      public Object[] getElements( Object inputElement ) {
        return toArray( inputElement );
      }
      public Object[] getChildren( Object parentElement ) {
        return toArray( ( ( JsonObject )parentElement ).get( "children" ) );
      }
    } );
    treeViewer.setLabelProvider( new LabelProviderImplementation() );
    treeViewer.addSelectionChangedListener( new ISelectionChangedListener() {
      public void selectionChanged( SelectionChangedEvent event ) {
        IStructuredSelection sel = ( IStructuredSelection )event.getSelection();
        JsonObject json = ( JsonObject )sel.getFirstElement();
        if( featurePage != null ) {
          featurePage.dispose();
        }
        if( json.get( "feature" ) != null ) {
          featurePage = new FeaturePage( parent, json );
        }
        parent.layout();
      }
    } );
    treeViewer.setInput( features );
    treeViewer.expandAll();
  }

  public Tree getTree() {
    return tree;
  }

  public Object[] toArray( Object inputElement ) {
    JsonArray json = ( JsonArray )inputElement;
    Object[] elements = new Object[ json.size() ];
    for( int i = 0; i < json.size(); i++ ) {
      elements[ i ] = json.get( i );
    }
    return elements;
  }

  private final class LabelProviderImplementation implements ILabelProvider, IFontProvider {

    public void removeListener( ILabelProviderListener listener ) {
    }

    public boolean isLabelProperty( Object element, String property ) {
      return false;
    }

    public void dispose() {
    }

    public void addListener( ILabelProviderListener listener ) {
    }

    public String getText( Object element ) {
      JsonObject json = ( ( JsonObject )element );
      String result = "?";
      if( json.get( "category" ) != null ) {
        result = json.get( "category" ).asString();
      } else if( json.get( "feature" ) != null ) {
        result = json.get( "feature" ).asString();
      }
      return result;
    }

    public Image getImage( Object element ) {
      return null;
    }

    public Font getFont( Object element ) {
      JsonObject json = ( ( JsonObject )element );
      if( json.get( "exclusive" ) != null || json.get( "category" ) != null ) {
        Display display = tree.getDisplay();
        FontData fontData = tree.getFont().getFontData()[ 0 ];
        if( json.get( "exclusive" ) != null ) {
          fontData.setStyle( SWT.ITALIC );
        }
        if( json.get( "category" ) != null ) {
          fontData.setStyle( SWT.BOLD );
        }
        return new Font( display, fontData );
      }
      return null;
    }
  }

}
