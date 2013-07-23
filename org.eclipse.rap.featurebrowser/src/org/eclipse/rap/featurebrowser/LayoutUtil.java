package org.eclipse.rap.featurebrowser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

  public class LayoutUtil {

    public static final int BARWIDTH = 20;

    public static GridLayout createVerticalLayout() {
      GridLayout layout = new GridLayout( 1, false );
      layout.marginHeight = 0;
      layout.marginWidth = 0;
      layout.verticalSpacing = 0;
      return layout;
    }

    public static GridLayout createVerticalLayout( int margin ) {
      GridLayout layout = new GridLayout( 1, false );
      layout.marginHeight = margin;
      layout.marginWidth = margin;
      layout.verticalSpacing = 0;
      return layout;
    }

    public static GridLayout createHorizontalLayout( int columns ) {
      GridLayout layout = new GridLayout( columns, false );
      layout.marginHeight = 0;
      layout.marginWidth = 0;
      layout.horizontalSpacing = 0;
      return layout;
    }

    public static GridData createVerticalLayoutData( int height ) {
      GridData result = new GridData( SWT.FILL, SWT.FILL, true, false );
      result.heightHint = height;
      return result;
    }

    public static GridData createHorizontalLayoutData( int width ) {
      GridData result = new GridData( SWT.FILL, SWT.FILL, false, true );
      result.widthHint = width;
      return result;
    }

    public static GridData createFillData() {
      return new GridData( SWT.FILL, SWT.FILL, true, true );
    }

    public static Composite createVerticalComposite( Composite parent ) {
      Composite result = new Composite( parent, SWT.NONE );
      result.setLayout( LayoutUtil.createVerticalLayout() );
      return result;
    }

    public static Composite createHorizontalComposite( Composite parent, int columns ) {
      Composite result = new Composite( parent, SWT.NONE );
      result.setLayout( LayoutUtil.createHorizontalLayout( columns ) );
      return result;
    }

}
