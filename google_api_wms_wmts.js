var tiledLayer = new google.maps.ImageMapType({
	getTileUrl: function (coord, zoom) {
		var url = "http://210.65.131.74/R02map/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0";
		url += "&LAYER=DH6762";
		url += "&STYLE=_null";
		url += "&TILEMATRIXSET=EPSG:3857";
		url += "&TILEMATRIX=EPSG:3857:" + zoom; // zoom
		url += "&TILECOL=" + coord.x; //437346" + //x
		url += "&TILEROW=" + coord.y; //227621" + //y
		url +="&FORMAT=image/png";
		return url;                 // return URL for the tile

	},
	tileSize: new google.maps.Size(256, 256),
	isPng: true
});

var wmsLayer = new google.maps.ImageMapType({
	getTileUrl: function (coord, zoom) {
		var proj = map.getProjection();
		var zfactor = Math.pow(2, zoom);
		// get Long Lat coordinates
		var top = proj.fromPointToLatLng(new google.maps.Point(coord.x * 256 / zfactor, coord.y * 256 / zfactor));
		var bot = proj.fromPointToLatLng(new google.maps.Point((coord.x + 1) * 256 / zfactor, (coord.y + 1) * 256 / zfactor));

		//corrections for the slight shift of the SLP (mapserver)
		var deltaX = 0.0013;
		var deltaY = 0.00058;

		//create the Bounding box string
		var bbox =     (top.lng() + deltaX) + "," +
			(bot.lat() + deltaY) + "," +
			(bot.lng() + deltaX) + "," +
			(top.lat() + deltaY);
		console.log(bbox);
		// base WMS URL
		// DPI=96&MAP_RESOLUTION=96&FORMAT_OPTIONS=dpi:96&TRANSPARENT=TRUE
		var url = "https://wms.nlsc.gov.tw/wms?SERVICE=WMS&VERSION=1.1.1&REQUEST=GetMap";
		url += "&BBOX=" + bbox;
		url += "&SRS=EPSG:3857";
		url += "&LAYER=LANDSECT";
		url += "&STYLES=";
		url += "&FORMAT=image/png" ;
		url += "&DPI=96&MAP_RESOLUTION=96&FORMAT_OPTIONS=dpi:96&TRANSPARENT=TRUE";
		url += "&WIDTH=256";
		url += "&HEIGHT=256";
		return url;                 // return URL for the tile

	},
	tileSize: new google.maps.Size(256, 256),
	isPng: true
});

map.overlayMapTypes.push(tiledLayer);
map.overlayMapTypes.push(wmsLayer);