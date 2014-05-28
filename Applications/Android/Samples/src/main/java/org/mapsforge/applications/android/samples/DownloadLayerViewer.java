/*
 * Copyright 2010, 2011, 2012, 2013 mapsforge.org
 * Copyright © 2013-2014 Ludwig M Brinckmann
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
package org.mapsforge.applications.android.samples;

import org.mapsforge.map.android.graphics.AndroidGraphicFactory;
import org.mapsforge.map.layer.download.TileDownloadLayer;
import org.mapsforge.map.layer.download.tilesource.OpenStreetMapMapnik;

/**
 * Shows how to use a tile download layer.
 */
public class DownloadLayerViewer extends BasicMapViewerXml {
	private TileDownloadLayer downloadLayer;

	@Override
	public void onPause() {
		super.onPause();
		this.downloadLayer.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		this.downloadLayer.onResume();
	}

	@Override
	protected void createLayers() {
		this.downloadLayer = new TileDownloadLayer(this.tileCaches.get(0),
				this.mapViewPositions.get(0), OpenStreetMapMapnik.INSTANCE,
				AndroidGraphicFactory.INSTANCE);
		this.layerManagers.get(0).getLayers().add(this.downloadLayer);
	}

}
