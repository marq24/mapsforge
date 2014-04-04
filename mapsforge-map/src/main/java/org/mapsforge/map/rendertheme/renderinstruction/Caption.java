/*
 * Copyright 2010, 2011, 2012, 2013 mapsforge.org
 * Copyright 2014 Ludwig M Brinckmann
 *
 * This program is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.mapsforge.map.rendertheme.renderinstruction;

import org.mapsforge.core.graphics.Bitmap;
import org.mapsforge.core.graphics.CaptionPosition;
import org.mapsforge.core.graphics.Paint;
import org.mapsforge.core.model.Tag;
import org.mapsforge.map.rendertheme.RenderCallback;

import java.util.List;

/**
 * Represents a text label on the map.
 *
 * If a bitmap symbol is present the caption position is calculated relative to the bitmap, the
 * center of which is at the point of the POI. The bitmap itself is never rendered.
 *
 */

public class Caption extends RenderInstruction {

	private final Bitmap bitmap;
	private final CaptionPosition captionPosition;
	private final float dy;
	private final Paint fill;
	private final float fontSize;
	private final float gap;
	private final Paint stroke;
	private final TextKey textKey;

	Caption(CaptionBuilder captionBuilder) {
		super(captionBuilder.getCategory());
		this.bitmap = captionBuilder.bitmap;
		this.captionPosition = captionBuilder.captionPosition;
		this.gap = captionBuilder.gap;
		this.dy = captionBuilder.dy;
		this.fill = captionBuilder.fill;
		this.fontSize = captionBuilder.fontSize;
		this.stroke = captionBuilder.stroke;
		this.textKey = captionBuilder.textKey;
	}

	@Override
	public void destroy() {
		// no-op
	}

	@Override
	public void renderNode(RenderCallback renderCallback, List<Tag> tags) {
		String caption = this.textKey.getValue(tags);
		if (caption == null) {
			return;
		}

		float horizontalOffset = 0f;
		float verticalOffset = this.dy;

		if (this.bitmap != null) {
			// calculations if the position of the caption is relative to a symbol
			float textHeight;
			if (this.stroke != null) {
				textHeight = this.stroke.getTextHeight(caption);
			} else {
				textHeight = this.fill.getTextHeight(caption);
			}

			if (CaptionPosition.RIGHT == this.captionPosition || CaptionPosition.LEFT == this.captionPosition) {
				float textWidth;
				if (this.stroke != null) {
					textWidth = this.stroke.getTextWidth(caption) / 2f;
				} else {
					textWidth = this.fill.getTextWidth(caption) / 2f;
				}
				horizontalOffset = this.bitmap.getWidth() / 2f + this.gap + textWidth;
				if (CaptionPosition.LEFT == this.captionPosition) {
					horizontalOffset *= -1f;
				}
				verticalOffset = textHeight / 2f;
			} else if (CaptionPosition.ABOVE == this.captionPosition) {
				verticalOffset -= this.bitmap.getHeight() / 2f + this.gap;
			} else if (CaptionPosition.BELOW == this.captionPosition) {
				verticalOffset += this.bitmap.getHeight() / 2f + this.gap + textHeight;
			}
		}

		renderCallback.renderPointOfInterestCaption(caption, horizontalOffset, verticalOffset, this.fill, this.stroke);
	}

	@Override
	public void renderWay(RenderCallback renderCallback, List<Tag> tags) {
		String caption = this.textKey.getValue(tags);
		if (caption == null) {
			return;
		}
		renderCallback.renderAreaCaption(caption, this.dy, this.fill, this.stroke);
	}

	@Override
	public void scaleStrokeWidth(float scaleFactor) {
		// do nothing
	}

	@Override
	public void scaleTextSize(float scaleFactor) {
		this.fill.setTextSize(this.fontSize * scaleFactor);
		this.stroke.setTextSize(this.fontSize * scaleFactor);
	}

}
