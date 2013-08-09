package org.eclipse.rap.featurebrowser.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Control;

public class GridDataUtil {

  private final GridData gridData;

  private GridDataUtil( GridData gridData ) {
    this.gridData = gridData;
  }

  public static GridDataUtil applyGridData( Control control ) {
    GridData gridData = new GridData();
    control.setLayoutData( gridData );
    return new GridDataUtil( gridData );
  }

  public static GridDataUtil onGridData( Control control ) {
    Object layoutData = control.getLayoutData();
    if( layoutData instanceof GridData ) {
      return new GridDataUtil( ( GridData )layoutData );
    }
    throw new IllegalStateException( "Control must have GridData layout data. Has " + layoutData );
  }

  public GridDataUtil horizontalFill() {
    gridData.horizontalAlignment = SWT.FILL;
    gridData.grabExcessHorizontalSpace = true;
    return this;
  }

  public GridDataUtil verticalFill() {
    gridData.verticalAlignment = SWT.FILL;
    gridData.grabExcessVerticalSpace = true;
    return this;
  }

  public GridDataUtil fill() {
    gridData.horizontalAlignment = SWT.FILL;
    gridData.verticalAlignment = SWT.FILL;
    gridData.grabExcessHorizontalSpace = true;
    gridData.grabExcessVerticalSpace = true;
    return this;
  }

  public GridDataUtil center() {
    gridData.horizontalAlignment = SWT.CENTER;
    gridData.verticalAlignment = SWT.CENTER;
    return this;
  }

  public GridDataUtil hGrab() {
    return hGrab( true );
  }

  public GridDataUtil hGrab( boolean grabExcessHorizontalSpace ) {
    gridData.grabExcessHorizontalSpace = grabExcessHorizontalSpace;
    return this;
  }

  public GridDataUtil vGrab() {
    return vGrab( true );
  }

  public GridDataUtil vGrab( boolean grabExcessVerticalSpace ) {
    gridData.grabExcessVerticalSpace = grabExcessVerticalSpace;
    return this;
  }

  public GridDataUtil hSpan( int horizontalSpan ) {
    gridData.horizontalSpan = horizontalSpan;
    return this;
  }

  public GridDataUtil vSpan( int verticalSpan ) {
    gridData.verticalSpan = verticalSpan;
    return this;
  }

  public GridDataUtil minHeight( int minimumHeight ) {
    gridData.minimumHeight = minimumHeight;
    return this;
  }

  public GridDataUtil minWidth( int minimumWidth ) {
    gridData.minimumWidth = minimumWidth;
    return this;
  }

  public GridDataUtil vIndent( int verticalIndent ) {
    gridData.verticalIndent = verticalIndent;
    return this;
  }

  public GridDataUtil hIndent( int horizontalIndent ) {
    gridData.horizontalIndent = horizontalIndent;
    return this;
  }

  public GridDataUtil height( int heightHint ) {
    gridData.heightHint = heightHint;
    return this;
  }

  public GridDataUtil width( int widthHint ) {
    gridData.widthHint = widthHint;
    return this;
  }

  public GridDataUtil vAlign( int verticalAlignment ) {
    gridData.verticalAlignment = verticalAlignment;
    return this;
  }

  public GridDataUtil hAlign( int horizontalAlignment ) {
    gridData.horizontalAlignment = horizontalAlignment;
    return this;
  }

  public GridDataUtil exclude( boolean exclude ) {
    gridData.exclude = exclude;
    return this;
  }

}
