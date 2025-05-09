/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.tests.junit;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.FontDialog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Automated Test Suite for class org.eclipse.swt.widgets.FontDialog
 *
 * @see org.eclipse.swt.widgets.FontDialog
 */
public class Test_org_eclipse_swt_widgets_FontDialog extends Test_org_eclipse_swt_widgets_Dialog {

@Override
@BeforeEach
public void setUp() {
	super.setUp();
	fontDialog = new FontDialog(shell, SWT.NULL);
	setDialog(fontDialog);
}

@Test
public void test_ConstructorLorg_eclipse_swt_widgets_Shell() {
	new FontDialog(shell);
	assertThrows(IllegalArgumentException.class,()-> new FontDialog(null),"No exception thrown for parent == null");
}

@Test
public void test_ConstructorLorg_eclipse_swt_widgets_ShellI() {
	assertThrows(IllegalArgumentException.class,()-> new FontDialog(null, SWT.NULL),"No exception thrown for parent == null");
}

@Test
public void test_setFontDataLorg_eclipse_swt_graphics_FontData() {
	FontData [] fontData = new FontData [1];
	fontData [0] = new FontData ();

	assertNull(fontDialog.getFontList());

	fontDialog.setFontList(fontData);
	assertArrayEquals(fontDialog.getFontList(), fontData);

	fontDialog.setFontList(null);
	assertNull(fontDialog.getFontList());
}

@Test
public void test_setRGBLorg_eclipse_swt_graphics_RGB() {
	RGB rgb = new RGB(255, 0, 0);
	fontDialog.setRGB(rgb);
	RGB rgb2 = fontDialog.getRGB();
	assertEquals(rgb, rgb2);
}

/* custom */
FontDialog fontDialog;
}
