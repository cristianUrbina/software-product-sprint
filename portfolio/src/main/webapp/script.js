// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */

let map;
let tmpMarker;

function addRandomGreeting() {
  const greetings =
      ['I love to eat bread', 'I like gossip, specially when it does not involve me','Architects is my favorite band', '"I\'m ready"-Sponge Bob'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}


async function getRandomFact(){
    const responseFromServer = await fetch('/hello');
    const facts = await responseFromServer.json();
    const fact = facts[Math.floor(Math.random() * facts.length)];
    const randomFactContainer = document.getElementById('random-fact-container');
    randomFactContainer.innerText = fact;
}

/** Creates a map and adds it to the page. */
function createMap() {
  const hometown = new google.maps.LatLng(22.265616, -97.855937);
  const highschool = new google.maps.LatLng(22.279427, -97.853034);
  const house = new google.maps.LatLng(22.24068001371074, -97.84064914423382);
  const mapOptions = {
    zoom: 12,
    center: hometown,
    mapTypeId: 'hybrid',
    mapId: 'd60cfbd9b7bbc615'
  };
  map = new google.maps.Map(document.getElementById('map'), mapOptions);
  map.addListener('click', (event) => {
      createMarkerForEdit(event.latLng.lat(), event.latLng.lng());
  });
  const highschoolMarker = new google.maps.Marker({
      position: highschool,
      map: map,
      title: 'My Highschool'
  });
  const houseMarker = new google.maps.Marker({
      position: house,
      map: map,
      title: 'My previous house'
  });
  const highschoolInfoWindow = new google.maps.InfoWindow({content: 'CBTIs 103'});
  highschoolInfoWindow.open(map,highschoolMarker);
}

function createMarkerForDisplay(lat, lng, content) {
  const marker =
      new google.maps.Marker({position: {lat: lat, lng: lng}, map: map});

  const infoWindow = new google.maps.InfoWindow({content: content});
  marker.addListener('click', () => {
    infoWindow.open(map, marker);
  });
}

function createMarkerForEdit(lat, lng){
    if(tmpMarker){
        tmpMarker.setMap(null);
    }
    tmpMarker = new google.maps.Marker({position:{lat:lat, lng:lng}, map:map});
    const infoWindow = new google.maps.InfoWindow({content:buildInfoWindowInput(lat,lng)});
    google.maps.event.addListener(infoWindow, 'closeclick',() => {
        tmpMarker.setMap(null);
    });
    infoWindow.open(map,tmpMarker);
}

function postMarker(lat, lng, content) {
  const params = new URLSearchParams();
  params.append('lat', lat);
  params.append('lng', lng);
  params.append('content', content);

  fetch('/markers', {method: 'POST', body: params});
}

function buildInfoWindowInput(lat, lng) {
  const textBox = document.createElement('textarea');
  const button = document.createElement('button');
  button.appendChild(document.createTextNode('Submit'));

  button.onclick = () => {
    postMarker(lat, lng, textBox.value);
    createMarkerForDisplay(lat, lng, textBox.value);
    tmpMarker.setMap(null);
  };

  const containerDiv = document.createElement('div');
  containerDiv.appendChild(textBox);
  containerDiv.appendChild(document.createElement('br'));
  containerDiv.appendChild(button);

  return containerDiv;
}
