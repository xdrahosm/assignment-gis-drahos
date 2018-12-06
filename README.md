# General course assignment

Build a map-based application, which lets the user see geo-based data on a map and filter/search through it in a meaningfull way. Specify the details and build it in your language of choice. The application should have 3 components:

1. Custom-styled background map, ideally built with [mapbox](http://mapbox.com). Hard-core mode: you can also serve the map tiles yourself using [mapnik](http://mapnik.org/) or similar tool.
2. Local server with [PostGIS](http://postgis.net/) and an API layer that exposes data in a [geojson format](http://geojson.org/).
3. The user-facing application (web, android, ios, your choice..) which calls the API and lets the user see and navigate in the map and shows the geodata. You can (and should) use existing components, such as the Mapbox SDK, or [Leaflet](http://leafletjs.com/).

## Data sources

- [Open Street Maps](https://www.openstreetmap.org/)

## My project

Fill in (either in English, or in Slovak):

**Name: Marek Drahos  
**Application description**: Aplikácia zobrazujúca hotely v Londýne. po vybatí konkrétneho hotela zobrazí 10 najbližších zaujímacých bodov v okolí. Taktiež pnoúka zobrazenie oblastí Londýna, ktoré sú farebne odlíšené na základe poètu zloèinov, ktoré sa v nich stali.

**Data source**: Open Street Maps, https://data.police.uk/data/

**Technologies used**: Java, Spring Boot, Mapbox JS