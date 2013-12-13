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

package org.eclipse.rap.demo.samples.util;

import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Control;

public class FormDataUtil {

  private final FormData formData;

  private FormDataUtil( FormData formData ) {
    this.formData = formData;
  }

  public static FormDataUtil applyFormData( Control control ) {
    FormData formData = new FormData();
    control.setLayoutData( formData );
    return new FormDataUtil( formData );
  }

  public FormDataUtil left( int percentage, int offset ) {
    formData.left = new FormAttachment( percentage, offset );
    return this;
  }

  public FormDataUtil right( int percentage, int offset ) {
    formData.right = new FormAttachment( percentage, offset );
    return this;
  }

  public FormDataUtil top( int percentage, int offset ) {
    formData.top = new FormAttachment( percentage, offset );
    return this;
  }

  public FormDataUtil bottom( int percentage, int offset ) {
    formData.bottom = new FormAttachment( percentage, offset );
    return this;
  }

}
