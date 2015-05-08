var geocoder;
geocoder = new google.maps.Geocoder();
function codeAddress() {
    var address = document.getElementById('address').value;
    geocoder.geocode( { 'address': address}, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            document.getElementById('inputCoordinateN').setAttribute('value', results[0].geometry.location.lat());
            document.getElementById('inputCoordinateE').setAttribute('value', results[0].geometry.location.lng());
        } else {
            alert('Geocode was not successful for the following reason: ' + status);
        }
    });
}