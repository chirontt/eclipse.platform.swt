/*******************************************************************************
 * Copyright (c) 2024 Yatta Solutions
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Yatta Solutions - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

import java.util.function.*;

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

/**
 * Snippet to test the use of the DPI-aware Image constructors with disabled and gray images
 * Work in progress in https://bugs.eclipse.org/399786
 * <p>
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * </p>
 */
public class Snippet382 {
	private static final String IMAGE_100 = "eclipse16.png";
	private static final String IMAGE_150 = "eclipse24.png";
	private static final String IMAGE_200 = "eclipse32.png";
	private static final String IMAGES_ROOT = "bin/org/eclipse/swt/snippets/";

	private static final String IMAGE_PATH_100 = IMAGES_ROOT + IMAGE_100;
	private static final String IMAGE_PATH_150 = IMAGES_ROOT + IMAGE_150;
	private static final String IMAGE_PATH_200 = IMAGES_ROOT + IMAGE_200;

	public static void main (String [] args) {
		final ImageFileNameProvider filenameProvider = zoom -> {
			switch (zoom) {
			case 100:
				return IMAGE_PATH_100;
			case 150:
				return IMAGE_PATH_150;
			case 200:
				return IMAGE_PATH_200;
			default:
				return null;
			}
		};
		final ImageDataProvider imageDataProvider = zoom -> {
			switch (zoom) {
			case 100:
				return new ImageData (IMAGE_PATH_100);
			case 150:
				return new ImageData (IMAGE_PATH_150);
			case 200:
				return new ImageData (IMAGE_PATH_200);
			default:
				return null;
			}
		};

		final Display display = new Display ();

		final Image imageWithFileNameProvider = new Image (display, filenameProvider);
		final Supplier<Image> disabledImageWithFileNameProvider = () ->  new Image (display,imageWithFileNameProvider, SWT.IMAGE_DISABLE);
		final Supplier<Image> greyImageWithFileNameProvider = () ->  new Image (display,imageWithFileNameProvider, SWT.IMAGE_GRAY);

		final Image imageWithDataProvider = new Image (display, imageDataProvider);
		final Image disabledImageWithDataProvider = new Image (display,imageWithDataProvider, SWT.IMAGE_DISABLE);
		final Image greyImageWithDataProvider = new Image (display,imageWithDataProvider, SWT.IMAGE_GRAY);

		final Image imageWithData = new Image (display, IMAGE_PATH_100);
		final Image disabledImageWithData = new Image (display,imageWithData, SWT.IMAGE_DISABLE);
		final Image greyImageWithData = new Image (display,imageWithData, SWT.IMAGE_GRAY);

		final Supplier<Image> imageWithDataCopy = () ->   new Image (display, imageWithDataProvider, SWT.IMAGE_COPY);
		final Supplier<Image> disabledImageWithDataCopy = () ->  new Image (display,disabledImageWithDataProvider, SWT.IMAGE_COPY);
		final Supplier<Image> greyImageWithDataCopy = () ->  new Image (display,greyImageWithDataProvider, SWT.IMAGE_COPY);

		final ImageGcDrawer imageGcDrawer = (gc, width, height) -> {
			gc.setBackground(display.getSystemColor(SWT.COLOR_RED));
			gc.fillRectangle(0, 0, width, height);
			gc.setForeground(display.getSystemColor(SWT.COLOR_YELLOW));
			gc.drawRectangle(4, 4, width - 8, height - 8);
		};

		final Image imageWithGcDrawer = new Image (display, imageGcDrawer, 16, 16);
		final Image disabledImageWithGcDrawer = new Image (display, imageWithGcDrawer, SWT.IMAGE_DISABLE);
		final Image greyImageWithGcDrawer = new Image (display, imageWithGcDrawer, SWT.IMAGE_GRAY);

		final ImageGcDrawer transparentImageGcDrawer = new ImageGcDrawer() {
			@Override
			public void drawOn(GC gc, int width, int height) {
				gc.drawImage(imageWithDataProvider, 0, 0);
			}
			@Override
			public int getGcStyle() {
				return SWT.TRANSPARENT;
			}
		};

		final Image imageWithTransparentGcDrawer = new Image (display, transparentImageGcDrawer, 16, 16);
		final Image disabledImageWithTransparentGcDrawer = new Image (display, imageWithTransparentGcDrawer, SWT.IMAGE_DISABLE);
		final Image greyImageWithTransparentGcDrawer = new Image (display, imageWithTransparentGcDrawer, SWT.IMAGE_GRAY);

		final Shell shell = new Shell (display);
		shell.setText("Snippet382");
		shell.setLayout (new GridLayout (3, false));
		Listener l = new Listener() {
			@Override
			public void handleEvent(Event e)  {
				if (e.type == SWT.Paint) {
					GC mainGC = e.gc;
					GCData gcData = mainGC.getGCData();
					gcData.nativeZoom = 100;

					try {
						mainGC.drawText("--ImageFileNameProvider--", 20, 20);
						drawImages(mainGC, gcData, "Normal",40, imageWithFileNameProvider);
						drawImages(mainGC, gcData, "Disabled",80, disabledImageWithFileNameProvider.get());
						drawImages(mainGC, gcData, "Greyed",120, greyImageWithFileNameProvider.get());

						mainGC.drawText("--ImageDataProvider--", 20, 150);
						drawImages(mainGC, gcData, "Normal",180, imageWithDataProvider);
						drawImages(mainGC, gcData, "Disabled",220, disabledImageWithDataProvider);
						drawImages(mainGC, gcData, "Greyed",260, greyImageWithDataProvider);

						mainGC.drawText("--Image with ImageData--", 20, 290);
						drawImages(mainGC, gcData, "Normal",320, imageWithData);
						drawImages(mainGC, gcData, "Disabled",360, disabledImageWithData);
						drawImages(mainGC, gcData, "Greyed",400, greyImageWithData);

						mainGC.drawText("--ImageDataProvider Copy--", 20, 430);
						drawImages(mainGC, gcData, "Normal",460, imageWithDataCopy.get());
						drawImages(mainGC, gcData, "Disabled",500, disabledImageWithDataCopy.get());
						drawImages(mainGC, gcData, "Greyed",540, greyImageWithDataCopy.get());

						mainGC.drawText("--ImageGcDrawer--", 20, 570);
						drawImages(mainGC, gcData, "Normal", 600, imageWithGcDrawer);
						drawImages(mainGC, gcData, "Disabled", 640, disabledImageWithGcDrawer);
						drawImages(mainGC, gcData, "Greyed", 680, greyImageWithGcDrawer);

						mainGC.drawText("--Transparent ImageGcDrawer--", 20, 710);
						drawImages(mainGC, gcData, "Normal", 740, imageWithTransparentGcDrawer);
						drawImages(mainGC, gcData, "Disabled", 780, disabledImageWithTransparentGcDrawer);
						drawImages(mainGC, gcData, "Greyed", 820, greyImageWithTransparentGcDrawer);
					} finally {
						mainGC.dispose ();
					}
				}
			}

			private void drawImages(GC mainGC, GCData gcData, String text, int y, final Image image) {
				gcData.nativeZoom = 100;
				mainGC.drawText(text, 0, y);
				mainGC.drawImage(image, 50, y);
				gcData.nativeZoom = 150;
				mainGC.drawImage(image, 100, (int) (y/1.5));
				gcData.nativeZoom = 200;
				mainGC.drawImage(image, 150, y/2);
				gcData.nativeZoom = 100;
			}
		};
		shell.addListener(SWT.Paint, l);

		shell.setSize(400, 750);
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
	}

}
