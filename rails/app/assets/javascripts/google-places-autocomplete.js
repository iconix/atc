// initialise the google maps objects, and add autocomplete listener
function init(){
  
  // center of the universe - Stanford, CA
  var latlng = new google.maps.LatLng(37.427502, -122.170286);

  var mapOptions = {
    zoom: 14,
    center: latlng,
    mapTypeId: google.maps.MapTypeId.ROADMAP
  };
  var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
  
  // Address Autocomplete
  var input = document.getElementById('location_search');
  var autocomplete = new google.maps.places.Autocomplete(input);
  autocomplete.bindTo('bounds', map);

  // Autocomplete Listener
  google.maps.event.addListener(autocomplete, 'place_changed', function() {
    var place = autocomplete.getPlace();
    var address;
    if (place.types == "street_address")
      address = place.formatted_address
    else
      address = place.name + ", " + place.formatted_address;

    console.log(address);

    $('#location_search').val(address);
    $('#deal_address').val(address);
    $('#business_address').val(address);
  });
}

$(document).ready(function() { 
  if( $('#map-canvas').length  ) {
    init();
  }; 
});