var tiledLayer = new google.maps.ImageMapType({
	getTileUrl: function (coord, zoom) {
		var url = "http://210.65.131.74/R02map/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0";
		url += "&LAYER=DH6762";
		url += "&STYLE=_null";
		url += "&TILEMATRIXSET=EPSG:3857";
		url += "&TILEMATRIX=EPSG:3857:" + zoom; 
		url += "&TILECOL=" + coord.x; 
		url += "&TILEROW=" + coord.y;
		url +="&FORMAT=image/png";
		return url;                 // return URL for the tile

		// another tiled url
		//var url = "http://wmts.nlsc.gov.tw/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0";
		//url += "&LAYER=LANDSECT";
		//url += "&STYLE=_null";
		//url += "&TILEMATRIXSET=EPSG:3857";
		//url += "&TILEMATRIX=EPSG:3857:" + zoom; 
		//url += "&TILECOL=" + coord.x; 
		//url += "&TILEROW=" + coord.y; 
		//url +="&FORMAT=image/png";
		//return url;

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

		//create the Bounding box string
		var bbox = top.lng() + "," + bot.lat() + "," + bot.lng() + "," + top.lat();
		var url = "https://wms.nlsc.gov.tw/wms?SERVICE=WMS&VERSION=1.1.1&REQUEST=GetMap";
		url += "&BBOX=" + bbox;
		url += "&SRS=EPSG:4326";
		url += "&LAYERS=LANDSECT";
		url += "&STYLES=_null";
		url += "&FORMAT=image%2Fpng" ;
		url += "&TRANSPARENT=TRUE";
		url += "&WIDTH=256";
		url += "&HEIGHT=256";
		console.log(url);
		return url;

	},
	tileSize: new google.maps.Size(256, 256),
	isPng: true
});

map.overlayMapTypes.push(tiledLayer);
map.overlayMapTypes.push(wmsLayer);