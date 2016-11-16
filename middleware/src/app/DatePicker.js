import DatePicker from 'react-toolbox/lib/date_picker';
import Autocomplete from 'react-toolbox/lib/autocomplete';
import TimePicker from 'react-toolbox/lib/time_picker';
import SubmitButton from './SubmitButton.js'
import theme from './timepicker_fix.scss';
// And then just use global variable.
import React from 'react';
import ReactDOM from 'react-dom';

import io from 'socket.io-client';




var Loading = require('react-loading');

import { withGoogleMap, GoogleMap, Circle } from "react-google-maps";





import 'whatwg-fetch';
// And then just use global variable.
var locationsArray = { };


// if you are on node v0.10, set a Promise library first, eg.
// fetch.Promise = require('bluebird');

// plain text or html


const datetime = new Date();
var time = new Date();
const min_datetime = new Date(1991, 11, 11);

var fct = "";
var icon_prefix = "mdi mdi-weather-";
var icon_url = "";
var email = getCookie("email");
var room = guid();

var gps_coord = {};

///generate unique uuid
function guid() {
  return email +"-"+ s4() + s4() + s4() + s4();
}

function s4() {
  return Math.floor((1 + Math.random()) * 0x10000)
    .toString(16)
    .substring(1);
}
console.log(room);


function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return  decodeURIComponent(c.substring(name.length,c.length));
        }
    }
    return "";
}

// Wrap all `react-google-maps` components with `withGoogleMap` HOC
var SimpleMapExampleGoogleMap = withGoogleMap(props => (
 
  <GoogleMap
    zoom={5}
    
    center={props.center}
    >
    <Circle
        center={props.center}
        radius={100000}
        options={{
          fillColor: `red`,
          fillOpacity: 0.20,
          strokeColor: `red`,
          strokeOpacity: 1,
          strokeWeight: 1,
        }}
      />
  </GoogleMap>
  ));



var Soc = React.createClass({
  getInitialState: function(){
    return{
      comments: "",
      forecast:"",
      predict:""
    };
  },
  componentDidMount: function(){

    this.socket = io();
    var _this=this;
    this.socket.on('connect', function() {
    // Connected, let's sign-up for to receive messages for this room
     _this.socket.emit('room', room);
    });

    this.socket.on('message', function(comment){
      _this.setState({comments:comment});
    });

  this.socket.on('locations', function(comment) {
    // Connected, let's sign-up for to receive messages for this room
      _this.props.addLocArray(comment);
    });

  
    this.socket.on('icon', function(comment){
    
    icon_url = icon_prefix+comment;
    _this.setState({forecast:icon_url,predict:comment});
    });
  },
  render: function(){
    return(

      <div>
        {this.state.comments}
         {this.state.forecast ? <div><h1><i className={this.state.forecast}></i> </h1> <h3>{this.state.predict}</h3></div>: null }
      </div>

    );
  }
});



class DatePickerIn extends React.Component {
  state = {gps:{lat:39.1703827,lng:-86.5164435} ,fct1: '',date2: '',date_url: '', locDisabled: '', location: '', timeDisabled: '', time, submitDisabled: '',loading:0, forecast: 0,locArray:{}}

  handleDateChange = (value) => {

    var d = value;
    var _this = this;
    var month = d.getMonth();
    var day = d.getDate();
    if(day<10){
      day = '0'+day.toString()
    }
    if(month<10){
      month = '0'+month.toString()
    }
    var date_format = d.getFullYear()+"/"+month+"/"+day+"/";
    this.setState({forecast:0,date2: d, date_url: date_format,location: '',submitDisabled: '',locDisabled:'', forecast:'',locArray:{}});
    console.log(date_format);
    fetch('/home/submit',{method: "POST", credentials: 'same-origin', headers: {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
  },
   body: JSON.stringify({room:room,  date: date_format,type:0,timest:this.state.time, req_no : (new Date).getTime()})

 })
    .then(function(res) {
        return res.text();
    }).then(function(body) {
          
    });
    
    

  }

  addLocArray = (value) => {
      locationsArray = {};
      gps_coord = {};

      var body = JSON.parse(value);
      for (var key in body) {
        if (body.hasOwnProperty(key)) {
          gps_coord[key] = {lat:parseFloat(body[key][1]),lng:parseFloat(body[key][0]) };
          locationsArray[key]=body[key][3];
        }
      }
    this.setState({locDisabled:"false",locArray:locationsArray});
  }

  handleTimeChange = (time) => {
    this.setState({time});
    console.log(this.state.time);
  }

  handleSubmit = () => {
     this.setState({loading:1,forecast:0});
     var _this = this;
     var location = this.state.date_url+this.state.location+"/";
     var timestamp = [this.state.time.getHours(), this.state.time.getSeconds(), this.state.time.getMinutes()];
     var time1 = '';
      for (var item in timestamp){
        if (item < 10){
          time1+=("0"+String(item));
        }
        else{
          time1+=(String(item));
        }
      }
      req_no += 1;
    fetch('/home/submit',{ method: "POST", credentials: 'same-origin', headers: {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
  },
   body: JSON.stringify({room:room, date:location , type:1,timest:time1, req_no : (new Date).getTime()})

 })
    .then(function(res) {
        return res.text();

    }).then(function(body) {
        console.log(body);
        
    });
    

  }




  handleLocationChange = (value) => {
     this.setState({gps:gps_coord[value],location: value,submitDisabled:"false"});
  };

  render () {
    return (
      <section >
      
        <DatePicker 
          label='Select a Date' 
          sundayFirstDayOfWeek 
          minDate={min_datetime} 
          maxDate={datetime} 
          onChange={this.handleDateChange} 
          value={this.state.date2} 
        />
        <TimePicker
          theme={theme}
          label='Select Time'
          format='ampm'
          hint="optional"
          disabled={!this.state.locDisabled}
          onChange={this.handleTimeChange}
          value={this.state.time}
        />
        
       <Autocomplete
                  direction="down"
                  label="Select Location"
                  disabled={!this.state.locDisabled}
                  multiple={false}
                  onChange={this.handleLocationChange}
                  source={this.state.locArray}
                  value={this.state.location}
                />
        <SubmitButton
          accent
          raised
          primary
          disabled = {!this.state.submitDisabled}
          label = "Submit" 
          onClick={this.handleSubmit}
        />

        {this.state.loading  ? <Loading type='cylon' color='#00796B' /> : null }

        <Soc addLocArray={this.addLocArray} />
          <SimpleMapExampleGoogleMap center={this.state.gps}
        containerElement={
          <div style={{ height: `400px` }} />
        }
        mapElement={
          <div style={{ height: `400px` }} />
        }
      />
      </section>
      );
  }
}

export default DatePickerIn;