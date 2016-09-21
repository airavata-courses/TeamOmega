import DatePicker from 'react-toolbox/lib/date_picker';
import Autocomplete from 'react-toolbox/lib/autocomplete';
import TimePicker from 'react-toolbox/lib/time_picker';
import SubmitButton from './SubmitButton.js'
// And then just use global variable.
import React from 'react';

var Loading = require('react-loading');



import 'whatwg-fetch';
// And then just use global variable.
var locationsArray = { };


// if you are on node v0.10, set a Promise library first, eg.
// fetch.Promise = require('bluebird');

// plain text or html


const datetime = new Date();
var time = new Date();
const min_datetime = new Date(1991, 11, 11);


class DatePickerIn extends React.Component {
  state = {date2: '',date_url: '', locDisabled: '', location: '', timeDisabled: '', time, submitDisabled: '',loading:0}

  handleDateChange = (value) => {
    var d = value;

    var month = d.getMonth();
    var day = d.getDate();
    if(day<10){
      day = '0'+day.toString()
    }
    if(month<10){
      month = '0'+month.toString()
    }
    var date_format = d.getFullYear()+"/"+month+"/"+day+"/";
    this.setState({date2: d, date_url: date_format,location: ''});
    
    fetch('/home/submit',{method: "POST",  headers: {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
  },
   body: JSON.stringify({date: date_format})

 })
    .then(function(res) {
        return res.text();
    }).then(function(body) {
        locationsArray = {}
       var body1 = JSON.parse(body);
      for (var key in body1) {
        if (body1.hasOwnProperty(key)) {

          locationsArray[key]=body1[key][3];
        }
      }
    });
    this.setState({locDisabled:"false"});


  }

  handleTimeChange = (time) => {
    this.setState({time});
    console.log(this.state.time);
  }

  handleSubmit = () => {
     this.setState({loading:1});
    fetch('/home/submit',{method: "POST",  headers: {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
  },
   body: JSON.stringify({date: this.state.date_url, location: this.state.location})

 })
    .then(function(res) {
        return res.text();

    }).then(function(body) {
        console.log(body);
    });

  }




  handleLocationChange = (value) => {
     this.setState({location: value,submitDisabled:"false"});
  };

  render () {
    return (
      <section>

        <DatePicker 
          label='Select a Date' 
          sundayFirstDayOfWeek 
          minDate={min_datetime} 
          maxDate={datetime} 
          onChange={this.handleDateChange} 
          value={this.state.date2} 
        />
        <TimePicker
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
                  source={locationsArray}
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
        { this.state.loading  ? <Loading type='cylon' color='#00796B' /> : null }
      </section>
      );
  }
}

export default DatePickerIn;