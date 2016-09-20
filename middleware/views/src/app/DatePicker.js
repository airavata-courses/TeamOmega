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
  state = {date2: '', locDisabled: '', location: '', timeDisabled: '', time, submitDisabled: ''}

  handleDateChange = (value) => {
    this.setState({date2: value,locDisabled: "false"});
    console.log(this.state.date2);
  }

  handleTimeChange = (time) => {
    this.setState({time});
    console.log(this.state.time);
  }


  handleLocationChange = (value) => {
    this.setState({location: value,timeDisabled:"false"});
    console.log(this.state.location);
    fetch('https://github.com/')
    .then(function(res) {
        return res.text();
    }).then(function(body) {
        console.log(body);
    });
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