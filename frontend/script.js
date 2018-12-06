
/* 
    TO DO:
    implements functions for changing data source through api calls
*/

function getHotels(sourceId){
    //this function will call backend api for hotel spawn
    source.setData("http://localhost:8080/hotel")
    map.setLayoutProperty('Area','visibility','none');
    map.setLayoutProperty(sourceId,'visibility','visible');

}

function getPoints(sourceId,x,y,name){
    //this function will call backend api for hotel spawn
    if (source=map.getSource(sourceId)){
        source.setData("http://localhost:8080/point?long="+x+"&lat="+y+"&name="+name)
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
        map.setLayoutProperty(sourceId,'visibility','visible');
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
               'fill-color': [
                   'match',
                ['get', 'color'],
                    '1', '#ff4c05',
                    '2', '#ff7818',
                    '3', '#ffd30b',
                    '4', '#fffa08',
                    '5', '#ffffff',
                    /* other */ '#ff0000'],

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
        $('#features').html("");
        var features = map.queryRenderedFeatures(e.point);
        for (i=0;i<features.length;i++){
            if(features[i].properties.type=='hotel' && features[i].layer.id=='Point' ){
                getPoints('Point',features[i].geometry.coordinates[0],features[i].geometry.coordinates[1],features[i].properties.title);
                $('#features').html("<h1>"+features[i].properties.title+"</h1>");
                break;
            }else if(features[i].properties.type=='point' && features[i].layer.id=='Point'){
                $('#features').html("<h1>"+features[i].properties.title+"</h1>");
                break;
            }else if (features[i].layer.id=='Area'){
                $('#features').html("<h1>"+features[i].properties.title+"</h1><br>" +
                    "<p>Total crime count: "+features[i].properties.crimeCount+"</p>");
                break;
            }
        }
    });
}