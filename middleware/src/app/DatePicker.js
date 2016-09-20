import DatePicker from 'react-toolbox/lib/date_picker';
import Autocomplete from 'react-toolbox/lib/autocomplete';
import TimePicker from 'react-toolbox/lib/time_picker';
// And then just use global variable.
import React from 'react';


import 'whatwg-fetch';
// And then just use global variable.
var locationsArray = {
  'ES-es': 'Spain',
  'TH-th': 'Thailand',
  'EN-gb': 'England',
  'EN-en': 'USA'
};


// if you are on node v0.10, set a Promise library first, eg.
// fetch.Promise = require('bluebird');

// plain text or html


const datetime = new Date();
var time = new Date();
const min_datetime = new Date(1991, 11, 11);


class DatePickerIn extends React.Component {
  state = {date2: '',date_url: '', locDisabled: '', location: '', timeDisabled: '', time, submitDisabled: ''}

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
    var date_format = "year="+d.getFullYear()+"&&"+"month="+month+"&&"+"day="+day;
    this.setState({date2: d, date_url: date_format, locDisabled: "false"});
    
    fetch('/home/submit',{method: "POST",  headers: {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
  },
   body: JSON.stringify({date: date_format})

 })
    .then(function(res) {
        return res.text();
    }).then(function(body) {
        console.log("Here....");
        console.log(body);
    });

  }

  handleTimeChange = (time) => {
    this.setState({time});
    console.log(this.state.time);
  }


  handleLocationChange = (value) => {
     this.setState({location: value,timeDisabled:"false"});
    
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
        
      </section>
    );
  }
}

export default DatePickerIn;