
/* 
    TO DO:
    implements functions for changing data source through api calls
*/

function getHotels(sourceId){
    //this function will call backend api for hotel spawn
    if (source=map.getSource(sourceId)){
        source.setData("http://localhost:8080/hotel")
    }
    else{
        map.addSource(sourceId, {
            type: 'geojson',
            data: 'http://localhost:8080/hotel'
        });
    }
}

function getPoints(sourceId,x,y){
    //this function will call backend api for hotel spawn
    if (source=map.getSource(sourceId)){
        source.setData("http://localhost:8080/point?long="+x+"&lat="+y)
    }
    else{
        map.addSource(sourceId, {
            type: 'geojson',
            data: "'http://localhost:8080/point?long='+x+'&lat='+y"
        });
    }
}

function getArea(sourceId){
    //this function will call backend api for hotel spawn
    if (source=map.getSource(sourceId)){
        source.setData("http://localhost:8080/area");
        map.setLayoutProperty('Point','visibility','none');
    }
    else{
        map.addSource(sourceId, {
            type: 'geojson',
            data: "'http://localhost:8080/area"
        });
    }
}


function prepareMap(){
    map.on('style.load',function(){
        map.addLayer({
            'id': 'Area',
            'type': 'fill',
            'source':{
                'type': 'geojson',
                'data':{
                    'type': 'FeatureCollection',
                    'features': []
                }
            },
            "layout":{
            },
            'paint': {
                'fill-color': '#000fff',
                'fill-opacity': 0.5,
                'fill-outline-color':'#000',
                
            }
        });
        map.addLayer({
            'id': 'Point',
            'type': 'symbol',
            'source':{
                'type': 'geojson',
                'data':"http://localhost:8080/hotel"
                
            },
            "layout":{
                "icon-image": "{icon}",

                "icon-allow-overlap":true
                
                }
        });


        
    });

    map.on('click', function (e) {
        var features = map.queryRenderedFeatures(e.point);
        for (i=0;i<features.length;i++){
            if(features[i].layer.id=='Point' && features[i].geometry.type=='Point' && features[i].properties.historic!=null){
                $('#features').html(features[i].properties.title+" "+features[i].properties.historic);
            }else if(features[i].layer.id=='Point' && features[i].geometry.type=='Point'){
                
                getPoints('Point',features[0].geometry.coordinates[0],features[0].geometry.coordinates[1]);
                points=map.getSource('Point');
                console.log(points);

            }
        }
    });
}