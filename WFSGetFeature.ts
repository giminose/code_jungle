// 使用WFS GetFeature，先mark掉作紀錄
const extent: ol.Extent = this.dragBox.getGeometry().getExtent();
const minXY = ol.proj.transform([extent[0], extent[1]], this.mapProjection, this.projections.twd97);
const maxXY = ol.proj.transform([extent[2], extent[3]], this.mapProjection, this.projections.twd97);
console.log(extent[0] + ',' + extent[1] + ',' + extent[2] + ',' + extent[3]);
console.log(minXY[0] + ',' + minXY[1] + ',' + maxXY[0] + ',' + maxXY[1]);
let wfsUrl = 'http://192.168.6.49:8080/geoserver/wfs?';
wfsUrl += 'service=wfs&';
wfsUrl += 'version=2.0.0&';
wfsUrl += 'request=GetFeature&';
wfsUrl += 'typeName=systex:land&';
wfsUrl += 'srsName=' + this.mapProjection.getCode() + '&';
wfsUrl += 'bbox=' + minXY[0] + ',' + minXY[1] + ',' + maxXY[0] + ',' + maxXY[1] + ',' + 'EPSG:3826' + '&';
wfsUrl += 'outputFormat=application/json';
this.service.getFeature(wfsUrl).subscribe(
	res => {
		res.forEach(e => {
			console.log(e.geometry);
			const features = new ol.format.GeoJSON().readFeatures(e.geometry);
			const style = new ol.style.Style({
				stroke: new ol.style.Stroke({
					color: 'rgba(0, 0, 255, 1.0)',
					width: 2
				})
			});
			features.forEach(f => {
				f.setStyle(style);
			});
			this.drawSource.addFeatures(features);
		});
	}
);